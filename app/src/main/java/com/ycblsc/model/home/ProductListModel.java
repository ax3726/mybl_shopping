package com.ycblsc.model.home;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class ProductListModel {

    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"temp_row_number":1,"temp_column":0,"id":136,"s_name":18,"s_product":11,"s_mangui":100,"s_kucunsj":15,"STATE":1,"s_kucun":11,"isTuiJian":"1","s_names":"远大1号","s_products":"德亚全脂牛奶","s_buque":89,"img":"http://yichao.gzqnkj.com/UploadFiles/2017/12/30/1514635321711830.jpg","s_price":"75.00","jianjie":"不是进口的"},{"temp_row_number":2,"temp_column":0,"id":149,"s_name":18,"s_product":7,"s_mangui":22,"s_kucunsj":22,"STATE":1,"s_kucun":22,"isTuiJian":"","s_names":"远大1号","s_products":"蓝莓","s_buque":0,"img":"http://yichao.gzqnkj.com/UploadFiles/2017/12/22/1513938503596220.png","s_price":"5.00","jianjie":"商品简介信息"},{"temp_row_number":3,"temp_column":0,"id":150,"s_name":18,"s_product":6,"s_mangui":33,"s_kucunsj":33,"STATE":1,"s_kucun":33,"isTuiJian":"1","s_names":"远大1号","s_products":"坚果","s_buque":0,"img":"http://yichao.gzqnkj.com/UploadFiles/2017/12/22/1513938530893170.png","s_price":"20.00","jianjie":""},{"temp_row_number":4,"temp_column":0,"id":151,"s_name":18,"s_product":3,"s_mangui":44,"s_kucunsj":44,"STATE":1,"s_kucun":44,"isTuiJian":"1","s_names":"远大1号","s_products":"薯条","s_buque":0,"img":"http://yichao.gzqnkj.com/UploadFiles/2017/12/22/1513938629411600.png","s_price":"8.30","jianjie":""},{"temp_row_number":5,"temp_column":0,"id":152,"s_name":18,"s_product":14,"s_mangui":30,"s_kucunsj":30,"STATE":1,"s_kucun":30,"isTuiJian":"","s_names":"远大1号","s_products":"洗发水","s_buque":0,"img":"http://yichao.gzqnkj.com/images/noImg.jpg","s_price":"333.00","jianjie":""},{"temp_row_number":6,"temp_column":0,"id":153,"s_name":18,"s_product":13,"s_mangui":12,"s_kucunsj":12,"STATE":1,"s_kucun":12,"isTuiJian":"","s_names":"远大1号","s_products":"毛巾","s_buque":0,"img":"http://yichao.gzqnkj.com/images/noImg.jpg","s_price":"22.00","jianjie":""}]
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
         * id : 136
         * s_name : 18
         * s_product : 11
         * s_mangui : 100
         * s_kucunsj : 15
         * STATE : 1
         * s_kucun : 11
         * isTuiJian : 1
         * s_names : 远大1号
         * s_products : 德亚全脂牛奶
         * s_buque : 89
         * img : http://yichao.gzqnkj.com/UploadFiles/2017/12/30/1514635321711830.jpg
         * s_price : 75.00
         * jianjie : 不是进口的
         */

        private int temp_row_number;
        private int temp_column;
        private int id;
        private int s_name;
        private int s_product;
        private int s_mangui;
        private int s_kucunsj;
        private int STATE;
        private int s_kucun;
        private String isTuiJian;
        private String s_names;
        private String s_products;
        private int s_buque;
        private String img;
        private double s_price;
        private String jianjie;
        private int count;//个数
        private boolean is_select;//是否选中

        public boolean isIs_select() {
            return is_select;
        }

        public void setIs_select(boolean is_select) {
            this.is_select = is_select;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
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

        public int getS_name() {
            return s_name;
        }

        public void setS_name(int s_name) {
            this.s_name = s_name;
        }

        public int getS_product() {
            return s_product;
        }

        public void setS_product(int s_product) {
            this.s_product = s_product;
        }

        public int getS_mangui() {
            return s_mangui;
        }

        public void setS_mangui(int s_mangui) {
            this.s_mangui = s_mangui;
        }

        public int getS_kucunsj() {
            return s_kucunsj;
        }

        public void setS_kucunsj(int s_kucunsj) {
            this.s_kucunsj = s_kucunsj;
        }

        public int getSTATE() {
            return STATE;
        }

        public void setSTATE(int STATE) {
            this.STATE = STATE;
        }

        public int getS_kucun() {
            return s_kucun;
        }

        public void setS_kucun(int s_kucun) {
            this.s_kucun = s_kucun;
        }

        public String getIsTuiJian() {
            return isTuiJian;
        }

        public void setIsTuiJian(String isTuiJian) {
            this.isTuiJian = isTuiJian;
        }

        public String getS_names() {
            return s_names;
        }

        public void setS_names(String s_names) {
            this.s_names = s_names;
        }

        public String getS_products() {
            return s_products;
        }

        public void setS_products(String s_products) {
            this.s_products = s_products;
        }

        public int getS_buque() {
            return s_buque;
        }

        public void setS_buque(int s_buque) {
            this.s_buque = s_buque;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public double getS_price() {
            return s_price;
        }

        public void setS_price(double s_price) {
            this.s_price = s_price;
        }

        public String getJianjie() {
            return jianjie;
        }

        public void setJianjie(String jianjie) {
            this.jianjie = jianjie;
        }
    }
}
