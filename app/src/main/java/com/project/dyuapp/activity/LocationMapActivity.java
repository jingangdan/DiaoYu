package com.project.dyuapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
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
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.runtimepermissions.PermissionUtils;
import com.project.dyuapp.runtimepermissions.PermissionsManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 田亭亭
 * @description 地图选点Activity
 * @created at 2017/12/8 0008
 * @change
 */
public class LocationMapActivity extends MyBaseActivity implements OnGetGeoCoderResultListener {

    @Bind(R.id.location_map_view)
    MapView locationMapView;
    @Bind(R.id.location_map_tv_place)
    TextView locationMapTvPlace;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private boolean isFirstLocation = true;
    public BDLocationListener myListener;
    private double lat;//纬度
    private double lon;//经度
    private String address = "";//地址
    private Marker marker;
    private BitmapDescriptor mCurrentMark;
    private LatLng cenpt;
    private ReverseGeoCodeOption reverseGeoCodeOption;
    private GeoCoder geoCoder;
    private Intent intent;
    private ReverseGeoCodeResult mResult;
    private String province = "";//省
    private String city = "";//市
    private String county = "";//县
    private String street_number = "";// 详细地址
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_loaction_map);
        ButterKnife.bind(this);
        setTvTitle("地图选点");
        getTvRight().setText("确定");
        getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                if (mResult == null || mResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(LocationMapActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                            .show();
                    return;
                } else {
                    lat = mResult.getLocation().latitude;
                    lon = mResult.getLocation().longitude;
                    address = mResult.getAddress();
                    city = mResult.getAddressDetail().city;
                    province = mResult.getAddressDetail().province;
                    county = mResult.getAddressDetail().district;
                    street_number = mResult.getAddressDetail().street+mResult.getAddressDetail().streetNumber;
                    intent.putExtra("lat", lat + "");
                    intent.putExtra("lon", lon + "");
                    intent.putExtra("address", address);
                    intent.putExtra("province", province);
                    intent.putExtra("city", city);
                    intent.putExtra("county", county);
                    intent.putExtra("street_number", street_number);
                    setResult(PublicStaticData.LOCATION_MAP, intent);
                    ScreenManager.getInstance().removeActivity(LocationMapActivity.this);
                }

            }
        });
        setIvBack();
        //获取BaiduMap对象
        mBaiduMap = locationMapView.getMap();
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myListener);
        //配置定位参数
        initLocation();
        //开始定位
        PermissionUtils.location(LocationMapActivity.this, new PermissionUtils.OnPermissionResult() {
            @Override
            public void onGranted() {
                mLocationClient.start();
            }
        });
        //创建GeoCoder实例对象
        geoCoder = GeoCoder.newInstance();
        //发起反地理编码请求(经纬度->地址信息)
        reverseGeoCodeOption = new ReverseGeoCodeOption();
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

    //地理编码查询结果回调函数
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    //反地理编码查询结果回调函数
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(LocationMapActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        mResult = reverseGeoCodeResult;
        mBaiduMap.clear();
        locationMapTvPlace.setText(reverseGeoCodeResult.getAddress() + "");
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

            province = location.getProvince();
            city = location.getCity();
            county = location.getDistrict();

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
            //设置定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(0).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            //这个判断是为了防止每次定位都重新设置中心点和marker
            if (isFirstLocation) {
                isFirstLocation = false;
                setUserMapCenter(location);
            }
            setMarket();
            Log.v("pcw", "lat : " + lat + " lon : " + lon);
            Log.i("BaiduLocationApiDem", sb.toString());
        }
    }

    /**
     * 设置中心点
     */
    private void setUserMapCenter(BDLocation location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
        Log.v("pcw", "setUserMapCenter : lat : " + lat + " lon : " + lon);
        cenpt = new LatLng(lat, lon);
        reverseGeoCodeOption.location(cenpt);
        geoCoder.reverseGeoCode(reverseGeoCodeOption);
        //设置查询结果监听者
        geoCoder.setOnGetGeoCodeResultListener(this);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(17f).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
        //设置地图定位模式
        MyLocationConfiguration.LocationMode locationMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(locationMode, true, null));
        //设置定位数据
        MyLocationData.Builder locationBuilder =
                new MyLocationData.Builder();
        locationBuilder.latitude(lat);
        locationBuilder.longitude(lon);
        MyLocationData locationData = locationBuilder.build();
        mBaiduMap.setMyLocationData(locationData);
        mBaiduMap.setMyLocationEnabled(true);//关键点，必须设置
        //设置图标
        mCurrentMark = BitmapDescriptorFactory.fromResource(R.mipmap.map_dian_selected);
        //获取坐标，待会用于POI信息点与定位的距离
        OverlayOptions option = new MarkerOptions().position(cenpt).icon(mCurrentMark)
                .zIndex(9)  //设置marker所在层级
                .draggable(true);  //设置手势拖拽
        marker = (Marker) (mBaiduMap.addOverlay(option));
        marker.setAlpha(0);

    }

    public void setMarket() {
        //设置反地理编码位置坐标
        reverseGeoCodeOption.location(cenpt);
        geoCoder.reverseGeoCode(reverseGeoCodeOption);
        //设置查询结果监听者
        geoCoder.setOnGetGeoCodeResultListener(this);
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                //地图操作的中心点
                cenpt = mapStatus.target;
                marker.setPosition(cenpt);
                locationMapTvPlace.setText("正在定位中......");
                geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(cenpt));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationClient.restart();
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    protected void onPause() {
        locationMapView.onPause();
        mLocationClient.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        locationMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        locationMapView.onDestroy();
        geoCoder.destroy();
        super.onDestroy();
    }
}
