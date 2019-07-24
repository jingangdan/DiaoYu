package com.project.dyuapp.search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.shop.GoodsAdapter;
import com.project.dyuapp.shop.GoodsData;
import com.project.dyuapp.shop.GoodsDetailActivity;
import com.project.dyuapp.shop.GsonUtils;

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
 * Created by jingang on 2019/7/9
 */
public class GoodsFragment extends MyBaseFragment {
    @Bind(R.id.lRecyclerView)
    LRecyclerView lRecyclerView;

    private String keyWords = "", lat = "", lng = "";
    private String type = "";//搜索类型

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_goods, null);
        ButterKnife.bind(this, view);
        df = new DecimalFormat("0.00");
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getArguments() != null) {
                keyWords = getArguments().getString("keyWords");
                lat = getArguments().getString("lat");
                lng = getArguments().getString("lng");
                type = getArguments().getString("type");
                if (!TextUtils.isEmpty(keyWords)) {
                    setAdapter();
                    initData();
                }
            }
        }
    }

    /**
     * 设置适配器
     */
    public void setAdapter() {
        lRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        goodsAdapter = new GoodsAdapter(getActivity());
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
                startActivity(new Intent(getActivity(), GoodsDetailActivity.class)
                        .putExtra("goods_id", goodsAdapter.getDataList().get(position).getGoods_id())
                        .putExtra("price", price)
                        .putExtra("module", "cate")
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

    private void initData() {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("object_id", "6");
        formBody.add("keyWords", keyWords);
        formBody.add("page", page + "");//传递键值对参数
        formBody.add("pagesize", pagesize + "");
        Request request = new Request.Builder()//创建Request 对象。
                .url(HttpUrl.search_object_new)
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
                message.what = 0x01;
                mHandler.sendMessage(message);

            }
        });
    }

    /*综合*/
    private void okhttpSearch() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.search_object_new);
        commonOkhttp.putParams("object_id", "6");//分类id 1钓场、2渔具店、3视频、4帖子
        commonOkhttp.putParams("keyWords", keyWords);
        commonOkhttp.putParams("lat", lat);//纬度  钓场、渔具店需要
        commonOkhttp.putParams("lng", lng);//经度  钓场、渔具店需要
        commonOkhttp.putCallback(new MyGenericsCallback<String>(getActivity()) {
            @Override
            public void onSuccess(String data, int d) {
                super.onSuccess(data, d);
            }
        });
        commonOkhttp.putCallback(new MyGenericsCallback<GoodsData>(getActivity()) {
            @Override
            public void onSuccess(GoodsData data, int d) {
                super.onSuccess(data, d);
                Log.e("ssssss", "data = " + data.toString());
                if (data.getCode() == 0) {
                    Log.e("ssssss", "size = " + data.getData().getGoods_list().size());
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
        });
        commonOkhttp.Execute();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
