package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.activity.MyFishShopAddBean;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.mysign.DataUtils;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myviews.StarBarTouch;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/11/25 0025.
 *
 * @description 个人中心-渔具店适配器
 * @change
 */


public class CenterFishingShopAdapter extends MyCommonAdapter<MyFishShopAddBean> {


    @Bind(R.id.item_center_shop_tv_time)
    TextView tvTime;
    @Bind(R.id.item_center_shop_tv_state)
    TextView tvState;
    @Bind(R.id.item_center_shop_img_icon)
    ImageView imgIcon;
    @Bind(R.id.item_center_shop_tv_name)
    TextView tvName;
    @Bind(R.id.item_center_shop_tv_address)
    TextView tvAddress;
    @Bind(R.id.item_center_shop_tv_distance)
    TextView tvDistance;
    @Bind(R.id.what_item_center_shop_tv_time)
    TextView whatTvTime;
    private OnItemClickListener onItemClickListener;
    private String whereFrom;
    @Bind(R.id.item_shop_ll_level)
    LinearLayout itemShopLlLevel;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickView(View view, int position);
    }

    public CenterFishingShopAdapter(List<MyFishShopAddBean> list, Context mContext, int resid, String whereFrom) {
        super(list, mContext, resid);
        this.whereFrom = whereFrom;
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, final int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        String juli = list.get(position).getJuli();//渔具店距离
        String shopThumb = list.get(position).getShop_image();//渔具店图片
        String shopName = list.get(position).getShop_name();//渔具店名称
        int level = list.get(position).getLevel();//等级
        String address = list.get(position).getAddress();//渔具店详细地址
        String shopStatus = list.get(position).getShop_status();//渔具店状态
        String addtime = list.get(position).getAddtime();//添加时间
        String createtime = list.get(position).getCreatetime();//创建时间
        if (!TextUtils.isEmpty(juli)) {
            if (juli.contains("km")) {
                tvDistance.setText(juli);
            } else {
                tvDistance.setText(juli + "km");
            }
        }
        if (!TextUtils.isEmpty(shopThumb)) {
            GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL + shopThumb, imgIcon);
        } else {
            imgIcon.setImageResource(R.mipmap.mine_img);
        }
        if (!TextUtils.isEmpty(shopName)) {
            tvName.setText(shopName);
        }
        if (!TextUtils.isEmpty(String.valueOf(level))) {
            SPUtils.setLevel(itemShopLlLevel, mContext, level);
        }

        if (TextUtils.equals(whereFrom, "1")) {
            itemShopLlLevel.setVisibility(View.VISIBLE);
            whatTvTime.setText("关注时间：");
            tvState.setText("取消关注");
            if (!TextUtils.isEmpty(createtime)) {
                tvTime.setText(DataUtils.convertDate(createtime, "yyyy-MM-dd"));
            }
        } else if (TextUtils.equals(whereFrom, "2")) {
            itemShopLlLevel.setVisibility(View.INVISIBLE);
            whatTvTime.setText("添加时间：");
            if (!TextUtils.isEmpty(shopStatus)) {
                //1待审核2已通过3不通过
                if (TextUtils.equals(shopStatus, "1")) {
                    //1待审核
                    tvState.setText("待审核");
                    tvState.setTextColor(mContext.getResources().getColor(R.color.c_269ceb));
                } else if (TextUtils.equals(shopStatus, "2")) {
                    //2已通过
                    tvState.setText("已通过");
                    tvState.setTextColor(mContext.getResources().getColor(R.color.c_999999));
                } else if (TextUtils.equals(shopStatus, "3")) {
                    //3不通过
                    tvState.setText("未通过");
                    tvState.setTextColor(mContext.getResources().getColor(R.color.c_f44e4e));
                }
                if (!TextUtils.isEmpty(addtime)) {
                    tvTime.setText(DataUtils.convertDate(addtime, "yyyy-MM-dd"));
                }
            }
        }
        if (onItemClickListener != null) {
            tvState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClickView(tvState, position);
                }
            });

        }
    }
}
