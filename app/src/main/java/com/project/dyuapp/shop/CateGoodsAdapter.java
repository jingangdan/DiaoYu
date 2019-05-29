package com.project.dyuapp.shop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.myutils.GlideUtils;

import java.text.DecimalFormat;

/**
 * Describe:
 * Created by jingang on 2019/5/21
 */
public class CateGoodsAdapter extends BaseAdapter<GoodsData.DataBean.GoodsListBean> {
    private OnItemClickListener mOnItemClickListener;

    public CateGoodsAdapter(Context context) {
        super(context);
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_cate_goods;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemHolder(final ViewHolder view, int position) {
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = view.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(view.itemView, position); // 2
                }
            });
        }
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

        tvPrice.setText("券后价¥" + df.format((priceOld - quan) / 100));
        tvPriceOld.setText("¥" + df.format(priceOld / 100));
        tvQuan.setText(String.format("券 ¥" + df.format(quan / 100)));

    }
}
