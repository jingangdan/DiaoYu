package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.MyRewardAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.MyRewardBean;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author litongtong
 * @created on 2017/12/4 16:02
 * @description 个人中心-我的消息-打赏
 * @change ${}
 */

public class RewardActivity extends MyBaseActivity {
    @Bind(R.id.reply_lv)
    PullToRefreshListView replyLv;

    private MyRewardAdapter adapter;
    private int page = 1;//当前页
    private List<MyRewardBean> rList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_reply);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("打赏");
        setAdapter();
        getData();
    }

    /**
     * 适配数据
     */
    private void setAdapter() {
        adapter = new MyRewardAdapter(this, rList, R.layout.item_mymsg_lv);
        replyLv.setAdapter(adapter);

        replyLv.setMode(PullToRefreshBase.Mode.BOTH);
        replyLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新
                page = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载
                getData();
            }
        });

        replyLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(RewardActivity.this,FansMainPagerActivity.class)
                        .putExtra("id",rList.get(i-1).getMember_list_id()));
            }
        });
    }

    /**
     * 获取数据
     */
    private void getData() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.reward);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.MyRewardEntity>(this) {
            @Override
            public void onSuccess(NetBean.MyRewardEntity data, int d) {
                super.onSuccess(data, d);
                if (page == 1) {
                    rList.clear();
                }
                if (data != null && data.size() > 0) {
                    rList.addAll(data);
                    ++page;
                } else {
                    if (page == 1) {
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                }
                adapter.notifyDataSetChanged();
                replyLv.onRefreshComplete();
            }

            @Override
            public void onOther(JsonResult<NetBean.MyRewardEntity> data, int d) {
                super.onOther(data, d);
                replyLv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                replyLv.onRefreshComplete();
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
