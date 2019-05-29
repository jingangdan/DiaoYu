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
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.LIvingPlaceAllEntity;
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
 * @describtion 地址选择器（省市县三级）
 * @created at 2017/8/19 0019
 */

public class PopSelectAddress_3 {

    private Context mContext; // 上下文对象
    private Window mWindow; // 当前Window
    private String selProvince, selCity, selZone; // 默认地址
    private String defaultProvince, defaultCity, defaultZone; // 默认地址
    private CallBackAddress callBack; // 会调接口对象
    private CycleWheelView cwvProvince, cwvCity, cwvZone; // 选择器控件

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

    private Map<String, String> provinceListCode = new HashMap();  // 省
    private Map<String, String> cityListCode = new HashMap();  // 市
    private Map<String, String> countyListCode = new HashMap();  // 区

    String outFilePath; // 文件真实路径
    String addressJson = ""; // 本地json数据
    boolean isFirstProvince = true; // 记录是否首次加载
    boolean isFirstCity = true; // 记录是否首次加载

    boolean isFirstP = true;// 是否首次加载省份
    boolean isFirstC = true;// 是否首次加载市
    boolean isFirstZ = true;// 是否首次加载区

    /**
     * 创建地址选择器
     *
     * @param mContext    上下文对象
     * @param mWindow     当前页面Window
     * @param selProvince 默认省份
     * @param selCity     默认市
     * @param selZone     默认区县
     */
    public PopSelectAddress_3(Context mContext, Window mWindow, String selProvince, String selCity, String selZone) {
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
        void onClickOk(String selProvince, String selCity, String selZone);

        void onClickCode(String selProvinceCode, String selCityCode, String selZoneCode);
    }

    // 网络获取方式初始化地址集合
    public void showHttpAddress() {

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
                callBack.onClickOk(selProvince, selCity, selZone);
                callBack.onClickCode(
                        provinceListCode.get(selProvince), cityListCode.get(selCity), countyListCode.get(selZone));

            }
        });

        //控件初始化
        cwvProvince = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_province);
        cwvCity = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_city); //市
        cwvZone = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_zone); //区
        cwvZone.setVisibility(View.VISIBLE);

        popAddress.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        popAddress.setOutsideTouchable(true);
        popAddress.setBackgroundDrawable(new BitmapDrawable());
        popAddress.showAtLocation(view, Gravity.BOTTOM, 0, 0);


        // 网络获取数据
        fillInInformationHttp("", 1);
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
                callBack.onClickOk(selProvince, selCity, selZone);
                callBack.onClickCode(
                        provinceCode.get(provinceList.indexOf(selProvince))
                        , countyStrList.indexOf(selCity) == -1 ? provinceCode.get(provinceList.indexOf(selProvince)) : cityCode.get(countyStrList.indexOf(selCity))
                        , cityStrList.indexOf(selZone) == -1 ? provinceCode.get(provinceList.indexOf(selProvince)) : countyCode.get(cityStrList.indexOf(selZone)));

            }
        });
        //控件初始化
        cwvProvince = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_province);
        cwvCity = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_city); //市
        cwvZone = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_zone); //区
        cwvZone.setVisibility(View.VISIBLE);
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
     *
     * @param id   父id
     * @param type 类型 1省 2市 3区
     */
    public void fillInInformationHttp(String id, final int type) {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.COMMON_GET_CITYS);
        commonOkhttp.putParams("pid", id);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.LivingPlaceSelectListEntity>((Activity) mContext, false) {
            @Override
            public void onSuccess(final NetBean.LivingPlaceSelectListEntity data, int d) {
                super.onSuccess(data, d);
                initAddressDate(data, type);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 分别加载省市县三级地区
     *
     * @param data 实体类
     * @param type 类型 1省 2市 3区
     */
    private void initAddressDate(final NetBean.LivingPlaceSelectListEntity data, int type) {
        if (type == 1) {
            try {
                provinceList = new ArrayList<>();
                cleanList(0);//清空数据
                for (int i = 0; i < data.size(); i++) {
                    String province = data.get(i).getName();// 省名字
                    provinceList.add(province);
                    provinceListCode.put(province, data.get(i).getCityid());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            //设置省的数据
            cwvProvince.setLabels(provinceList);
            //显示默认省份
            if (!TextUtils.isEmpty(defaultProvince) && isFirstP) {
                cwvProvince.setSelection(provinceList.indexOf(defaultProvince));
                selProvince = cwvProvince.getSelectLabel();
                isFirstP = false;
            } else {
                cwvProvince.setSelection(0);
                selProvince = cwvProvince.getSelectLabel();
                fillInInformationHttp(data.get(0).getCityid(), 2);
            }
            //省滚动监听
            cwvProvince.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
                @Override
                public void onItemSelected(int position, String label) {
                    selProvince = label;
                    if (data.size() > 0)
                        fillInInformationHttp(data.get(position).getCityid(), 2);
                }
            });
        } else if (type == 2) {
            cleanList(1);//清空数据
            for (int j = 0; j < data.size(); j++) {
                String city = data.get(j).getName();// 市名字
                countyStrList.add(city);
                cityListCode.put(city, data.get(j).getCityid());
            }
            //设置市的数据
            cwvCity.setLabels(countyStrList);

            //显示默认市
            if (!TextUtils.isEmpty(defaultCity)&&isFirstC) {
                cwvCity.setSelection(countyStrList.indexOf(defaultCity));
                selCity = cwvCity.getSelectLabel();
                isFirstC = false;
            } else {
                cwvCity.setSelection(0);
                selCity = cwvCity.getSelectLabel();
                //判断当前省份是否有下级市
                if (data.size() > 0)
                    fillInInformationHttp(data.get(0).getCityid(), 3);
            }

            //市滚动监听
            cwvCity.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
                @Override
                public void onItemSelected(int position, String label) {
                    selCity = label;
                    fillInInformationHttp(data.get(position).getCityid(), 3);
                }
            });
        } else {
            cleanList(2);//清空数据
            for (int k = 0; k < data.size(); k++) {
                String area = data.get(k).getName();// 区域的名称
                cityStrList.add(area);
                countyListCode.put(area, data.get(k).getCityid());
            }

            //设置区的数据
            cwvZone.setLabels(cityStrList);

            if (!TextUtils.isEmpty(defaultZone) && isFirstZ) {
                cwvZone.setSelection(cityStrList.indexOf(defaultZone));
                selZone = cwvZone.getSelectLabel();
                isFirstZ = false;
            } else {
                cwvZone.setSelection(0);
                if (data.size() > 0)
                    selZone = cwvZone.getSelectLabel();
            }
            //区滚动监听
            cwvZone.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
                @Override
                public void onItemSelected(int position, String label) {
                    selZone = label;
                }
            });
        }
    }

    /**
     * 省市县同时加载
     *
     * @param bean 实体类
     */
    private void initAddressDate(NetBean.LivingPlaceAllListEntity bean) {
        try {
            provinceList = new ArrayList<>();
            for (int i = 0; i < bean.size(); i++) {
                String province = bean.get(i).getP();// 省名字
                provinceList.add(province);
                provinceCode.add(bean.get(i).getCid());
                provinceListCode.put(province, bean.get(i).getCid());

                List<LIvingPlaceAllEntity.CBean> jsonCs = null;
                jsonCs = bean.get(i).getC();

                ArrayList<String> mCitiesDatas = new ArrayList<>();// 当前省的所有市
                for (int j = 0; j < jsonCs.size(); j++) {

                    String city = jsonCs.get(j).getN();// 市名字
                    mCitiesDatas.add(city);
                    cityCode.add(jsonCs.get(j).getCid());
                    countyStrList.add(city);
                    cityListCode.put(city, jsonCs.get(j).getCid());

                    List<LIvingPlaceAllEntity.CBean.ABean> jsonAreas = null;
                    jsonAreas = jsonCs.get(j).getA();

                    ArrayList<String> mAreasDatas = new ArrayList<>();// 当前市的所有区
                    for (int k = 0; k < jsonAreas.size(); k++) {
                        String area = jsonAreas.get(k).getS();// 区域的名称
                        mAreasDatas.add(area);
                        countyCode.add(jsonAreas.get(k).getCid());
                        cityStrList.add(area);
                        countyListCode.put(area, jsonCs.get(j).getCid());
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
                callBack.onClickOk(selProvince, selCity, selZone);
                callBack.onClickCode(
                        provinceCode.get(provinceList.indexOf(selProvince)), cityListCode.get(selCity), countyListCode.get(selZone));

            }
        });
        //控件初始化
        cwvProvince = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_province);
        cwvCity = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_city); //市
        cwvZone = (CycleWheelView) view.findViewById(R.id.pop_address_cwv_zone); //区
        cwvZone.setVisibility(View.VISIBLE);
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

    //设置屏幕透明度
    public void backgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.alpha = alpha; //0.0-1.0
        mWindow.setAttributes(lp);
    }

    public void cleanList(int i) {
        switch (i) {
            case 0:
                provinceList.clear();
                provinceCode.clear();
                provinceListCode.clear();
                break;
            case 1:
                cityCode.clear();
                countyStrList.clear();
                cityListCode.clear();
            case 2:
                countyCode.clear();
                cityStrList.clear();
                countyListCode.clear();
                break;
        }
    }
}
