package com.project.dyuapp.entity;

/**
 * @author ${shipeiyun}
 * @created on 2017/12/6 0006
 * @description 个人中心信息
 * @change ${}
 */
public class PersonEntity {

    /**
     * member_list_nickname : 熊二
     * member_list_headpic : /public/img/mystery.png
     * money : 616
     * scores : 10
     * level : 1
     * tienum : 3
     * guannum : 1
     * fennum : 1
     */
    private String member_list_nickname;
    private String member_list_headpic;
    private String money;
    private String scores;
    private String level;
    private String tienum;
    private String guannum;
    private String fennum;

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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
}
