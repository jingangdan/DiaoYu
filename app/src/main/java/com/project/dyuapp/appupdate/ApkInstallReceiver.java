package com.project.dyuapp.appupdate;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

public class ApkInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long downloadApkId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            long id = SpUtils.getInstance(context).getLong("downloadId", -1L);
            if (downloadApkId == id) {
                System.out.println("22222 downloadApkId == id");
                installApk(context, downloadApkId);
            }
        }
    }

//    private void installAPK() {
//        File apkFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), apkName);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Uri apkUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", apkFile);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//        } else {
//            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
//        }
//        mContext.startActivity(intent);
//    }

    private static void installApk(Context context, long downloadApkId) {
        System.out.println("22222 安装apk");

        Intent install = new Intent(Intent.ACTION_VIEW);
        DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadFileUri = dManager.getUriForDownloadedFile(downloadApkId);
        if (downloadFileUri != null) {

//            install.setDataAndType(downloadFileUri,
//                    "application/vnd.android.package-archive");
//            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(install);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                System.out.println("22222 7.0" + downloadFileUri.toString());
                install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                install.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                Uri contentUri = FileProvider.getUriForFile(context,
//                        "com.bh.yibeitong.fileprovider",
//                        new File(String.valueOf(downloadFileUri)));
                install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
            } else {
                System.out.println("22222 no 7.0" + downloadFileUri.toString());
                install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            if (context.getPackageManager().queryIntentActivities(install, 0).size() > 0) {
                System.out.println("22222 安装走起");
                context.startActivity(install);
            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                // 适配android7.0 ，不能直接访问原路径
//                // 需要对intent 授权
//                Log.d("DownloadManager", "7.0" + downloadFileUri.toString());
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                i.setDataAndType(FileProvider.getUriForFile(context,
//                        context.getPackageName() + ".fileProvider",
//                        new File(String.valueOf(downloadFileUri))),
//                        "application/vnd.android.package-archive");
//
//            } else {
//                //i.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
//
//                Log.d("DownloadManager", "no 7.0" + downloadFileUri.toString());
//                install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
//                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(install);
//            }

        } else {
            System.out.println("22222 下载失败");
            //Log.e("DownloadManager", "下载失败");
        }
    }
}