package com.project.dyuapp.myviews;

import android.app.Dialog;
import android.content.Context;

import com.project.dyuapp.R;

/**
 * @功能描述： 等待框
 * @Author： 刘晓丽
 * @data： 2016/11/24 14:04
 */
public class MyProgressDialog extends Dialog {

    public MyProgressDialog(Context context) {
        this(context, R.style.style_dialog);
    }

    public MyProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_progress);
        this.setCancelable(false);
    }

}
