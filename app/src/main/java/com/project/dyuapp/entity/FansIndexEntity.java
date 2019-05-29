package com.project.dyuapp.entity;

/**
 * @describtion  7个人中心-粉丝-主页
 * @author gengqiufang
 * @created at 2017/12/11 0011
 */

public class FansIndexEntity {
  private String member_list_nickname; //昵称
    private String  member_list_headpic ;//头像
    private String  scores;
    private String  level ;//等级
    private String  tienum ;//帖子数目
    private String    guannum;//关注数目
    private String   fennum ;//粉丝数目
    private String  is_hxguanzhu;//是否互相关注  1是 2否

    public String getMember_list_nickname() {
        return member_list_nickname;
    }

    public String getMember_list_headpic() {
        return member_list_headpic;
    }

    public String getScores() {
        return scores;
    }

    public String getLevel() {
        return level;
    }

    public String getTienum() {
        return tienum;
    }

    public String getGuannum() {
        return guannum;
    }

    public String getFennum() {
        return fennum;
    }

    public String getIs_hxguanzhu() {
        return is_hxguanzhu;
    }
}
