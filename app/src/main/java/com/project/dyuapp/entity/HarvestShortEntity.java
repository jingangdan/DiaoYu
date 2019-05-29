package com.project.dyuapp.entity;

import java.util.List;

/**
 * @创建者 任伟
 * @创建时间 2017/12/18 14:52
 * @描述 ${}
 */

public class HarvestShortEntity {

    /**
     * article_id : 29
     * userid : null
     * title : 自拍-逗比恶搞2
     * thumb : https://vthumb.ykimg.com/054101015A3F98918B7B44A088D69E8B
     * content : XMzI1OTQwNzU5Mg==
     * cc_id : ,8,
     * c_id : ,43,
     * readtimes : 37
     * is_tuijian : 1
     * countyid : null
     * introduction : 方法是大事都发送方式发送到
     * addtime : 1514188867
     * video_status : 2
     * refuse_desc :
     * cate_name : ["自拍"]
     * comments_num : 5
     * zan_num : 2
     * is_zan : 2
     */

    private String article_id;
    private Object userid;
    private String title;
    private String thumb;
    private String content;
    private String cc_id;
    private String c_id;
    private String readtimes;
    private String is_tuijian;
    private Object countyid;
    private String introduction;
    private String addtime;
    private String video_status;
    private String refuse_desc;
    private String comments_num;
    private String zan_num;
    private int is_zan;
    private List<String> cate_name;

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public Object getUserid() {
        return userid;
    }

    public void setUserid(Object userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCc_id() {
        return cc_id;
    }

    public void setCc_id(String cc_id) {
        this.cc_id = cc_id;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getReadtimes() {
        return readtimes;
    }

    public void setReadtimes(String readtimes) {
        this.readtimes = readtimes;
    }

    public String getIs_tuijian() {
        return is_tuijian;
    }

    public void setIs_tuijian(String is_tuijian) {
        this.is_tuijian = is_tuijian;
    }

    public Object getCountyid() {
        return countyid;
    }

    public void setCountyid(Object countyid) {
        this.countyid = countyid;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getVideo_status() {
        return video_status;
    }

    public void setVideo_status(String video_status) {
        this.video_status = video_status;
    }

    public String getRefuse_desc() {
        return refuse_desc;
    }

    public void setRefuse_desc(String refuse_desc) {
        this.refuse_desc = refuse_desc;
    }

    public String getComments_num() {
        return comments_num;
    }

    public void setComments_num(String comments_num) {
        this.comments_num = comments_num;
    }

    public String getZan_num() {
        return zan_num;
    }

    public void setZan_num(String zan_num) {
        this.zan_num = zan_num;
    }

    public int getIs_zan() {
        return is_zan;
    }

    public void setIs_zan(int is_zan) {
        this.is_zan = is_zan;
    }

    public List<String> getCate_name() {
        return cate_name;
    }

    public void setCate_name(List<String> cate_name) {
        this.cate_name = cate_name;
    }
}
