package com.ycblsc.model.home;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class ShopInfoModel {

    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"temp_row_number":1,"temp_column":0,"id":18,"s_name":"远大1号","s_weizhi":"远大生态风景123号","customId":251,"s_state":1,"bz":"","img":"/Code2/18.jpg","USER_NAME":"buhuoyuan"}]
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
         * id : 18
         * s_name : 远大1号
         * s_weizhi : 远大生态风景123号
         * customId : 251
         * s_state : 1
         * bz :
         * img : /Code2/18.jpg
         * USER_NAME : buhuoyuan
         */

        private int temp_row_number;
        private int temp_column;
        private int id;
        private String s_name;
        private String s_weizhi;
        private int customId;
        private int s_state;
        private String bz;
        private String img;
        private String USER_NAME;

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

        public String getS_name() {
            return s_name;
        }

        public void setS_name(String s_name) {
            this.s_name = s_name;
        }

        public String getS_weizhi() {
            return s_weizhi;
        }

        public void setS_weizhi(String s_weizhi) {
            this.s_weizhi = s_weizhi;
        }

        public int getCustomId() {
            return customId;
        }

        public void setCustomId(int customId) {
            this.customId = customId;
        }

        public int getS_state() {
            return s_state;
        }

        public void setS_state(int s_state) {
            this.s_state = s_state;
        }

        public String getBz() {
            return bz;
        }

        public void setBz(String bz) {
            this.bz = bz;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUSER_NAME() {
            return USER_NAME;
        }

        public void setUSER_NAME(String USER_NAME) {
            this.USER_NAME = USER_NAME;
        }
    }
}
