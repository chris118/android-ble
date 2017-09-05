package com.hhit.hhble;

import android.app.Application;

import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;

/**
 * Created by chrisw on 2017/9/5.
 */

public class HHApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RxRetrofitApp.init(this);
    }
}
