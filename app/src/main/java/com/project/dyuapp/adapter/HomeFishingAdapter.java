package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.HomeFishingPlaceEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @创建者 任伟
 * @创建时间 2017/12/09 15:11
 * @描述 ${}
 */

public class HomeFishingAdapter extends MyCommonAdapter<HomeFishingPlaceEntity> {

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
    @Bind(R.id.item_fishing_ll_level)
    LinearLayout itemFishingLlLevel;

    public HomeFishingAdapter(List<HomeFishingPlaceEntity> list, Context mContext, int resid) {
        super(list, mContext, resid);
    }

    public HomeFishingAdapter(List<HomeFishingPlaceEntity> list, Context mContext) {
        super(list, mContext, R.layout.item_home_fishing_place);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());

        String st_position = "";//省市区
        String str_address = "";//详细地址

        int star = list.get(position).getDp_score();

        //钓场图片
        if (list.get(position).getG_image() != null)
            GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL + list.get(position).getG_image(), imgIcon);
        //标题
        if (list.get(position).getG_name() != null)
            tvName.setText(list.get(position).getG_name());
        //钓场类型
        if (list.get(position).getName() != null)
            tvAddress.setText(list.get(position).getName());

        //星级
        if (!TextUtils.isEmpty(String.valueOf(star))) {
            SPUtils.setLevel(itemFishingLlLevel, mContext, star);
        }

        //钓场省市区
        if (list.get(position).getPosition() != null)
            st_position = list.get(position).getPosition();
        //钓场详细地址
        if (list.get(position).getGrounds_address() != null)
            str_address = list.get(position).getGrounds_address();
        tvDetailedAddress.setText(st_position + str_address);
        //距离
        if ((Object) list.get(position).getDistance() != null)
            tvDistance.setText(list.get(position).getDistance() + "km");
        //收费
        if (list.get(position).getPay_type() != null && "2".equals(list.get(position).getPay_type())) {
            tvCost.setText("免费");
        } else {
            if (list.get(position).getPay_content() != null)
                tvCost.setText(list.get(position).getPay_content());
        }
    }
}