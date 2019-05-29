package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.FishingGearDetailsBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/12/9 0009.
 *
 * @description
 * @change
 */


public class AroundShopAdapter extends MyCommonAdapter<FishingGearDetailsBean.AmbitusBean> {
    @Bind(R.id.item_shop_img_icon)
    ImageView imgIcon;
    @Bind(R.id.item_shop_tv_name)
    TextView tvName;
    @Bind(R.id.item_shop_tv_address)
    TextView tvAddress;
    @Bind(R.id.item_shop_tv_distance)
    TextView tvDistance;
    @Bind(R.id.item_shop_ll_level)
    LinearLayout itemShopLlLevel;


    public AroundShopAdapter(List<FishingGearDetailsBean.AmbitusBean> list, Context mContext, int resid) {
        super(list, mContext, resid);
    }

    public AroundShopAdapter(List<FishingGearDetailsBean.AmbitusBean> list, Context mContext) {
        super(list, mContext, R.layout.item_home_fishing_shop);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        String juli = list.get(position).getJuli();
        String shopImage = list.get(position).getShop_image();
        String shopName = list.get(position).getShop_name();
        int star = list.get(position).getStar();
        String address = list.get(position).getAddress();

        if (!TextUtils.isEmpty(juli)) {
            if (juli.contains("km")) {
                tvDistance.setText(juli);
            } else {
                tvDistance.setText(juli + "km");
            }
        }
        if (!TextUtils.isEmpty(shopImage)) {
            Glide.with(mContext).load(HttpUrl.IMAGE_URL + shopImage).into(imgIcon);
        } else {
            imgIcon.setImageResource(R.mipmap.mine_img);
        }
        if (!TextUtils.isEmpty(shopName)) {
            tvName.setText(shopName);
        }
        if (!TextUtils.isEmpty(String.valueOf(star))) {
            SPUtils.setLevel(itemShopLlLevel, mContext, star);
        }
        if (!TextUtils.isEmpty(address)) {
            tvAddress.setText(address);
        }
    }
}
