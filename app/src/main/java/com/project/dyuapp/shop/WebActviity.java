package com.project.dyuapp.shop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Describe:
 * Created by jingang on 2019/5/29
 */
public class WebActviity extends MyBaseActivity {
    @Bind(R.id.web_frame)
    FrameLayout webFrame;
    @Bind(R.id.adView)
    AdView adView;
    //    @Bind(R.id.webView)
    WebView webView;

    private String wcUrl = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        wcUrl = getIntent().getStringExtra("wcUrl");
        Log.e("ssssss", "url = " + wcUrl);

        setWebView();
    }

    public void setWebView() {
        webView = new WebView(this);
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        //解决一些图片加载问题
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setDomStorageEnabled(true);
        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Referer", "https://");

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                //view.loadUrl(url);
//                //设置加载进度条
//                //  view.setWebChromeClient(new GoodsDetailActivity.WebChromeClientProgress());
//                try {
//                    if (url.startsWith("http:") || url.startsWith("https:")) {
//                        view.loadUrl(url);
//                    } else {
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                        startActivity(intent);
//                        WebActviity.this.finish();
//                    }
//                    return true;
//                } catch (Exception e) {
//                    return false;
//                }
//            }
//
//        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                    } else {
                        view.loadUrl("https://");
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        webFrame.addView(webView);
        webView.loadUrl(wcUrl, extraHeaders);
        setAd();
    }

    public void setAd() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
            }
        });
        adView.loadAd(adRequest);
    }
}
