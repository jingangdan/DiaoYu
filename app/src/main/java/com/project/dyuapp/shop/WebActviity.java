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

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;

import java.net.URLDecoder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Describe:
 * Created by jingang on 2019/5/29
 */
public class WebActviity extends MyBaseActivity {
    @Bind(R.id.web_frame)
    FrameLayout webFrame;
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

//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setSupportZoom(true); //设置可以支持缩放
//        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        webView.loadUrl(wcUrl);

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
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        webFrame.addView(webView);
        webView.loadUrl(wcUrl);

        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);
                //设置加载进度条
                //  view.setWebChromeClient(new GoodsDetailActivity.WebChromeClientProgress());

                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        WebActviity.this.finish();
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
//                return true;
            }

        });
    }


    public void setWebView2() {
        // 设置允许加载混合内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 允许所有SSL证书
        webView.setWebViewClient(new WebViewClient() {
            /**
             * Description: handle https
             * Created by Michael Lee on 12/6/16 08:38
             */
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String decoded_url = url;
                try {
                    decoded_url = URLDecoder.decode(url, "UTF-8");
                    Log.d("WebView", "shouldOverrideUrlLoading's url is :" + decoded_url);
                } catch (Exception e) {
                    Log.e("WebView", "Catch error when decoding. Error message : " + e);
                }

                if (!checkUrlValid(decoded_url)) {
                    super.shouldOverrideUrlLoading(view, url);
                    Log.d("WebView", "Handle url with system~~");
                    return false;
                } else {
                    // Do your special things
                    return true;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("WebView", "onPageStarted : " + url);
                if (!checkUrlValid(url)) {
                    super.onPageStarted(view, url, favicon);
                    Log.d("WebView", "Handle url with system~~");
                    return;
                } else {
                    // Do your special things
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("WebView", "onPageFinished : " + url);
                if (!checkUrlValid(url)) {
                    super.onPageFinished(view, url);
                    Log.d("WebView", "Handle url with system~~");
                    return;
                } else {
                    // Do your special things
                }
            }
        });
        // 试着加载一个HTTPs的页面
        webView.loadUrl(wcUrl);
    }

    private boolean checkUrlValid(String aUrl) {
        boolean result = true;
        if (aUrl == null || aUrl.equals("") || !aUrl.contains("http")) {
            return false;
        }
        if (aUrl.contains("s.click")) {
            result = false;
        }
        return result;
    }

}
