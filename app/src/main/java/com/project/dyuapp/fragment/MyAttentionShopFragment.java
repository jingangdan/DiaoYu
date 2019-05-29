package com.project.dyuapp.fragment;

import android.content.Intent;
import android.content.res.Resources;
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
import com.project.dyuapp.activity.MyFishShopAddBean;
import com.project.dyuapp.adapter.CenterFishingShopAdapter;
import com.project.dyuapp.base.LazyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.entity.SuccessBean;
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
 * @description 个人中心-我的渔具店-我关注的
 * @change
 */


public class MyAttentionShopFragment extends LazyBaseFragment {


    @Bind(R.id.my_attention_shop_lv)
    PullToRefreshListView myAttentionShopLv;
    private List<MyFishShopAddBean> myAttentionShopList = new ArrayList<>();//列表集合
    private CenterFishingShopAdapter mAdapter;
    private String lat = "";
    private String lng = "";
    private int page = 1;
    private String fishingShopId = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_attention_shop, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            page = 1;
            okhttpMyFishShopg();
        }
    }

    /**
     * 初始化数据
     */
    private void initView() {
        myAttentionShopLv.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new CenterFishingShopAdapter(myAttentionShopList, getActivity(), R.layout.item_center_fishing_shop, "1");
        myAttentionShopLv.setAdapter(mAdapter);
        //条目点击事件
        myAttentionShopLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivityForResult(new Intent(getActivity(), FishingShopDetailsActivity.class)
                        .putExtra("id", myAttentionShopList.get(i - 1).getFishing_shop_id())
                        .putExtra("lat", myAttentionShopList.get(i - 1).getLatitude())
                        .putExtra("lon", myAttentionShopList.get(i - 1).getLongitude())
                        .putExtra("cityid", myAttentionShopList.get(i - 1).getCityid()), PublicStaticData.MY_ATTENTION_SHOP);
            }
        });
        //按钮点击事件
        mAdapter.setOnItemClickListener(new CenterFishingShopAdapter.OnItemClickListener() {
            @Override
            public void onClickView(View view, int position) {

                fishingShopId = myAttentionShopList.get(position).getFishing_shop_id();
                okhttpIsAttention();
            }
        });
        myAttentionShopLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                okhttpMyFishShopg();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                okhttpMyFishShopg();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PublicStaticData.MY_ATTENTION_SHOP) {
            if (resultCode == PublicStaticData.FISHING_SHOP_DETAILS) {
                page = 1;
                okhttpMyFishShopg();
            }
        }


    }

    /**
     * 关注接口
     */
    private void okhttpIsAttention() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.ANGLING_FOLLOW_FISH);
        commonOkhttp.putParams("f_gid", fishingShopId);
        commonOkhttp.putCallback(new MyGenericsCallback<SuccessBean>(getActivity()) {
            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                try {
                    ToastUtils.getInstance(getActivity()).showMessage(message.toString());
                    page = 1;
                    okhttpMyFishShopg();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        commonOkhttp.Execute();
    }


    /**
     * 个人中心-渔具店：我关注的接口数据
     */
    private void okhttpMyFishShopg() {
        lat = PublicStaticData.sharedPreferences.getString("latitude", "");
        lng = PublicStaticData.sharedPreferences.getString("longitude", "");
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.myFishShopg);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putParams("lat", lat);
        commonOkhttp.putParams("lng", lng);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.MyFishShopAddEntity>(getActivity()) {
            @Override
            public void onSuccess(NetBean.MyFishShopAddEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    if (page == 1) {
                        myAttentionShopList.clear();
                    }
                    if (data != null && data.size() != 0) {
                        myAttentionShopList.addAll(data);
                        ++page;
                    } else {
                        commonOkhttp.showNoData(getActivity(), page);
                    }
                    mAdapter.notifyDataSetChanged();
                    if (myAttentionShopLv != null) {
                        myAttentionShopLv.onRefreshComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(getActivity()).showMessage(getActivity().getResources().getString(R.string.data_parsing_failed));
                }
            }

            @Override
            public void onOther(JsonResult<NetBean.MyFishShopAddEntity> data, int d) {
                super.onOther(data, d);
                if (data.getCode() == 512) {
                    ToastUtils.getInstance(getActivity()).showMessage(data.getMessage());
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
