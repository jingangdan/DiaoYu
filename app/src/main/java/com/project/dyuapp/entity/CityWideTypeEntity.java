package com.project.dyuapp.entity;

/**
 * @author gengqiufang
 * @describtion 同城类型
 * @created at 2017/12/2 0002
 */

public class CityWideTypeEntity {
    private int pic;
    private String title;
    private String num;

    public CityWideTypeEntity(int pic, String title) {
        this.pic = pic;
        this.title = title;
    }

    public CityWideTypeEntity(int pic, String title, String num) {
        this.pic = pic;
        this.title = title;
        this.num = num;
    }

    public int getPic() {
        return pic;
    }

    public String getTitle() {
        return title;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
