package com.ycblsc.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/1.
 * 会员充值
 */

public class RechargeModel implements Serializable{
    private String  RechargeName;
    private String Coupon;
    private boolean state;

    public String getRechargeName() {
        return RechargeName;
    }

    public void setRechargeName(String rechargeName) {
        RechargeName = rechargeName;
    }

    public String getCoupon() {
        return Coupon;
    }

    public void setCoupon(String coupon) {
        Coupon = coupon;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
    public RechargeModel(String RechargeName, String Coupon) {
        this.RechargeName = RechargeName;
        this.Coupon = Coupon;
    }
}
