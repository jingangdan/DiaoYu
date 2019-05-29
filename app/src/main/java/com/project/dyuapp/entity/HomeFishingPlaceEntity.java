package com.project.dyuapp.entity;

/**
 * @创建者 任伟
 * @创建时间 2017/12/09 14:57
 * @描述 ${}
 */

public class HomeFishingPlaceEntity {

    /**
     * f_gid : 1
     * g_name : 追风钓场
     * cat_id : 73
     * g_image : /data/upload/carphoto/timg.jpg,/data/upload/carphoto/timg2.jpg,
     * pay_type : 1
     * pay_content : 100/天
     * grounds_address : 花鸟鱼虫市场
     * longitude : 114.463
     * latitude : 38.0284
     * name : 黑坑
     * distance : 5.32
     */

    private String f_gid;
    private String g_name;
    private String cat_id;
    private String g_image;
    private String pay_type;
    private String pay_content;
    private String grounds_address;
    private String longitude;
    private String latitude;
    private String name;
    private double distance;
    private String catname;
    private String juli;
    private String position;
    /**
     * provinceid : 130000
     * cityid : 130100
     * countyid : 130105
     * juli : 1.28
     * address : 河北省石家庄市新华区北二环
     */

    private String provinceid;
    private String cityid;
    private String countyid;
    private String address;

    private int dp_score;

    public int getDp_score() {
        return dp_score;
    }

    public void setDp_score(int dp_score) {
        this.dp_score = dp_score;
    }

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

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getG_image() {
        return g_image;
    }

    public void setG_image(String g_image) {
        this.g_image = g_image;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getJuli() {
        return juli;
    }

    public void setJuli(String juli) {
        this.juli = juli;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
