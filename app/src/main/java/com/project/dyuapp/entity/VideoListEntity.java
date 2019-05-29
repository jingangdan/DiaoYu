package com.project.dyuapp.entity;

/**
 * @describtion  渔乐短片
 * @author gengqiufang
 * @created at 2017/12/8 0008
 */

public class VideoListEntity {
    private String url;
    private boolean isShow=true;

    public VideoListEntity(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
