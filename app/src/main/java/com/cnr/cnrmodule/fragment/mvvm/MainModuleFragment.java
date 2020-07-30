package com.cnr.cnrmodule.fragment.mvvm;


import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cnr.cnrmodule.R;
import com.cnr.cnrmodule.adapter.BaseAdapter;
import com.cnr.cnrmodule.databinding.MainModuleFragmentBinding;
import com.cnr.cnrmodule.repository.AppViewModelFactory;
import com.cnr.httpz.base.BaseModelFragment;

public class MainModuleFragment extends BaseModelFragment<MainModuleFragmentBinding,MainModuleViewModel> {

    public static MainModuleFragment newInstance() {
        return new MainModuleFragment();
    }

    private BaseAdapter baseAdapter;

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return binding.swipeRefreshLayout;
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_module_fragment;
    }

    @Override
    public void initEventAndData(Bundle savedInstanceState) {
        baseAdapter = new BaseAdapter(viewModel.liveData.getValue());
        binding.recyclerView.setAdapter(baseAdapter);
        baseAdapter.getLoadMoreModule().setOnLoadMoreListener(this);

        viewModel.liveData.observe(this, recommendInfoBeans -> {
            if (isRefresh){
                baseAdapter.setList(recommendInfoBeans);
            }else {
                baseAdapter.addData(recommendInfoBeans);
            }
            if (baseAdapter.getData().size() < commonParam_fetchDataIndex){
                //如果不够一页,显示没有更多数据布局
                baseAdapter.getLoadMoreModule().loadMoreEnd();
            } else {
                baseAdapter.getLoadMoreModule().loadMoreComplete();
            }
        });
    }

    @Override
    public void onRefresh() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        baseAdapter.getLoadMoreModule().setEnableLoadMore(false);
        super.onRefresh();
    }

    @Override
    public void onLoadMore() {
        // 打开或关闭加载更多功能（默认为true）
        baseAdapter.getLoadMoreModule().setEnableLoadMore(true);

        // 是否自定加载下一页（默认为true）
        baseAdapter.getLoadMoreModule().setAutoLoadMore(true);

        // 当数据不满一页时，是否继续自动加载（默认为true）
        baseAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);

        // 所有数据加载完成后，是否允许点击（默认为false）
        //baseAdapter.getLoadMoreModule().setEnableLoadMoreEndClick(false);

        // 是否处于加载中
        //baseAdapter.getLoadMoreModule().isLoading();

        // 预加载的位置（默认为1）
        //baseAdapter.getLoadMoreModule().setPreLoadNumber(1);

        super.onLoadMore();
    }

    @Override
    public void requestData() {
        viewModel.requestNetWork();
    }

    @Override
    public MainModuleViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return  ViewModelProviders.of(this, factory).get(MainModuleViewModel.class);
    }
}
