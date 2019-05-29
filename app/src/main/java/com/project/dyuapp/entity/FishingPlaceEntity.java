package com.project.dyuapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @创建者 任伟
 * @创建时间 2017/12/11 14:45
 * @描述 ${}
 */

public class FishingPlaceEntity implements Serializable {


    public static class FishForumBean {
        /**
         * member_list_id :
         * title :
         * thumb_img :
         * is_tuijian :
         * is_jinghua :
         * member_list_headpic :
         * member_list_nickname :
         * str_content :
         */
        private String f_id;
        private String member_list_id;
        private String title;
        private String thumb_img;
        private String is_tuijian;
        private String is_jinghua;
        private String member_list_headpic;
        private String member_list_nickname;
        private String str_content;

        public String getMember_list_id() {
            return member_list_id;
        }

        public String getF_id() {
            return f_id;
        }

        public void setMember_list_id(String member_list_id) {
            this.member_list_id = member_list_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumb_img() {
            return thumb_img;
        }

        public void setThumb_img(String thumb_img) {
            this.thumb_img = thumb_img;
        }

        public String getIs_tuijian() {
            return is_tuijian;
        }

        public void setIs_tuijian(String is_tuijian) {
            this.is_tuijian = is_tuijian;
        }

        public String getIs_jinghua() {
            return is_jinghua;
        }

        public void setIs_jinghua(String is_jinghua) {
            this.is_jinghua = is_jinghua;
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

        public String getStr_content() {
            return str_content;
        }

        public void setStr_content(String str_content) {
            this.str_content = str_content;
        }
    }

    /**
     * f_gid : 118
     * g_name : 柳清河
     * g_image : /data/upload/2018-01-31/1517387452474.jpeg
     * cat_id : 77
     * fishcats : 22,23,24,31
     * pay_type : 2
     * cityid : 371300
     * pay_content :
     * fishseat : null
     * carparks : null
     * g_intro :
     * position : 柳青街道
     * ground_phone : null
     * grounds_address : null
     * longitude : 118.375221
     * latitude : 35.117124
     * name : 江河
     * fish_type : 鲫鱼 ,鲤鱼 ,草鱼,其他鱼
     * images : [{"img_url":"/data/upload/2018-03-15/1521100632869.png"}]
     * fish_forum : []
     * fish_dianping : [{"dp_id":"28","score":"3","addtime":"1526117696","intro":"Asdasdasdasasdasdasd","member_list_id":"379","member_list_headpic":"/data/upload/avatar/avator.png","member_list_nickname":"钓鱼吧0460","content":[{"id":"16","str_content":"Asdasdasdasasdasdasd","str_imgs":"/data/upload/2018-05-12/5af6b540b3d09.jpeg","dp_id":"28"}]},{"dp_id":"27","score":"1","addtime":"1526115617","intro":"","member_list_id":"379","member_list_headpic":"/data/upload/avatar/avator.png","member_list_nickname":"钓鱼吧0460","content":[{"id":"15","str_content":"","str_imgs":"/data/upload/2018-05-12/5af6ad22a02c6.jpeg","dp_id":"27"},{"id":"14","str_content":"","str_imgs":"/data/upload/2018-05-12/5af6ad2240ce8.jpeg","dp_id":"27"},{"id":"13","str_content":"","str_imgs":"/data/upload/2018-05-12/5af6ad21a02cd.jpeg","dp_id":"27"}]},{"dp_id":"26","score":"1","addtime":"1526019382","intro":"","member_list_id":"379","member_list_headpic":"/data/upload/avatar/avator.png","member_list_nickname":"钓鱼吧0460","content":[]}]
     * is_follow : 0
     * fish_list : [{"f_gid":"117","g_name":"后孙沟水库","g_image":"/data/upload/2018-01-31/1517387355274.jpeg","cat_id":"76","pay_type":"1","pay_content":"10/天","position":"沂新线","grounds_address":null,"longitude":"118.352087","latitude":"35.287276","name":"湖库","distance":19.06},{"f_gid":"108","g_name":"小杏花水库","g_image":"/data/upload/2018-01-31/1517386166613.jpeg","cat_id":"76","pay_type":"1","pay_content":"15/天","position":"汪沟镇","grounds_address":null,"longitude":"118.289043","latitude":"35.286713","name":"湖库","distance":20.44},{"f_gid":"111","g_name":"大杏花水库","g_image":"/data/upload/2018-01-31/1517386509792.jpeg","cat_id":"76","pay_type":"1","pay_content":"10/天","position":"汪沟镇","grounds_address":null,"longitude":"118.296500","latitude":"35.295666","name":"湖库","distance":21.13},{"f_gid":"116","g_name":"施家庄水库","g_image":"/data/upload/2018-01-31/1517387265496.jpeg","cat_id":"76","pay_type":"2","pay_content":"","position":"李官镇","grounds_address":null,"longitude":"118.360800","latitude":"35.327644","name":"湖库","distance":23.47},{"f_gid":"71","g_name":"沭河大桥","g_image":"/data/upload/2018-01-31/1517370478912.jpeg","cat_id":"77","pay_type":"2","pay_content":"","position":"白旄镇","grounds_address":null,"longitude":"118.558171","latitude":"34.968222","name":"江河","distance":23.51}]
     */

    private String f_gid;
    private String g_name;
    private String g_image;
    private String cat_id;
    private String fishcats;
    private String pay_type;
    private String cityid;
    private String pay_content;
    private String fishseat;
    private String carparks;
    private String g_intro;
    private String position;
    private String ground_phone;
    private String grounds_address;
    private String longitude;
    private String latitude;
    private String name;
    private String fish_type;
    private int is_follow;
    private List<ImagesBean> images;
    private List<FishForumBean> fish_forum;
    private List<FishDianpingBean> fish_dianping;
    private List<FishListBean> fish_list;

    public String getF_gid() {
        return f_gid;
    }

    public void setF_gid(String f_gid) {
        this.f_gid = f_gid;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public String getG_image() {
        return g_image;
    }

    public void setG_image(String g_image) {
        this.g_image = g_image;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getFishcats() {
        return fishcats;
    }

    public void setFishcats(String fishcats) {
        this.fishcats = fishcats;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getPay_content() {
        return pay_content;
    }

    public void setPay_content(String pay_content) {
        this.pay_content = pay_content;
    }

    public String getFishseat() {
        return fishseat;
    }

    public void setFishseat(String fishseat) {
        this.fishseat = fishseat;
    }

    public String getCarparks() {
        return carparks;
    }

    public void setCarparks(String carparks) {
        this.carparks = carparks;
    }

    public String getG_intro() {
        return g_intro;
    }

    public void setG_intro(String g_intro) {
        this.g_intro = g_intro;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGround_phone() {
        return ground_phone;
    }

    public void setGround_phone(String ground_phone) {
        this.ground_phone = ground_phone;
    }

    public String getGrounds_address() {
        return grounds_address;
    }

    public void setGrounds_address(String grounds_address) {
        this.grounds_address = grounds_address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFish_type() {
        return fish_type;
    }

    public void setFish_type(String fish_type) {
        this.fish_type = fish_type;
    }

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public List<FishForumBean> getFish_forum() {
        return fish_forum;
    }

    public void setFish_forum(List<FishForumBean> fish_forum) {
        this.fish_forum = fish_forum;
    }

    public List<FishDianpingBean> getFish_dianping() {
        return fish_dianping;
    }

    public void setFish_dianping(List<FishDianpingBean> fish_dianping) {
        this.fish_dianping = fish_dianping;
    }

    public List<FishListBean> getFish_list() {
        return fish_list;
    }

    public void setFish_list(List<FishListBean> fish_list) {
        this.fish_list = fish_list;
    }

    public static class ImagesBean {
        /**
         * img_url : /data/upload/2018-03-15/1521100632869.png
         */

        private String img_url;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }

    public static class FishDianpingBean {
        /**
         * dp_id : 28
         * score : 3
         * addtime : 1526117696
         * intro : Asdasdasdasasdasdasd
         * member_list_id : 379
         * member_list_headpic : /data/upload/avatar/avator.png
         * member_list_nickname : 钓鱼吧0460
         * content : [{"id":"16","str_content":"Asdasdasdasasdasdasd","str_imgs":"/data/upload/2018-05-12/5af6b540b3d09.jpeg","dp_id":"28"}]
         */

        private String dp_id;
        private String score;
        private String addtime;
        private String intro;
        private String member_list_id;
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

        public String getMember_list_id() {
            return member_list_id;
        }

        public void setMember_list_id(String member_list_id) {
            this.member_list_id = member_list_id;
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
             * id : 16
             * str_content : Asdasdasdasasdasdasd
             * str_imgs : /data/upload/2018-05-12/5af6b540b3d09.jpeg
             * dp_id : 28
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

    public static class FishListBean {
        /**
         * f_gid : 117
         * g_name : 后孙沟水库
         * g_image : /data/upload/2018-01-31/1517387355274.jpeg
         * cat_id : 76
         * pay_type : 1
         * pay_content : 10/天
         * position : 沂新线
         * grounds_address : null
         * longitude : 118.352087
         * latitude : 35.287276
         * name : 湖库
         * distance : 19.06
         */

        private String f_gid;
        private String g_name;
        private String g_image;
        private String cat_id;
        private String pay_type;
        private String pay_content;
        private String position;
        private String grounds_address;
        private String longitude;
        private String latitude;
        private String name;
        private String distance;

        public String getF_gid() {
            return f_gid;
        }

        public void setF_gid(String f_gid) {
            this.f_gid = f_gid;
        }

        public String getG_name() {
            return g_name;
        }

        public void setG_name(String g_name) {
            this.g_name = g_name;
        }

        public String getG_image() {
            return g_image;
        }

        public void setG_image(String g_image) {
            this.g_image = g_image;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPay_content() {
            return pay_content;
        }

        public void setPay_content(String pay_content) {
            this.pay_content = pay_content;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getGrounds_address() {
            return grounds_address;
        }

        public void setGrounds_address(String grounds_address) {
            this.grounds_address = grounds_address;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
    /**
     * f_gid : 9
     * g_name : 钓场名称
     * g_image : /data/upload/2017-12-07/1512635612596.jpg
     * cat_id : 74
     * pay_type :
     * pay_content :
     * fishseat : 1
     * carparks : 2
     * g_intro : 头嘟嘟嘟
     * ground_phone : 18518328663
     * grounds_address : 详细地址
     * longitude : 114.52297
     * latitude : 38.021264
     * name : 斤塘
     * images : [{"img_url":""}]
     * fish_forum : [{"member_list_id":"","title":"","thumb_img":"","is_tuijian":"","is_jinghua":"","member_list_headpic":"","member_list_nickname":"","str_content":""}]
     * is_follow : 0
     * fish_list : [{"f_gid":"1","g_name":"追风钓场","g_image":"/data/upload/carphoto/timg.jpg,/data/upload/carphoto/timg2.jpg,","cat_id":"73","pay_type":"1","pay_content":"100/天","grounds_address":"花鸟鱼虫市场","longitude":"114.463","latitude":"38.0284","name":"黑坑","distance":12138.04},{"f_gid":"2","g_name":"黑人钓场","g_image":"/data/upload/carphoto/timg.jpg,/data/upload/carphoto/timg2.jpg,","cat_id":"73","pay_type":"1","pay_content":"100/天","grounds_address":"花鸟鱼虫市场","longitude":"114.463","latitude":"38.0284","name":"黑坑","distance":12138.04},{"f_gid":"5","g_name":"钓场名称","g_image":null,"cat_id":"74","pay_type":"1","pay_content":"12","grounds_address":"详细地址","longitude":"114.369","latitude":"11.45","name":"斤塘","distance":12674.12},{"f_gid":"6","g_name":"钓场名称","g_image":null,"cat_id":"74","pay_type":"1","pay_content":"12","grounds_address":"详细地址","longitude":"114.369","latitude":"11.45","name":"斤塘","distance":12674.12},{"f_gid":"7","g_name":"钓场名称","g_image":"/data/upload/carphoto/timg2.jpg","cat_id":"74","pay_type":"1","pay_content":"12/斤","grounds_address":"详细地址","longitude":"114.369","latitude":"11.45","name":"斤塘","distance":12674.12}]
     */


//    private String f_gid;
//    private String g_name;
//    private String g_image;
//    private String cat_id;
//    private String pay_type;
//    private String pay_content;
//    private String fishseat;
//    private String carparks;
//    private String g_intro;
//    private String position;
//    private String ground_phone;
//    private String grounds_address;
//    private String longitude;
//    private String latitude;
//    private String name;
//    private String fish_type;
//    private int is_follow;
//    private List<ImagesBean> images;
//    private List<FishForumBean> fish_forum;
//    private List<FishListBean> fish_list;
//
//    private List<FishDianpingBean> fish_dp;
//
//    public List<FishDianpingBean> getFish_dp() {
//        return fish_dp;
//    }
//
//    public void setFish_dp(List<FishDianpingBean> fish_dp) {
//        this.fish_dp = fish_dp;
//    }
//
//    public String getF_gid() {
//        return f_gid;
//    }
//
//    public void setF_gid(String f_gid) {
//        this.f_gid = f_gid;
//    }
//
//    public String getG_name() {
//        return g_name;
//    }
//
//    public void setG_name(String g_name) {
//        this.g_name = g_name;
//    }
//
//    public String getG_image() {
//        return g_image;
//    }
//
//    public void setG_image(String g_image) {
//        this.g_image = g_image;
//    }
//
//    public String getCat_id() {
//        return cat_id;
//    }
//
//    public void setCat_id(String cat_id) {
//        this.cat_id = cat_id;
//    }
//
//    public String getPay_type() {
//        return pay_type;
//    }
//
//    public void setPay_type(String pay_type) {
//        this.pay_type = pay_type;
//    }
//
//    public String getPay_content() {
//        return pay_content;
//    }
//
//    public void setPay_content(String pay_content) {
//        this.pay_content = pay_content;
//    }
//
//    public String getFishseat() {
//        return fishseat;
//    }
//
//    public void setFishseat(String fishseat) {
//        this.fishseat = fishseat;
//    }
//
//    public String getCarparks() {
//        return carparks;
//    }
//
//    public void setCarparks(String carparks) {
//        this.carparks = carparks;
//    }
//
//    public String getG_intro() {
//        return g_intro;
//    }
//
//    public void setG_intro(String g_intro) {
//        this.g_intro = g_intro;
//    }
//
//    public String getPosition() {
//        return position;
//    }
//
//    public void setPosition(String position) {
//        this.position = position;
//    }
//
//    public String getGround_phone() {
//        return ground_phone;
//    }
//
//    public void setGround_phone(String ground_phone) {
//        this.ground_phone = ground_phone;
//    }
//
//    public String getGrounds_address() {
//        return grounds_address;
//    }
//
//    public void setGrounds_address(String grounds_address) {
//        this.grounds_address = grounds_address;
//    }
//
//    public String getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(String longitude) {
//        this.longitude = longitude;
//    }
//
//    public String getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(String latitude) {
//        this.latitude = latitude;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getFish_type() {
//        return fish_type;
//    }
//
//    public void setFish_type(String fish_type) {
//        this.fish_type = fish_type;
//    }
//
//    public int getIs_follow() {
//        return is_follow;
//    }
//
//    public void setIs_follow(int is_follow) {
//        this.is_follow = is_follow;
//    }
//
//    public List<ImagesBean> getImages() {
//        return images;
//    }
//
//    public void setImages(List<ImagesBean> images) {
//        this.images = images;
//    }
//
//    public List<FishForumBean> getFish_forum() {
//        return fish_forum;
//    }
//
//    public void setFish_forum(List<FishForumBean> fish_forum) {
//        this.fish_forum = fish_forum;
//    }
//
//    public List<FishListBean> getFish_list() {
//        return fish_list;
//    }
//
//    public void setFish_list(List<FishListBean> fish_list) {
//        this.fish_list = fish_list;
//    }
//
//    public static class ImagesBean implements Serializable {
//        /**
//         * img_url :
//         */
//
//        private String img_url;
//
//        public String getImg_url() {
//            return img_url;
//        }
//
//        public void setImg_url(String img_url) {
//            this.img_url = img_url;
//        }
//    }
//
//    public static class FishForumBean {
//        /**
//         * member_list_id :
//         * title :
//         * thumb_img :
//         * is_tuijian :
//         * is_jinghua :
//         * member_list_headpic :
//         * member_list_nickname :
//         * str_content :
//         */
//        private String f_id;
//        private String member_list_id;
//        private String title;
//        private String thumb_img;
//        private String is_tuijian;
//        private String is_jinghua;
//        private String member_list_headpic;
//        private String member_list_nickname;
//        private String str_content;
//
//        public String getMember_list_id() {
//            return member_list_id;
//        }
//
//        public String getF_id() {
//            return f_id;
//        }
//
//        public void setMember_list_id(String member_list_id) {
//            this.member_list_id = member_list_id;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getThumb_img() {
//            return thumb_img;
//        }
//
//        public void setThumb_img(String thumb_img) {
//            this.thumb_img = thumb_img;
//        }
//
//        public String getIs_tuijian() {
//            return is_tuijian;
//        }
//
//        public void setIs_tuijian(String is_tuijian) {
//            this.is_tuijian = is_tuijian;
//        }
//
//        public String getIs_jinghua() {
//            return is_jinghua;
//        }
//
//        public void setIs_jinghua(String is_jinghua) {
//            this.is_jinghua = is_jinghua;
//        }
//
//        public String getMember_list_headpic() {
//            return member_list_headpic;
//        }
//
//        public void setMember_list_headpic(String member_list_headpic) {
//            this.member_list_headpic = member_list_headpic;
//        }
//
//        public String getMember_list_nickname() {
//            return member_list_nickname;
//        }
//
//        public void setMember_list_nickname(String member_list_nickname) {
//            this.member_list_nickname = member_list_nickname;
//        }
//
//        public String getStr_content() {
//            return str_content;
//        }
//
//        public void setStr_content(String str_content) {
//            this.str_content = str_content;
//        }
//    }
//
//    public static class FishListBean {
//        /**
//         * f_gid : 1
//         * g_name : 追风钓场
//         * g_image : /data/upload/carphoto/timg.jpg,/data/upload/carphoto/timg2.jpg,
//         * cat_id : 73
//         * pay_type : 1
//         * pay_content : 100/天
//         * grounds_address : 花鸟鱼虫市场
//         * longitude : 114.463
//         * latitude : 38.0284
//         * name : 黑坑
//         * distance : 12138.04
//         */
//
//        private String f_gid;
//        private String g_name;
//        private String g_image;
//        private String cat_id;
//        private String pay_type;
//        private String pay_content;
//        private String grounds_address;
//        private String longitude;
//        private String latitude;
//        private String name;
//        private double distance;
//
//        public String getF_gid() {
//            return f_gid;
//        }
//
//        public void setF_gid(String f_gid) {
//            this.f_gid = f_gid;
//        }
//
//        public String getG_name() {
//            return g_name;
//        }
//
//        public void setG_name(String g_name) {
//            this.g_name = g_name;
//        }
//
//        public String getG_image() {
//            return g_image;
//        }
//
//        public void setG_image(String g_image) {
//            this.g_image = g_image;
//        }
//
//        public String getCat_id() {
//            return cat_id;
//        }
//
//        public void setCat_id(String cat_id) {
//            this.cat_id = cat_id;
//        }
//
//        public String getPay_type() {
//            return pay_type;
//        }
//
//        public void setPay_type(String pay_type) {
//            this.pay_type = pay_type;
//        }
//
//        public String getPay_content() {
//            return pay_content;
//        }
//
//        public void setPay_content(String pay_content) {
//            this.pay_content = pay_content;
//        }
//
//        public String getGrounds_address() {
//            return grounds_address;
//        }
//
//        public void setGrounds_address(String grounds_address) {
//            this.grounds_address = grounds_address;
//        }
//
//        public String getLongitude() {
//            return longitude;
//        }
//
//        public void setLongitude(String longitude) {
//            this.longitude = longitude;
//        }
//
//        public String getLatitude() {
//            return latitude;
//        }
//
//        public void setLatitude(String latitude) {
//            this.latitude = latitude;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public double getDistance() {
//            return distance;
//        }
//
//        public void setDistance(double distance) {
//            this.distance = distance;
//        }
//    }
//
//    public static class FishDianpingBean {
//        /**
//         * "dp_id": "26",
//         * "score": "1",
//         * "addtime": "1526019382",
//         * "member_list_id": "379",
//         * "member_list_headpic": "/data/upload/avatar/avator.png",
//         * "member_list_nickname": "钓鱼吧0460",
//         * "str_content": null
//         */
//        private String dp_id;
//        private String score;
//        private String addtime;
//        private String member_list_id;
//        private String member_list_headpic;
//        private String member_list_nickname;
//        private String str_content;
//
//        public String getDp_id() {
//            return dp_id;
//        }
//
//        public void setDp_id(String dp_id) {
//            this.dp_id = dp_id;
//        }
//
//        public String getScore() {
//            return score;
//        }
//
//        public void setScore(String score) {
//            this.score = score;
//        }
//
//        public String getAddtime() {
//            return addtime;
//        }
//
//        public void setAddtime(String addtime) {
//            this.addtime = addtime;
//        }
//
//        public String getMember_list_id() {
//            return member_list_id;
//        }
//
//        public void setMember_list_id(String member_list_id) {
//            this.member_list_id = member_list_id;
//        }
//
//        public String getMember_list_headpic() {
//            return member_list_headpic;
//        }
//
//        public void setMember_list_headpic(String member_list_headpic) {
//            this.member_list_headpic = member_list_headpic;
//        }
//
//        public String getMember_list_nickname() {
//            return member_list_nickname;
//        }
//
//        public void setMember_list_nickname(String member_list_nickname) {
//            this.member_list_nickname = member_list_nickname;
//        }
//
//        public String getStr_content() {
//            return str_content;
//        }
//
//        public void setStr_content(String str_content) {
//            this.str_content = str_content;
//        }
//    }
}
