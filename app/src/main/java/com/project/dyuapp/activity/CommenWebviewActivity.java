package com.project.dyuapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myviews.InWebView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @功能描述： 公用webview界面
 * @Author： 刘晓丽
 * @data： 2016/12/29 09:30
 */

public class CommenWebviewActivity extends MyBaseActivity {

    @Bind(R.id.commen_wv_wv)
    WebView commenWvWv;

    private InWebView wv;

    private String title = "";
    private String url = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.commen_wv);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        setTvTitle(title);
        getIvBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commenWvWv.canGoBack()) {
                    commenWvWv.goBack();// 返回前一个页面
                } else {
                    ScreenManager.getInstance().removeActivity(CommenWebviewActivity.this);
                }
            }
        });
    }

    private void initData() {
        title = this.getIntent().getStringExtra("title");
        url = this.getIntent().getStringExtra("url");
        wv = new InWebView(commenWvWv);
        wv.loadUrl(url);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wv != null) {
            wv.myDestroy();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (commenWvWv.canGoBack()) {
                commenWvWv.goBack();// 返回前一个页面
                return true;
            } else {
                ScreenManager.getInstance().removeActivity(this);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
