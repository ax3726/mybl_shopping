package com.ycblsc.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/9 0009.
 * 用户信息
 */

public class UserModel implements Serializable{
    private String id;
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public UserModel(){}
    public UserModel(String id, String phone) {
        this.id = id;
        this.phone = phone;
    }
}
