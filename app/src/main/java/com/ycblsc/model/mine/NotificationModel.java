package com.ycblsc.model.mine;

import java.util.List;

/**
 * Created by Administrator on 2018/1/7.
 */

public class NotificationModel {

    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"temp_row_number":1,"temp_column":0,"tfId":9,"tf_ncrUserType":"","CreateTime":"2018/1/4 23:12:11","tf_nvcContent":"5569"},{"temp_row_number":2,"temp_column":0,"tfId":6,"tf_ncrUserType":"","CreateTime":"2018/1/2 14:44:25","tf_nvcContent":"111222"},{"temp_row_number":3,"temp_column":0,"tfId":4,"tf_ncrUserType":"","CreateTime":"2018/1/2 14:43:13","tf_nvcContent":"555公告"}]
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
         * tfId : 9
         * tf_ncrUserType :
         * CreateTime : 2018/1/4 23:12:11
         * tf_nvcContent : 5569
         */

        private int temp_row_number;
        private int temp_column;
        private int tfId;
        private String tf_ncrUserType;
        private String CreateTime;
        private String tf_nvcContent;

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

        public int getTfId() {
            return tfId;
        }

        public void setTfId(int tfId) {
            this.tfId = tfId;
        }

        public String getTf_ncrUserType() {
            return tf_ncrUserType;
        }

        public void setTf_ncrUserType(String tf_ncrUserType) {
            this.tf_ncrUserType = tf_ncrUserType;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getTf_nvcContent() {
            return tf_nvcContent;
        }

        public void setTf_nvcContent(String tf_nvcContent) {
            this.tf_nvcContent = tf_nvcContent;
        }
    }
}
