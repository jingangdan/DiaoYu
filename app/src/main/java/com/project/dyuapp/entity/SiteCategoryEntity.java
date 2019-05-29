package com.project.dyuapp.entity;

/**
 * @author gengqiufang
 * @describtion 4站点分类 集合接口
 * @created at 2017/12/9 0009
 */

public class SiteCategoryEntity {
    private String d_id; //ID
    private String name; //名称
    private boolean isSel;
    private String parent_id;

    public SiteCategoryEntity(String d_id, String name) {
        this.d_id = d_id;
        this.name = name;
    }
    public SiteCategoryEntity(String d_id, String name,boolean isSel) {
        this.d_id = d_id;
        this.name = name;
        this.isSel = isSel;
    }
    public String getD_id() {
        return d_id;
    }

    public String getName() {
        return name;
    }

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }
}
