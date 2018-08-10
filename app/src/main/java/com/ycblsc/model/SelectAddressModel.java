package com.ycblsc.model;

import java.util.List;

/**
 * Created by Administrator on 2018/8/10.
 */

public class SelectAddressModel {

    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"id":4,"s_name":"小易","s_weizhi":"流水西苑小区702室","customId":8,"s_state":1,"bz":"","F_OrderBy":1,"addr_sheng":"010501","addr_shi":"01050101","addr_qu":"0105010102","jingdu":"106.595332240136","weidu":"26.6503287320808","telphone":"15979719752","createUID":"","createTime":"2018/8/9 18:22:17","s_weizhiFull":"贵州省贵阳市观山湖区流水西苑小区702室"},{"id":5,"s_name":"小明子","s_weizhi":"西湖区文二路303号","customId":8,"s_state":1,"bz":"","F_OrderBy":2,"addr_sheng":"010501","addr_shi":"01050102","addr_qu":"0105010201","jingdu":"107.948238594885","weidu":"26.6317423326968","telphone":"15979719752","createUID":"","createTime":"2018/8/9 18:26:39","s_weizhiFull":"贵州省黔东南州凯里市西湖区文二路303号"}]
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
         * id : 4
         * s_name : 小易
         * s_weizhi : 流水西苑小区702室
         * customId : 8
         * s_state : 1
         * bz :
         * F_OrderBy : 1
         * addr_sheng : 010501
         * addr_shi : 01050101
         * addr_qu : 0105010102
         * jingdu : 106.595332240136
         * weidu : 26.6503287320808
         * telphone : 15979719752
         * createUID :
         * createTime : 2018/8/9 18:22:17
         * s_weizhiFull : 贵州省贵阳市观山湖区流水西苑小区702室
         */

        private int id;
        private String s_name;
        private String s_weizhi;
        private int customId;
        private int s_state;
        private String bz;
        private int F_OrderBy;
        private String addr_sheng;
        private String addr_shi;
        private String addr_qu;
        private String jingdu;
        private String weidu;
        private String telphone;
        private String createUID;
        private String createTime;
        private String s_weizhiFull;
        private boolean isState;

        public boolean isState() {
            return isState;
        }

        public void setState(boolean state) {
            isState = state;
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

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getCreateUID() {
            return createUID;
        }

        public void setCreateUID(String createUID) {
            this.createUID = createUID;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getS_weizhiFull() {
            return s_weizhiFull;
        }

        public void setS_weizhiFull(String s_weizhiFull) {
            this.s_weizhiFull = s_weizhiFull;
        }
    }
}
