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
import com.project.dyuapp.entity.FishingGearDetailsBean;
import com.project.dyuapp.mysign.DataUtils;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myviews.StarBarTouch;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/12/9 0009.
 *
 * @description
 * @change
 */


public class CommentListAdapter extends MyCommonAdapter<FishingGearDetailsBean.CommentsBean> {

    @Bind(R.id.item_fishing_friends_comment_pimg_head)
    PorterShapeImageView pimgHead;
    @Bind(R.id.item_fishing_friends_comment_tv_name)
    TextView tvName;
    @Bind(R.id.item_fishing_friends_comment_tv_content)
    TextView tvContent;
    @Bind(R.id.item_fishing_friends_comment_tv_time)
    TextView tvTime;
    @Bind(R.id.item_shop_ll_level)
    LinearLayout itemShopLlLevel;


    public CommentListAdapter(List<FishingGearDetailsBean.CommentsBean> list, Context mContext, int resid) {
        super(list, mContext, resid);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        String cContent = list.get(position).getC_content();
        String createTime = list.get(position).getCreatetime();
        String memberListHeadpic = list.get(position).getMember_list_headpic();
        String memberListNickname = list.get(position).getMember_list_nickname();
        if (!TextUtils.isEmpty(cContent)) {
            tvContent.setText(cContent);
        }
        if (!TextUtils.isEmpty(createTime)) {
            tvTime.setText(DataUtils.convertDate(createTime, "yyyy-MM-dd"));
        }
        if (!TextUtils.isEmpty(memberListHeadpic)) {
            GlideUtils.loadImageViewHead(mContext, HttpUrl.IMAGE_URL + memberListHeadpic, pimgHead);
        }
        if (!TextUtils.isEmpty(memberListNickname)) {
            tvName.setText(memberListNickname);
        }
        if (!TextUtils.isEmpty(list.get(position).getC_codes())) {
            SPUtils.setLevel(itemShopLlLevel, mContext, Integer.parseInt(list.get(position).getC_codes()));
        }

    }
}
