package com.project.dyuapp.entity;

/**
 * @describtion  首页技巧 下拉分类----------无用--------
 * @author gengqiufang
 * @created at 2017/12/2 0002
 */

public class ClassifyDownEntity {
    private String name;
    private boolean isSel;

    public ClassifyDownEntity(String name) {
        this.name = name;
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
}
