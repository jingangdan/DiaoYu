package com.project.dyuapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.project.dyuapp.R;
import com.project.dyuapp.adapter.FishingPlaceSelectAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.FishingPlaceTypeEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.ScreenManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author 田亭亭
 * @description 钓场类型(发布钓场)
 * @created at 2017/11/29 0029
 * @change gqf 所有类型选择
 */
public class FishingPlaceSelectActivity extends MyBaseActivity {

    @Bind(R.id.fishing_place_type_lv)
    ListView fishingPlaceTypeLv;
    private List<FishingPlaceTypeEntity> typeList = new ArrayList<>();
    private FishingPlaceSelectAdapter mAdapter;
    private String title = "钓场类型";
    private String selectList="";
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_fishing_place_type);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
            title = getIntent().getStringExtra("title");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("selectList"))){
            selectList = getIntent().getStringExtra("selectList");
            list = Arrays.asList(selectList.split(","));
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
                StringBuffer fingerling_name = new StringBuffer();
                StringBuffer fingerling_name_id = new StringBuffer();
                HashMap<Integer,Boolean> checkBoxes = mAdapter.getIsSelected();
                for (int i = 0; i < checkBoxes.size(); i++) {
                    if (checkBoxes.get(i)){
                        fingerling_name.append(typeList.get(i).getName()+",");
                        fingerling_name_id.append(typeList.get(i).getD_id()+",");
                    }
                }
                if (TextUtils.isEmpty(fingerling_name) || TextUtils.isEmpty(fingerling_name_id)){
                    showMessage("请选择钓场类型");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("sel", fingerling_name.toString().substring(0,fingerling_name.length()-1));
                intent.putExtra("sel_id", fingerling_name_id.toString().substring(0,fingerling_name_id.length()-1));
                setResult(Activity.RESULT_OK,intent);
                ScreenManager.getInstance().removeActivity(FishingPlaceSelectActivity.this);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mAdapter = new FishingPlaceSelectAdapter(typeList, FishingPlaceSelectActivity.this, R.layout.item_fingerling_list,list);
        fishingPlaceTypeLv.setAdapter(mAdapter);
        fishingPlaceTypeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setIsSelected(position);
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
                mAdapter.notifyData();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }
        });
        commonOkhttp.Execute();
    }
}
