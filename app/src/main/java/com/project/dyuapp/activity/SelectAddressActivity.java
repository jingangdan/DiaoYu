package com.project.dyuapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CommonAdapter;
import com.project.dyuapp.adapter.CommonViewHolder;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.entity.SelectAddressEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.ScreenManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.project.dyuapp.myutils.PublicStaticData.SELECT_ADDRESS;

/**
 * @author shipeiyun
 * @description 个人中心-金币商城-商品兑换-选择地址
 * @created at 2017/12/6 0006
 */
public class SelectAddressActivity extends MyBaseActivity {

    @Bind(R.id.select_address_pv_list)
    PullToRefreshListView selectAddressPvList;

    private List<SelectAddressEntity> mList = new ArrayList<>();//列表集合
    private SelectAdapter adapter;
    private String addressid = ""; //地址ID
    private String name = "";//收货人
    private String phone = "";//收货人电话
    private String site = "";//地址
    private int currentPosition = 0;
    private SelectAddressEntity entity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_select_address);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("选择地址");
        getTvRight().setText("完成");
        getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("addressid", addressid);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("site", site);
                setResult(SELECT_ADDRESS, intent);
                ScreenManager.getInstance().removeActivity(SelectAddressActivity.this);
            }
        });
        initView();
        okhttpServiceAddress();
    }

    /**
     * 初始化数据
     */
    private void initView() {
        selectAddressPvList.setMode(PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY);
        adapter = new SelectAdapter(SelectAddressActivity.this, mList, R.layout.item_select_address);
        selectAddressPvList.setAdapter(adapter);
        selectAddressPvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentPosition != (i - 1)) {
                    mList.get(i - 1).setIs_default("1");
                    mList.get(currentPosition).setIs_default("0");
                    currentPosition = i - 1;
                    addressid = mList.get(i - 1).getAddressid();
                    name = mList.get(i - 1).getUsername();
                    phone = mList.get(i - 1).getPhone();
                    site = mList.get(i - 1).getXqaddress();
                    adapter.notifyDataSetChanged();
                } else {
                    mList.get(i - 1).setIs_default("1");
                    mList.get(currentPosition).setIs_default("1");
                    addressid = mList.get(i - 1).getAddressid();
                    name = mList.get(i - 1).getUsername();
                    phone = mList.get(i - 1).getPhone();
                    site = mList.get(i - 1).getXqaddress();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 8个人中心-服务地址
     */
    private void okhttpServiceAddress() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.serviceAddress);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.ServiceAddressListEntity>(SelectAddressActivity.this) {
            @Override
            public void onSuccess(NetBean.ServiceAddressListEntity data, int d) {
                super.onSuccess(data, d);
                if (data != null) {
                    for (int i = 0; i < mList.size(); i++) {
                        if ("1".equals(mList.get(i).getIs_default())) {
                            currentPosition = i;
                        }
                    }
                    mList.clear();
                    mList.addAll(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        commonOkhttp.Execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        okhttpServiceAddress();
    }

    /**
     * 列表地址适配
     */
    class SelectAdapter extends CommonAdapter<SelectAddressEntity> {

        @Bind(R.id.item_select_address_chb_default)
        CheckBox chbDefault;//默认地址
        @Bind(R.id.item_select_address_tv_name)
        TextView tvName;//收货人
        @Bind(R.id.item_select_address_tv_phone)
        TextView tvPhone;//收货电话
        @Bind(R.id.item_select_address_tv_site)
        TextView tvSite;//收货地址

        /**
         * 构造函数
         *
         * @param context   上下文
         * @param data      显示的数据
         * @param layout_id listview使用的条目的布局文件id
         */
        public SelectAdapter(Context context, List<SelectAddressEntity> data, int layout_id) {
            super(context, data, R.layout.item_select_address);
        }

        @Override
        public void convert(CommonViewHolder h, SelectAddressEntity item, int position) {
            ButterKnife.bind(this, h.getConvertView());
            String isDefault = item.getIs_default();//是否默认地址1是0否
            if (isDefault.equals("1")) {
                chbDefault.setChecked(true);
            } else {
                chbDefault.setChecked(false);
            }

            String name = item.getUsername();//收货人
            if (!TextUtils.isEmpty(name)) {
                tvName.setText(name);
            }
            String phone = item.getPhone();//收货电话
            if (!TextUtils.isEmpty(phone)) {
                tvPhone.setText(phone);
            }
            String site = item.getXqaddress();//收货地址
            if (!TextUtils.isEmpty(site)) {
                tvSite.setText(site);
            }
        }
    }

    //新地址
    @OnClick(R.id.select_address_ll_add)
    public void onClick() {
        startActivity(new Intent(SelectAddressActivity.this, NewAddressActivity.class).putExtra("flag", "1"));
    }

}
