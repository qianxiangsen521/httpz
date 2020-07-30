package com.cnr.cnrmodule;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.cnr.cnrmodule.fragment.mvvm.MainModuleFragment;
import com.cnr.cnrmodule.fragment.mvp.MainFragment;
import com.cnr.cnrmodule.widget.MainTabButton;
import com.cnr.httpz.base.BaseActivity;
import com.cnr.httpz.http.MainPresenter;

public class MainActivity extends BaseActivity<MainPresenter> {

    //当前tab位置
    public static int mFragCurrentIndex = 0;
    MainTabButton tab1zhuanqu;
    MainTabButton tab2zhibo;
    ImageView tab3yaokong;
    MainTabButton tab4yingshi;
    MainTabButton tab5mine;

    MainFragment prefectureFragment;
    MainModuleFragment liveFragment;
    MainModuleFragment remoteFragment;

    MainModuleFragment mediaFragment;
    MainModuleFragment mineFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        tab1zhuanqu = findViewById(R.id.tab1zhuanqu);
        tab2zhibo = findViewById(R.id.tab2zhibo);
        tab3yaokong = findViewById(R.id.tab3yaokong);
        tab4yingshi = findViewById(R.id.tab4yingshi);
        tab5mine = findViewById(R.id.tab5mine);

        tab1zhuanqu.setOnClickListener(this);
        tab2zhibo.setOnClickListener(this);
        tab3yaokong.setOnClickListener(this);
        tab4yingshi.setOnClickListener(this);
        tab5mine.setOnClickListener(this);

        switchFragment(mFragCurrentIndex, true);
    }

    /**
     * fragment切换
     *
     * @param index   索引值
     * @param isFirst 是否第一次
     */
    private void switchFragment(int index, boolean isFirst) {
        setSelected(index, true);
        if (mFragCurrentIndex == index && !isFirst) {
            return;
        }
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        Fragment fragmentNow = fragmentManager.findFragmentByTag("home" + mFragCurrentIndex);
        if (fragmentNow != null) {

            fragmentManager.beginTransaction().hide(fragmentNow).commit();
        }
        Fragment fragment = fragmentManager.findFragmentByTag("home" + index);
        if (fragment == null) {
            fragment = createMainFragment(index);
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment, "home" +
                    index)
                    .commit();
        } else {

            fragmentManager.beginTransaction().show(fragment).commit();
        }
        mFragCurrentIndex = index;
    }
    // 把显示的Fragment隐藏
    private void setSelected(int pos, boolean isSelected) {
        if (pos == 0) {
            tab1zhuanqu.setHasSelected(isSelected);
        } else if (pos == 1) {
            tab2zhibo.setHasSelected(isSelected);
        } /*else if (pos == 2) {
            tab3yaokong.setHasSelected(isSelected);
        } */else if (pos == 3) {
            tab4yingshi.setHasSelected(isSelected);
        } else if(pos == 4){
            tab5mine.setHasSelected(isSelected);
        }
    }

    private Fragment createMainFragment(int index) {
        Fragment fragment = null;
        switch (index) {
            case 0:
                if (prefectureFragment == null) {
                    prefectureFragment = MainFragment.newInstance();
                }
                fragment = prefectureFragment;

                break;
            case 1:
                if (liveFragment == null) {
                    liveFragment = MainModuleFragment.newInstance();
                }
                fragment = liveFragment;
                break;
            case 2:
                if (remoteFragment == null) {
                    remoteFragment = MainModuleFragment.newInstance();
                }
                fragment = remoteFragment;
                break;
            case 3:
                if (mediaFragment == null) {
                    mediaFragment = MainModuleFragment.newInstance();
                }
                fragment = mediaFragment;
                break;
            case 4:
                if (mineFragment == null) {
                    mineFragment = MainModuleFragment.newInstance();
                }
                fragment = mineFragment;
                break;
            default:
                break;
        }
        return fragment;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mFragCurrentIndex);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected MainPresenter createPresenter() {
        return null;
    }



    @Override
    public void onClick(View v) {
        setSelected(mFragCurrentIndex, false);
        switch (v.getId()) {
            case R.id.tab1zhuanqu:
                switchFragment(0, false);
                break;
            case R.id.tab2zhibo:
                switchFragment(1, false);
                break;
            case R.id.tab3yaokong:
                switchFragment(2, false);
                break;
            case R.id.tab4yingshi:
                switchFragment(3, false);
                break;
            case R.id.tab5mine:
                switchFragment(4, false);
                break;
        }
    }

}
