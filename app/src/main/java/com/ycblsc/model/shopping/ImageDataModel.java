package com.ycblsc.model.shopping;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3 0003.
 */

public class ImageDataModel {

    /**
     * ReturnCode : 1
     * ReturnMessage :
     * ReturnData : [{"I_ID":49,"I_FolderID":21,"I_User_ID":1,"I_Title":"便利架广告","I_Content":"","I_Date":"2017/12/30 3:09:24","F_Click":10000,"F_Img":"http://yichao.gzqnkj.com/UploadFiles/2017/12/30/1514654501919570.jpg","F_Source":"蚁巢便利","F_Author":"蚁巢便利","F_CheckUID":"","F_CheckDate":"","F_Status":"1","F_Url":"","F_IsNew":"1","F_IsFirstShow":"1","F_OrderBy":49,"F_Abstract":"","F_Color":"","F_Size":"","F_Style":"","F_UrlOther":"","I_Organ_ID":1,"I_Organ_ID_True":1,"IsRecommend":"0","RecommendOrgan":1,"IsYouXiu":"2","F_IsComment":"0","F_Img2":""}]
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
         * I_ID : 49
         * I_FolderID : 21
         * I_User_ID : 1
         * I_Title : 便利架广告
         * I_Content :
         * I_Date : 2017/12/30 3:09:24
         * F_Click : 10000
         * F_Img : http://yichao.gzqnkj.com/UploadFiles/2017/12/30/1514654501919570.jpg
         * F_Source : 蚁巢便利
         * F_Author : 蚁巢便利
         * F_CheckUID :
         * F_CheckDate :
         * F_Status : 1
         * F_Url :
         * F_IsNew : 1
         * F_IsFirstShow : 1
         * F_OrderBy : 49
         * F_Abstract :
         * F_Color :
         * F_Size :
         * F_Style :
         * F_UrlOther :
         * I_Organ_ID : 1
         * I_Organ_ID_True : 1
         * IsRecommend : 0
         * RecommendOrgan : 1
         * IsYouXiu : 2
         * F_IsComment : 0
         * F_Img2 :
         */

        private int I_ID;
        private int I_FolderID;
        private int I_User_ID;
        private String I_Title;
        private String I_Content;
        private String I_Date;
        private int F_Click;
        private String F_Img;
        private String F_Source;
        private String F_Author;
        private String F_CheckUID;
        private String F_CheckDate;
        private String F_Status;
        private String F_Url;
        private String F_IsNew;
        private String F_IsFirstShow;
        private int F_OrderBy;
        private String F_Abstract;
        private String F_Color;
        private String F_Size;
        private String F_Style;
        private String F_UrlOther;
        private int I_Organ_ID;
        private int I_Organ_ID_True;
        private String IsRecommend;
        private int RecommendOrgan;
        private String IsYouXiu;
        private String F_IsComment;
        private String F_Img2;

        public int getI_ID() {
            return I_ID;
        }

        public void setI_ID(int I_ID) {
            this.I_ID = I_ID;
        }

        public int getI_FolderID() {
            return I_FolderID;
        }

        public void setI_FolderID(int I_FolderID) {
            this.I_FolderID = I_FolderID;
        }

        public int getI_User_ID() {
            return I_User_ID;
        }

        public void setI_User_ID(int I_User_ID) {
            this.I_User_ID = I_User_ID;
        }

        public String getI_Title() {
            return I_Title;
        }

        public void setI_Title(String I_Title) {
            this.I_Title = I_Title;
        }

        public String getI_Content() {
            return I_Content;
        }

        public void setI_Content(String I_Content) {
            this.I_Content = I_Content;
        }

        public String getI_Date() {
            return I_Date;
        }

        public void setI_Date(String I_Date) {
            this.I_Date = I_Date;
        }

        public int getF_Click() {
            return F_Click;
        }

        public void setF_Click(int F_Click) {
            this.F_Click = F_Click;
        }

        public String getF_Img() {
            return F_Img;
        }

        public void setF_Img(String F_Img) {
            this.F_Img = F_Img;
        }

        public String getF_Source() {
            return F_Source;
        }

        public void setF_Source(String F_Source) {
            this.F_Source = F_Source;
        }

        public String getF_Author() {
            return F_Author;
        }

        public void setF_Author(String F_Author) {
            this.F_Author = F_Author;
        }

        public String getF_CheckUID() {
            return F_CheckUID;
        }

        public void setF_CheckUID(String F_CheckUID) {
            this.F_CheckUID = F_CheckUID;
        }

        public String getF_CheckDate() {
            return F_CheckDate;
        }

        public void setF_CheckDate(String F_CheckDate) {
            this.F_CheckDate = F_CheckDate;
        }

        public String getF_Status() {
            return F_Status;
        }

        public void setF_Status(String F_Status) {
            this.F_Status = F_Status;
        }

        public String getF_Url() {
            return F_Url;
        }

        public void setF_Url(String F_Url) {
            this.F_Url = F_Url;
        }

        public String getF_IsNew() {
            return F_IsNew;
        }

        public void setF_IsNew(String F_IsNew) {
            this.F_IsNew = F_IsNew;
        }

        public String getF_IsFirstShow() {
            return F_IsFirstShow;
        }

        public void setF_IsFirstShow(String F_IsFirstShow) {
            this.F_IsFirstShow = F_IsFirstShow;
        }

        public int getF_OrderBy() {
            return F_OrderBy;
        }

        public void setF_OrderBy(int F_OrderBy) {
            this.F_OrderBy = F_OrderBy;
        }

        public String getF_Abstract() {
            return F_Abstract;
        }

        public void setF_Abstract(String F_Abstract) {
            this.F_Abstract = F_Abstract;
        }

        public String getF_Color() {
            return F_Color;
        }

        public void setF_Color(String F_Color) {
            this.F_Color = F_Color;
        }

        public String getF_Size() {
            return F_Size;
        }

        public void setF_Size(String F_Size) {
            this.F_Size = F_Size;
        }

        public String getF_Style() {
            return F_Style;
        }

        public void setF_Style(String F_Style) {
            this.F_Style = F_Style;
        }

        public String getF_UrlOther() {
            return F_UrlOther;
        }

        public void setF_UrlOther(String F_UrlOther) {
            this.F_UrlOther = F_UrlOther;
        }

        public int getI_Organ_ID() {
            return I_Organ_ID;
        }

        public void setI_Organ_ID(int I_Organ_ID) {
            this.I_Organ_ID = I_Organ_ID;
        }

        public int getI_Organ_ID_True() {
            return I_Organ_ID_True;
        }

        public void setI_Organ_ID_True(int I_Organ_ID_True) {
            this.I_Organ_ID_True = I_Organ_ID_True;
        }

        public String getIsRecommend() {
            return IsRecommend;
        }

        public void setIsRecommend(String IsRecommend) {
            this.IsRecommend = IsRecommend;
        }

        public int getRecommendOrgan() {
            return RecommendOrgan;
        }

        public void setRecommendOrgan(int RecommendOrgan) {
            this.RecommendOrgan = RecommendOrgan;
        }

        public String getIsYouXiu() {
            return IsYouXiu;
        }

        public void setIsYouXiu(String IsYouXiu) {
            this.IsYouXiu = IsYouXiu;
        }

        public String getF_IsComment() {
            return F_IsComment;
        }

        public void setF_IsComment(String F_IsComment) {
            this.F_IsComment = F_IsComment;
        }

        public String getF_Img2() {
            return F_Img2;
        }

        public void setF_Img2(String F_Img2) {
            this.F_Img2 = F_Img2;
        }
    }
}
