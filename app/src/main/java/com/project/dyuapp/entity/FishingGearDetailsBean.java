package com.project.dyuapp.entity;

import java.util.List;

/**
 * Created by ${田亭亭} on 2017/12/9 0009.
 *
 * @description
 * @change
 */


public class FishingGearDetailsBean {


    /**
     * fishing_shop : {"fishing_shop_id":"2","shop_name":"上官渔具","shop_image":"/data/upload/2017-09-07/59b111a1906ec.jpg","shop_intro":"渔具很好","count":"0","img_count":"22","star":0,"is_collect":2}
     * imgs : [{"img_url":"data"}]
     * comments : [{"member_list_nickname":"熊三","member_list_headpic":"/public/img/mystery.png","c_content":"评论内容11评论内容11评论内容11评论内容11评论内容11评论内容11","createtime":"1508551694"}]
     * ambitus : [{"fishing_shop_id":"2","shop_name":"上官渔具","shop_image":"/data/upload/2017-09-07/59b111a1906ec.jpg","shop_intro":"渔具很好","img_count":"22","star":0}]
     */

    private FishingShopBean fishing_shop;
    private List<ImgsBean> imgs;
    private List<CommentsBean> comments;
    private List<AmbitusBean> ambitus;

    public FishingShopBean getFishing_shop() {
        return fishing_shop;
    }

    public void setFishing_shop(FishingShopBean fishing_shop) {
        this.fishing_shop = fishing_shop;
    }

    public List<ImgsBean> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImgsBean> imgs) {
        this.imgs = imgs;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public List<AmbitusBean> getAmbitus() {
        return ambitus;
    }

    public void setAmbitus(List<AmbitusBean> ambitus) {
        this.ambitus = ambitus;
    }

    public class FishingShopBean {
        /**
         * fishing_shop_id : 2
         * shop_name : 上官渔具
         * shop_image : /data/upload/2017-09-07/59b111a1906ec.jpg
         * shop_intro : 渔具很好
         * count : 0
         * img_count : 22
         * star : 0
         * is_collect : 2
         */

        private String fishing_shop_id;
        private String shop_name;
        private String shop_image;
        private String shop_intro;
        private String count;
        private String imgs_count;
        private String star;
        private int is_collect;
        private String shop_phone;
        private String provinceid;
        private String cityid;
        private String countyid;
        private String shop_address;
        private String longitude;
        private String latitude;

        public String getFishing_shop_id() {
            return fishing_shop_id;
        }

        public void setFishing_shop_id(String fishing_shop_id) {
            this.fishing_shop_id = fishing_shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_image() {
            return shop_image;
        }

        public void setShop_image(String shop_image) {
            this.shop_image = shop_image;
        }

        public String getShop_intro() {
            return shop_intro;
        }

        public void setShop_intro(String shop_intro) {
            this.shop_intro = shop_intro;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getImg_count() {
            return imgs_count;
        }

        public void setImg_count(String img_count) {
            this.imgs_count = img_count;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }

        public String getShop_phone() {
            return shop_phone;
        }

        public void setShop_phone(String shop_phone) {
            this.shop_phone = shop_phone;
        }

        public String getProvinceid() {
            return provinceid;
        }

        public void setProvinceid(String provinceid) {
            this.provinceid = provinceid;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getCountyid() {
            return countyid;
        }

        public void setCountyid(String countyid) {
            this.countyid = countyid;
        }

        public String getShop_address() {
            return shop_address;
        }

        public void setShop_address(String shop_address) {
            this.shop_address = shop_address;
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
    }

    public class ImgsBean {
        /**
         * img_url : data
         */

        private String img_url;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
    }

    public class CommentsBean {
        /**
         * member_list_nickname : 熊三
         * member_list_headpic : /public/img/mystery.png
         * c_content : 评论内容11评论内容11评论内容11评论内容11评论内容11评论内容11
         * createtime : 1508551694
         */

        private String member_list_nickname;
        private String member_list_headpic;
        private String c_content;
        private String createtime;
        private String c_codes;

        public String getC_codes() {
            return c_codes;
        }

        public void setC_codes(String c_codes) {
            this.c_codes = c_codes;
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

        public String getC_content() {
            return c_content;
        }

        public void setC_content(String c_content) {
            this.c_content = c_content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }

    public class AmbitusBean {
        /**
         * fishing_shop_id : 2
         * shop_name : 上官渔具
         * shop_image : /data/upload/2017-09-07/59b111a1906ec.jpg
         * shop_intro : 渔具很好
         * img_count : 22
         * star : 0
         */

        private String fishing_shop_id;
        private String shop_name;
        private String shop_image;
        private String shop_intro;
        private String img_count;
        private int star;
        private String longitude;
        private String latitude;
        private String juli;
        private String provinceid;
        private String cityid;
        private String countyid;
        private String shop_address;
        private String address;

        public String getFishing_shop_id() {
            return fishing_shop_id;
        }

        public void setFishing_shop_id(String fishing_shop_id) {
            this.fishing_shop_id = fishing_shop_id;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_image() {
            return shop_image;
        }

        public void setShop_image(String shop_image) {
            this.shop_image = shop_image;
        }

        public String getShop_intro() {
            return shop_intro;
        }

        public void setShop_intro(String shop_intro) {
            this.shop_intro = shop_intro;
        }

        public String getImg_count() {
            return img_count;
        }

        public void setImg_count(String img_count) {
            this.img_count = img_count;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
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

        public String getJuli() {
            return juli;
        }

        public void setJuli(String juli) {
            this.juli = juli;
        }

        public String getProvinceid() {
            return provinceid;
        }

        public void setProvinceid(String provinceid) {
            this.provinceid = provinceid;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getCountyid() {
            return countyid;
        }

        public void setCountyid(String countyid) {
            this.countyid = countyid;
        }

        public String getShop_address() {
            return shop_address;
        }

        public void setShop_address(String shop_address) {
            this.shop_address = shop_address;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
