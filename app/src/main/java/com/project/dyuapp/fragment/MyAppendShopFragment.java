package com.project.dyuapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.activity.FishingShopDetailsActivity;
import com.project.dyuapp.activity.MyFishShopAddBean;
import com.project.dyuapp.adapter.CenterFishingShopAdapter;
import com.project.dyuapp.base.LazyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/11/28 0028.
 *
 * @description 个人中心-我的渔具店-我添加的的
 * @change
 */


public class MyAppendShopFragment extends LazyBaseFragment {

    @Bind(R.id.my_append_shop_lv)
    PullToRefreshListView myAppendShopLv;

    private List<MyFishShopAddBean> myAppendShopList = new ArrayList<>();//列表集合
    private CenterFishingShopAdapter mAdapter;

    private String lat = "";
    private String lng = "";
    private int page = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_append_shop, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            page = 1;
            okhttpMyFishShopAdd();
        }
    }
    /**
     * 初始化数据
     */
    private void initView() {
        myAppendShopLv.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new CenterFishingShopAdapter(myAppendShopList, getActivity(), R.layout.item_center_fishing_shop, "2");
        myAppendShopLv.setAdapter(mAdapter);
        //条目点击事件
        myAppendShopLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String shopStatus = myAppendShopList.get(position - 1).getShop_status();
                if (!TextUtils.isEmpty(shopStatus)) {
                    //1待审核2已通过3不通过
                    if (TextUtils.equals(shopStatus, "1") || TextUtils.equals(shopStatus, "3")) {
                        //1待审核.3未通过
                        return;
                    } else if (TextUtils.equals(shopStatus, "2")) {
                        //2已通过
                        startActivity(new Intent(getActivity(), FishingShopDetailsActivity.class)
                                .putExtra("id", myAppendShopList.get(position - 1).getFishing_shop_id())
                                .putExtra("lat", myAppendShopList.get(position - 1).getLatitude())
                                .putExtra("lon", myAppendShopList.get(position - 1).getLongitude())
                                .putExtra("cityid", myAppendShopList.get(position - 1).getCityid()));
                    }

                }
            }
        });

        myAppendShopLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()

        {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                okhttpMyFishShopAdd();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                okhttpMyFishShopAdd();
            }
        });


    }

    /**
     * 个人中心-渔具店：我添加接口数据
     */

    private void okhttpMyFishShopAdd() {
        lat = PublicStaticData.sharedPreferences.getString("latitude", "");
        lng = PublicStaticData.sharedPreferences.getString("longitude", "");
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.myFishShopAdd);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putParams("lat", lat);
        commonOkhttp.putParams("lng", lng);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.MyFishShopAddEntity>(getActivity()) {
            @Override
            public void onSuccess(NetBean.MyFishShopAddEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    if (page == 1) {
                        myAppendShopList.clear();
                    }
                    if (data != null && data.size() != 0) {
                        myAppendShopList.addAll(data);
                        ++page;
                    } else {
                        commonOkhttp.showNoData(getActivity(), page);
                    }
                    mAdapter.notifyDataSetChanged();
                    if (myAppendShopLv != null) {
                        myAppendShopLv.onRefreshComplete();
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
