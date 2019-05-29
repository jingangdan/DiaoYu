package com.project.dyuapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.adapter.ChargeTypeCostAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.entity.ChargeTypeCostBean;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myutils.ToastUtils;
import com.project.dyuapp.myviews.GridViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 田亭亭
 * @description 收费类型Activity
 * @created at 2017/11/29 0029
 * @change
 */
public class ChargeTypeActivity extends MyBaseActivity {

    @Bind(R.id.charge_type_tv_yetang_free)
    TextView tvYetangFree;//野塘（免费）收费类型值
    @Bind(R.id.charge_type_gv_jintang_list)
    GridViewForScrollView gvJintang;//斤塘
    @Bind(R.id.charge_type_gv_tiantang_list)
    GridViewForScrollView gvTiantang;//天塘

    public List<ChargeTypeCostBean> jintangList; //斤塘收费类型集合
    public List<ChargeTypeCostBean> tiantangList;//天塘收费类型集合

    public ChargeTypeCostAdapter mJinAdapter;//斤塘收费类型适配器
    public ChargeTypeCostAdapter mTianAdapter;//天塘收费类型适配器

    @Bind(R.id.et_chager_type_time)
    EditText etChagerTypeTime;
    @Bind(R.id.rl_charge_type_time)
    RelativeLayout rlChargeTypeTime;
    @Bind(R.id.et_chager_type_gan)
    EditText etChagerTypeGan;
    @Bind(R.id.rl_charge_type_gan)
    RelativeLayout rlChargeTypeGan;


    private int jinPosition = -1;//斤塘收费类型位置
    private int tianPosition = -1;//天塘收费类型位置

    private int i = 0;
    private String yeTangCost = "";//野塘收费类型值
    private String jinTangCost = "";//斤塘收费类型值
    private String tianTangCost = "";//天塘收费类型值

    private String timeCost = "";
    private String ganCost = "";

    public String charge_type_code = "";
    public String charge_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_charge_type);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("charge_type_code"))) {
            charge_type_code = getIntent().getStringExtra("charge_type_code");
            if ("2".equals(charge_type_code))
                onViewClicked();
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("charge_type"))) {
            charge_type = getIntent().getStringExtra("charge_type");
        }
        initView();
        initData();

        etChagerTypeTime.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //清除选中状态
                    clearSelectOne(-1);

                    etChagerTypeGan.setText("");
                    etChagerTypeTime.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    etChagerTypeTime.setHintTextColor(ChargeTypeActivity.this.getResources().getColor(R.color.c_ffffff));
                    etChagerTypeTime.setTextColor(ChargeTypeActivity.this.getResources().getColor(R.color.c_ffffff));
                    etChagerTypeTime.setSelection(etChagerTypeTime.getText().toString().length());

                } else {
                    etChagerTypeTime.setBackgroundResource(R.drawable.bg_click_btn_gray);
                    etChagerTypeTime.setHintTextColor(ChargeTypeActivity.this.getResources().getColor(R.color.c_666666));
                }
            }
        });

        etChagerTypeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) etChagerTypeTime.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etChagerTypeTime, 0);
            }
        });

        etChagerTypeGan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //获得焦点
                if (b) {
                    clearSelectOne(-1);
                    etChagerTypeTime.setText("");
                    etChagerTypeGan.setBackgroundResource(R.drawable.bg_click_btn_blue);
                    etChagerTypeGan.setHintTextColor(ChargeTypeActivity.this.getResources().getColor(R.color.c_ffffff));
                    etChagerTypeGan.setSelection(etChagerTypeGan.getText().toString().length());
                    etChagerTypeGan.setTextColor(ChargeTypeActivity.this.getResources().getColor(R.color.c_ffffff));
                } else {
                    etChagerTypeGan.setBackgroundResource(R.drawable.bg_click_btn_gray);
                    etChagerTypeGan.setHintTextColor(ChargeTypeActivity.this.getResources().getColor(R.color.c_666666));
                }
            }
        });

    }

    /**
     * 设置收费类型数据
     */
    private void initData() {
        jintangList = new ArrayList<>();
        jintangList.add(new ChargeTypeCostBean(false, "10元"));
        jintangList.add(new ChargeTypeCostBean(false, "15元"));
        jintangList.add(new ChargeTypeCostBean(false, "18元"));
        jintangList.add(new ChargeTypeCostBean(false, "20元"));
        jintangList.add(new ChargeTypeCostBean(false, "25元"));
        jintangList.add(new ChargeTypeCostBean(false, "自定义"));
        if ("1".equals(charge_type_code)) {
            mJinAdapter = new ChargeTypeCostAdapter(jintangList, ChargeTypeActivity.this, R.layout.item_charge_type_cost, 1, charge_type_code, charge_type);
        } else {
            mJinAdapter = new ChargeTypeCostAdapter(jintangList, ChargeTypeActivity.this, R.layout.item_charge_type_cost, 1);
        }
        gvJintang.setAdapter(mJinAdapter);
        mJinAdapter.setOnItemClickListener(new ChargeTypeCostAdapter.OnItemClickListener() {
            @Override
            public void onCustomClick(View view, int position) {
                clearSelectTwo();
                for (ChargeTypeCostBean person : jintangList) { //遍历list集合中的数据
                    person.setSelect(false);//全部设为未选中
                }
                if (jinPosition == -1) { //选中
                    if (position == jintangList.size() - 1) {
                        ToastUtils.getInstance(ChargeTypeActivity.this).showMessage("斤塘自定义");
                        jintangList.get(position).setSelect(false);
                        mJinAdapter.notifyDataSetChanged();//刷新adapter
                        return;
                    }
                    jintangList.get(position).setSelect(true);
                    jinPosition = position;
                } else if (jinPosition == position) { //同一个item选中变未选中
                    for (ChargeTypeCostBean person : jintangList) {
                        person.setSelect(false);
                    }
                    jinPosition = -1;
                } else if (jinPosition != position) { //不是同一个item选中当前的，去除上一个选中的
                    for (ChargeTypeCostBean person : jintangList) {
                        person.setSelect(false);
                    }
                    jintangList.get(position).setSelect(true);
                    jinPosition = position;
                }
                clearAdapterEdtFocusable(-1);
                jinTangCost = jintangList.get(jinPosition).getName();

                mJinAdapter.charge_type_codesj = "";
                mJinAdapter.charge_types = "";
                mJinAdapter.setRlBackGround(-1);
                clearSelect();

                mJinAdapter.notifyDataSetChanged();//刷新adapter
                mTianAdapter.notifyDataSetChanged();
            }
        });

        tiantangList = new ArrayList<>();
        tiantangList.add(new ChargeTypeCostBean(false, "50元"));
        tiantangList.add(new ChargeTypeCostBean(false, "80元"));
        tiantangList.add(new ChargeTypeCostBean(false, "100元"));
        tiantangList.add(new ChargeTypeCostBean(false, "150元"));
        tiantangList.add(new ChargeTypeCostBean(false, "200元"));
        tiantangList.add(new ChargeTypeCostBean(false, "300元"));
        tiantangList.add(new ChargeTypeCostBean(false, "自定义"));
        if ("3".equals(charge_type_code)) {
            mTianAdapter = new ChargeTypeCostAdapter(tiantangList, ChargeTypeActivity.this, R.layout.item_charge_type_cost, 2, charge_type_code, charge_type);
        } else {
            mTianAdapter = new ChargeTypeCostAdapter(tiantangList, ChargeTypeActivity.this, R.layout.item_charge_type_cost, 2);
        }
        gvTiantang.setAdapter(mTianAdapter);
        mTianAdapter.setOnItemClickListener(new ChargeTypeCostAdapter.OnItemClickListener() {
            @Override
            public void onCustomClick(View view, int position) {
                clearSelectThere();
                for (ChargeTypeCostBean person : tiantangList) { //遍历list集合中的数据
                    person.setSelect(false);//全部设为未选中
                }
                if (tianPosition == -1) { //选中
                    if (position == tiantangList.size() - 1) {
                        ToastUtils.getInstance(ChargeTypeActivity.this).showMessage("天塘自定义");
                        tiantangList.get(position).setSelect(false);
                        mTianAdapter.notifyDataSetChanged();//刷新adapter
                        return;
                    }
                    tiantangList.get(position).setSelect(true);
                    tianPosition = position;
                } else if (tianPosition == position) { //同一个item选中变未选中
                    for (ChargeTypeCostBean person : tiantangList) {
                        person.setSelect(false);
                    }
                    tianPosition = -1;
                } else if (tianPosition != position) { //不是同一个item选中当前的，去除上一个选中的
                    for (ChargeTypeCostBean person : tiantangList) {
                        person.setSelect(false);
                    }
                    tiantangList.get(position).setSelect(true);
                    tianPosition = position;
                }
                clearAdapterEdtFocusable(-1);
                tianTangCost = tiantangList.get(tianPosition).getName();

                mTianAdapter.charge_type_codesj = "";
                mTianAdapter.charge_types = "";
                mTianAdapter.setRlBackGround(-1);
                clearSelect();

                mTianAdapter.notifyDataSetChanged();//刷新adapter
                mJinAdapter.notifyDataSetChanged();
            }
        });

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

                String charge_type = "";
                String charge_type_code = "";

                if ("自定义".equals(jintangList.get(5).getName()) && mJinAdapter.getEdt(1) != null
                        && !TextUtils.isEmpty(mJinAdapter.getEdt(1).getText().toString().trim())) {
                    charge_type = mJinAdapter.getEdt(1).getText().toString().trim() + "元";
                    charge_type_code = "1";
                } else if ("自定义".equals(tiantangList.get(6).getName()) && !TextUtils.isEmpty(mTianAdapter.getEdt(2).getText().toString().trim())) {
                    charge_type = mTianAdapter.getEdt(2).getText().toString().trim() + "元";
                    charge_type_code = "3";
                } else if (!TextUtils.isEmpty(yeTangCost)) {
                    charge_type = yeTangCost;
                    charge_type_code = "2";
                } else if (!TextUtils.isEmpty(jinTangCost)) {
                    charge_type = jinTangCost;
                    charge_type_code = "1";
                } else if (!TextUtils.isEmpty(tianTangCost)) {
                    charge_type = tianTangCost;
                    charge_type_code = "3";
                } else if (!TextUtils.isEmpty(etChagerTypeTime.getText().toString().trim())) {
                    charge_type = etChagerTypeTime.getText().toString().trim() + "元";
                    charge_type_code = "4";
                } else if (!TextUtils.isEmpty(etChagerTypeGan.getText().toString().trim())) {
                    charge_type = etChagerTypeGan.getText().toString().trim() + "元";
                    charge_type_code = "5";
                } else {
                    if (!TextUtils.isEmpty(ChargeTypeActivity.this.charge_type_code) && !TextUtils.isEmpty(ChargeTypeActivity.this.charge_type)) {
                        Intent intent = new Intent();
                        intent.putExtra("charge_type", charge_type);
                        intent.putExtra("charge_type_code", charge_type_code);
                        setResult(Activity.RESULT_OK, intent);
                        ScreenManager.getInstance().removeActivity(ChargeTypeActivity.this);
                    } else {
                        ToastUtils.getInstance(ChargeTypeActivity.this).showMessage("请选择收费类型");
                    }
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("charge_type", charge_type);
                intent.putExtra("charge_type_code", charge_type_code);
                setResult(Activity.RESULT_OK, intent);
                ScreenManager.getInstance().removeActivity(ChargeTypeActivity.this);


            }
        });

    }

    @OnClick(R.id.charge_type_tv_yetang_free)
    public void onViewClicked() {
        if (i % 2 == 0) {
            tvYetangFree.setBackgroundResource(R.drawable.bg_blue_round_angle);
            tvYetangFree.setTextColor(ChargeTypeActivity.this.getResources().getColor(R.color.c_ffffff));
            if (jintangList != null && tiantangList != null)
                clearSelectOne(-1);
            yeTangCost = "免费";
        } else {
            tvYetangFree.setBackgroundResource(R.drawable.bg_gray_round_angle);
            tvYetangFree.setTextColor(ChargeTypeActivity.this.getResources().getColor(R.color.c_666666));
            yeTangCost = "";
        }
        i++;
    }

    /**
     * 野塘
     */
    public void clearSelectOne(int a) {
        //判断是否为适配器调用 1 斤塘 2 天塘
        if (a == -1) {
            jinPosition = -1;
            tianPosition = -1;
            for (ChargeTypeCostBean person : jintangList) {
                person.setSelect(false);
            }
            mJinAdapter.notifyDataSetChanged();
            for (ChargeTypeCostBean person : tiantangList) {
                person.setSelect(false);
            }
            mTianAdapter.notifyDataSetChanged();

        } else if (a == 1) {//1 斤塘
            jinPosition = -1;
            tianPosition = -1;
            for (ChargeTypeCostBean person : jintangList) {
                person.setSelect(false);
            }
            mJinAdapter.notifyDataSetChanged();
            for (ChargeTypeCostBean person : tiantangList) {
                person.setSelect(false);
            }
            mTianAdapter.notifyDataSetChanged();

        } else if (a == 2) {//2 天塘
            jinPosition = -1;
            tianPosition = -1;
            for (ChargeTypeCostBean person : jintangList) {
                person.setSelect(false);
            }
            mJinAdapter.notifyDataSetChanged();
            for (ChargeTypeCostBean person : tiantangList) {
                person.setSelect(false);
            }
            mTianAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 斤塘
     */
    private void clearSelectTwo() {
        i = 0;
        jinPosition = -1;
        tvYetangFree.setBackgroundResource(R.drawable.bg_gray_round_angle);
        tvYetangFree.setTextColor(ChargeTypeActivity.this.getResources().getColor(R.color.c_666666));
        for (ChargeTypeCostBean person : tiantangList) {
            person.setSelect(false);
        }

        clearTimeGanEdtFocusable();
    }

    /**
     * 天塘
     */
    private void clearSelectThere() {
        i = 0;
        tianPosition = -1;
        tvYetangFree.setBackgroundResource(R.drawable.bg_gray_round_angle);
        tvYetangFree.setTextColor(ChargeTypeActivity.this.getResources().getColor(R.color.c_666666));
        for (ChargeTypeCostBean person : jintangList) {
            person.setSelect(false);
        }
        mJinAdapter.notifyDataSetChanged();

        clearTimeGanEdtFocusable();

    }

    public void clearSelect() {
        jintangList.get(jintangList.size() - 1).setShowBraw(false);
        tiantangList.get(tiantangList.size() - 1).setShowBraw(false);
    }

    /**
     * 清空适配器中输入框的焦点
     */
    public void clearAdapterEdtFocusable(int a) {
        if (a == 2) {
            if (mTianAdapter.custom_tiantang != null) {
                //使输入框获得焦点并弹出输入法
                mTianAdapter.custom_tiantang.setFocusable(true);
                mTianAdapter.custom_tiantang.setFocusableInTouchMode(true);
                mTianAdapter.custom_tiantang.requestFocus();

            }
        } else if (a == 1) {
            if (mJinAdapter.custom_jintang != null) {
                //使输入框获得焦点并弹出输入法
                mJinAdapter.custom_jintang.setFocusable(true);
                mJinAdapter.custom_jintang.setFocusableInTouchMode(true);
                mJinAdapter.custom_jintang.requestFocus();
            }
        } else {
            if (mJinAdapter.custom_jintang != null) {
                mJinAdapter.custom_jintang.setFocusable(false);
                mJinAdapter.custom_jintang.setText("");
                //clearTimeGanEdtFocusable();
            }
            if (mTianAdapter.custom_tiantang != null) {
                mTianAdapter.custom_tiantang.setFocusable(false);
                mTianAdapter.custom_tiantang.setText("");
               //clearTimeGanEdtFocusable();
            }
        }
    }

    public void clearTimeGanEdtFocusable() {
        etChagerTypeTime.setText("");
        etChagerTypeTime.setBackgroundResource(R.drawable.bg_click_btn_gray);

        etChagerTypeGan.setText("");
        etChagerTypeGan.setBackgroundResource(R.drawable.bg_click_btn_gray);
    }

}
