package com.project.dyuapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.project.dyuapp.activity.SkillDetailsActivity;
import com.project.dyuapp.activity.SystemMsgActivity;
import com.project.dyuapp.activity.VideoDetailsActivity;
import com.project.dyuapp.entity.JpushEntity;
import com.project.dyuapp.myutils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * @describe：
 * @author：刘晓丽
 * @createdate：2017/11/17 15:17
 */

public class MyJpushReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";
    private NotificationManager nm;
    private JpushEntity bean;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        printBundle(bundle);
        Log.e(TAG, "==极光推送==" + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.e(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "接受到推送下来的通知");
            receivingNotification(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.e(TAG, "用户点击打开了通知");
            openNotification(context, bundle);
        } else {
            Log.e(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.e(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.e(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.e(TAG, "extras : " + extras);

        EventBus.getDefault().post(0);
    }

    private void openNotification(Context context, Bundle bundle) {
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.e(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.e(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.e(TAG, "extras : " + extras);
        Intent intent = null;


        Gson gson = new Gson();
        bean = gson.fromJson(extras, JpushEntity.class);

        if (bean.getType().equals("video")) {
            //视频
            ToastUtils.getInstance(context).showMessage("视频");
            intent = new Intent(context, VideoDetailsActivity.class);
            intent.putExtra("article_id", bean.getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else if (bean.getType().equals("forum")) {
            //帖子
//            ToastUtils.getInstance(context).showMessage("帖子");
            intent = new Intent(context, SkillDetailsActivity.class);
            intent.putExtra("id", bean.getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else if (bean.getType().equals("notice")) {
            //公告
            ToastUtils.getInstance(context).showMessage("公告");
        }

//        if (bean.getMesType().equals("1")) {
//            intent = new Intent(context, SystemMsgActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        } else if (bean.getMesType().equals("2")) {
//            intent = new Intent(context, SkillDetailsActivity.class);
//            intent.putExtra("id", bean.getF_id());
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        } else {
//
//        }
    }

    // 打印所有的 intent extra 数据
    private String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.e(TAG, "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                        if (myKey.equals("txt")) {
                            try {
                                Gson gson = new Gson();
                                bean = gson.fromJson(json.optString(myKey), JpushEntity.class);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}