package com.project.dyuapp.myutils;

import android.content.Context;

/**
 * @创建者 任伟
 * @创建时间 2017/12/06 16:44
 * @描述 ${和风天气状况代码和图标对照表}
 */

public class WeatherCodeUtils {

    private static volatile WeatherCodeUtils weatherCodeUtils;

    public static WeatherCodeUtils getInstance(Context context) {
        if (weatherCodeUtils == null) {
            synchronized (WeatherCodeUtils.class) {
                if (weatherCodeUtils == null) {
                    weatherCodeUtils = new WeatherCodeUtils();
                }
            }
        }
        return weatherCodeUtils;
    }

    /**
     * 根据天气状况代码返回对应天气和天气图片
     *
     * @param code     天气情况代码
     * @param isImgUrl 是否为天气情况图片
     * @return
     */
    public String getWeather(int code, boolean isImgUrl) {
        switch (code) {
            case 100:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/100.png";
                } else {
                    return "晴";
                }
            case 101:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/101.png";
                } else {
                    return "多云";
                }
            case 102:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/102.png";
                } else {
                    return "少云";
                }
            case 103:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/103.png";
                } else {
                    return "晴间多云";
                }
            case 104:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/104.png";
                } else {
                    return "阴";
                }
            case 200:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/200.png";
                } else {
                    return "有风";
                }
            case 201:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/201.png";
                } else {
                    return "平静";
                }
            case 202:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/202.png";
                } else {
                    return "微风";
                }
            case 203:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/203.png";
                } else {
                    return "和风";
                }
            case 204:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/204.png";
                } else {
                    return "清风";
                }
            case 205:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/205.png";
                } else {
                    return "强风/劲风";
                }
            case 206:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/206.png";
                } else {
                    return "疾风";
                }
            case 207:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/207.png";
                } else {
                    return "大风";
                }
            case 208:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/208.png";
                } else {
                    return "烈风";
                }
            case 209:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/209.png";
                } else {
                    return "风暴";
                }
            case 210:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/210.png";
                } else {
                    return "狂爆风";
                }
            case 211:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/211.png";
                } else {
                    return "飓风";
                }
            case 212:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/212.png";
                } else {
                    return "龙卷风";
                }
            case 213:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/213.png";
                } else {
                    return "热带风暴";
                }
            case 300:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/300.png";
                } else {
                    return "阵雨";
                }
            case 301:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/301.png";
                } else {
                    return "强阵雨";
                }
            case 302:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/302.png";
                } else {
                    return "雷阵雨";
                }
            case 303:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/303.png";
                } else {
                    return "强雷阵雨";
                }
            case 304:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/304.png";
                } else {
                    return "雷阵雨伴有冰雹";
                }
            case 305:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/305.png";
                } else {
                    return "小雨";
                }
            case 306:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/306.png";
                } else {
                    return "中雨";
                }
            case 307:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/307.png";
                } else {
                    return "大雨";
                }
            case 308:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/308.png";
                } else {
                    return "极端降雨";
                }
            case 309:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/309.png";
                } else {
                    return "毛毛雨/细雨";
                }
            case 310:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/310.png";
                } else {
                    return "暴雨";
                }
            case 311:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/311.png";
                } else {
                    return "大暴雨";
                }
            case 312:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/312.png";
                } else {
                    return "特大暴雨";
                }
            case 313:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/313.png";
                } else {
                    return "冻雨";
                }
            case 400:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/400.png";
                } else {
                    return "小雪";
                }
            case 401:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/401.png";
                } else {
                    return "中雪";
                }
            case 402:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/402.png";
                } else {
                    return "大雪";
                }
            case 403:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/403.png";
                } else {
                    return "暴雪";
                }
            case 404:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/404.png";
                } else {
                    return "雨夹雪";
                }
            case 405:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/405.png";
                } else {
                    return "雨雪天气";
                }
            case 406:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/406.png";
                } else {
                    return "阵雨夹雪";
                }
            case 407:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/407.png";
                } else {
                    return "阵雪";
                }
            case 500:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/500.png";
                } else {
                    return "薄雾";
                }
            case 501:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/501.png";
                } else {
                    return "雾";
                }
            case 502:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/502.png";
                } else {
                    return "霾";
                }
            case 503:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/503.png";
                } else {
                    return "扬沙";
                }
            case 504:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/504.png";
                } else {
                    return "浮尘";
                }
            case 507:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/507.png";
                } else {
                    return "沙尘暴";
                }
            case 508:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/508.png";
                } else {
                    return "强沙尘暴";
                }
            case 900:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/900.png";
                } else {
                    return "热";
                }
            case 901:
                if (isImgUrl) {
                    return "https://cdn.heweather.com/cond_icon/999.png";
                } else {
                    return "未知";
                }
        }
        return "";
    }
}
