package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.ComUserEntity;
import com.project.dyuapp.entity.RegisterCodeEntity;
import com.project.dyuapp.entity.WeatherEntity;
import com.project.dyuapp.myutils.CommonUserDo;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.RegexUtils;
import com.project.dyuapp.myutils.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * @author 界面：gengqiufang
 * @describtion 忘记密码
 * @created at 2017/11/27 0027
 */
public class ForgetPswActivity extends MyBaseActivity {

    @Bind(R.id.edt_phone)
    EditText edtPhone;
    @Bind(R.id.edt_code)
    EditText edtCode;
    @Bind(R.id.tv_get_code)
    TextView tvGetCode;
    @Bind(R.id.edt_password)
    EditText edtPassword;
    @Bind(R.id.iv_password_show)
    ImageView ivPasswordShow;
    @Bind(R.id.forget_psw_tv_ok)
    TextView tvOk;

    private ComUserEntity comUserEntity=new ComUserEntity();//用户信息实体

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_forget_psw);
        ButterKnife.bind(this);
        initTitle();//初始化title
    }

    //初始化title
    private void initTitle() {
        getRlTitle().setBackgroundColor(getResources().getColor(R.color.c_ffffff));
        getIvBack().setImageResource(R.mipmap.nav_back_02);
        getIvBack().setBackgroundColor(getResources().getColor(R.color.c_ffffff));
        setIvBack();
        getTvTitle().setTextColor(getResources().getColor(R.color.c_333333));
        setTvTitle("重置密码");
    }

    @OnClick({R.id.tv_get_code, R.id.iv_password_show, R.id.forget_psw_tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                //获取验证码
                comUserEntity.setPhone(edtPhone.getText().toString().trim());
                if (TextUtils.isEmpty(comUserEntity.getPhone())){
                    showMessage("请输入手机号");
                    break;
                }
                if (!RegexUtils.isMobilePhoneNumber(comUserEntity.getPhone())){
                    showMessage("请输入正确手机号");
                    break;
                }
                sendCode(comUserEntity.getPhone());
                break;
            case R.id.iv_password_show:
                //密码显示状态改变
                new CommonUserDo().changePsw(edtPassword,ivPasswordShow);
                break;
            case R.id.forget_psw_tv_ok:
                //完成
                clickOk();
                break;
        }
    }

    //完成
    private void clickOk(){
        comUserEntity.setPhone(edtPhone.getText().toString().trim());
        comUserEntity.setCode(edtCode.getText().toString().trim());
        comUserEntity.setPassword(edtPassword.getText().toString().trim());
        if (TextUtils.isEmpty(comUserEntity.getPhone())){
            showMessage("请输入手机号");
            return;
        }
        if (!RegexUtils.isMobilePhoneNumber(comUserEntity.getPhone())){
            showMessage("请输入正确手机号");
            return;
        }
        if (TextUtils.isEmpty(comUserEntity.getCode())){
            showMessage("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(comUserEntity.getPassword())){
            showMessage("请输入密码");
            return;
        }
        if (!RegexUtils.isPWD(comUserEntity.getPassword())){
            showMessage("密码为6-16位数字、字母、字符");
            return;
        }

        forgetPsw();
    }

    /**
     * 发送验证码
     */
    public void sendCode(String phone) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.USER_SEND_CODE);
        commonOkhttp.putParams("telphone", phone);
        commonOkhttp.putCallback(new MyGenericsCallback<RegisterCodeEntity>(ForgetPswActivity.this) {
            @Override
            public void onSuccess(RegisterCodeEntity data, int d) {
                super.onSuccess(data, d);
                Toast.makeText(ForgetPswActivity.this, "验证码为"+data.getCode(), Toast.LENGTH_LONG).show();
                //获取验证码
                new CommonUserDo().getCode(ForgetPswActivity.this,tvGetCode,edtPhone.getText().toString().trim());
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 找回密码
     */
    public void forgetPsw() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.USER_FORGET_PWD);
        commonOkhttp.putParams("telphone", comUserEntity.getPhone());
        commonOkhttp.putParams("pwd", comUserEntity.getPassword());
        commonOkhttp.putParams("code", comUserEntity.getCode());
        commonOkhttp.putCallback(new MyGenericsCallback<WeatherEntity>(ForgetPswActivity.this) {
            @Override
            public void onSuccess(WeatherEntity data, int d) {
                super.onSuccess(data, d);
//                showMessage(getString(R.string.forget_success));
                SPUtils.savePreference(ForgetPswActivity.this,"isLogin","1");//0 未登录  1已登录

                SPUtils.savePreference(ForgetPswActivity.this,"phone",comUserEntity.getPhone());//0 未登录  1已登录
                SPUtils.savePreference(ForgetPswActivity.this,"pwd",comUserEntity.getPassword());//0 未登录  1已登录
                //跳转首页
                Intent intent = new Intent(ForgetPswActivity.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }
}
