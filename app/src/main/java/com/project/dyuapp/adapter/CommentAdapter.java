package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.ForumCommentsEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author gengqiufang
 * @describtion 帖子评价
 * @created at 2017/12/11 0011
 */

public class CommentAdapter extends MyCommonAdapter<ForumCommentsEntity> {
    @Bind(R.id.item_skill_detail_iv_head)
    PorterShapeImageView ivHead;
    @Bind(R.id.item_skill_detail_tv_name)
    TextView tvName;
    @Bind(R.id.item_skill_detail_tv_time)
    TextView tvTime;
    @Bind(R.id.item_skill_detail_tv_content)
    TextView tvContent;
    @Bind(R.id.item_skill_detail_tv_content_reply)
    TextView tvContentReply;
    @Bind(R.id.item_skill_detail_ll_reply)
    LinearLayout llReply;
    @Bind(R.id.item_skill_detail_tv_name_reply)
    TextView tvNameReply;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onReplyClick(View view, int position);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CommentAdapter(List<ForumCommentsEntity> list, Context mContext) {
        super(list, mContext, R.layout.item_common_comment);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, final int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        ForumCommentsEntity item = list.get(position);
        GlideUtils.loadImageViewHead(mContext, HttpUrl.IMAGE_URL + item.getMember_list_headpic(), ivHead);
        tvName.setText(item.getMember_list_nickname());
        if (!TextUtils.isEmpty(item.getReturn_id())) {
            llReply.setVisibility(View.VISIBLE);
            tvContentReply.setText(item.getReturn_content());
            tvNameReply.setText(item.getReturn_id());
            tvContent.setText(item.getC_content());
        } else {
            llReply.setVisibility(View.GONE);
            tvContent.setText(item.getC_content());
        }
        tvTime.setText(Utils.convertDate(item.getCreatetime(), "yyyy-MM-dd"));
        if (onItemClickListener != null) {
            tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onReplyClick(tvContent, position);
                }
            });
        }

    }
}
