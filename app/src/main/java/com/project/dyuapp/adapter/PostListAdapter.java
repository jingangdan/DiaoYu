package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.ForumEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author gengqiufang
 * @describtion 帖子
 * @created at 2017/11/30 0030
 */

public class PostListAdapter extends MyCommonAdapter<ForumEntity> {

    @Bind(R.id.post_list_iv_pic)
    ImageView ivPic;
    @Bind(R.id.post_list_tv_title)
    TextView tvTitle;
    @Bind(R.id.post_list_tv_content)
    TextView tvContent;
    @Bind(R.id.post_list_tv_label1)
    TextView ivLabel1;
    @Bind(R.id.post_list_tv_label2)
    TextView ivLabel2;
    @Bind(R.id.post_list_iv_head)
    PorterShapeImageView ivHead;
    @Bind(R.id.post_list_tv_name)
    TextView tvName;

    public PostListAdapter(List<ForumEntity> list, Context mContext) {
        super(list, mContext, R.layout.item_post_list);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        //帖子图片
        GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL+list.get(position).getThumb_img(),ivPic);
        //帖子标题
        if (!TextUtils.isEmpty(list.get(position).getTitle())){
            tvTitle.setText(list.get(position).getTitle());
        }
        //帖子内容
        if (!TextUtils.isEmpty(list.get(position).getContent())){
            tvContent.setText(list.get(position).getContent());
        }
        //1精华  2不精华帖
        if((!TextUtils.isEmpty(list.get(position).getIs_jinghua()))&&list.get(position).getIs_jinghua().equals("1")){
            ivLabel2.setVisibility(View.VISIBLE);
        }else {
            ivLabel2.setVisibility(View.GONE);
        }
        //1推荐  2不推荐
        if((!TextUtils.isEmpty(list.get(position).getIs_jinghua()))&&list.get(position).getIs_tuijian().equals("1")){
            ivLabel1.setVisibility(View.VISIBLE);
        }else {
            ivLabel1.setVisibility(View.GONE);
        }
        //发帖人头像
        GlideUtils.loadImageViewHead(mContext, HttpUrl.IMAGE_URL+list.get(position).getMember_list_headpic(),ivHead);
        //发帖人昵称
        if (!TextUtils.isEmpty(list.get(position).getMember_list_nickname())){
            tvName.setText(list.get(position).getMember_list_nickname());
        }
    }
}
