package com.project.dyuapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.FishingGearAreaBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/11/29 0029.
 *
 * @description 抽屉选择区域列表适配器
 * @change
 */


public class DrawerAreaListAdapter extends MyCommonAdapter<FishingGearAreaBean> {
    @Bind(R.id.item_type_tv_name)
    TextView tvName;
    @Bind(R.id.item_type_img_select)
    ImageView imgSelect;
    @Bind(R.id.tv_type_line)
    TextView tvLine;
    private int selectPosition;

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public DrawerAreaListAdapter(List<FishingGearAreaBean> list, Context mContext, int resid) {
        super(list, mContext, resid);
    }


    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, final int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        tvLine.setVisibility(View.GONE);
        tvName.setText(list.get(position).getName());
        if (selectPosition == position) {
            imgSelect.setVisibility(View.VISIBLE);
        } else {
            imgSelect.setVisibility(View.GONE);
        }
    }
}
