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
import com.project.dyuapp.adapter.PraiseMeAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.MyPraiseBean;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author litongtong
 * @created on 2017/11/28 15:22
 * @description 个人中心-我的消息-赞过我
 * @change ${}
 */

public class PraiseMeActivity extends MyBaseActivity {
    @Bind(R.id.reply_lv)
    PullToRefreshListView replyLv;//赞过我列表

    private PraiseMeAdapter adapter;
    private int page = 1;//当前页面
    private List<MyPraiseBean> list = new ArrayList<>();//赞过我列表结合

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_reply);
        ButterKnife.bind(this);
        setIvBack();//返回
        setTvTitle("赞过我");//标题
        setAdapter();
        getData();
    }

    /**
     * 适配数据
     */
    private void setAdapter() {
        adapter =new PraiseMeAdapter(this, list, R.layout.item_mymsg_lv);
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
                startActivity(new Intent(PraiseMeActivity.this,FansMainPagerActivity.class)
                        .putExtra("id",list.get(i-1).getMember_list_id()));
            }
        });
    }

    /**
     * 获取列表数据
     */
    private void getData(){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.fabulous);
        commonOkhttp.putParams("page",page+"");
        commonOkhttp.putParams("rows","10");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.MyPraiseEntity>(this){
            @Override
            public void onSuccess(NetBean.MyPraiseEntity data, int d) {
                super.onSuccess(data, d);
                if (page == 1) {
                    list.clear();
                }
                if (data != null && data.size() != 0) {
                    list.addAll(data);
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
            public void onOther(JsonResult<NetBean.MyPraiseEntity> data, int d) {
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
