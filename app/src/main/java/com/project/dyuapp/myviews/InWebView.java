package com.project.dyuapp.myviews;

import android.annotation.SuppressLint;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class InWebView {
    private WebView wv;

    public InWebView(WebView wv) {
        this.wv = wv;
        initWebView();
    }


    @SuppressLint("NewApi")
    private void initWebView() {

        //webview的配置
        WebSettings setting = wv.getSettings();
        setting.setJavaScriptEnabled(true);

        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        setting.setUseWideViewPort(true);//关键点

        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕

        setting.setSupportZoom(true);//支持缩放
        setting.setBuiltInZoomControls(true);
        setting.setAppCacheEnabled(true);
        setting.setDisplayZoomControls(false);//显示缩放按钮
        setting.setBlockNetworkImage(false);
        setting.setAllowFileAccess(true);//允许访问文件

        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        setting.setLoadWithOverviewMode(true);//适配时很重要

        setting.setDomStorageEnabled(true);

        wv.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                wv.loadUrl(url);

                return true;
            }
        });
    }

    /**
     * 加载网页
     *
     * @param url 网址
     */
    public void loadUrl(String url) {
        wv.loadUrl(url);
    }

    public void myDestroy() {
        wv.stopLoading();
        wv.removeAllViews();
        wv.destroy();
        wv = null;
    }
}
