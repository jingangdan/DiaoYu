package com.project.dyuapp.mysign;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数组操作工具类
 * 
 * Utils of data operation
 *
 * @author AigeStudio 2015-07-22
 */
public final class DataUtils {
    /**
     * 一维数组转换为二维数组
     *
     * @param src    ...
     * @param row    ...
     * @param column ...
     * @return ...
     */
    public static String[][] arraysConvert(String[] src, int row, int column) {
        String[][] tmp = new String[row][column];
        for (int i = 0; i < row; i++) {
            tmp[i] = new String[column];
            System.arraycopy(src, i * column, tmp[i], 0, column);
        }
        return tmp;
    }
    /**
     * 根据毫秒转换日期
     *
     * @param timeString
     * @param dataFormat yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String convertDate(String timeString, String dataFormat) {
        Date d = new Date(Long.valueOf(timeString) * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
        String s = sdf.format(d);
        return s;
    }
}
