package com.project.dyuapp.entity;

/**
 * @author ${shipeiyun}
 * @created on 2017/12/18 0018
 * @description 联系方式
 * @change ${}
 */
public class ContactInformation {
    /**
     * data : {"tel":"12310001000","qq":"1511512"}
     * code : 0
     * message : 联系方式
     */
    private String tel;
    private String qq;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
