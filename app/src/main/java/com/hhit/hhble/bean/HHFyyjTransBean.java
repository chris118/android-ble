package com.hhit.hhble.bean;

import java.util.List;

/**
 * Created by chrisw on 2017/9/8.
 */

public class HHFyyjTransBean {
    private String type;

    private String dateBegin;

    private String dateCreated;

    private String dateEnd;

    private String fyyjStatus;

    private int id;

    private List<HHMaterialBean> fywzs;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getFyyjStatus() {
        return fyyjStatus;
    }

    public void setFyyjStatus(String fyyjStatus) {
        this.fyyjStatus = fyyjStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<HHMaterialBean> getFywzs() {
        return fywzs;
    }

    public void setFywzs(List<HHMaterialBean> fywzs) {
        this.fywzs = fywzs;
    }
}
