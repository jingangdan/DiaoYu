package com.project.dyuapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.project.dyuapp.R;
import com.project.dyuapp.adapter.FishingPlaceTypeAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.FishingPlaceTypeEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author 田亭亭
 * @description 钓场类型Activity
 * @created at 2017/11/29 0029
 * @change gqf 所有类型选择
 */
public class FishingPlaceTypeActivity extends MyBaseActivity {

    @Bind(R.id.fishing_place_type_lv)
    ListView fishingPlaceTypeLv;
    private List<FishingPlaceTypeEntity> typeList = new ArrayList<>();
    private FishingPlaceTypeAdapter mAdapter;
    private int selectPosition = -1;
    private String title = "钓场类型";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_fishing_place_type);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
            title = getIntent().getStringExtra("title");
        }
        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        setTvTitle(title);
        setIvBack();
        getTvRight().setText("确定");
        getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition ==-1){
                    showMessage("请选择");
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("sel",typeList.get(selectPosition).getName());
                    intent.putExtra("sel_id",typeList.get(selectPosition).getD_id());
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mAdapter = new FishingPlaceTypeAdapter(typeList, FishingPlaceTypeActivity.this, R.layout.item_fishing_place_type);
        fishingPlaceTypeLv.setAdapter(mAdapter);
        mAdapter.setSelectPosition(selectPosition);
        fishingPlaceTypeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = position;
                mAdapter.setSelectPosition(selectPosition);
                mAdapter.notifyDataSetChanged();
            }
        });
        fishingPlace();
    }

    public void fishingPlace(){
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.ANGLING_ANG_TYPE);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.FishingPlaceTypeListEntity>(this) {
            @Override
            public void onSuccess(NetBean.FishingPlaceTypeListEntity data, int d) {
                super.onSuccess(data, d);
                typeList.clear();
                typeList.addAll(data);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }
        });
        commonOkhttp.Execute();
    }
}
