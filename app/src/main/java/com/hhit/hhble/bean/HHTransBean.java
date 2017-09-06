package com.hhit.hhble.bean;

import android.os.Parcelable;

/**
 * Created by chrisw on 2017/9/6.
 */

public class HHTransBean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HHTransBean(String name) {
        this.name = name;
    }
}
