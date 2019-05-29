package com.project.dyuapp.entity;

/**
 * @author litongtong
 * @created on 2017/12/7 09:25
 * @description 我的消息-赞过我
 * @change ${}
 */

public class MyPraiseBean {

    /**
     * c_id : 108
     * object_id : 128
     * member_list_id : 73
     * createtime : 1513407396
     * object_type : 1
     * is_look : 1
     * member_list_nickname : 18330141301
     * member_list_headpic : /data/upload/2017-12-14/5a323aefc9c01.jpg
     * info : {"title":"发布个饵料帖","name":"饵料配方"}
     */

    private String c_id;
    private String object_id;
    private String member_list_id;
    private String createtime;
    private String object_type;
    private String is_look;
    private String member_list_nickname;
    private String member_list_headpic;
    private InfoBean info;

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getMember_list_id() {
        return member_list_id;
    }

    public void setMember_list_id(String member_list_id) {
        this.member_list_id = member_list_id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }

    public String getIs_look() {
        return is_look;
    }

    public void setIs_look(String is_look) {
        this.is_look = is_look;
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

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * title : 发布个饵料帖
         * name : 饵料配方
         */

        private String title;
        private String name;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
