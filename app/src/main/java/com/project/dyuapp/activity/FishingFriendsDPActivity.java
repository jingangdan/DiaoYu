package com.project.dyuapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lzy.imagepicker.bean.ImageItem;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.FishingFriendDPEntity;
import com.project.dyuapp.entity.FishingFriendDPListEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyPicMethodUtil;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.ToastUtils;
import com.project.dyuapp.myutils.Utils;
import com.project.dyuapp.myviews.GridViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 钓场详情-钓友点评
 * Created by jingang on 2018/5/11.
 */

public class FishingFriendsDPActivity extends MyBaseActivity {
    @Bind(R.id.lv_finshingFriendDP)
    PullToRefreshListView lvFinshingFriendDP;
    private String fishingShopId;
    private int page = 1;

    private List<FishingFriendDPEntity> dpList;
    private Adapter mAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_fishing_place_details_dp);
        ButterKnife.bind(this);
        setTvTitle("钓友点评");
        setIvBack();

        fishingShopId = getIntent().getStringExtra("fishing_shop_id");

        setAdapter();
        page = 1;
        okHttpFishingFriendDP();
    }

    /**
     * 适配数据
     */
    private void setAdapter() {
        dpList = new ArrayList<>();

        mAdapter = new Adapter(dpList, this);
        lvFinshingFriendDP.setAdapter(mAdapter);

        lvFinshingFriendDP.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        okHttpFishingFriendDP();
                        lvFinshingFriendDP.onRefreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        okHttpFishingFriendDP();
                        lvFinshingFriendDP.onRefreshComplete();
                    }
                }, 2000);
            }
        });
    }


    /**
     * 全部评论列表接口
     */
    public void okHttpFishingFriendDP() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.ANGLING_ANG_INFO_DP);
        commonOkhttp.putParams("f_gid", fishingShopId);//点评对象ID
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putParams("pagesize", "10");
        commonOkhttp.putCallback(new MyGenericsCallback<FishingFriendDPListEntity>(this) {
            @Override
            public void onSuccess(FishingFriendDPListEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    if (page == 1) {
                        dpList.clear();
                    }
                    if (data != null && data.size() != 0) {
                        dpList.addAll(data);
                        ++page;
                    } else {
                        commonOkhttp.showNoData(FishingFriendsDPActivity.this, page);
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(FishingFriendsDPActivity.this).showMessage(FishingFriendsDPActivity.this.getResources().getString(R.string.data_parsing_failed));
                }
            }
        });
        commonOkhttp.Execute();
    }

    public class Adapter extends MyCommonAdapter<FishingFriendDPEntity> {
        @Bind(R.id.item_fishing_friends_dp_head)
        PorterShapeImageView pimgHead;
        @Bind(R.id.item_fishing_friends_dp_tv_name)
        TextView tvName;
        @Bind(R.id.item_fishing_friends_dp_tv_time)
        TextView tvTime;
        @Bind(R.id.item_fishing_friends_dp_tv_content)
        TextView tvContent;
        @Bind(R.id.item_dp_ll_level)
        LinearLayout itemDpLlLevel;
        @Bind(R.id.item_fishing_friend_dp_img)
        GridViewForScrollView itemFishingFriendDpImg;

        public Adapter(List<FishingFriendDPEntity> list, Context mContext) {
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
                itemFishingFriendDpImg.setAdapter(new ImgAdapter(list.get(position).getContent(), mContext));
            } else {
                itemFishingFriendDpImg.setVisibility(View.GONE);
            }

            final ArrayList<ImageItem> dpImageItem = new ArrayList<>();

            for (int i = 0; i < list.get(position).getContent().size(); i++) {
                    dpImageItem.add(new ImageItem("", HttpUrl.IMAGE_URL + list.get(position).getContent().get(i).getStr_imgs()));
            }
            itemFishingFriendDpImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MyPicMethodUtil.preview(FishingFriendsDPActivity.this, dpImageItem, position);
                }
            });

        }
    }

    public static class ImgAdapter extends MyCommonAdapter<FishingFriendDPEntity.ContentBean> {
        @Bind(R.id.item_fishing_friends_dp_img)
        ImageView itemFishingFriendsDpImg;

        public ImgAdapter(List<FishingFriendDPEntity.ContentBean> list, Context mContext) {
            super(list, mContext, R.layout.item_fishimg_friend_dp_img);
        }

        @Override
        public void setDate(MyCommentViewHolder commentViewHolder, int position) {
            ButterKnife.bind(this, commentViewHolder.getConverView());
            if (!TextUtils.isEmpty(list.get(position).getStr_imgs())) {
                GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL + list.get(position).getStr_imgs(), itemFishingFriendsDpImg);
            }
        }
    }


}
