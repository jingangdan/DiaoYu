package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.lzy.imagepicker.bean.ImageItem;
import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.FishingPlaceEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.Utils;
import com.project.dyuapp.myviews.GridViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jingang on 2018/5/12.
 */

public class FishingFriendDPAdapter extends MyCommonAdapter<FishingPlaceEntity.FishDianpingBean> {
    @Bind(R.id.item_fishing_friends_dp_head)
    PorterShapeImageView pimgHead;
    @Bind(R.id.item_fishing_friends_dp_tv_name)
    TextView tvName;
    @Bind(R.id.item_fishing_friends_dp_tv_time)
    TextView tvTime;
    @Bind(R.id.item_fishing_friends_dp_ll)
    LinearLayout itemFishingFriendsDpLl;
    @Bind(R.id.item_fishing_friends_dp_tv_content)
    TextView tvContent;
    @Bind(R.id.item_fishing_friends_dp_ll_1)
    LinearLayout itemFishingFriendsDpLl1;
    @Bind(R.id.item_fishing_friends_dp_rl)
    RelativeLayout itemFishingFriendsDpRl;
    @Bind(R.id.item_dp_ll_level)
    LinearLayout itemDpLlLevel;
    @Bind(R.id.item_fishing_friend_dp_img)
    GridViewForScrollView itemFishingFriendDpImg;
    ArrayList<ImageItem> imageItems = new ArrayList<>();

    private onItemClickListener onItemClickListener;


    public FishingFriendDPAdapter(List<FishingPlaceEntity.FishDianpingBean> list, Context mContext) {
        super(list, mContext, R.layout.item_fishing_friends_dp);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());

        GlideUtils.loadImageViewHead(mContext, HttpUrl.IMAGE_URL + list.get(position).getMember_list_headpic(), pimgHead);
        tvName.setText(list.get(position).getMember_list_nickname());
        if (!TextUtils.isEmpty(list.get(position).getIntro())) {
            tvContent.setText(list.get(position).getIntro());
        } else {
            tvContent.setVisibility(View.GONE);
        }
        tvTime.setText(Utils.convertDate(list.get(position).getAddtime(), "yyyy-MM-dd"));
        if (!TextUtils.isEmpty(list.get(position).getScore())) {
            SPUtils.setLevel(itemDpLlLevel, mContext, Integer.parseInt(list.get(position).getScore()));
        }

        if (list.get(position).getContent().size() > 0) {
            itemFishingFriendDpImg.setAdapter(new FishingDetailsImgAdapter(list.get(position).getContent(), mContext));
        } else {
            itemFishingFriendDpImg.setVisibility(View.GONE);
        }

//        itemFishingFriendDpImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                onItemClickListener.onItemClick(position);
//            }
//        });


    }

//    public void setOnItemClickListener(FishingFriendDPAdapter.onItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }

}
