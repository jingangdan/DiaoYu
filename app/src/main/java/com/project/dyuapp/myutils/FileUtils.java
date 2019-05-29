package com.project.dyuapp.myutils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

/**
 * @创建者 任伟
 * @创建时间 2017/05/05 11:25
 * @描述 ${文件工具类}
 */

public class FileUtils {

    public static String PATH_JSON = "/dyb/address/";// 创建.json文件路径
    public static String JSON_NAME = "address.json";// 创建.json文件名
    /**
     * 创建一个.json文件
     *
     * @param fileName 文件名
     * @return
     */
    public static File createAddressFile(String fileName) {
        String outDirPath; // 文件根目录
        //手机文件保存路径
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            outDirPath = Environment.getExternalStorageDirectory().toString();
        } else {
            outDirPath = Environment.getDataDirectory().toString();
        }
        File outDir = new File(outDirPath + PATH_JSON);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        return new File(outDir, fileName);
    }

    // 判断文件是否存在
    public static boolean judeFileExists(File file) {

        if (file.exists()) {
           return true;
        } else {
            return false;
        }
    }

    // 获得根目录
    public static String getRootPath(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String outDirPath = Environment.getExternalStorageDirectory().toString();
            return outDirPath;
        } else {
            String outDirPath = Environment.getDataDirectory().toString();
            return outDirPath;
        }
    }

    // 获取本地json数据
    public static String getFileString(String outFilePath) {
        try {
            File urlFile = new File(outFilePath);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String mimeTypeLine = null;
            StringBuffer sb = new StringBuffer();
            while ((mimeTypeLine = br.readLine()) != null) {
                sb.append(mimeTypeLine);
            }
            return sb.toString();
        } catch (Exception e) {
        }
        return "";
    }
/*    *//**
     * 将文件转成base64 字符串
     * @param path文件路径
     * @return  *
     * @throws Exception
     *//*

    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);;
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }*/
    //读取本地json生成json字符串
    public static String initJsonData(Context mContext) {
        String mJson = "";
        try {
            InputStream is = mContext.getResources().getAssets().open("地区json.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            mJson = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mJson;
    }

    // 将json数据写入本地文本文件中
    public static void writeTxtToFile(String strcontent, File file) {
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
        }
    }
}
