package com.ycblsc.model.shopping;

import java.util.List;

/**
 * Created by Administrator on 2018/8/6.
 */

public class ShoppingInfoModel {

    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"temp_row_number":1,"temp_column":0,"id":51,"s_name":"贵阳市花果园国际中心3号楼A2915","s_weizhi":"花果园国际中心3号楼A2915","customId":"","s_state":1,"bz":"天鸽公司","img":"","F_OrderBy":80000022,"addr_sheng":"010501","addr_shi":"01050101","addr_qu":"0105010101","jingdu":"106.691994862641","weidu":"26.5681150647411","maxTime":15,"maxArea":999999999,"sendAddress":"花果园A-D区、售楼部","s_weizhiFull":"贵州省贵阳市南明区花果园国际中心3号楼A2915"}]
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
         * id : 51
         * s_name : 贵阳市花果园国际中心3号楼A2915
         * s_weizhi : 花果园国际中心3号楼A2915
         * customId :
         * s_state : 1
         * bz : 天鸽公司
         * img :
         * F_OrderBy : 80000022
         * addr_sheng : 010501
         * addr_shi : 01050101
         * addr_qu : 0105010101
         * jingdu : 106.691994862641
         * weidu : 26.5681150647411
         * maxTime : 15
         * maxArea : 999999999
         * sendAddress : 花果园A-D区、售楼部
         * s_weizhiFull : 贵州省贵阳市南明区花果园国际中心3号楼A2915
         */

        private int temp_row_number;
        private int temp_column;
        private int id;
        private String s_name;
        private String s_weizhi;
        private String customId;
        private int s_state;
        private String bz;
        private String img;
        private int F_OrderBy;
        private String addr_sheng;
        private String addr_shi;
        private String addr_qu;
        private String jingdu;
        private String weidu;
        private int maxTime;
        private String maxArea;
        private String sendAddress;
        private String s_weizhiFull;

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

        public String getCustomId() {
            return customId;
        }

        public void setCustomId(String customId) {
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

        public int getF_OrderBy() {
            return F_OrderBy;
        }

        public void setF_OrderBy(int F_OrderBy) {
            this.F_OrderBy = F_OrderBy;
        }

        public String getAddr_sheng() {
            return addr_sheng;
        }

        public void setAddr_sheng(String addr_sheng) {
            this.addr_sheng = addr_sheng;
        }

        public String getAddr_shi() {
            return addr_shi;
        }

        public void setAddr_shi(String addr_shi) {
            this.addr_shi = addr_shi;
        }

        public String getAddr_qu() {
            return addr_qu;
        }

        public void setAddr_qu(String addr_qu) {
            this.addr_qu = addr_qu;
        }

        public String getJingdu() {
            return jingdu;
        }

        public void setJingdu(String jingdu) {
            this.jingdu = jingdu;
        }

        public String getWeidu() {
            return weidu;
        }

        public void setWeidu(String weidu) {
            this.weidu = weidu;
        }

        public int getMaxTime() {
            return maxTime;
        }

        public void setMaxTime(int maxTime) {
            this.maxTime = maxTime;
        }

        public String getMaxArea() {
            return maxArea;
        }

        public void setMaxArea(String maxArea) {
            this.maxArea = maxArea;
        }

        public String getSendAddress() {
            return sendAddress;
        }

        public void setSendAddress(String sendAddress) {
            this.sendAddress = sendAddress;
        }

        public String getS_weizhiFull() {
            return s_weizhiFull;
        }

        public void setS_weizhiFull(String s_weizhiFull) {
            this.s_weizhiFull = s_weizhiFull;
        }
    }
}
