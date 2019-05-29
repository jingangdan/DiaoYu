package com.project.dyuapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.project.dyuapp.R;
import com.project.dyuapp.activity.CityDiaoyouActivity;
import com.project.dyuapp.activity.CityFishingPlaceActivity;
import com.project.dyuapp.activity.CityFishingShopActivity;
import com.project.dyuapp.activity.SkillDetailsActivity;
import com.project.dyuapp.activity.WeatherActivity;
import com.project.dyuapp.adapter.CityWideTypeAdapter;
import com.project.dyuapp.adapter.PostListAdapter;
import com.project.dyuapp.base.MyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.CityWideEntity;
import com.project.dyuapp.entity.CityWideTypeEntity;
import com.project.dyuapp.entity.ForumEntity;
import com.project.dyuapp.entity.ForumListEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.ToastUtils;
import com.project.dyuapp.myviews.GridViewForScrollView;
import com.project.dyuapp.myviews.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @describe：同城
 * @author：刘晓丽
 * @createdate：2017/8/18 11:00
 * 界面：gqf
 */

public class CityWideFragment extends MyBaseFragment {

    @Bind(R.id.city_wide_ngv)
    GridViewForScrollView ngv;
    @Bind(R.id.city_wide_nlv)
    ListViewForScrollView nlv;
    @Bind(R.id.title_tv_title)
    TextView tvTitle;
    @Bind(R.id.city_wide_psv)
    PullToRefreshScrollView psv;

    private PostListAdapter mAdapter;
    private ArrayList<ForumEntity> mData = new ArrayList<>();
    private List<ForumEntity> mDataRecommend = new ArrayList<>();
    private ArrayList<CityWideTypeEntity> mDataType;

    private int page = 1;
    private boolean isGetInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_wide, null);
        ButterKnife.bind(this, view);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("同城");
        initNgv();
        initNlv();
        isGetInfo = true;
        okHttpForumList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isGetInfo) {
            okHttpForumList();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && !isGetInfo) {
            okHttpForumList();
        }
    }

    private void initNgv() {
        CityWideTypeAdapter mAdapterType = new CityWideTypeAdapter(mDataType, getActivity());
        ngv.setAdapter(mAdapterType);
        ngv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), CityDiaoyouActivity.class));
                        //同城钓友
                        break;
                    case 1:
                        //钓鱼天气
                        startActivity(new Intent(getActivity(), WeatherActivity.class));
                        break;
                    case 2:
                        //钓场
                        startActivity(new Intent(getActivity(), CityFishingPlaceActivity.class));
                        break;
                    case 3:
                        //渔具店
                        startActivity(new Intent(getActivity(), CityFishingShopActivity.class));
                        break;

                }
            }
        });
    }

    private void initNlv() {
        mAdapter = new PostListAdapter(mData, getActivity());
        nlv.setAdapter(mAdapter);
        nlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), SkillDetailsActivity.class)
                        .putExtra("id",mData.get(position).getF_id()));
            }
        });
        psv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page = 1;
                okHttpForumList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                okHttpCityWideTiezi();
            }
        });
    }

    //同城
    public void okHttpForumList() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.cityWide);
        commonOkhttp.putParams("latitude", SPUtils.getPreference(getActivity(), "latitude"));
        commonOkhttp.putParams("longitude", SPUtils.getPreference(getActivity(), "longitude"));
        commonOkhttp.putCallback(new MyGenericsCallback<CityWideEntity>(getActivity()) {
            @Override
            public void onSuccess(CityWideEntity data, int d) {
                super.onSuccess(data, d);
                mData.clear();
                //数量
                mDataType = new ArrayList<>();
                mDataType.add(new CityWideTypeEntity(R.mipmap.find_friend, "同城钓友", "共有<font color='#f092a6'>" + data.getDyNum() + "</font>位钓友"));
                mDataType.add(new CityWideTypeEntity(R.mipmap.find_weather, "钓鱼天气", data.getWeather().getLocation() + data.getWeather().getCond_txt_d() + "<font color='#f092a6'>" + data.getWeather().getTmp_max() + "/" + data.getWeather().getTmp_min() + "℃</font>"));
                mDataType.add(new CityWideTypeEntity(R.mipmap.find_site, "钓场", "发现钓场<font color='#f092a6'>" + data.getDcNum() + "</font>个"));
                mDataType.add(new CityWideTypeEntity(R.mipmap.find_shop, "渔具店", "渔具店<font color='#f092a6'>" + data.getYjdNum() + "</font>个"));
                CityWideTypeAdapter mAdapterType = new CityWideTypeAdapter(mDataType, getActivity());
                ngv.setAdapter(mAdapterType);
                mDataRecommend = data.getDongtai();
                page = 1;
                okHttpCityWideTiezi();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                psv.onRefreshComplete();
            }

            @Override
            public void onOther(JsonResult<CityWideEntity> data, int d) {
                super.onOther(data, d);
                psv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }

    public void okHttpCityWideTiezi() {
        String forumId="";
        for (ForumEntity item:mDataRecommend){
            forumId+=item.getF_id()+",";
        }
        if (!TextUtils.isEmpty(forumId)){
            forumId=forumId.substring(0,forumId.length()-1);
        }
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.cityWideTiezi);
        commonOkhttp.putParams("tiziid",forumId );//不需要查的帖子ID（用,隔开）
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putCallback(new MyGenericsCallback<ForumListEntity>(getActivity()) {
            @Override
            public void onSuccess(ForumListEntity data, int d) {
                super.onSuccess(data, d);
                if (page == 1) {
                    mData.clear();
                    mData.addAll(mDataRecommend);
                }
                if (data != null && data.size() > 0) {
                    mData.addAll(data);
                    ++page;
                } else {
                    if (page == 1) {
                        ToastUtils.getInstance(getActivity()).showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        ToastUtils.getInstance(getActivity()).showMessage(getResources().getString(R.string.list_bottom));
                    }
                }
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                psv.onRefreshComplete();
                isGetInfo = false;
            }

            @Override
            public void onOther(JsonResult<ForumListEntity> data, int d) {
                super.onOther(data, d);
                psv.onRefreshComplete();
                isGetInfo = false;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                psv.onRefreshComplete();
                isGetInfo = false;
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
