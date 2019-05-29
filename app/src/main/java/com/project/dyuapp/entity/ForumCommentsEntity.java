package com.project.dyuapp.entity;

/**
 * @author gengqiufang
 * @describtion 帖子评论
 * @created at 2017/12/13 0013
 */

public class ForumCommentsEntity {
    private String c_id;//评论id
    private String member_list_headpic;//评论者头像
    private String member_list_nickname;//评论者昵称
    private String c_content;//评论内容
    private String return_id;//所回复评论的评论者昵称
    private String createtime;//评论时间
    private String is_look;// 1已读 2未读（无用请忽略此字段）
    private String return_content;//所回复评论的内容
    private String c_codes;//等级

    public String getC_id() {
        return c_id;
    }

    public String getMember_list_headpic() {
        return member_list_headpic;
    }

    public String getMember_list_nickname() {
        return member_list_nickname;
    }

    public String getC_content() {
        return c_content;
    }

    public String getReturn_id() {
        return return_id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public String getIs_look() {
        return is_look;
    }

    public String getReturn_content() {
        return return_content;
    }

    public String getC_codes() {
        return c_codes;
    }

    public void setC_codes(String c_codes) {
        this.c_codes = c_codes;
    }
}
