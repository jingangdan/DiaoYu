package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.AttentionPlaceEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.project.dyuapp.myutils.HttpUrl.IMAGE_URL;

/**
 * Created by ${田亭亭} on 2017/11/25 0025.
 *
 * @description 个人中心-钓场适配器
 * @change
 */


public class CenterFishingPlaceAdapter extends MyCommonAdapter<AttentionPlaceEntity> {

    @Bind(R.id.item_center_place_tv_time)
    TextView tvTime;
    @Bind(R.id.item_center_place_tv_state)
    TextView tvState;
    @Bind(R.id.item_center_place_img_icon)
    ImageView imgIcon;
    @Bind(R.id.item_center_place_tv_name)
    TextView tvName;
    @Bind(R.id.item_center_place_tv_cost)
    TextView tvCost;
    @Bind(R.id.item_center_place_tv_address)
    TextView tvAddress;
    @Bind(R.id.item_center_place_tv_detailed_address)
    TextView tvDetailedAddress;
    @Bind(R.id.item_center_place_tv_distance)
    TextView tvDistance;
    @Bind(R.id.what_item_center_place_tv_time)
    TextView whatTvTime;
    private String whereFrom;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickview(View view, int position);
    }

    public CenterFishingPlaceAdapter(List<AttentionPlaceEntity> list, Context mContext, int resid, String whereFrom) {
        super(list, mContext, resid);
        this.whereFrom = whereFrom;
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, final int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        if (onItemClickListener != null) {
            tvState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClickview(tvState, position);
                }
            });
        }
        if (TextUtils.equals(whereFrom, "1")) {
            tvTime.setText(Utils.convertDate(list.get(position).getCreatetime(), "yyyy-MM-dd"));
        }else if (TextUtils.equals(whereFrom, "2")) {
            tvTime.setText(Utils.convertDate(list.get(position).getAddtime(), "yyyy-MM-dd"));
        }

        //钓场图片
        if (list.get(position).getG_image() != null) {
            GlideUtils.loadImageView(mContext, IMAGE_URL + list.get(position).getG_image(), imgIcon);
        }
        //标题
        if (list.get(position).getG_name() != null)
            tvName.setText(list.get(position).getG_name());
        //钓场类型
        if (list.get(position).getCatname() != null)
            tvAddress.setText(list.get(position).getCatname());
        //钓场详细地址
        String address ="";
        String grounds_address ="";
        if (list.get(position).getAddress() != null)
            tvDetailedAddress.setText(list.get(position).getAddress());
        //距离
        if ((Object) list.get(position).getJuli() != null)
            tvDistance.setText(list.get(position).getJuli() + "km");
        //收费
        if (list.get(position).getPay_type() != null)
            tvCost.setText(list.get(position).getPay_type());

        if (TextUtils.equals(whereFrom, "1")) {
            whatTvTime.setText("关注时间：");
            tvState.setText("取消关注");
        } else if (TextUtils.equals(whereFrom, "2")) {
            whatTvTime.setText("添加时间：");
            if ("1".equals(list.get(position).getStatus())){
                tvState.setText("审核中");//1待审核2已通过3拒绝
                tvState.setTextColor(mContext.getResources().getColor(R.color.c_269ceb));
            }else if ("2".equals(list.get(position).getStatus())){
                tvState.setText("已通过");//1待审核2已通过3拒绝
                tvState.setTextColor(mContext.getResources().getColor(R.color.c_999999));
            }else if ("3".equals(list.get(position).getStatus())){
                tvState.setText("未通过");//1待审核2已通过3拒绝
                tvState.setTextColor(mContext.getResources().getColor(R.color.c_e64969));
            }
        }
    }
}