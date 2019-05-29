package com.project.dyuapp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gengqiufang
 * @describtion 帖子-详情
 * @created at 2017/12/11 0011
 */

public class ForumDetailEntity {
    private String f_id;
    private String top_id;
    private String member_list_id;//发帖人id
    private String title;//帖子标题
    private String addtime;//发帖时间
    private String member_list_nickname;//昵称
    private String member_list_headpic;//头像
    private String is_hxguanzhu;
    private String scores; //用户积分（不涉及请忽略）
    private String ground_id="";//钓场类型
    private String erliao="";//饵料类型
    private String yuzhong="";//鱼种
    private String fishing_time="";//出钓时间
    private String conment_sum;//评论总数
    private int zan_sum;//赞次数
    private String dashang_sum;//打赏次数
    private MemberListLevelBean member_list_level;//发帖人等级
    private List<ContentBean> content = new ArrayList<>();
    private List<ZanHeadpicBean> zan_headpic = new ArrayList<>();//点赞人头像
    private String guanzhu;//1关注  0未关注 2相互关注
    private String zan;//0未点赞  1已点赞
    private String shoucang;//0未收藏   1已收藏

    //打赏记录
    private List<DashangListBean> dashang_list;

    public List<DashangListBean> getDashang_list() {
        return dashang_list;
    }

    public void setDashang_list(List<DashangListBean> dashang_list) {
        this.dashang_list = dashang_list;
    }

    public String getTop_id() {
        return top_id;
    }

    public String getZan() {
        return zan;
    }

    public String getShoucang() {
        return shoucang;
    }

    public String getIs_hxguanzhu() {
        return is_hxguanzhu;
    }

    public String getF_id() {
        return f_id;
    }

    public String getMember_list_id() {
        return member_list_id;
    }

    public String getTitle() {
        return title;
    }

    public String getAddtime() {
        return addtime;
    }

    public String getMember_list_nickname() {
        return member_list_nickname;
    }

    public String getMember_list_headpic() {
        return member_list_headpic;
    }

    public String getScores() {
        return scores;
    }

    public String getGround_id() {
        return ground_id;
    }

    public String getErliao() {
        return erliao;
    }

    public String getYuzhong() {
        return yuzhong;
    }

    public String getFishing_time() {
        return fishing_time;
    }

    public String getConment_sum() {
        return conment_sum;
    }

    public int getZan_sum() {
        return zan_sum;
    }

    public String getDashang_sum() {
        return dashang_sum;
    }

    public MemberListLevelBean getMember_list_level() {
        return member_list_level;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public List<ZanHeadpicBean> getZan_headpic() {
        return zan_headpic;
    }

    public class MemberListLevelBean {
        private String member_lvl_name;
        private String member_lvl_id;

        public String getMember_lvl_name() {
            return member_lvl_name;
        }

        public String getMember_lvl_id() {
            return member_lvl_id;
        }
    }

    public class ZanHeadpicBean {
        private String member_list_headpic;

        public String getMember_list_headpic() {
            return member_list_headpic;
        }
    }

    public String getGuanzhu() {
        return guanzhu;
    }

    public static class DashangListBean {
        /**
         * member_list_headpic : /data/upload/avatar/avator.png
         * member_list_nickname : 钓鱼吧0460
         * c_id : 23
         * object_id : 368
         * member_list_id : 379
         * to_member_id : 845
         * createtime : 1522216210
         * coin_num : 1
         * object_type : 1
         * desc :
         * is_inform : 1
         * is_look : 1
         */

        private String member_list_headpic;
        private String member_list_nickname;
        private String c_id;
        private String object_id;
        private String member_list_id;
        private String to_member_id;
        private String createtime;
        private String coin_num;
        private String object_type;
        private String desc;
        private String is_inform;
        private String is_look;

        public String getMember_list_headpic() {
            return member_list_headpic;
        }

        public void setMember_list_headpic(String member_list_headpic) {
            this.member_list_headpic = member_list_headpic;
        }

        public String getMember_list_nickname() {
            return member_list_nickname;
        }

        public void setMember_list_nickname(String member_list_nickname) {
            this.member_list_nickname = member_list_nickname;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getObject_id() {
            return object_id;
        }

        public void setObject_id(String object_id) {
            this.object_id = object_id;
        }

        public String getMember_list_id() {
            return member_list_id;
        }

        public void setMember_list_id(String member_list_id) {
            this.member_list_id = member_list_id;
        }

        public String getTo_member_id() {
            return to_member_id;
        }

        public void setTo_member_id(String to_member_id) {
            this.to_member_id = to_member_id;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getCoin_num() {
            return coin_num;
        }

        public void setCoin_num(String coin_num) {
            this.coin_num = coin_num;
        }

        public String getObject_type() {
            return object_type;
        }

        public void setObject_type(String object_type) {
            this.object_type = object_type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIs_inform() {
            return is_inform;
        }

        public void setIs_inform(String is_inform) {
            this.is_inform = is_inform;
        }

        public String getIs_look() {
            return is_look;
        }

        public void setIs_look(String is_look) {
            this.is_look = is_look;
        }
    }


}


