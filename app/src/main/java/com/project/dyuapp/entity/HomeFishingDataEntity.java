package com.project.dyuapp.entity;

/**
 * @创建者 任伟
 * @创建时间 2017/12/09 13:40
 * @描述 ${}
 */

public class HomeFishingDataEntity {
    private String city_name = "";//市级名称
    private String longitude = "";//经度
    private String latitude = "";//纬度
    private String order = "";//显示排序方式  // 1 离我最近  2人气最高 3渔获贴数由多到少  4渔获贴数由少到多
    private String countyid = "";//区及id
    private String cat_id = "";//钓场类型
    private String pay_type = "";//收费类型 1收费  2免费
    private String page = "";

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getCountyid() {
        return countyid;
    }

    public void setCountyid(String countyid) {
        this.countyid = countyid;
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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
