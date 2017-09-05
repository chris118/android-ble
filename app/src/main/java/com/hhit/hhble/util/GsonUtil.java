package com.hhit.hhble.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by chrisw on 2017/9/5.
 */

public class GsonUtil {

    private static Gson INSTANCE;

    public static Gson getInstance(){
        if(INSTANCE == null){
            synchronized (GsonUtil.class){
                if(INSTANCE == null) {
                    INSTANCE = new GsonBuilder()
                            .setLenient()// json宽松
                            .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                            .serializeNulls() //智能null
                            .setPrettyPrinting()// 调教格式
                            .disableHtmlEscaping() //默认是GSON把HTML 转义的
                            .create();
                    return INSTANCE;
                }
            }
        }
        return INSTANCE;
    }
}
