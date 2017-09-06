package com.hhit.hhble.bean;

/**
 * Created by chrisw on 2017/9/5.
 */

import java.util.List;
public class HHFyyjArguBean {

    public static class FyyjArgu{
        private String fyyjId;

        private String carNum;

        private String gpsDeviceId;

        private List<String> fcbqDeviceIds ;

        public void setFyyjId(String fyyjId){
            this.fyyjId = fyyjId;
        }
        public String getFyyjId(){
            return this.fyyjId;
        }
        public void setCarNum(String carNum){
            this.carNum = carNum;
        }
        public String getCarNum(){
            return this.carNum;
        }
        public void setGpsDeviceId(String gpsDeviceId){
            this.gpsDeviceId = gpsDeviceId;
        }
        public String getGpsDeviceId(){
            return this.gpsDeviceId;
        }
        public void setString(List<String> fcbqDeviceIds){
            this.fcbqDeviceIds = fcbqDeviceIds;
        }
        public List<String> getString(){
            return this.fcbqDeviceIds;
        }
    }

    public HHFyyjArguBean.FyyjArgu getFyyjArgu() {
        return FyyjArgu;
    }

    public void setFyyjArgu(HHFyyjArguBean.FyyjArgu fyyjArgu) {
        FyyjArgu = fyyjArgu;
    }

    private FyyjArgu FyyjArgu;



}
