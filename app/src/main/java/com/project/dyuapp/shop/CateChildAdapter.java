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
import com.project.dyuapp.myutils.HttpUrl;

import java.util.List;

/**
 * Describe:
 * Created by jingang on 2019/5/20
 */
public class CateChildAdapter extends RecyclerView.Adapter<CateChildAdapter.MyViewHolder> {
    private Context mContext;
    private List<CateData.ChildBean> cateList;
    private OnItemClickListener mOnItemClickListener;

    public CateChildAdapter(Context mContext, List<CateData.ChildBean> cateList) {
        this.mContext = mContext;
        this.cateList = cateList;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder vh = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_cate_child, viewGroup,
                false));
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
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

        GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL + cateList.get(i).getCate_img(), holder.img);
        holder.tvCate.setText(cateList.get(i).getCate_name());

    }

    @Override
    public int getItemCount() {
        return cateList.size();
    }

    class MyViewHolder extends BaseRecyclerViewHolder {
        TextView tvCate;
        ImageView img;

        public MyViewHolder(View view) {
            super(view);
            tvCate = view.findViewById(R.id.tvItemCateChild);
            img = view.findViewById(R.id.ivItemCateChild);
        }
    }
}
