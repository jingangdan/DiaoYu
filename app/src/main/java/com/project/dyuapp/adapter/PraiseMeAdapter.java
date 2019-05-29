package com.project.dyuapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.dyuapp.R;
import com.project.dyuapp.entity.MyPraiseBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyDateUtils;

import java.util.List;

/**
 * @author litongtong
 * @created on 2017/12/7 09:50
 * @description 赞过我适配器
 * @change ${}
 */

public class PraiseMeAdapter extends CommonAdapter<MyPraiseBean>{
    /**
     * 构造函数
     *
     * @param context   上下文
     * @param data      显示的数据
     * @param layout_id listview使用的条目的布局文件id
     */
    public PraiseMeAdapter(Context context, List<MyPraiseBean> data, int layout_id) {
        super(context, data, layout_id);
    }

    @Override
    public void convert(CommonViewHolder h, MyPraiseBean item, int position) {
        TextView tvContent = (TextView) h.getConvertView().findViewById(R.id.mymsg_tv_content);
        if(data.get(position).getObject_type().equals("1")){//对象的类型 1帖子 2视频
            tvContent.setText("赞了您的帖子");
        }else {
            tvContent.setText("赞了您的视频");
        }

        h.getConvertView().findViewById(R.id.mymsg_iv_img).setVisibility(View.VISIBLE);
        h.getConvertView().findViewById(R.id.mymsg_tv_right).setVisibility(View.GONE);

        //点赞人的头像
        Glide.with(context).load(HttpUrl.IMAGE_URL+data.get(position).getMember_list_headpic())
                .placeholder(R.mipmap.mine_edit_head).error(R.mipmap.mine_edit_head)
                .into((ImageView) h.getConvertView().findViewById(R.id.mymsg_iv_head));
        //点赞人的昵称
        ((TextView)h.getConvertView().findViewById(R.id.mymsg_tv_name)).setText(data.get(position).getMember_list_nickname());
        //点赞时间
        ((TextView)h.getConvertView().findViewById(R.id.mymsg_tv_time)).setText(MyDateUtils.timeStampToData(data.get(position).getCreatetime(),"yyyy-MM-dd"));
        //被赞的帖子/视频标题
        ((TextView)h.getConvertView().findViewById(R.id.mymsg_tv_title)).setText(data.get(position).getInfo().getTitle());
        //被赞的帖子/视频类型
        ((TextView)h.getConvertView().findViewById(R.id.mynmsg_tv_type)).setText("#"+data.get(position).getInfo().getName()+"#");
    }
}
