package com.project.dyuapp.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.BaseRecyclerViewHolder;
import com.project.dyuapp.entity.HomeFishingPlaceEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.List;

/**
 * 搜索-综合-钓场
 * Created by jingang on 2018/3/11.
 */

public class SearchFishingGroundsAdapter extends RecyclerView.Adapter<SearchFishingGroundsAdapter.MyViewHolder> {
    private List<HomeFishingPlaceEntity> list;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public SearchFishingGroundsAdapter(List<HomeFishingPlaceEntity> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder vh = new MyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_home_fishing_place, parent, false)
        );
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition(); // 1
                    onItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }

        String st_position = "";//省市区
        String str_address = "";//详细地址

        //钓场图片
        if (list.get(position).getG_image() != null)
            GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL + list.get(position).getG_image(), holder.imgIcon);
        //标题
        if (list.get(position).getG_name() != null)
            holder.tvName.setText(list.get(position).getG_name());
        //钓场类型
        if (list.get(position).getName() != null)
            holder.tvAddress.setText(list.get(position).getName());

        //钓场省市区
        if (list.get(position).getPosition() != null)
            st_position = list.get(position).getPosition();
        //钓场详细地址
        if (list.get(position).getGrounds_address() != null)
            str_address = list.get(position).getGrounds_address();
        holder.tvDetailedAddress.setText(st_position + str_address);
        //距离
        if ((Object) list.get(position).getDistance() != null)
            holder.tvDistance.setText(list.get(position).getDistance() + "km");
        //收费
        if (list.get(position).getPay_type() != null && "2".equals(list.get(position).getPay_type())) {
            holder.tvCost.setText("免费");
        } else {
            if (list.get(position).getPay_content() != null)
                holder.tvCost.setText(list.get(position).getPay_content());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends BaseRecyclerViewHolder {
        ImageView imgIcon;
        TextView tvName;
        TextView tvCost;
        TextView tvAddress;
        TextView tvDetailedAddress;
        TextView tvDistance;

        public MyViewHolder(View view) {
            super(view);
            imgIcon = (ImageView) view.findViewById(R.id.item_place_img_icon);
            tvName = (TextView) view.findViewById(R.id.item_place_tv_name);
            tvCost = (TextView) view.findViewById(R.id.item_place_tv_cost);
            tvAddress = (TextView) view.findViewById(R.id.item_place_tv_address);
            tvDetailedAddress = (TextView) view.findViewById(R.id.item_place_tv_detailed_address);
            tvDistance = (TextView) view.findViewById(R.id.item_place_tv_distance);
        }
    }
}
