package com.project.dyuapp.entity;

/**
 * @创建者 任伟
 * @创建时间 2017/12/07 13:55
 * @描述 ${添加钓场实体}
 */

public class ContentFishingEntity {
    private String shenfen;//发布钓场会员身份 1钓场老板 2钓鱼人
    private String g_name;//钓场名称
    private String cat_id;//钓场类型id
    private String fishcats;//多选鱼种id 注前后都用，拼接如,1,2,3,4,
    private String pay_type;//收费方式  1收费(按斤塘收费)  2免费   3收费(按天塘收费)
    private String pay_content;//收费详情  例 50元
    private String fishseat;//钓位数量
    private String carparks;//车位数量
    private String g_intro;//钓场简介
    private String ground_phone;//钓场电话
    private String provinceid;//省id
    private String cityid;//市id
    private String countyid;//所在区县ID
    private String grounds_address;//钓场具体位置
    private String longitude;//经度
    private String latitude;//纬度
    private String address;//地址

    public String getShenfen() {
        return shenfen;
    }

    public void setShenfen(String shenfen) {
        this.shenfen = shenfen;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
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

    public String getGround_phone() {
        return ground_phone;
    }

    public void setGround_phone(String ground_phone) {
        this.ground_phone = ground_phone;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
