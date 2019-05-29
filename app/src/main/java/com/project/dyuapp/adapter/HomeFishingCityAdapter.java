package com.project.dyuapp.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.HomeFishingPlaceEntity;
import com.project.dyuapp.myutils.GlideUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.project.dyuapp.myutils.HttpUrl.IMAGE_URL;

/**
 * @创建者 任伟
 * @创建时间 2017/12/13 18:05
 * @描述 ${}
 */

public class HomeFishingCityAdapter extends MyCommonAdapter<HomeFishingPlaceEntity> {
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

    public HomeFishingCityAdapter(List<HomeFishingPlaceEntity> list, Context mContext, int resid) {
        super(list, mContext, resid);
    }

    public HomeFishingCityAdapter(List<HomeFishingPlaceEntity> list, Context mContext) {
        super(list, mContext, R.layout.item_home_fishing_place);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());

        //钓场图片
        if (list.get(position).getG_image() != null)
            GlideUtils.loadImageView(mContext, IMAGE_URL + list.get(position).getG_image(), imgIcon);
        //标题
        if (list.get(position).getG_name() != null)
            tvName.setText(list.get(position).getG_name());
        //钓场类型
        if (list.get(position).getCatname() != null)
            tvAddress.setText(list.get(position).getCatname());
        //钓场详细地址
        String str_position = "";
        String grounds_address = "";
        if (list.get(position).getPosition() != null)
            str_position = list.get(position).getPosition();
        if (list.get(position).getGrounds_address() != null)
            grounds_address = list.get(position).getGrounds_address();
        tvDetailedAddress.setText(str_position + grounds_address);
        //距离
        if (list.get(position).getJuli() != null)
            tvDistance.setText(list.get(position).getJuli() + "km");
        //收费
        if (list.get(position).getPay_type() != null)
            if ("2".equals(list.get(position).getPay_type())) {
                tvCost.setText("免费");
            } else {
                tvCost.setText(list.get(position).getPay_content());
            }
//            tvCost.setText(list.get(position).getPay_type());
    }
}
