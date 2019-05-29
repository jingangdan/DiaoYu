package com.project.dyuapp.entity;

/**
 * @author ${shipeiyun}
 * @created on 2017/12/19 0019
 * @description 登录成功
 * @change ${}
 */

public class LoginEntity {

    /**
     * state : 74
     */
    private String state;
    private String jpush_acc;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getJpush_acc() {
        return jpush_acc;
    }
}
