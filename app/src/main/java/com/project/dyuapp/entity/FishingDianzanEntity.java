package com.project.dyuapp.entity;

/**
 * @author ${shipeiyun}
 * @created on 2017/12/14 0014
 * @description 点赞人列表
 * @change ${}
 */
public class FishingDianzanEntity {
    private String object_id;//会员id
    private String object_type;//点赞对象：1帖子 2文章  3钓场  4渔具店
    private String member_list_nickname;//会员昵称
    private String member_list_headpic;//会员头像
    private String scores;//会员积分
    private String member_tiezi;//会员帖子数
    private String member_guanzhu;//会员关注数量
    private String member_fensi;//会员粉丝数量
    private int relationship;// 点赞人和当前登录用户的关系:1.互粉；2.我关注的；3.未关注；
    private String member_lvl_name;//会员级别名称
    private String member_lvl_id;//会员级别
    private String member_list_id;//会员级别

    public String getMember_list_id() {
        return member_list_id;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
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

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getMember_tiezi() {
        return member_tiezi;
    }

    public void setMember_tiezi(String member_tiezi) {
        this.member_tiezi = member_tiezi;
    }

    public String getMember_guanzhu() {
        return member_guanzhu;
    }

    public void setMember_guanzhu(String member_guanzhu) {
        this.member_guanzhu = member_guanzhu;
    }

    public String getMember_fensi() {
        return member_fensi;
    }

    public void setMember_fensi(String member_fensi) {
        this.member_fensi = member_fensi;
    }

    public int getRelationship() {
        return relationship;
    }

    public void setRelationship(int relationship) {
        this.relationship = relationship;
    }

    public String getMember_lvl_name() {
        return member_lvl_name;
    }

    public void setMember_lvl_name(String member_lvl_name) {
        this.member_lvl_name = member_lvl_name;
    }

    public String getMember_lvl_id() {
        return member_lvl_id;
    }

    public void setMember_lvl_id(String member_lvl_id) {
        this.member_lvl_id = member_lvl_id;
    }
}
