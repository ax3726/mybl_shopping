package com.ycblsc.model;

import com.ycblsc.model.home.ProductListModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/16 0016.
 */

public class CartEventModel {
    private int type;//0 便利架  1 限时达
    private ArrayList<ProductListModel.ReturnDataBean> dataBeans=new ArrayList<>();

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<ProductListModel.ReturnDataBean> getDataBeans() {
        return dataBeans;
    }

    public void setDataBeans(ArrayList<ProductListModel.ReturnDataBean> dataBeans) {
        this.dataBeans = dataBeans;
    }

    public CartEventModel(int type, ArrayList<ProductListModel.ReturnDataBean> dataBeans) {
        this.type = type;
        this.dataBeans = dataBeans;
    }
}
