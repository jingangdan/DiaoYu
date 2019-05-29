package com.project.dyuapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.myviews.GridViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/12/7 0007.
 *
 * @description
 * @change
 */


public class HomeVideoAdapter extends MyCommonAdapter<String> {

    @Bind(R.id.home_video_item_tv_name)
    TextView tvName;
    @Bind(R.id.home_video_item_tv_more)
    TextView tvMore;
    @Bind(R.id.home_video_item_ll_more)
    LinearLayout homeVideoItemLlMore;
    @Bind(R.id.home_video_item_gv)
    GridViewForScrollView homeVideoItemGv;
    private List<String> itemList;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onAllClick(View view, int position);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HomeVideoAdapter(List<String> list, Context mContext, int resid) {
        super(list, mContext, resid);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, final int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        tvName.setText(list.get(position));
        itemList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            itemList.add("");
        }
        homeVideoItemGv.setAdapter(new MyCommonAdapter<String>(itemList, mContext, R.layout.item_home_gv_video) {
            @Override
            public void setDate(MyCommentViewHolder commentViewHolder, int position) {

            }
        });
        if (onItemClickListener != null) {
            homeVideoItemGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                    onItemClickListener.onItemClick(homeVideoItemGv, i);
                }
            });
            homeVideoItemLlMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onAllClick(homeVideoItemGv, position);
                }
            });
        }

    }
}
