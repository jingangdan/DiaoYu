package com.project.dyuapp.entity;

/**
 * Created by ${田亭亭} on 2017/12/5 0005.
 *
 * @description
 * @change
 */


public class MyEventBus {
    private int position;
    private String cid;
    private boolean isPost;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public MyEventBus(int position, String cid) {
        this.position = position;
        this.cid = cid;
    }

    public MyEventBus(String cid, boolean isPost) {
        this.cid = cid;
        this.isPost = isPost;
    }

    public MyEventBus(String cid) {
        this.cid = cid;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isPost() {
        return isPost;
    }

    public void setPost(boolean post) {
        isPost = post;
    }
}
