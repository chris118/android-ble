package com.hhit.hhble.bean;

/**
 * Created by chrisw on 2017/9/5.
 */

public class HHBaseResponse {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
