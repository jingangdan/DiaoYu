package com.project.dyuapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.project.dyuapp.R;
import com.project.dyuapp.activity.SkillDetailsActivity;
import com.project.dyuapp.activity.VideoDetailsActivity;
import com.project.dyuapp.adapter.PostList2Adapter;
import com.project.dyuapp.base.LazyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.ForumNewEntity;
import com.project.dyuapp.entity.ForumNewListEntity;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @describe：
 * @author：刘晓丽
 * @createdate：2017/11/23 15:00
 */

public class HomePostFragment extends LazyBaseFragment {

    @Bind(R.id.home_post_lv)
    ListView homePostLv;


    private int flag = 0;
    private PostList2Adapter mAdapter;
//    private List<ForumEntity> mData = new ArrayList<>();
    private List<ForumNewEntity.DataBean> mData = new ArrayList<>();
    private int page = 1;
    private PullToRefreshBase<ScrollView> refreshView1;

    public static HomePostFragment newInstance(int flag) {
        Bundle bundle = new Bundle();
        bundle.putInt("flag", flag);
        HomePostFragment myFragment = new HomePostFragment();
        myFragment.setArguments(bundle);
        return myFragment;
    }

    /**
     * 刷新、加载页面数据
     *
     * @param position
     * @param isRefresh
     * @param refreshView
     */
    public void refresh(int position, boolean isRefresh, PullToRefreshBase<ScrollView> refreshView) {
        refreshView1 = refreshView;
        flag = position;
        if (isRefresh) {
            page = 1;
        }
        getData(refreshView1);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            flag = bundle.getInt("flag");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_home_post, container, false);
        ButterKnife.bind(this, view);
        //适配数据
        mAdapter = new PostList2Adapter(mData, getActivity());
        homePostLv.setAdapter(mAdapter);
        //点击列表跳至帖子详情
        homePostLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mData.get(i).getType().equals("forum")){
                    //帖子
                    startActivity(new Intent(getActivity(), SkillDetailsActivity.class).putExtra("id", mData.get(i).getId()));
                }else if(mData.get(i).getType().equals("video")){
                    //视频
                    startActivity(new Intent(getActivity(), VideoDetailsActivity.class)
                            .putExtra("article_id", mData.get(i).getId()));
                }

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
        getData(refreshView1);
    }

    /**
     * 获取列表
     */
//    private void getData(final PullToRefreshBase<ScrollView> refreshView) {
//        CommonOkhttp commonOkhttp = new CommonOkhttp();
//        commonOkhttp.putUrl(HttpUrl.forumCate);
//        commonOkhttp.putParams("page", page + "");
//        commonOkhttp.putParams("rows", "6");
//        commonOkhttp.putParams("cateid", (flag + 1) + "");//1.推荐；2.最新；3.热门；4.本地  (默认为1)
//        commonOkhttp.putCallback(new MyGenericsCallback<ForumListEntity>(getActivity()) {
//            @Override
//            public void onSuccess(ForumListEntity data, int d) {
//                super.onSuccess(data, d);
//                if (page == 1) {
//                    mData.clear();
//                }
//                if (data != null && data.size() > 0) {
//                    mData.addAll(data);
//                    ++page;
//                } else {
//                    if (page == 1) {
////                        showMessage(getResources().getString(R.string.list_no_data));
//                    } else {
//                        showMessage(getResources().getString(R.string.list_bottom));
//                    }
//                }
//                if (mAdapter != null) {
//                    mAdapter.notifyDataSetChanged();
//                }
//                if (refreshView != null && refreshView.isRefreshing()) {
//                    refreshView.onRefreshComplete();
//                }
//
//            }
//
//        });
//        commonOkhttp.Execute();
//    }

    private void getData(final PullToRefreshBase<ScrollView> refreshView) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.forumCate2);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putParams("rows", "6");
        commonOkhttp.putParams("cateid", (flag + 1) + "");//1.推荐；2.最新；3.热门；4.本地  (默认为1)
        commonOkhttp.putCallback(new MyGenericsCallback<ForumNewListEntity>(getActivity()) {
            @Override
            public void onSuccess(ForumNewListEntity data, int d) {
                super.onSuccess(data, d);

                Log.e("11111111111","111 = "+data);

                if (page == 1) {
                    mData.clear();
                }
                if (data != null && data.size() > 0) {
                    mData.addAll(data);
                    ++page;
                } else {
                    if (page == 1) {
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
