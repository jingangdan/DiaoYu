package com.project.dyuapp.myutils;

import android.content.Context;
import android.widget.Toast;

/**
 * @describe：Toast公用类
 * @author：刘晓丽
 * @createdate：2017/11/20 9:46
 */

public class ToastUtils {

    protected static Toast toast = null;

    private static volatile ToastUtils mToastUtils;

    private ToastUtils(Context context) {
        toast = Toast.makeText(context.getApplicationContext(), null, Toast.LENGTH_SHORT);
    }

    public static ToastUtils getInstance(Context context) {
        if (null == mToastUtils) {
            synchronized (ToastUtils.class) {
                if (null == mToastUtils) {
                    mToastUtils = new ToastUtils(context);
                }
            }
        }
        return mToastUtils;
    }

    public void showMessage(String toastMsg) {
        toast.setText(toastMsg);
        toast.show();
    }

    public void toastCancel() {
        if (null != toast) {
            toast.cancel();
            toast = null;
        }
        mToastUtils = null;
    }
}
