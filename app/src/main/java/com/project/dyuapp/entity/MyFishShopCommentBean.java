package com.project.dyuapp.entity;

import java.util.List;

/**
 * Created by ${田亭亭} on 2017/12/11 0011.
 *
 * @description
 * @change
 */


public class MyFishShopCommentBean {


    /**
     * c_id : 43
     * c_codes : null
     * c_content : 评论内容10
     * c_imgs : ["/public/img/mystery.png","/public/img/mystery.png","/public/img/mystery.png"]
     * createtime : 1506393592
     * is_look : 0
     * shop_name : 上官渔具5555
     * imgs : []
     */

    private String c_id;
    private String c_codes;
    private String c_content;
    private String createtime;
    private String is_look;
    private String shop_name;
    private List<String> c_imgs;
    private String fishing_shop_id;
    private String cityid;
    private String longitude;
    private String latitude;


    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_codes() {
        return c_codes;
    }

    public void setC_codes(String c_codes) {
        this.c_codes = c_codes;
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

    public String getIs_look() {
        return is_look;
    }

    public void setIs_look(String is_look) {
        this.is_look = is_look;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public List<String> getC_imgs() {
        return c_imgs;
    }

    public void setC_imgs(List<String> c_imgs) {
        this.c_imgs = c_imgs;
    }


    public String getFishing_shop_id() {
        return fishing_shop_id;
    }

    public void setFishing_shop_id(String fishing_shop_id) {
        this.fishing_shop_id = fishing_shop_id;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
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
