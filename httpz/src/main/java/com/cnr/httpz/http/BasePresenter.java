package com.cnr.httpz.http;

import com.cnr.httpz.http.MainView;

import java.util.HashMap;

public abstract class BasePresenter {

    protected MainView mainView;
    protected FindItemsInteractor findItemsInteractor;

    public BasePresenter(MainView mainView, FindItemsInteractor findItemsInteractor) {
        this.mainView = mainView;
        this.findItemsInteractor = findItemsInteractor;
    }

    public abstract void request(HashMap<String,String> hashMap);

    public abstract void dispose();

    public void onDestroy() {
        mainView = null;
    }

}
