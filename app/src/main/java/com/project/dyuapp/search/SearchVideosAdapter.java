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
import com.project.dyuapp.entity.VideoListBean;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyDateUtils;
import com.project.dyuapp.myutils.SPUtils;

import java.util.List;

/**
 * 搜索-综合-视频
 * Created by jingang on 2018/3/12.
 */

public class SearchVideosAdapter extends RecyclerView.Adapter<SearchVideosAdapter.MyViewHolder> {
    private List<VideoListBean> list;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public SearchVideosAdapter(List<VideoListBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder vh = new MyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_relevant_video, parent, false)
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
        //视频缩略图
        GlideUtils.loadImageView(mContext, list.get(position).getThumb(), holder.imgIcon);
        //视频标题
        holder.title.setText(list.get(position).getTitle());
        //时间
        holder.time.setText(MyDateUtils.timeStampToData(list.get(position).getAddtime(), "yyyy-MM-dd"));
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

