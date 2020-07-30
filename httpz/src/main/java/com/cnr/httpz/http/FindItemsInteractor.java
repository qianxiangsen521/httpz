package com.cnr.httpz.http;

import com.cnr.httpz.BaseResponse;

import java.util.HashMap;

public abstract class FindItemsInteractor{


    public interface OnFinishedListener {
        void onNext(BaseResponse items);

        void onCompleted();

        void onError(Throwable t);

    }

   public abstract void findItems(final OnFinishedListener listener, HashMap<String,String> hashMap);


    public void dispose(){

    }
}