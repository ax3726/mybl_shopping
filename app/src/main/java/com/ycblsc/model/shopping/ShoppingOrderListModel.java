package com.ycblsc.model.shopping;

import java.util.List;

/**
 * Created by Administrator on 2018/8/17.
 */

public class ShoppingOrderListModel {

    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"id":23,"F_CreateDate":"2018/8/17 10:30:29","startTime":"2018/8/17 10:30:29","status":"010703","orderMoney":"17.60","shopName":"贵阳市花果园国际","statusName":"待发货","getTime":"","endTime":"","jiedanTime":"2018/8/17 10:30:29","cancelTime":"","backTime":"","sendTime":"","maxTime":"15","trueTime":"","cspf":"","sd":[{"proId":"10","proPrice":"2.80","proCount":"2","proSum":"5.60","s_name":"吐鲁番葡萄干","img":"http://yichao2.gzqnkj.net/UploadFiles/2018/03/09/1520606916202200.png"},{"proId":"7","proPrice":"12.00","proCount":"1","proSum":"12.00","s_name":"溜溜梅","img":"http://yichao2.gzqnkj.net/UploadFiles/2018/03/09/1520606518926210.png"}]},{"id":22,"F_CreateDate":"2018/8/17 10:28:33","startTime":"2018/8/17 10:28:33","status":"010703","orderMoney":"2.80","shopName":"贵阳市花果园国际","statusName":"待发货","getTime":"","endTime":"","jiedanTime":"2018/8/17 10:28:33","cancelTime":"","backTime":"","sendTime":"","maxTime":"15","trueTime":"","cspf":"","sd":[{"proId":"10","proPrice":"2.80","proCount":"1","proSum":"2.80","s_name":"吐鲁番葡萄干","img":"http://yichao2.gzqnkj.net/UploadFiles/2018/03/09/1520606916202200.png"}]},{"id":21,"F_CreateDate":"2018/8/17 10:19:25","startTime":"2018/8/17 10:19:25","status":"010703","orderMoney":"2.80","shopName":"贵阳市花果园国际","statusName":"待发货","getTime":"","endTime":"","jiedanTime":"2018/8/17 10:19:25","cancelTime":"","backTime":"","sendTime":"","maxTime":"15","trueTime":"","cspf":"","sd":[{"proId":"10","proPrice":"2.80","proCount":"1","proSum":"2.80","s_name":"吐鲁番葡萄干","img":"http://yichao2.gzqnkj.net/UploadFiles/2018/03/09/1520606916202200.png"}]},{"id":20,"F_CreateDate":"2018/8/16 20:12:36","startTime":"2018/8/16 20:12:36","status":"010703","orderMoney":"12.00","shopName":"贵阳市花果园国际","statusName":"待发货","getTime":"","endTime":"","jiedanTime":"2018/8/16 20:12:36","cancelTime":"","backTime":"","sendTime":"","maxTime":"15","trueTime":"","cspf":"","sd":[{"proId":"7","proPrice":"12.00","proCount":"1","proSum":"12.00","s_name":"溜溜梅","img":"http://yichao2.gzqnkj.net/UploadFiles/2018/03/09/1520606518926210.png"}]},{"id":19,"F_CreateDate":"2018/8/16 20:12:23","startTime":"2018/8/16 20:12:23","status":"010703","orderMoney":"24.00","shopName":"贵阳市花果园国际","statusName":"待发货","getTime":"","endTime":"","jiedanTime":"2018/8/16 20:12:23","cancelTime":"","backTime":"","sendTime":"","maxTime":"15","trueTime":"","cspf":"","sd":[{"proId":"7","proPrice":"12.00","proCount":"2","proSum":"24.00","s_name":"溜溜梅","img":"http://yichao2.gzqnkj.net/UploadFiles/2018/03/09/1520606518926210.png"}]}]
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
         * id : 23
         * F_CreateDate : 2018/8/17 10:30:29
         * startTime : 2018/8/17 10:30:29
         * status : 010703
         * orderMoney : 17.60
         * shopName : 贵阳市花果园国际
         * statusName : 待发货
         * getTime :
         * endTime :
         * jiedanTime : 2018/8/17 10:30:29
         * cancelTime :
         * backTime :
         * sendTime :
         * maxTime : 15
         * trueTime :
         * cspf :
         * sd : [{"proId":"10","proPrice":"2.80","proCount":"2","proSum":"5.60","s_name":"吐鲁番葡萄干","img":"http://yichao2.gzqnkj.net/UploadFiles/2018/03/09/1520606916202200.png"},{"proId":"7","proPrice":"12.00","proCount":"1","proSum":"12.00","s_name":"溜溜梅","img":"http://yichao2.gzqnkj.net/UploadFiles/2018/03/09/1520606518926210.png"}]
         */

        private int id;
        private String F_CreateDate;
        private String startTime;
        private String status;
        private String orderMoney;
        private String shopName;
        private String statusName;
        private String getTime;
        private String endTime;
        private String jiedanTime;
        private String cancelTime;
        private String backTime;
        private String sendTime;
        private String maxTime;
        private String trueTime;
        private String cspf;
        private List<SdBean> sd;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getF_CreateDate() {
            return F_CreateDate;
        }

        public void setF_CreateDate(String F_CreateDate) {
            this.F_CreateDate = F_CreateDate;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrderMoney() {
            return orderMoney;
        }

        public void setOrderMoney(String orderMoney) {
            this.orderMoney = orderMoney;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getGetTime() {
            return getTime;
        }

        public void setGetTime(String getTime) {
            this.getTime = getTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getJiedanTime() {
            return jiedanTime;
        }

        public void setJiedanTime(String jiedanTime) {
            this.jiedanTime = jiedanTime;
        }

        public String getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(String cancelTime) {
            this.cancelTime = cancelTime;
        }

        public String getBackTime() {
            return backTime;
        }

        public void setBackTime(String backTime) {
            this.backTime = backTime;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getMaxTime() {
            return maxTime;
        }

        public void setMaxTime(String maxTime) {
            this.maxTime = maxTime;
        }

        public String getTrueTime() {
            return trueTime;
        }

        public void setTrueTime(String trueTime) {
            this.trueTime = trueTime;
        }

        public String getCspf() {
            return cspf;
        }

        public void setCspf(String cspf) {
            this.cspf = cspf;
        }

        public List<SdBean> getSd() {
            return sd;
        }

        public void setSd(List<SdBean> sd) {
            this.sd = sd;
        }

        public static class SdBean {
            /**
             * proId : 10
             * proPrice : 2.80
             * proCount : 2
             * proSum : 5.60
             * s_name : 吐鲁番葡萄干
             * img : http://yichao2.gzqnkj.net/UploadFiles/2018/03/09/1520606916202200.png
             */

            private String proId;
            private String proPrice;
            private String proCount;
            private String proSum;
            private String s_name;
            private String img;

            public String getProId() {
                return proId;
            }

            public void setProId(String proId) {
                this.proId = proId;
            }

            public String getProPrice() {
                return proPrice;
            }

            public void setProPrice(String proPrice) {
                this.proPrice = proPrice;
            }

            public String getProCount() {
                return proCount;
            }

            public void setProCount(String proCount) {
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
        }
    }
}
