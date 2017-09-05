package com.hhit.hhble.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chrisw on 2017/9/5.
 */

public class HHDeviceBean implements Parcelable {
    private String name;
    private int state;
    private String desc;

    public HHDeviceBean(String name, int state, String desc) {
        this.name = name;
        this.state = state;
        this.desc = desc;
    }

    protected HHDeviceBean(Parcel in) {
        name = in.readString();
        state = in.readInt();
        desc = in.readString();
    }

    public static final Creator<HHDeviceBean> CREATOR = new Creator<HHDeviceBean>() {
        @Override
        public HHDeviceBean createFromParcel(Parcel in) {
            return new HHDeviceBean(in);
        }

        @Override
        public HHDeviceBean[] newArray(int size) {
            return new HHDeviceBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(state);
        dest.writeString(desc);
    }
}
