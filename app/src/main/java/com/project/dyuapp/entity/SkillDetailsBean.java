package com.project.dyuapp.entity;

/**
 * @创建者 任伟-------------无用----------
 * @创建时间 2017/12/04 16:12
 * @描述 ${}
 */

public class SkillDetailsBean {
    private String content_tv;
    private String content_iv;

    public SkillDetailsBean(String content_tv, String content_iv) {
        this.content_tv = content_tv;
        this.content_iv = content_iv;
    }

    public String getContent_tv() {
        return content_tv;
    }

    public void setContent_tv(String content_tv) {
        this.content_tv = content_tv;
    }

    public String getContent_iv() {
        return content_iv;
    }

    public void setContent_iv(String content_iv) {
        this.content_iv = content_iv;
    }
}
