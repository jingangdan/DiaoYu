package com.project.dyuapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.ReplyAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.MyReplyBean;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author litongtong
 * @created on 2017/11/28 14:14
 * @description 个人中心-我的消息-回复
 * @change ${}
 */

public class ReplyActivity extends MyBaseActivity {
    @Bind(R.id.reply_lv)
    PullToRefreshListView replyLv;

    private ReplyAdapter rAdapter;
    private List<MyReplyBean> list = new ArrayList<>();
    private int page = 1;//当前页

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_reply);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("回复");
        setAdapter();
        getData();
    }

    private void setAdapter() {
        rAdapter = new ReplyAdapter(this, list,R.layout.item_mymsg_lv);
        replyLv.setAdapter(rAdapter);

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
    }


    /**
     * 请求接口获取回复列表
     */
    private void getData(){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.reply);
        commonOkhttp.putParams("page",page+"");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.MyReplyEntity>(this){
            @Override
            public void onSuccess(NetBean.MyReplyEntity data, int d) {
                super.onSuccess(data, d);
                if(page == 1){
                    list.clear();
                }
                if(data!=null&&data.size()!=0){
                    list.addAll(data);
                    ++page;
                }else {
                    if (page == 1) {
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                }

                rAdapter.notifyDataSetChanged();
                replyLv.onRefreshComplete();
            }
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                replyLv.onRefreshComplete();
            }

            @Override
            public void onOther(JsonResult<NetBean.MyReplyEntity> data, int d) {
                super.onOther(data, d);
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
