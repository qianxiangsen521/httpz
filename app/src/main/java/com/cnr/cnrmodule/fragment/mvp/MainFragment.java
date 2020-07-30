package com.cnr.cnrmodule.fragment.mvp;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cnr.cnrmodule.R;
import com.cnr.cnrmodule.adapter.BaseAdapter;
import com.cnr.cnrmodule.bean.DemoEntity;
import com.cnr.cnrmodule.bean.LiveProgramListResponse;
import com.cnr.httpz.BaseResponse;
import com.cnr.httpz.base.BaseFragment;
import com.cnr.httpz.http.MainPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainFragment extends BaseFragment<MainPresenter> {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private List<DemoEntity.ResultBean.ItemsBean> listBeans;

    private BaseAdapter baseAdapter;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this,new LivePrgListInteractorImpl());
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_module_fragment;
    }

    @Override
    public void initEventAndData(Bundle savedInstanceState) {
        listBeans = new ArrayList<>();
        recyclerView = mView.findViewById(R.id.recyclerView);
        swipeRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout);
        baseAdapter = new BaseAdapter(listBeans);
        recyclerView.setAdapter(baseAdapter);
        baseAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Override
    public void requestData() {
        if (mvpPresenter != null){
            HashMap<String,String> hashMap = new HashMap<>();
            mvpPresenter.request(hashMap);
        }
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
    public void onNext(BaseResponse baseResponse) {
        if (baseResponse instanceof DemoEntity){
            DemoEntity liveProgramListResponse = (DemoEntity)baseResponse;
            if (isRefresh){
                baseAdapter.setList(liveProgramListResponse.getResult().getItems());
            }else {
                baseAdapter.addData(liveProgramListResponse.getResult().getItems());
            }
            if (baseAdapter.getData().size() < commonParam_fetchDataIndex){
                //如果不够一页,显示没有更多数据布局
                baseAdapter.getLoadMoreModule().loadMoreEnd();
            } else {
                baseAdapter.getLoadMoreModule().loadMoreComplete();
            }
        }
    }
}
