package com.ycblsc.model.home;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class ProuductTypeModel {

    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"temp_row_number":1,"temp_column":0,"F_NAME":"全部","F_CODE":"01","f_img":"http://yichao.gzqnkj.com/images/noImg.jpg"},{"temp_row_number":2,"temp_column":0,"F_NAME":"化妆品","F_CODE":"0103","f_img":"http://yichao.gzqnkj.com/images/noImg.jpg"},{"temp_row_number":3,"temp_column":0,"F_NAME":"食品","F_CODE":"0101","f_img":"http://yichao.gzqnkj.com/images/noImg.jpg"},{"temp_row_number":4,"temp_column":0,"F_NAME":"日用品","F_CODE":"0102","f_img":"http://yichao.gzqnkj.com/images/noImg.jpg"}]
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
         * F_NAME : 全部
         * F_CODE : 01
         * f_img : http://yichao.gzqnkj.com/images/noImg.jpg
         */

        private int temp_row_number;
        private int temp_column;
        private String F_NAME;
        private String F_CODE;
        private String f_img;


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

        public String getF_NAME() {
            return F_NAME;
        }

        public void setF_NAME(String F_NAME) {
            this.F_NAME = F_NAME;
        }

        public String getF_CODE() {
            return F_CODE;
        }

        public void setF_CODE(String F_CODE) {
            this.F_CODE = F_CODE;
        }

        public String getF_img() {
            return f_img;
        }

        public void setF_img(String f_img) {
            this.f_img = f_img;
        }
    }
}
