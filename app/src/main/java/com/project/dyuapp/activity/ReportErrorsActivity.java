package com.project.dyuapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.SuccessBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myutils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author 田亭亭
 * @description （钓场、渔具店）报错Activity
 * @created at 2017/12/2 0002
 * @change
 */
public class ReportErrorsActivity extends MyBaseActivity {

    @Bind(R.id.report_errors_tv_type)
    TextView reportErrorsTvType;//报错类型
    @Bind(R.id.report_errors_edt_problem)
    EditText reportErrorsEdtProblem;//输入问题

    private String whereFrom = "";//1.从钓场详情，2从渔具店详情
    private String fishingShopId = "";//渔具店id
    private String content = "";//输入问题
    private String errorTypeId = "";//报错类型id
    private String errorTypeName = "";//报错类型名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_report_errors);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        whereFrom = getIntent().getStringExtra("whereFrom");
        fishingShopId = getIntent().getStringExtra("fishing_shop_id");
        if (!TextUtils.isEmpty(whereFrom)) {
            if (TextUtils.equals(whereFrom, "1")) {
                //钓场详情
                setTvTitle("钓场报错");
            } else if (TextUtils.equals(whereFrom, "2")) {
                //渔具店详情
                setTvTitle("渔具店报错");
            }
        }
        setIvBack();
    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    /**
     * 报错接口(渔具店)
     * //
     */
    private void okhttpMistakeShop() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.mistakeShop);
        commonOkhttp.putParams("fishing_shop_id", fishingShopId);
        commonOkhttp.putParams("content", content);
        commonOkhttp.putParams("content_type", errorTypeId);
        commonOkhttp.putCallback(new MyGenericsCallback<SuccessBean>(this) {
            @Override
            public void onSuccess(SuccessBean data, int d) {
                super.onSuccess(data, d);
                if (data.getState() == 200) {
                    ScreenManager.getInstance().removeActivity(ReportErrorsActivity.this);
                }
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 报错接口（钓场）
     * //
     */
    private void okhttpFishingPlace() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.ANGLING_REPORT_ERRORS);
        commonOkhttp.putParams("f_gid", fishingShopId);
        commonOkhttp.putParams("content", content);
        commonOkhttp.putParams("content_type", errorTypeId);
        commonOkhttp.putCallback(new MyGenericsCallback<SuccessBean>(this) {
            @Override
            public void onSuccess(SuccessBean data, int d) {
                super.onSuccess(data, d);
                if (data.getState() == 200) {
                    ScreenManager.getInstance().removeActivity(ReportErrorsActivity.this);
                }
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }

    @OnClick({R.id.report_errors_tv_site_more, R.id.report_errors_ll_more, R.id.report_errors_tv_submit})
    public void onViewClicked(View view) {
        content = reportErrorsEdtProblem.getText().toString();
        switch (view.getId()) {
            case R.id.report_errors_tv_site_more:
            case R.id.report_errors_ll_more:
                startActivityForResult(new Intent(ReportErrorsActivity.this, ErrorTypeActivity.class)
                                .putExtra("whereFrom", whereFrom)
                                .putExtra("errorTypeId",errorTypeId),
                        PublicStaticData.REPORT_ERRORS);
                break;
            case R.id.report_errors_tv_submit:
                if (TextUtils.isEmpty(errorTypeId)) {
                    ToastUtils.getInstance(ReportErrorsActivity.this).showMessage("请选择报错类型");
                    return;
                }
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.getInstance(ReportErrorsActivity.this).showMessage("请输入您发现的问题");
                    return;
                }
                if (TextUtils.equals("1", whereFrom)) {
                    okhttpFishingPlace();//钓场
                } else if (TextUtils.equals("2", whereFrom)) {
                    okhttpMistakeShop();//渔具店
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PublicStaticData.REPORT_ERRORS) {
            if (resultCode == PublicStaticData.ERROR_TYPE) {
                if (data != null) {
                    errorTypeId = data.getStringExtra("error_type_id");
                    errorTypeName = data.getStringExtra("error_type_name");
                    reportErrorsTvType.setText(errorTypeName);
                }
            }
        }
    }
}
