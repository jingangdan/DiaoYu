package com.project.dyuapp.entity;

/**
 * Created by jingang on 2018/7/13.
 */

public class VersionBean {
    /**
     * a_id : 3
     * addtime : 1531453610
     * version : 1.0.6
     * upinfo : asdasdadasdasd
     * load_url : /data/upload/app/2018-07-13/5b4820aa8606d.apk
     */
    private String a_id;
    private String addtime;
    private String version;
    private String upinfo;
    private String load_url;

    public String getA_id() {
        return a_id;
    }

    public void setA_id(String a_id) {
        this.a_id = a_id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpinfo() {
        return upinfo;
    }

    public void setUpinfo(String upinfo) {
        this.upinfo = upinfo;
    }

    public String getLoad_url() {
        return load_url;
    }

    public void setLoad_url(String load_url) {
        this.load_url = load_url;
    }
}
