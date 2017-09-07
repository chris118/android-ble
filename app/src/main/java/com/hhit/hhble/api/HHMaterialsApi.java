package com.hhit.hhble.api;

import com.hhit.hhble.bean.HHMaterialBean;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseResultEntity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by chrisw on 2017/9/7.
 */

public class HHMaterialsApi extends BaseApi<List<HHMaterialBean>> {
    public HHMaterialsApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
    }

    @Override
    public Observable<BaseResultEntity<List<HHMaterialBean>>> getObservable(Retrofit retrofit) {
        HHApi service = retrofit.create(HHApi.class);
        return service.getMaterails("all", startPos, count);
    }

    private int startPos;
    private int count;

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
