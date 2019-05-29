package com.project.dyuapp.callback;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.dyuapp.myviews.MyProgressDialog;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * okhttp
 *
 * @param <T> 实体bean
 * @change 2017/8/10 gqf
 */
public class MyGenericsCallback<T extends Object> extends Callback<JsonResult<T>> {

    private Activity activity; // 当前调用对应的activity
    private MyProgressDialog myProgressDialog;
    private boolean isShowLoading = true;

    private String flagMessage = "message";
    private String flagCode = "code";
    private String flagData = "data";

    public MyGenericsCallback(Activity activity) {
        this(activity, true);
    }

    public MyGenericsCallback(Activity activity, boolean isShowLoading) {
        this.activity = activity;
        this.isShowLoading = isShowLoading;
        if (isShowLoading && activity != null) {
            myProgressDialog = new MyProgressDialog(activity);
            myProgressDialog.show();
        }
    }


    @Override
    public JsonResult<T> parseNetworkResponse(Response response, int id) throws Exception {
        dismissDialog();
        try {
            String responseBody = response.body().string();
            Log.e("OkHttpLogTAG", getClass().getName() + responseBody);

            String message = "";
            int code = -1;
            String data = "";
            /*获取*/
            try {
                JSONObject o = new JSONObject(responseBody);
                message = o.getString(flagMessage);
                code = o.getInt(flagCode);
                data = o.getString(flagData);
            } catch (Exception e) {
                e.printStackTrace();
            }
             /*设置*/
            JsonResult j = JsonResult.class.newInstance();
            j.setCode(code);
            j.setMessage(message);
            /*解析data*/
            if (TextUtils.isEmpty(data)) {
                j.setData("");
            } else {
                if (data.substring(0, 1).equals("{") || data.substring(0, 1).equals("[")) {
                    Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                    Gson gson = new Gson();
                    T bean = gson.fromJson(data, entityClass);
                    j.setData(bean);
                } else {
                    j.setData(data);
                }
            }
            return j;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onError(Call call, Exception e, int id) {
        Log.e("===onError===", getClass().getName() + e.toString());
        dismissDialog();
//        Toast.makeText(activity.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        String str = "连接失败，请检查网络";
        if (e instanceof TimeoutException) {
            str = "连接超时";
        }
       if (e.toString().contains("404")){
           str = "404";
       }
        Toast.makeText(activity.getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JsonResult<T> response, int id) {
        if (response == null) {
            //Log.e("===response=null===", getClass().getName() + response);
            JsonResult<T> jsonResult = new JsonResult<>();
            jsonResult.setMessage("返回数据为空或解析失败");
            onOther(jsonResult, id);
        } else {
            if (response.getCode() == 0) {
                onSuccess(response.getData(), id);
                onSuccessMessage(response.getCode(), response.getMessage(), id);
            } else {
                onOther(response, id);
            }
        }
    }

    /**
     * 调用成功后执行方法 (包含data)
     *
     * @param data
     * @param d
     */
    public void onSuccess(T data, int d) {

    }

    /**
     * 调用成功后执行方法(包含messages、 code)
     *
     * @param code
     * @param message
     * @param d
     */
    public void onSuccessMessage(int code, String message, int d) {

    }

    /**
     * 调用成功，但返回非0 code 时执行方法
     *
     * @param d
     */
    public void onOther(JsonResult<T> data, int d) {
        //Toast.makeText(activity.getApplicationContext(), "code:" + data.getCode() + ",message:" + data.getMessage(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(activity.getApplicationContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void dismissDialog() {
        if (myProgressDialog != null && isShowLoading) {
            myProgressDialog.dismiss();
        }
    }
}
