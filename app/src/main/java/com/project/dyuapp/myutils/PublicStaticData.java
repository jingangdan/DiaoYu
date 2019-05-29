package com.project.dyuapp.myutils;

import android.content.SharedPreferences;

/**
 * @describe： 静态数据类
 * @author：刘晓丽
 * @createdate：2017/3/29 11:04
 */

public class PublicStaticData {

    public static SharedPreferences sharedPreferences;
    public static Boolean isSDCard;//是否有SD卡
    public static String outDir;//保存文件的根路径
    public static String picFilePath = "";//保存图片文件的路径

    public static int width;//屏幕宽度
    public static int height;//屏幕高度

    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    /*跳转FishingPlaceTypeActivity的请求码*/
    public static final int CODE_CLASSIFY = 102;//发布饵料，技巧

    public static final int CODE_PLACE_TYPE = 1001;//钓场类型
    public static final int CODE_PLACE_NAME = 1002;//钓场名称
    public static final int CODE_COST_TYPE = 2003;//收费类型
    public static final int CODE_FISH_TYPE = 2004;//选择鱼种
    public static final int CODE_BAIT = 1003;//饵料
    public static final int CODE_FISH_SEED = 1004;//鱼种
    public static final int CODE_FISH_METHOD = 1005;//钓法
    public static final int CODE_LENGHT = 1006;//钓竿长度

    public static final int REQUEST_CODE = 107;//扫描二维码跳转Activity RequestCode
    public static final int REQUEST_CAMERA_PERM = 108;//请求CAMERA权限码
    public static final int REQUEST_LOCATION_PERM = 109;//请求定位权限码

    public static final int EXCHANGE_INFO = 109;//兑换信息
    public static final int SELECT_ADDRESS = 110;//选择地址

    public static final int APPEND_PLACE = 111;//发布钓场
    public static final int APPEND_SHOP = 112;//发布渔具店
    public static final int LOCATION_MAP = 113;//地图选点
    public static final int FISHING_SHOP_DETAILS = 114;//渔具店详情
    public static final int REPORT_ERRORS = 115;//报错
    public static final int ERROR_TYPE = 116;//报错类型
    public static final int HOME_FISHING_SHOP = 117;//首页渔具店
    public static final int RESULT_CODE_RREVIEW = 118;//点评结果码
    public static final int MY_ATTENTION_SHOP = 119;//我关注的渔具店

    public static final String SP_FILE = "diaoyu";//存数据名

    /*帖子顶级分类（52渔获战报、53问答、54渔具DIY、55路亚海钓、56鱼饵配方、57杂谈、58技巧）*/
    public static final String ID_GET_FISH = "52";
    public static final String ID_QUESTION = "53";
    public static final String ID_FISH_TOOL = "54";
    public static final String ID_LUYA = "55";
    public static final String ID_BAIT = "56";
    public static final String ID_TALk = "57";
    public static final String ID_SKILL = "58";
    /*(视频-分类)*/
    public static final String ID_VIDEO = "1";
    public static final String COM_PROJECT_DYUAPP_MAIN_OUT = "main_out_boradcast_receiver";

    public static final int GOLD_MALL = 120;//金币商城
    public static final int SHOP_EXCHANGE = 121;//金币详情

    public static final int HOME_CODE = 0x01;//首页
    public static final int CITY_CHANGE_CODE = 0x02;//切换城市
}

