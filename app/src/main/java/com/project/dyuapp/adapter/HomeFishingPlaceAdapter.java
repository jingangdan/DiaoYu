package com.project.dyuapp.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.HomeFishingPlaceEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/11/25 0025.
 *
 * @description 首页-钓场适配器
 * @change
 */


public class HomeFishingPlaceAdapter extends MyCommonAdapter<HomeFishingPlaceEntity> {

    @Bind(R.id.item_place_img_icon)
    ImageView imgIcon;
    @Bind(R.id.item_place_tv_name)
    TextView tvName;
    @Bind(R.id.item_place_tv_cost)
    TextView tvCost;
    @Bind(R.id.item_place_tv_address)
    TextView tvAddress;
    @Bind(R.id.item_place_tv_detailed_address)
    TextView tvDetailedAddress;
    @Bind(R.id.item_place_tv_distance)
    TextView tvDistance;

    public HomeFishingPlaceAdapter(List<HomeFishingPlaceEntity> list, Context mContext, int resid) {
        super(list, mContext, resid);
    }
    public HomeFishingPlaceAdapter(List<HomeFishingPlaceEntity> list, Context mContext) {
        super(list, mContext, R.layout.item_home_fishing_place);
    }
    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
    }
}
