package com.hhit.hhble.api;

import com.hhit.hhble.bean.HHFyyjArgu;
import com.hhit.hhble.util.GsonUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by chrisw on 2017/9/5.
 */

public class HHStartTransportApi extends BaseApi {

    public HHFyyjArgu getArgu() {
        return argu;
    }

    public void setArgu(HHFyyjArgu argu) {
        this.argu = argu;
    }

    private HHFyyjArgu argu;

    public HHStartTransportApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
//        setShowProgress(true);
//        setCancel(true);

//        setCache(true);
//        setMethod("fycj/Fyyj");
//        setCookieNetWorkTime(60);
//        setCookieNoNetWorkTime(24*60*60);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HHApi service = retrofit.create(HHApi.class);
        String json = GsonUtil.getInstance().toJson(argu);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        return service.startTransport(body);
    }
}
