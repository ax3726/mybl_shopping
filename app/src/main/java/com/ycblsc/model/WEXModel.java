package com.ycblsc.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */

public class WEXModel  implements Serializable {


    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"appid":"wxfb354855bec653ed","partnerid":"1500562681","prepayid":"wx2018032315150242cca37f1a0769067530","package":"Sign=WXPay","noncestr":"A01A0380CA3C61428C26A231F0E49A09","timestamp":"1521789303","sign":"B539D5DBE815FEDCEA620281D54311CD"}]
     */

    private String ReturnCode;
    private String ReturnMessage;
    private List<ReturnDataBean> ReturnData;

    public String getReturnCode() {
        return ReturnCode;
    }

    public void setReturnCode(String ReturnCode) {
        this.ReturnCode = ReturnCode;
    }

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public void setReturnMessage(String ReturnMessage) {
        this.ReturnMessage = ReturnMessage;
    }

    public List<ReturnDataBean> getReturnData() {
        return ReturnData;
    }

    public void setReturnData(List<ReturnDataBean> ReturnData) {
        this.ReturnData = ReturnData;
    }

    public static class ReturnDataBean {
        /**
         * appid : wxfb354855bec653ed
         * partnerid : 1500562681
         * prepayid : wx2018032315150242cca37f1a0769067530
         * package : Sign=WXPay
         * noncestr : A01A0380CA3C61428C26A231F0E49A09
         * timestamp : 1521789303
         * sign : B539D5DBE815FEDCEA620281D54311CD
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
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

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
