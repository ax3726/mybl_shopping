package com.ycblsc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/29 0029.
 */

public class GoodsTypeModel {
    private String name;
    private boolean is_select;
    private List<String> list = new ArrayList<>();

    public List<String> getList() {
        return list;
    }

    public void setList(int cout) {
        list.clear();
        for (int i = 0; i < cout; i++) {
            list.add("");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    public GoodsTypeModel(String name, int count) {
        this.name = name;
        setList(count);
    }
}
