package com.project.dyuapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.dyuapp.R;
import com.project.dyuapp.activity.SkillDetailsActivity;
import com.project.dyuapp.entity.MyReplyBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyDateUtils;

import java.util.List;

/**
 * @author litongtong
 * @created on 2017/11/28 14:31
 * @description 个人中心-我的消息-回复列表适配器
 * @change ${}
 */

public class ReplyAdapter extends CommonAdapter<MyReplyBean>{


    /**
     * 构造函数
     *
     * @param context   上下文
     * @param data      显示的数据
     * @param layout_id listview使用的条目的布局文件id
     */
    public ReplyAdapter(Context context, List<MyReplyBean> data, int layout_id) {
        super(context, data, layout_id);
    }

    @Override
    public void convert(CommonViewHolder h, MyReplyBean item, final int position) {

        //回复人头像
        Glide.with(context).load(HttpUrl.IMAGE_URL+data.get(position).getMember_list_headpic())
                .placeholder(R.mipmap.mine_edit_head).error(R.mipmap.mine_edit_head)
                .into((ImageView) h.getConvertView().findViewById(R.id.mymsg_iv_head));
        //回复人昵称
        ((TextView)h.getConvertView().findViewById(R.id.mymsg_tv_name)).setText(data.get(position).getMember_list_nickname());
        //回复内容
        ((TextView)h.getConvertView().findViewById(R.id.mymsg_tv_content)).setText(data.get(position).getC_content());
        //回复时间
        ((TextView)h.getConvertView().findViewById(R.id.mymsg_tv_time)).setText(MyDateUtils.timeStampToData(data.get(position).getCreatetime(),"yyyy-MM-dd"));
        //被回复的帖子
        ((TextView)h.getConvertView().findViewById(R.id.mymsg_tv_title)).setText(data.get(position).getInfo().getTitle());
        //被回复的帖子类型
        TextView tvType = (TextView)h.getConvertView().findViewById(R.id.mynmsg_tv_type);
        tvType.setText(data.get(position).getInfo().getName());

        //回复按钮点击事件监听
        h.getConvertView().findViewById(R.id.mymsg_tv_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //点击回复跳至被回复帖子详情
                    Intent skill = new Intent(context,SkillDetailsActivity.class);
                    skill.putExtra("id",data.get(position).getObject_id());
                    context.startActivity(skill);


            }
        });

        h.getConvertView().findViewById(R.id.mymsg_ll_tizi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击帖子跳至被回复帖子详情
                Intent skill = new Intent(context,SkillDetailsActivity.class);
                skill.putExtra("id",data.get(position).getObject_id());
                context.startActivity(skill);
            }
        });
    }
}
