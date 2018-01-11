package com.ycblsc.model.mine;

import java.util.List;

/**
 * Created by Administrator on 2018/1/11.
 * 消费记录
 */

public class ConsumptionModel {

    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"temp_row_number":1,"temp_column":0,"F_CreateDate":"2018/1/8 13:45:21","proPrice":"15.00","proCount":1,"proSum":"15.00","s_name":"","img":"http://yichao.gzqnkj.com/images/noImg.jpg","s_weizhi":"远大生态风景123号"},{"temp_row_number":2,"temp_column":0,"F_CreateDate":"2018/1/8 13:45:21","proPrice":"11.00","proCount":1,"proSum":"11.00","s_name":"","img":"http://yichao.gzqnkj.com/images/noImg.jpg","s_weizhi":"远大生态风景123号"},{"temp_row_number":3,"temp_column":0,"F_CreateDate":"2018/1/8 13:38:48","proPrice":"15.00","proCount":1,"proSum":"15.00","s_name":"","img":"http://yichao.gzqnkj.com/images/noImg.jpg","s_weizhi":"远大生态风景123号"},{"temp_row_number":4,"temp_column":0,"F_CreateDate":"2018/1/8 13:38:48","proPrice":"11.00","proCount":1,"proSum":"11.00","s_name":"","img":"http://yichao.gzqnkj.com/images/noImg.jpg","s_weizhi":"远大生态风景123号"},{"temp_row_number":5,"temp_column":0,"F_CreateDate":"2018/1/8 12:11:11","proPrice":"15.00","proCount":1,"proSum":"15.00","s_name":"","img":"http://yichao.gzqnkj.com/images/noImg.jpg","s_weizhi":"远大生态风景123号"}]
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
         * F_CreateDate : 2018/1/8 13:45:21
         * proPrice : 15.00
         * proCount : 1
         * proSum : 15.00
         * s_name :
         * img : http://yichao.gzqnkj.com/images/noImg.jpg
         * s_weizhi : 远大生态风景123号
         */

        private int temp_row_number;
        private int temp_column;
        private String F_CreateDate;
        private String proPrice;
        private int proCount;
        private String proSum;
        private String s_name;
        private String img;
        private String s_weizhi;

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

        public String getF_CreateDate() {
            return F_CreateDate;
        }

        public void setF_CreateDate(String F_CreateDate) {
            this.F_CreateDate = F_CreateDate;
        }

        public String getProPrice() {
            return proPrice;
        }

        public void setProPrice(String proPrice) {
            this.proPrice = proPrice;
        }

        public int getProCount() {
            return proCount;
        }

        public void setProCount(int proCount) {
            this.proCount = proCount;
        }

        public String getProSum() {
            return proSum;
        }

        public void setProSum(String proSum) {
            this.proSum = proSum;
        }

        public String getS_name() {
            return s_name;
        }

        public void setS_name(String s_name) {
            this.s_name = s_name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getS_weizhi() {
            return s_weizhi;
        }

        public void setS_weizhi(String s_weizhi) {
            this.s_weizhi = s_weizhi;
        }
    }
}
