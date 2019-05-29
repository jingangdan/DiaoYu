package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.ComUserEntity;
import com.project.dyuapp.entity.LoginEntity;
import com.project.dyuapp.entity.RegisterCodeEntity;
import com.project.dyuapp.myutils.CommonUserDo;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PopSelectAddress;
import com.project.dyuapp.myutils.RegexUtils;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myviews.MyProgressDialog;

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
 * @describtion 注册
 * @created at 2017/11/28 0028
 */
public class RegisterActivity extends MyBaseActivity {

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
    @Bind(R.id.register_tv_sel_address)
    TextView tvSelAddress;
    @Bind(R.id.register_rl_sel_address)
    RelativeLayout rlSelAddress;
    @Bind(R.id.register_tv_register)
    TextView tvRegister;

    @Bind(R.id.register_tv_agreement)
    TextView tvAgreement;
    @Bind(R.id.register_iv_agree)
    ImageView ivAgree;
    @Bind(R.id.register_ll_agree)
    LinearLayout llAgree;

    private ComUserEntity comUserEntity = new ComUserEntity();//用户信息实体

    private  LoginEntity mData;
    private boolean isClickLogin = true;//预防重复登录的标志位
    private MyProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initTitle();//初始化title
        loadingDialog = new MyProgressDialog(this);
    }

    //初始化title
    private void initTitle() {
        getRlTitle().setBackgroundColor(getResources().getColor(R.color.c_ffffff));
        getIvBack().setImageResource(R.mipmap.nav_back_02);
        getIvBack().setBackgroundColor(getResources().getColor(R.color.c_ffffff));
        setIvBack();
        getTvTitle().setTextColor(getResources().getColor(R.color.c_333333));
        setTvTitle("用户注册");
    }

    @OnClick({R.id.tv_get_code, R.id.iv_password_show, R.id.register_rl_sel_address, R.id.register_tv_register, R.id.register_ll_agree, R.id.register_tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                String phone = edtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    showMessage("请输入手机号");
                    break;
                } else if (!RegexUtils.isMobilePhoneNumber(phone)) {
                    showMessage("请输入正确手机号");
                    break;
                }
                sendCode(phone);//发送验证码
                //获取验证码
//                new CommonUserDo().getCode(RegisterActivity.this, tvGetCode, edtPhone.getText().toString().trim());
                break;
            case R.id.iv_password_show:
                //密码显示状态改变
                new CommonUserDo().changePsw(edtPassword, ivPasswordShow);
                break;
            case R.id.register_rl_sel_address:
                //选择地址
                selectAddress();
                break;
            case R.id.register_tv_register:
                //注册
                clickRegister();
                break;
            case R.id.register_ll_agree:
                //我已阅读并同意
                clickAgree();
                break;
            case R.id.register_tv_agreement:
                //《钓鱼吧服务协议条款》
                startActivity(new Intent(this, CommenWebviewActivity.class)
                        .putExtra("title", "《钓鱼吧服务协议条款》")
                        .putExtra("url", HttpUrl.HFIVE_CONTENT_ID_8));
                break;
        }

    }

    //点击注册
    private void clickRegister() {
        comUserEntity.setPhone(edtPhone.getText().toString().trim());
        comUserEntity.setCode(edtCode.getText().toString().trim());
        comUserEntity.setPassword(edtPassword.getText().toString().trim());
        comUserEntity.setAddress(tvSelAddress.getText().toString());
        if (TextUtils.isEmpty(comUserEntity.getPhone())) {
            showMessage("请输入手机号");
            return;
        }
        if (!RegexUtils.isMobilePhoneNumber(comUserEntity.getPhone())) {
            showMessage("请输入正确手机号");
            return;
        }
        if (TextUtils.isEmpty(comUserEntity.getCode())) {
            showMessage("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(comUserEntity.getPassword())) {
            showMessage("请输入密码");
            return;
        }
        if (!RegexUtils.isPWD(comUserEntity.getPassword())) {
            showMessage("密码为6-16位数字、字母、字符");
            return;
        }
        if (TextUtils.isEmpty(comUserEntity.getAddress())) {
            showMessage("请选择地址");
            return;
        }
        if (!comUserEntity.isAgree()) {
            showMessage("请阅读并同意《钓鱼吧服务协议条款》");
            return;
        }
        if (isClickLogin) {
            loadingDialog.show();
            isClickLogin = false;
            register();
        }

        //跳转首页
//        Intent intent = new Intent(RegisterActivity.this,CityActivity.class);
//        startActivity(intent);
//        showMessage(getString(R.string.register_success));

    }

    //我已阅读并同意
    private void clickAgree() {
        if (comUserEntity.isAgree()) {
            comUserEntity.setAgree(false);
            ivAgree.setImageResource(R.mipmap.sign_selected_not);
        } else {
            comUserEntity.setAgree(true);
            ivAgree.setImageResource(R.mipmap.sign_selected);
        }
    }

    /**
     * 注册
     */
    public void register() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.USER_REGISTER);
        commonOkhttp.putParams("telphone", comUserEntity.getPhone());
        commonOkhttp.putParams("pwd", comUserEntity.getPassword());
        commonOkhttp.putParams("code", comUserEntity.getCode());
        commonOkhttp.putParams("provinceid", comUserEntity.getProvinceid());
        commonOkhttp.putParams("cityid", comUserEntity.getCityid());
        commonOkhttp.putCallback(new MyGenericsCallback<LoginEntity>(RegisterActivity.this,false) {
            @Override
            public void onSuccess(LoginEntity data, int d) {
                super.onSuccess(data, d);
                mData=data;
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

    /**
     * 发送验证码
     */
    public void sendCode(String phone) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.USER_SENDCDE);
        commonOkhttp.putParams("telphone", phone);
        commonOkhttp.putCallback(new MyGenericsCallback<RegisterCodeEntity>(RegisterActivity.this) {
            @Override
            public void onSuccess(RegisterCodeEntity data, int d) {
                super.onSuccess(data, d);
//                Toast.makeText(RegisterActivity.this, "验证码为"+data.getCode(), Toast.LENGTH_LONG).show();
                //获取验证码
                new CommonUserDo().getCode(RegisterActivity.this, tvGetCode, edtPhone.getText().toString().trim());
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 选择地区弹窗
     */
    public void selectAddress() {
        //设置默认地址(本地读取)
        String province = "河北省";
        final String city = "石家庄市";
        PopSelectAddress popSelectAddress = new PopSelectAddress(this, getWindow(), province, city, "");
        popSelectAddress.showHttpAddress(); // 网络下载地址数据
//        popSelectAddress.showLocalAddress(); // 加载本地地址数据
        popSelectAddress.setCallBack(new PopSelectAddress.CallBackAddress() {
            @Override
            public void onClickOk(String selProvince, String selCity, String selZone, String selProvinceCode, String selCityCode, String selZoneCode) {
                tvSelAddress.setText(selProvince + selCity);
                comUserEntity.setProvinceid(selProvinceCode);
                comUserEntity.setCityid(selCityCode);
            }
        });
    }

    /**
     * 注册发送短信
     */
/*    private void regSendSMS() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.regSendSMS);
        commonOkhttp.putParams("phone", registerEtPhone.getText().toString());
        commonOkhttp.putCallback(new MyGenericsCallback<RegSendSMSBean>(this, false) {
            @Override
            public void onSuccess(RegSendSMSBean data, int d) {
                super.onSuccess(data, d);
                //验证码倒计时
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(registerTvGetcode, 60000, 1000);
                mCountDownTimerUtils.start();
                // registerEtCode.setText("666666");
            }
        });
        commonOkhttp.Execute();
    }*/

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
                    SPUtils.savePreference(RegisterActivity.this,"isLogin","1");//0 未登录  1已登录
                    SPUtils.savePreference(RegisterActivity.this,"phone",comUserEntity.getPhone());
                    SPUtils.savePreference(RegisterActivity.this,"pwd",comUserEntity.getPassword());
                    SPUtils.savePreference(RegisterActivity.this,"userid",mData.getState());//用户id
                    showMessage("登录成功！");
                    //跳转首页
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    intent.putExtra("isRepeatedLogin",true);
                    startActivity(intent);
                    ScreenManager.getInstance().removeActivity(RegisterActivity.this);
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
}
