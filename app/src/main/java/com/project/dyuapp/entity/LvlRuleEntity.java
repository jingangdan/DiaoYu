package com.project.dyuapp.entity;

/**
 * @author ${shipeiyun}
 * @created on 2017/12/8 0008
 * @description 等级规则
 * @change ${}
 */
public class LvlRuleEntity {
    private String member_lvl_id;//等级
    private String member_lvl_name;//头衔
    private String max_score;//所需积分
    private String gold;//金币
    private String mix_score;

    public String getMember_lvl_id() {
        return member_lvl_id;
    }

    public void setMember_lvl_id(String member_lvl_id) {
        this.member_lvl_id = member_lvl_id;
    }

    public String getMember_lvl_name() {
        return member_lvl_name;
    }

    public void setMember_lvl_name(String member_lvl_name) {
        this.member_lvl_name = member_lvl_name;
    }

    public String getMax_score() {
        return max_score;
    }

    public void setMax_score(String max_score) {
        this.max_score = max_score;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getMix_score() {
        return mix_score;
    }

    public void setMix_score(String mix_score) {
        this.mix_score = mix_score;
    }
}
