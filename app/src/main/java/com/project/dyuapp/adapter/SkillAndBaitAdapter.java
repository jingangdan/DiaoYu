package com.project.dyuapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.SiteCategoryEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @describtion  分类类型
 * @author gengqiufang
 * @created at 2017/12/9 0009
 */


public class SkillAndBaitAdapter extends MyCommonAdapter<SiteCategoryEntity> {

    @Bind(R.id.item_type_tv_name)
    TextView tvName;
    @Bind(R.id.item_type_img_select)
    ImageView imgSelect;

    public SkillAndBaitAdapter(List<SiteCategoryEntity> list, Context mContext) {
        super(list, mContext, R.layout.item_fishing_place_type);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        if (list.get(position).isSel()) {
            imgSelect.setVisibility(View.VISIBLE);
        } else {
            imgSelect.setVisibility(View.GONE);
        }
        tvName.setText(list.get(position).getName());

    }
}
