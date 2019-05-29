package com.project.dyuapp.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.BaseRecyclerViewHolder;
import com.project.dyuapp.entity.FishingGearEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;

import java.util.List;

/**
 * 搜索-综合-渔具店
 * Created by jingang on 2018/3/12.
 */

public class SearchFishingShopAdapter extends RecyclerView.Adapter<SearchFishingShopAdapter.MyViewHolder> {
    private List<FishingGearEntity> list;
    private Context mContext;
    private String whereFroms;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SearchFishingShopAdapter(List<FishingGearEntity> list, Context mContext, String whereFroms) {
        this.list = list;
        this.mContext = mContext;
        this.whereFroms = whereFroms;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder vh = new MyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_home_fishing_shop, parent, false)
        );
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition(); // 1
                    onItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
        String juli = list.get(position).getJuli();
        String shopImage = list.get(position).getShop_image();
        String shopName = list.get(position).getShop_name();
        String star = list.get(position).getStar();
        String address = "";
        if (whereFroms.equals("SearchMessageActivity")) {
            address = list.get(position).getShop_address();
        } else {
            address = list.get(position).getAddress();
        }


        //渔具店距离
        if (!TextUtils.isEmpty(juli)) {
            if (juli.contains("km")) {
                holder.tvDistance.setText(juli);
            } else {
                holder.tvDistance.setText(juli + "km");
            }
        }
        //图片
        if (!TextUtils.isEmpty(shopImage)) {
            GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL + shopImage, holder.imgIcon);
        } else {
            holder.imgIcon.setImageResource(R.mipmap.mine_img);
        }
        //渔具店名称
        if (!TextUtils.isEmpty(shopName)) {
            holder.tvName.setText(shopName);
        }
        //星级
        if (!TextUtils.isEmpty(star)) {
            SPUtils.setLevel(holder.itemShopLlLevel, mContext, Integer.parseInt(star));
        }
        //渔具店地址
        if (!TextUtils.isEmpty(address)) {
            holder.tvAddress.setText(address);
        }

        if (onItemClickListener != null) {
            holder.itemShopRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemShopRl, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends BaseRecyclerViewHolder {
        ImageView imgIcon;
        TextView tvName;
        TextView tvAddress;
        TextView tvDistance;
        RelativeLayout itemShopRl;
        LinearLayout itemShopLlLevel;

        public MyViewHolder(View view) {
            super(view);
            imgIcon = (ImageView) view.findViewById(R.id.item_shop_img_icon);
            tvName = (TextView) view.findViewById(R.id.item_shop_tv_name);
            tvAddress = (TextView) view.findViewById(R.id.item_shop_tv_address);
            tvDistance = (TextView) view.findViewById(R.id.item_shop_tv_distance);
            itemShopRl = (RelativeLayout) view.findViewById(R.id.item_shop_rl);
            itemShopLlLevel = (LinearLayout) view.findViewById(R.id.item_shop_ll_level);

        }
    }
}
