package com.project.dyuapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.project.dyuapp.R;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.MyLetterBean;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyDateUtils;
import com.project.dyuapp.myutils.ToastUtils;

import java.util.List;

/**
 * @author litongtong
 * @created on 2017/11/28 11:03
 * @description 我的私信列表适配器
 * @change ${}
 */

public class MyPLetterAdapter extends CommonAdapter<MyLetterBean> {
    /**
     * 构造函数
     *
     * @param context   上下文
     * @param data      显示的数据
     * @param layout_id listview使用的条目的布局文件id
     */
    public MyPLetterAdapter(Context context, List<MyLetterBean> data, int layout_id) {
        super(context, data, layout_id);
    }

    @Override
    public void convert(CommonViewHolder h, MyLetterBean item, final int position) {
        //头像
        Glide.with(context).load(HttpUrl.IMAGE_URL + data.get(position).getMember_list_headpic())
                .placeholder(R.mipmap.mine_edit_head).error(R.mipmap.mine_edit_head).into((PorterShapeImageView) h.getConvertView().findViewById(R.id.myletter_iv_head));

        //昵称
        ((TextView) h.getConvertView().findViewById(R.id.myletter_tv_name)).setText(data.get(position).getMember_list_nickname());
        //时间
        ((TextView) h.getConvertView().findViewById(R.id.myletter_tv_time)).setText(MyDateUtils.timeStampToData(data.get(position).getCreatetime(), "yyyy-MM-dd"));
        //内容
        ((TextView) h.getConvertView().findViewById(R.id.myletter_tv_content)).setText(data.get(position).getDesc());
        //删除
        ((TextView) h.getConvertView().findViewById(R.id.myletter_tv_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(data, position, context);
            }
        });
    }

    /**
     * 删除私信
     *
     * @param list
     * @param position
     * @param context
     */
    private void delete(final List<MyLetterBean> list, final int position, final Context context) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.letterDel);
        commonOkhttp.putParams("message_id", list.get(position).getMessage_id());
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.EmptytEntity>((Activity) context) {
            @Override
            public void onSuccess(NetBean.EmptytEntity data, int d) {
                super.onSuccess(data, d);
                list.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                ToastUtils.getInstance(context).showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }

}
