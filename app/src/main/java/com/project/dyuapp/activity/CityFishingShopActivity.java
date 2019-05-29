package com.project.dyuapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CityFishingShopAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.FishingGearEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 田亭亭
 * @description 同城-渔具店
 * @created at 2017/11/25 0025
 * @change
 */
public class CityFishingShopActivity extends MyBaseActivity {

    @Bind(R.id.city_fishing_shop_plv)
    PullToRefreshListView cityFishingShopPlv;
    private CityFishingShopAdapter mAdapter;
    private List<FishingGearEntity> cityFishingShopList;
    private int page = 1;
    private String lat = "";
    private String lng = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_city_fishing_shop);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        cityFishingShopList = new ArrayList<>();

        mAdapter = new CityFishingShopAdapter(cityFishingShopList, CityFishingShopActivity.this, R.layout.item_home_fishing_shop);
        cityFishingShopPlv.setAdapter(mAdapter);
        page = 1;
        okhttpFishingShop();
    }

    private void initView() {
        setIvBack();
        setTvTitle("渔具店");
        cityFishingShopPlv.setMode(PullToRefreshBase.Mode.BOTH);
        cityFishingShopPlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(CityFishingShopActivity.this, FishingShopDetailsActivity.class)
                        .putExtra("id", cityFishingShopList.get(position - 1).getFishing_shop_id())
                        .putExtra("lat", cityFishingShopList.get(position - 1).getLatitude())
                        .putExtra("lon", cityFishingShopList.get(position - 1).getLongitude())
                        .putExtra("cityid", cityFishingShopList.get(position - 1).getCityid()));
            }
        });
        cityFishingShopPlv.setMode(PullToRefreshBase.Mode.BOTH);
        cityFishingShopPlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                okhttpFishingShop();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                okhttpFishingShop();
            }
        });
    }

    /**
     * 同城-渔具店列表接口数据
     */
    private void okhttpFishingShop() {
        lat = PublicStaticData.sharedPreferences.getString("latitude", "");
        lng = PublicStaticData.sharedPreferences.getString("longitude", "");
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.fishingShop);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putParams("longitude", lng);
        commonOkhttp.putParams("latitude", lat);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.FishingGearListEntity>(this) {
            @Override
            public void onSuccess(NetBean.FishingGearListEntity data, int d) {
                super.onSuccess(data, d);
                System.out.println("=====数据========" + new Gson().toJson(data));
                try {
                    if (page == 1) {
                        cityFishingShopList.clear();
                    }
                    if (data != null && data.size() != 0) {
                        cityFishingShopList.addAll(data);
                        ++page;
                    } else {
                        commonOkhttp.showNoData(CityFishingShopActivity.this, page);
                    }
                    mAdapter.notifyDataSetChanged();
                    if (cityFishingShopPlv != null) {
                        cityFishingShopPlv.onRefreshComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(CityFishingShopActivity.this).showMessage(CityFishingShopActivity.this.getResources().getString(R.string.data_parsing_failed));
                }


            }

            @Override
            public void onOther(JsonResult<NetBean.FishingGearListEntity> data, int d) {
                super.onOther(data, d);
                System.out.println("=====数据========" + new Gson().toJson(data));
                System.out.println("=====数据========" + data.getMessage());
            }
        });
        commonOkhttp.Execute();
    }
}
