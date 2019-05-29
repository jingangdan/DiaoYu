package com.project.dyuapp.entity;

/**
 * Created by ${田亭亭} on 2017/11/29 0029.
 *
 * @description
 * @change
 */


public class DrawerListBean {
    private boolean isSelect;
    private String d_id;
    private String parent_id;
    private String name;

    public DrawerListBean(boolean isSelect, String name) {
        this.isSelect = isSelect;
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
