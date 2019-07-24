package com.project.dyuapp.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.myutils.HttpUrl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
 * Describe:测试一把
 * Created by jingang on 2019/5/20
 */
public class CateActivity extends MyBaseActivity {
    @Bind(R.id.rv_cate)
    RecyclerView rvCate;
    @Bind(R.id.tvCateName)
    TextView tvCateName;
    @Bind(R.id.rv_cate_child)
    RecyclerView rv_cate_child;
    @Bind(R.id.etCate)
    EditText etCate;
    @Bind(R.id.rv_cate_recommend)
    RecyclerView rvCateRecommend;

    private GridLayoutManager mManager;
    private LinearLayoutManager mManager2;

    private CateAdapter cateAdapter;
    private CateChildAdapter cateChildAdapter;
    private CateGoodsAdapter cateGoodsAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private List<CateData> cateList = new ArrayList<>();
    private List<CateData.ChildBean> cateChildList = new ArrayList<>();
    private String price = "";
    DecimalFormat df;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x03:
                    Bundle bundle = msg.getData();
                    String date = bundle.getString("msg");
                    Log.e("ssssss", "date=" + date);
                    if (!TextUtils.isEmpty(date)) {
                        GoodsData data = GsonUtils.gsonIntance().gsonToBean(date, GoodsData.class);
                        if (data.getCode() == 0) {
                            cateGoodsAdapter.clear();
                            cateGoodsAdapter.addAll(data.getData().getGoods_list());
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_cate);
        ButterKnife.bind(this);

        initTitle();
    }

    private void initTitle() {
        setIvBack();
        setTvTitle("商品分类");

        df = new DecimalFormat("0.00");
        setAdapter();
        initData();
        getRecommendData();
    }

    /**
     * 设置适配器
     */
    public void setAdapter() {
        mManager = new GridLayoutManager(this, 1);
        mManager2 = new LinearLayoutManager(this);
        mManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mManager.setOrientation(GridLayoutManager.HORIZONTAL);
        rvCateRecommend.setLayoutManager(mManager2);
        cateGoodsAdapter = new CateGoodsAdapter(this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(cateGoodsAdapter);
        rvCateRecommend.setAdapter(cateGoodsAdapter);

        rvCate.setLayoutManager(new LinearLayoutManager(this));
        rv_cate_child.setLayoutManager(new GridLayoutManager(this, 3));
        cateAdapter = new CateAdapter(this, cateList);
        rvCate.setAdapter(cateAdapter);

        cateChildAdapter = new CateChildAdapter(this, cateChildList);
        rv_cate_child.setAdapter(cateChildAdapter);

        cateGoodsAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (cateGoodsAdapter.getDataList().get(position).isHas_coupon()) {
                    price = df.format(((Integer.valueOf(cateGoodsAdapter.getDataList().get(position).getMin_group_price())
                            -
                            Integer.valueOf(cateGoodsAdapter.getDataList().get(position).getCoupon_discount())))
                            / 100);
                } else {
                    price = df.format(Integer.valueOf(cateGoodsAdapter.getDataList().get(position).getMin_group_price()) / 100);
                }

                startActivity(new Intent(CateActivity.this, GoodsDetailActivity.class)
                        .putExtra("goods_id", cateGoodsAdapter.getDataList().get(position).getGoods_id())
                        .putExtra("price", price)
                        .putExtra("module", "recommend")
                );
            }
        });

        cateAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                tvCateName.setText(cateList.get(position).getCate_name());
                cateAdapter.changeSelected(position);
                cateChildList.clear();
                cateChildList.addAll(cateList.get(position).getChild());
                cateChildAdapter.notifyDataSetChanged();
            }
        });

        cateChildAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(CateActivity.this, GoodsActivity.class)
                        .putExtra("a", "cate_goods")
                        .putExtra("cate_id", cateChildList.get(position).getId())
                        .putExtra("keyword", "")
                        .putExtra("tag", 3)
                );
            }
        });
    }

    /**
     * 获取数据
     */
    public void initData() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.shop_cate_list);
        commonOkhttp.putCallback(new MyGenericsCallback<CateDataEntity>(this) {
            @Override
            public void onSuccess(CateDataEntity data, int d) {
                super.onSuccess(data, d);
                cateList.clear();
                cateList.addAll(data);
                cateAdapter.notifyDataSetChanged();
                tvCateName.setText(cateList.get(0).getCate_name());

                cateChildList.clear();
                cateChildList.addAll(data.get(0).getChild());
                cateChildAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 获取推荐数据
     */
    public void getRecommendData() {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("a", "recommend");
        formBody.add("page", "1");//传递键值对参数
        formBody.add("pagesize", "9");
        Request request = new Request.Builder()//创建Request 对象。
                .url(HttpUrl.shop_goods_list)
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
                message.what = 0x03;
                mHandler.sendMessage(message);

            }
        });
    }

    @OnClick({R.id.ivCateSearch, R.id.relCate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCateSearch:
                //搜索
                if (TextUtils.isEmpty(etCate.getText().toString().trim())) {
                    showMessage("请输入商品名进行搜索");
                    return;
                }
                startActivity(new Intent(this, GoodsActivity.class)
                        .putExtra("a", "search_goods")
                        .putExtra("cate_id", "")
                        .putExtra("keyword", etCate.getText().toString().trim())
                        .putExtra("tag", 1)
                );
                etCate.setText("");
                break;
            case R.id.relCate:
                //精品推荐
                startActivity(new Intent(this, GoodsActivity.class)
                        .putExtra("a", "recommend")
                        .putExtra("cate_id", "")
                        .putExtra("keyword", "")
                        .putExtra("tag", 2)
                );
                break;
            default:
                break;
        }
    }
}
