package com.project.dyuapp.entity;

/**
 * @author litongtong
 * @created on 2017/12/13 11:28
 * @description 我的消息-未读实体类
 * @change ${}
 */

public class MymsgBean {

    /**
     * sixin : 2
     * system : 1
     * reply : 4
     * fabulous : 2
     * reward : 3
     */

    private String sixin;
    private String system;
    private String reply;
    private String fabulous;
    private String reward;

    public String getSixin() {
        return sixin;
    }

    public void setSixin(String sixin) {
        this.sixin = sixin;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getFabulous() {
        return fabulous;
    }

    public void setFabulous(String fabulous) {
        this.fabulous = fabulous;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }
}
