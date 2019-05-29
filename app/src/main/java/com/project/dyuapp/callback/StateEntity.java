package com.project.dyuapp.callback;

/**
 * @author gengqiufang
 * @describtion
 * @created at 2017/12/9 0009
 */

public class StateEntity {
    private String state;

    private String total;//发帖总数
    private String today;//今日发帖数量

    private int pagenum;//发私信 总条数

    public String getTotal() {
        return total;
    }

    public String getToday() {
        return today;
    }

    private int is_dianzan;//当前点赞状态：1.已点赞；2.未点赞

    public int getIs_dianzan() {
        return is_dianzan;
    }

    public void setIs_dianzan(int is_dianzan) {
        this.is_dianzan = is_dianzan;
    }

    private int is_follow;//当前关注状态：1.已关注/已收藏；2.未关注/未收藏

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public int getPagenum() {
        return pagenum;
    }
}
