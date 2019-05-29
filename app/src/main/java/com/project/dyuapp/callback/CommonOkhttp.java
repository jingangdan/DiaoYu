package com.project.dyuapp.callback;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.project.dyuapp.R;
import com.project.dyuapp.entity.UpLoadFileEntity;
import com.project.dyuapp.myutils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gengqiufang
 * @describtion 公共网络请求
 * @created at 2017/5/9 0009
 * change lingong
 */
public class CommonOkhttp {
    private HashMap<String, String> map;
    private ArrayList<UpLoadFileEntity> entityArrayList;
    private MyGenericsCallback callback;
    private String url;
    private boolean isContainFile;
    private boolean isParams;

    public CommonOkhttp() {}

    public void putUrl(String url) {
        this.url = url;
    }

    public void putParams(String key, String value) {
        if (!isParams){
            isParams=true;
            map = new HashMap<>();
        }
        map.put(key, value);
    }

    public void putFile(String name, String filename, File file) {
        if (!isContainFile) {
            isContainFile = true;
            entityArrayList = new ArrayList<>();
        }
        entityArrayList.add(new UpLoadFileEntity(name, filename, file));
    }

    public void putCallback(MyGenericsCallback callback) {
        this.callback = callback;
    }

    public void Execute() {
        PostFormBuilder builder = OkHttpUtils.post();
        builder.url(url);
        if (isParams){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (TextUtils.isEmpty(entry.getKey())) {
                    Log.e("===commonOkhttp", "存在入参名为空");
                } else if (TextUtils.isEmpty(entry.getValue())) {
                    Log.e("===commonOkhttp", "存在入参值为空");
                    builder.addParams(entry.getKey(), "");
                } else {
                    builder.addParams(entry.getKey(), entry.getValue());
                }
            }
            Log.e("OkHttpLogTAG==入参", new Gson().toJson(map));
        }
        if (isContainFile) {
            for (UpLoadFileEntity item : entityArrayList) {
                builder.addFile(item.getName(), item.getFileName(), item.getFile());
            }
            Log.e("OkHttpLogTAG==入参", new Gson().toJson(entityArrayList));
        }
        builder.build().execute(callback);
    }

    public void showNoData(Context context, int page) {
        if (page == 1) {
            ToastUtils.getInstance(context).showMessage(context.getString(R.string.list_no_data));
        } else {
            ToastUtils.getInstance(context).showMessage(context.getString(R.string.list_bottom));
        }
    }
}
