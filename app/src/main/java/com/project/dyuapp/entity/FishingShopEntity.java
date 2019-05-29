package com.project.dyuapp.entity;

/**
 * @创建者 任伟
 * @创建时间 2017/12/08 08:57
 * @描述 ${}
 */

public class FishingShopEntity {
    private String shop_name;//渔具店名称
    private String shop_phone;//渔具店电话
    private String shop_intro;//渔具店简介
    private String tv_osition;//选择地址
    private String shop_address;//详细地址
    private String longitude;//经度
    private String latitude;//纬度
    private String shenfen;//身份 ：1渔具店老板2钓鱼人
    private String province;//省
    private String city;//市
    private String county;//县

    public String getTv_osition() {
        return tv_osition;
    }

    public void setTv_osition(String tv_osition) {
        this.tv_osition = tv_osition;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_phone() {
        return shop_phone;
    }

    public void setShop_phone(String shop_phone) {
        this.shop_phone = shop_phone;
    }

    public String getShop_intro() {
        return shop_intro;
    }

    public void setShop_intro(String shop_intro) {
        this.shop_intro = shop_intro;
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

    public String getShenfen() {
        return shenfen;
    }

    public void setShenfen(String shenfen) {
        this.shenfen = shenfen;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
}
