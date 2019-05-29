package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.VideoListBean;
import com.project.dyuapp.mysign.DataUtils;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myviews.InWebView;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/12/16 0016.
 *
 * @description 相关视频适配器
 * @change
 */


public class VideoRelevantAdapter extends MyCommonAdapter<VideoListBean> {

    @Bind(R.id.video_iv_img)
    ImageView videoIvImg;
    @Bind(R.id.video_tv_title)
    TextView videoTvTitle;
    @Bind(R.id.video_tv_time)
    TextView videoTvTime;
    private InWebView inWeb;

    public VideoRelevantAdapter(List<VideoListBean> list, Context mContext, int resid) {
        super(list, mContext, resid);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        String addtime = list.get(position).getAddtime();
        String title = list.get(position).getTitle();
        String thumb = list.get(position).getThumb();
        if (!TextUtils.isEmpty(addtime)) {
            videoTvTime.setText(DataUtils.convertDate(addtime, "yyyy-MM-dd"));
        }
        String content = list.get(position).getContent();
        if (!TextUtils.isEmpty(thumb)) {
            GlideUtils.loadImageView(mContext,thumb, videoIvImg);
        } else {
            videoIvImg.setBackgroundResource(R.mipmap.mine_img);
        }
        if (!TextUtils.isEmpty(title)) {
            videoTvTitle.setText(title);
        }

    }
}
