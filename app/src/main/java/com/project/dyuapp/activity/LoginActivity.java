package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.ComUserEntity;
import com.project.dyuapp.entity.LoginEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.RegexUtils;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myviews.MyProgressDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;

/**
 * @author 界面：gengqiufang
 * @describtion 登录
 * @created at 2017/11/23 0023
 */
public class LoginActivity extends MyBaseActivity {

    @Bind(R.id.login_edt_phone)
    EditText edtPhone;
    @Bind(R.id.login_edt_password)
    EditText edtPassword;
    @Bind(R.id.login_tv_forget_password)
    TextView tvForgetPassword;
    @Bind(R.id.login_tv_login)
    TextView tvLogin;
    @Bind(R.id.login_tv_register)
    TextView tvRegister;
    @Bind(R.id.login_tv_qq)
    TextView tvQq;
    @Bind(R.id.login_tv_weixin)
    TextView tvWeixin;

    private ComUserEntity comUserEntity = new ComUserEntity();//用户信息实体
    private String userId;
    private String username;
    private String icon;
    private String loginType;//登陆类型 2 qq   3 微信

    private LoginEntity mData;
    private boolean isClickLogin = true;//预防重复登录的标志位
    private MyProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initTitle();
        initData();
        loadingDialog = new MyProgressDialog(this);
    }

    private void initTitle() {
        getRlTitle().setBackgroundColor(getResources().getColor(R.color.c_ffffff));
        getIvBack().setImageResource(R.mipmap.nav_back_02);
        getIvBack().setBackgroundColor(getResources().getColor(R.color.c_ffffff));
        setIvBack();
        getTvTitle().setTextColor(getResources().getColor(R.color.c_333333));
        setTvTitle("登录");
//        getIvBack().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (getIntent().getBooleanExtra("isSettingActivity", false)) {
//                    ScreenManager.getInstance().clearActivityStack();
//                    //跳转首页
//                    Intent intent = new Intent(LoginActivity.this, CityActivity.class);
//                    intent.putExtra("isSettingActivity",true);
//                    startActivity(intent);
//                } else {
//                    ScreenManager.getInstance().removeActivity(LoginActivity.this);
//                }
//            }
//        });
    }

    private void initData() {
        if (SPUtils.getPreference(LoginActivity.this, "phone") != null)
            edtPhone.setText(SPUtils.getPreference(LoginActivity.this, "phone"));
        if (SPUtils.getPreference(LoginActivity.this, "pwd") != null)
            edtPassword.setText(SPUtils.getPreference(LoginActivity.this, "pwd"));
    }

    @OnClick({R.id.login_tv_forget_password, R.id.login_tv_login, R.id.login_tv_register, R.id.login_tv_qq, R.id.login_tv_weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_tv_forget_password:
                //忘记密码
                startActivity(new Intent(this, ForgetPswActivity.class));
                break;
            case R.id.login_tv_login:
                //登录
                clickLogin();
                break;
            case R.id.login_tv_register:
                //注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login_tv_qq:
                //QQ登录
                if (UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.QQ)) {
                    loginType = "2";
                    UMShareAPI.get(this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                } else {
                    showMessage("请先安装QQ");
                }
                break;
            case R.id.login_tv_weixin:
                //微信
                if (UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.WEIXIN)) {
                    loginType = "3";
                    UMShareAPI.get(this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                } else {
                    showMessage("请先安装微信");
                }
                break;
        }
    }

    //点击登录
    private void clickLogin() {
        comUserEntity.setPhone(edtPhone.getText().toString().trim());
        comUserEntity.setPassword(edtPassword.getText().toString().trim());
        if (TextUtils.isEmpty(comUserEntity.getPhone())) {
            showMessage("请输入手机号");
            return;
        }
        if (!RegexUtils.isMobilePhoneNumber(comUserEntity.getPhone())) {
            showMessage("请输入正确手机号");
            return;
        }
        if (TextUtils.isEmpty(comUserEntity.getPassword())) {
            showMessage("请输入密码");
            return;
        }
        if (!RegexUtils.isPWD(comUserEntity.getPassword())) {
            showMessage("密码为6-16位字母和数字的组合");
            return;
        }
        if (isClickLogin) {
            loadingDialog.show();
            isClickLogin = false;
            login();
        }
    }

    /**
     * 登陆
     */
    public void login() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.USER_LOGIN);
        commonOkhttp.putParams("telphone", comUserEntity.getPhone());
        commonOkhttp.putParams("pwd", comUserEntity.getPassword());
        commonOkhttp.putCallback(new MyGenericsCallback<LoginEntity>(LoginActivity.this, false) {
            @Override
            public void onSuccess(LoginEntity data, int d) {
                super.onSuccess(data, d);
                mData = data;
                setAlias(data.getJpush_acc());
            }

            @Override
            public void onOther(JsonResult<LoginEntity> data, int d) {
                super.onOther(data, d);
                loadingDialog.dismiss();
                isClickLogin = true;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                loadingDialog.dismiss();
                isClickLogin = true;
            }
        });
        commonOkhttp.Execute();
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param share_media 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.d("gqf******","----------onStart----------");
        }

        /**
         * @desc 授权成功的回调
         * @param share_media 平台名称
         * @param i
         * @param map 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            userId = map.get("uid");
            username = map.get("name");
            icon = map.get("iconurl");
            thirdLogin();
            Log.d("gqf******","----------onComplete------userId----"+userId);
            Log.d("gqf******","----------onComplete-----username-----"+username);
            Log.d("gqf******","----------onComplete-----iconurl-----"+icon);
        }

        /**
         * @desc 授权失败的回调
         * @param share_media 平台名称
         * @param i
         * @param throwable 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Toast.makeText(LoginActivity.this, "失败：" + throwable.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("gqf******","----------onError----------"+throwable.getMessage());
        }

        /**
         * @desc 授权取消的回调
         * @param share_media 平台名称
         * @param i
         */
        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * 第三方登录
     */
    private void thirdLogin() {
        loadingDialog.show();
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.thirdParty);
        commonOkhttp.putParams("usid", userId);
        commonOkhttp.putParams("username", username);
        commonOkhttp.putParams("icon", icon);
        commonOkhttp.putParams("type", loginType);
        commonOkhttp.putParams("province",SPUtils.getPreference(this,"province"));
        commonOkhttp.putParams("city",SPUtils.getPreference(this,"city"));
        commonOkhttp.putParams("county",SPUtils.getPreference(this,"county"));
        commonOkhttp.putParams("lat",SPUtils.getPreference(this,"latitude"));
        commonOkhttp.putParams("log",SPUtils.getPreference(this,"longitude"));
        commonOkhttp.putCallback(new MyGenericsCallback<LoginEntity>(this, false) {
            @Override
            public void onSuccess(LoginEntity data, int d) {
                super.onSuccess(data, d);
                mData = data;
                Log.d("gqf******","----------thirdLogin----别名------"+data.getJpush_acc());
                setAlias(data.getJpush_acc());
            }

            @Override
            public void onOther(JsonResult<LoginEntity> data, int d) {
                super.onOther(data, d);
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                loadingDialog.dismiss();
            }
        });
        commonOkhttp.Execute();
    }

    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    private void setAlias(String alias) {
        if (TextUtils.isEmpty(alias)) {
            showMessage("设置的别名为空");
            loadingDialog.dismiss();
            isClickLogin = true;
            return;
        }
        if (!isValidTagAndAlias(alias)) {
            showMessage("无效的别名");
            loadingDialog.dismiss();
            isClickLogin = true;
            return;
        }
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }


    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
            }
        }
    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            loadingDialog.dismiss();
            isClickLogin = true;
            switch (code) {
                case 0:
                    //登录后的操作
                    SPUtils.savePreference(LoginActivity.this, "isLogin", "1");//0 未登录  1已登录
                    SPUtils.savePreference(LoginActivity.this, "phone", comUserEntity.getPhone());
                    SPUtils.savePreference(LoginActivity.this, "pwd", comUserEntity.getPassword());
                    SPUtils.savePreference(LoginActivity.this, "userid", mData.getState());//用户id
                    showMessage("登录成功！");
                    //极光推送-向服务器修改自己的类别(手机号)
                    JPushInterface.setAlias(LoginActivity.this, comUserEntity.getPhone(), null);
                    //跳转首页
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    ScreenManager.getInstance().removeActivity(LoginActivity.this);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                /*case 6002:
                    logs = "设置别名失败，60秒后重试";
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;*/
                default:
                    showMessage("设置别名失败：" + code);
            }
        }
    };

    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
