package com.project.dyuapp.myviews;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.project.dyuapp.R;

public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int layout) {
        super(context, R.style.DialogTheme);
        // set content
        setContentView(layout);
        // set window params
        Window window = getWindow();
        window.setWindowAnimations(R.style.dialogWindowAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        // set width,height by density and gravity
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //params.gravity = Gravity.BOTTOM;
        //params.alpha = 0.6f;
        window.setAttributes(params);
    }

    public CustomDialog(Context context, int layout, boolean isBottom) {
        super(context, R.style.CustomDialogTheme);
        // set content
        setContentView(layout);
        // set window params
        Window window = getWindow();
        window.setWindowAnimations(R.style.dialogWindowAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        // set width,height by density and gravity
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        //params.alpha = 0.6f;
        window.setAttributes(params);
    }

    public CustomDialog(Context context, int layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        //setCanceledOnTouchOutside(true);
        window.setWindowAnimations(R.style.dialogWindowAnim);

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //params.gravity = Gravity.BOTTOM;
        //params.alpha = 0.6f;
        window.setAttributes(params);
    }

    public CustomDialog(Context context, int layout, int style, boolean isBottom) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        //setCanceledOnTouchOutside(true);
        window.setWindowAnimations(R.style.dialogWindowAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        //params.alpha = 0.6f;
        window.setAttributes(params);
    }

    public CustomDialog(Context context, int width, int y, int layout, int style, boolean isBottom) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        //setCanceledOnTouchOutside(true);
        window.setWindowAnimations(R.style.dialogWindowAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.y = y;
        params.gravity = Gravity.BOTTOM;
        //params.alpha = 0.6f;
        window.setAttributes(params);
    }

}