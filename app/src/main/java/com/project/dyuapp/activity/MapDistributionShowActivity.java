package com.project.dyuapp.activity;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyApplacation;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.MarkerInfoUtil;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.ToastUtils;
import com.project.dyuapp.runtimepermissions.PermissionUtils;
import com.project.dyuapp.runtimepermissions.PermissionsManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author 田亭亭
 * @description 地图分布显示（钓场，渔具店）
 * @created at 2017/12/7 0007
 * @change
 */
public class MapDistributionShowActivity extends MyBaseActivity {

    @Bind(R.id.distribution_show_map_view)
    MapView distributionShowMapView;
    @Bind(R.id.distribution_show_ll_charge_type)
    LinearLayout llChargeType;
    private String whereFrom = "";
    private String countyid = "";
    private String city = "";
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private boolean isFirstLocation = true;
    public BDLocationListener myListener;
    private double lat;//纬度
    private double lon;//经度
    private List<MarkerInfoUtil> infos;
    private String catId = "";
    private String payType = "";
    private MapStatus mMapStatus;//定义地图状态
    private MapStatusUpdate mMapStatusUpdate;//定义地图状态更新
    private LatLng mLatLng;//点
    private Marker marker;
    private OverlayOptions options;
    private BitmapDescriptor bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_map_distribution_show);
        ButterKnife.bind(this);
        whereFrom = getIntent().getStringExtra("whereFrom");
        countyid = getIntent().getStringExtra("countyid");
        if (TextUtils.equals(whereFrom, "1")) {
            llChargeType.setVisibility(View.VISIBLE);
            setTvTitle("钓场");
            catId = getIntent().getStringExtra("cat_id");
            payType = getIntent().getStringExtra("pay_type");
            okhttpMapAngList();
        } else if (TextUtils.equals(whereFrom, "2")) {
            llChargeType.setVisibility(View.GONE);
            setTvTitle("渔具店");
            okhttpAllFishingShop();
        }
        setIvBack();
        //获取BaiduMap对象
        mBaiduMap = distributionShowMapView.getMap();
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myListener);
        //配置定位参数
        initLocation();
        //开始定位
        PermissionUtils.location(MapDistributionShowActivity.this, new PermissionUtils.OnPermissionResult() {
            @Override
            public void onGranted() {
                mLocationClient.start();
            }
        });


    }

    /**
     * 配置定位参数
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @OnClick(R.id.distribution_show_img_map_current)
    public void onViewClicked() {
        mLocationClient.requestLocation();
        double longitude = Double.parseDouble(PublicStaticData.sharedPreferences.getString("longitude", "0"));
        double latitude = Double.parseDouble(PublicStaticData.sharedPreferences.getString("latitude", "0"));
        mLatLng = new LatLng(latitude, longitude);
        mMapStatusUpdate = MapStatusUpdateFactory.newLatLng(mLatLng);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    /**
     * 实现定位监听 位置一旦有所改变就会调用这个方法
     * 可以在这个方法里面获取到定位之后获取到的一系列数据
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());

            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            lat = location.getLatitude();
            lon = location.getLongitude();
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            //这个判断是为了防止每次定位都重新设置中心点和marker
            if (isFirstLocation) {
                isFirstLocation = false;
                setUserMapCenter();
                if (TextUtils.equals(whereFrom, "1")) {
                    catId = getIntent().getStringExtra("cat_id");
                    payType = getIntent().getStringExtra("pay_type");
                    okhttpMapAngList();
                } else if (TextUtils.equals(whereFrom, "2")) {
                    okhttpAllFishingShop();
                }
            }
            Log.v("pcw", "lat : " + lat + " lon : " + lon);
            Log.i("BaiduLocationApiDem", sb.toString());

        }
    }

    /**
     * 设置中心点
     */
    private void setUserMapCenter() {
        Log.v("pcw", "setUserMapCenter : lat : " + lat + " lon : " + lon);
        mLatLng = new LatLng(lat, lon);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //定义地图状态
        mMapStatus = new MapStatus.Builder().target(mLatLng).zoom(17f).build();
        MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        int accuracyCircleFillColor = 0x00000000;//自定义精度圈填充颜色
        int accuracyCircleStrokeColor = 0x00000000;//自定义精度圈边框颜色
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(mCurrentMode, true, null, accuracyCircleFillColor, accuracyCircleStrokeColor));
        mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }

    /**
     * 所有渔具店分布接口
     */
    private void okhttpAllFishingShop() {
        if (PublicStaticData.sharedPreferences.getString("city", "").contains("市")) {
            city = PublicStaticData.sharedPreferences.getString("city", "").substring(0, PublicStaticData.sharedPreferences.getString("city", "").length() - 1);
        } else {
            city = PublicStaticData.sharedPreferences.getString("city", "");
        }
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.allfishing_shop);
        commonOkhttp.putParams("countyid", countyid);
        commonOkhttp.putParams("city", city);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.FishingGearListEntity>(this) {
            @Override
            public void onSuccess(NetBean.FishingGearListEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    infos = new ArrayList<MarkerInfoUtil>();
                    if (data != null && data.size() != 0) {
                        for (int i = 0; i < data.size(); i++) {
                            if (!TextUtils.isEmpty(data.get(i).getLatitude()) && !TextUtils.isEmpty(data.get(i).getLongitude())) {
                                infos.add(new MarkerInfoUtil(Double.parseDouble(data.get(i).getLatitude()),
                                        Double.parseDouble(data.get(i).getLongitude()), data.get(i).getShop_name(), data.get(i).getAddress()));
                            }
                        }
                        addOverlay(infos);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("=======解析失败==========" + e.getMessage());
                    ToastUtils.getInstance(MapDistributionShowActivity.this).showMessage(MapDistributionShowActivity.this.getResources().getString(R.string.data_parsing_failed));
                }
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 所有钓场分布接口
     */
    private void okhttpMapAngList() {
        if (PublicStaticData.sharedPreferences.getString("city", "").contains("市")) {
            city = PublicStaticData.sharedPreferences.getString("city", "").substring(0, PublicStaticData.sharedPreferences.getString("city", "").length() - 1);
        } else {
            city = PublicStaticData.sharedPreferences.getString("city", "");
        }
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.map_ang_list);
        commonOkhttp.putParams("city_name", city);    //城市名称  Y
        commonOkhttp.putParams("cat_id", catId);//钓场类型id
        commonOkhttp.putParams("longitude", PublicStaticData.sharedPreferences.getString("longitude", ""));//经度
        commonOkhttp.putParams("latitude", PublicStaticData.sharedPreferences.getString("latitude", ""));//纬度
        commonOkhttp.putParams("countyid", countyid);// 区域id
        commonOkhttp.putParams("pay_type", payType);//收费类型 1收费  2免费
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.HomeFishingPlaceListEntity>(MapDistributionShowActivity.this) {
            @Override
            public void onSuccess(NetBean.HomeFishingPlaceListEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    infos = new ArrayList<MarkerInfoUtil>();
                    if (data != null && data.size() != 0) {
                        for (int i = 0; i < data.size(); i++) {

                            if (!TextUtils.isEmpty(data.get(i).getLatitude()) && !TextUtils.isEmpty(data.get(i).getLongitude())) {
                                infos.add(new MarkerInfoUtil(Double.parseDouble(data.get(i).getLatitude()),
                                        Double.parseDouble(data.get(i).getLongitude()), data.get(i).getG_name()
                                        , data.get(i).getPosition(), data.get(i).getPay_type()));
                            }

                        }
                        addOverlay(infos);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(MapDistributionShowActivity.this).showMessage(MapDistributionShowActivity.this.getResources().getString(R.string.data_parsing_failed));
                }
            }
        });
        commonOkhttp.Execute();
    }

    //显示多个marker
    private void addOverlay(List<MarkerInfoUtil> infos2) {
        //清空地图
        mBaiduMap.clear();
        for (MarkerInfoUtil info : infos2) {
            //创建marker的显示图标
            if (TextUtils.equals(payType, "1") || TextUtils.equals(info.getType(), "1")) {
                //收费
                bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.map_dian_selected);

            } else if (TextUtils.equals(payType, "2") || TextUtils.equals(info.getType(), "2")) {
                //免费
                bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.map_landdian_selected);

            } else {
                if (TextUtils.equals(whereFrom, "1")) {

                } else if (TextUtils.equals(whereFrom, "2")) {
                    bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.map_dian_selected);
                }
            }
            //获取经纬度
            mLatLng = new LatLng(info.getLatitude(), info.getLongitude());
            //设置marker
            options = new MarkerOptions()
                    .position(mLatLng)//设置位置
                    .icon(bitmap)//设置图标样式
                    .zIndex(9);// 设置marker所在层级
            //添加marker
            marker = (Marker) mBaiduMap.addOverlay(options);

            //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
            Bundle bundle = new Bundle();
            //info必须实现序列化接口
            bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);
        }
        //将地图显示在最后一个marker的位置
        if (infos2.size() != 0) {
            mLatLng = new LatLng(infos2.get(0).getLatitude(), infos2.get(0).getLongitude());
        }
        mMapStatus = new MapStatus.Builder().target(mLatLng).zoom(17f).build();
        mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        setMarketClick();
    }

    /**
     * 设置标记点
     */
    public void setMarketClick() {

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                //从marker中获取info信息
                Bundle bundle = marker.getExtraInfo();
                MarkerInfoUtil infoUtil = (MarkerInfoUtil) bundle.getSerializable("info");
                showInfoWindow(marker, infoUtil);
                return false;
            }
        });
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mBaiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    /**
     * 显示InfoWindow
     */
    private void showInfoWindow(final Marker marker, final MarkerInfoUtil infoUtil) {

        View view = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.map_info_window, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplacation.isAvilible(MapDistributionShowActivity.this, "com.baidu.BaiduMap")) {
//                    Intent naviIntent = new Intent("android.intent.action.VIEW", Uri.parse("baidumap://map/geocoder?location=" + infoUtil.getLatitude() + "," + infoUtil.getLatitude()));
                    Intent naviIntent = new Intent();//"&destination=" + infoUtil.getDescription() +
                    naviIntent.setData(Uri.parse("baidumap://map/direction?region=beijing&origin=" + infoUtil.getLatitude() + "," + infoUtil.getLongitude() + "&mode=driving"));
                    startActivity(naviIntent);
                } else {
                    ToastUtils.getInstance(MapDistributionShowActivity.this).showMessage("请安装百度地图");
                }
            }
        });
        TextView tvName = (TextView) view.findViewById(R.id.map_info_window_tv_name);
        tvName.setText(infoUtil.getName());
        TextView tvAddress = (TextView) view.findViewById(R.id.map_info_window_tv_address);
        tvAddress.setText(infoUtil.getDescription());

        InfoWindow infoWindowDetails = new InfoWindow(view, marker.getPosition(), -marker.getIcon().getBitmap().getHeight());

        mBaiduMap.showInfoWindow(infoWindowDetails);

        Point point = mBaiduMap.getProjection().toScreenLocation(marker.getPosition());//经纬坐标转屏幕坐标

        point.y -= marker.getIcon().getBitmap().getHeight() * 2;//自定义设置地图中心的偏移量

        mLatLng = mBaiduMap.getProjection().fromScreenLocation(point);//屏幕坐标转经纬坐标

        MapStatusUpdate m = MapStatusUpdateFactory.newLatLng(mLatLng);
        mBaiduMap.setMapStatus(m);
    }

//    public void showPointDialog() {
//        Window window = getWindow();
//        int width = window.getWindowManager().getDefaultDisplay().getWidth() - 48;
//        int y = 48;
//        if (TextUtils.equals(whereFrom, "1")) {
//            mCustomDialog = new CustomDialog(MapDistributionShowActivity.this, width, y, R.layout.item_home_fishing_place, R.style.DialogTheme, false);
//            RelativeLayout rl = (RelativeLayout) mCustomDialog.findViewById(R.id.item_place_rl);
//            rl.setBackgroundDrawable(MapDistributionShowActivity.this.getResources().getDrawable(R.drawable.bg_white_round_angle));
//            rl.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (MyApplacation.isAvilible(MapDistributionShowActivity.this, "com.baidu.BaiduMap")) {
//                        Intent naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("baidumap://map/geocoder?location=" + latLngList.get(0).latitude + "," + latLngList.get(0).longitude));
//                        startActivity(naviIntent);
//                    } else {
//                        ToastUtils.getInstance(MapDistributionShowActivity.this).showMessage("请安装百度地图");
//                    }
//                }
//            });
//        } else if (TextUtils.equals(whereFrom, "2")) {
//            mCustomDialog = new CustomDialog(MapDistributionShowActivity.this, width, y, R.layout.item_home_fishing_shop, R.style.DialogTheme, false);
//            RelativeLayout rl = (RelativeLayout) mCustomDialog.findViewById(R.id.item_shop_rl);
//            rl.setBackgroundDrawable(MapDistributionShowActivity.this.getResources().getDrawable(R.drawable.bg_white_round_angle));
//            rl.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (MyApplacation.isAvilible(MapDistributionShowActivity.this, "com.baidu.BaiduMap")) {
//                        Intent naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("baidumap://map/geocoder?location=" + latLngList.get(0).latitude + "," + latLngList.get(0).longitude));
//                        startActivity(naviIntent);
//                    } else {
//                        ToastUtils.getInstance(MapDistributionShowActivity.this).showMessage("请安装百度地图");
//                    }
//                }
//            });
//        }
//
//
//        mCustomDialog.show();
//        mCustomDialog.setCanceledOnTouchOutside(true);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationClient.restart();
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    protected void onPause() {
        distributionShowMapView.onPause();
        mLocationClient.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        distributionShowMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        distributionShowMapView.onDestroy();
        super.onDestroy();
    }
}
