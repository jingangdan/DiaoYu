package com.project.dyuapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.project.dyuapp.R;
import com.project.dyuapp.activity.SkillDetailsActivity;
import com.project.dyuapp.adapter.PostListAdapter;
import com.project.dyuapp.base.LazyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.ForumEntity;
import com.project.dyuapp.entity.ForumListEntity;
import com.project.dyuapp.entity.IndexListEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myviews.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @describtion  首页技巧
 * @author gengqiufang
 * @created at 2017/12/14 0014
 */

public class HomeSkillFragment extends LazyBaseFragment {

    @Bind(R.id.home_post_lv)
    ListViewForScrollView homePostLv;

    private String catId = "";
    private PostListAdapter mAdapter;
    private List<ForumEntity> mData = new ArrayList<>();
    private int page = 1;
    private PullToRefreshBase<ScrollView> refreshView1;

    public static HomeSkillFragment newInstance(String catId) {
        Bundle bundle = new Bundle();
        bundle.putString("catId", catId);
        HomeSkillFragment myFragment = new HomeSkillFragment();
        myFragment.setArguments(bundle);
        return myFragment;
    }

    /**
     * 刷新、加载页面数据
     *
     * @param id
     * @param isRefresh
     * @param refreshView
     */
    public void refresh(String id, boolean isRefresh, PullToRefreshBase<ScrollView> refreshView) {
        refreshView1 = refreshView;
        catId = id;
        if (isRefresh) {
            page = 1;
        }
        if (TextUtils.isEmpty(catId)){
            //首页
            getHomeData(refreshView1);
        }else {
            //分类
            getData(refreshView1);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            catId = bundle.getString("catId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_home_skill, container, false);
        ButterKnife.bind(this, view);
        //适配数据
        mAdapter = new PostListAdapter(mData, getActivity());
        homePostLv.setAdapter(mAdapter);
        //点击列表跳至帖子详情
        homePostLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), SkillDetailsActivity.class).putExtra("id", mData.get(i).getF_id()));
            }
        });
        return view;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        /*if (isVisible) {
            //更新界面数据，如果数据还在下载中，就显示加载框
            tiXianDongJieAdapter.notifyDataSetChanged();
            if (mRefreshState == STATE_REFRESHING) {
                mRefreshListener.onRefreshing();
            }
        } else {
            //关闭加载框
            mRefreshListener.onRefreshFinish();
        }*/
    }

    @Override
    protected void onFragmentFirstVisible() {
        //去服务器下载数据
        /*mRefreshState = STATE_REFRESHING;
        mCategoryController.loadBaseData();*/
        page = 1;
        if (TextUtils.isEmpty(catId)){
            //首页
            getHomeData(refreshView1);
        }else {
            //分类
            getData(refreshView1);
        }

    }

    /**
     * 获取列表
     */
    private void getData(final PullToRefreshBase<ScrollView> refreshView) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.forumList);
        commonOkhttp.putParams("page", page + "");//分页
        commonOkhttp.putParams("rows", "6");//每页条数
        commonOkhttp.putParams("top_id", PublicStaticData.ID_SKILL);//帖子顶级分类
        if (!TextUtils.isEmpty(catId)) {
            commonOkhttp.putParams("cat_id", catId);//分类id
        }
        commonOkhttp.putCallback(new MyGenericsCallback<ForumListEntity>(getActivity()) {
            @Override
            public void onSuccess(ForumListEntity data, int d) {
                super.onSuccess(data, d);
                if (page == 1) {
                    mData.clear();
                }
                if (data != null && data.size() > 0) {
                    mData.addAll(data);
                    ++page;
                } else {
                    if (page == 1) {
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                }
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                if (refreshView != null && refreshView.isRefreshing()) {
                    refreshView.onRefreshComplete();
                }
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 获取技巧首页列表
     */
    private void getHomeData(final PullToRefreshBase<ScrollView> refreshView) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.indexList);
        commonOkhttp.putParams("top_id", PublicStaticData.ID_SKILL);//帖子顶级分类
        commonOkhttp.putParams("page", page+"");
        commonOkhttp.putCallback(new MyGenericsCallback<IndexListEntity>(getActivity()) {
            @Override
            public void onSuccess(IndexListEntity data, int d) {
                super.onSuccess(data, d);
                if (page == 1) {
                    mData.clear();
                    mData.addAll(data.getOneList());
                }
                if (data != null && data.getSenList().size() > 0) {
                    mData.addAll(data.getSenList());
                    ++page;
                } else {
                    if (page == 1) {
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                }
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                if (refreshView != null && refreshView.isRefreshing()) {
                    refreshView.onRefreshComplete();
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
