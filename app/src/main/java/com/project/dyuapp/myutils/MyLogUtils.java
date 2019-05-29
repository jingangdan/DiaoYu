package com.project.dyuapp.myutils;

import android.util.Log;

/**
 * @describe：Log公用类
 * @author：刘晓丽
 * @createdate：2017/11/20 9:46
 */

public class MyLogUtils {
    public static final String TAG = "888888";//测试使用  gqf

    private static boolean isText = true;//发布正式版时需要把这个打印的开关关掉

    private static final int LOG_E = 1;
    private static final int LOG_V = 2;
    private static final int LOG_I = 3;
    private static final int LOG_D = 4;


    private static void myLog(String tag, String message, int flag) {
        if (!isText) {
            return;
        }
        switch (flag) {
            case LOG_E:
                Log.e(tag, message);
                break;
            case LOG_V:
                Log.v(tag, message);
                break;
            case LOG_I:
                Log.i(tag, message);
                break;
            case LOG_D:
                Log.d(tag, message);
                break;
        }
    }

    public static void logE(String tag, String message) {
        myLog(tag, message, LOG_E);
    }

    public static void logV(String tag, String message) {
        myLog(tag, message, LOG_V);
    }

    public static void logI(String tag, String message) {
        myLog(tag, message, LOG_I);
    }

    public static void logD(String tag, String message) {
        myLog(tag, message, LOG_D);
    }

}
