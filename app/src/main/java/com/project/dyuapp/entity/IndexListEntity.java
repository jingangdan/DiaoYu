package com.project.dyuapp.entity;

import java.util.List;

/**
 * @author gengqiufang
 * @describtion 技巧，饵料首页数据
 * @created at 2017/12/15 0015
 */

public class IndexListEntity {
    private List<ForumEntity> oneList;
    private List<ForumEntity> senList;

    public List<ForumEntity> getOneList() {
        return oneList;
    }

    public List<ForumEntity> getSenList() {
        return senList;
    }
}
