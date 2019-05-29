package com.project.dyuapp.myutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.LivingPlaceBean;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myviews.CycleWheelView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * @author 任伟
 * @describtion 地址选择器
 * @created at 2017/8/19 0019
 */

public class PopSelectAddress {

    private Context mContext; // 上下文对象
    private Window mWindow; // 当前Window
    private String selProvince, selCity, selZone; // 默认地址
    private String defaultProvince, defaultCity, defaultZone; // 默认地址
    private CallBackAddress callBack; // 会调接口对象
    private CycleWheelView cwvProvince, cwvCity,cwvZone; // 选择器控件

    // 解析后的省市区
    private List<String> provinceList = new ArrayList<>(); // 省
    private List<String> cityListStr = new ArrayList<>(); // 市
    private List<String> countyListStr = new ArrayList<>(); // 县
    private Map<String, ArrayList<String>> countyList = new HashMap();  // 市
    private Map<String, ArrayList<String>> cityList = new HashMap();  // 区

    // 所有市，区的集合
    private List<String> countyStrList = new ArrayList<>(); // 市
    private List<String> cityStrList = new ArrayList<>(); // 区

    // 省市区的代码编号集合
    private List<String> provinceCode = new ArrayList<>();
    private List<String> cityCode = new ArrayList<>();
    private List<String> countyCode = new ArrayList<>();

    private Map<String, String> countyListCode = new HashMap();  // 市
    private Map<String, String> cityListCode = new HashMap();  // 区

    String outFilePath; // 文件真实路径
    String addressJson = ""; // 本地json数据
    boolean isFirstProvince = true; // 记录是否首次加载
    boolean isFirstCity = true; // 记录是否首次加载

    /**
     * 创建地址选择器
     *
     * @param mContext    上下文对象
     * @param mWindow     当前页面Window
     * @param selProvince 默认省份
     * @param selCity     默认市
     * @param selZone     默认区县
     */
    public PopSelectAddress(Context mContext, Window mWindow, String selProvince, String selCity, String selZone) {
        this.mContext = mContext;
        this.mWindow = mWindow;
        this.defaultProvince = selProvince;
        this.defaultCity = selCity;
        this.defaultZone = selZone;
    }

    // 创建回调
    public void setCallBack(CallBackAddress callBack) {
        this.callBack = callBack;
    }

    public interface CallBackAddress {
        void onClickOk(String selProvince, String selCity, String selZone,String selProvinceCode, String selCityCode, String selZoneCode);

       // void onClickCode(String selProvinceCode, String selCityCode, String selZoneCode);

    }

    // 网络获取方式初始化地址集合
    public void showHttpAddress() {
        // 网络获取数据
        fillInInformationHttp();
        // 判断本地是否下载过数据
//        outFilePath = FileUtils.getRootPath() + PATH_JSON + JSON_NAME;
//        if (!FileUtils.judeFileExists(new File(outFilePath))) {
//            // 网络获取数据
//            fillInInformationHttp();
//        } else {
//            // 获取本地数据
//            addressJson = FileUtils.getFileString(outFilePath);
//            LivingPlaceBean livingPlaceBean = null;
//            try {
//                Gson gson = new Gson();
//                livingPlaceBean = gson.fromJson(addressJson,LivingPlaceBean.class);// 将字符串转换成实体类
//                // 加载数据,显示地址选择器
//                initAddressDate(livingPlaceBean);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    // 本地加载方式初始化地址集合
    public void showLocalAddress() {
        try {
            // 获取省份集合
            JSONArray jsonArray = new JSONArray(FileUtils.initJsonData(mContext));
            provinceList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
                String province = jsonP.getString("region_name");// 省名字

                provinceList.add(province);
                provinceCode.add(jsonP.getString("region_id"));

                JSONArray jsonCs = null;
                jsonCs = jsonP.getJSONArray("sub");

                ArrayList<String> mCitiesDatas = new ArrayList<>();// 当前省的所有市
                for (int j = 0; j < jsonCs.length(); j++) {

                    JSONObject jsonCity = jsonCs.getJSONObject(j);
                    String city = jsonCity.getString("region_name");// 市名字
                    mCitiesDatas.add(city);
                    cityCode.add(jsonCity.getString("region_id"));
                    countyStrList.add(city);

                    JSONArray jsonAreas = null;
                    jsonAreas = jsonCity.getJSONArray("sub");

                    ArrayList<String> mAreasDatas = new ArrayList<>();// 当前市的所有区
                    for (int k = 0; k < jsonAreas.length(); k++) {
                        String area = jsonAreas.getJSONObject(k).getString("region_name");// 区域的名称
                        mAreasDatas.add(area);
                        countyCode.add(jsonAreas.getJSONObject(k).getString("region_id"));
                        cityStrList.add(area);
                    }
                    cityList.put(city, mAreasDatas);
                }
                countyList.put(province, mCitiesDatas);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 显示弹窗
        backgroundAlpha(0.8f);
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_address, null);
        final PopupWindow popAddress = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 完成按钮
        TextView tvOk = (TextView) view.findViewById(R.id.pop_address_ok);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //完成
                popAddress.dismiss();
                callBack.onClickOk(selProvince,
                        selCity,
                        selZone,
                        provinceCode.get(provinceList.indexOf(selProvince)),
                        countyStrList.indexOf(selCity) == -1 ? provinceCode.get(provinceList.indexOf(selProvince)) : cityCode.get(countyStrList.indexOf(selCity)),
                        cityStrList.indexOf(selZone) == -1 ? provinceCode.get(provinceList.indexOf(selProvince)) : countyCode.get(cityStrList.indexOf(selZone)));
//                callBack.onClickCode(
//                        provinceCode.get(provinceList.indexOf(selProvince))
//                        , countyStrList.indexOf(selCity) == -1 ? provinceCode.get(provinceList.indexOf(selProvince)) : cityCode.get(countyStrList.indexOf(selCity))
//                        , cityStrList.indexOf(selZone) == -1 ? provinceCode.get(provinceList.indexOf(selProvince)) : countyCode.get(cityStrList.indexOf(selZone)));
            }
        });
        //控件初始化
        cwvProvince = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_province);
        cwvCity = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_city); //市
        cwvZone = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_zone); //区
        //设置省的数据
        cwvProvince.setLabels(provinceList);

        if (!TextUtils.isEmpty(defaultProvince)) {
            cwvProvince.setSelection(provinceList.indexOf(defaultProvince));
        } else {
            cwvProvince.setSelection(0);
            selProvince = cwvProvince.getSelectLabel();
        }
        //省滚动监听
        cwvProvince.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, String label) {
                selProvince = label;
                cwvCity.setLabels(countyList.get(cwvProvince.getSelectLabel()));
                cwvZone.setLabels(cityList.get(cwvCity.getSelectLabel()));
                // 首次加载时判断是否有默认地址
                if (isFirstProvince) {
                    isFirstProvince = false;
                    if (!TextUtils.isEmpty(defaultCity)) {
                        cwvCity.setSelection(countyList.get(defaultProvince).indexOf(defaultCity));
                    } else {
                        cwvCity.setSelection(0);
                        selCity = cwvCity.getSelectLabel();
                    }

                    if (!TextUtils.isEmpty(defaultZone)) {
                        cwvZone.setSelection(cityList.get(defaultCity).indexOf(defaultZone));
                    } else {
                        cwvZone.setSelection(0);
                        selZone = cwvZone.getSelectLabel();
                    }
                } else {
                    cwvCity.setSelection(0);
                    cwvZone.setSelection(0);
                    selCity = cwvCity.getSelectLabel();
                    selZone = cwvZone.getSelectLabel();
                }
            }
        });
        //设置市的数据
        cityListStr = countyList.get(cwvProvince.getSelectLabel());
        cwvCity.setLabels(cityListStr);
        //市滚动监听
        cwvCity.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, String label) {
                selCity = label;
                cwvZone.setLabels(cityList.get(cwvCity.getSelectLabel()));
                if (isFirstCity) {
                    isFirstCity = false;
                    if (!TextUtils.isEmpty(defaultZone)) {
                        cwvZone.setSelection(cityList.get(defaultCity).indexOf(defaultZone));
                    } else {
                        cwvZone.setSelection(0);
                        selZone = cwvZone.getSelectLabel();
                    }
                } else {
                    cwvZone.setSelection(0);
                    selZone = cwvZone.getSelectLabel();
                }

            }
        });
        //设置区的数据
        countyListStr = cityList.get(cwvCity.getSelectLabel());
        cwvZone.setLabels(countyListStr);
        //区滚动监听
        cwvZone.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, String label) {
                selZone = label;
            }
        });
        popAddress.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        popAddress.setOutsideTouchable(true);
        popAddress.setBackgroundDrawable(new BitmapDrawable());
        popAddress.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 网络获取
     */
    public void fillInInformationHttp() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.COMMON_GETCITYS);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.LivingPlaceBeanList>((Activity) mContext) {
            @Override
            public void onSuccess(NetBean.LivingPlaceBeanList data, int d) {
                super.onSuccess(data, d);
                initAddressDate(data);
            }

            @Override
            public void onOther(JsonResult<NetBean.LivingPlaceBeanList> data, int d) {
                super.onOther(data, d);
                ToastUtils.getInstance(mContext).showMessage(data.getMessage());
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }
        });
        commonOkhttp.Execute();
    }

    // 加载数据,显示地址选择器
    private void initAddressDate(NetBean.LivingPlaceBeanList bean) {
        try {
            provinceList = new ArrayList<>();
            for (int i = 0; i < bean.size(); i++) {
                String province = bean.get(i).getP();// 省名字
                provinceList.add(province);
                provinceCode.add(bean.get(i).getCid());
                countyListCode.put(province,bean.get(i).getCid());

                List<LivingPlaceBean.CBean> jsonCs = null;

                jsonCs = bean.get(i).getC();

                ArrayList<String> mCitiesDatas = new ArrayList<>();// 当前省的所有市
                for (int j = 0; j < jsonCs.size(); j++) {


                    String city = jsonCs.get(j).getN();// 市名字
                    mCitiesDatas.add(city);
                    countyStrList.add(city);
                    cityCode.add(jsonCs.get(j).getCid());
                    cityListCode.put(city,jsonCs.get(j).getCid());

//                    List<LivingPlaceBean.DataBean.SAreaBeanX.SAreaBean> jsonAreas = null;
//                    jsonAreas = jsonCs.get(j).getSArea();
//
//                    ArrayList<String> mAreasDatas = new ArrayList<>();// 当前市的所有区
//                    for (int k = 0; k < jsonAreas.size(); k++) {
//                        String area = jsonAreas.get(k).getName();// 区域的名称
//                        mAreasDatas.add(area);
//                        countyCode.add(jsonAreas.get(k).getID());
//                        cityStrList.add(area);
//                    }
//                    cityList.put(city, mAreasDatas);
                }
                countyList.put(province, mCitiesDatas);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 显示弹窗
        backgroundAlpha(0.8f);
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_address, null);
        final PopupWindow popAddress = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 完成按钮
        TextView tvOk = (TextView) view.findViewById(R.id.pop_address_ok);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //完成
                popAddress.dismiss();
                callBack.onClickOk(selProvince, selCity, "",provinceCode.get(provinceList.indexOf(selProvince))
                        , cityListCode.get(selCity), "");
//                callBack.onClickCode(
//                        provinceCode.get(provinceList.indexOf(selProvince))
//                        , cityListCode.get(selCity), "");

            }
        });
        //控件初始化
        cwvProvince = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_province);
        cwvCity = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_city); //市
//        cwvZone = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_zone); //区
        //设置省的数据
        cwvProvince.setLabels(provinceList);

        if (!TextUtils.isEmpty(defaultProvince)) {
            cwvProvince.setSelection(provinceList.indexOf(defaultProvince));
        } else {
            cwvProvince.setSelection(0);
            selProvince = cwvProvince.getSelectLabel();
        }
        //省滚动监听
        cwvProvince.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, String label) {
                selProvince = label;
                cwvCity.setLabels(countyList.get(cwvProvince.getSelectLabel()));
                // 首次加载时判断是否有默认地址
                if (isFirstProvince) {
                    isFirstProvince = false;
                    if (!TextUtils.isEmpty(defaultCity)) {
                        cwvCity.setSelection(countyList.get(defaultProvince).indexOf(defaultCity));
                    } else {
                        cwvCity.setSelection(0);
                    }
                } else {
                    cwvCity.setSelection(0);
                }
                selCity = cwvCity.getSelectLabel();
            }
        });
        //设置市的数据
        cityListStr = countyList.get(cwvProvince.getSelectLabel());
        cwvCity.setLabels(cityListStr);
        //市滚动监听
        cwvCity.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, String label) {
                selCity = label;

            }
        });
//        //设置区的数据
//        countyListStr = cityList.get(cwvCity.getSelectLabel());
//        cwvZone.setLabels(countyListStr);
//        //区滚动监听
//        cwvZone.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
//            @Override
//            public void onItemSelected(int position, String label) {
//                selZone = label;
//            }
//        });
        popAddress.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        popAddress.setOutsideTouchable(true);
        popAddress.setBackgroundDrawable(new BitmapDrawable());
        popAddress.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    //设置屏幕透明度
    public void backgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.alpha = alpha; //0.0-1.0
        mWindow.setAttributes(lp);
    }
}
