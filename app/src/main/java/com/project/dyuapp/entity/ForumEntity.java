package com.project.dyuapp.entity;

import java.util.List;

/**
 * @author gengqiufang
 * @describtion 帖子
 * @created at 2017/12/9 0009
 */

public class ForumEntity {
    private String f_id;// 帖子id
    private String top_id;//顶级类别帖子(我的收藏)
    private String title; //
    private String content;// 内容
    private String intro;// 内容
    private String thumb_img;//封面图
    private String is_tuijian; // 1推荐  2不推荐
    private String is_jinghua; // 1精华  2不精华帖
    private String member_list_nickname;//  昵称
    private String member_list_headpic;// 头像

    private List<String> thumb_img_list;
    public List<String> getThumb_img_list() {
        return thumb_img_list;
    }

    public void setThumb_img_list(List<String> thumb_img_list) {
        this.thumb_img_list = thumb_img_list;
    }

    public String getIntro() {
        return intro;
    }

    public String getF_id() {
        return f_id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getThumb_img() {
        return thumb_img;
    }

    public String getIs_tuijian() {
        return is_tuijian;
    }

    public String getIs_jinghua() {
        return is_jinghua;
    }

    public String getMember_list_nickname() {
        return member_list_nickname;
    }

    public String getMember_list_headpic() {
        return member_list_headpic;
    }

    public String getTop_id() {
        return top_id;
    }

}
