package com.hhit.hhble.api;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseResultEntity;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by chrisw on 2017/9/5.
 */

public interface HHApi {
    @POST("fycj/Fyyj")
    Observable<BaseResultEntity<Void>> startTransport(@Body RequestBody body);
}
