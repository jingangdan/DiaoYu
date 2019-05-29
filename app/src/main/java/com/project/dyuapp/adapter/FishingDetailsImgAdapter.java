package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.FishingPlaceEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jingang on 2018/5/12.
 */

public class FishingDetailsImgAdapter extends MyCommonAdapter<FishingPlaceEntity.FishDianpingBean.ContentBean> {
    @Bind(R.id.item_fishing_friends_dp_img)
    ImageView itemFishingFriendsDpImg;

    public FishingDetailsImgAdapter(List<FishingPlaceEntity.FishDianpingBean.ContentBean> list, Context mContext) {
        super(list, mContext, R.layout.item_fishimg_friend_dp_img);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        if (!TextUtils.isEmpty(list.get(position).getStr_imgs())) {
            GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL + list.get(position).getStr_imgs(), itemFishingFriendsDpImg);
        }
    }
}
