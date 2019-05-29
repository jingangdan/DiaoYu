package com.project.dyuapp.entity;

/**
 * @author gengqiufang
 * @describtion 发私信历史记录
 * @created at 2017/12/16 0016
 */

public class LetterHistoryEntity {
    private String message_id; //聊天记录ID
    private String object_id;
    private String from_member__id;//本人ID
    private String to_member_id;//聊天对象ID
    private String desc; //聊天内容
    private String object_type;
    private String createtime; //聊天时间
    private String is_look;
    private String is_new; //是否是当天第一条记录   1是  0否
    private String form_nickname; //本人昵称
    private String form_headpic; //本人头像
    private String to_nickname;//聊天对象昵称
    private String to_headpic;//聊天对象头像

    public String getMessage_id() {
        return message_id;
    }

    public String getObject_id() {
        return object_id;
    }

    public String getFrom_member__id() {
        return from_member__id;
    }

    public String getTo_member_id() {
        return to_member_id;
    }

    public String getDesc() {
        return desc;
    }

    public String getObject_type() {
        return object_type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public String getIs_look() {
        return is_look;
    }

    public String getIs_new() {
        return is_new;
    }

    public String getForm_nickname() {
        return form_nickname;
    }

    public String getForm_headpic() {
        return form_headpic;
    }

    public String getTo_nickname() {
        return to_nickname;
    }

    public String getTo_headpic() {
        return to_headpic;
    }
}
