package com.cnr.httpz.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.databinding.ObservableList.OnListChangedCallback;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.cnr.httpz.BaseResponse;
import com.cnr.httpz.R;
import com.cnr.httpz.http.MainView;
import com.cnr.httpz.stateview.StateView;
import com.cnr.utils.MSConfig;
import com.cnr.utils.ToastUtils;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;


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

public abstract class BaseModelFragment<V extends ViewDataBinding,VM extends BaseViewModel> extends Fragment
        implements ISupportFragment, SwipeRefreshLayout.OnRefreshListener,
        OnLoadMoreListener,
        View.OnTouchListener ,
        MainView<BaseResponse>
{

    protected final static int START_PAGE_INDEX = MSConfig.COMMON_PAGE_START;
    protected final static int PAGE_NUM_COUNT = MSConfig.COMMON_PAGE_NUM;

    protected final static String START_PAGE = "startPage";
    protected final static String PAGE_NUM = "pageNumber";
    protected BaseActivity mActivity;
    protected boolean isRefresh;
    protected StateView mStateView;

    protected VM viewModel;

    protected V binding;
    private int viewModelId;

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
    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        binding.setVariable(viewModelId, viewModel);
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.setLifecycleOwner(this);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        //viewModel.injectLifecycleProvider(this);
    }
    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
    }
    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 反注册 EventBus
//        EventBus.getDefault().register(this);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewDataBinding();
        registorUIChangeLiveDataCallBack();
        initEventAndData(savedInstanceState);

        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setOnRefreshListener(this);
            getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
            getSwipeRefreshLayout().setProgressViewOffset(true, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics()));

            mStateView = StateView.inject(getSwipeRefreshLayout());
        }

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //防止点击穿透
        binding.getRoot().setOnTouchListener(this);
        if (getSwipeRefreshLayout() != null){
            onRefresh();
        }
    }
    public void showTools(String text){
        Snackbar.make(binding.getRoot(), text,
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

        if (binding != null) {
            binding.unbind();
        }
    }

    @Override
    public void onRefresh() {
        if (getSwipeRefreshLayout() != null) {
            getSwipeRefreshLayout().setRefreshing(true);
        }
        isRefresh = true;
        commonParam_fetchDataIndex = MSConfig.COMMON_PAGE_START;
        requestData();
    }

    @Override
    public void onLoadMore() {
        isRefresh = false;
        commonParam_fetchDataIndex += commonParam_pageNumCount;
        requestData();
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }



    @Override
    public void onNext(BaseResponse baseResponse) {
        showContent();
    }

    private void showContent(){
        if (mStateView != null){
            mStateView.showContent();
        }
    }
    private void showRetry(){
        if (mStateView != null){
            mStateView.showRetry();
            mStateView.setOnRetryClickListener(() -> {
                requestData();
            });
        }
    }
    @Override
    public void onError(Throwable t) {
        showRetry();
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
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    protected void registorUIChangeLiveDataCallBack() {
        viewModel.getUC().getShowErrorEvent().observe(this, new Observer<String>() {

            @Override
            public void onChanged(String s) {
                onError(new Throwable(s));
            }
        });

        //加载对话框显示
        viewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                showContent();
            }
        });
        //加载对话框消失
        viewModel.getUC().getDismissDialogEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
//                dismissDialog();
                onCompleted();
            }
        });
        //跳入新页面
        viewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
            }
        });
        viewModel.getUC().getStartContainerActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                String canonicalName = (String) params.get(BaseViewModel.ParameterField.CANONICAL_NAME);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
//                startActivity(canonicalName, bundle);
            }
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                getActivity().finish();
            }
        });
        //关闭上一层
        viewModel.getUC().getOnBackPressedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                getActivity().onBackPressed();
            }
        });
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

    public class ListChangeCallback<T> extends OnListChangedCallback<ObservableList<T>>{


        @Override
        public void onChanged(ObservableList<T> sender) {

        }

        @Override
        public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
            onChanged(sender);
        }

        @Override
        public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
            onChanged(sender);
        }

        @Override
        public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
            onChanged(sender);
        }

        @Override
        public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
            onChanged(sender);
        }
    }
}