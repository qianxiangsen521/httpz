package com.cnr.cnrmodule.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.cnr.cnrmodule.R;
import com.cnr.cnrmodule.bean.DemoEntity;
import com.cnr.cnrmodule.bean.LiveProgramListResponse;
import com.cnr.cnrmodule.databinding.ItemLayoutBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseAdapter extends BaseQuickAdapter<DemoEntity.ResultBean.ItemsBean,
        BaseDataBindingHolder<ItemLayoutBinding>>  implements LoadMoreModule {

    public BaseAdapter(@Nullable List<DemoEntity.ResultBean.ItemsBean> data) {
        super(R.layout.item_layout, data);
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemLayoutBinding> itemLayoutBindingBaseDataBindingHolder, DemoEntity.ResultBean.ItemsBean recommendInfoBean) {
        ItemLayoutBinding dataBinding = itemLayoutBindingBaseDataBindingHolder.getDataBinding();
        dataBinding.textView.setText(recommendInfoBean.getName());
        Glide.with(getContext()).load(recommendInfoBean.getImg()).into(dataBinding.imageView);
    }
}
