package com.project.dyuapp.entity;

import java.util.List;

/**
 * Created by ${田亭亭} on 2017/12/13 0013.
 *
 * @description
 * @change
 */


public class NewestDetailBean {


    /**
     * article_id : 54
     * title : sfd sdf sdf
     * thumb : /data/upload/2017-09-23/59c5bf42513ec.jpg
     * content : https://v.qq.com/x/cover/a7icv0jfw3qp5cz.html?ptag=baidu.aladdin.variety
     * introduction : null
     * name : 江湖行
     */

    private String article_id;
    private String title;
    private String thumb;
    private String content;
    private String introduction;
    private String name;
    private String count;
    private int is_zan;
    private String contentSum;
    private List<String> member_list_headpic;

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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getIs_zan() {
        return is_zan;
    }

    public void setIs_zan(int is_zan) {
        this.is_zan = is_zan;
    }

    public String getContentSum() {
        return contentSum;
    }

    public void setContentSum(String contentSum) {
        this.contentSum = contentSum;
    }

    public List<String> getMember_list_headpic() {
        return member_list_headpic;
    }

    public void setMember_list_headpic(List<String> member_list_headpic) {
        this.member_list_headpic = member_list_headpic;
    }
}
