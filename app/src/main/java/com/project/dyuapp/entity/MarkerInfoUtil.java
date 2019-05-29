package com.project.dyuapp.entity;

import java.io.Serializable;

/**
 * Created by ${田亭亭} on 2017/12/15 0015.
 *
 * @description
 * @change
 */


public class MarkerInfoUtil implements Serializable {
    private static final long serialVersionUID = 8633299996744734593L;

    private double latitude;//纬度
    private double longitude;//经度
    private String name;//名字
    private int imgId;//图片
    private String description;//描述
    private String type;//类型

    //构造方法
    public MarkerInfoUtil() {
    }

    public MarkerInfoUtil(double latitude, double longitude, String name, int imgId, String description) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.imgId = imgId;
        this.description = description;
    }

    public MarkerInfoUtil(double latitude, double longitude,String name, String description) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
    }

    public MarkerInfoUtil(double latitude, double longitude, String name, String description, String type) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
        this.type = type;
    }

    @Override
    public String toString() {
        return "MarkerInfoUtil{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", imgId=" + imgId +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    //getter setter
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
