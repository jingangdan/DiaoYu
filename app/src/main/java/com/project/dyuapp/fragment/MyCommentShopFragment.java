package com.project.dyuapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.activity.FishingShopDetailsActivity;
import com.project.dyuapp.adapter.CenterShopCommentAdapter;
import com.project.dyuapp.base.LazyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.MyFishShopCommentBean;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/11/28 0028.
 *
 * @description 个人中心-我的渔具店-我点评的
 * @change
 */


public class MyCommentShopFragment extends LazyBaseFragment {


    @Bind(R.id.my_comment_shop_lv)
    PullToRefreshListView myCommentShopLv;

    private List<MyFishShopCommentBean> myCommentShopList;//列表集合
    private CenterShopCommentAdapter mAdapter;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_comment_shop, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            page = 1;
            okhttpMyFishShop();
        }
    }

    /**
     * 初始化数据
     */
    private void initView() {

        myCommentShopLv.setMode(PullToRefreshBase.Mode.BOTH);
        myCommentShopList = new ArrayList<>();
        mAdapter = new CenterShopCommentAdapter(myCommentShopList, getActivity(), R.layout.item_shop_my_comment);
        myCommentShopLv.setAdapter(mAdapter);
        //条目点击事件
        myCommentShopLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), FishingShopDetailsActivity.class)
                        .putExtra("id", myCommentShopList.get(i - 1).getFishing_shop_id())
                        .putExtra("lat", myCommentShopList.get(i - 1).getLatitude())
                        .putExtra("lon", myCommentShopList.get(i - 1).getLongitude())
                        .putExtra("cityid", myCommentShopList.get(i - 1).getCityid()));
            }
        });
        myCommentShopLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                okhttpMyFishShop();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                okhttpMyFishShop();
            }
        });


    }

    /**
     * 个人中心-渔具店：我评论的接口数据
     */
    private void okhttpMyFishShop() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.myFishShop);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.MyFishShopCommentEntity>(getActivity()) {
            @Override
            public void onSuccess(NetBean.MyFishShopCommentEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    if (page == 1) {
                        myCommentShopList.clear();
                    }
                    if (data != null && data.size() != 0) {
                        myCommentShopList.addAll(data);
                        ++page;
                    } else {
                        commonOkhttp.showNoData(getActivity(), page);
                    }
                    mAdapter.notifyDataSetChanged();
                    if (myCommentShopLv != null) {
                        myCommentShopLv.onRefreshComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(getActivity()).showMessage(getActivity().getResources().getString(R.string.data_parsing_failed));
                }


            }
        });
        commonOkhttp.Execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
