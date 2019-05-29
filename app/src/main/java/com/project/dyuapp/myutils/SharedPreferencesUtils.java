package com.project.dyuapp.myutils;

import android.content.SharedPreferences;

/**
 * @describe：
 * @author：刘晓丽
 * @createdate：2017/11/20 14:01
 * <p>
 * 使用：保存数据
 * SharedPreferencesUtils.setParam(this, "String", "xiaanming");
 * SharedPreferencesUtils.setParam(this, "int", 10);
 * SharedPreferencesUtils.setParam(this, "boolean", true);
 * SharedPreferencesUtils.setParam(this, "long", 100L);
 * SharedPreferencesUtils.setParam(this, "float", 1.1f);
 * <p>
 * 使用：获取数据
 * SharedPreferencesUtils.getParam(TimerActivity.this, "String", "");                                                                                        SharedPreferencesUtils.getParam(TimerActivity.this, "int", 0);
 * SharedPreferencesUtils.getParam(TimerActivity.this, "boolean", false);
 * SharedPreferencesUtils.getParam(TimerActivity.this, "long", 0L);
 * SharedPreferencesUtils.getParam(TimerActivity.this, "float", 0.0f);
 */

public class SharedPreferencesUtils {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_date";


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void setParam(String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences.Editor editor = PublicStaticData.sharedPreferences.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }else{
            MyLogUtils.logE("==SharedPreferencesUtils==","保存本地的数据失败");
            return;
        }
        editor.commit();
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = PublicStaticData.sharedPreferences;

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }
        MyLogUtils.logE("==SharedPreferencesUtils==","获取本地保存的数据失败");
        return null;
    }
}
