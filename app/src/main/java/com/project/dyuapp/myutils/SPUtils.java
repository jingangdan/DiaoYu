package com.project.dyuapp.myutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.project.dyuapp.R;
import com.youku.cloud.utils.SdkApplication;

import static com.project.dyuapp.myutils.PublicStaticData.SP_FILE;

/**
 * @author gengqiufang
 * @describtion 存取数据 公共类
 * @created at 2017/4/26 0026
 */

public class SPUtils {
    /**
     * 传入key，value，将数据存入Constant.SP_FILE(zzyj)中
     * @param context 上下文对象
     * @param key     键值
     * @param value   数据
     */
    public static void savePreference(Context context, String key, String value) {
        SharedPreferences preference = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 根据key值 从名为Constant.SP_FILE(zzyj)的SharedPreferences中取出数据
     * @param context 上下文对象
     * @param key     键值
     * @return
     */
    public static String getPreference(Context context, String key) {
        SharedPreferences preference = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
        return preference.getString(key, "");
    }
/**
 * @describtion  根据key值 从名为Constant.SP_FILE(zzyj)的SharedPreferences中移除数据
 * @author gengqiufang
 * @created at 2017/4/26 0026
 */
    public static void removePreference(Context context, String key) {
        SharedPreferences preference = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * @describtion  根据key值 清除Constant.SP_FILE(zzyj)的SharedPreferences
     * @author gengqiufang
     * @created at 2017/4/26 0026
     */
    public static void clearSP(Context context) {
        SharedPreferences preference = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.clear();
        editor.commit();
    }

    public static int getRealHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) SdkApplication.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            wm.getDefaultDisplay().getRealSize(point);
            return point.x > point.y ? point.y
                    : point.x;
        } else if (Build.VERSION.SDK_INT >= 13) {
            wm.getDefaultDisplay().getSize(point);
            return point.x > point.y ? point.y
                    : point.x;
        } else {
            wm.getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.widthPixels > displayMetrics.heightPixels ? displayMetrics.heightPixels
                    : displayMetrics.widthPixels;
        }
    }

    public static int getRealWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) SdkApplication.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            wm.getDefaultDisplay().getRealSize(point);
            return point.x < point.y ? point.y
                    : point.x;
        } else if (Build.VERSION.SDK_INT >= 13) {
            wm.getDefaultDisplay().getSize(point);
            return point.x < point.y ? point.y
                    : point.x;
        } else {
            wm.getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.widthPixels < displayMetrics.heightPixels ? displayMetrics.heightPixels
                    : displayMetrics.widthPixels;
        }
    }
    public static void setLevel(LinearLayout linearLayout, Context mContext, int star) {
        linearLayout.removeAllViews();
        for (int i = 0; i < star; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_level_image, null);
            ImageView item_iv = (ImageView) view.findViewById(R.id.item_img_level);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(item_iv.getLayoutParams());
            float dimension = mContext.getResources().getDimension(R.dimen.x24);
            int round = Math.round(dimension);
            lp.setMargins(round, 0, 0, 0);
            item_iv.setLayoutParams(lp);
            linearLayout.addView(view);
        }
    }
}
