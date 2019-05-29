package com.project.dyuapp.myutils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @创建者 任伟
 * @创建时间 2017/12/06 17:57
 * @描述 ${}
 */

public class Utils {

    /**
     * 字符串转时间戳 "yyyy-MM-dd HH:mm:ss"
     *
     * @param timeString 时间字符串
     * @param dataFormat 时间格式
     * @return
     */
    public static long strToStamp(String timeString, String dataFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
        Date d;
        long l = 0;
        try {
            d = sdf.parse(timeString);
            l = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * 时间戳转字符串 "yyyy-MM-dd HH:mm:ss"
     *
     * @param time       时间戳
     * @param dataFormat 转换格式
     */
    public static String convertDate(Long time, String dataFormat) {
        Date d = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
        String s = sdf.format(d);
        return s;
    }

    public static String convertDate(String str, String dataFormat) {
        long time = 0l;
        if (!TextUtils.isEmpty(str)) {
            time = Long.parseLong(str);
        }
        Date d = new Date(time * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
        String s = sdf.format(d);
        return s;
    }

    /**
     * 比较时间1是否大于时间2
     *
     * @param time1 mm:ss
     * @param time2 mm:ss
     * @return
     */
    public static boolean compareTime(String time1, String time2) {
        return strToStamp("yyyy-MM-dd HH:mm:ss", time1) > strToStamp("yyyy-MM-dd HH:mm:ss", time2);
    }

    /**
     * 获取当前年月日字符串
     *
     * @return
     */
    public static String getCurrentTime() {
        long currentTime = System.currentTimeMillis();
        return convertDate(currentTime, "yyyy-MM-dd");
    }

    /**
     * 验证手机及电话格式
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "^((13[0-9])|(15[^4,\\D])|(14[0,1-9])|(18[0,1-9])|(17[0,1-9]))\\d{8}$"
                + "||" + "^\\d{3}-?\\d{3}-?\\d{4}|\\d{3}-?\\d{3}-?\\d{4}&"
                + "||" + "^\\d{4}-?\\d{8}|\\d{4}-?\\d{8}&"
                + "||" + "^\\d{4}-?\\d{7}|\\d{4}-?\\d{7}&"
                + "||" + "^\\d{3}-?\\d{7}|\\d{4}-?\\d{7}&"
                + "||" + "^\\d{3}-?\\d{8}|\\d{4}-?\\d{8}&";

        // String expression = "^\\d{3}-?\\d{8}|\\d{4}-?\\d{8}&";//前面三位，后面八位
        // String expression = "^\\d{3}-?\\d{7}|\\d{4}-?\\d{7}&";//前面三位，后面七位
        // String expression = "^\\d{4}-?\\d{7}|\\d{4}-?\\d{7}&";//前面四位，后面七位
        // String expression = "^\\d{4}-?\\d{8}|\\d{4}-?\\d{8}&";//前面四位，后面八位

        // String expression = "^\\d{3}-?\\d{3}-?\\d{4}|\\d{3}-?\\d{3}-?\\d{4}&";//400的
        // String expression = "^((13[0-9])|(15[^4,\\D])|(14[0,1-9])|(18[0,1-9])|(17[0,1-9]))\\d{8}$";//手机号
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        } else {
        }
        return isValid;
    }

    /**
     * 获取指定日期周几
     *
     * @param date 指定日期
     * @return 返回值为： "周日", "周一", "周二", "周三", "周四", "周五", "周六"
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0)
            week = 0;
        return weekDays[week];
    }

    /**
     * strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss
     *
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * 当前系统时间加num天
     *
     * @param date
     * @return
     */
    public static Date getNextDay(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +num);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取当前时间加num天的星期
     * @param loc
     * @param num
     * @return
     */
    public static String getWeek(String loc,int num){
        try {
            return getWeekOfDate(getNextDay(stringToDate(loc,"yyyy-MM-dd HH:mm"),num));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取屏幕的宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * 获取屏幕的高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }
}
