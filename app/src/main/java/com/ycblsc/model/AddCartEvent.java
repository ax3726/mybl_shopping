package com.ycblsc.model;

/**
 * Created by Administrator on 2018/8/6.
 */

public class AddCartEvent {

    private String id;

    public AddCartEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
