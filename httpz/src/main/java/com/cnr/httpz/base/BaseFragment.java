package com.cnr.httpz.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.cnr.httpz.BaseResponse;
import com.cnr.httpz.R;
import com.cnr.httpz.http.BasePresenter;
import com.cnr.httpz.http.MainView;
import com.cnr.httpz.stateview.StateView;
import com.cnr.utils.MSConfig;
import com.cnr.utils.ToastUtils;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;

//onAttach -->  onCreate() -->onCreateView()-->onActivityCreated


//         getChildFragmentManager（）的定义是：返回一个私有FragmentManager，用于放置和管理Fragment中的Fragment。
//
//        同时getFragmentManager（）的定义是：返回FragmentManager以与与片段活动关联的片段进行交互。
//
//        基本上，区别在于Fragment现在有自己的内部FragmentManager可以处理Fragments。子FragmentManager是处理仅包含在它所添加的Fragment中的Fragments的子类。另一个FragmentManager包含在整个Activity中。
//
//        要在MainActivity上显示Fragment1，我们必须使用getSupportFragmentManager（）
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.container_view_on_main, Fragment1.newInstance());
//        要从Fragment1显示Fragment2，我们有两种方式
//
//        使用getFragmentManager（）
//
//        getFragmentManager().beginTransaction().replace(R.id.container_view_on_main, Fragment1.newInstance());
//        使用getChildFragmentManager（）
//
//        首先，我们必须在fragment1.xml中创建一个id为container_view_on_fragment1的布局
//
//        getChildFragmentManager().beginTransaction().replace(R.id.container_view_on_fragment1, Fragment2.newInstance()).commit();

public abstract class BaseFragment<P extends BasePresenter> extends Fragment
        implements ISupportFragment, SwipeRefreshLayout.OnRefreshListener,
        OnLoadMoreListener,
        View.OnTouchListener ,
        MainView<BaseResponse> {

    protected final static int START_PAGE_INDEX = MSConfig.COMMON_PAGE_START;
    protected final static int PAGE_NUM_COUNT = MSConfig.COMMON_PAGE_NUM;

    protected final static String START_PAGE = "startPage";
    protected final static String PAGE_NUM = "pageNumber";
    protected View mView;
    protected BaseActivity mActivity;
    protected int delayMillis = 300;
    protected boolean isRefresh;
    protected P mvpPresenter;
    protected StateView mStateView;

    protected abstract P createPresenter();

    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    /**
     * 请求参数，请求数据的索引
     */
    public int commonParam_fetchDataIndex = START_PAGE_INDEX;
    /**
     * 请求参数，每页取的数据量
     */
    public int commonParam_pageNumCount = PAGE_NUM_COUNT;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 反注册 EventBus
//        EventBus.getDefault().register(this);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {
            if (getLayoutId() != 0) {
                mView = inflater.inflate(getLayoutId(), null);
            }
        }catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEventAndData(savedInstanceState);
        mvpPresenter = createPresenter();

        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setOnRefreshListener(this);
            getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.colorAccent));
            getSwipeRefreshLayout().setProgressViewOffset(true, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics()));

            mStateView = StateView.inject(getSwipeRefreshLayout());
        }

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //防止点击穿透
        mView.setOnTouchListener(this);
        if (getSwipeRefreshLayout() != null){
            onRefresh();
        }
    }
    public void showTools(String text){
        Snackbar.make(mView.getRootView(), text,
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
    public void showNull(String str){
        ToastUtils.showShort(str);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 反注册 EventBus
//        EventBus.getDefault().unregister(this);
        if (mvpPresenter != null){
            mvpPresenter.onDestroy();
            mvpPresenter = null;
        }
    }

    @Override
    public void onRefresh() {
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setRefreshing(true);
        }
        new Handler().postDelayed(() -> {
            isRefresh = true;
            commonParam_fetchDataIndex = MSConfig.COMMON_PAGE_START;
            requestData();
        }, delayMillis);
    }

    @Override
    public void onLoadMore() {
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setEnabled(false);
        }
        new Handler().postDelayed(() -> {
            isRefresh = false;
            commonParam_fetchDataIndex += commonParam_pageNumCount;
            requestData();
        }, delayMillis);
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setEnabled(true);
        }
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }



    @Override
    public void onNext(BaseResponse baseResponse) {
        if (mStateView != null){
            mStateView.showContent();
        }
    }

    @Override
    public void onError(Throwable t) {
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setRefreshing(false);
        }
        if (mStateView != null){
            mStateView.showRetry();
            mStateView.setOnRetryClickListener(() -> {
                requestData();
            });
        }
    }

    @Override
    public void onCompleted() {
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setRefreshing(false);
        }
    }

    public void showEmpty(){
        if (mStateView != null){
            mStateView.showEmpty();
        }
    }

    /**
     * 添加 Fragment　getFragmentManager　显示底部
     * 管理首页 直播 分类 我的  播放页剧集列表
     * @param fragment
     */
    public void addFragment(@IdRes int fl_container, @NonNull  Fragment fragment, @NonNull String tag) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
        fragmentTransaction.replace(fl_container, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commitAllowingStateLoss();

    }
    public void addFragment(@IdRes int fl_container, @NonNull  Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.animator.fragment_slide_left_enter,
                R.animator.tv_fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
        fragmentTransaction.replace(fl_container,fragment);
        fragmentTransaction.commit();

    }
    /**
     * mActivity.getSupportFragmentManager()  getFragmentManager()
     *  管理  搜索页 搜索详情
     * @param fl_container
     * @param fragment
     * @param tag
     */
    public void addChildFragment(@IdRes int fl_container,@NonNull  Fragment fragment, @NonNull String tag) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
        fragmentTransaction.add(fl_container, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commitAllowingStateLoss();

    }

    public final void startActivity(@NonNull String extraName,int extraValue, @NonNull Class<?> targetActivity) {
        if (!TextUtils.isEmpty(extraName)) {
            final Intent intent = new Intent(mActivity, targetActivity);
            intent.putExtra(extraName, extraValue);
            startActivity(intent);
        } else {
            throw new NullPointerException("传递的值的键名称为null或空");
        }
    }
    public final void startActivity( @NonNull Class<?> targetActivity) {
        final Intent intent = new Intent(mActivity, targetActivity);
        startActivity(intent);
    }
}