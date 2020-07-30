package com.cnr.cnrmodule.fragment.mvp;

import com.cnr.cnrmodule.bean.DemoEntity;
import com.cnr.cnrmodule.bean.LiveProgramListResponse;
import com.cnr.cnrmodule.http.DemoApiService;
import com.cnr.cnrmodule.http.RetrofitClient;
import com.cnr.httpz.ApiDisposableObserver;
import com.cnr.httpz.BaseResponse;
import com.cnr.httpz.http.FindItemsInteractor;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LivePrgListInteractorImpl extends FindItemsInteractor {

    @Override
    public void findItems(OnFinishedListener listener, HashMap<String,String> hashMap) {
        Observable<DemoEntity> liveProgramListResponse = RetrofitClient.getInstance().create(DemoApiService.class).
                demoGet();
        RetrofitClient.execute(liveProgramListResponse, new Observer<BaseResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse baseResponse) {
                listener.onNext(baseResponse);
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onComplete() {
                listener.onCompleted();
            }
        });
    }
}