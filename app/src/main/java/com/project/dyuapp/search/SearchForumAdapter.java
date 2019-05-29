package com.project.dyuapp.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.project.dyuapp.R;
import com.project.dyuapp.base.BaseRecyclerViewHolder;
import com.project.dyuapp.entity.ForumEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.List;

/**
 * 搜索-综合-帖子
 * Created by jingang on 2018/3/12.
 */

public class SearchForumAdapter extends RecyclerView.Adapter<SearchForumAdapter.MyViewHolder> {
    private List<ForumEntity> list;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public SearchForumAdapter(List<ForumEntity> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder vh = new MyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_post_list, parent, false)
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
        //帖子图片
        GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL + list.get(position).getThumb_img(), holder.ivPic);
        //帖子标题
        if (!TextUtils.isEmpty(list.get(position).getTitle())) {
            holder.tvTitle.setText(list.get(position).getTitle());
        }
        //帖子内容
        if (!TextUtils.isEmpty(list.get(position).getContent())) {
            holder.tvContent.setText(list.get(position).getContent());
        }
        //1精华  2不精华帖
        if ((!TextUtils.isEmpty(list.get(position).getIs_jinghua())) && list.get(position).getIs_jinghua().equals("1")) {
            holder.ivLabel2.setVisibility(View.VISIBLE);
        } else {
            holder.ivLabel2.setVisibility(View.GONE);
        }
        //1推荐  2不推荐
        if ((!TextUtils.isEmpty(list.get(position).getIs_jinghua())) && list.get(position).getIs_tuijian().equals("1")) {
            holder.ivLabel1.setVisibility(View.VISIBLE);
        } else {
            holder.ivLabel1.setVisibility(View.GONE);
        }
        //发帖人头像
        GlideUtils.loadImageViewHead(mContext, HttpUrl.IMAGE_URL + list.get(position).getMember_list_headpic(), holder.ivHead);
        //发帖人昵称
        if (!TextUtils.isEmpty(list.get(position).getMember_list_nickname())) {
            holder.tvName.setText(list.get(position).getMember_list_nickname());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends BaseRecyclerViewHolder {
        ImageView ivPic;
        TextView tvTitle;
        TextView tvContent;
        TextView ivLabel1;
        TextView ivLabel2;
        PorterShapeImageView ivHead;
        TextView tvName;

        public MyViewHolder(View view) {
            super(view);
            ivPic = (ImageView) view.findViewById(R.id.post_list_iv_pic);
            tvTitle = (TextView) view.findViewById(R.id.post_list_tv_title);
            tvContent = (TextView) view.findViewById(R.id.post_list_tv_content);
            ivLabel1 = (TextView) view.findViewById(R.id.post_list_tv_label1);
            ivLabel2 = (TextView) view.findViewById(R.id.post_list_tv_label2);
            ivHead = (PorterShapeImageView) view.findViewById(R.id.post_list_iv_head);
            tvName = (TextView) view.findViewById(R.id.post_list_tv_name);
        }
    }
}

