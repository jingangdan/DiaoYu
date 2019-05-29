package com.project.dyuapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.LetterHistoryEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyDateUtils;
import com.project.dyuapp.myutils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author gengqiufang
 * @describtion 发私信适配器
 * @created at 2017/12/7 0007
 */

public class SendMessageAdapter extends MyCommonAdapter<LetterHistoryEntity> {
    @Bind(R.id.item_send_message_tv_time)
    TextView tvTime;

    @Bind(R.id.item_send_message_iv_my_head)
    PorterShapeImageView ivMyHead;
    @Bind(R.id.item_send_message_tv_my_name)
    TextView tvMyName;
    @Bind(R.id.item_send_message_tv_my_message)
    TextView tvMyMessage;
    @Bind(R.id.item_send_message_ll_my)
    RelativeLayout llMy;

    @Bind(R.id.item_send_message_iv_other_head)
    PorterShapeImageView ivOtherHead;
    @Bind(R.id.item_other_message_tv_other_name)
    TextView tvOtherName;
    @Bind(R.id.item_send_message_tv_other_message)
    TextView tvOtherMessage;
    @Bind(R.id.item_send_message_ll_other)
    LinearLayout llOther;

    private String toMemberId="";

    public SendMessageAdapter(List<LetterHistoryEntity> list, Context mContext, String toMemberId) {
        super(list, mContext, R.layout.item_send_message);
        this.toMemberId = toMemberId;
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        LetterHistoryEntity item=list.get(position);
        if (item.getFrom_member__id().equals(toMemberId)) {
            //其他人
            llMy.setVisibility(View.GONE);
            llOther.setVisibility(View.VISIBLE);
            GlideUtils.loadImageViewHead(mContext, HttpUrl.IMAGE_URL+item.getForm_headpic(),ivOtherHead);
            tvOtherName.setText(item.getForm_nickname());
            tvOtherMessage.setText(item.getDesc());
        } else {
            llMy.setVisibility(View.VISIBLE);
            llOther.setVisibility(View.GONE);
            GlideUtils.loadImageViewHead(mContext, HttpUrl.IMAGE_URL+item.getForm_headpic(),ivMyHead);
            tvMyName.setText(item.getForm_nickname());
            tvMyMessage.setText(item.getDesc());
        }
        if (item.getIs_new().equals("1")){
            tvTime.setVisibility(View.VISIBLE);
            String currentData=MyDateUtils.getCurrentTime("yyyy-MM-dd");
            String createData=Utils.convertDate(item.getCreatetime(),"yyyy-MM-dd");
            if (currentData.equals(createData)){
                tvTime.setText(Utils.convertDate(item.getCreatetime(),"HH:mm"));
            }else {
                tvTime.setText(createData);
            }
        }else {
            tvTime.setVisibility(View.GONE);
        }


    }
}
