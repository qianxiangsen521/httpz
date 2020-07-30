package com.cnr.httpz;


import com.cnr.utils.AppManager;
import com.cnr.utils.KLog;
import com.cnr.utils.ToastUtils;
import com.cnr.utils.Utils;

import io.reactivex.observers.DisposableObserver;

public abstract class ApiDisposableObserver<T> extends DisposableObserver<T> {
    public abstract void onResult(T t);


    public abstract void onCompleted();


    @Override
    public void onComplete() {
        onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        KLog.e(e.getMessage());
        if (e instanceof ResponseThrowable) {
            ResponseThrowable rError =  (ResponseThrowable) e;
            ToastUtils.showShort(rError.getMessage());
            return;
        }
        //其他全部甩锅网络异常
        ToastUtils.showShort("网络异常"+e.getMessage());

    }

    @Override
    public void onStart() {
        super.onStart();
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable(Utils.getContext())) {
            ToastUtils.showShort("无网络，读取缓存数据");
            onComplete();
        }
    }

    @Override
    public void onNext(T o) {
        BaseResponse baseResponse = (BaseResponse) o;
        switch (baseResponse.status.getCode()) {
            case CodeRule.CODE_10000:
                //请求成功, 正确的操作方式
                onResult((T) baseResponse);
                break;
            case CodeRule.CODE_220:
                // 请求成功, 正确的操作方式, 并消息提示
                onResult((T) baseResponse);
                break;
            case CodeRule.CODE_300:
                //请求失败，不打印Message
                KLog.e("请求失败");
                ToastUtils.showShort("错误代码:", baseResponse.status.getCode());
                break;
            case CodeRule.CODE_330:
                //请求失败，打印Message
                ToastUtils.showShort(baseResponse.status.getMsgToShow());
                break;
            case CodeRule.CODE_500:
                //服务器内部异常
                ToastUtils.showShort("错误代码:", baseResponse.status.getCode());
                break;
            case CodeRule.CODE_10001:
                //参数为空
                KLog.e("参数为空");
                ToastUtils.showShort("参数为空:", baseResponse.status.getCode());
                break;
            case CodeRule.CODE_20001:
                //没有数据
                ToastUtils.showShort("没有数据:", baseResponse.status.getCode());
                onError(new NullPointerException("没有数据"));
                KLog.e("没有数据");
                break;
            case CodeRule.CODE_510:
                //无效的Token，提示跳入登录页
                ToastUtils.showShort("token已过期，请重新登录");
                //关闭所有页面
                AppManager.getAppManager().finishAllActivity();
                //跳入登录界面
                //*****该类仅供参考，实际业务Code, 根据需求来定义，******//
                break;
            case CodeRule.CODE_530:
                ToastUtils.showShort("请先登录");
                break;
            case CodeRule.CODE_551:
                ToastUtils.showShort("错误代码:", baseResponse.status.getCode());
                break;
            case CodeRule.CODE_30001:
                ToastUtils.showShort(baseResponse.status.getMsgToShow());
                break;
            default:
                ToastUtils.showShort("错误代码:", baseResponse.getStatus().getCode());
                break;
        }
    }

    public static final class CodeRule {
        //请求成功, 正确的操作方式
        static final int CODE_10000 = 200;
        //请求成功, 消息提示
        static final int CODE_220 = 220;
        //请求失败，不打印Message
        static final int CODE_300 = 300;
        //请求失败，打印Message
        static final int CODE_330 = 330;
        //服务器内部异常
        static final int CODE_500 = 500;
        //所有参数问题
        static final int CODE_10001 = 10001;

        //没有数据
        static final int CODE_20001 = 20001;

        //无效的Token
        static final int CODE_510 = 510;
        //未登录
        static final int CODE_530 = 530;
        //请求的操作异常终止：未知的页面类型
        static final int CODE_551 = 551;
        //直接显示服务器返回的信息，如用户不存在，用户已注册等
        static final int CODE_30001 = 30001;
    }
}
