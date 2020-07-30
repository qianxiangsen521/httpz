package com.cnr.cnrmodule.repository;

import com.cnr.cnrmodule.bean.DemoEntity;
import com.cnr.cnrmodule.bean.LiveProgramListResponse;
import com.cnr.cnrmodule.bean.MianBeanResponse;

import java.util.HashMap;

import io.reactivex.Observable;

public interface HttpDataSource {

    Observable<MianBeanResponse> postMaindDemo(HashMap<String, String> catalog);

    Observable<LiveProgramListResponse> postProgramList(HashMap<String,String> hashMap);

    Observable<DemoEntity> demoGet();
}