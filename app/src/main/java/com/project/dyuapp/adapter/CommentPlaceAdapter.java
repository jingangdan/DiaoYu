package com.project.dyuapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.dyuapp.R;
import com.project.dyuapp.callback.PostEditCallBack;
import com.project.dyuapp.entity.ContentBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyLogUtils;
import com.project.dyuapp.myutils.WindowUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author jingang
 * @describtion 钓场-点评
 * @created at 2017/11/28 0028
 */

public class CommentPlaceAdapter extends BaseAdapter {

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

    private List<ContentBean> list;
    private Context mContext;

    public CommentPlaceAdapter(List<ContentBean> list, Context mContext, PostEditCallBack callBack) {
        this.list = list;
        this.mContext = mContext;
        this.callBack = callBack;
    }

    public CommentPlaceAdapter(List<ContentBean> list, Context mContext, PostEditCallBack callBack, boolean firstIsCanDel) {
        this.list = list;
        this.mContext = mContext;
        this.callBack = callBack;
        this.firstIsCanDel = firstIsCanDel;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_publish_post, null);
        ButterKnife.bind(this, view);
        llContent.setVisibility(View.GONE);

        if ((!firstIsCanDel) && position == 0) {
            tvDelContent.setVisibility(View.GONE);
        } else {
            tvDelContent.setVisibility(View.VISIBLE);
        }
        if (list.get(position).isPic()) {
            //添加的是图
            isPic(position);
        } else {
            //添加的是文字
            isText(position);
        }
        return view;
    }

    private void isPic(final int position) {
        flPic.setVisibility(View.VISIBLE);
        //llContent.setVisibility(View.GONE);
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
    }

    private void isText(final int position) {
        flPic.setVisibility(View.GONE);
       // llContent.setVisibility(View.VISIBLE);
        tvDelContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.delContent(position);
            }
        });
        edtContent.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence text, int start, int before, int count) {
                list.get(position).setStr_content(text.toString());
                MyLogUtils.logD(MyLogUtils.TAG, "==onTextChanged==" + "text:" + text);
            }
        });
        edtContent.setText(list.get(position).getStr_content());
    }
}
