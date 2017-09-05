package com.hhit.hhble.bean;

/**
 * Created by chrisw on 2017/9/5.
 */

public class HHDeviceBean {
    private String name;
    private int state;
    private String desc;

    public HHDeviceBean(String name, int state, String desc) {
        this.name = name;
        this.state = state;
        this.desc = desc;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
