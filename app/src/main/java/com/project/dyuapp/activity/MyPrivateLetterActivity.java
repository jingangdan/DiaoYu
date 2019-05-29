package com.project.dyuapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.MyPLetterAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.MyLetterBean;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author litongtong
 * @created on 2017/11/28 10:54
 * @description 个人中心-我的消息-我的私信
 * @change ${}
 */

public class MyPrivateLetterActivity extends MyBaseActivity {
    @Bind(R.id.myletter_lv)
    PullToRefreshListView myletterLv;//我的私信列表

    private MyPLetterAdapter pAdapter;
    private List<MyLetterBean> list = new ArrayList<>();
    private int page = 1;//当前页

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_myprivateletter);
        ButterKnife.bind(this);
        setIvBack();//返回
        setTvTitle("我的私信");
        setAdapter();
        getData();
    }

    /**
     * 适配数据
     */
    private void setAdapter(){
        pAdapter = new MyPLetterAdapter(this, list,R.layout.item_mypletter_lv);
        myletterLv.setAdapter(pAdapter);
        myletterLv.setMode(PullToRefreshBase.Mode.BOTH);
        myletterLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
     * 请求接口获取我的私信列表
     */
    private void getData(){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.letter);
        commonOkhttp.putParams("page",page+"");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.MyLetterEntity>(this){
            @Override
            public void onSuccess(NetBean.MyLetterEntity data, int d) {
                super.onSuccess(data, d);
                if(page == 1){
                    list.clear();
                }
                if(data!=null && data.size()>0){
                    list.addAll(data);
                    ++page;
                }else {
                    if (page == 1) {
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                }
                pAdapter.notifyDataSetChanged();
                myletterLv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                myletterLv.onRefreshComplete();
            }

            @Override
            public void onOther(JsonResult<NetBean.MyLetterEntity> data, int d) {
                super.onOther(data, d);
                myletterLv.onRefreshComplete();
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
