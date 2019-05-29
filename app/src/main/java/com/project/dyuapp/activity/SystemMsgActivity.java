package com.project.dyuapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CommonAdapter;
import com.project.dyuapp.adapter.CommonViewHolder;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.MySystemBean;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyDateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author litongtong
 * @created on 2017/12/4 16:07
 * @description 个人中心-我的消息-系统消息
 * @change ${}
 */

public class SystemMsgActivity extends MyBaseActivity{
    @Bind(R.id.nearby_lv)
    PullToRefreshListView nearbyLv;//列表

    private CommonAdapter sAdapter;
    private List<MySystemBean> list = new ArrayList<>();
    private int page = 1;//当前页

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_find_nearby_friend);
        ButterKnife.bind(this);
        setTvTitle("系统消息");//标题
        setIvBack();//返回
        getTvRight().setText("清空");
        setAdapter();
        getData();
    }

    /**
     * 适配数据
     */
    private void setAdapter(){
        sAdapter = new CommonAdapter(this, list, R.layout.item_systemmsg_lv) {
            @Override
            public void convert(CommonViewHolder h, Object item, int position) {
                //消息时间
                ((TextView)h.getConvertView().findViewById(R.id.sysmsg_tv_time)).setText(MyDateUtils.timeStampToData(list.get(position).getCreatetime(),"yyyy-MM-dd"));
                //消息内容
                ((TextView)h.getConvertView().findViewById(R.id.sysmsg_tv_content)).setText(list.get(position).getDesc());
            }

        };
        nearbyLv.setAdapter(sAdapter);

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
        getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //清空
                if(list.size()>0){
                    systemEmpty();
                }else {
                    showMessage("无可清理消息");
                }
            }
        });
    }


    /**
     * 获取数据
     */
    private void getData(){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.system);
        commonOkhttp.putParams("page",page+"");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.MySystemEntity>(this){
            @Override
            public void onSuccess(NetBean.MySystemEntity data, int d) {
                super.onSuccess(data, d);
                if(page == 1){
                    list.clear();
                }
                if(data!=null&&data.size()>0){
                    list.addAll(data);
                    ++page;
                }else {
                    if (page == 1) {
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                }
                sAdapter.notifyDataSetChanged();
                nearbyLv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                nearbyLv.onRefreshComplete();
            }

            @Override
            public void onOther(JsonResult<NetBean.MySystemEntity> data, int d) {
                super.onOther(data, d);
                nearbyLv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 清空消息
     */
    private void systemEmpty(){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.systemEmpty);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.EmptytEntity>(this){
            @Override
            public void onSuccess(NetBean.EmptytEntity data, int d) {
                super.onSuccess(data, d);
                list.clear();
                sAdapter.notifyDataSetChanged();
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
