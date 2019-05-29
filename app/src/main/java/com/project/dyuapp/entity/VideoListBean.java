package com.project.dyuapp.entity;

/**
 * @author litongtong
 * @created on 2017/12/11 17:00
 * @description 视频列表
 * @change ${}
 */

public class VideoListBean {

    /**
     * article_id : 53
     * title : 哈哈哈
     * thumb :
     * content : 撒地方是第三方
     */

    private String article_id;
    private String title;
    private String thumb;
    private String content;
    private String addtime;

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
