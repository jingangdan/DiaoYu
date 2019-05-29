package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.WeatherEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.ShareUtil;
import com.project.dyuapp.myutils.Utils;
import com.project.dyuapp.myutils.WeatherCodeUtils;
import com.project.dyuapp.runtimepermissions.PermissionsManager;
import com.umeng.socialize.UMShareAPI;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


/**
 * @describe：首页-天气
 * @author：刘晓丽
 * @createdate：2017/11/28 15:26
 */
public class WeatherActivity extends MyBaseActivity {

    @Bind(R.id.weather_ibtn_back)
    ImageButton weatherIbtnBack;
    @Bind(R.id.weather_tv_title)
    TextView weatherTvTitle;
    @Bind(R.id.weather_ibtn_right)
    ImageButton weatherIbtnRight;
    @Bind(R.id.weather_tv1)
    TextView weatherTv1;
    @Bind(R.id.weather_tv2)
    TextView weatherTv2;
    @Bind(R.id.weather_tv3)
    TextView weatherTv3;
    @Bind(R.id.weather_tv4)
    TextView weatherTv4;
    @Bind(R.id.weather_tv5)
    TextView weatherTv5;
    @Bind(R.id.weather_tv6)
    TextView weatherTv6;
    @Bind(R.id.weather_tv7)
    TextView weatherTv7;
    @Bind(R.id.weather_tv8)
    TextView weatherTv8;
    @Bind(R.id.weather_tv9)
    TextView weatherTv9;
    @Bind(R.id.weather_date1_tv1)
    TextView weatherDate1Tv1;
    @Bind(R.id.weather_date1_tv2)
    TextView weatherDate1Tv2;
    @Bind(R.id.weather_date1_iv)
    ImageView weatherDate1Iv;
    @Bind(R.id.weather_date1_tv3)
    TextView weatherDate1Tv3;
    @Bind(R.id.weather_date1_tv4)
    TextView weatherDate1Tv4;
    @Bind(R.id.weather_ll_date1)
    LinearLayout weatherLlDate1;
    @Bind(R.id.weather_date2_tv1)
    TextView weatherDate2Tv1;
    @Bind(R.id.weather_date2_tv2)
    TextView weatherDate2Tv2;
    @Bind(R.id.weather_date2_iv)
    ImageView weatherDate2Iv;
    @Bind(R.id.weather_date2_tv3)
    TextView weatherDate2Tv3;
    @Bind(R.id.weather_date2_tv4)
    TextView weatherDate2Tv4;
    @Bind(R.id.weather_ll_date2)
    LinearLayout weatherLlDate2;
    @Bind(R.id.weather_date3_tv1)
    TextView weatherDate3Tv1;
    @Bind(R.id.weather_date3_tv2)
    TextView weatherDate3Tv2;
    @Bind(R.id.weather_date3_iv)
    ImageView weatherDate3Iv;
    @Bind(R.id.weather_date3_tv3)
    TextView weatherDate3Tv3;
    @Bind(R.id.weather_date3_tv4)
    TextView weatherDate3Tv4;
    @Bind(R.id.weather_ll_date3)
    LinearLayout weatherLlDate3;
    @Bind(R.id.weather_ll_date)
    LinearLayout weatherLlDate;
    private WeatherEntity weatherEntity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        getWeather();//获取天气详情
    }

    @OnClick({R.id.weather_ibtn_back, R.id.weather_ibtn_right, R.id.weather_ll_date1, R.id.weather_ll_date2, R.id.weather_ll_date3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.weather_ibtn_back:
                //返回按钮
                WeatherActivity.this.finish();
                break;
            case R.id.weather_ibtn_right:
                //分享
                new ShareUtil(WeatherActivity.this, WeatherActivity.this,"钓鱼吧"," ",HttpUrl.URL+"?m=Home&c=Wechat&a=downPage");
                break;
            case R.id.weather_ll_date1:
                if (weatherEntity != null){
                    setData(weatherEntity,0);
                    weatherLlDate1.setBackgroundResource(R.drawable.bg_weather_white);
                    weatherLlDate2.setBackgroundResource(R.drawable.bg_weather_black);
                    weatherLlDate3.setBackgroundResource(R.drawable.bg_weather_black);
                }
                break;
            case R.id.weather_ll_date2:
                if (weatherEntity != null) {
                    setData(weatherEntity,1);
                    weatherLlDate1.setBackgroundResource(R.drawable.bg_weather_black);
                    weatherLlDate2.setBackgroundResource(R.drawable.bg_weather_white);
                    weatherLlDate3.setBackgroundResource(R.drawable.bg_weather_black);
                }
                break;
            case R.id.weather_ll_date3:
                if (weatherEntity != null) {
                    setData(weatherEntity,2);
                    weatherLlDate1.setBackgroundResource(R.drawable.bg_weather_black);
                    weatherLlDate2.setBackgroundResource(R.drawable.bg_weather_black);
                    weatherLlDate3.setBackgroundResource(R.drawable.bg_weather_white);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    /**
     * 权限申请回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    public void getWeather() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.COMMON_GETWEATHER);
        commonOkhttp.putCallback(new MyGenericsCallback<WeatherEntity>(this) {
            @Override
            public void onSuccess(WeatherEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    if (data.getHeWeather6()!=null){
                        weatherTvTitle.setText(data.getHeWeather6().get(0).getBasic().getParent_city());//城市
                    }
                    weatherEntity = data;
                    setData(weatherEntity,0);

                    //今天
                    String pres = data.getHeWeather6().get(0).getDaily_forecast().get(0).getPres();//气压
                    String tmp = data.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_max();//温度
                    int code_d = Integer.parseInt(data.getHeWeather6().get(0).getDaily_forecast().get(0).getCond_code_d());//白天天气状况代码
                    int int_fraction = getFraction(pres, tmp);//分数
                    String weatherImg = WeatherCodeUtils.getInstance(WeatherActivity.this).getWeather(code_d ,true);//天气图片地址

                    weatherDate1Tv2.setText(getWeather(int_fraction));//今日适合/不适合钓鱼
                    GlideUtils.loadImageView(WeatherActivity.this,weatherImg, weatherDate1Iv);//今日天气图片
                    weatherDate1Tv3.setText(data.getHeWeather6().get(0).getDaily_forecast().get(0).getCond_txt_d());//根据日出或日落判断显示白天或者夜晚天气情况
                    weatherDate1Tv4.setText(data.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_min() + "~" +
                            data.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_max() + "℃");//今日温度范围

                    // 明天
                    String pres_2 = data.getHeWeather6().get(0).getDaily_forecast().get(1).getPres();//气压
                    String tmp_2 = data.getHeWeather6().get(0).getDaily_forecast().get(1).getTmp_max();//温度
                    int code_d_2 = Integer.parseInt(data.getHeWeather6().get(0).getDaily_forecast().get(1).getCond_code_d());//白天天气状况代码
                    int int_fraction_2 = getFraction(pres_2, tmp_2);//分数
                    String weatherImg_2 = WeatherCodeUtils.getInstance(WeatherActivity.this).getWeather(code_d_2 ,true);//天气图片地址

                    weatherDate2Tv1.setText(Utils.getWeek(data.getHeWeather6().get(0).getUpdate().getLoc(),1));//明天周几
                    weatherDate2Tv2.setText(getWeather(int_fraction_2));//明天适合/不适合钓鱼
                    GlideUtils.loadImageView(WeatherActivity.this,weatherImg_2, weatherDate2Iv);//明天天气图片
                    weatherDate2Tv3.setText(data.getHeWeather6().get(0).getDaily_forecast().get(1).getCond_txt_d());//明天天气状况
                    weatherDate2Tv4.setText(data.getHeWeather6().get(0).getDaily_forecast().get(1).getTmp_min() + "~" +
                            data.getHeWeather6().get(0).getDaily_forecast().get(1).getTmp_max() + "℃");//明天温度范围

                    // 后天
                    String pres_3 = data.getHeWeather6().get(0).getDaily_forecast().get(2).getPres();//气压
                    String tmp_3 = data.getHeWeather6().get(0).getDaily_forecast().get(2).getTmp_max();//温度
                    int code_d_3 = Integer.parseInt(data.getHeWeather6().get(0).getDaily_forecast().get(2).getCond_code_d());//白天天气状况代码
                    int int_fraction_3 = getFraction(pres_3, tmp_3);//分数
                    String weatherImg_3 = WeatherCodeUtils.getInstance(WeatherActivity.this).getWeather(code_d_3 ,true);//天气图片地址

                    weatherDate3Tv1.setText(Utils.getWeek(data.getHeWeather6().get(0).getUpdate().getLoc(),2));//后天周几
                    weatherDate3Tv2.setText(getWeather(int_fraction_3));//后天适合/不适合钓鱼
                    GlideUtils.loadImageView(WeatherActivity.this,weatherImg_3, weatherDate3Iv);//后天天气图片
                    weatherDate3Tv3.setText(data.getHeWeather6().get(0).getDaily_forecast().get(2).getCond_txt_d());//后天天气状况
                    weatherDate3Tv4.setText(data.getHeWeather6().get(0).getDaily_forecast().get(2).getTmp_min() + "~" +
                            data.getHeWeather6().get(0).getDaily_forecast().get(2).getTmp_max() + "℃");//后天温度范围
                }catch (Exception e){

                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }
        });
        commonOkhttp.Execute();
    }

    private void setData(WeatherEntity data,int position) {
        long current = Utils.strToStamp(data.getHeWeather6().get(0).getUpdate().getLoc(),"yyyy-MM-dd HH:mm");//当前时间时间戳
        long current_d = Utils.strToStamp(data.getHeWeather6().get(0).getDaily_forecast().get(position).getDate()
                +" "+data.getHeWeather6().get(0).getDaily_forecast().get(position).getMs(),"yyyy-MM-dd HH:mm");//日出时间时间戳
        long current_n = Utils.strToStamp(data.getHeWeather6().get(0).getDaily_forecast().get(position).getDate()
                +" "+data.getHeWeather6().get(0).getDaily_forecast().get(position).getMr(),"yyyy-MM-dd HH:mm");//日落时间时间戳
        boolean isDay = current>=current_d || current < current_n;//当前时间大于等于日出时间 || 小于日落时间  == 白天
        String pres = data.getHeWeather6().get(0).getDaily_forecast().get(position).getPres();//气压
        String tmp = data.getHeWeather6().get(0).getDaily_forecast().get(position).getTmp_max();//温度
        int int_fraction = getFraction(pres, tmp);//分数


        weatherTv1.setText(int_fraction + "");//分数
        weatherTv2.setText(getWeather(int_fraction));//是否适合钓鱼
        weatherTv4.setText(true?data.getHeWeather6().get(0).getDaily_forecast().get(position).getCond_txt_d()
                :data.getHeWeather6().get(0).getDaily_forecast().get(position).getCond_txt_n());//根据日出或日落判断显示白天或者夜晚天气情况
        weatherTv5.setText("实时温度：" + data.getHeWeather6().get(0).getDaily_forecast().get(position).getTmp_min() + "~" +
                data.getHeWeather6().get(0).getDaily_forecast().get(position).getTmp_max() + "℃");//温度范围
        weatherTv6.setText("风向风力：" + data.getHeWeather6().get(0).getDaily_forecast().get(position).getWind_dir()
                + data.getHeWeather6().get(0).getDaily_forecast().get(position).getWind_sc());//风向风力
        weatherTv7.setText("湿度：" + data.getHeWeather6().get(0).getDaily_forecast().get(position).getHum() + "%");//湿度
        weatherTv8.setText("气压：" + data.getHeWeather6().get(0).getDaily_forecast().get(position).getPres() + "hPa");//气压
        weatherTv9.setText("日出/日落：" + data.getHeWeather6().get(0).getDaily_forecast().get(position).getSr() + "/"
                + data.getHeWeather6().get(0).getDaily_forecast().get(position).getSs());//日出/日落时间
    }

    /**
     * 根据气压，温度计算分数
     *
     * @param pres 气压
     * @param tmp  温度
     * @return
     */
    public int getFraction(String pres, String tmp) {
        int int_pres = Integer.parseInt(pres);
        int int_tmp = Integer.parseInt(tmp);
        int int_fraction = (int_pres-1000) + (int_tmp < 2 ? int_tmp : int_tmp / 2) + 50;
        return int_fraction;
    }

    /**
     * 根据分数判断是否适合钓鱼
     *
     * @param int_fraction
     * @return
     */
    public String getWeather(int int_fraction) {
        if (int_fraction < 60) {
            return "不太适合";
        } else if (int_fraction >= 60 && int_fraction < 70) {
            return "适合";
        } else if (int_fraction >= 70) {
            return "非常适合";
        }
        return "";
    }


}
