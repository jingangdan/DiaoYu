package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.CityWideTypeEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author gengqiufang
 * @describtion 同城头布局，类型适配器
 * @created at 2017/12/2 0002
 */

public class CityWideTypeAdapter extends MyCommonAdapter<CityWideTypeEntity> {

    @Bind(R.id.city_wide_iv_pic)
    ImageView ivPic;
    @Bind(R.id.city_wide_tv_title)
    TextView tvTitle;
    @Bind(R.id.city_wide_tv_num)
    TextView tvNum;

    public CityWideTypeAdapter(List<CityWideTypeEntity> list, Context mContext) {
        super(list, mContext, R.layout.item_city_wide);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        ivPic.setImageResource(list.get(position).getPic());
        tvTitle.setText(list.get(position).getTitle());
        tvNum.setText(Html.fromHtml(list.get(position).getNum()));
    }
}
