package com.ycblsc.model.home;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class HeadListModel {

    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"id":"/images/header/1.png","url":"http://yichao.gzqnkj.com/images/header/1.png"},{"id":"/images/header/10.png","url":"http://yichao.gzqnkj.com/images/header/10.png"},{"id":"/images/header/11.png","url":"http://yichao.gzqnkj.com/images/header/11.png"},{"id":"/images/header/2.png","url":"http://yichao.gzqnkj.com/images/header/2.png"},{"id":"/images/header/3.png","url":"http://yichao.gzqnkj.com/images/header/3.png"},{"id":"/images/header/4.png","url":"http://yichao.gzqnkj.com/images/header/4.png"},{"id":"/images/header/5.png","url":"http://yichao.gzqnkj.com/images/header/5.png"},{"id":"/images/header/6.png","url":"http://yichao.gzqnkj.com/images/header/6.png"},{"id":"/images/header/7.png","url":"http://yichao.gzqnkj.com/images/header/7.png"},{"id":"/images/header/8.png","url":"http://yichao.gzqnkj.com/images/header/8.png"},{"id":"/images/header/9.png","url":"http://yichao.gzqnkj.com/images/header/9.png"}]
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
         * id : /images/header/1.png
         * url : http://yichao.gzqnkj.com/images/header/1.png
         */

        private String id;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
