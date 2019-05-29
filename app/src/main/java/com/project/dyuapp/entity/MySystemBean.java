package com.project.dyuapp.entity;

/**
 * @author litongtong
 * @created on 2017/12/7 10:03
 * @description 系统消息
 * @change ${}
 */

public class MySystemBean {
    private String message_id;
    private String desc;
    private String createtime;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
