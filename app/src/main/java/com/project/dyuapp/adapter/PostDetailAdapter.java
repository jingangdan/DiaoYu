package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.ContentBean;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.WindowUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author gengqiufang
 * @describtion 帖子详情
 * @created at 2017/12/11 0011
 */

public class PostDetailAdapter extends MyCommonAdapter<ContentBean> {

    @Bind(R.id.item_skill_details_tv)
    TextView itemSkillDetailsTv;
    @Bind(R.id.item_skill_details_iv)
    ImageView itemSkillDetailsIv;

    public PostDetailAdapter(List<ContentBean> list, Context mContext) {
        super(list, mContext, R.layout.item_skill_details);
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        // 判断当前数据是否为图片数据
        if (!TextUtils.isEmpty(list.get(position).getStr_imgs())) {
            if (TextUtils.isEmpty(list.get(position).getStr_content())){
                itemSkillDetailsTv.setVisibility(View.GONE);
            }else {
                itemSkillDetailsTv.setVisibility(View.VISIBLE);
            }
            itemSkillDetailsIv.setVisibility(View.VISIBLE);
            //使图片根据高度自适应宽度
            int screenWidth = WindowUtils.getScreenWidth(mContext); //获得屏幕宽度
            ViewGroup.LayoutParams lp = itemSkillDetailsIv.getLayoutParams(); //获取布局参数管理器
            lp.width = screenWidth; //设置宽度为获取屏幕宽度
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT; //设置宽度为自适应宽度

            itemSkillDetailsIv.setLayoutParams(lp);  //将布局参数设置到控件上
            GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL+list.get(position).getStr_imgs(),itemSkillDetailsIv);
        }
        if (!TextUtils.isEmpty(list.get(position).getStr_content())){
            if (TextUtils.isEmpty(list.get(position).getStr_imgs())){
                itemSkillDetailsIv.setVisibility(View.GONE);
            }else {
                itemSkillDetailsIv.setVisibility(View.VISIBLE);
            }
            itemSkillDetailsTv.setVisibility(View.VISIBLE);
            itemSkillDetailsTv.setText(list.get(position).getStr_content());
        }
    }
}
