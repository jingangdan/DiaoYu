package com.project.dyuapp.adapter;

import android.content.Context;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.SiteCategoryEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author gengqiufang
 * @describtion 首页技巧，下拉分类
 * @created at 2017/12/2 0002
 */

public class ClassifyDownAdapter extends MyCommonAdapter<SiteCategoryEntity> {
    @Bind(R.id.pop_classify_item_tv)
    TextView tv;

    public ClassifyDownAdapter(List<SiteCategoryEntity> list, Context mContext) {
        super(list, mContext, R.layout.pop_classify_item);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        tv.setText(list.get(position).getName());
        if (list.get(position).isSel()){
            tv.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }else {
            tv.setTextColor(mContext.getResources().getColor(R.color.c_666666));
        }
    }
}
