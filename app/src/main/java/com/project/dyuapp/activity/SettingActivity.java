package com.project.dyuapp.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.ContactInformation;
import com.project.dyuapp.entity.StatusEntity;
import com.project.dyuapp.myutils.CheckPermissionUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myutils.ShareUtil;
import com.project.dyuapp.myviews.CustomDialog;
import com.umeng.socialize.UMShareAPI;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.project.dyuapp.myutils.PublicStaticData.REQUEST_CAMERA_PERM;
import static com.project.dyuapp.myutils.PublicStaticData.REQUEST_CODE;

/**
 * @author shipeiyun
 * @description 我的-设置
 * @created at 2017/11/28 0028
 */
public class SettingActivity extends MyBaseActivity implements EasyPermissions.PermissionCallbacks {

    private String qq;//联系客服

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("设置");
        contactInformation();
    }

    @OnClick({R.id.setting_rl_add, R.id.setting_rl_code, R.id.setting_rl_feedback, R.id.setting_rl_agreement,
            R.id.setting_rl_contact, R.id.setting_rl_share, R.id.setting_tv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_rl_add:
                //扫码加钓友
                //初始化二维码权限
                initPermission();
                cameraTask(R.id.setting_rl_add);
                break;
            case R.id.setting_rl_code:
                //我的二维码
                goToActivity(MyCodeActivity.class);
                break;
            case R.id.setting_rl_feedback:
                //意见反馈
                goToActivity(FeedbackActivity.class);
                break;
            case R.id.setting_rl_agreement:
                //服务协议
                startActivity(new Intent(SettingActivity.this, CommenWebviewActivity.class)
                        .putExtra("title", "服务协议").putExtra("url", HttpUrl.agreement));
                break;
            case R.id.setting_rl_contact:
                //联系客服
                showContact();
                break;
            case R.id.setting_rl_share:
                //分享
                new ShareUtil(SettingActivity.this, SettingActivity.this,"钓鱼吧"," ",HttpUrl.URL+"?m=Home&c=Wechat&a=downPage");
                break;
            case R.id.setting_tv_logout:
                //退出登录
                SPUtils.savePreference(this, "isLogin", "0");//0 未登录  1已登录
                SPUtils.removePreference(this, "userid");
                showMessage("退出登录");
                //清除cookie
            /*    CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
                if (cookieJar instanceof PersistentCookieJar) {
                    ((PersistentCookieJar) cookieJar).clear();
                }*/
                SPUtils.savePreference(SettingActivity.this, "isOut", "1");//1 是退出操作
               // JPushInterface.stopPush(getApplicationContext());//注销推送
                startActivity(new Intent(this, LoginActivity.class)
                        .putExtra("isSettingActivity", true)
                );
                ScreenManager.getInstance().removeActivity(SettingActivity.this);
                break;
        }
    }

    /**
     * 15联系方式
     */
    private void contactInformation() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.contactInformation);
        commonOkhttp.putCallback(new MyGenericsCallback<ContactInformation>(SettingActivity.this) {
            @Override
            public void onSuccess(ContactInformation data, int d) {
                super.onSuccess(data, d);
                qq = data.getQq();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 扫二维码关注
     */
    private void qrcodeGz(String id) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.qrcodeGz);
        commonOkhttp.putParams("memberid", id);
        commonOkhttp.putCallback(new MyGenericsCallback<StatusEntity>(SettingActivity.this) {
            @Override
            public void onSuccess(StatusEntity data, int d) {
                super.onSuccess(data, d);
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 联系客服弹窗
     */
    private void showContact() {
        final CustomDialog dialog = new CustomDialog(SettingActivity.this, R.layout.dialog_contact, R.style.CustomDialogTheme);
        dialog.setCanceledOnTouchOutside(true);
        TextView tvNum = (TextView) dialog.findViewById(R.id.dialog_contact_tv_num);
        TextView tvConfirm = (TextView) dialog.findViewById(R.id.dialog_contact_tv_confirm);
        tvNum.setText(qq);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 初始化权限事件
     */
    private void initPermission() {
        //检查权限
        String[] permissions = CheckPermissionUtils.checkPermission(this);
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
        } else {
            //申请权限
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    if (!TextUtils.isEmpty(result)) {
                        qrcodeGz(result);
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(SettingActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        } else if (requestCode == REQUEST_CAMERA_PERM) {
            Toast.makeText(this, "从设置页面返回...", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * EsayPermissions接管权限处理逻辑
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_CAMERA_PERM)
    public void cameraTask(int viewId) {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            onClick(viewId);
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "需要请求camera权限",
                    REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    /**
     * 扫码加钓友点击事件处理逻辑
     *
     * @param viewId
     */
    private void onClick(int viewId) {
        switch (viewId) {
            case R.id.setting_rl_add:
                Intent intent = new Intent(getApplication(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        Toast.makeText(this, "执行onPermissionsGranted()...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        Toast.makeText(this, "执行onPermissionsDenied()...", Toast.LENGTH_SHORT).show();
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "当前App需要申请camera权限,需要打开设置页面么?")
                    .setTitle("权限申请")
                    .setPositiveButton("确认")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(REQUEST_CAMERA_PERM)
                    .build()
                    .show();
        }
    }

}
