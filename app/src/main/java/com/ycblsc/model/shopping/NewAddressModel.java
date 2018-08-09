package com.ycblsc.model.shopping;

import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 * 新增地址
 */

public class NewAddressModel {

    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"oid":62,"sname":"贵州省","code":"010501","F_OrderBy":10000,"other":""},{"oid":63,"sname":"广东省","code":"010502","F_OrderBy":10000,"other":""}]
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
         * oid : 62
         * sname : 贵州省
         * code : 010501
         * F_OrderBy : 10000
         * other :
         */

        private int oid;
        private String sname;
        private String code;
        private int F_OrderBy;
        private String other;

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getF_OrderBy() {
            return F_OrderBy;
        }

        public void setF_OrderBy(int F_OrderBy) {
            this.F_OrderBy = F_OrderBy;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }
    }
}

