package com.project.dyuapp.entity;

/**
 * @author ${shipeiyun}
 * @created on 2017/12/6 0006
 * @description 粉丝列表
 * @change ${}
 */
public class FansEntity {

    /**
     * member_list_id : 2
     * member_list_nickname : 熊二
     * member_list_headpic : /public/img/mystery.png
     * scores : 10
     * level : 1
     * tienum : 1
     * guannum : 1
     * fennum : 1
     * is_hxguanzhu : 1
     */
    private String member_list_id;
    private String member_list_nickname;
    private String member_list_headpic;
    private String scores;
    private String level;
    private String tienum;
    private String guannum;
    private String fennum;
    private int is_hxguanzhu;

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

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTienum() {
        return tienum;
    }

    public void setTienum(String tienum) {
        this.tienum = tienum;
    }

    public String getGuannum() {
        return guannum;
    }

    public void setGuannum(String guannum) {
        this.guannum = guannum;
    }

    public String getFennum() {
        return fennum;
    }

    public void setFennum(String fennum) {
        this.fennum = fennum;
    }

    public int getIs_hxguanzhu() {
        return is_hxguanzhu;
    }

    public void setIs_hxguanzhu(int is_hxguanzhu) {
        this.is_hxguanzhu = is_hxguanzhu;
    }
}
