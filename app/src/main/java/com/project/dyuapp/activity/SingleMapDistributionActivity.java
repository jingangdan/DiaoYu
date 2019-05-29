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
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.ToastUtils;
import com.project.dyuapp.runtimepermissions.PermissionUtils;
import com.project.dyuapp.runtimepermissions.PermissionsManager;

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
public class SingleMapDistributionActivity extends MyBaseActivity {

    @Bind(R.id.single_distribution_show_map_view)
    MapView singleDistributionShowMapView;
    @Bind(R.id.single_distribution_show_ll_charge_type)
    LinearLayout llChargeType;
    private String whereFrom = "";

    private double lat;//纬度
    private double lon;//经度
    private String overLat = "";//纬度
    private String overLon = "";//经度
    private String name = "";//经度
    private String address = "";//经度
    private String payType = "";//经度
    //    private CustomDialog mCustomDialog;
    private BitmapDescriptor mCurrentMark;//当前定位图标
    private OverlayOptions mOverlayOption;//覆盖物类
    private InfoWindow infoWindowDetails;//覆盖物弹框
    private MapStatus mMapStatus;//地图状态
    private MapStatusUpdate mMapStatusUpdate;//状态更新
    private BaiduMap mBaiduMap;//地图图层
    private LocationClient mLocationClient;//定位
    private boolean isFirstLocation = true;//是否第一次定位
    public BDLocationListener myListener;//定位改变监听

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_single_map_distribution);
        ButterKnife.bind(this);
        whereFrom = getIntent().getStringExtra("whereFrom");
        overLat = getIntent().getStringExtra("lat");
        overLon = getIntent().getStringExtra("lon");
        name = getIntent().getStringExtra("name");
        address = getIntent().getStringExtra("address");
        payType = getIntent().getStringExtra("pay_type");
        if (TextUtils.equals(whereFrom, "1")) {
            llChargeType.setVisibility(View.VISIBLE);
            setTvTitle("钓场");
        } else if (TextUtils.equals(whereFrom, "2")) {
            llChargeType.setVisibility(View.GONE);
            setTvTitle("渔具店");
        }
        setIvBack();
        //获取BaiduMap对象
        mBaiduMap = singleDistributionShowMapView.getMap();
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myListener);
        //配置定位参数
        initLocation();
        //开始定位
        PermissionUtils.location(SingleMapDistributionActivity.this, new PermissionUtils.OnPermissionResult() {
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

    @OnClick(R.id.single_distribution_show_img_map_current)
    public void onViewClicked() {
        mLocationClient.requestLocation();
        double longitude = Double.parseDouble(PublicStaticData.sharedPreferences.getString("longitude", "0"));
        double latitude = Double.parseDouble(PublicStaticData.sharedPreferences.getString("latitude", "0"));
        LatLng point = new LatLng(latitude, longitude);
        mMapStatus = new MapStatus.Builder().target(point).zoom(17.0f).build();
        mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
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
        LatLng cenpt = new LatLng(lat, lon);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //定义地图状态
        mMapStatus = new MapStatus.Builder().target(cenpt).zoom(17.0f).build();
        MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        int accuracyCircleFillColor = 0x00000000;//自定义精度圈填充颜色
        int accuracyCircleStrokeColor = 0x00000000;//自定义精度圈边框颜色
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(mCurrentMode, true, null, accuracyCircleFillColor, accuracyCircleStrokeColor));
        mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
        setMarket();
    }

    /**
     * 设置标记点
     */
    public void setMarket() {
        if (!TextUtils.isEmpty(overLat) && !TextUtils.isEmpty(overLon)) {
            lat = Double.valueOf(overLat);
            lon = Double.valueOf(overLon);
            LatLng cenpt = new LatLng(lat, lon);
            if (TextUtils.equals(payType, "1")) {
                //收费
                mCurrentMark = BitmapDescriptorFactory.fromResource(R.mipmap.map_dian_selected);
            } else if (TextUtils.equals(payType, "2")) {
                //免费
                mCurrentMark = BitmapDescriptorFactory.fromResource(R.mipmap.map_landdian_selected);
            } else {
                if (TextUtils.equals(whereFrom, "1")) {

                } else if (TextUtils.equals(whereFrom, "2")) {
                    mCurrentMark = BitmapDescriptorFactory.fromResource(R.mipmap.map_dian_selected);
                }
            }
            mOverlayOption = new MarkerOptions().position(cenpt).icon(mCurrentMark);
            //在地图上批量添加
            mBaiduMap.addOverlay(mOverlayOption);
            mMapStatus = new MapStatus.Builder().target(cenpt).zoom(17.0f).build();
            mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.setMapStatus(mMapStatusUpdate);
            mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    showInfoWindow(marker.getPosition(), marker);
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
    }

    /**
     * 显示InfoWindow
     */
    private void showInfoWindow(final LatLng latLng, final Marker marker) {

        View view = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.map_info_window, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplacation.isAvilible(SingleMapDistributionActivity.this, "com.baidu.BaiduMap")) {
                    /*"android.intent.action.VIEW", Uri.parse("baidumap://map/geocoder?location=" + latLng.latitude + "," + latLng.longitude)*/
                    Intent naviIntent = new Intent();// + "&destination=" + address
                    naviIntent.setData(Uri.parse("baidumap://map/direction?region=beijing&origin=" + latLng.latitude + "," + latLng.longitude + "&mode=driving"));
                    startActivity(naviIntent);
                } else {
                    ToastUtils.getInstance(SingleMapDistributionActivity.this).showMessage("请安装百度地图");
                }
            }
        });
        TextView tvName = (TextView) view.findViewById(R.id.map_info_window_tv_name);
        tvName.setText(name);
        TextView tvAddress = (TextView) view.findViewById(R.id.map_info_window_tv_address);
        tvAddress.setText(address);

        infoWindowDetails = new InfoWindow(view, marker.getPosition(), -marker.getIcon().getBitmap().getHeight());

        mBaiduMap.showInfoWindow(infoWindowDetails);

        Point point = mBaiduMap.getProjection().toScreenLocation(marker.getPosition());//经纬坐标转屏幕坐标

        point.y -= marker.getIcon().getBitmap().getHeight() * 2;//自定义设置地图中心的偏移量

        LatLng latLngCenter = mBaiduMap.getProjection().fromScreenLocation(point);//屏幕坐标转经纬坐标
        mMapStatus = new MapStatus.Builder().target(latLngCenter).zoom(17.0f).build();
        mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }


//    public void showPointDialog() {
//        Window window = getWindow();
//        int width = window.getWindowManager().getDefaultDisplay().getWidth() - 48;
//        int y = 48;
//        if (TextUtils.equals(whereFrom, "1")) {
//            mCustomDialog = new CustomDialog(SingleMapDistributionActivity.this, width, y, R.layout.item_home_fishing_place, R.style.DialogTheme, false);
//            RelativeLayout rl = (RelativeLayout) mCustomDialog.findViewById(R.id.item_place_rl);
//            rl.setBackgroundDrawable(SingleMapDistributionActivity.this.getResources().getDrawable(R.drawable.bg_white_round_angle));
//            rl.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (MyApplacation.isAvilible(SingleMapDistributionActivity.this, "com.baidu.BaiduMap")) {
//                        Intent naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("baidumap://map/geocoder?location=" + latLngList.get(0).latitude + "," + latLngList.get(0).longitude));
//                        startActivity(naviIntent);
//                    } else {
//                        ToastUtils.getInstance(SingleMapDistributionActivity.this).showMessage("请安装百度地图");
//                    }
//                }
//            });
//        } else if (TextUtils.equals(whereFrom, "2")) {
//            mCustomDialog = new CustomDialog(SingleMapDistributionActivity.this, width, y, R.layout.item_home_fishing_shop, R.style.DialogTheme, false);
//            RelativeLayout rl = (RelativeLayout) mCustomDialog.findViewById(R.id.item_shop_rl);
//            rl.setBackgroundDrawable(SingleMapDistributionActivity.this.getResources().getDrawable(R.drawable.bg_white_round_angle));
//            rl.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (MyApplacation.isAvilible(SingleMapDistributionActivity.this, "com.baidu.BaiduMap")) {
//                        Intent naviIntent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("baidumap://map/geocoder?location=" + latLngList.get(0).latitude + "," + latLngList.get(0).longitude));
//                        startActivity(naviIntent);
//                    } else {
//                        ToastUtils.getInstance(SingleMapDistributionActivity.this).showMessage("请安装百度地图");
//                    }
//                }
//            });
//        }
//        mCustomDialog.show();
//        mCustomDialog.setCanceledOnTouchOutside(true);
//}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationClient.restart();
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    protected void onPause() {
        singleDistributionShowMapView.onPause();
        mLocationClient.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        singleDistributionShowMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        singleDistributionShowMapView.onDestroy();
        super.onDestroy();
    }

}
