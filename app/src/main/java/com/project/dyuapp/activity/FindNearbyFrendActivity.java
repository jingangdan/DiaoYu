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
import com.project.dyuapp.adapter.NearbyFdAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.EmptyResultBean;
import com.project.dyuapp.entity.NearByBean;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author litongtong
 * @created on 2017/11/28 11:45
 * @description 个人中心-我的消息-发现周边钓友
 * @change ${}
 */

public class FindNearbyFrendActivity extends MyBaseActivity {
    @Bind(R.id.nearby_lv)
    PullToRefreshListView nearbyLv;//列表

    private NearbyFdAdapter fAdapter;
    private int page = 1;//当前页数
    private List<NearByBean> nList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_find_nearby_friend);
        ButterKnife.bind(this);
        setTvTitle("发现周边钓友");//标题
        setIvBack();//返回
        setAdapter();
        getData();
    }

    /**
     * 适配数据
     */
    private void setAdapter(){
        fAdapter = new NearbyFdAdapter(this, nList,R.layout.item_nearby_friend_lv);
        nearbyLv.setAdapter(fAdapter);
        fAdapter.setOnAttentionCallBack(new NearbyFdAdapter.onAttentionCallBack() {
            @Override
            public void onAttentionClick(View view, int position) {
                editAttention(position);
            }
        });

        nearbyLv.setMode(PullToRefreshBase.Mode.BOTH);
        nearbyLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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

        nearbyLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击列表进入钓友发帖页面
                startActivity(new Intent(FindNearbyFrendActivity.this,FansMainPagerActivity.class)
                        .putExtra("id",nList.get(i-1).getMember_list_id()));
            }
        });
    }


    /**
     * 获取周边钓友列表
     */
    private void getData(){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.periphery);
        commonOkhttp.putParams("page",page+"");
        commonOkhttp.putParams("lng",PublicStaticData.sharedPreferences.getString("longitude",""));
        commonOkhttp.putParams("lat", PublicStaticData.sharedPreferences.getString("latitude",""));
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.NearByFdEntity>(this){
            @Override
            public void onSuccess(NetBean.NearByFdEntity data, int d) {
                super.onSuccess(data, d);
                if(page == 1){
                    nList.clear();
                }
                if(data!=null&&data.size()>0){
                    nList.addAll(data);
                    ++page;
                }else {
                    if (page == 1) {
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                }
                fAdapter.notifyDataSetChanged();
                nearbyLv.onRefreshComplete();
            }

            @Override
            public void onOther(JsonResult<NetBean.NearByFdEntity> data, int d) {
                super.onOther(data, d);
                nearbyLv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                nearbyLv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 关注、取消关注
     */
    private void editAttention(int position){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.attention);
        commonOkhttp.putParams("memberid",nList.get(position).getMember_list_id());
        commonOkhttp.putCallback(new MyGenericsCallback<EmptyResultBean>(this){
            @Override
            public void onSuccess(EmptyResultBean data, int d) {
                super.onSuccess(data, d);
                page = 1;
                getData();
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
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
