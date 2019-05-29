package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.widget.EditText;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.SuccessBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myutils.ToastUtils;
import com.project.dyuapp.myviews.StarBarTouch;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 田亭亭
 * @description 渔具店-点评Activity
 * @created at 2017/12/11 0011
 * @change
 */
public class ReviewActivity extends MyBaseActivity {

    @Bind(R.id.review_edt_content)
    EditText edtContent;
    @Bind(R.id.review_star_bar_touch_count)
    StarBarTouch starBarTouchCount;
    private String objectType = "";
    private String objectId = "";
    private String content = "";
    private int cCodes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_review);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTvTitle("点评");
        setIvBack();
        starBarTouchCount.setIntegerMark(true);
        objectId = getIntent().getStringExtra("object_id");
        objectType = getIntent().getStringExtra("object_type");
    }

    /**
     * 评论接口
     */
    public void okHttpActionComments() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.actionComments);
        commonOkhttp.putParams("object_type", objectType);//1帖子 2视频  3钓场  4渔具店
        commonOkhttp.putParams("object_id", objectId);//点评对象ID
        commonOkhttp.putParams("content", content);
        commonOkhttp.putParams("c_codes", cCodes + "");
        commonOkhttp.putCallback(new MyGenericsCallback<SuccessBean>(this) {
            @Override
            public void onSuccess(SuccessBean data, int d) {
                super.onSuccess(data, d);
                if (data.getState() == 200) {
                    Intent it  = new Intent();
                    it.putExtra("lat",getIntent().getStringExtra("lat"));
                    it.putExtra("lon",getIntent().getStringExtra("lon"));
                    setResult(PublicStaticData.RESULT_CODE_RREVIEW,it);
                    ScreenManager.getInstance().removeActivity(ReviewActivity.this);
                }
            }
        });
        commonOkhttp.Execute();
    }


    @OnClick(R.id.review_tv_public)
    public void onViewClicked() {
        content = edtContent.getText().toString();
        cCodes = (int) starBarTouchCount.getStarMark();
        if (!TextUtils.isEmpty(content)) {
            okHttpActionComments();
        } else {
            ToastUtils.getInstance(ReviewActivity.this).showMessage("请输入评价");
        }
    }
}
