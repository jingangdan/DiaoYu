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
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tiantingting
 * @describtion 帖子评价
 * @created at 2017/12/14 0011
 */

public class AllCommentAdapter extends MyCommonAdapter<ForumCommentsEntity> {


    @Bind(R.id.item_fishing_friends_comment_pimg_head)
    PorterShapeImageView pimgHead;
    @Bind(R.id.item_fishing_friends_comment_tv_name)
    TextView tvName;
    @Bind(R.id.item_fishing_friends_comment_tv_time)
    TextView tvTime;
    @Bind(R.id.item_fishing_friends_comment_tv_content)
    TextView tvContent;
    @Bind(R.id.item_shop_ll_level)
    LinearLayout itemShopLlLevel;


    public AllCommentAdapter(List<ForumCommentsEntity> list, Context mContext) {
        super(list, mContext, R.layout.item_fishing_friends_comment);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        ForumCommentsEntity item = list.get(position);
        GlideUtils.loadImageViewHead(mContext, HttpUrl.IMAGE_URL + item.getMember_list_headpic(), pimgHead);
        tvName.setText(item.getMember_list_nickname());
        if (!TextUtils.isEmpty(item.getC_content())) {
            tvContent.setText(item.getC_content());
        }
        tvTime.setText(Utils.convertDate(item.getCreatetime(), "yyyy-MM-dd"));
        if (!TextUtils.isEmpty(item.getC_codes())) {
            SPUtils.setLevel(itemShopLlLevel,mContext,Integer.parseInt(item.getC_codes()));
        }
    }
}
