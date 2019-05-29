package com.project.dyuapp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gengqiufang
 * @describtion 2 修改-发布的展示页面
 * @created at 2017/12/15 0015
 */

public class ReleaseSkillShowEntity {
    private String f_id;
    private String title;
    private String intro;
    private String thumb_img;
    private String top_id;
    private String cat_id;
    private String is_caogao;
    private String is_agree;
    private String addtime;
    private String name;
    private String cname;
    private String ground;
    private String ground_id;
    private String xiancu;
    private String diaogan_brand;
    private String erliao;
    private String yuzhong;
    private String diaofa;
    private String diaogan_long;
    private String fishing_time;
    private String ground_type;
    private List<String> nerliao=new ArrayList<>();
    private List<String> nyuzhong=new ArrayList<>();
    private List<String> ndiaofa=new ArrayList<>();
    private List<String> ndiaogan_long=new ArrayList<>();
    private List<String> nground_type=new ArrayList<>();
    private List<ContentBean> content=new ArrayList<>();

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public List<String> getNground_type() {
        return nground_type;
    }

    public String getGround_type() {
        return ground_type;
    }

    public String getGround_id() {
        return ground_id;
    }

    public String getFishing_time() {
        return fishing_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Object getThumb_img() {
        return thumb_img;
    }

    public void setThumb_img(String thumb_img) {
        this.thumb_img = thumb_img;
    }

    public String getTop_id() {
        return top_id;
    }

    public void setTop_id(String top_id) {
        this.top_id = top_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getIs_caogao() {
        return is_caogao;
    }

    public void setIs_caogao(String is_caogao) {
        this.is_caogao = is_caogao;
    }

    public String getIs_agree() {
        return is_agree;
    }

    public void setIs_agree(String is_agree) {
        this.is_agree = is_agree;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getGround() {
        return ground;
    }

    public void setGround(String ground) {
        this.ground = ground;
    }

    public String getXiancu() {
        return xiancu;
    }

    public void setXiancu(String xiancu) {
        this.xiancu = xiancu;
    }

    public String getDiaogan_brand() {
        return diaogan_brand;
    }

    public void setDiaogan_brand(String diaogan_brand) {
        this.diaogan_brand = diaogan_brand;
    }

    public String getErliao() {
        return erliao;
    }

    public void setErliao(String erliao) {
        this.erliao = erliao;
    }

    public String getYuzhong() {
        return yuzhong;
    }

    public void setYuzhong(String yuzhong) {
        this.yuzhong = yuzhong;
    }

    public String getDiaofa() {
        return diaofa;
    }

    public void setDiaofa(String diaofa) {
        this.diaofa = diaofa;
    }

    public String getDiaogan_long() {
        return diaogan_long;
    }

    public void setDiaogan_long(String diaogan_long) {
        this.diaogan_long = diaogan_long;
    }

    public List<String> getNerliao() {
        return nerliao;
    }

    public void setNerliao(List<String> nerliao) {
        this.nerliao = nerliao;
    }

    public List<String> getNyuzhong() {
        return nyuzhong;
    }

    public void setNyuzhong(List<String> nyuzhong) {
        this.nyuzhong = nyuzhong;
    }

    public List<String> getNdiaofa() {
        return ndiaofa;
    }

    public void setNdiaofa(List<String> ndiaofa) {
        this.ndiaofa = ndiaofa;
    }

    public List<String> getNdiaogan_long() {
        return ndiaogan_long;
    }

    public void setNdiaogan_long(List<String> ndiaogan_long) {
        this.ndiaogan_long = ndiaogan_long;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }
}
