package com.ycblsc.model.shopping;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/8/6.
 */

public class GoodsInfoModel implements Serializable {

    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"id":7,"s_products":"溜溜梅","jianjie":"商品简介信息","note":"","s_price":12,"img":"http://yichao2.gzqnkj.net/UploadFiles/2018/03/09/1520606518926210.png","sd":[]}]
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

    public static class ReturnDataBean implements Serializable{
        /**
         * id : 7
         * s_products : 溜溜梅
         * jianjie : 商品简介信息
         * note :
         * s_price : 12
         * img : http://yichao2.gzqnkj.net/UploadFiles/2018/03/09/1520606518926210.png
         * sd : []
         */

        private int id;
        private String s_products;
        private String jianjie;
        private String note;
        private double s_price;
        private String img;
        private List<SdBean> sd;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getS_products() {
            return s_products;
        }

        public void setS_products(String s_products) {
            this.s_products = s_products;
        }

        public String getJianjie() {
            return jianjie;
        }

        public void setJianjie(String jianjie) {
            this.jianjie = jianjie;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public double getS_price() {
            return s_price;
        }

        public void setS_price(double s_price) {
            this.s_price = s_price;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public List<SdBean> getSd() {
            return sd;
        }

        public void setSd(List<SdBean> sd) {
            this.sd = sd;
        }
        public static class SdBean implements Serializable {
            private String title;
            private String F_Value;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getF_Value() {
                return F_Value;
            }

            public void setF_Value(String f_Value) {
                F_Value = f_Value;
            }
        }
    }
}
