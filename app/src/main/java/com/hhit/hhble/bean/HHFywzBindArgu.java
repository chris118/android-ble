package com.hhit.hhble.bean;

/**
 * Created by chrisw on 2017/9/7.
 */

public class HHFywzBindArgu {
    public static class FywzBindArgu{
        private int fywzId;
        private String bqId;

        public int getFywzId() {
            return fywzId;
        }

        public void setFywzId(int fywzId) {
            this.fywzId = fywzId;
        }

        public String getBqId() {
            return bqId;
        }

        public void setBqId(String bqId) {
            this.bqId = bqId;
        }
    }

    private FywzBindArgu FywzBindArgu;

    public HHFywzBindArgu.FywzBindArgu getFywzBindArgu() {
        return FywzBindArgu;
    }

    public void setFywzBindArgu(HHFywzBindArgu.FywzBindArgu fywzBindArgu) {
        FywzBindArgu = fywzBindArgu;
    }
}
