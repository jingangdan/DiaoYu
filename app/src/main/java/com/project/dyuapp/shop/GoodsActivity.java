package com.project.dyuapp.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.myutils.HttpUrl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
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
 * Created by jingang on 2019/5/21
 */
public class GoodsActivity extends MyBaseActivity {
    @Bind(R.id.lRecyclerView)
    LRecyclerView lRecyclerView;

    //接受页面传值
    private int tag = 0;
    private String a = "", cate_id = "", keyword = "";
    private String module = "";

    private int page = 1, pagesize = 10;
    private GoodsAdapter goodsAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    DecimalFormat df;
    private String price = "";
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    Bundle bundle = msg.getData();
                    String date = bundle.getString("msg");
                    Log.e("ssssss", "date=" + date);
                    if (!TextUtils.isEmpty(date)) {
                        GoodsData data = GsonUtils.gsonIntance().gsonToBean(date, GoodsData.class);
                        if (data.getCode() == 0) {
                            lRecyclerView.refreshComplete(data.getData().getGoods_list().size());
                            if (page == 1) {
                                goodsAdapter.clear();
                            }
                            goodsAdapter.addAll(data.getData().getGoods_list());

                            if (data.getData().getGoods_list().size() < 10) {
                                lRecyclerView.setNoMore(true);
                            }
                        }
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_goods);
        ButterKnife.bind(this);

        initTitle();
    }

    public void initTitle() {
        setIvBack();
        setTvTitle("商品列表");

        setIntent();
        df = new DecimalFormat("0.00");

        setAdapter();
        initData();
    }

    /**
     * 获取页面传值
     */
    public void setIntent() {
        tag = getIntent().getIntExtra("tag", 1);
        a = getIntent().getStringExtra("a");
        cate_id = getIntent().getStringExtra("cate_id");
        keyword = getIntent().getStringExtra("keyword");
    }

    /**
     * 设置适配器
     */
    public void setAdapter() {
        lRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        goodsAdapter = new GoodsAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(goodsAdapter);
        lRecyclerView.setAdapter(lRecyclerViewAdapter);

        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (goodsAdapter.getDataList().get(position).isHas_coupon()) {
                    price = df.format(((Integer.valueOf(goodsAdapter.getDataList().get(position).getMin_group_price())
                            -
                            Integer.valueOf(goodsAdapter.getDataList().get(position).getCoupon_discount())))
                            / 100);
                } else {
                    price = df.format(Integer.valueOf(goodsAdapter.getDataList().get(position).getMin_group_price()) / 100);
                }

                if (tag == 1 || tag == 3) {
                    module = "cate";
                } else if (tag == 2) {
                    module = "recommend";
                }

                startActivity(new Intent(GoodsActivity.this, GoodsDetailActivity.class)
                        .putExtra("goods_id", goodsAdapter.getDataList().get(position).getGoods_id())
                        .putExtra("price", price)
                        .putExtra("module", module)
                );
            }
        });

        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                initData();
            }
        });

        lRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                initData();
            }
        });
    }

    /**
     * 获取数据
     */
    private void initData() {
        //url = HttpUrl.shop_goods_list+"a="+a;
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("a", a);
        if (tag == 1) {
            formBody.add("keyword", keyword);
        } else if (tag == 3) {
            formBody.add("cate_id", cate_id);
        }
        formBody.add("page", page + "");//传递键值对参数
        formBody.add("pagesize", pagesize + "");
        Request request = new Request.Builder()//创建Request 对象。
                .url(HttpUrl.shop_goods_list)
                .post(formBody.build())//传递请求体
                .build();
        Log.e("ssssss", "url = " + HttpUrl.shop_goods_list + a);
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
                message.what = 0x01;
                mHandler.sendMessage(message);

            }
        });
    }


}
