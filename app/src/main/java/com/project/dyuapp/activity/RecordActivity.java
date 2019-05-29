package com.project.dyuapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CommonAdapter;
import com.project.dyuapp.adapter.CommonViewHolder;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.ExchangeLogEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyDateUtils;
import com.project.dyuapp.myviews.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author shipeiyun
 * @description 个人中心-金币商城-兑换记录
 * @created at 2017/11/28 0028
 */
public class RecordActivity extends MyBaseActivity {

    @Bind(R.id.record_tv_gold)
    TextView recordTvGold;//已兑换金币
    @Bind(R.id.record_lvsv)
    ListViewForScrollView recordLvsv;

    private List<ExchangeLogEntity.GoodsBean> recordList = new ArrayList<>();//列表集合
    private RecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_record);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("兑换记录");
        initView();
        okhttpExchangeLog();
    }

    private void initView() {
        recordLvsv.setFocusable(false);
        adapter = new RecordAdapter(this, recordList, R.layout.item_record);
        recordLvsv.setAdapter(adapter);
        recordLvsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                goToActivity(ShopExchangeActivity.class);
            }
        });
    }

    /**
     * 26个人中心-金币商城-兑换记录
     */
    private void okhttpExchangeLog(){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.exchangeLog);
        commonOkhttp.putCallback(new MyGenericsCallback<ExchangeLogEntity>(RecordActivity.this){
            @Override
            public void onSuccess(ExchangeLogEntity data, int d) {
                super.onSuccess(data, d);
                if (data != null){
                    String gold = data.getTotal_money();//已兑换金币
                    if (!TextUtils.isEmpty(gold)) {
                        recordTvGold.setText(gold);
                    } else {
                        recordTvGold.setText("0");
                    }
                    if (data.getGoods() != null) {
                        recordList.addAll(data.getGoods());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 列表适配
     */
    class RecordAdapter extends CommonAdapter<ExchangeLogEntity.GoodsBean> {

        @Bind(R.id.item_record_tv_date)
        TextView itemRecordTvDate;//兑换时间
        @Bind(R.id.item_record_tv_state)
        TextView itemRecordTvState;//状态
        @Bind(R.id.item_record_iv)
        PorterShapeImageView itemRecordIv;//图片
        @Bind(R.id.item_record_tv_name)
        TextView itemRecordTvName;//商品名称
        @Bind(R.id.item_record_tv_gold)
        TextView itemRecordTvGold;//金币
        @Bind(R.id.item_record_tv_count)
        TextView itemRecordTvCount;//数量

        /**
         * 构造函数
         *
         * @param context   上下文
         * @param data      显示的数据
         * @param layout_id listview使用的条目的布局文件id
         */
        public RecordAdapter(Context context, List<ExchangeLogEntity.GoodsBean> data, int layout_id) {
            super(context, data, R.layout.item_record);
        }

        @Override
        public void convert(CommonViewHolder h, ExchangeLogEntity.GoodsBean item, int position) {
            ButterKnife.bind(this, h.getConvertView());
            String date = item.getAddtime();//兑换时间
            if (!TextUtils.isEmpty(date)){
                itemRecordTvDate.setText(MyDateUtils.timeStampToData(date,"yyyy-MM-dd"));
            }
            String img = item.getPic_url();//图片
            if (!TextUtils.isEmpty(img)){
                Glide.with(context).load(HttpUrl.IMAGE_URL+img).into(itemRecordIv);
            } else {
                itemRecordIv.setImageResource(R.mipmap.mine_img);
            }
            String name = item.getGoods_name();//商品名称
            if (!TextUtils.isEmpty(name)){
                itemRecordTvName.setText(name);
            }
            String gold = item.getPrice();//金币
            if (!TextUtils.isEmpty(gold)){
                itemRecordTvGold.setText(gold);
            }
            String num = item.getGoods_num();//数量
            if (!TextUtils.isEmpty(num)){
                itemRecordTvCount.setText(num);
            }
            String status = item.getOrder_status();//状态 1待审核 2审核通过 3审核不通过 4已发货 5订单完成
            if (status.equals("1")){
                itemRecordTvState.setText("待审核");
            } else if (status.equals("2")){
                itemRecordTvState.setText("审核通过");
            } else if (status.equals("3")){
                itemRecordTvState.setText("审核不通过");
            } else if (status.equals("4")){
                itemRecordTvState.setText("已发货");
            } else if (status.equals("5")){
                itemRecordTvState.setText("订单完成");
            }
        }
    }

}
