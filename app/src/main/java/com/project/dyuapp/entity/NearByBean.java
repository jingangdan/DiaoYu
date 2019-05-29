package com.project.dyuapp.entity;

/**
 * @author litongtong
 * @created on 2017/12/8 10:09
 * @description 发现周边钓友实体类
 * @change ${}
 */

public class NearByBean {
    private String lng;
    private String lat;
    private String member_list_id;
    private String member_list_nickname;
    private String member_list_headpic;
    private String member_list_addtime;
    private String scores;
    private String juli;
    private String level;
    private int guanzhu;

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getMember_list_id() {
        return member_list_id;
    }

    public void setMember_list_id(String member_list_id) {
        this.member_list_id = member_list_id;
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

    public String getMember_list_addtime() {
        return member_list_addtime;
    }

    public void setMember_list_addtime(String member_list_addtime) {
        this.member_list_addtime = member_list_addtime;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getJuli() {
        return juli;
    }

    public void setJuli(String juli) {
        this.juli = juli;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getGuanzhu() {
        return guanzhu;
    }

    public void setGuanzhu(int guanzhu) {
        this.guanzhu = guanzhu;
    }
}
