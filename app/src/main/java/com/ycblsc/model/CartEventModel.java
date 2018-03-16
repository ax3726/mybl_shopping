package com.ycblsc.model;

import com.ycblsc.model.home.ProductListModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/16 0016.
 */

public class CartEventModel {

    private ArrayList<ProductListModel.ReturnDataBean> dataBeans=new ArrayList<>();

    public ArrayList<ProductListModel.ReturnDataBean> getDataBeans() {
        return dataBeans;
    }

    public void setDataBeans(ArrayList<ProductListModel.ReturnDataBean> dataBeans) {
        this.dataBeans = dataBeans;
    }

    public CartEventModel(ArrayList<ProductListModel.ReturnDataBean> dataBeans) {
        this.dataBeans = dataBeans;
    }
}
