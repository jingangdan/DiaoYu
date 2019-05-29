package com.project.dyuapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.myutils.ScreenManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jingang on 2018/3/31.
 */

public class ChargeType2Activity extends MyBaseActivity {
    @Bind(R.id.tvYe)
    TextView tvYe;
    @Bind(R.id.tvJin1)
    TextView tvJin1;
    @Bind(R.id.tvJin2)
    TextView tvJin2;
    @Bind(R.id.tvJin3)
    TextView tvJin3;
    @Bind(R.id.tvJin4)
    TextView tvJin4;
    @Bind(R.id.tvJin5)
    TextView tvJin5;
    @Bind(R.id.etJin)
    EditText etJin;
    @Bind(R.id.rlJin)
    RelativeLayout rlJin;
    @Bind(R.id.tvTian1)
    TextView tvTian1;
    @Bind(R.id.tvTian2)
    TextView tvTian2;
    @Bind(R.id.tvTian3)
    TextView tvTian3;
    @Bind(R.id.tvTian4)
    TextView tvTian4;
    @Bind(R.id.tvTian5)
    TextView tvTian5;
    @Bind(R.id.tvTian6)
    TextView tvTian6;
    @Bind(R.id.etTian)
    EditText etTian;
    @Bind(R.id.rlTian)
    RelativeLayout rlTian;
    @Bind(R.id.etTime)
    EditText etTime;
    @Bind(R.id.etGan)
    EditText etGan;
    @Bind(R.id.rlTime)
    RelativeLayout rlTime;
    @Bind(R.id.rlGan)
    RelativeLayout rlGan;
    @Bind(R.id.ll_charge_type2)
    LinearLayout llChargeType2;

    private String charge_type;
    private int charge_type_code;

    private int charge_type_edit;

    private String sJin, sTian, sTime, sGan;

    private InputMethodManager imm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_charge_type2);
        ButterKnife.bind(this);

        initView();
        setClearEdit();

        etJin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    setViewColor();
                    rlJin.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    etJin.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                    etJin.setHintTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                    charge_type_code = 1;
                    charge_type = etJin.getText().toString();

                    InputMethodManager inputManager =
                            (InputMethodManager) etJin.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(etJin, 0);

                    getChargeType();

                }
            }
        });

        etTian.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    setViewColor();
                    rlTian.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    etTian.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                    etTian.setHintTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                    charge_type_code = 1;
                    charge_type = etTian.getText().toString();

                    InputMethodManager inputManager =
                            (InputMethodManager) etTian.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(etTian, 0);
                }
            }
        });

        etTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    setViewColor();
                    rlTime.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    etTime.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                    etTime.setHintTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                    charge_type_code = 1;
                    charge_type = etTime.getText().toString();

                    InputMethodManager inputManager =
                            (InputMethodManager) etTime.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(etTime, 0);
                }
            }
        });

        etGan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    setViewColor();
                    rlGan.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    etGan.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                    etGan.setHintTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                    charge_type_code = 1;
                    charge_type = etGan.getText().toString();

                    InputMethodManager inputManager =
                            (InputMethodManager) etGan.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(etGan, 0);
                }
            }
        });

        //getChargeType();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        setTvTitle("收费类型");
        setIvBack();
        getTvRight().setText("确定");
        getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //强制隐藏键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0); //强制隐藏键盘

                sJin = etJin.getText().toString().trim();
                sTian = etTian.getText().toString().trim();
                sTime = etTime.getText().toString().trim();
                sGan = etGan.getText().toString().trim();

                if (!TextUtils.isEmpty(sJin)) {
                    charge_type = sJin;
                    charge_type_code = 1;
                    charge_type_edit = 1;

                }
                if (!TextUtils.isEmpty(sTian)) {
                    charge_type = sTian;
                    charge_type_code = 3;
                    charge_type_edit = 1;
                }
                if (!TextUtils.isEmpty(sTime)) {
                    charge_type = sTime;
                    charge_type_code = 4;
                    charge_type_edit = 1;
                }
                if (!TextUtils.isEmpty(sGan)) {
                    charge_type = sGan;
                    charge_type_code = 5;
                    charge_type_edit = 1;
                }

                Intent intent = new Intent();
                intent.putExtra("charge_type", charge_type);
                intent.putExtra("charge_type_code", "" + charge_type_code);
                intent.putExtra("charge_type_edit", "" + charge_type_edit);
                setResult(Activity.RESULT_OK, intent);
                ScreenManager.getInstance().removeActivity(ChargeType2Activity.this);


            }
        });

    }

    public void getChargeType() {
        charge_type = getIntent().getStringExtra("charge_type");
        charge_type_code = Integer.parseInt(getIntent().getStringExtra("charge_type_code"));
        charge_type_edit = Integer.parseInt(getIntent().getStringExtra("charge_type_edit"));

        Log.e("11111111111111", charge_type);
        Log.e("11111111111111", "" + charge_type_code);
        Log.e("11111111111111", "" + charge_type_edit);

        if (charge_type_edit == 1) {
            //编辑框
            if (charge_type_code == 1) {
                etJin.setText(charge_type);
            }
            if (charge_type_code == 3) {
                etTian.setText(charge_type);
            }
            if (charge_type_code == 4) {
                etTime.setText(charge_type);
            }
            if (charge_type_code == 5) {
                etGan.setText(charge_type);
            }
        } else if (charge_type_edit == 0) {
            if (charge_type_code == 2) {
                //免费
                setViewColor();
                tvYe.setBackgroundResource(R.drawable.bg_click_btn_blue);
                tvYe.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
            }
            if (charge_type_code == 1) {
                if (charge_type.equals(tvJin1.getText().toString())) {
                    setViewColor();
                    setClearEdit();
                    tvJin1.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    tvJin1.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                }
                if (charge_type.equals(tvJin2.getText().toString())) {
                    setViewColor();
                    setClearEdit();
                    tvJin2.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    tvJin2.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                }
                if(charge_type.equals(tvJin3.getText().toString())){
                    setViewColor();
                    setClearEdit();
                    tvJin3.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    tvJin3.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                }
                if(charge_type.equals(tvJin4.getText().toString())){
                    setViewColor();
                    setClearEdit();
                    tvJin4.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    tvJin4.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                }
                if(charge_type.equals(tvJin5.getText().toString())){
                    setViewColor();
                    setClearEdit();
                    tvJin5.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    tvJin5.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                }
            }else if(charge_type_code  == 3){
                if(charge_type.equals(tvTian1.getText().toString())){
                    setViewColor();
                    setClearEdit();
                    tvTian1.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    tvTian1.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                }
                if(charge_type.equals(tvTian2.getText().toString())){
                    setViewColor();
                    setClearEdit();
                    tvTian2.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    tvTian2.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                }
                if(charge_type.equals(tvTian3.getText().toString())){
                    setViewColor();
                    setClearEdit();
                    tvTian3.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    tvTian3.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                }
                if(charge_type.equals(tvTian4.getText().toString())){
                    setViewColor();
                    setClearEdit();
                    tvTian4.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    tvTian4.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                }
                if(charge_type.equals(tvTian5.getText().toString())){
                    setViewColor();
                    setClearEdit();
                    tvTian5.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    tvTian5.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                }
                if(charge_type.equals(tvTian6.getText().toString())){
                    setViewColor();
                    setClearEdit();
                    tvTian6.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    tvTian6.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                }
            }
        }

    }

    @OnClick({R.id.tvYe, R.id.tvJin1, R.id.tvJin2, R.id.tvJin3, R.id.tvJin4, R.id.tvJin5, R.id.etJin, R.id.tvTian1, R.id.tvTian2, R.id.tvTian3, R.id.tvTian4, R.id.tvTian5, R.id.tvTian6, R.id.etTian, R.id.etTime, R.id.etGan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvYe:
                setViewColor();
                setClearEdit();
                tvYe.setBackgroundResource(R.drawable.bg_click_btn_blue);
                tvYe.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 2;
                charge_type = tvYe.getText().toString();
                charge_type_edit = 0;
                break;
            case R.id.tvJin1:
                setViewColor();
                setClearEdit();
                tvJin1.setBackgroundResource(R.drawable.bg_click_btn_blue);
                tvJin1.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 1;
                charge_type = tvJin1.getText().toString();
                charge_type_edit = 0;
                break;
            case R.id.tvJin2:
                setViewColor();
                setClearEdit();
                tvJin2.setBackgroundResource(R.drawable.bg_click_btn_blue);
                tvJin2.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 1;
                charge_type = tvJin2.getText().toString();
                charge_type_edit = 0;
                break;
            case R.id.tvJin3:
                setViewColor();
                setClearEdit();
                tvJin3.setBackgroundResource(R.drawable.bg_click_btn_blue);
                tvJin3.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 1;
                charge_type = tvJin3.getText().toString();
                charge_type_edit = 0;
                break;
            case R.id.tvJin4:
                setViewColor();
                setClearEdit();
                tvJin4.setBackgroundResource(R.drawable.bg_click_btn_blue);
                tvJin4.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 1;
                charge_type = tvJin4.getText().toString();
                charge_type_edit = 0;
                break;
            case R.id.tvJin5:
                setViewColor();
                setClearEdit();
                tvJin5.setBackgroundResource(R.drawable.bg_click_btn_blue);
                tvJin5.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 1;
                charge_type = tvJin5.getText().toString();
                charge_type_edit = 0;
                break;
            case R.id.etJin:
                setViewColor();
                rlJin.setBackgroundResource(R.drawable.bg_click_btn_blue);
                etJin.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                etJin.setHintTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 1;
                //charge_type = etJin.getText().toString();
                break;
            case R.id.tvTian1:
                setViewColor();
                setClearEdit();
                tvTian1.setBackgroundResource(R.drawable.bg_click_btn_blue);
                tvTian1.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 3;
                charge_type = tvTian1.getText().toString();
                charge_type_edit = 0;
                break;
            case R.id.tvTian2:
                setViewColor();
                setClearEdit();
                tvTian2.setBackgroundResource(R.drawable.bg_click_btn_blue);
                tvTian2.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 3;
                charge_type = tvTian2.getText().toString();
                charge_type_edit = 0;
                break;
            case R.id.tvTian3:
                setViewColor();
                setClearEdit();
                tvTian3.setBackgroundResource(R.drawable.bg_click_btn_blue);
                tvTian3.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 3;
                charge_type = tvTian3.getText().toString();
                charge_type_edit = 0;
                break;
            case R.id.tvTian4:
                setViewColor();
                setClearEdit();
                tvTian4.setBackgroundResource(R.drawable.bg_click_btn_blue);
                tvTian4.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 3;
                charge_type = tvTian4.getText().toString();
                charge_type_edit = 0;
                break;
            case R.id.tvTian5:
                setViewColor();
                setClearEdit();
                tvTian5.setBackgroundResource(R.drawable.bg_click_btn_blue);
                tvTian5.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 3;
                charge_type = tvTian5.getText().toString();
                charge_type_edit = 0;
                break;
            case R.id.tvTian6:
                setViewColor();
                setClearEdit();
                tvTian6.setBackgroundResource(R.drawable.bg_click_btn_blue);
                tvTian6.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 3;
                charge_type = tvTian6.getText().toString();
                charge_type_edit = 0;
                break;
            case R.id.etTian:
                setViewColor();
                rlTian.setBackgroundResource(R.drawable.bg_click_btn_blue);
                etTian.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                etTian.setHintTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 3;
                charge_type = etTian.getText().toString();
                break;
            case R.id.etTime:
                setViewColor();
                rlTime.setBackgroundResource(R.drawable.bg_click_btn_blue);
                etTime.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                etTime.setHintTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 4;
                charge_type = etTime.getText().toString();
                break;
            case R.id.etGan:
                setViewColor();
                rlGan.setBackgroundResource(R.drawable.bg_click_btn_blue);
                etGan.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                etGan.setHintTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_ffffff));
                charge_type_code = 5;
                charge_type = etGan.getText().toString();
                break;
        }
    }

    public void setViewColor() {
        tvYe.setBackgroundResource(R.drawable.bg_click_btn_gray);
        tvJin1.setBackgroundResource(R.drawable.bg_click_btn_gray);
        tvJin2.setBackgroundResource(R.drawable.bg_click_btn_gray);
        tvJin3.setBackgroundResource(R.drawable.bg_click_btn_gray);
        tvJin4.setBackgroundResource(R.drawable.bg_click_btn_gray);
        tvJin5.setBackgroundResource(R.drawable.bg_click_btn_gray);

        tvTian1.setBackgroundResource(R.drawable.bg_click_btn_gray);
        tvTian2.setBackgroundResource(R.drawable.bg_click_btn_gray);
        tvTian3.setBackgroundResource(R.drawable.bg_click_btn_gray);
        tvTian4.setBackgroundResource(R.drawable.bg_click_btn_gray);
        tvTian5.setBackgroundResource(R.drawable.bg_click_btn_gray);
        tvTian6.setBackgroundResource(R.drawable.bg_click_btn_gray);

        rlJin.setBackgroundResource(R.drawable.bg_click_btn_gray);
        rlTian.setBackgroundResource(R.drawable.bg_click_btn_gray);
        rlTime.setBackgroundResource(R.drawable.bg_click_btn_gray);
        rlGan.setBackgroundResource(R.drawable.bg_click_btn_gray);

        tvYe.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));
        tvJin1.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));
        tvJin2.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));
        tvJin3.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));
        tvJin4.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));
        tvJin5.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));

        tvTian1.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));
        tvTian2.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));
        tvTian3.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));
        tvTian4.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));
        tvTian5.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));
        tvTian6.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));

        etJin.setText("");
        etTian.setText("");
        etTime.setText("");
        etGan.setText("");

        etJin.setHintTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));
        etTian.setHintTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));
        etTime.setHintTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));
        etGan.setHintTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));

    }

    public void setClearEdit() {
        imm = (InputMethodManager) ChargeType2Activity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏虚拟键盘
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(this.getWindow().peekDecorView().getWindowToken(), 0);
        }

        rlJin.setBackgroundResource(R.drawable.bg_click_btn_gray);
        etJin.setTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));
        etJin.setHintTextColor(ChargeType2Activity.this.getResources().getColor(R.color.c_666666));

    }

    @OnClick(R.id.ll_charge_type2)
    public void onViewClicked() {
        // setViewColor();
        //  setClearEdit();
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(this.getWindow().peekDecorView().getWindowToken(), 0);
        }
    }
}
