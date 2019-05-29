package com.project.dyuapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CommonAdapter;
import com.project.dyuapp.adapter.CommonViewHolder;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.GoldMallEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myviews.GridViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shipeiyun
 * @description 个人中心-金币商城
 * @created at 2017/11/28 0028
 */
public class GoldMallActivity extends MyBaseActivity {

    @Bind(R.id.gold_mall_tv_gold)
    TextView goldMallTvGold;//我的金币
    @Bind(R.id.gold_mall_gv)
    GridViewForScrollView goldMallGv;
    @Bind(R.id.gold_mall_sv)
    PullToRefreshScrollView goldMallSv;

    private List<GoldMallEntity.GoodsBean> gvList = new ArrayList<>();//列表集合
    private GoldMallAdapter mallAdapter;
    private String myMoney;//我的金币
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_gold_mall);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("金币商城");
        initView();
        page = 1;
        okhttpIndex();
    }

    private void initView() {
        goldMallGv.setFocusable(false);
        mallAdapter = new GoldMallAdapter(this, gvList, R.layout.item_gold_mall);
        goldMallGv.setAdapter(mallAdapter);
        goldMallGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivityForResult(new Intent(GoldMallActivity.this, ShopExchangeActivity.class)
                        .putExtra("goods_id", gvList.get(i).getGoods_id()).putExtra("myMoney", myMoney), PublicStaticData.GOLD_MALL);
            }
        });
        goldMallSv.setMode(PullToRefreshBase.Mode.BOTH);
        goldMallSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page = 1;
                okhttpIndex();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                okhttpIndex();
            }
        });
    }

    @OnClick({R.id.gold_mall_ll_record, R.id.gold_mall_ll_task})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gold_mall_ll_record:
                //兑换记录
                goToActivity(RecordActivity.class);
                break;
            case R.id.gold_mall_ll_task:
                //金币任务
                startActivity(new Intent(this, CommenWebviewActivity.class)
                        .putExtra("title", "金币任务")
                        .putExtra("url", HttpUrl.goldTask));
                break;
        }
    }

    /**
     * 25个人中心-金币商城
     */
    private void okhttpIndex() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.index);
        commonOkhttp.putParams("page", page+"");
        commonOkhttp.putParams("rows", "4");
        commonOkhttp.putCallback(new MyGenericsCallback<GoldMallEntity>(GoldMallActivity.this) {
            @Override
            public void onSuccess(GoldMallEntity data, int d) {
                super.onSuccess(data, d);
                if (data != null) {
                    myMoney = data.getMoney();//我的金币
                    goldMallTvGold.setText(myMoney);//我的金币

                    if (page == 1) {
                        gvList.clear();
                    }
                    if (data != null && data.getGoods().size() > 0) {
                        gvList.addAll(data.getGoods());
                        ++page;
                    } else {
                        if (page == 1) {
                            Toast.makeText(GoldMallActivity.this, getString(R.string.list_no_data), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GoldMallActivity.this, getString(R.string.list_bottom), Toast.LENGTH_SHORT).show();
                        }
                    }
                    mallAdapter.notifyDataSetChanged();
                    goldMallSv.onRefreshComplete();
                }
            }
        });
        commonOkhttp.Execute();
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        page = 1;
        okhttpIndex();
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PublicStaticData.GOLD_MALL){
            if (resultCode == PublicStaticData.SHOP_EXCHANGE) {
                page = 1;
                okhttpIndex();
            }
        }
    }

    /**
     * 列表适配
     */
    class GoldMallAdapter extends CommonAdapter<GoldMallEntity.GoodsBean> {

        @Bind(R.id.item_gold_mall_iv)
        ImageView itemGoldMallIv;//图片
        @Bind(R.id.item_gold_mall_tv_name)
        TextView itemGoldMallTvName;//商品名称
        @Bind(R.id.item_gold_mall_tv)
        TextView itemGoldMallTv;//兑换所需金币

        /**
         * 构造函数
         *
         * @param context   上下文
         * @param data      显示的数据
         * @param layout_id listview使用的条目的布局文件id
         */
        public GoldMallAdapter(Context context, List<GoldMallEntity.GoodsBean> data, int layout_id) {
            super(context, data, R.layout.item_gold_mall);
        }

        @Override
        public void convert(CommonViewHolder h, GoldMallEntity.GoodsBean item, int position) {
            ButterKnife.bind(this, h.getConvertView());
            String img = item.getPic_url();//图片
            if (!TextUtils.isEmpty(img)) {
                Glide.with(context).load(HttpUrl.IMAGE_URL + img).into(itemGoldMallIv);
            } else {
                itemGoldMallIv.setImageResource(R.mipmap.mine_img);
            }
            String name = item.getGoods_name();//商品名称
            if (!TextUtils.isEmpty(name)) {
                itemGoldMallTvName.setText(name);
            }
            String price = item.getPrice();//兑换所需金币
            if (!TextUtils.isEmpty(price)) {
                itemGoldMallTv.setText(price);
            }
        }
    }

}
