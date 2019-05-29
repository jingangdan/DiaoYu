package com.project.dyuapp.entity;

/**
 * @author ${shipeiyun}
 * @created on 2017/12/9 0009
 * @description 二维码页信息
 * @change ${}
 */
public class MyQrcodeEntity {

    /**
     * member_list_nickname : 熊大
     * member_list_headpic : /public/img/mystery.png
     * scores : 740
     * level : 3
     * ercode : ./data/upload/201712/1512781306901png
     */

    private String member_list_nickname;
    private String member_list_headpic;
    private String scores;
    private String level;
    private String ercode;

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

    public String getErcode() {
        return ercode;
    }

    public void setErcode(String ercode) {
        this.ercode = ercode;
    }
}
