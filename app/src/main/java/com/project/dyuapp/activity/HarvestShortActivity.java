package com.project.dyuapp.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.callback.StateEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myviews.VideoEnabledWebView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @创建者 任伟
 * @创建时间 2017/09/07 17:49
 * @描述 ${首页—渔乐短片}
 */
public class HarvestShortActivity extends MyBaseActivity {

    @Bind(R.id.harvest_short_ll)
    LinearLayout harvesShortLl;

    private WebView mWebView;
    private FrameLayout mHomeFrameLayout;
    private TextView mHomeharvesShortTitle;// 标题布局
    private RelativeLayout mHomeharvestShortTitleRl;// 标签按钮布局
    private TextView mHomeFabulousTv;//点赞
    private MyWebChromeClient mMyWebChromeClient;
    String url = "http://player.youku.com/embed/XMzIwMzc1NjcxMg";
//    String url = "http://m.tv.sohu.com/20130704/n380744170.shtml";

    private RelativeLayout rltitle;// 标题布局
    private HashMap<Integer, View> hashView = new HashMap<>();
    private NetBean.HarvestShortListEntity harvestShortListEntity = new NetBean.HarvestShortListEntity();
    private int page = 1;
    private int vertical = 1;//当前屏幕方向 1竖向2横向

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_harvest_short);
        ButterKnife.bind(this);
        setIvBack();
        rltitle = getRlTitle();
        setTvTitle("渔乐短片");
        initView(harvestShortListEntity);
        getHttpList();
    }

    private void initWebView(WebView mwv, FrameLayout mFrameLayout, TextView harvesTitleTv, RelativeLayout harvestShorRl) {
        WebSettings settings = mwv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDisplayZoomControls(false); //隐藏webview缩放按钮
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mMyWebChromeClient = new MyWebChromeClient(mwv, mFrameLayout, harvesTitleTv, harvestShorRl);// 记录当前操作的控件
        mwv.setWebChromeClient(mMyWebChromeClient);
        mwv.setWebViewClient(new WebViewClient() {
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

    public class VideoEnabledWebChromeClient extends WebChromeClient implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

        private View activityNonVideoView;
        private ViewGroup activityVideoView;
        private View loadingView;
        private VideoEnabledWebView webView;
        private boolean isVideoFullscreen; // Indicates if the video is being displayed using a custom view (typically full-screen)
        private FrameLayout videoViewContainer;
        private CustomViewCallback videoViewCallback;
        private Activity mContext;

        private VideoEnabledWebView wb;
        private FrameLayout mFrameLayout;
        private TextView harvesShortTitle;// 标题布局
        private RelativeLayout harvestShortTitleRl;// 标签按钮布局

        /**
         * Builds a video enabled WebChromeClient.
         *
         * @param activityNonVideoView A View in the activity's layout that contains every other view that should be hidden when the video goes full-screen.
         * @param activityVideoView    A ViewGroup in the activity's layout that will display the video. Typically you would like this to fill the whole layout.
         */
        public VideoEnabledWebChromeClient(Activity activity, View activityNonVideoView,
                                           ViewGroup activityVideoView,VideoEnabledWebView mwv, FrameLayout mFrameLayout, TextView harvesTitleTv, RelativeLayout harvestShorRl) {
            this.mContext = activity;
            this.activityNonVideoView = activityNonVideoView;
            this.activityVideoView = activityVideoView;
            this.loadingView = null;
            this.webView = null;
            this.isVideoFullscreen = false;

            this.wb = mwv;
            this.mFrameLayout = mFrameLayout;
            this.harvesShortTitle = harvesTitleTv;
            this.harvestShortTitleRl = harvestShorRl;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (view instanceof FrameLayout) {
                // A video wants to be shown
                FrameLayout frameLayout = (FrameLayout) view;
                View focusedChild = frameLayout.getFocusedChild();
                // Save video related variables
                this.isVideoFullscreen = true;
                this.videoViewContainer = frameLayout;
                this.videoViewCallback = callback;
                // Hide the non-video view, add the video view, and show it
                activityNonVideoView.setVisibility(View.GONE);
                activityVideoView.addView(videoViewContainer, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
                activityVideoView.setVisibility(View.VISIBLE);
                if (focusedChild instanceof VideoView) {
                    // VideoView (typically API level <11)
                    VideoView videoView = (VideoView) focusedChild;
                    // Handle all the required events
                    videoView.setOnPreparedListener(this);
                    videoView.setOnCompletionListener(this);
                    videoView.setOnErrorListener(this);
                } else // Usually android.webkit.HTML5VideoFullScreen$VideoSurfaceView, sometimes android.webkit.HTML5VideoFullScreen$VideoTextureView
                {
                    // HTML5VideoFullScreen (typically API level 11+)
                    // Handle HTML5 video ended event
                    if (webView != null && webView.getSettings().getJavaScriptEnabled()) {
                        // Run javascript code that detects the video end and notifies the interface
                        String js = "javascript:";
                        js += "_ytrp_html5_video = document.getElementsByTagName('video')[0];";
                        js += "if (_ytrp_html5_video !== undefined) {";
                        {
                            js += "function _ytrp_html5_video_ended() {";
                            {
                                js += "_ytrp_html5_video.removeEventListener('ended', _ytrp_html5_video_ended);";
                                js += "_VideoEnabledWebView.notifyVideoEnd();"; // Must match Javascript interface name and method of VideoEnableWebView
                            }
                            js += "}";
                            js += "_ytrp_html5_video.addEventListener('ended', _ytrp_html5_video_ended);";
                        }
                        js += "}";
                        webView.loadUrl(js);
                    }
                }

                setFullscreen(true);
            }
        }

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) // Only available in API level 14+
        {
            onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            if (isVideoFullscreen) {
                // Hide the video view, remove it, and show the non-video view
                activityVideoView.setVisibility(View.GONE);//播放视频的
                activityVideoView.removeView(videoViewContainer);
                activityNonVideoView.setVisibility(View.VISIBLE);

                // Call back
                if (videoViewCallback != null) videoViewCallback.onCustomViewHidden();

                // Reset video related variables
                isVideoFullscreen = false;
                videoViewContainer = null;
                videoViewCallback = null;

                setFullscreen(false);
            }
        }

        @Override
        public View getVideoLoadingProgressView() // Video will start loading, only called in the case of VideoView (typically API level <11)
        {
            if (loadingView != null) {
                loadingView.setVisibility(View.VISIBLE);
                return loadingView;
            } else {
                return super.getVideoLoadingProgressView();
            }
        }

        @Override
        public void onPrepared(MediaPlayer mp) // Video will start playing, only called in the case of VideoView (typically API level <11)
        {
            if (loadingView != null) {
                loadingView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCompletion(MediaPlayer mp) // Video finished playing, only called in the case of VideoView (typically API level <11)
        {
            onHideCustomView();
        }

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) // Error while playing video, only called in the case of VideoView (typically API level <11)
        {
            return false; // By returning false, onCompletion() will be called
        }

        private void setFullscreen(boolean enable) {
            if (enable) { //show status bar
                mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
                lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                mContext.getWindow().setAttributes(lp);
                mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            } else { //hide status bar
                mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
                lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mContext.getWindow().setAttributes(lp);
                mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            }
            //记录当前操作的控件
            mWebView = wb;
            mHomeFrameLayout = mFrameLayout;
            mHomeharvesShortTitle = harvesShortTitle;
            mHomeharvestShortTitleRl = harvestShortTitleRl;
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;
        private WebView wb;
        private FrameLayout mFrameLayout;
        private TextView harvesShortTitle;// 标题布局
        private RelativeLayout harvestShortTitleRl;// 标签按钮布局

        public MyWebChromeClient(WebView mWebView, FrameLayout mFrameLayout, TextView harvesTitleTv, RelativeLayout harvestShorRl) {
            this.wb = mWebView;
            this.mFrameLayout = mFrameLayout;
            this.harvesShortTitle = harvesTitleTv;
            this.harvestShortTitleRl = harvestShorRl;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            //记录当前操作的控件
            mWebView = wb;
            mHomeFrameLayout = mFrameLayout;
            mHomeharvesShortTitle = harvesShortTitle;
            mHomeharvestShortTitleRl = harvestShortTitleRl;

            mHomeFrameLayout.addView(mCustomView);
            mCustomViewCallback = callback;
            wb.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        public void onHideCustomView() {
            wb.setVisibility(View.VISIBLE);
            //记录当前操作的控件
            mWebView = wb;
            mHomeFrameLayout = mFrameLayout;
            mHomeharvesShortTitle = harvesShortTitle;
            mHomeharvestShortTitleRl = harvestShortTitleRl;

            if (mCustomView == null) {
                return;
            }
            mCustomView.setVisibility(View.GONE);
            mHomeFrameLayout.removeView(mCustomView);
            mCustomViewCallback.onCustomViewHidden();
            mCustomView = null;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            super.onHideCustomView();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                for (int i = 0; i < hashView.size(); i++) {
                    if (i != mHomeharvesShortTitle.getId()) {
                        hashView.get(i).setVisibility(View.GONE);
                    }
                }
                rltitle.setVisibility(View.GONE);
                mHomeharvesShortTitle.setVisibility(View.GONE);
                mHomeharvestShortTitleRl.setVisibility(View.GONE);
                vertical = 1;
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                for (int i = 0; i < hashView.size(); i++) {
                    if (i != mHomeharvesShortTitle.getId()) {
                        hashView.get(i).setVisibility(View.VISIBLE);
                    }
                }
                rltitle.setVisibility(View.VISIBLE);
                mHomeharvesShortTitle.setVisibility(View.VISIBLE);
                mHomeharvestShortTitleRl.setVisibility(View.VISIBLE);
                vertical = 2;
                break;
        }
    }

    public void getHttpList(){
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.INDEX_SHORT_VIDEOS);
        commonOkhttp.putParams("cc_id", "8");
        commonOkhttp.putParams("page", page+"");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.HarvestShortListEntity>(this) {
            @Override
            public void onSuccess(NetBean.HarvestShortListEntity data, int d) {
                super.onSuccess(data, d);
                initView(data);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }
        });
        commonOkhttp.Execute();
    }

    private void initView(final NetBean.HarvestShortListEntity data) {
        for (int i = 0; i < data.size(); i++) {
            View view = View.inflate(this, R.layout.item_harvest_short_wb, null);
            FrameLayout mFrameLayout = (FrameLayout) view.findViewById(R.id.video_fullView);
            View mView=  view.findViewById(R.id.harvest_short_view);
            TextView harvesTitleTv = (TextView) view.findViewById(R.id.harvest_short_title);//标题
            RelativeLayout harvestShorRl = (RelativeLayout) view.findViewById(R.id.harvest_short_title_rl);
            TagFlowLayout tagFlowLayout = (TagFlowLayout) view.findViewById(R.id.item_harvest_short_fishing_fl);//标签
            TextView itemHarvestShortContentTv = (TextView) view.findViewById(R.id.item_harvest_short_content_tv);//评论数
            final TextView itemHarvestShortFabulousTv = (TextView) view.findViewById(R.id.item_harvest_short_fabulous_tv);//点赞数
            final ImageView itemHarvestShortFabulousIv = (ImageView) view.findViewById(R.id.item_harvest_short_fabulous_iv);//点赞图标

//            initWebView(mWebView, mFrameLayout, harvesTitleTv, harvestShorRl);
            VideoEnabledWebView webView = (VideoEnabledWebView) view.findViewById(R.id.harvest_short_wv);
            FrameLayout videoLayout = (FrameLayout) view.findViewById(R.id.videoLayout);

            // 标题
            harvesTitleTv.setText(data.get(i).getTitle());

            // 评论数量
            itemHarvestShortContentTv.setText(data.get(i).getComments_num());
            final int finalI = i;
            view.findViewById(R.id.item_harvest_short_content_ll).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HarvestShortActivity.this, VideoDetailsActivity.class).putExtra("article_id", data.get(finalI).getArticle_id()));
                }
            });
            // 点赞数量
            itemHarvestShortFabulousTv.setText(data.get(i).getZan_num());
            view.findViewById(R.id.item_harvest_short_fabulous_ll).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dianzanRun(data.get(finalI).getArticle_id(),itemHarvestShortFabulousTv,itemHarvestShortFabulousIv);
                }
            });

            //实现点击视频跳转详情
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HarvestShortActivity.this, VideoDetailsActivity.class).putExtra("article_id", data.get(finalI).getArticle_id()));
                }
            });
            //实现点击标题跳转
            harvesTitleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HarvestShortActivity.this, VideoDetailsActivity.class).putExtra("article_id", data.get(finalI).getArticle_id()));
                }
            });
            //实现点击标签跳转
            tagFlowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HarvestShortActivity.this, VideoDetailsActivity.class).putExtra("article_id", data.get(finalI).getArticle_id()));
                }
            });


            // 标签
            final List<String> tagList = Arrays.asList(data.get(i).getCate_name().get(0));
            TagAdapter hotAdapter = new TagAdapter<String>(tagList) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(HarvestShortActivity.this).inflate(R.layout.flow_layout_tag_fishing_tv, parent, false);
                    tv.setText(tagList.get(position));
                    return tv;
                }
            };
            tagFlowLayout.setAdapter(hotAdapter);

//            webView.setWebChromeClient(new VideoEnabledWebChromeClient(this, webView, videoLayout,webView, mFrameLayout, harvesTitleTv, harvestShorRl));
            harvesTitleTv.setId(i);
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setPluginState(WebSettings.PluginState.ON);
            settings.setAllowFileAccess(true);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            settings.setDisplayZoomControls(false); //隐藏webview缩放按钮
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            webView.loadUrl(data.get(i).getContent());
            hashView.put(i, view);
            harvesShortLl.addView(view);
        }
    }

    /**
     * 点赞、取消点赞
     * @param article_id
     * @param itemHarvestShortFabulousTv
     * @param itemHarvestShortFabulousIv
     */
    private void dianzanRun(String article_id, final TextView itemHarvestShortFabulousTv, final ImageView itemHarvestShortFabulousIv) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.dianzanRun);
        commonOkhttp.putParams("object_type", "2");//点赞对象：1帖子 2文章  3钓场  4渔具店
        commonOkhttp.putParams("object_id", article_id);//点赞对象ID
        commonOkhttp.putCallback(new MyGenericsCallback<StateEntity>(this) {
            @Override
            public void onSuccess(StateEntity data, int d) {
                super.onSuccess(data, d);
                String zan = itemHarvestShortFabulousTv.getText().toString().trim();
                //当前点赞状态：1.已点赞；2.未点赞
                if (data.getIs_dianzan() == 1) {
                    itemHarvestShortFabulousIv.setImageResource(R.mipmap.yule_dz_selected);
                    itemHarvestShortFabulousTv.setText(Integer.parseInt(zan)+1+"");
                } else {
                    itemHarvestShortFabulousIv.setImageResource(R.mipmap.yule_dz_unselected);
                    itemHarvestShortFabulousTv.setText(Integer.parseInt(zan)-1+"");
                }
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (vertical == 1){
                ScreenManager.getInstance().removeActivity(HarvestShortActivity.this);
            }else{
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                for (int i = 0; i < hashView.size(); i++) {
                    if (i != mHomeharvesShortTitle.getId()) {
                        hashView.get(i).setVisibility(View.VISIBLE);
                    }
                }
                rltitle.setVisibility(View.VISIBLE);
                mHomeharvesShortTitle.setVisibility(View.VISIBLE);
                mHomeharvestShortTitleRl.setVisibility(View.VISIBLE);
                vertical = 2;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null)
            mWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null)
            mWebView.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null)
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return;
            }
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null)
            mWebView.destroy();
    }
}