package com.ycblsc.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/5/23.
 */

public class WEXModel {

    /**
     * data : {"appid":"wxb49050ad3515f101","noncestr":"a78c1705a9e94371b4a26cf0989211a0","package":"Sign=WXPay","partnerid":"1480189592","prepayid":"wx20170523161544052a24a4d20959674795","sign":"B86CE6A0BF32B4587B353499F112A181","timestamp":"1495527305"}
     * status : 200
     * info : 调用成功
     */

    private DataBean data;
    private String status;
    private String info;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static class DataBean {
        /**
         * appid : wxb49050ad3515f101
         * noncestr : a78c1705a9e94371b4a26cf0989211a0
         * package : Sign=WXPay
         * partnerid : 1480189592
         * prepayid : wx20170523161544052a24a4d20959674795
         * sign : B86CE6A0BF32B4587B353499F112A181
         * timestamp : 1495527305
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
