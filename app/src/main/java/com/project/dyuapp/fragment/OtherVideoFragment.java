package com.project.dyuapp.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.project.dyuapp.R;
import com.project.dyuapp.activity.VideoDetailsActivity;
import com.project.dyuapp.adapter.SiteCategoryAdapter;
import com.project.dyuapp.adapter.SiteCategoryVideoAdapter;
import com.project.dyuapp.base.LazyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.entity.NewestListBean;
import com.project.dyuapp.entity.SiteCategoryEntity;
import com.project.dyuapp.entity.SiteCategoryListEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.ToastUtils;
import com.project.dyuapp.myviews.SupportPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author ${tiantingting}
 * @created on 2017/12/4 0027
 * @description 视频--首页
 * @change ${}
 */
public class OtherVideoFragment extends LazyBaseFragment {


    @Bind(R.id.other_video_gv)
    PullToRefreshGridView otherVideoGv;
    @Bind(R.id.other_video_lv_type)
    RecyclerView otherVideoLvType;
    @Bind(R.id.other_video_ll_type_up)
    LinearLayout otherVideoLlTypeUp;
    @Bind(R.id.other_video_view)
    View otherVideoView;
    @Bind(R.id.other_video_fl)
    FrameLayout otherVideoFl;
    @Bind(R.id.bg_view)
    View bgView;
    private List<NewestListBean> itemOtherVideoList;
    private List<SiteCategoryEntity> typeList;
    SiteCategoryVideoAdapter mAdapter;//视频列表集合适配器
    private int selectPosition = 0;
    private SmoothScrollLayoutManager linearLayoutManager;
    private String articleId = "";
    private SiteCategoryAdapter adapter;
    private SiteCategoryAdapter fruitAdapter;
    public String cId = "";
    public String secondCid = "";
    private int page = 1;
    private SupportPopupWindow popDate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_video, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getArguments() != null) {
                cId = getArguments().getString("cid");
                if (!TextUtils.isEmpty(cId)) {
                    selectPosition = 0;
                    okHttpGetSiteCategory();
                }
            }
        }
    }


    /**
     * 初始化数据
     */
    private void initView() {
        //视频列表适配器
        initVideoAdapter();
    }

    /**
     * 二级分类适配器
     */
    private void initTypeAdapter() {
        linearLayoutManager = new SmoothScrollLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        otherVideoLvType.setLayoutManager(linearLayoutManager);
        adapter = new SiteCategoryAdapter(getActivity(), typeList);
        otherVideoLvType.setAdapter(adapter);
        secondCid = typeList.get(selectPosition).getD_id();
        adapter.setSelectPosition(selectPosition);
        adapter.setOnItemClickListener(new SiteCategoryAdapter.OnItemClickListener() {
            @Override
            public void onRecycleOnClick(View view, int position) {
                secondCid = typeList.get(position).getD_id();
                adapter.setSelectPosition(position);
                adapter.notifyDataSetChanged();
                secondCid = typeList.get(position).getD_id();
                page = 1;
                okhttpNewest();
                if (popDate != null) {
                    popDate.dismiss();
                }

            }
        });
    }

    public class SmoothScrollLayoutManager extends LinearLayoutManager {

        public SmoothScrollLayoutManager(Context context) {
            super(context);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, final int position) {
            LinearSmoothScroller smoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {
                        // 返回：滑过1px时经历的时间(ms)。
                        @Override
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return 150f / displayMetrics.densityDpi;
                        }
                    };

            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }
    }


    /**
     * 视频列表适配器
     */
    private void initVideoAdapter() {
        itemOtherVideoList = new ArrayList<>();
        mAdapter = new SiteCategoryVideoAdapter(itemOtherVideoList, getActivity(), R.layout.item_home_gv_video);
        otherVideoGv.setAdapter(mAdapter);
        otherVideoGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                articleId = itemOtherVideoList.get(position).getArticle_id();
                startActivity(new Intent(getActivity(), VideoDetailsActivity.class).putExtra("article_id", articleId));
            }
        });
        otherVideoGv.setMode(PullToRefreshBase.Mode.BOTH);
        otherVideoGv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                page = 1;
                okhttpNewest();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                okhttpNewest();
            }
        });
    }


    //4站点分类 集合接口
    public void okHttpGetSiteCategory() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.getSiteCategory);
        commonOkhttp.putParams("cid", cId);
        commonOkhttp.putCallback(new MyGenericsCallback<SiteCategoryListEntity>(getActivity()) {
            @Override
            public void onSuccess(SiteCategoryListEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    typeList = new ArrayList<>();
                    if (data != null && data.size() != 0) {
                        typeList.addAll(data);
                        initTypeAdapter();
                    }
                    secondCid = typeList.get(selectPosition).getD_id();
                    page = 1;
                    okhttpNewest();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 二级分类（弹框）数据
     */
    public void okHttpPopGetSiteCategory() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.getSiteCategory);
        commonOkhttp.putParams("cid", cId);
        commonOkhttp.putCallback(new MyGenericsCallback<SiteCategoryListEntity>(getActivity()) {
            @Override
            public void onSuccess(SiteCategoryListEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    typeList = new ArrayList<>();
                    if (data != null && data.size() != 0) {
                        typeList.addAll(data);
                        selectPosition = adapter.getSelectPosition();
                        showSiteCategoryPop(otherVideoView);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        commonOkhttp.Execute();
    }

    @OnClick(R.id.other_video_ll_type_up)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.other_video_ll_type_up:
                //查看更多
                otherVideoFl.setVisibility(View.INVISIBLE);
                bgView.setVisibility(View.VISIBLE);
                okHttpPopGetSiteCategory();
                break;
        }
    }

    public void showSiteCategoryPop(View view) {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_site_category_layout, null);

        popDate = new SupportPopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (popDate.isShowing()) {
            return;
        }
        RecyclerView rvType = (RecyclerView) popupView.findViewById(R.id.pop_site_category_rv_type);
        LinearLayout llOn = (LinearLayout) popupView.findViewById(R.id.pop_site_category_ll_type_on);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        rvType.setLayoutManager(layoutManager);
        llOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //收起
                otherVideoFl.setVisibility(View.VISIBLE);
                adapter.setSelectPosition(selectPosition);
                adapter.notifyDataSetChanged();
                bgView.setVisibility(View.GONE);
                otherVideoLvType.smoothScrollToPosition(selectPosition);
                popDate.dismiss();
            }
        });
        fruitAdapter = new SiteCategoryAdapter(getActivity(), typeList);
        rvType.setAdapter(fruitAdapter);
        fruitAdapter.setSelectPosition(selectPosition);
        secondCid = typeList.get(selectPosition).getD_id();
        fruitAdapter.setOnItemClickListener(new SiteCategoryAdapter.OnItemClickListener() {
            @Override
            public void onRecycleOnClick(View view, int position) {
                selectPosition = position;
                fruitAdapter.setSelectPosition(position);
                fruitAdapter.notifyDataSetChanged();
                secondCid = typeList.get(position).getD_id();
                otherVideoLvType.smoothScrollToPosition(selectPosition);
                page = 1;
                okhttpNewest();
                if (popDate != null) {
                    popDate.dismiss();
                }
            }
        });
        popDate.setOutsideTouchable(true);
        popDate.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                otherVideoFl.setVisibility(View.VISIBLE);
                bgView.setVisibility(View.GONE);
                adapter.setSelectPosition(selectPosition);
                adapter.notifyDataSetChanged();
                popDate.dismiss();
            }
        });
        bgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDate.dismiss();
            }
        });
        popDate.setBackgroundDrawable(new BitmapDrawable());


        popDate.showAsDropDown(view);
    }

    /**
     * 二级分类下的列表接口
     */
    private void okhttpNewest() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.newest);
        commonOkhttp.putParams("c_id", secondCid);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.NewestListEntity>(getActivity()) {
            @Override
            public void onSuccess(NetBean.NewestListEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    if (page == 1) {
                        itemOtherVideoList.clear();
                    }
                    if (data != null && data.size() != 0) {
                        itemOtherVideoList.addAll(data);
                        ++page;
                    } else {
                        commonOkhttp.showNoData(getActivity(), page);
                    }
                    mAdapter.notifyDataSetChanged();

                    if (otherVideoGv != null) {
                        otherVideoGv.onRefreshComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(getActivity()).showMessage(getActivity().getResources().getString(R.string.data_parsing_failed));
                }

            }
        });
        commonOkhttp.Execute();
    }

}
