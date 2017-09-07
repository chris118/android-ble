package com.hhit.hhble.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chrisw on 2017/9/7.
 */

public class HHMaterialBean implements Parcelable {
    private String bz;
    private String ccbh;
    private String clmc;
    private String fywzStatus;
    private String gys;
    private int id;
    private String mybh;
    private int pch;
    private String qydw;
    private int qysl;
    private String wzbh;

    protected HHMaterialBean(Parcel in) {
        bz = in.readString();
        ccbh = in.readString();
        clmc = in.readString();
        fywzStatus = in.readString();
        gys = in.readString();
        id = in.readInt();
        mybh = in.readString();
        pch = in.readInt();
        qydw = in.readString();
        qysl = in.readInt();
        wzbh = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bz);
        dest.writeString(ccbh);
        dest.writeString(clmc);
        dest.writeString(fywzStatus);
        dest.writeString(gys);
        dest.writeInt(id);
        dest.writeString(mybh);
        dest.writeInt(pch);
        dest.writeString(qydw);
        dest.writeInt(qysl);
        dest.writeString(wzbh);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HHMaterialBean> CREATOR = new Creator<HHMaterialBean>() {
        @Override
        public HHMaterialBean createFromParcel(Parcel in) {
            return new HHMaterialBean(in);
        }

        @Override
        public HHMaterialBean[] newArray(int size) {
            return new HHMaterialBean[size];
        }
    };

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getCcbh() {
        return ccbh;
    }

    public void setCcbh(String ccbh) {
        this.ccbh = ccbh;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public String getFywzStatus() {
        return fywzStatus;
    }

    public void setFywzStatus(String fywzStatus) {
        this.fywzStatus = fywzStatus;
    }

    public String getGys() {
        return gys;
    }

    public void setGys(String gys) {
        this.gys = gys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMybh() {
        return mybh;
    }

    public void setMybh(String mybh) {
        this.mybh = mybh;
    }

    public int getPch() {
        return pch;
    }

    public void setPch(int pch) {
        this.pch = pch;
    }

    public String getQydw() {
        return qydw;
    }

    public void setQydw(String qydw) {
        this.qydw = qydw;
    }

    public int getQysl() {
        return qysl;
    }

    public void setQysl(int qysl) {
        this.qysl = qysl;
    }

    public String getWzbh() {
        return wzbh;
    }

    public void setWzbh(String wzbh) {
        this.wzbh = wzbh;
    }
}
