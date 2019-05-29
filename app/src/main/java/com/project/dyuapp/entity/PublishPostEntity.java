package com.project.dyuapp.entity;

/**
 * @author gengqiufang
 * @describtion 发布帖子的实体
 * @created at 2017/11/28 0028
 */
public class PublishPostEntity {
    private String img="";
    private String content="";
    private boolean isPic;

    public PublishPostEntity(boolean isPic) {
        this.isPic = isPic;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPic() {
        return isPic;
    }

    public void setPic(boolean pic) {
        isPic = pic;
    }
}
