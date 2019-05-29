package com.project.dyuapp.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.project.dyuapp.R;
import com.project.dyuapp.adapter.FingerlingListAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.FingerlingEntity;
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
 * @description 鱼种activity
 * @created at 2017/12/2 0002
 * @change
 */
public class FingerlingListActivity extends MyBaseActivity {


    @Bind(R.id.fingerling_list_lv)
    ListView fingerlingListLv;
    private List<FingerlingEntity> fingerlingList;
    private FingerlingListAdapter mAdapter;

    private String selectList = "";
    private List<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_fingerling_list);
        ButterKnife.bind(this);
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
        setTvTitle("鱼种");
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
                        fingerling_name.append(fingerlingList.get(i).getName()+",");
                        fingerling_name_id.append(fingerlingList.get(i).getD_id()+",");
                    }
                }
                if (TextUtils.isEmpty(fingerling_name) || TextUtils.isEmpty(fingerling_name_id)){
                    showMessage("请选择鱼种");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("fingerling_name", fingerling_name.toString().substring(0,fingerling_name.length()-1));
                intent.putExtra("fingerling_name_id", fingerling_name_id.toString().substring(0,fingerling_name_id.length()-1));
                setResult(Activity.RESULT_OK,intent);
                ScreenManager.getInstance().removeActivity(FingerlingListActivity.this);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        fingerlingList = new ArrayList<>();
        mAdapter = new FingerlingListAdapter(fingerlingList, FingerlingListActivity.this, R.layout.item_fingerling_list,list);
        fingerlingListLv.setAdapter(mAdapter);
        fingerlingListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setIsSelected(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        getfingerLing();
    }

    public void getfingerLing(){
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.ANGLING_FISH_TYPE);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.FingerlingListEntity>(this) {
            @Override
            public void onSuccess(NetBean.FingerlingListEntity data, int d) {
                super.onSuccess(data, d);
                fingerlingList.clear();
                fingerlingList.addAll(data);
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
