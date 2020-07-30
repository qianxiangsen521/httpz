package com.cnr.cnrmodule.repository;

import com.cnr.cnrmodule.bean.DemoEntity;
import com.cnr.cnrmodule.bean.LiveProgramListResponse;
import com.cnr.cnrmodule.bean.MianBeanResponse;
import com.cnr.cnrmodule.http.DemoApiService;

import java.util.HashMap;

import io.reactivex.Observable;

public class HttpDataSourceImpl implements HttpDataSource {
    private DemoApiService apiService;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    public static HttpDataSourceImpl getInstance(DemoApiService apiService) {
        if (INSTANCE == null) {
            synchronized (HttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HttpDataSourceImpl(DemoApiService apiService) {
        this.apiService = apiService;
    }


    @Override
    public Observable<MianBeanResponse> postMaindDemo(HashMap<String, String> catalog) {
        return apiService.getMianBeanResponse(catalog);
    }

    @Override
    public Observable<LiveProgramListResponse> postProgramList(HashMap<String, String> hashMap) {
        return apiService.getLiveListResponse(hashMap);
    }

    @Override
    public Observable<DemoEntity> demoGet() {
        return apiService.demoGet();
    }
}
