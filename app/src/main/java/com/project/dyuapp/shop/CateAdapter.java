package com.project.dyuapp.shop;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.BaseRecyclerViewHolder;

import java.util.List;

/**
 * Describe:
 * Created by jingang on 2019/5/20
 */
public class CateAdapter extends RecyclerView.Adapter<CateAdapter.MyViewHolder> {
    private Context mContext;
    private List<CateData> cateList;
    private OnItemClickListener mOnItemClickListener;
    private int mSelect = 0;

    public CateAdapter(Context mContext, List<CateData> cateList) {
        this.mContext = mContext;
        this.cateList = cateList;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 刷新方法
     *
     * @param positon
     */
    public void changeSelected(int positon) {
        if (positon != mSelect) {
            mSelect = positon;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder vh = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_cate, viewGroup,
                false));
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
        //点击改变背景
        if (mSelect == position) {
            holder.line.setBackgroundColor(Color.WHITE);
            holder.tvCate.setTextColor(Color.rgb(241, 83, 83));
            holder.ivCate.setVisibility(View.VISIBLE);
        } else {
            holder.line.setBackgroundColor(Color.rgb(246, 246, 246));
            holder.tvCate.setTextColor(Color.rgb(51, 51, 51));
            holder.ivCate.setVisibility(View.INVISIBLE);
        }

        holder.tvCate.setText(cateList.get(position).getCate_name());
    }

    @Override
    public int getItemCount() {
        return cateList.size();
    }

    class MyViewHolder extends BaseRecyclerViewHolder {
        TextView tvCate;
        LinearLayout line;
        ImageView ivCate;

        public MyViewHolder(View view) {
            super(view);
            tvCate = view.findViewById(R.id.tvItemCate);
            line = view.findViewById(R.id.linItemCate);
            ivCate = view.findViewById(R.id.ivItemCate);
        }
    }
}
