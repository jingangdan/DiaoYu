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
import com.project.dyuapp.activity.SkillDetailsActivity;
import com.project.dyuapp.adapter.PostListAdapter;
import com.project.dyuapp.base.LazyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.ForumEntity;
import com.project.dyuapp.entity.ForumListEntity;
import com.project.dyuapp.entity.IndexListEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.ToastUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author gengqiufang
 * @describtion 帖子列表（饵料）
 * @created at 2017/12/1 0001
 */

public class PostListFragment extends LazyBaseFragment {

    @Bind(R.id.home_post_plv)
    PullToRefreshListView plv;

    private String catId = "";
    private int page = 1;

    private PostListAdapter mAdapter;
    private ArrayList<ForumEntity> mData = new ArrayList<>();

    public static PostListFragment newInstance(String catId) {
        Bundle bundle = new Bundle();
        bundle.putString("catId", catId);
        PostListFragment myFragment = new PostListFragment();
        myFragment.setArguments(bundle);
        return myFragment;
    }

    public void setPage(int page) {
        this.page = page;
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
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);
        ButterKnife.bind(this, view);
        initPlv();
//        okHttpForumList();
        return view;
    }
    @Override
    protected void onFragmentFirstVisible() {
        page = 1;
        if (TextUtils.isEmpty(catId)){
            //首页
            getHomeData();
        }else {
            //分类
            okHttpForumList();;
        }

    }
    private void initPlv() {
        mAdapter = new PostListAdapter(mData, getActivity());
        plv.setAdapter(mAdapter);
        plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent skill = new Intent(getActivity(), SkillDetailsActivity.class);
                skill.putExtra("id",mData.get(position-1).getF_id());
                startActivity(skill);
            }
        });
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                if (TextUtils.isEmpty(catId)){
                    //首页
                    getHomeData();
                }else {
                    //分类
                    okHttpForumList();;
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (TextUtils.isEmpty(catId)){
                    //首页
                    getHomeData();
                }else {
                    //分类
                    okHttpForumList();;
                }
        }
        });
    }

    //1帖子-饵料
    public void okHttpForumList() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.forumList);
        commonOkhttp.putParams("page", page + "");//分页
        commonOkhttp.putParams("rows", "6");//每页条数
        commonOkhttp.putParams("top_id", PublicStaticData.ID_BAIT);//帖子顶级分类
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
                if (data.size() == 0) {
                    if (page == 1) {
                        ToastUtils.getInstance(getActivity()).showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        ToastUtils.getInstance(getActivity()).showMessage(getResources().getString(R.string.list_bottom));
                    }
                } else {
                    mData.addAll(data);
                    page += 1;
                }
                mAdapter.notifyDataSetChanged();
                plv.onRefreshComplete();
            }

            @Override
            public void onOther(JsonResult<ForumListEntity> data, int d) {
                super.onOther(data, d);
                plv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                plv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }
    /**
     * 获取技巧首页列表
     */
    private void getHomeData() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.indexList);
        commonOkhttp.putParams("top_id", PublicStaticData.ID_BAIT);//帖子顶级分类
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
