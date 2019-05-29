package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.FishingGearDetailsBean;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/12/9 0009.
 *
 * @description
 * @change
 */


public class FishingFriendsPhotoAdapter extends MyCommonAdapter<FishingGearDetailsBean.ImgsBean> {

    @Bind(R.id.item_fishing_friends_img_photo)
    ImageView imgPhoto;

    public FishingFriendsPhotoAdapter(List<FishingGearDetailsBean.ImgsBean> list, Context mContext, int resid) {
        super(list, mContext, resid);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        if (!TextUtils.isEmpty(list.get(position).getImg_url())) {
            GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL+list.get(position).getImg_url(),imgPhoto);
        }
    }
}
