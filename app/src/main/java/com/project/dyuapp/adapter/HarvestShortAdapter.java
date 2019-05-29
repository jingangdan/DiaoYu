package com.project.dyuapp.adapter;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.activity.HarvestShortActivity;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.VideoListEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author gengqiufang
 * @describtion 渔乐短片
 * @created at 2017/12/8 0008
 */

public class HarvestShortAdapter extends MyCommonAdapter<VideoListEntity> {
    private ArrayList<String> strTabList = new ArrayList<>();
//    private ArrayList<InWebView> arrayInWebView = new ArrayList<>();
//
//    public ArrayList<InWebView> getArrayInWebView() {
//        return arrayInWebView;
//    }

    private FrameLayout mFrameLayout;
    private MyWebChromeClient mMyWebChromeClient;

    private HashMap<Integer, WebView> hashWh = new HashMap<>();
    private HarvestShortActivity harvestShort;

    public HarvestShortAdapter(List<VideoListEntity> list, Context mContext) {
        super(list, mContext, R.layout.item_harvest_short_wb);
        strTabList.add("视频标签");
        strTabList.add("视频标签");
        harvestShort = (HarvestShortActivity) mContext;
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        WebView wv = (WebView) commentViewHolder.getConverView().findViewById(R.id.harvest_short_wv);
        TagFlowLayout xcLy = (TagFlowLayout) commentViewHolder.getConverView().findViewById(R.id.item_harvest_short_fishing_fl);
        mFrameLayout = (FrameLayout) commentViewHolder.getConverView().findViewById(R.id.video_fullView);
        TextView content_tv = (TextView) commentViewHolder.getConverView().findViewById(R.id.item_harvest_short_content_tv);
        TextView fabuous_tv = (TextView) commentViewHolder.getConverView().findViewById(R.id.item_harvest_short_fabulous_tv);

        hashWh.put(position, wv);

//        InWebView inWeb= new InWebView(wv);
//        arrayInWebView.add(inWeb);
//        if (list.get(position).isShow()) {
//            inWeb.loadUrl(list.get(position).getUrl());
//        } else {
//            inWeb.loadUrl("");
//        }

    /*    WebSettings webSetting = wv.getSettings();
        webSetting.setDefaultTextEncodingName("utf-8");
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDomStorageEnabled(true);//解决无法播放优酷视频网页
        wv.setWebChromeClient(new WebChromeClient());//避免偶然问题
        wv.setWebViewClient(new WebViewClient() {//不写的话自动跳到默认浏览器了
            public boolean shouldOverrideUrlLoading(WebView view, String url) {//不写否则会出现优酷视频周末无法播放。周一-周五可以播放的问题
                if (url.startsWith("intent") || url.startsWith("youku")) {
                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }
        });*/

        initWebView(wv);
        wv.loadUrl(list.get(position).getUrl());
        xcLy.setAdapter(new TagAdapter(strTabList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.flow_layout_tag_fishing_tv, parent, false);
                tv.setText(strTabList.get(position));
                return tv;
            }
        });
    }

    private void initWebView(WebView mWebView) {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mMyWebChromeClient = new MyWebChromeClient(mWebView);
        mWebView.setWebChromeClient(mMyWebChromeClient);
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    private class MyWebChromeClient extends WebChromeClient {
        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;
        private WebView mWebView;

        public MyWebChromeClient(WebView mWebView) {
            this.mWebView = mWebView;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
//            if (mCustomView != null) {
//                callback.onCustomViewHidden();
//                return;
//            }
//            mCustomView = view;
//            mFrameLayout.addView(mCustomView);
//            mCustomViewCallback = callback;
//            mWebView.setVisibility(View.GONE);
//            harvestShort.setmWebView(mWebView);
            harvestShort.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        public void onHideCustomView() {
//            mWebView.setVisibility(View.VISIBLE);
//            if (mCustomView == null) {
//                return;
//            }
//            mCustomView.setVisibility(View.GONE);
//            mFrameLayout.removeView(mCustomView);
//            mCustomViewCallback.onCustomViewHidden();
//            mCustomView = null;
            harvestShort.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            super.onHideCustomView();
        }
    }

    public void onPause() {

    }
}
