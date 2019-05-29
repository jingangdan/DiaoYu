package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.NewestListBean;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myviews.InWebView;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/12/13 0013.
 *
 * @description 二级分类下视频列表适配器
 * @change
 */


public class SiteCategoryVideoAdapter extends MyCommonAdapter<NewestListBean> {
    @Bind(R.id.item_video_img_icon)
    ImageView imgIcon;
    @Bind(R.id.item_video_tv_title)
    TextView tvTitle;
    InWebView inWeb;

    public SiteCategoryVideoAdapter(List<NewestListBean> list, Context mContext, int resid) {
        super(list, mContext, R.layout.item_home_gv_video);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        String title = list.get(position).getTitle();
        String thumb = list.get(position).getThumb();
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        String content = list.get(position).getContent();
        if (!TextUtils.isEmpty(thumb)) {
            GlideUtils.loadImageView(mContext, thumb, imgIcon);
        } else {
            imgIcon.setBackgroundResource(R.mipmap.mine_img);
        }
//        if (inWeb != null) {
//            inWeb.myDestroy();
//        }
    }

}
