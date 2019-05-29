package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.dyuapp.R;
import com.project.dyuapp.callback.DraftsCallBack;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.DraftBoxBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyDateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author gengqiufang
 * @describtion 草稿箱
 * @created at 2017/11/30 0030
 */

public class DraftsAdapter extends MyCommonAdapter<DraftBoxBean> {
    @Bind(R.id.item_drafts_tv_type)
    TextView tvType;
    @Bind(R.id.item_drafts_tv_title)
    TextView tvTitle;
    @Bind(R.id.item_drafts_iv_pic)
    ImageView ivPic;
    @Bind(R.id.item_drafts_tv_content)
    TextView tvContent;
    @Bind(R.id.item_drafts_tv_time)
    TextView tvTime;
    @Bind(R.id.item_drafts_tv_del)
    TextView tvDel;
    @Bind(R.id.item_drafts_tv_repeat)
    TextView tvRepeat;
    @Bind(R.id.item_drafts_tv_change)
    TextView tvChange;
    private DraftsCallBack callBack;

    public DraftsAdapter(List<DraftBoxBean> list, Context mContext, DraftsCallBack callBack) {
        super(list, mContext, R.layout.item_drafts);
        this.callBack = callBack;
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, final int position) {
        ButterKnife.bind(this,commentViewHolder.getConverView());
        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClickDel(position);
            }
        });
        tvRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClickRepeat(position);
            }
        });
        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClickChange(position);
            }
        });

        //帖子类型
        if(!TextUtils.isEmpty(list.get(position).getName())){
            tvType.setText("#"+list.get(position).getName()+"#");
        }
        //帖子标题
        tvTitle.setText(list.get(position).getTitle());
        //帖子图片
        Glide.with(mContext).load(HttpUrl.IMAGE_URL+list.get(position).getThumb_img()).error(R.mipmap.mine_img).placeholder(R.mipmap.mine_img).into(ivPic);
        //帖子内容
        tvContent.setText(list.get(position).getContent());
        //时间
        tvTime.setText(MyDateUtils.timeStampToData(list.get(position).getAddtime(),"yyyy-MM-dd"));
    }
}
