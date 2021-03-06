package com.uuzuche.lib_zxing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uuzuche.lib_zxing.R;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * @describe：基础Activity
 * @author：刘晓丽
 * @createdate：2017/11/20 9:45
 */

public abstract class MyBaseActivity extends AutoLayoutActivity {

    private ImageButton title_ibtn_back;//返回按钮
    private TextView title_tv_title;//标题
    private FrameLayout base_fl_content;//内容布局
    private RelativeLayout rlTitle;//标题

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initBaseView();
    }

    private void initBaseView() {
        title_ibtn_back = (ImageButton) findViewById(R.id.title_ibtn_back);
        title_tv_title = (TextView) findViewById(R.id.title_tv_title);
        base_fl_content = (FrameLayout) findViewById(R.id.base_fl_content);
        rlTitle = (RelativeLayout) findViewById(R.id.viewtitle_rl);
    }

    public void setBaseContentView(int layoutID) {
        View view = LayoutInflater.from(this).inflate(layoutID, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
        base_fl_content.addView(view);
    }

    public ImageButton getIvBack() {
        title_ibtn_back.setVisibility(View.VISIBLE);
        return title_ibtn_back;
    }

    public RelativeLayout getRlTitle() {
        return rlTitle;
    }

    public void setIvBack() {
        title_ibtn_back.setVisibility(View.VISIBLE);
        title_ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public TextView getTvTitle() {
        title_tv_title.setVisibility(View.VISIBLE);
        return title_tv_title;
    }

    public void setTvTitle(String title) {
        title_tv_title.setVisibility(View.VISIBLE);
        title_tv_title.setText(title != null ? title : "");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showMessage(String message) {
//        ToastUtils.getInstance(MyBaseActivity.this).showMessage(message);
    }

    /**
     * 跳转到指定的activity
     *
     * @param clazz 目标activity
     */
    public void goToActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
