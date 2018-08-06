package com.ycblsc.model.shopping;

import java.util.List;

/**
 * Created by Administrator on 2018/8/6 0006.
 */

public class TimeAddressModel {


    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"id":1,"F_Key":"serviceNote","F_Value":"亲，当您附近区域有...","remark":"限时达商城说明"}]
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
         * id : 1
         * F_Key : serviceNote
         * F_Value : 亲，当您附近区域有...
         * remark : 限时达商城说明
         */

        private int id;
        private String F_Key;
        private String F_Value;
        private String remark;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getF_Key() {
            return F_Key;
        }

        public void setF_Key(String F_Key) {
            this.F_Key = F_Key;
        }

        public String getF_Value() {
            return F_Value;
        }

        public void setF_Value(String F_Value) {
            this.F_Value = F_Value;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
