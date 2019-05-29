package com.project.dyuapp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gengqiufang
 * @describtion
 * @created at 2017/12/11 0011
 */

public class CityWideEntity {
    private String dyNum;//钓友数量
    private String dcNum;//钓场数量
    private String yjdNum;//渔具店数量
    private WeatherBean weather=new WeatherBean();
    private List<ForumEntity> dongtai=new ArrayList<>();

    public String getDyNum() {
        return dyNum;
    }

    public String getDcNum() {
        return dcNum;
    }

    public String getYjdNum() {
        return yjdNum;
    }

    public WeatherBean getWeather() {
        return weather;
    }

    public List<ForumEntity> getDongtai() {
        return dongtai;
    }

    public class WeatherBean {
        private String location;//天气地址
        private String tmp_max;//最大温度
        private String tmp_min;//最小温度
        private String cond_txt_d;//天气情况

        public String getLocation() {
            return location;
        }

        public String getTmp_max() {
            return tmp_max;
        }

        public String getTmp_min() {
            return tmp_min;
        }

        public String getCond_txt_d() {
            return cond_txt_d;
        }
    }
}
