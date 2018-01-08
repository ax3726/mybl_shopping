package com.ycblsc.model.mine;

import java.util.List;

/**
 * Created by Administrator on 2018/1/7.
 * 个人信息
 */

public class PersonInfoModel {

    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"temp_row_number":1,"temp_column":0,"id":4,"name":"二哈","telphone":"13433607807","address":"","tf_money":"684.00","icon":"http://yichao.gzqnkj.com/Photo/1515037466192662.png","gender":"1"}]
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
         * id : 4
         * name : 二哈
         * telphone : 13433607807
         * address :
         * tf_money : 684.00
         * icon : http://yichao.gzqnkj.com/Photo/1515037466192662.png
         * gender : 1
         */

        private int temp_row_number;
        private int temp_column;
        private int id;
        private String name;
        private String telphone;
        private String address;
        private String tf_money;
        private String icon;
        private String gender;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTf_money() {
            return tf_money;
        }

        public void setTf_money(String tf_money) {
            this.tf_money = tf_money;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}
