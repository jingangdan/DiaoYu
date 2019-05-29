package com.project.dyuapp.runtimepermissions;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

/**
 * 6.0权限
 */
public class PermissionUtils {

    private static PermissionsManager pm = PermissionsManager.getInstance();

    public interface OnPermissionResult {
        void onGranted();
//        void onDenied();
    }

    private static void permission(final Activity activity, final String permission, final String permissionExplain, final OnPermissionResult callback) {
        if (pm.hasPermission(activity, permission)) {
            callback.onGranted();
            return;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            new AlertDialog.Builder(activity)
                    .setTitle(permissionExplain)
                    .setPositiveButton("给予权限", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermission(activity, permission, callback);

                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
            return;
        }
        requestPermission(activity, permission, callback);
    }

    public static void multiplePermission(final Activity activity, final String[] permission, final String permissionExplain, final OnPermissionResult callback) {
        for (String permissionItem : permission) {
            if (pm.hasPermission(activity, permissionItem)) {
                callback.onGranted();
                return;
            }
        }

        for (String permissionItem : permission) {
            final String permissionItem1 = permissionItem;
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionItem)) {
                new AlertDialog.Builder(activity)
                        .setTitle(permissionExplain)
                        .setPositiveButton("给予权限", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermission(activity, permissionItem1, callback);

                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                return;
            }
        }

        requestMultiplePermission(activity, permission, callback);
    }

    private static void requestMultiplePermission(final Activity activity, String[] permission, final OnPermissionResult callback) {
        Log.d("*-*", "requesting");
        pm.requestPermissionsIfNecessaryForResult(activity, permission, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                if (callback != null) {
                    callback.onGranted();
                }
            }

            @Override
            public void onDenied(String permission) {
                Toast.makeText(activity, "您拒绝了权限申请，无法启动相关组件", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void requestPermission(final Activity activity, String permission, final OnPermissionResult callback) {
        Log.d("*-*", "requesting");
        pm.requestPermissionsIfNecessaryForResult(activity, new String[]{permission}, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                if (callback != null) {
                    callback.onGranted();
                }
            }

            @Override
            public void onDenied(String permission) {
                Toast.makeText(activity, "您拒绝了权限申请，无法启动相关组件", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void phone(final Activity activity, final OnPermissionResult callback) {
        permission(
                activity,
                Manifest.permission.CALL_PHONE,
                "您需要同意电话权限以使用相关功能",
                callback
        );
    }

    public static void storage(final Activity activity, final OnPermissionResult callback) {
        permission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                "您需要同意内存卡权限以使用相关功能",
                callback
        );
    }

    public static void contacts(final Activity activity, final OnPermissionResult callback) {
        permission(
                activity,
                Manifest.permission.GET_ACCOUNTS,
                "您需要同意联系人权限以使用相关功能",
                callback
        );
    }

    public static void camera(final Activity activity, final OnPermissionResult callback) {
        permission(
                activity,
                Manifest.permission.CAMERA,
                "您需要同意相机权限以使用相关功能",
                callback
        );
    }

    public static void cameraAndStorage(final Activity activity, final OnPermissionResult callback) {
        multiplePermission(
                activity,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                "您需要同意相机权限以使用相关功能",
                callback
        );
    }

    public static void location(final Activity activity, final OnPermissionResult callback) {
//        permission(
//                activity,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                "您需要同意定位权限以使用相关功能",
//                callback
//        );
        multiplePermission(
                activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                "您需要同意定位权限以使用相关功能",
                callback
        );
    }

    public static void recordAudio(final Activity activity, final OnPermissionResult callback) {
        permission(
                activity,
                Manifest.permission.RECORD_AUDIO,
                "您需要同意录音权限以使用相关功能",
                callback
        );
    }
}
