package com.project.dyuapp.appupdate;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

/**
 * Created by jingang on 2016/05/18
 */
public class ApkUpdateUtils {
    public static final String TAG = ApkUpdateUtils.class.getSimpleName();

    private static final String KEY_DOWNLOAD_ID = "downloadId";

    public static void download(Context context, String url, String title) {
        long downloadId = SpUtils.getInstance(context).getLong(KEY_DOWNLOAD_ID, -1L);
        if (downloadId != -1L) {
            System.out.println("11111 downloadId != -1L 已经下载过了 执行安装或者是重新下载");
            FileDownloadManager fdm = FileDownloadManager.getInstance(context);
            int status = fdm.getDownloadStatus(downloadId);
            Uri uri = fdm.getDownloadUri(downloadId);
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                System.out.println("11111 状态 成功 执行安装");
                //启动更新界面
                if (uri != null) {
                    if (compare(getApkInfo(context, uri.getPath()), context)) {
                        startInstall(context, uri);
                        return;
                    } else {
                        fdm.getDm().remove(downloadId);
                    }
                }
                start(context, url, title);
            } else if (status == DownloadManager.STATUS_FAILED) {
                System.out.println("11111 状态 失败 执行下载");
                start(context, url, title);
            } else {
                //Log.d(TAG, "apk is already downloading 直接安装");
                System.out.println("11111 apk is already downloading 直接安装");

            }
        } else {
            System.out.println("11111 未下载过 执行下载");
            start(context, url, title);
        }
    }

    private static void start(Context context, String url, String title) {
        long id = FileDownloadManager.getInstance(context).startDownload(url,
                title, "下载完成后点击打开");
        SpUtils.getInstance(context).putLong(KEY_DOWNLOAD_ID, id);
        //Log.d(TAG, "apk start download " + id);
        System.out.println("11111 apk start download " + id);

    }

    public static void startInstall(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            System.out.println("11111 andorid7.0");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            System.out.println("11111 no android7.0");
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            System.out.println("11111 安装走起");
            context.startActivity(intent);
        }

    }


    /**
     * 获取apk程序信息[packageName,versionName...]
     *
     * @param context Context
     * @param path    apk path
     */
    private static PackageInfo getApkInfo(Context context, String path) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            return info;
        }
        return null;
    }


    /**
     * 下载的apk和当前程序版本比较
     *
     * @param apkInfo apk file's packageInfo
     * @param context Context
     * @return 如果当前应用版本小于apk的版本则返回true
     */
    private static boolean compare(PackageInfo apkInfo, Context context) {
        if (apkInfo == null) {
            return false;
        }
        String localPackage = context.getPackageName();
        if (apkInfo.packageName.equals(localPackage)) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(localPackage, 0);
                if (apkInfo.versionCode > packageInfo.versionCode) {
                    return true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}


