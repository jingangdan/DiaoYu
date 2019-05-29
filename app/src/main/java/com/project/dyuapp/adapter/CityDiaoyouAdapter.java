package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.project.dyuapp.R;
import com.project.dyuapp.entity.FishingFriendEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyDateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author ${shipeiyun}
 * @created on 2017/12/4 0004
 * @description 同城-同城钓友适配
 * @change ${}
 */
public class CityDiaoyouAdapter extends CommonAdapter<FishingFriendEntity> {
    @Bind(R.id.item_city_diaoyou_psiv)
    PorterShapeImageView itemCityDiaoyouPsiv;//头像
    @Bind(R.id.item_city_diaoyou_tv_name)
    TextView itemCityDiaoyouTvName;//昵称
    @Bind(R.id.item_city_diaoyou_tv_grade)
    TextView itemCityDiaoyouTvGrade;//等级
    @Bind(R.id.item_city_diaoyou_tv_site)
    TextView itemCityDiaoyouTvSite;//距离
    @Bind(R.id.item_city_diaoyou_tv_time)
    TextView itemCityDiaoyouTvTime;//时间
    @Bind(R.id.item_city_diaoyou_iv)
    ImageView itemCityDiaoyouIv;//状态

    private OnItemClickListener onItemClickListener;
    private int type;//按钮类型  关注、取消、互关

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
    public CityDiaoyouAdapter(Context context, List<FishingFriendEntity> data, int layout_id) {
        super(context, data, R.layout.item_city_diaoyou);
    }

    @Override
    public void convert(CommonViewHolder h, FishingFriendEntity item, final int position) {
        ButterKnife.bind(this, h.getConvertView());
        //按钮状态
        type = item.getGuanzhu();
        if (type == 1){
            itemCityDiaoyouIv.setImageResource(R.mipmap.button_cancel);
        } else if (type == 0){
            itemCityDiaoyouIv.setImageResource(R.mipmap.button_attention);
        }

        String img = item.getMember_list_headpic();//头像
        if (!TextUtils.isEmpty(img)){
            Glide.with(context).load(HttpUrl.IMAGE_URL+img).into(itemCityDiaoyouPsiv);
        } else {
            itemCityDiaoyouPsiv.setImageResource(R.mipmap.mine_edit_head);
        }
        String name = item.getMember_list_nickname();//昵称
        if (!TextUtils.isEmpty(name)){
            itemCityDiaoyouTvName.setText(name);
        }
        String level = item.getLevel();//等级
        if (!TextUtils.isEmpty(level)){
            itemCityDiaoyouTvGrade.setText("Lv."+level);
        }else {
            itemCityDiaoyouTvGrade.setText("Lv.0");
        }
        String site = item.getJuli();//距离
        if (!TextUtils.isEmpty(site)){
            itemCityDiaoyouTvSite.setText(site);
        }
        String time = item.getMember_list_addtime();//时间
        if (!TextUtils.isEmpty(time)){
            itemCityDiaoyouTvTime.setText(MyDateUtils.timeStampToData(time,"yyyy-MM-dd"));
        }

        //按钮点击事件
        if (onItemClickListener != null){
            itemCityDiaoyouIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClickview(itemCityDiaoyouIv,position);
                }
            });
        }
    }
}
