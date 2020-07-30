package com.cnr.httpz.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cnr.httpz.BaseResponse;
import com.cnr.httpz.R;
import com.cnr.httpz.http.BasePresenter;
import com.cnr.httpz.http.MainView;
import com.cnr.httpz.stateview.StateView;
import com.cnr.utils.MSConfig;
import com.cnr.utils.ToastUtils;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;

import pub.devrel.easypermissions.EasyPermissions;


public abstract class BaseActivity<P extends BasePresenter> extends FragmentActivity
        implements View.OnClickListener, MainView<BaseResponse>,
        SwipeRefreshLayout.OnRefreshListener{
    protected final static int START_PAGE_INDEX = MSConfig.COMMON_PAGE_START;
    protected final static int PAGE_NUM_COUNT = MSConfig.COMMON_PAGE_NUM;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;

    public static final String[] LOCATION_AND_CONTACTS =
            {Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ,
                    Manifest.permission.READ_EXTERNAL_STORAGE
//                    ,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                    ,
//                    Manifest.permission.BLUETOOTH
//                    ,
//                    Manifest.permission.BLUETOOTH_ADMIN
//                    ,
//                    Manifest.permission.RECORD_AUDIO
//                    Manifest.permission.ACCESS_COARSE_LOCATION
            };
    private static final int REQUEST_ENABLE_BT = 100;

    protected Activity mContext;
    protected P mvpPresenter;
    protected FragmentManager fragmentManager;

    protected StateView mStateView;
    protected boolean isRefresh;

    /**
     * 请求参数，请求数据的索引
     */
    public int commonParam_fetchDataIndex = START_PAGE_INDEX;

    protected int delayMillis = 300;

    public Group mGroup;
    public Group getGroup() {
        return mGroup;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
//        EventBus.getDefault().register(this);
        mContext = this;
        initEventAndData(savedInstanceState);
        requestData();
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setOnRefreshListener(this);
            mStateView = StateView.inject(getSwipeRefreshLayout());
        }
    }

    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
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
    protected void onDestroy() {
        super.onDestroy();
        // 反注册 EventBus
//        EventBus.getDefault().unregister(this);
        if (mvpPresenter != null){
            mvpPresenter.onDestroy();
            mvpPresenter = null;
        }
    }


    /**
     * 获取 layout
     */
    @LayoutRes
    protected abstract int getLayout();

    /**
     * 请求网络数据
     */
    protected abstract void requestData();


    protected abstract P createPresenter();
    /**
     * 初始化数据和事件
     */
    protected abstract void initEventAndData(Bundle savedInstanceState);

    @Override
    public void onNext(BaseResponse baseResponse) {
        if (mStateView != null){
            mStateView.showContent();
        }
    }

    @Override
    public void onCompleted() {
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setRefreshing(false);
        }
    }

    @Override
    public void onError(Throwable t) {
        if (mStateView != null){
            mStateView.showRetry();
            mStateView.setOnRetryClickListener(() -> {
                requestData();
            });
        }
    }
    public void showEmpty(){
        if (mStateView != null){
            mStateView.showEmpty();
        }
    }


    protected final void startActivity(@NonNull Class<?> targetActivity) {

        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
    }
    protected final void startActivity(Bundle mBundle, @NonNull Class<?> targetActivity) {

        Intent intent = new Intent(this, targetActivity);
        if (null != mBundle) {
            intent.putExtras(mBundle);
        }
        startActivity(intent);
    }
    /**
     * 跳转到指定的Activity
     *
     * @param extraName      要传递的值的键名称
     * @param extraValue     要传递的String类型值
     * @param targetActivity 要跳转的目标Activity
     */
    public final void startActivity(@NonNull String extraName, @NonNull String extraValue, @NonNull Class<?> targetActivity) {
        if (!TextUtils.isEmpty(extraName)) {

            final Intent intent = new Intent(this, targetActivity);
            intent.putExtra(extraName, extraValue);

            startActivity(intent);
        } else {
            throw new NullPointerException("传递的值的键名称为null或空");
        }
    }

    public final void startActivity(@NonNull String extraName,int extraValue, @NonNull Class<?> targetActivity) {
        if (!TextUtils.isEmpty(extraName)) {
            final Intent intent = new Intent(this, targetActivity);
            intent.putExtra(extraName, extraValue);
            startActivity(intent);
        } else {
            throw new NullPointerException("传递的值的键名称为null或空");
        }
    }
    public void showTools(@StringRes int text){
        Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), text,
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
    public void showTools(@NonNull String text){
        ToastUtils.showShort(text);
//        Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), text,
//                Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
    /**
     * 添加 Fragment　getSupportFragmentManager　不显示底部
     *
     * @param fragment
     */
    public void addFragment(int var1, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
        fragmentTransaction.add(var1, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void addFragment(@IdRes int fl_container, @NonNull  Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.animator.fragment_slide_left_enter,
                R.animator.tv_fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
        fragmentTransaction.replace(fl_container,fragment);
        fragmentTransaction.commit();
    }
}
