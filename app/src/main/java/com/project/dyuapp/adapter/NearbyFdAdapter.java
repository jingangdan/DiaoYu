package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.dyuapp.R;
import com.project.dyuapp.entity.NearByBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyDateUtils;

import java.util.List;

/**
 * @author litongtong
 * @created on 2017/11/28 13:44
 * @description 周边钓友适配器
 * @change ${}
 */

public class NearbyFdAdapter extends CommonAdapter<NearByBean>{
    /**
     * 构造函数
     *
     * @param context   上下文
     * @param data      显示的数据
     * @param layout_id listview使用的条目的布局文件id
     */
    public NearbyFdAdapter(Context context, List<NearByBean> data, int layout_id) {
        super(context, data, layout_id);
    }

    @Override
    public void convert(CommonViewHolder h, NearByBean item, final int position) {
        //周边钓友头像
        Glide.with(context).load(HttpUrl.IMAGE_URL+data.get(position).getMember_list_headpic())
                .placeholder(R.mipmap.mine_edit_head).error(R.mipmap.mine_edit_head).
                into((ImageView)h.getConvertView().findViewById(R.id.nearbyfd_iv_head));

       //钓友昵称
        ((TextView)h.getConvertView().findViewById(R.id.nearbyfd_tv_name)).setText(data.get(position).getMember_list_nickname());
        //级别
        if(!TextUtils.isEmpty(data.get(position).getLevel())){
            ((TextView)h.getConvertView().findViewById(R.id.nearbyfd_tv_levle)).setText("Lv."+data.get(position).getLevel());
        }
        //距离
        ((TextView)h.getConvertView().findViewById(R.id.nearbyfd_tv_distance)).setText(data.get(position).getJuli());
        //钓友注册时间
        ((TextView)h.getConvertView().findViewById(R.id.nearbyfd_tv_time)).setText(MyDateUtils.timeStampToData(data.get(position).getMember_list_addtime(),"yyyy-MM-dd"));
        //是否关注
        final ImageView ivState = (ImageView) h.getConvertView().findViewById(R.id.nearbyfd_iv_state);
        if(data.get(position).getGuanzhu()==0){//是否关注 0未关注，1关注 2互相关注
            ivState.setImageResource(R.mipmap.button_attention);
        }else if(data.get(position).getGuanzhu()==1){
            ivState.setImageResource(R.mipmap.button_cancel);
        }else if(data.get(position).getGuanzhu() == 2){
            ivState.setImageResource(R.mipmap.button_mutual);
        }

        ivState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAttentionCallBack.onAttentionClick(ivState,position);
            }
        });

    }

    private onAttentionCallBack onAttentionCallBack;

    public interface onAttentionCallBack{
      void onAttentionClick(View view, int position);
    }
    public void setOnAttentionCallBack(onAttentionCallBack onAttentionCallBack){
        this.onAttentionCallBack = onAttentionCallBack;
    }
}
