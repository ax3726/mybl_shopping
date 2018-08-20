package com.ycblsc.model;

/**
 * Created by Administrator on 2018/8/6.
 */

public class AddCartEvent {

    private String id;
    private int type;

    public AddCartEvent(String id,int type) {
        this.id = id;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
