package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.SiteCategoryVideoAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.entity.NewestListBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 田亭亭
 * @description 首页-视频-列表条目activity
 * @created at 2017/12/7 0007
 * @change
 */
public class HomeVideoTypeActivity extends MyBaseActivity {
    @Bind(R.id.home_video_type_gv)
    PullToRefreshGridView homeVideoTypeGv;
    private List<NewestListBean> homeVideoTypeList;
    private int page = 1;
    private String type = "";
    private SiteCategoryVideoAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_home_video_type);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化数据
     */
    private void initView() {
        setTvTitle(getIntent().getStringExtra("item_title"));
        setIvBack();
        type = getIntent().getStringExtra("type");
        okhttpVideosMore();
        homeVideoTypeList = new ArrayList<>();
        homeVideoTypeGv.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new SiteCategoryVideoAdapter(homeVideoTypeList, HomeVideoTypeActivity.this, R.layout.item_home_gv_video);
        homeVideoTypeGv.setAdapter(mAdapter);
        homeVideoTypeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String articleId = homeVideoTypeList.get(position).getArticle_id();
                startActivity(new Intent(HomeVideoTypeActivity.this, VideoDetailsActivity.class).putExtra("article_id", articleId));
            }
        });
        homeVideoTypeGv.setMode(PullToRefreshBase.Mode.BOTH);
        homeVideoTypeGv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                page = 1;
                okhttpVideosMore();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                okhttpVideosMore();
            }
        });

    }

    /**
     * 全部视频列表接口
     * type Y 1 最新视频 2 钓鱼大全3钓鱼教学4四海钓鱼 5 快乐垂钓
     * page N 1 不传默认是1
     */
    private void okhttpVideosMore() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.videosMore);
        commonOkhttp.putParams("type", type);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.NewestListEntity>(HomeVideoTypeActivity.this) {
            @Override
            public void onSuccess(NetBean.NewestListEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    if (page == 1) {
                        homeVideoTypeList.clear();
                    }
                    if (data != null && data.size() != 0) {
                        homeVideoTypeList.addAll(data);
                        ++page;
                    } else {
                        commonOkhttp.showNoData(HomeVideoTypeActivity.this, page);
                    }
                    mAdapter.notifyDataSetChanged();

                    if (homeVideoTypeGv != null) {
                        homeVideoTypeGv.onRefreshComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(HomeVideoTypeActivity.this).showMessage(HomeVideoTypeActivity.this.getResources().getString(R.string.data_parsing_failed));
                }

            }
        });
        commonOkhttp.Execute();
    }
}
