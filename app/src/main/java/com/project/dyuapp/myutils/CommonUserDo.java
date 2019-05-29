package com.project.dyuapp.myutils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dyuapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @describtion  公共用户操作方法
 * @author gengqiufang
 * @created at 2017/11/28 0028
 */

public class CommonUserDo {

    //获取验证码
    public void getCode(Context mContext, TextView tvGetCode, String phoneNum){
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtils.getInstance(mContext).showMessage("请输入手机号");
            return;
        }
        if (!RegexUtils.isMobilePhoneNumber(phoneNum)) {
            ToastUtils.getInstance(mContext).showMessage("请输入正确手机号");
            return;
        }
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tvGetCode, 60000, 1000);
        mCountDownTimerUtils.start();
    }

    //密码显示状态改变
    public void changePsw(EditText edtPassword, ImageView ivPasswordShow){
        if (edtPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            ivPasswordShow.setImageResource(R.mipmap.sign_show_not);
            edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            ivPasswordShow.setImageResource(R.mipmap.sign_show);
            edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }
    /**
     * 从asset路径下读取对应文件转String输出
     * @param mContext
     * @return
     */
    public static String getJson(Context mContext, String fileName) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        AssetManager am = mContext.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    am.open(fileName)));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            sb.delete(0, sb.length());
        }
        return sb.toString().trim();
    }
}
