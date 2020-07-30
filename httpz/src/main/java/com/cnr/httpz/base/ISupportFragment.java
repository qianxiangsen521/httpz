package com.cnr.httpz.base;


import android.os.Bundle;

import androidx.annotation.LayoutRes;


public interface ISupportFragment {

    @LayoutRes
    int getLayoutId();

    void initEventAndData(Bundle savedInstanceState);

    void requestData();



}
