package com.project.dyuapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.StatusEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.RegexUtils;
import com.project.dyuapp.myutils.ScreenManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shipeiyun
 * @description 个人中心-设置-意见反馈
 * @created at 2017/11/28 0028
 */
public class FeedbackActivity extends MyBaseActivity {

    @Bind(R.id.feedback_edt_edit)
    EditText feedbackEdtEdit;//意见编辑
    @Bind(R.id.feedback_edt_contact)
    EditText feedbackEdtContact;//联系方式

    private String phone;//联系方式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("意见反馈");
    }

    @OnClick(R.id.feedback_tv_submit)
    public void onClick(){
        phone  = feedbackEdtContact.getText().toString();
        if (!phone.equals("")) {
            if (!RegexUtils.isMobilePhoneNumber(phone)) {
                showMessage("请输入正确手机号");
                return;
            }
        }
        okhttpIdeaBack();
    }

    /**
     * 16个人中心-设置-意见反馈
     */
    private void okhttpIdeaBack(){
        String content = feedbackEdtEdit.getText().toString();
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.ideaBack);
        if (!TextUtils.isEmpty(content)) {
            commonOkhttp.putParams("content", content);
        }
        commonOkhttp.putParams("telphone",phone);
        commonOkhttp.putCallback(new MyGenericsCallback<StatusEntity>(FeedbackActivity.this){
            @Override
            public void onSuccess(StatusEntity data, int d) {
                super.onSuccess(data, d);
                ScreenManager.getInstance().removeActivity(FeedbackActivity.this);
            }
        });
        commonOkhttp.Execute();
    }

}
