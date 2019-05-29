package com.project.dyuapp.entity;

import com.project.dyuapp.activity.MyFishShopAddBean;

import java.util.ArrayList;

/**
 * @author
 * @data 2017/4/12
 * @describe class description
 * @change administrator 2017/4/12 modify content
 */

public class NetBean {
    /*通用无反参*/
    public static class EmptytEntity extends ArrayList<EmptyResultBean> {
    }

    /*获取所有省市接口*/
    public static class LivingPlaceBeanList extends ArrayList<LivingPlaceBean> {
    }

    /*获取所有省市县地区接口*/
    public static class LivingPlaceAllListEntity extends ArrayList<LIvingPlaceAllEntity> {
    }

    /*分级获取地址*/
    public static class LivingPlaceSelectListEntity extends ArrayList<LivingPlaceSelectEntity> {
    }


    /*我的消息-我的私信*/
    public static class MyLetterEntity extends ArrayList<MyLetterBean> {
    }

    /*我的消息-回复*/
    public static class MyReplyEntity extends ArrayList<MyReplyBean> {
    }

    /*我的消息-赞过我*/
    public static class MyPraiseEntity extends ArrayList<MyPraiseBean> {
    }

    /*我的消息-系统消息*/
    public static class MySystemEntity extends ArrayList<MySystemBean> {
    }

    /*我的消息-打赏*/
    public static class MyRewardEntity extends ArrayList<MyRewardBean> {
    }

    /*我的消息-发现周边钓友*/
    public static class NearByFdEntity extends ArrayList<NearByBean> {
    }

    /*个人中心-粉丝*/
    public static class FansListEntity extends ArrayList<FansEntity> {
    }


    /*14个人中心-每日签到-等级规则*/
    public static class LvlRuleListEntity extends ArrayList<LvlRuleEntity> {

    }

    /*8个人中心-服务地址 */
    public static class ServiceAddressListEntity extends ArrayList<SelectAddressEntity> {
    }

    /*钓场类型*/
    public static class FishingPlaceTypeListEntity extends ArrayList<FishingPlaceTypeEntity> {
    }

    /*钓场鱼种*/
    public static class FingerlingListEntity extends ArrayList<FingerlingEntity> {
    }

    /*渔具店列表*/
    public static class FishingGearListEntity extends ArrayList<FishingGearEntity> {
    }

    /*渔具店区域筛选*/
    public static class FishingGearAreaEntity extends ArrayList<FishingGearAreaBean> {
    }

    /*钓场类型(钓场筛选)*/
    public static class DrawerListEntity extends ArrayList<DrawerListBean> {
    }

    /*钓场列表*/
    public static class HomeFishingPlaceListEntity extends ArrayList<HomeFishingPlaceEntity> {

    }

    /*搜索历史列表*/
    public static class SearchHistoryEntity extends ArrayList<SearchHistoryBean> {
    }

    /*视频列表*/
    public static class VideoListEntity extends ArrayList<VideoListBean> {
    }

    /*个人中心-渔具店-我添加的*/
    public static class MyFishShopAddEntity extends ArrayList<MyFishShopAddBean> {
    }

    /*个人中心-渔具店-我点评的*/
    public static class MyFishShopCommentEntity extends ArrayList<MyFishShopCommentBean> {
    }

    /*个人中心-草稿箱列表*/
    public static class DraftBoxEntity extends ArrayList<DraftBoxBean> {
    }

    /*首页-钓场-钓场详情-全部钓友上图*/
    public static class FishingPlaceImageListEntity extends ArrayList<FishingPlaceEntity.ImagesBean> {
    }

    /*13个人中心-每日签到-查看历史记录*/
    public static class SignHistoryListEntity extends ArrayList<SignHistoryEntity> {
    }

    /*7同城-附近钓友*/
    public static class FishingFriendListEntity extends ArrayList<FishingFriendEntity> {
    }

    /*二级分类下的视频列表实体*/
    public static class NewestListEntity extends ArrayList<NewestListBean> {
    }

    /*3首页-渔获-详情-点赞*/
    public static class DianzanListEntity extends ArrayList<FishingDianzanEntity> {
    }

    /*个人中心-我的钓场-我关注的*/
    public static class AttentionPlaceListEntity extends ArrayList<AttentionPlaceEntity> {
    } /*个人中心-我的钓场-我关注的*/

    public static class FIshingFriendsImageEntity extends ArrayList<FIshingFriendsImageBean> {
    }

    /*搜索-热门关键词*/
    public static class HotEntity extends ArrayList<String>{}

    /*首页-公告*/
    public static class NoticeEntity extends ArrayList<NoticeBean>{}
    /*首页-渔乐短片*/
    public static class HarvestShortListEntity extends ArrayList<HarvestShortEntity>{}
    /*首页-渔乐短片(优酷)*/
    public static class HarvestShortYoukuListEntity extends ArrayList<HarvestShortYoukuEntity>{}

}
