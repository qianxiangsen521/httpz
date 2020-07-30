/*
 *
 *  * Copyright (C) 2018 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.cnr.httpz.http;

import com.cnr.httpz.BaseResponse;

import java.util.HashMap;


public class MainPresenter extends BasePresenter implements FindItemsInteractor.OnFinishedListener{


    public MainPresenter(MainView mainView, FindItemsInteractor findItemsInteractor) {
        super(mainView,findItemsInteractor);
    }
    /**
     *
     * @param hashMap
     */
    @Override
    public void request(HashMap<String,String> hashMap) {
        findItemsInteractor.findItems(this,hashMap);
    }

    @Override
    public void dispose() {
        findItemsInteractor.dispose();
    }

    @Override
    public void onNext(BaseResponse items) {
        if (mainView != null) {
            mainView.onNext(items);
        }
    }
    @Override
    public void onCompleted() {
        if (mainView != null) {
            mainView.onCompleted();
        }
    }

    @Override
    public void onError(Throwable t) {
        if (mainView != null) {
            mainView.onError(t);
        }
    }

}
