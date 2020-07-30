package com.cnr.cnrmodule;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.cnr.cnrmodule.util.StringTool;
import com.cnr.httpz.BuildConfig;
import com.cnr.utils.KLog;
import com.cnr.utils.Utils;


public class BaseApplication extends Application {

    private static BaseApplication mInstance;
    public static int width;
    public static int height;

    public static BaseApplication getInstance() {
        return mInstance;
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        width = StringTool.getScreenWidth();
        height = StringTool.getScreenHeight();
        Utils.init(this);
        //是否开启打印日志
        KLog.init(BuildConfig.DEBUG);
    }
}