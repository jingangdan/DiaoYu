package com.project.dyuapp.myutils;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * @author gengqiufang
 * @describtion 历史记录工具类
 * @created at 2017/12/8 0008
 */

public class HistoryUtils {
    private Context context;
    private static String keyHistory = "SearchHistory";

    private static volatile HistoryUtils mHistoryUtils;

    private HistoryUtils(Context context) {
        this.context = context;
    }

    public static HistoryUtils getInstance(Context context) {
        if (null == mHistoryUtils) {
            synchronized (ToastUtils.class) {
                if (null == mHistoryUtils) {
                    mHistoryUtils = new HistoryUtils(context);
                }
            }
        }
        return mHistoryUtils;
    }

    //读取历史记录
    public ArrayList<String> readHistory() {
        ArrayList<String> list = new ArrayList<>();
        String str = SPUtils.getPreference(context, keyHistory);
        if (!TextUtils.isEmpty(str)) {
            if (str.contains(",")) {
                String str1[] = str.split(",");
                for (String item : str1) {
                    list.add(item);
                }
            } else {
                list.add(str);
            }
        }
        return list;
    }

    //插入历史记录
    public void insertHistory(String value) {
        String searchHistory = SPUtils.getPreference(context, keyHistory);
        if (TextUtils.isEmpty(searchHistory)) {
            SPUtils.savePreference(context, keyHistory, value);
        } else {
            SPUtils.savePreference(context, keyHistory, value + "," + searchHistory);
             //只显示5条不重复的数据
            ArrayList<String> list = new ArrayList<>();
            String str = SPUtils.getPreference(context, keyHistory);
            String str1[] = str.split(",");
            for (String item : str1) {
                if (!list.contains(item) && list.size() < 5) {
                    list.add(item);
                }
            }
            reloadHistory(list);
        }

    }

    //删除单条历史记录h后重新存储
    public void reloadHistory(ArrayList<String> historyList) {
        String history = "";
        for (String str : historyList) {
            history = history + str + ",";
        }
        if (!TextUtils.isEmpty(history)) {
            history = history.substring(0, history.length() - 1);
        }
        SPUtils.savePreference(context, keyHistory, history);
    }
}
