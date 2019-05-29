package com.project.dyuapp.entity;

/**
 * @author litongtong
 * @created on 2017/12/11 16:12
 * @description 搜索历史列表
 * @change ${}
 */

public class SearchHistoryBean {

    /**
     * s_id : 1
     * keywords : 厉害了我的哥
     */

    private String s_id;
    private String keywords;

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
