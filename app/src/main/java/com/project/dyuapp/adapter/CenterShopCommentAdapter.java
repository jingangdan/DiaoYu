package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.MyFishShopCommentBean;
import com.project.dyuapp.mysign.DataUtils;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myviews.StarBarTouch;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/11/25 0025.
 *
 * @description 个人中心-渔具店适配器
 * @change
 */


public class CenterShopCommentAdapter extends MyCommonAdapter<MyFishShopCommentBean> {


    @Bind(R.id.item_shop_comment_tv_name)
    TextView tvName;
    @Bind(R.id.item_shop_ll_level)
    LinearLayout itemShopLlLevel;
    @Bind(R.id.item_shop_comment_tv_content)
    TextView tvContent;
    @Bind(R.id.item_shop_comment_tv_time)
    TextView tvTime;

    public CenterShopCommentAdapter(List<MyFishShopCommentBean> list, Context mContext, int resid) {
        super(list, mContext, resid);

    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, final int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        String cContent = list.get(position).getC_content();
        String createTime = list.get(position).getCreatetime();
        String shopName = list.get(position).getShop_name();
        String cCodes = list.get(position).getC_codes();
        if (!TextUtils.isEmpty(cContent)) {
            tvContent.setText(cContent);

        }
        if (!TextUtils.isEmpty(createTime)) {
            tvTime.setText(DataUtils.convertDate(createTime, "yyyy-MM-dd"));
        }
        if (!TextUtils.isEmpty(shopName)) {
            tvName.setText(shopName);
        }
        if (!TextUtils.isEmpty(cCodes)) {
            //星级
            SPUtils.setLevel(itemShopLlLevel, mContext, Integer.parseInt(cCodes));
        }
    }

}
