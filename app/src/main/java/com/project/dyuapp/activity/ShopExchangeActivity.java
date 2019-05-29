package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.GoodsExchangeEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.ScreenManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shipeiyun
 * @description 个人中心-金币商城-商品兑换
 * @created at 2017/11/29 0029
 */
public class ShopExchangeActivity extends MyBaseActivity {

    @Bind(R.id.shop_exchange_iv_img)
    ImageView ivImg;//商品图片
    @Bind(R.id.shop_exchange_tv_name)
    TextView tvName;//商品名称
    @Bind(R.id.shop_exchange_tv_introduce)
    TextView tvIntroduce;//商品简介
    @Bind(R.id.shop_exchange_tv_gold)
    TextView tvGold;//单价
    @Bind(R.id.shop_exchange_tv_exchange)
    TextView shopExchangeTvExchange;

    private String goods_id = "";//商品id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_shop_exchange);
        ButterKnife.bind(this);
        //返回
        getIvBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(PublicStaticData.SHOP_EXCHANGE);
                ScreenManager.getInstance().removeActivity(ShopExchangeActivity.this);
            }
        });
        setTvTitle("商品兑换");
        goods_id = getIntent().getStringExtra("goods_id");
        okhttpGoodsExchange();
    }

    //兑换按钮
    @OnClick(R.id.shop_exchange_tv_exchange)
    public void onClick() {
        startActivity(new Intent(ShopExchangeActivity.this, ExchangeInfoActivity.class)
                .putExtra("goods_id", goods_id).putExtra("myMoney", getIntent().getStringExtra("myMoney")));
    }

    /**
     * 27个人中心-金币商城-兑换商品
     */
    private void okhttpGoodsExchange() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.goodsExchange);
        commonOkhttp.putParams("goods_id", goods_id);
        commonOkhttp.putCallback(new MyGenericsCallback<GoodsExchangeEntity>(ShopExchangeActivity.this) {
            @Override
            public void onSuccess(GoodsExchangeEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    String img = data.getPic_url();//商品图片
                    if (!TextUtils.isEmpty(img)) {
                        Glide.with(ShopExchangeActivity.this).load(HttpUrl.IMAGE_URL + img).into(ivImg);
                    } else {
                        ivImg.setImageResource(R.mipmap.mine_img);
                    }
                    String name = data.getGoods_name();//商品名称
                    if (!TextUtils.isEmpty(name)) {
                        tvName.setText(name);
                    }
                    String introduce = data.getContent();//商品简介
                    if (!TextUtils.isEmpty(introduce)) {
                        tvIntroduce.setText(introduce);
                    }
                    String price = data.getPrice();//单价
                    if (!TextUtils.isEmpty(price)) {
                        tvGold.setText(price);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ShopExchangeActivity.this, "数据解析失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commonOkhttp.Execute();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            setResult(PublicStaticData.SHOP_EXCHANGE);
            ScreenManager.getInstance().removeActivity(ShopExchangeActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
