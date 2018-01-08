package com.ycblsc.model.mine;

import java.util.List;

/**
 * Created by Administrator on 2018/1/7.
 * 充值
 */

public class MineRechargeModel {

    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"temp_row_number":1,"temp_column":0,"id":1,"tf_Money":"60.00","zengsong":"11.00","status":"1","CreateTime":"","CreateUID":""},{"temp_row_number":2,"temp_column":0,"id":2,"tf_Money":"100.00","zengsong":"28.00","status":"1","CreateTime":"","CreateUID":""},{"temp_row_number":3,"temp_column":0,"id":3,"tf_Money":"200.00","zengsong":"65.00","status":"1","CreateTime":"","CreateUID":""},{"temp_row_number":4,"temp_column":0,"id":4,"tf_Money":"500.00","zengsong":"156.00","status":"1","CreateTime":"","CreateUID":""}]
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
         * temp_row_number : 1
         * temp_column : 0
         * id : 1
         * tf_Money : 60.00
         * zengsong : 11.00
         * status : 1
         * CreateTime :
         * CreateUID :
         */

        private int temp_row_number;
        private int temp_column;
        private int id;
        private String tf_Money;
        private String zengsong;
        private String status;
        private String CreateTime;
        private String CreateUID;
        private boolean isState;

        public boolean isState() {
            return isState;
        }

        public void setState(boolean state) {
            isState = state;
        }

        public int getTemp_row_number() {
            return temp_row_number;
        }

        public void setTemp_row_number(int temp_row_number) {
            this.temp_row_number = temp_row_number;
        }

        public int getTemp_column() {
            return temp_column;
        }

        public void setTemp_column(int temp_column) {
            this.temp_column = temp_column;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTf_Money() {
            return tf_Money;
        }

        public void setTf_Money(String tf_Money) {
            this.tf_Money = tf_Money;
        }

        public String getZengsong() {
            return zengsong;
        }

        public void setZengsong(String zengsong) {
            this.zengsong = zengsong;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getCreateUID() {
            return CreateUID;
        }

        public void setCreateUID(String CreateUID) {
            this.CreateUID = CreateUID;
        }
    }
}
