package com.project.dyuapp.entity;

/**
 * @author litongtong
 * @created on 2017/12/6 17:49
 * @description DiaoYu
 * @change ${}
 */

public class MyReplyBean {

    /**
     * c_id : 130
     * member_list_id : 82
     * object_id : 126
     * object_type : 1
     * return_id : 126
     * c_content : Eee
     * c_imgs : null
     * c_codes : 0
     * createtime : 1513411614
     * is_look : 1
     * member_list_nickname : 18230417676
     * member_list_headpic : /data/upload/2017-12-15/5a33317e260b5.png
     * info : {"title":"测试用","name":"钓技大全"}
     */

    private String c_id;
    private String member_list_id;
    private String object_id;
    private String object_type;
    private String return_id;
    private String c_content;
    private String c_imgs;
    private String c_codes;
    private String createtime;
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

    public String getMember_list_id() {
        return member_list_id;
    }

    public void setMember_list_id(String member_list_id) {
        this.member_list_id = member_list_id;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }

    public String getReturn_id() {
        return return_id;
    }

    public void setReturn_id(String return_id) {
        this.return_id = return_id;
    }

    public String getC_content() {
        return c_content;
    }

    public void setC_content(String c_content) {
        this.c_content = c_content;
    }

    public String getC_imgs() {
        return c_imgs;
    }

    public void setC_imgs(String c_imgs) {
        this.c_imgs = c_imgs;
    }

    public String getC_codes() {
        return c_codes;
    }

    public void setC_codes(String c_codes) {
        this.c_codes = c_codes;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
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
         * title : 测试用
         * name : 钓技大全
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
