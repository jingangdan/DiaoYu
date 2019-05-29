package com.project.dyuapp.shop;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Created by jingang on 2016/8/12 0012.
 * Gson json 解析
 */

public class GsonUtils {
    private static Gson sGson = null;
    private static GsonUtils sGsonUtil = null;
    public static GsonUtils gsonIntance(){
        if (sGsonUtil == null){
            sGsonUtil = new GsonUtils();
        }
        if (sGson == null){
            sGson = new Gson();
        }
        return sGsonUtil;
    }
    /**
     * 转成json
     * */
    public static String gsonToString(Object object){
        sGson = new Gson();
        String gsonString = null;
        gsonString = sGson.toJson(object);
        Log.i("GSONUtils测试","gsonToString:"+gsonString);
        return gsonString;
    }
    /**
     * 转成bean
     * */
    public <T> T gsonToBean(String gsonString, Class<T> cls){
        sGson = new Gson();
        T t = null;
        t = sGson.fromJson(gsonString,cls);
        return t;
    }
    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     * 解决泛型问题
     * */
    public <T>List<T> gsonToListBean(String string,Class<T> cls){
        sGson = new Gson();
        List<T> list = null;
        JsonArray array = new JsonParser().parse(string).getAsJsonArray();
        for (JsonElement element : array){
            list.add(sGson.fromJson(element,cls));
        }
//        list = sGson.fromJson(string, new TypeToken<List<T>>(){
//        }.getType());

        return list;
    }
    /**
     * list中有map
     * */
    public <T> List<Map<String ,T>> gsonToListMap(String json){
        sGson = new Gson();
        List<Map<String,T>> list = null;
        list = sGson.fromJson(json,new TypeToken<List<Map<String,T>>>(){}.getType());
        return list;
    }
    /**
     * 转成map的
     * */
    public <T> Map<String, T> GsonToMaps(String gsonString) {
        sGson = new Gson();
        Map<String, T> map = null;
        map = sGson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }
}
