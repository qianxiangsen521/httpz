package com.cnr.utils;


import com.cnr.httpz.ExceptionHandle;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by goldze on 2017/6/19.
 * 有关Rx的工具类
 */
public class RxUtils {

    /**
     * 线程调度器
     */
    public static ObservableTransformer schedulersTransformer() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static ObservableTransformer exceptionTransformer() {

        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable observable) {
                return observable
//                        .map(new HandleFuc<T>())  //这里可以取出BaseResponse中的Result
                        .onErrorResumeNext(new HttpResponseFunc());
            }
        };
    }

    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable t) {
            return Observable.error(ExceptionHandle.handleException(t));
        }
    }


}
