package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.dyuapp.R;
import com.project.dyuapp.entity.MyRewardBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyDateUtils;

import java.util.List;

/**
 * @author litongtong
 * @created on 2017/12/8 09:46
 * @description 打赏适配器
 * @change ${}
 */

public class MyRewardAdapter extends CommonAdapter<MyRewardBean>{

    /**
     * 构造函数
     *
     * @param context   上下文
     * @param data      显示的数据
     * @param layout_id listview使用的条目的布局文件id
     */
    public MyRewardAdapter(Context context, List<MyRewardBean> data, int layout_id) {
        super(context, data, layout_id);
    }

    @Override
    public void convert(CommonViewHolder h, MyRewardBean item, int position) {

        //打赏人头像
        Glide.with(context).load(HttpUrl.IMAGE_URL+data.get(position).getMember_list_headpic())
                .placeholder(R.mipmap.mine_edit_head).error(R.mipmap.mine_edit_head)
                .into((ImageView) h.getConvertView().findViewById(R.id.mymsg_iv_head));
        //打赏人昵称
        ((TextView)h.getConvertView().findViewById(R.id.mymsg_tv_name)).setText(data.get(position).getMember_list_nickname());
        ImageView ivImg = (ImageView) h.getConvertView().findViewById(R.id.mymsg_iv_img);
        ivImg.setVisibility(View.VISIBLE);
        ivImg.setImageResource(R.mipmap.mine_message_05icon);
        //打赏理由
        if(!TextUtils.isEmpty(data.get(position).getDesc())){
            ((TextView)h.getConvertView().findViewById(R.id.mymsg_tv_content)).setText(data.get(position).getDesc());
        }else {
            ((TextView)h.getConvertView().findViewById(R.id.mymsg_tv_content)).setText("打赏了您的帖子");
        }
        //打赏时间
        ((TextView)h.getConvertView().findViewById(R.id.mymsg_tv_time)).setText(MyDateUtils.timeStampToData(data.get(position).getCreatetime(),"yyyy-MM-dd"));
        //被打赏的帖子标题
        ((TextView)h.getConvertView().findViewById(R.id.mymsg_tv_title)).setText(data.get(position).getTitle());
        //被打赏的帖子类型
        ((TextView)h.getConvertView().findViewById(R.id.mynmsg_tv_type)).setText("#"+data.get(position).getName()+"#");
        //打赏金币数量
        TextView tvRight = (TextView) h.getConvertView().findViewById(R.id.mymsg_tv_right);
        tvRight.setText(data.get(position).getCoin_num()+"金币");
        tvRight.setTextColor(context.getResources().getColor(R.color.c_f44e4e));
    }
}
