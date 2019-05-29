package com.project.dyuapp.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Created by ${田亭亭} on 2017/11/25 0025.
 *
 * @description 首页-钓场适配器
 * @change
 */


public class HomeFishingPlaceDetailsAdapter extends MyCommonAdapter<FishingPlaceEntity.FishListBean> {

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

    public HomeFishingPlaceDetailsAdapter(List<FishingPlaceEntity.FishListBean> list, Context mContext, int resid) {
        super(list, mContext, resid);
    }
    public HomeFishingPlaceDetailsAdapter(List<FishingPlaceEntity.FishListBean> list, Context mContext) {
        super(list, mContext, R.layout.item_home_fishing_place);
    }
    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        //钓场图片
        if (list.get(position).getG_image() != null)
            GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL + list.get(position).getG_image(), imgIcon);
        //标题
        if (list.get(position).getG_name() != null)
            tvName.setText(list.get(position).getG_name());
        //钓场类型
        if (list.get(position).getName() != null)
            tvAddress.setText(list.get(position).getName());
        //钓场详细地址
        if (list.get(position).getGrounds_address() != null)
            tvDetailedAddress.setText(list.get(position).getGrounds_address());
        //距离
        if ((Object)list.get(position).getDistance() != null)
            tvDistance.setText(list.get(position).getDistance() + "km");
        //收费
        if (list.get(position).getPay_content() != null)
            if ("2".equals(list.get(position).getPay_type())) {
                tvCost.setText("免费");
            } else {
                tvCost.setText(list.get(position).getPay_content());
            }
    }
}
