package com.project.dyuapp.myutils;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * @创建者 任伟
 * @创建时间 2017/12/04 16:28
 * @描述 ${屏幕尺寸工具类}
 */

public class WindowUtils {
    //获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
    //获取屏幕的高度
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }
}
