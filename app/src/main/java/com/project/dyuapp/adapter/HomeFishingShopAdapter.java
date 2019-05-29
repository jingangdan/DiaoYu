package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.FishingGearEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/11/25 0025.
 *
 * @description 首页-渔具适配器
 * @change
 */

public class HomeFishingShopAdapter extends MyCommonAdapter<FishingGearEntity> {

    @Bind(R.id.item_shop_img_icon)
    ImageView imgIcon;
    @Bind(R.id.item_shop_tv_name)
    TextView tvName;
    @Bind(R.id.item_shop_tv_address)
    TextView tvAddress;
    @Bind(R.id.item_shop_tv_distance)
    TextView tvDistance;
    @Bind(R.id.item_shop_rl)
    RelativeLayout itemShopRl;
    @Bind(R.id.item_shop_ll_level)
    LinearLayout itemShopLlLevel;


    private String whereFroms;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HomeFishingShopAdapter(List<FishingGearEntity> list, Context mContext, int resid, String whereFrom) {
        super(list, mContext, resid);
        whereFroms = whereFrom;
    }

    public HomeFishingShopAdapter(List<FishingGearEntity> list, Context mContext, String whereFrom) {
        super(list, mContext, R.layout.item_home_fishing_shop);
        whereFroms = whereFrom;
    }


    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, final int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        String juli = list.get(position).getJuli();
        String shopImage = list.get(position).getShop_image();
        String shopName = list.get(position).getShop_name();
        String star = list.get(position).getStar();
        String address = "";
        if (whereFroms.equals("SearchResultActivity")) {
            address = list.get(position).getShop_address();
        } else {
            address = list.get(position).getAddress();
        }


        //渔具店距离
        if (!TextUtils.isEmpty(juli)) {
            if (juli.contains("km")) {
                tvDistance.setText(juli);
            } else {
                tvDistance.setText(juli + "km");
            }
        }
        //图片
        if (!TextUtils.isEmpty(shopImage)) {
            GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL + shopImage, imgIcon);
        } else {
            imgIcon.setImageResource(R.mipmap.mine_img);
        }
        //渔具店名称
        if (!TextUtils.isEmpty(shopName)) {
            tvName.setText(shopName);
        }
        //星级
        if (!TextUtils.isEmpty(star)) {
            SPUtils.setLevel(itemShopLlLevel, mContext, Integer.parseInt(star));
        }
        //渔具店地址
        if (!TextUtils.isEmpty(address)) {
            tvAddress.setText(address);
        }

        if (onItemClickListener != null) {
            itemShopRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(itemShopRl, position);
                }
            });
        }
    }


}
