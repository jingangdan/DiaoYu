package com.project.dyuapp.shop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.myutils.GlideUtils;

import java.text.DecimalFormat;

/**
 * Describe:
 * Created by jingang on 2019/5/21
 */
public class GoodsAdapter extends BaseAdapter<GoodsData.DataBean.GoodsListBean> {
    public GoodsAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_goods;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemHolder(ViewHolder view, int position) {
        TextView tvName, tvPrice, tvPriceOld, tvQuan;
        ImageView img = view.getView(R.id.ivItemGoods);
        tvName = view.getView(R.id.tvItemGoodsName);
        tvPrice = view.getView(R.id.tvItemGoodsPrice);
        tvPriceOld = view.getView(R.id.tvItemGoodsPriceOld);
        tvQuan = view.getView(R.id.tvItemGoodsQuan);
        tvPriceOld.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        GlideUtils.loadImageViewNoPlaceHolder(mContext, mDataList.get(position).getGoods_thumbnail_url(), img);
        tvName.setText(mDataList.get(position).getGoods_name());
        DecimalFormat df = new DecimalFormat("0.00");

        //我脑子好乱啊
        int quan = Integer.valueOf(mDataList.get(position).getCoupon_discount());
        int priceOld = Integer.valueOf(mDataList.get(position).getMin_group_price());

        tvPrice.setText("¥" + df.format((priceOld - quan) / 100));
        tvPriceOld.setText("¥" + df.format(priceOld / 100));
        tvQuan.setText(String.format("券 ¥" + df.format(quan / 100)));

    }
}
