package com.project.dyuapp.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.dyuapp.R;
import com.project.dyuapp.callback.PostEditCallBack;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.ContentBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyLogUtils;
import com.project.dyuapp.myutils.WindowUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author gengqiufang
 * @describtion
 * @created at 2017/11/28 0028
 */

public class PublishPostAdapter extends MyCommonAdapter<ContentBean> {

    @Bind(R.id.item_publish_post_edt_content)
    EditText edtContent;
    @Bind(R.id.item_publish_post_tv_del_content)
    TextView tvDelContent;
    @Bind(R.id.item_publish_post_ll_content)
    LinearLayout llContent;
    @Bind(R.id.item_publish_post_iv_pic)
    ImageView ivPic;
    @Bind(R.id.item_publish_post_tv_del_pic)
    TextView tvDelPic;
    @Bind(R.id.item_publish_post_fl_pic)
    FrameLayout flPic;
    private PostEditCallBack callBack;
    private int index = -1;//光标所在位置
    private boolean firstIsCanDel;//第一个是否可删除 渔获为true   其他为false
    private Activity activity;

    private int focue;

    public PublishPostAdapter(List<ContentBean> list, Context mContext, PostEditCallBack callBack) {
        super(list, mContext, R.layout.item_publish_post);
        this.callBack = callBack;
    }

    public PublishPostAdapter(List<ContentBean> list, Context mContext, PostEditCallBack callBack, boolean firstIsCanDel) {
        super(list, mContext, R.layout.item_publish_post);
        this.callBack = callBack;
        this.firstIsCanDel = firstIsCanDel;
    }

    public PublishPostAdapter(List<ContentBean> list, Context mContext, PostEditCallBack callBack, Activity activity) {
        super(list, mContext, R.layout.item_publish_post);
        this.callBack = callBack;
        this.activity = activity;
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, final int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        if ((!firstIsCanDel) && position == 0) {
            tvDelContent.setVisibility(View.GONE);
        } else {
            tvDelContent.setVisibility(View.VISIBLE);
        }
        if (list.get(position).isPic()) {
            //添加的是图
            flPic.setVisibility(View.VISIBLE);
            llContent.setVisibility(View.GONE);

            //使图片根据高度自适应宽度
            int screenWidth = WindowUtils.getScreenWidth(mContext); //获得屏幕宽度
            ViewGroup.LayoutParams lp = ivPic.getLayoutParams(); //获取布局参数管理器
            lp.width = screenWidth; //设置宽度为获取屏幕宽度
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT; //设置宽度为自适应宽度
            ivPic.setLayoutParams(lp);  //将布局参数设置到控件上

            if (list.get(position).getStr_imgs().contains("upload")) {
                Glide.with(mContext).load(HttpUrl.IMAGE_URL + list.get(position).getStr_imgs()).into(ivPic);
            } else {
                Glide.with(mContext).load(list.get(position).getStr_imgs()).into(ivPic);
            }
            tvDelPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.delPic(position);
                }
            });
        } else {
            //添加的是文字
            flPic.setVisibility(View.GONE);
            llContent.setVisibility(View.VISIBLE);
            tvDelContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.delContent(position);
                }
            });
            edtContent.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        index = position;//点击edt
                        MyLogUtils.logD(MyLogUtils.TAG, "==setOnTouchListener=up=" + "position:" + position + "index:" + index);
                    }
                    return false;
                }
            });
            if (edtContent.isFocused()) {
                if (index==position&&getSoftButtonsBarHeight(activity)>0){
                    MyLogUtils.logD(MyLogUtils.TAG, "==isFocused()=1=" + "position:" + position + "index:" + index);
                }else {
                    index = -1;//焦点没在edt
                    MyLogUtils.logD(MyLogUtils.TAG, "==isFocused()=2=" + "position:" + position + "index:" + index);
                }
            }
            edtContent.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable editable) {
                }

                public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence text, int start, int before, int count) {
                    MyLogUtils.logD(MyLogUtils.TAG, "==onTextChanged==" + "position:" + position + "index:" + index + "text:" + text);
                    //如果该edittext有默认内容，还要在if那里进行过滤
                    if (index >= 0 && text.length() > 0 && index == position) {
                        list.get(index).setStr_content(text.toString());
                        MyLogUtils.logD(MyLogUtils.TAG, "==onTextChanged=9999999999999=" + "position:" + position + "index:" + index + "text:" + text);
                    }
                }
            });
            edtContent.setText(list.get(position).getStr_content());
            edtContent.clearFocus();
            if (index != -1 && index == position) {
                edtContent.requestFocus();//保证光标还在上次编辑处
                MyLogUtils.logD(MyLogUtils.TAG, "==requestFocus==" + "position:" + position + "index:" + index);
            }
        }
    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        context.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }
}
