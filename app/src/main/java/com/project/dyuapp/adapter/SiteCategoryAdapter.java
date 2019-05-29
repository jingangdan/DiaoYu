package com.project.dyuapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.entity.SiteCategoryEntity;

import java.util.List;

/**
 * Created by ${田亭亭} on 2017/12/13 0013.
 *
 * @description
 * @change
 */


public class SiteCategoryAdapter extends RecyclerView.Adapter<SiteCategoryAdapter.AgeItemViewHolder> {
    private Context mContext;
    private List<SiteCategoryEntity> mData;
    private int selectPosition = 0;
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onRecycleOnClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public SiteCategoryAdapter(Context context, List<SiteCategoryEntity> data) {
        this.mContext = context;
        this.mData = data;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    @Override
    public AgeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_screen_type, parent, false);
        int width = ((Activity) mContext).getWindow().getWindowManager().getDefaultDisplay().getWidth() - 48;
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(width / 4, 98);//设置宽度和高度
        item.setLayoutParams(params);
        return new AgeItemViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final AgeItemViewHolder holder, final int position) {
        ViewGroup.LayoutParams lp = holder.getTextView().getLayoutParams();
        lp.width = mContext.getWallpaperDesiredMinimumWidth() / 4;
        lp.height = 98;
        holder.getTextView().setLayoutParams(lp);
        holder.getTextView().setText(mData.get(position).getName());
        if (onItemClickListener != null) {
            holder.getTextView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onRecycleOnClick(holder.getTextView(), position);
                }
            });
        }
        if (selectPosition == position) {
            holder.getTextView().setTextColor(mContext.getResources().getColor(R.color.c_269ceb));
        } else {
            holder.getTextView().setTextColor(mContext.getResources().getColor(R.color.c_333333));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // ViewHolder
    public class AgeItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public AgeItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_srceen_type_tv_name);
            mTextView.setTag(this);
        }

        public TextView getTextView() {
            return mTextView;
        }
    }
}
