package com.project.dyuapp.adapter;

import android.content.Context;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/12/7 0007.
 *
 * @description
 * @change
 */


public class HomeVideoTypeAdapter extends MyCommonAdapter<String> {

    public HomeVideoTypeAdapter(List<String> list, Context mContext, int resid) {
        super(list, mContext, R.layout.item_home_gv_video);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());

    }
}
