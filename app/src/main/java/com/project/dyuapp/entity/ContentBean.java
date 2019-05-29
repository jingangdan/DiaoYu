package com.project.dyuapp.entity;

/**
 * @author gengqiufang
 * @describtion 帖子内容的实体
 * @created at 2017/11/28 0028
 */
public class ContentBean {
    private String str_content="";
    private String str_imgs="";
    private boolean isPic;
    private int picPosition;

    public int getPicPosition() {
        return picPosition;
    }

    public void setPicPosition(int picPosition) {
        this.picPosition = picPosition;
    }

    public ContentBean(boolean isPic) {
        this.isPic = isPic;
    }

    public String getStr_content() {
        return str_content;
    }

    public void setStr_content(String str_content) {
        this.str_content = str_content;
    }

    public String getStr_imgs() {
        return str_imgs;
    }

    public void setStr_imgs(String str_imgs) {
        this.str_imgs = str_imgs;
    }

    public boolean isPic() {
        return isPic;
    }

    public void setPic(boolean pic) {
        isPic = pic;
    }
}
