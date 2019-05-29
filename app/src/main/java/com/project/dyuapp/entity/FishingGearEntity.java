package com.project.dyuapp.entity;

/**
 * Created by ${田亭亭} on 2017/12/8 0008.
 *
 * @description
 * @change
 */


public class FishingGearEntity {


    /**
     * fishing_shop_id : 2
     * shop_name : 上官渔具5555
     * shop_image : /data/upload/2017-09-07/59b111a1906ec.jpg
     * longitude : 114.514
     * latitude : 38.0481
     * provinceid : 130000
     * cityid : 130100
     * countyid : 130133
     * shop_address : 怀特1
     * juli : 0km
     * star : 0
     * address : 河北省石家庄市赵县怀特1
     */

    private String fishing_shop_id;
    private String shop_name;
    private String shop_image;//首页图片
    private String star;//首页等级
    private String longitude;
    private String latitude;
    private String provinceid;
    private String cityid;
    private String countyid;
    private String shop_address;
    private String juli;
    private String address;
    private String shop_thumb;//同城图片
    private int level;//同城等级

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

    public String getJuli() {
        return juli;
    }

    public void setJuli(String juli) {
        this.juli = juli;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShop_thumb() {
        return shop_thumb;
    }

    public void setShop_thumb(String shop_thumb) {
        this.shop_thumb = shop_thumb;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
