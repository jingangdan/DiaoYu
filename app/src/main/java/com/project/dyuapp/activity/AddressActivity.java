package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.AddressAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.entity.SelectAddressEntity;
import com.project.dyuapp.entity.StatusEntity;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shipeiyun
 * @description 个人中心-收货地址
 * @created at 2017/11/28 0028
 */
public class AddressActivity extends MyBaseActivity {

    @Bind(R.id.address_pv_list)
    PullToRefreshListView addressPvList;

    private List<SelectAddressEntity> addressList = new ArrayList<>();//收货地址集合
    private AddressAdapter adapter;
    private String addressid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("收货地址");
        initAdapter();
        okhttpServiceAddress();
    }

    /**
     * 列表适配
     */
    private void initAdapter() {
        addressPvList.setMode(PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY);
        adapter = new AddressAdapter(this, addressList, R.layout.item_address);
        addressPvList.setAdapter(adapter);
        //按钮点击事件
        adapter.setmOnItemClickListener(new AddressAdapter.OnItemClickListener() {
            @Override
            public void onSetDefaultClick(View view, int position) {
                addressid = addressList.get(position+1).getAddressid();
                okhttpSetDefault();
            }

            @Override
            public void onDeleteClick(View view, int position) {
                addressid = addressList.get(position+1).getAddressid();
                okhttpServiceDzDel();
            }

            @Override
            public void onEditClick(View view, int position) {
                //flag 0编辑  1新增
                startActivity(new Intent(AddressActivity.this, NewAddressActivity.class)
                        .putExtra("flag", "0")
                        .putExtra("addressid", addressList.get(position+1).getAddressid()));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        okhttpServiceAddress();
    }

    /**
     * 8个人中心-服务地址
     */
    private void okhttpServiceAddress() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.serviceAddress);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.ServiceAddressListEntity>(AddressActivity.this) {
            @Override
            public void onSuccess(NetBean.ServiceAddressListEntity data, int d) {
                super.onSuccess(data, d);
                if (data != null) {
                    addressList.clear();
                    addressList.addAll(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 28个人中心-服务地址-删除
     */
    private void okhttpServiceDzDel(){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.serviceDzDel);
        commonOkhttp.putParams("addressid",addressid);
        commonOkhttp.putCallback(new MyGenericsCallback<StatusEntity>(AddressActivity.this){
            @Override
            public void onSuccess(StatusEntity data, int d) {
                super.onSuccess(data, d);
                okhttpServiceAddress();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 34个人中心-服务地址-设置默认
     */
    private void okhttpSetDefault(){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.setDefault);
        commonOkhttp.putParams("addressid",addressid);
        commonOkhttp.putCallback(new MyGenericsCallback<StatusEntity>(AddressActivity.this){
            @Override
            public void onSuccess(StatusEntity data, int d) {
                super.onSuccess(data, d);
                okhttpServiceAddress();
            }
        });
        commonOkhttp.Execute();
    }

    @OnClick(R.id.address_ll_add)
    public void onClick() {
        //flag 0编辑  1新增
        startActivity(new Intent(AddressActivity.this, NewAddressActivity.class).putExtra("flag", "1"));
    }

}
