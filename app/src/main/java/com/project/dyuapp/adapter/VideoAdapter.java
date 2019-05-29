package com.project.dyuapp.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.VideoListBean;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyDateUtils;

import java.util.List;

/**
 * @author gengqiufang
 * @describtion 视频
 * @created at 2017/12/7 0007
 */

public class VideoAdapter extends MyCommonAdapter<VideoListBean> {
    public VideoAdapter(List<VideoListBean> list, Context mContext) {
        super(list, mContext, R.layout.item_relevant_video);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        //视频缩略图
        GlideUtils.loadImageView(mContext, list.get(position).getThumb(), (ImageView) commentViewHolder.FindViewById(R.id.video_iv_img));
        //视频标题
        ((TextView)commentViewHolder.FindViewById(R.id.video_tv_title)).setText(list.get(position).getTitle());
        //时间
        ((TextView)commentViewHolder.FindViewById(R.id.video_tv_time)).setText(MyDateUtils.timeStampToData(list.get(position).getAddtime(),"yyyy-MM-dd"));
    }
}
