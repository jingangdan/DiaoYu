package com.project.dyuapp.entity;

/**
 * Created by ${田亭亭} on 2017/12/9 0009.
 *
 * @description
 * @change
 */


public class FishingGearAreaBean {


        /**
         * cityid : 130102
         * name : 长安区
         */

        private String cityid;
        private String name;

    public FishingGearAreaBean(String cityid, String name) {
        this.cityid = cityid;
        this.name = name;
    }

    public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
}
