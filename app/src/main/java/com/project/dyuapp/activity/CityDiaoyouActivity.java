package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CityDiaoyouAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.callback.StateEntity;
import com.project.dyuapp.entity.EmptyResultBean;
import com.project.dyuapp.entity.FishingFriendEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author shipeiyun
 * @description 同城-同城钓友
 * @created at 2017/12/4 0004
 */
public class CityDiaoyouActivity extends MyBaseActivity {

    @Bind(R.id.city_diaoyou_lv)
    PullToRefreshListView cityDiaoyouLv;

    private List<FishingFriendEntity> mList = new ArrayList<>();//列表集合
    private CityDiaoyouAdapter cityAdapter;
    private int page = 1;
    private String lat = "";
    private String lng = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_city_diaoyou);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("同城钓友");
        initView();
        page = 1;
        okhttpFishingFriend();
    }

    /**
     * 初始化数据
     */
    private void initView() {
        cityDiaoyouLv.setMode(PullToRefreshBase.Mode.BOTH);
        cityAdapter = new CityDiaoyouAdapter(CityDiaoyouActivity.this, mList, R.layout.item_city_diaoyou);
        cityDiaoyouLv.setAdapter(cityAdapter);
        //条目点击事件
        cityDiaoyouLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(CityDiaoyouActivity.this, FansMainPagerActivity.class)
                        .putExtra("id", mList.get(i - 1).getMember_list_id()));
            }
        });
        //按钮点击事件
        cityAdapter.setOnItemClickListener(new CityDiaoyouAdapter.OnItemClickListener() {
            @Override
            public void onClickview(View view, int position) {
                okHttpAttention(mList.get(position).getMember_list_id());
            }
        });
        cityDiaoyouLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                okhttpFishingFriend();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                okhttpFishingFriend();
            }
        });
    }

    /**
     * 7同城-附近钓友
     */
    private void okhttpFishingFriend() {
        lat = PublicStaticData.sharedPreferences.getString("latitude", "");
        lng = PublicStaticData.sharedPreferences.getString("longitude", "");
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.fishingFriend);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putParams("longitude", lng);
        commonOkhttp.putParams("latitude", lat);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.FishingFriendListEntity>(CityDiaoyouActivity.this) {
            @Override
            public void onSuccess(NetBean.FishingFriendListEntity data, int d) {
                super.onSuccess(data, d);
                if (page == 1) {
                    mList.clear();
                }
                if (data != null && data.size() > 0) {
                    mList.addAll(data);
                    ++page;
                } else {
                    if (page == 1) {
                        Toast.makeText(CityDiaoyouActivity.this, getString(R.string.list_no_data), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CityDiaoyouActivity.this, getString(R.string.list_bottom), Toast.LENGTH_SHORT).show();
                    }
                }
                cityAdapter.notifyDataSetChanged();
                cityDiaoyouLv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 关注、取消关注
     */
    private void okHttpAttention(String objectId) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.follow);
        commonOkhttp.putParams("object_type", "4");//关注对象：1帖子  2钓场  3渔具店 4用户
        commonOkhttp.putParams("object_id", objectId);
        commonOkhttp.putCallback(new MyGenericsCallback<StateEntity>(this) {
            @Override
            public void onSuccess(StateEntity data, int d) {
                super.onSuccess(data, d);
                page = 1;
                okhttpFishingFriend();
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }

}
