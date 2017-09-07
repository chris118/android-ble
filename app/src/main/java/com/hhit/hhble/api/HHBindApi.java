package com.hhit.hhble.api;

import com.hhit.hhble.bean.HHFywzBindArgu;
import com.hhit.hhble.bean.HHMaterialBean;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by chrisw on 2017/9/7.
 */

public class HHBindApi extends BaseApi{
    public HHBindApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HHApi service = retrofit.create(HHApi.class);

        HHFywzBindArgu argu = new HHFywzBindArgu();
        HHFywzBindArgu.FywzBindArgu fywz = new HHFywzBindArgu.FywzBindArgu();
        fywz.setFywzId(device.getId());
        fywz.setBqId(labelAddress);
        argu.setFywzBindArgu(fywz);

        return service.bindMaterial(argu);
    }

    private String labelAddress;

    private HHMaterialBean device;

    public String getLabelAddress() {
        return labelAddress;
    }

    public void setLabelAddress(String labelAddress) {
        this.labelAddress = labelAddress;
    }

    public HHMaterialBean getDevice() {
        return device;
    }

    public void setDevice(HHMaterialBean device) {
        this.device = device;
    }
}
