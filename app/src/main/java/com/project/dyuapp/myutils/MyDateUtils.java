package com.project.dyuapp.myutils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @describe：时间工具类
 * @author：刘晓丽
 * @createdate：2017/11/20 13:44
 */
@SuppressLint("SimpleDateFormat")
public class MyDateUtils {

    /**
     * 两个时间的差值
     *
     * @param startDate 开始时间 时间戳格式
     * @param endDate   结束时间 时间戳格式
     * @return
     */
    public static String twoDateDistance1(long startDate, long endDate) {

        long timeLong = endDate - startDate;
        long year = timeLong / (24 * 60 * 60 * 1000 * 365);
        String yearStr = String.valueOf(year);
        long month = timeLong % (24 * 60 * 60 * 1000 * 365) / (24 * 60 * 60 * 1000 * 30);
        String monthStr = String.valueOf(month);
        long day = timeLong % (24 * 60 * 60 * 1000 * 365) % (24 * 60 * 60 * 1000 * 30) / (24 * 60 * 60 * 1000);
        String dayStr = String.valueOf(day);
        long hour = timeLong % (24 * 60 * 60 * 1000 * 365) % (24 * 60 * 60 * 1000 * 30) % (24 * 60 * 60 * 1000)
                / (60 * 60 * 1000);
        String hourStr = String.valueOf(hour);
        long minute = timeLong % (24 * 60 * 60 * 1000 * 365) % (24 * 60 * 60 * 1000 * 30) % (24 * 60 * 60 * 1000)
                % (60 * 60 * 1000) / (60 * 1000);
        long sec = timeLong % (24 * 60 * 60 * 1000 * 365) % (24 * 60 * 60 * 1000 * 30) % (24 * 60 * 60 * 1000)
                % (60 * 60 * 1000) % (60 * 1000) / 1000;
        String minuteStr = String.valueOf(minute);
        String returnStr = "";
        if (year != 0) {
            returnStr += yearStr + "年";
        }
        if (month != 0) {
            returnStr += monthStr + "月";
        }
        if (day != 0) {
            returnStr += dayStr + "天";
        }
        if (hour != 0) {
            returnStr += hourStr + "小时";
        }
        if (minute != 0) {
            returnStr += minuteStr + "分钟";
        }
        if (sec != 0)
            returnStr += sec + "秒";
        return returnStr == null ? "已结束" : returnStr;
    }

    /**
     * 两个时间的差值
     *
     * @param timeLong 时间戳 差值
     * @return
     */
    public static String twoDateDistance2(long timeLong) {

        long hour = timeLong / (60 * 60);
        String hourStr = String.valueOf(hour);
        long minute = timeLong % (60 * 60) / 60;
        String minuteStr = String.valueOf(minute);
        long sec = timeLong % (60 * 60) % 60;
        String secStr = String.valueOf(sec);
        String returnStr = "";
        if (hour != 0) {
            if (hourStr.length() == 1) {
                returnStr += "0" + hourStr + ":";
            } else {
                returnStr += hourStr + ":";
            }
        } else {
            returnStr += "00" + ":";
        }
        if (minute != 0) {
            if (minuteStr.length() == 1) {
                returnStr += "0" + minuteStr + ":";
            } else {
                returnStr += minuteStr + ":";
            }
        } else {
            returnStr += "00" + ":";
        }
        if (sec != 0) {
            if (secStr.length() == 1) {
                returnStr += "0" + secStr;
            } else {
                returnStr += secStr;
            }
        } else {
            returnStr += "00";
        }
        return returnStr == null ? "已结束" : returnStr;
    }

    /**
     * 倒计时
     *
     * @param str1   开始时间 "yyyy-MM-dd HH:mm:ss"
     * @param str2   结束时间 "yyyy-MM-dd HH:mm:ss"
     * @param format 时间格式 "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String twoDateDistance3(String str1, String str2, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date d1 = df.parse(str1);
            Date d2 = df.parse(str2);
            long timeLong = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别
            if (timeLong > 0) {
                long year = timeLong / (24 * 60 * 60 * 1000 * 365);
                long month = timeLong % (24 * 60 * 60 * 1000 * 365) / (24 * 60 * 60 * 1000 * 30);
                long day = timeLong % (24 * 60 * 60 * 1000 * 365) % (24 * 60 * 60 * 1000 * 30) / (24 * 60 * 60 * 1000);
                long hour = timeLong % (24 * 60 * 60 * 1000 * 365) % (24 * 60 * 60 * 1000 * 30) % (24 * 60 * 60 * 1000) / (60 * 60 * 1000);
                long minute = timeLong % (24 * 60 * 60 * 1000 * 365) % (24 * 60 * 60 * 1000 * 30) % (24 * 60 * 60 * 1000) % (60 * 60 * 1000) / (60 * 1000);
                long sec = timeLong % (24 * 60 * 60 * 1000 * 365) % (24 * 60 * 60 * 1000 * 30) % (24 * 60 * 60 * 1000) % (60 * 60 * 1000) % (60 * 1000) / 1000;
                String returnStr = "";
                if (year != 0) {
                    returnStr += year + "年";
                }
                if (month != 0) {
                    returnStr += month + "月";
                }
                if (day != 0) {
                    returnStr += day + "天";
                }
                if (hour != 0) {
                    if ((hour + "").length() == 1) {
                        returnStr += "0" + hour + ":";
                    } else {
                        returnStr += hour + ":";
                    }
                } else {
                    returnStr += "00" + ":";
                }
                if (minute != 0) {
                    if ((minute + "").length() == 1) {
                        returnStr += "0" + minute + ":";
                    } else {
                        returnStr += minute + ":";
                    }
                } else {
                    returnStr += "00" + ":";
                }
                if (sec != 0) {
                    if ((sec + "").length() == 1) {
                        returnStr += "0" + sec;
                    } else {
                        returnStr += sec;
                    }
                } else {
                    returnStr += "00" + "";
                }
                return "".equals(returnStr) ? "已结束" : returnStr;
            } else
                return "已结束";

        } catch (Exception e) {
        }
        return "已结束";
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss  大写的HH是24小时制日期格式
     * @return
     */
    public static long dateToTimeStamp(String date_str, String format) {
        long time = 0;
        if (date_str == null || "".equals(date_str))
            return 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            time = sdf.parse(date_str).getTime() / 1000;
            return time;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 根据日期格式获取当前时间
     *
     * @param format "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getCurrentTime(String format) {
        String date = "";
        SimpleDateFormat sDateFormat = new SimpleDateFormat(format);
        date = sDateFormat.format(new Date());
        return date;
    }


    /**
     * 时间戳转日期格式
     *
     * @param date_str 时间戳 String型 如"1403499900"
     * @param format   日期格式 String型 如"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String timeStampToData(String date_str, String format) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String date = sdf.format(new Date(Long.parseLong(date_str) * 1000));
            return date;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }


    /**
     * 两个时间的大小
     *
     * @param str1   开始时间 "yyyy-MM-dd HH:mm:ss"
     * @param str2   结束时间 "yyyy-MM-dd HH:mm:ss"
     * @param format 时间格式 "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static long compareTime(String str1, String str2, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date d1 = df.parse(str1);
            Date d2 = df.parse(str2);
            long timeLong = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别
            return timeLong;
        } catch (Exception e) {

        }
        return -1;
    }

    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     *
     * @param time_current 系统当前时间
     * @param time_creat   需要比较的时间
     * @return
     */
    public static String getStandardDate(Long time_current, Long time_creat) {
        StringBuffer sb = new StringBuffer();

        long time = (time_current - time_creat) * 1000;

        long mill = time / 1000;//秒前

        long minute = time / 60 / 1000;// 分钟前

        long hour = time / 60 / 60 / 1000;// 小时

        long day = time / 24 / 60 / 60 / 1000;// 天前

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time1 = sdf.format(new Date(time_creat * 1000));

        if (day - 1 >= 0) {
            sb.append(time1);
        } else if (hour - 1 >= 0) {
            sb.append(hour + "小时前");
        } else if (minute - 1 >= 0) {
            sb.append(minute + "分钟前");
        } else {
            sb.append("刚刚");
        }
        return sb.toString();
    }


}
