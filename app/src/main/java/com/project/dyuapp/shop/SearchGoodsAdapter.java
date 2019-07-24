package com.project.dyuapp.shop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.BaseRecyclerViewHolder;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.MyDateUtils;
import com.project.dyuapp.search.OnItemClickListener;
import com.project.dyuapp.search.SearchMessageEntity;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Describe:搜索-综合-商品
 * Created by jingang on 2019/7/23
 */
public class SearchGoodsAdapter extends RecyclerView.Adapter<SearchGoodsAdapter.MyViewHolder> {
    private List<GoodsEntity> list;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public SearchGoodsAdapter(List<GoodsEntity> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        MyViewHolder vh = new MyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_relevant_video, parent, false)
        );
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition(); // 1
                    onItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }

        DecimalFormat df = new DecimalFormat("0.00");

        //视频缩略图
        GlideUtils.loadImageView(mContext, list.get(position).getGoods_thumbnail_url(), holder.imgIcon);
        //视频标题
        holder.title.setText(list.get(position).getGoods_name());
        //时间
//        holder.time.setText(MyDateUtils.timeStampToData(list.get(position).getAddtime(), "yyyy-MM-dd"));
        //我脑子好乱啊
        int quan = Integer.valueOf(list.get(position).getCoupon_discount());
        int priceOld = Integer.valueOf(list.get(position).getMin_group_price());
        holder.time.setText("¥" + df.format((priceOld - quan) / 100));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends BaseRecyclerViewHolder {
        ImageView imgIcon;
        TextView title, time;

        public MyViewHolder(View view) {
            super(view);
            imgIcon = (ImageView) view.findViewById(R.id.video_iv_img);
            title = (TextView) view.findViewById(R.id.video_tv_title);
            time = (TextView) view.findViewById(R.id.video_tv_time);

        }
    }
}
