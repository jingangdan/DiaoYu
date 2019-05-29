package com.project.dyuapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.AllCommentAdapter;
import com.project.dyuapp.adapter.CommentAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.ForumCommentsEntity;
import com.project.dyuapp.entity.ForumCommentsListEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author litongtong
 * @created on 2017/12/4 16:32
 * @description 首页-渔获-详情-全部评论
 * @change ${}
 */

public class AllCommentActivity extends MyBaseActivity {
    @Bind(R.id.nearby_lv)
    PullToRefreshListView nearbyLv;//列表

    private String objectId = "";
    private String objectType = "";
    private String whereFrom = "";
    private int page = 1;
    private List<ForumCommentsEntity> mDataComments;
    private AllCommentAdapter mAdapterComment;
    private CommentAdapter mCommentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_find_nearby_friend);
        ButterKnife.bind(this);
        setTvTitle("全部评论");//标题
        setIvBack();//返回
        objectId = getIntent().getStringExtra("object_id");
        objectType = getIntent().getStringExtra("object_type");
        whereFrom = getIntent().getStringExtra("whereFrom");
        setAdapter();
        page = 1;
        okHttpForumComments();
    }

    /**
     * 适配数据
     */
    private void setAdapter() {
        mDataComments = new ArrayList<>();
        if (!TextUtils.isEmpty(whereFrom)) {
            if (TextUtils.equals(whereFrom, "1")) {
                mAdapterComment = new AllCommentAdapter(mDataComments, AllCommentActivity.this);
                nearbyLv.setAdapter(mAdapterComment);
            } else if ((TextUtils.equals(whereFrom, "2") || (TextUtils.equals(whereFrom, "3")))) {
                mCommentAdapter = new CommentAdapter(mDataComments, AllCommentActivity.this);
                nearbyLv.setAdapter(mCommentAdapter);
            }
        }

        nearbyLv.setMode(PullToRefreshBase.Mode.BOTH);
        nearbyLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        okHttpForumComments();
                        nearbyLv.onRefreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        okHttpForumComments();
                        nearbyLv.onRefreshComplete();
                    }
                }, 2000);
            }
        });
    }

    /**
     * 全部评论列表接口
     */
    public void okHttpForumComments() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.forumComments);
        commonOkhttp.putParams("object_type", objectType);//1帖子 2文章  3钓场  4渔具店
        commonOkhttp.putParams("object_id", objectId);//点评对象ID
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putParams("rows", "10");
        commonOkhttp.putCallback(new MyGenericsCallback<ForumCommentsListEntity>(this) {
            @Override
            public void onSuccess(ForumCommentsListEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    if (page == 1) {
                        mDataComments.clear();
                    }
                    if (data != null && data.size() != 0) {
                        mDataComments.addAll(data);
                        ++page;
                    } else {
                        commonOkhttp.showNoData(AllCommentActivity.this, page);
                    }
                    if (TextUtils.equals(whereFrom, "1")) {
                        mAdapterComment.notifyDataSetChanged();
                    } else if ((TextUtils.equals(whereFrom, "2") || (TextUtils.equals(whereFrom, "3")))) {
                        mCommentAdapter.notifyDataSetChanged();
                    }

                    if (nearbyLv != null) {
                        nearbyLv.onRefreshComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(AllCommentActivity.this).showMessage(AllCommentActivity.this.getResources().getString(R.string.data_parsing_failed));
                }
            }
        });
        commonOkhttp.Execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
