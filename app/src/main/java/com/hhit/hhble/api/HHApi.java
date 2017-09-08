package com.hhit.hhble.api;

import com.hhit.hhble.bean.HHFywzBindArgu;
import com.hhit.hhble.bean.HHFyyjTransBean;
import com.hhit.hhble.bean.HHMaterialBean;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseResultEntity;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chrisw on 2017/9/5.
 */

public interface HHApi {
    @POST("fycj/Fyyj")
    Observable<BaseResultEntity> startTransport(@Body RequestBody body);

    @GET("fycj/Fywzs/{status}")
    Observable<BaseResultEntity<List<HHMaterialBean>>> getMaterails(@Path("status") String status,
                                                                    @Query("startPos") int startPos,
                                                                    @Query("count") int count);

    @PUT("fycj/Fywz/Bind")
    Observable<BaseResultEntity> bindMaterial(@Body HHFywzBindArgu body);

    @GET("fycj/Fyyjs")
    Observable<BaseResultEntity<List<HHFyyjTransBean>>> getTransList(@Query("startPos") int startPos,
                                                                 @Query("count") int count);
}
