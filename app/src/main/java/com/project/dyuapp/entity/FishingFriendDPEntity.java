package com.project.dyuapp.entity;

import java.util.List;

/**
 * Created by jingang on 2018/5/11.
 */

public class FishingFriendDPEntity {

    /**
     * dp_id : 27
     * score : 1
     * addtime : 1526115617
     * intro :
     * member_list_headpic : /data/upload/avatar/avator.png
     * member_list_nickname : 钓鱼吧0460
     * content : [{"id":"15","str_content":"","str_imgs":"/data/upload/2018-05-12/5af6ad22a02c6.jpeg","dp_id":"27"},{"id":"14","str_content":"","str_imgs":"/data/upload/2018-05-12/5af6ad2240ce8.jpeg","dp_id":"27"},{"id":"13","str_content":"","str_imgs":"/data/upload/2018-05-12/5af6ad21a02cd.jpeg","dp_id":"27"}]
     */

    private String dp_id;
    private String score;
    private String addtime;
    private String intro;
    private String member_list_headpic;
    private String member_list_nickname;
    private List<ContentBean> content;

    public String getDp_id() {
        return dp_id;
    }

    public void setDp_id(String dp_id) {
        this.dp_id = dp_id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getMember_list_headpic() {
        return member_list_headpic;
    }

    public void setMember_list_headpic(String member_list_headpic) {
        this.member_list_headpic = member_list_headpic;
    }

    public String getMember_list_nickname() {
        return member_list_nickname;
    }

    public void setMember_list_nickname(String member_list_nickname) {
        this.member_list_nickname = member_list_nickname;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * id : 15
         * str_content :
         * str_imgs : /data/upload/2018-05-12/5af6ad22a02c6.jpeg
         * dp_id : 27
         */

        private String id;
        private String str_content;
        private String str_imgs;
        private String dp_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getDp_id() {
            return dp_id;
        }

        public void setDp_id(String dp_id) {
            this.dp_id = dp_id;
        }
    }
}
