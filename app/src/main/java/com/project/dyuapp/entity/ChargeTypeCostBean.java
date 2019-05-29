package com.project.dyuapp.entity;

/**
 * Created by ${田亭亭} on 2017/11/29 0029.
 *
 * @description
 * @change
 */


public class ChargeTypeCostBean {
    private boolean isSelect;
    private String name;
    private boolean isFocus;//是否获得焦点
    private boolean isShowBraw;//是否显示背景

    public ChargeTypeCostBean(boolean isSelect, String name) {
        this.isSelect = isSelect;
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFocus() {
        return isFocus;
    }

    public void setFocus(boolean focus) {
        isFocus = focus;
    }

    public boolean isShowBraw() {
        return isShowBraw;
    }

    public void setShowBraw(boolean showBraw) {
        isShowBraw = showBraw;
    }
}
