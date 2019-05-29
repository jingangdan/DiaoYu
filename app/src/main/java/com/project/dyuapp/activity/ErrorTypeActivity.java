package com.project.dyuapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.project.dyuapp.R;
import com.project.dyuapp.adapter.ErrorTypeAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.entity.ErrorTypeBean;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myutils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 田亭亭
 * @description 报错类型Activity
 * @created at 2017/12/2 0002
 * @change
 */
public class ErrorTypeActivity extends MyBaseActivity {
    @Bind(R.id.error_type_lv)
    ListView errorTypeLv;
    private List<ErrorTypeBean> errorTypeList;
    private ErrorTypeAdapter mAdapter;
    private int selectPosition = -1;
    private String whereFrom = "";
    private String errorTypeId = "";
    private String errorTypeName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_error_type);
        ButterKnife.bind(this);
        initView();
        initData();
        if (!TextUtils.isEmpty(getIntent().getStringExtra("errorTypeId"))){
            errorTypeId= getIntent().getStringExtra("errorTypeId");
            selectPosition=Integer.parseInt(errorTypeId)-1;
            mAdapter.setSelectPosition(selectPosition);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        setTvTitle("报错类型");
        setIvBack();
        getTvRight().setText("确定");
        getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition < 0) {
                    ToastUtils.getInstance(ErrorTypeActivity.this).showMessage("请选择报错类型");
                    return;
                }
                errorTypeId = errorTypeList.get(selectPosition).getErrorTypeId();
                errorTypeName = errorTypeList.get(selectPosition).getErrorTypeName();
                Intent it = new Intent();
                it.putExtra("error_type_id", errorTypeId);
                it.putExtra("error_type_name", errorTypeName);
                setResult(PublicStaticData.ERROR_TYPE, it);
                ScreenManager.getInstance().removeActivity(ErrorTypeActivity.this);
            }
        });

    }

    /**
     * 初始化数据
     */
    private void initData() {
        whereFrom = getIntent().getStringExtra("whereFrom");
        errorTypeList = new ArrayList<>();
        if (!TextUtils.isEmpty(whereFrom)) {
            if (TextUtils.equals(whereFrom, "1")) {
                //钓场详情
                errorTypeList.add(new ErrorTypeBean("钓场已关", "1"));
                errorTypeList.add(new ErrorTypeBean("地图位置错误", "2"));
                errorTypeList.add(new ErrorTypeBean("钓场信息错误", "3"));
                errorTypeList.add(new ErrorTypeBean("钓场重复", "4"));
                errorTypeList.add(new ErrorTypeBean("其他", "5"));
            } else if (TextUtils.equals(whereFrom, "2")) {
                //渔具店详情
                errorTypeList.add(new ErrorTypeBean("渔具店已关", "1"));
                errorTypeList.add(new ErrorTypeBean("地图位置错误", "2"));
                errorTypeList.add(new ErrorTypeBean("渔具店信息错误", "3"));
                errorTypeList.add(new ErrorTypeBean("渔具店重复", "4"));
                errorTypeList.add(new ErrorTypeBean("其他", "5"));
            }
        }

        mAdapter = new ErrorTypeAdapter(errorTypeList, ErrorTypeActivity.this, R.layout.item_fishing_place_type);
        errorTypeLv.setAdapter(mAdapter);
        mAdapter.setSelectPosition(selectPosition);
        errorTypeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = position;
                mAdapter.setSelectPosition(selectPosition);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
