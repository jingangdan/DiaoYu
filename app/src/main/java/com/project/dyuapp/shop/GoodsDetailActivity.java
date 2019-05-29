package com.project.dyuapp.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.myutils.HttpUrl;

import java.io.IOException;
import java.nio.charset.Charset;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static com.alibaba.fastjson.util.IOUtils.UTF8;

/**
 * Describe:
 * Created by jingang on 2019/5/22
 */
public class GoodsDetailActivity extends MyBaseActivity {
    @Bind(R.id.web_frame)
    FrameLayout webFrame;
    @Bind(R.id.linGoodsDetail)
    LinearLayout linGoodsDetail;
    @Bind(R.id.progressBarLoading)
    ProgressBar mLoadingProgress;

    @Bind(R.id.tvGoodsDetailPrice)
    TextView tvPrice;

    private WebView webView;
    private String goods_id = "", price = "";
    private String module = "";
    private String wcUrl = "";

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x02:
                    Bundle bundle = msg.getData();
                    String date = bundle.getString("msg");
                    if (!TextUtils.isEmpty(date)) {
                        GoodsDetailData data = GsonUtils.gsonIntance().gsonToBean(date, GoodsDetailData.class);
                        if (data.getCode() == 0) {
                            wcUrl = data.getMessage().get_$0().getWe_app_web_view_url();
//                            data.getMessage().get_$0().getWeibo_app_web_view_short_url();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);

        initTitle();
        initData();
    }

    public void initTitle() {
        setIvBack();
        setTvTitle("商品详情");
        goods_id = getIntent().getStringExtra("goods_id");
        module = getIntent().getStringExtra("module");
        price = getIntent().getStringExtra("price");
        tvPrice.setText("¥" + price);
        setWebView();
    }

    /**
     * 获取详情
     */
    private void initData() {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("module", module);
        formBody.add("goods_id", goods_id);
        Request request = new Request.Builder()//创建Request 对象。
                .url(HttpUrl.shop_goods_detail)
                .post(formBody.build())//传递请求体
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                String bodyString = buffer.clone().readString(charset);

                //传递的数据
                Bundle bundle = new Bundle();
                bundle.putString("msg", bodyString);
                //发送数据
                Message message = Message.obtain();
                message.setData(bundle);   //message.obj=bundle  传值也行
                message.what = 0x02;
                mHandler.sendMessage(message);

            }
        });
    }

    public void setWebView() {
        webView = new WebView(this);
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        //解决一些图片加载问题
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webFrame.addView(webView);
        webView.loadUrl(HttpUrl.shop_goods_detail_web + "goods_id=" + goods_id);

        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                //设置加载进度条
                view.setWebChromeClient(new WebChromeClientProgress());
                return true;
            }

        });
    }

    @OnClick({R.id.lin1, R.id.lin2, R.id.lin3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin1:
                break;
            case R.id.lin2:
                break;
            case R.id.lin3:
                if (TextUtils.isEmpty(wcUrl)) {
                    showMessage("无推广链接");
                    return;
                }
//                https://mobile.yangkeduo.com/duo_coupon_landing.html?goods_id=8732290862&pid=1763821_66044416&cpsSign=CC_190523_1763821_66044416_6f99fbe1e8bf62ea48d78afee7f8318f&duoduo_type=3&launch_wx=1
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse(wcUrl);
//                intent.setData(content_url);
//                startActivity(intent);

                startActivity(new Intent(this, WebActviity.class)
                        .putExtra("wcUrl", wcUrl));

                break;
        }
    }

    private class WebChromeClientProgress extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int progress) {
            if (mLoadingProgress != null) {
                mLoadingProgress.setProgress(progress);
                if (progress == 100) mLoadingProgress.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, progress);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一浏览页面
                return true;
            } else {
                finish();//关闭Activity
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
