package com.cnr.cnrmodule;

import android.os.Bundle;
import android.view.View;

import com.cnr.cnrmodule.fragment.mvvm.MainModuleFragment;
import com.cnr.cnrmodule.fragment.mvp.MainFragment;
import com.cnr.httpz.base.BaseActivity;
import com.cnr.httpz.http.MainPresenter;

public class MainActivity extends BaseActivity<MainPresenter> {


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected MainPresenter createPresenter() {
        return null;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(R.id.fl_content, MainFragment.newInstance());

            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addFragment(R.id.fl_content, MainModuleFragment.newInstance());
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

}
