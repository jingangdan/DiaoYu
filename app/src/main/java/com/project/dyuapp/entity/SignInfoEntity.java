package com.project.dyuapp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ${shipeiyun}
 * @created on 2017/12/7 0007
 * @description 签到信息
 * @change ${}
 */
public class SignInfoEntity {

    /**
     * member_list_nickname : 熊大
     * member_list_headpic : /public/img/mystery.png
     * money : 2001
     * scores : 735
     * level : 3
     * signPercentage : 15
     * is_sign : 0
     * sign_log : [{"adddate":"2017-12-01"},{"adddate":"2017-12-02"}]
     */
    private String member_list_nickname;
    private String member_list_headpic;
    private String money;
    private String scores;
    private String level;
    private int signPercentage;
    private int is_sign;
    /**
     * adddate : 2017-12-01
     */

    private List<SignLogBean> sign_log=new ArrayList<>();

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

    public int getSignPercentage() {
        return signPercentage;
    }

    public void setSignPercentage(int signPercentage) {
        this.signPercentage = signPercentage;
    }

    public int getIs_sign() {
        return is_sign;
    }

    public void setIs_sign(int is_sign) {
        this.is_sign = is_sign;
    }

    public List<SignLogBean> getSign_log() {
        return sign_log;
    }

    public void setSign_log(List<SignLogBean> sign_log) {
        this.sign_log = sign_log;
    }

    public static class SignLogBean {
        private String adddate;

        public String getAdddate() {
            return adddate;
        }

        public void setAdddate(String adddate) {
            this.adddate = adddate;
        }
    }
}
