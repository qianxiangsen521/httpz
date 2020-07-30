package com.cnr.cnrmodule.repository;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.cnr.cnrmodule.bean.DemoEntity;
import com.cnr.cnrmodule.bean.LiveProgramListResponse;
import com.cnr.cnrmodule.bean.MianBeanResponse;
import com.cnr.httpz.BaseResponse;
import com.cnr.httpz.base.BaseModel;

import java.util.HashMap;

import io.reactivex.Observable;

public class DemoRepository extends BaseModel implements HttpDataSource, LocalDataSource {
    private volatile static DemoRepository INSTANCE = null;
    private final HttpDataSource mHttpDataSource;

    private final LocalDataSource mLocalDataSource;

    private DemoRepository(@NonNull HttpDataSource httpDataSource,
                           @NonNull LocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static DemoRepository getInstance(HttpDataSource httpDataSource,
                                             LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (DemoRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DemoRepository(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public Observable<MianBeanResponse> postMaindDemo(HashMap<String, String> catalog) {
        return mHttpDataSource.postMaindDemo(catalog);
    }

    @Override
    public Observable<LiveProgramListResponse> postProgramList(HashMap<String, String> hashMap) {
        return mHttpDataSource.postProgramList(hashMap);
    }

    @Override
    public Observable<DemoEntity> demoGet() {
        return mHttpDataSource.demoGet();
    }

    @Override
    public void saveUserName(String userName) {
        mLocalDataSource.saveUserName(userName);
    }

    @Override
    public void savePassword(String password) {
        mLocalDataSource.savePassword(password);
    }

    @Override
    public String getUserName() {
        return mLocalDataSource.getUserName();
    }

    @Override
    public String getPassword() {
        return mLocalDataSource.getPassword();
    }
}