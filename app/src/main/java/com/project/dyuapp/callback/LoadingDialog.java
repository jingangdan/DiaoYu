package com.project.dyuapp.callback;

import android.app.Dialog;
import android.content.Context;

import com.project.dyuapp.R;


/**
 * 等待提示框
 */

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        this(context, R.style.loading_dialog,R.layout.dialog_loading);
    }

    public LoadingDialog(Context context, int theme, int resLayout) {
        super(context, theme);
        this.setContentView(resLayout);
    }

}
