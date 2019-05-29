package com.project.dyuapp.entity;

/**
 * @author litongtong
 * @created on 2017/12/6 16:25
 * @description 我的消息-我的私信列表
 * @change ${}
 */

public class MyLetterBean {

    /**
     * message_id : 40
     * from_member__id : 1
     * desc : 私信内容3
     * createtime : 1506388415
     * member_list_nickname : 熊大
     * member_list_headpic : /public/img/mystery.png
     */

    private String message_id;
    private String from_member__id;
    private String desc;
    private String createtime;
    private String member_list_nickname;
    private String member_list_headpic;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getFrom_member__id() {
        return from_member__id;
    }

    public void setFrom_member__id(String from_member__id) {
        this.from_member__id = from_member__id;
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

    public String getMember_list_nickname() {
        return member_list_nickname;
    }

    public void setMember_list_nickname(String member_list_nickname) {
        this.member_list_nickname = member_list_nickname;
    }

    public String getMember_list_headpic() {
        return member_list_headpic;
    }

    public void setMember_list_headpic(String member_list_headpic) {
        this.member_list_headpic = member_list_headpic;
    }
}
