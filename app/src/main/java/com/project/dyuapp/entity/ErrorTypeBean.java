package com.project.dyuapp.entity;

/**
 * Created by ${田亭亭} on 2017/12/9 0009.
 *
 * @description
 * @change
 */


public class ErrorTypeBean {
    private String errorTypeName;
    private String errorTypeId;
    private boolean isSel;

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

    public ErrorTypeBean(String errorTypeName, String errorTypeId) {
        this.errorTypeName = errorTypeName;
        this.errorTypeId = errorTypeId;
    }

    public String getErrorTypeName() {
        return errorTypeName;
    }

    public void setErrorTypeName(String errorTypeName) {
        this.errorTypeName = errorTypeName;
    }

    public String getErrorTypeId() {
        return errorTypeId;
    }

    public void setErrorTypeId(String errorTypeId) {
        this.errorTypeId = errorTypeId;
    }
}
