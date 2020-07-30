package com.cnr.cnrmodule.fragment.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.cnr.cnrmodule.bean.DemoEntity;
import com.cnr.cnrmodule.repository.DemoRepository;
import com.cnr.httpz.ResponseThrowable;
import com.cnr.httpz.base.BaseViewModel;
import com.cnr.utils.RxUtils;
import com.cnr.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

public class MainModuleViewModel extends BaseViewModel<DemoRepository> {

    public MutableLiveData<List<DemoEntity.ResultBean.ItemsBean>> liveData = new MutableLiveData<>();

    public MainModuleViewModel(@NonNull Application application, DemoRepository model) {
        super(application, model);
    }
    /**
     * 网络请求方法，在ViewModel中调用Model层，通过Okhttp+Retrofit+RxJava发起请求
     */
    public void requestNetWork() {
        //可以调用addSubscribe()添加Disposable，请求与View周期同步
        model.demoGet()
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new DisposableObserver<DemoEntity>() {
                    @Override
                    public void onNext(DemoEntity response) {
                        //请求成功
                        if (response.getCode() == 1) {
                            liveData.setValue(response.getResult().getItems());
                        } else {
                            //code错误时也可以定义Observable回调到View层去处理
                            ToastUtils.showShort("数据错误");
                        }
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        dismissDialog();
                        onErrorDialog(throwable.getMessage());
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                    }
                });
    }
}
