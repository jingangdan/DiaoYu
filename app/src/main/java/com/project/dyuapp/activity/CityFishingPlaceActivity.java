package com.project.dyuapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.HomeFishingCityAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.HomeFishingPlaceEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author 田亭亭
 * @description 同城-钓场
 * @created at 2017/11/25 0025
 * @change
 */
public class CityFishingPlaceActivity extends MyBaseActivity {

    @Bind(R.id.city_fishing_place_plv)
    PullToRefreshListView cityFishingPlacePlv;
    private HomeFishingCityAdapter mAdapter;
    private List<HomeFishingPlaceEntity> cityFishingPlaceList;

    private int page = 1;//分页
    private String lat = "";
    private String lng = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_city_fishing_place);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        cityFishingPlaceList = new ArrayList<>();
        mAdapter = new HomeFishingCityAdapter(cityFishingPlaceList, CityFishingPlaceActivity.this, R.layout.item_home_fishing_place);
        cityFishingPlacePlv.setAdapter(mAdapter);
    }

    private void initView() {
        setIvBack();
        setTvTitle("钓场");
        cityFishingPlacePlv.setMode(PullToRefreshBase.Mode.BOTH);
        cityFishingPlacePlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CityFishingPlaceActivity.this, FishingPlaceDetailsActivity.class);
                intent.putExtra("FishingPlaceId", cityFishingPlaceList.get(position - 1).getF_gid());
                startActivity(intent);
            }
        });
        cityFishingPlacePlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                attentionPlaceList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                attentionPlaceList();
            }
        });
        attentionPlaceList();
    }

    /**
     * 我添加的
     */
    private void attentionPlaceList() {
        lat = PublicStaticData.sharedPreferences.getString("latitude", "");
        lng = PublicStaticData.sharedPreferences.getString("longitude", "");
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.RELEASE_FISHING_GROUNDDD);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putParams("longitude", lng);
        commonOkhttp.putParams("latitude", lat);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.HomeFishingPlaceListEntity>(this) {
            @Override
            public void onSuccess(NetBean.HomeFishingPlaceListEntity data, int d) {
                super.onSuccess(data, d);
                cityFishingPlacePlv.onRefreshComplete();
                if (page <= 1) {
                    cityFishingPlaceList.clear();
                    cityFishingPlaceList.addAll(data);
                } else {
                    cityFishingPlaceList.addAll(data);
                }
                mAdapter.notifyDataSetChanged();
            }


            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                cityFishingPlacePlv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                cityFishingPlacePlv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();

    }
}

