package com.project.dyuapp.myutils;

/**
 * Created by ${田亭亭} on 2017/10/31 0031.
 *
 * @description
 * @change
 */

public class HttpUrl {
//    public static final String URL = "http://diaoyuba.yh3m.cc/index.php";//接口地址
//    public static final String IMAGE_URL = "http://diaoyuba.yh3m.cc";//图片地址

    public static final String URL = "http://www.diaoyu8.com/index.php";//接口地址
    public static final String IMAGE_URL = "http://www.diaoyu8.com";//图片地址
    /*取所有省市地区接口*/
    public static final String COMMON_GETCITYS = URL + "?m=ApiMobile&c=Common&a=getAllCitys";
    /*取所有省市县地区接口*/
    public static final String COMMON_GETAREA = URL + "?m=ApiMobile&c=Common&a=getArea";
    /*分级获取地区接口*/
    public static final String COMMON_GET_CITYS = URL + "?m=ApiMobile&c=Common&a=getCitys";
    /*天气详情*/
    public static final String COMMON_GETWEATHER = URL + "?m=ApiMobile&c=Common&a=getWeather";
    //1个人中心
    public static final String person = URL + "?m=ApiMobile&c=PersonCenter&a=person";
    //6个人中心-粉丝
    public static final String fans = URL + "?m=ApiMobile&c=PersonCenter&a=fans";
    //8个人中心-服务地址
    public static final String serviceAddress = URL + "?m=ApiMobile&c=PersonCenter&a=serviceAddress";
    //9个人中心-服务地址-新地址（编辑）
    public static final String addAddress = URL + "?m=ApiMobile&c=PersonCenter&a=addAddress";
    //10个人中心-关注
    public static final String follower = URL + "?m=ApiMobile&c=PersonCenter&a=follower";
    //11个人中心-每日签到-个人信息
    public static final String signInfo = URL + "?m=ApiMobile&c=PersonCenter&a=signInfo";
    //12个人中心-每日签到-签到
    public static final String signIn = URL + "?m=ApiMobile&c=PersonCenter&a=signIn";
    //13个人中心-每日签到-查看历史记录
    public static final String signHistory = URL + "?m=ApiMobile&c=PersonCenter&a=signHistory";
    //14个人中心-每日签到-等级规则
    public static final String lvlRule = URL + "?m=ApiMobile&c=PersonCenter&a=lvlRule";
    //25个人中心-金币商城
    public static final String index = URL + "?m=ApiMobile&c=Mall&a=index";
    //26个人中心-金币商城-兑换记录
    public static final String exchangeLog = URL + "?m=ApiMobile&c=Mall&a=exchangeLog";
    //27个人中心-金币商城-兑换商品
    public static final String goodsExchange = URL + "?m=ApiMobile&c=Mall&a=goodsExchange";
    //15个人中心-设置-我的二维码
    public static final String myQrcode = URL + "?m=ApiMobile&c=PersonCenter&a=myQrcode";
    //16个人中心-设置-意见反馈
    public static final String ideaBack = URL + "?m=ApiMobile&c=PersonCenter&a=ideaBack";
    //个人中心-金币商城-兑换商品
    public static final String goodsExchanges = URL + "?m=ApiMobile&c=Mall&a=goodsExchanges";
    //个人中心-金币商城-兑换商品动作
    public static final String goodsExchangeAction = URL + "?m=ApiMobile&c=Mall&a=goodsExchangeAction";
    //个人中心-服务地址（返回信息）
    public static final String addressInfo = URL + "?m=ApiMobile&c=PersonCenter&a=addressInfo";
    //28个人中心-服务地址-删除
    public static final String serviceDzDel = URL + "?m=ApiMobile&c=PersonCenter&a=serviceDzDel";
    //34个人中心-服务地址-设置默认
    public static final String setDefault = URL + "?m=ApiMobile&c=PersonCenter&a=setDefault";
    //29积分规则链接地址
    public static final String integrationRule = URL + "?m=ApiMobile&c=Common&a=integrationRule";
    //30金币任务链接地址
    public static final String goldTask = URL + "?m=ApiMobile&c=Common&a=goldTask";
    //31等级规则链接地址
    public static final String gradeRule = URL + "?m=ApiMobile&c=Common&a=gradeRule";
    //7同城-附近钓友
    public static final String fishingFriend = URL + "?m=ApiMobile&c=Release&a=fishingFriend";
    //3首页-渔获-详情-点赞
    public static final String dianzanList = URL + "?m=ApiMobile&c=Index&a=dianzanList";
    //5获取当前用户金币、积分数量
    public static final String memberInfo = URL + "?m=ApiMobile&c=Fishing&a=memberInfo";
    //6首页-渔获-详情-赞赏支持
    public static final String fishingReward = URL + "?m=ApiMobile&c=Fishing&a=reward";
    //服务协议
    public static final String agreement = URL + "?m=Home&c=Index&a=hfive&content_id=7";
    //15联系方式
    public static final String contactInformation = URL + "?m=ApiMobile&c=Release&a=contactInformation";
    //39 扫二维码关注
    public static final String qrcodeGz = URL + "?m=ApiMobile&c=PersonCenter&a=qrcodeGz";

    /*注册*/
    public static final String USER_REGISTER = URL + "?m=ApiMobile&c=User&a=register";
    /*注册发送验证码*/
    public static final String USER_SENDCDE = URL + "?m=ApiMobile&c=User&a=send_code";
    /*登录*/
    public static final String USER_LOGIN = URL + "?m=ApiMobile&c=User&a=Login";
    /*第三方登录*/
    public static final String thirdParty = URL + "?m=ApiMobile&c=User&a=thirdParty";
    /*注册协议*/
    public static final String HFIVE_CONTENT_ID_8 = URL + "?m=Home&c=Index&a=hfive&content_id=8";

    /*渔具店*/
    public static final String fishingGear = URL + "?m=ApiMobile&c=Common&a=fishingGear";
    /*渔具店-筛选*/
    public static final String fishingGearArea = URL + "?m=ApiMobile&c=Common&a=fishingGearArea";
    /*渔具店详情*/
    public static final String fishingGearDetails = URL + "?m=ApiMobile&c=Common&a=fishingGearDetails";
    /*渔具店-关注*/
    public static final String is_attention = URL + "?m=ApiMobile&c=FishingShop&a=is_attention";
    /*渔具店报错*/
    public static final String mistakeShop = URL + "?m=ApiMobile&c=FishingShop&a=mistakeShop";
    /*渔具店传图*/
    public static final String spreadImgs = URL + "?m=ApiMobile&c=FishingShop&a=spreadImgs";
    /*个人中心-渔具店-我添加的*/
    public static final String myFishShopAdd = URL + "?m=ApiMobile&c=PersonCenter&a=myFishShopAdd";
    /*个人中心-渔具店-我关注的*/
    public static final String myFishShopg = URL + "?m=ApiMobile&c=PersonCenter&a=myFishShopg";
    /*个人中心-渔具店-我点评的*/
    public static final String myFishShop = URL + "?m=ApiMobile&c=PersonCenter&a=myFishShop";

    /*我的消息*/
    public static final String mymsg = URL + "?m=ApiMobile&c=News&a=index";
    /*我的消息-我的私信*/
    public static final String letter = URL + "?m=ApiMobile&c=News&a=letter";
    /*我的消息-我的私信-删除*/
    public static final String letterDel = URL + "?m=ApiMobile&c=News&a=letterDel";
    /*我的消息-回复*/
    public static final String reply = URL + "?m=ApiMobile&c=News&a=reply";
    /*我的消息-赞过我*/
    public static final String fabulous = URL + "?m=ApiMobile&c=News&a=fabulous";
    /*我的消息-系统消息*/
    public static final String system = URL + "?m=ApiMobile&c=News&a=system";
    /*我的消息-系统消息-清空*/
    public static final String systemEmpty = URL + "?m=ApiMobile&c=News&a=systemEmpty";
    /*我的消息-打赏*/
    public static final String reward = URL + "?m=ApiMobile&c=News&a=reward";
    /*我的消息-发现周边钓友*/
    public static final String periphery = URL + "?m=ApiMobile&c=News&a=periphery";
    /*个人资料-获取信息*/
    public static final String editData = URL + "?m=ApiMobile&c=PersonCenter&a=editData";
    /*个人资料-编辑信息*/
    public static final String editDatat = URL + "?m=ApiMobile&c=PersonCenter&a=editDatat";

    /*发布钓场*/
    public static final String ANGLING_PUBLISH_ANG = URL + "?m=ApiMobile&c=Angling&a=publish_ang";
    /*钓场类型*/
    public static final String ANGLING_ANG_TYPE = URL + "?m=ApiMobile&c=Angling&a=ang_type";
    /*鱼种*/
    public static final String ANGLING_FISH_TYPE = URL + "?m=ApiMobile&c=Angling&a=fish_type";
    /*钓场列表*/
    public static final String ANGLING_ANG_LIST = URL + "?m=ApiMobile&c=Angling&a=ang_list";
    /*地图钓场显示*/
    public static final String map_ang_list = URL + "?m=ApiMobile&c=Angling&a=map_ang_list";
    /*发布渔具店*/
    public static final String ANGLING_ADDFISHINGSHOP = URL + "?m=ApiMobile&c=FishingShop&a=addFishingShop";
    /*忘记密码发送验证码*/
    public static final String USER_SEND_CODE = URL + "?m=ApiMobile&c=User&a=s_send_code";
    /*密码找回*/
    public static final String USER_FORGET_PWD = URL + "?m=ApiMobile&c=User&a=forget_pwd";
    /*关注、取消关注*/
    public static final String attention = URL + "?m=ApiMobile&c=PersonCenter&a=attention";
    /*钓场筛选-区域列表*/
    public static final String USER_GET_COUNTY = URL + "?m=ApiMobile&c=Angling&a=get_county";
    /*设置定位、切换定位*/
    public static final String setPosition = URL + "?m=ApiMobile&c=Common&a=setPosition";
    /*唤醒定位*/
    public static final String updatePosition = URL + "?m=ApiMobile&c=Common&a=updatePosition";

    public static final String forumList = URL + "?m=ApiMobile&c=Common&a=forumList";//1帖子-列表
    public static final String getSiteCategory = URL + "?m=ApiMobile&c=Common&a=getSiteCategory";//4站点分类 集合接口
    public static final String skillCategory = URL + "?m=Api&c=Release&a=skillCategory";//1 发布-技巧-技巧分类
    public static final String releaseSkill = URL + "?m=ApiMobile&c=Release&a=releaseSkill";//2 发布-技巧
    public static final String cityWide = URL + "?m=ApiMobile&c=Index&a=cityWide";//23同城
    public static final String cityWideTiezi = URL + "?m=ApiMobile&c=Index&a=cityWideTiezi";//23同城
    public static final String fansIndex = URL + "?m=ApiMobile&c=PersonCenter&a=fansIndex";//7个人中心-粉丝-主页
    public static final String myCollect = URL + "?m=ApiMobile&c=PersonCenter&a=myCollect";//19个人中心-我的收藏（帖子）
    public static final String forumDetail = URL + "?m=ApiMobile&c=Common&a=forumDetail";//2帖子-详情
    public static final String myTiezi = URL + "?m=ApiMobile&c=PersonCenter&a=myTiezi";//32个人中心-我的帖子
    public static final String publishFishing = URL + "?m=ApiMobile&c=Fishing&a=publishFishing";//发布渔获
    public static final String yuhuo = URL + "?m=ApiMobile&c=Common&a=yuhuo";//渔获帖子统计

    /*钓场-点评*/
    public static final String dianPing = URL + "?m=ApiMobile&c=Release&a=releaseDianping";

    /*视频*/
    public static final String videos = URL + "?m=ApiMobile&c=Release&a=videos";
    /*视频-二级分类*/
    public static final String newest = URL + "?m=ApiMobile&c=Release&a=newest";
    /*视频-详情*/
    public static final String newestDetail = URL + "?m=ApiMobile&c=Release&a=newestDetail";
    /*全部视频列表*/
    public static final String videosMore = URL + "?m=ApiMobile&c=Release&a=videosMore";


    /*搜索历史列表*/
    public static final String get_searchHistory = URL + "?m=ApiMobile&c=Common&a=get_searchHistory";
    /*删除历史搜索记录*/
    public static final String del_searchHistory = URL + "?m=ApiMobile&c=Common&a=del_searchHistory";
    /*搜索-结果*/
    public static final String search_object = URL + "?m=ApiMobile&c=Common&a=search_object";

    /*搜索-结果 新*/
    public static final String search_object_new = "http://www.diaoyu8.com/?m=ApiMobile&c=Common&a=search_object";
    /*搜索-热门关键词*/
    public static final String get_hot_words = URL + "?m=ApiMobile&c=Common&a=get_hot_words";


    /*个人中心-草稿箱*/
    public static final String draftBox = URL + "?m=ApiMobile&c=PersonCenter&a=draftBox";
    /*个人中心-草稿箱-删除*/
    public static final String caogaoDel = URL + "?m=ApiMobile&c=PersonCenter&a=caogaoDel";
    /*个人中心-草稿箱-重发*/
    public static final String caogaoCf = URL + "?m=ApiMobile&c=PersonCenter&a=caogaoCf";


    /*钓场详情*/
    public static final String ANGLING_ANG_INFO = URL + "?m=ApiMobile&c=Angling&a=ang_info";

    /*点评列表(钓场详情)*/
    public static final String ANGLING_ANG_INFO_DP = URL + "?m=ApiMobile&c=Angling&a=ang_dp_list";

    /*钓场报错*/
    public static final String ANGLING_REPORT_ERRORS = URL + "?m=ApiMobile&c=Angling&a=report_errors";
    /*钓场传图*/
    public static final String ANGLING_UPLOAD_IMAGES = URL + "?m=ApiMobile&c=Angling&a=upload_images";
    /*钓场-关注|取消关注*/
    public static final String ANGLING_FOLLOW_FISH = URL + "?m=ApiMobile&c=Angling&a=follow_fish";
    /*钓友全部传图*/
    public static final String ANGLING_ALL_IMAGES = URL + "?m=ApiMobile&c=Angling&a=all_images";
    /*同城渔具店*/
    public static final String fishingShop = URL + "?m=ApiMobile&c=Release&a=fishingShop";
    /*渔获详情-全部评论*/
    public static final String forumComments = URL + "?m=ApiMobile&c=Common&a=forumComments";
    /*视频点赞*/
    public static final String dianzanRun = URL + "?m=ApiMobile&c=Fishing&a=dianzanRun";
    /*个人中心-我的钓场-我关注的*/
    public static final String PERSONCENTER_MY_FISHG = URL + "?m=ApiMobile&c=PersonCenter&a=myFishg";
    /*个人中心-我的钓场-我添加的*/
    public static final String PERSONCENTER_MY_FISHGADD = URL + "?m=ApiMobile&c=PersonCenter&a=myFishAdd";
    /*同城-钓场列表*/
    public static final String RELEASE_FISHING_GROUNDDD = URL + "?m=ApiMobile&c=Release&a=fishingGround";
    /*首页*/
    public static final String forumCate = URL + "?m=ApiMobile&c=Index&a=forumCate";

    /*首页 新的*/
    public static final String forumCate2 = URL + "?m=ApiMobile&c=Index&a=forumCateNew";

    /*收藏、取消收藏 */
    public static final String follow = URL + "?m=ApiMobile&c=Fishing&a=follow";
    /*视频-渔具店评论接口*/
    public static final String actionComments = URL + "?m=ApiMobile&c=Common&a=actionComments";
    /*全部钓友上图*/
    public static final String allImages = URL + "?m=ApiMobile&c=Common&a=allImages";
    public static final String releaseSkillShow = URL + "?m=ApiMobile&c=Release&a=releaseSkillShow";//修改
    public static final String releaseSkillSave = URL + "?m=ApiMobile&c=Release&a=releaseSkillSave";//保存
    /*钓场-全部地图显示*/
    public static final String allfishing_shop = URL + "?m=ApiMobile&c=Common&a=allfishing_shop";
    public static final String indexList = URL + "?m=ApiMobile&c=Common&a=indexList";//技巧饵料首页
    public static final String sendLetter = URL + "?m=ApiMobile&c=PersonCenter&a=sendLetter";//发私信
    public static final String letterHistory = URL + "?m=ApiMobile&c=PersonCenter&a=letterHistory";//发私信历史记录
    public static final String bannerForum = URL + "?m=ApiMobile&c=Common&a=bannerForum";//轮播图
    public static final String historyNum = URL + "?m=ApiMobile&c=PersonCenter&a=historyNum";//私信历史记录总条数
    /*相关视频*/
    public static final String videosRelevant = URL + "?m=ApiMobile&c=Release&a=videosRelevant";

    /*首页-公告*/
    public static final String notice = URL + "?m=Home&c=Index&a=notice";
    /*首页-渔乐短片*/
    public static final String INDEX_SHORT_VIDEOS = URL + "?m=ApiMobile&c=Index&a=shortVideos";
    /*钓场详情-渔获详情-渔获帖子*/
    public static final String INDEX_SAME_FORUM = URL + "?m=ApiMobile&c=Index&a=sameForum";

    /**
     * 分享成功后调用
     */
    public static final String fshare = URL + "?m=ApiMobile&c=Common&a=fshare";

    /**
     * 获取最新版本
     */
    public static final String version = URL + "?m=ApiMobile&c=Release&a=version";

    /**
     * 分类列表
     * 参数：无
     */
    public static final String shop_cate_list = URL + "?m=ApiMobile&c=Pdd&a=cate_list";

    /**
     * 商品列表
     * 参数：a = search_goods(搜索 keyword)、recommend(推荐)、cate_goods(分类 cate_id)
     */
    public static final String shop_goods_list = URL + "?m=ApiMobile&c=Pdd";


    /**
     * 商品详情（获取购买跳转连接）
     */
    public static final String shop_goods_detail = URL + "?m=ApiMobile&c=Pdd&a=promotion";

    /**
     * 商品详情连接
     * 参数：goods_id
     */
    public static final String shop_goods_detail_web = "https://mobile.yangkeduo.com/goods.html?";

}
