package com.cnr.cnrmodule.http;

import com.cnr.cnrmodule.bean.DemoEntity;
import com.cnr.cnrmodule.bean.LiveProgramListResponse;
import com.cnr.cnrmodule.bean.MianBeanResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DemoApiService {

    @Headers({"urlname:qh"})
    @POST("/hdtvapi/main")
    @FormUrlEncoded
    Observable<MianBeanResponse> getMianBeanResponse(@FieldMap Map<String, String> params);

    @Headers({"urlname:qh"})
    @POST("/mobileTv/getLiveChannel")
    @FormUrlEncoded
    Observable<LiveProgramListResponse> getLiveListResponse(@FieldMap Map<String, String> params);

    @Headers({"urlname:qh"})
    @GET("action/apiv2/banner?catalog=1")
    Observable<DemoEntity> demoGet();

}
