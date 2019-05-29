package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.project.dyuapp.R;
import com.project.dyuapp.entity.FansEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author ${shipeiyun}
 * @created on 2017/11/27 0027
 * @description 我的-关注、粉丝 适配器
 * @change ${}
 */
public class AttentionFansAdapter extends CommonAdapter<FansEntity> {
    @Bind(R.id.item_iv_head)
    PorterShapeImageView itemIvHead;
    @Bind(R.id.item_tv_name)
    TextView itemTvName;
    @Bind(R.id.item_tv_grade)
    TextView itemTvGrade;
    @Bind(R.id.item_tv_post)
    TextView itemTvPost;
    @Bind(R.id.item_tv_attention)
    TextView itemTvAttention;
    @Bind(R.id.item_tv_fans)
    TextView itemTvFans;
    @Bind(R.id.item_iv)
    ImageView itemIv;

    private OnItemClickListener onItemClickListener;
    private int ishuguan;//是否互相关注  1 是  2否
    private String type;//1 关注  2粉丝

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickview(View view, int position);
    }

    /**
     * 构造函数
     *
     * @param context   上下文
     * @param data      显示的数据
     * @param layout_id listview使用的条目的布局文件id
     */
    public AttentionFansAdapter(Context context, List<FansEntity> data, int layout_id,String type) {
        super(context, data, R.layout.item_attention_fans);
        this.type = type;
    }

    @Override
    public void convert(CommonViewHolder h, FansEntity item, final int position) {
        ButterKnife.bind(this, h.getConvertView());
        //按钮样式切换
        ishuguan = item.getIs_hxguanzhu();
        if (type.equals("1")){
            if (ishuguan == 1) {
                itemIv.setImageResource(R.mipmap.button_mutual);
            } else {
                itemIv.setImageResource(R.mipmap.button_cancel);
            }
        }else if (type.equals("2")) {
            if (ishuguan == 1) {
                itemIv.setImageResource(R.mipmap.button_mutual);
            } else {
                itemIv.setImageResource(R.mipmap.button_attention);
            }
        }
        String img = item.getMember_list_headpic();//头像
        if (!TextUtils.isEmpty(img)) {
            GlideUtils.loadImageViewHead(context,HttpUrl.IMAGE_URL + img,itemIvHead);
        } else {
            itemIvHead.setImageResource(R.mipmap.mine_edit_head);
        }
        String name = item.getMember_list_nickname();//昵称
        if (!TextUtils.isEmpty(name)) {
            itemTvName.setText(name);
        }
        String scores = item.getLevel();//等级
        if (!TextUtils.isEmpty(scores)) {
            itemTvGrade.setText("Lv." + scores);
        }
        String tie = item.getTienum();//帖子
        if (!TextUtils.isEmpty(tie)) {
            itemTvPost.setText(tie);
        }
        String guan = item.getGuannum();//关注
        if (!TextUtils.isEmpty(guan)) {
            itemTvAttention.setText(guan);
        }
        String fen = item.getFennum();//粉丝
        if (!TextUtils.isEmpty(fen)) {
            itemTvFans.setText(fen);
        }

        //按钮点击事件
        if (onItemClickListener != null){
            itemIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClickview(itemIv,position);
                }
            });
        }
    }
}
