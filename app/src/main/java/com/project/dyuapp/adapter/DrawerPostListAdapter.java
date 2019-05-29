package com.project.dyuapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.DrawerListBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/11/29 0029.
 *
 * @description 抽屉渔获帖子列表适配器
 * @change
 */


public class DrawerPostListAdapter extends MyCommonAdapter<DrawerListBean> {
    @Bind(R.id.item_drawer_chb_value)
    CheckBox chbValue;
    private OnItemClickListener onItemClickListener;
    private int selectPosition;

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public interface OnItemClickListener {
        void onCustomClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DrawerPostListAdapter(List<DrawerListBean> list, Context mContext, int resid) {
        super(list, mContext, R.layout.item_drawer_layout);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, final int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        chbValue.setText(list.get(position).getName());
        if (list.get(position).isSelect()) {//状态选中
            chbValue.setChecked(true);
        } else {
            chbValue.setChecked(false);
        }
        if (selectPosition == position) {
            chbValue.setChecked(true);
        } else {
            chbValue.setChecked(false);
        }
        if (onItemClickListener != null) {
            chbValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onCustomClick(chbValue, position);
                }
            });
        }
    }
}
