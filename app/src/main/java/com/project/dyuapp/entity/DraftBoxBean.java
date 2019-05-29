package com.project.dyuapp.entity;

/**
 * @author litongtong
 * @created on 2017/12/12 14:16
 * @description 草稿箱实体类
 * @change ${}
 */

public class DraftBoxBean {

    /**
     * f_id : 1
     * title : 手动发帖测试1
     * thumb_img : 11.jpg
     * str_content : 帖子内容
     * top_id : 56
     * addtime : 1511852782
     * name : #渔获#
     */

    private String f_id;
    private String title;
    private String thumb_img;
    private String content;
    private String top_id;
    private String addtime;
    private String name;
    private String intro;

    public String getIntro() {
        return intro;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb_img() {
        return thumb_img;
    }

    public void setThumb_img(String thumb_img) {
        this.thumb_img = thumb_img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTop_id() {
        return top_id;
    }

    public void setTop_id(String top_id) {
        this.top_id = top_id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
