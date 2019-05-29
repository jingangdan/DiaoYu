package com.project.dyuapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.bean.ImageItem;
import com.project.dyuapp.R;
import com.project.dyuapp.activity.AddressActivity;
import com.project.dyuapp.activity.AttentionFansActivity;
import com.project.dyuapp.activity.DraftsActivity;
import com.project.dyuapp.activity.GoldMallActivity;
import com.project.dyuapp.activity.MyMessageActivity;
import com.project.dyuapp.activity.PersonalCenterFishingPlaceActivity;
import com.project.dyuapp.activity.PersonalCenterFishingShopActivity;
import com.project.dyuapp.activity.PersonalInfoActivity;
import com.project.dyuapp.activity.PostListActivity;
import com.project.dyuapp.activity.PualsignActivity;
import com.project.dyuapp.activity.SettingActivity;
import com.project.dyuapp.base.MyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.MymsgBean;
import com.project.dyuapp.entity.PersonEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyPicMethodUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * @describe：我的
 * @author：刘晓丽
 * @createdate：2017/8/18 11:00
 */

public class MeFragment extends MyBaseFragment {

    @Bind(R.id.me_civ_img)
    CircleImageView meCivImg;//头像
    @Bind(R.id.me_tv_name)
    TextView meTvName;//昵称
    @Bind(R.id.me_tv_grade)
    TextView meTvGrade;//等级
    @Bind(R.id.me_tv_gold)
    TextView meTvGold;//金币
    @Bind(R.id.me_tv_integral)
    TextView meTvIntegral;//积分
    @Bind(R.id.me_tv_post)
    TextView meTvPost;//帖子
    @Bind(R.id.me_tv_attention)
    TextView meTvAttention;//关注
    @Bind(R.id.me_tv_fans)
    TextView meTvFans;//粉丝

    ArrayList<ImageItem> imageItems = new ArrayList<>();
    @Bind(R.id.my_iv_mymsgred)
    ImageView myIvMymsgred;
    private boolean isGetInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);
        ButterKnife.bind(this, view);
        isGetInfo = true;
        okhttpPerson();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isGetInfo) {
            okhttpPerson();
        }
        getMessage();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && !isGetInfo) {
            okhttpPerson();
        }
    }

    @OnClick({R.id.me_civ_img, R.id.me_rl_info, R.id.me_ll_post, R.id.me_ll_attention, R.id.me_ll_fans,
            R.id.me_ll_pualsign, R.id.me_ll_collect, R.id.me_ll_site, R.id.me_ll_gold,
            R.id.me_ll_shop, R.id.me_ll_place, R.id.me_ll_message, R.id.me_ll_draft, R.id.me_ll_set})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.me_civ_img:
                preview();
                break;
//            case R.id.me_iv_edit:
            case R.id.me_rl_info:
                //编辑
                startActivity(new Intent(getActivity(), PersonalInfoActivity.class));
                break;
            case R.id.me_ll_post:
                //帖子
                startActivity(new Intent(getActivity(), PostListActivity.class)
                        .putExtra("title", "我的帖子")
                        .putExtra("isMy", true)
                );
                break;
            case R.id.me_ll_attention:
                //关注
                startActivity(new Intent(getActivity(), AttentionFansActivity.class).putExtra("wherefrom", "0"));
                break;
            case R.id.me_ll_fans:
                //粉丝
                startActivity(new Intent(getActivity(), AttentionFansActivity.class).putExtra("wherefrom", "1"));
                break;
            case R.id.me_ll_pualsign:
                //每日签到
                startActivity(new Intent(getActivity(), PualsignActivity.class));
                break;
            case R.id.me_ll_collect:
                //我的收藏
                startActivity(new Intent(getActivity(), PostListActivity.class)
                        .putExtra("title", "我的收藏")
                        .putExtra("isMyCollection", true));
                break;
            case R.id.me_ll_site:
                //收货地址
                startActivity(new Intent(getActivity(), AddressActivity.class));
                break;
            case R.id.me_ll_gold:
                //金币商城
                startActivity(new Intent(getActivity(), GoldMallActivity.class));
                break;
            case R.id.me_ll_shop:
                //我的渔具店
                startActivity(new Intent(getActivity(), PersonalCenterFishingShopActivity.class));
                break;
            case R.id.me_ll_place:
                //我的钓场
                startActivity(new Intent(getActivity(), PersonalCenterFishingPlaceActivity.class));
                break;
            case R.id.me_ll_message:
                //我的消息
                startActivity(new Intent(getActivity(), MyMessageActivity.class));
                break;
            case R.id.me_ll_draft:
                //草稿箱
                startActivity(new Intent(getActivity(), DraftsActivity.class));
                break;
            case R.id.me_ll_set:
                //设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    private void preview() {
        MyPicMethodUtil.previewHead(getActivity(),imageItems);
//        Intent intentPreview = new Intent(getActivity(), ImagePreviewDelActivity.class);
//        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageItems);
//        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
//        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
//        intentPreview.putExtra("edit", false);
//        intentPreview.putExtra("isShowNum", false);
//        startActivityForResult(intentPreview, PublicStaticData.REQUEST_CODE_PREVIEW);
    }

    /**
     * 个人中心
     */
    private void okhttpPerson() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.person);
        commonOkhttp.putCallback(new MyGenericsCallback<PersonEntity>(getActivity()) {
            @Override
            public void onSuccess(PersonEntity data, int d) {
                super.onSuccess(data, d);
                isGetInfo = false;
                //头像
                imageItems.clear();
                imageItems.add(new ImageItem("", HttpUrl.IMAGE_URL + data.getMember_list_headpic()));
                GlideUtils.loadImageViewNo(getActivity(), HttpUrl.IMAGE_URL + data.getMember_list_headpic(), meCivImg);
                meTvName.setText(data.getMember_list_nickname());//昵称
                if (!TextUtils.isEmpty(data.getLevel())) {
                    meTvGrade.setText("Lv." + data.getLevel());//等级
                }
                meTvGold.setText(data.getMoney());//金币
                meTvIntegral.setText(data.getScores());//积分
                meTvPost.setText(data.getTienum());//帖子
                meTvAttention.setText(data.getGuannum());//关注
                meTvFans.setText(data.getFennum());//粉丝
            }

            @Override
            public void onOther(JsonResult<PersonEntity> data, int d) {
                super.onOther(data, d);
                isGetInfo = false;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                isGetInfo = false;
            }
        });
        commonOkhttp.Execute();
    }


    /**
     * 获取未读消息数
     */
    public void getMessage() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.mymsg);
        commonOkhttp.putCallback(new MyGenericsCallback<MymsgBean>(getActivity()) {
            @Override
            public void onSuccess(MymsgBean data, int d) {
                super.onSuccess(data, d);
                //有消息时“我的”显示红点
                if ((!TextUtils.isEmpty(data.getSixin()) && !data.getSixin().equals("0")) ||
                        (!TextUtils.isEmpty(data.getReply()) && !data.getReply().equals("0")) ||
                        (!TextUtils.isEmpty(data.getFabulous()) && !data.getFabulous().equals("0")) ||
                        (!TextUtils.isEmpty(data.getReward()) && !data.getReward().equals("0")) ||
                        (!TextUtils.isEmpty(data.getSystem()) && !data.getSystem().equals("0"))) {
                    myIvMymsgred.setVisibility(View.VISIBLE);
                } else {
                   // myIvMymsgred.setVisibility(View.GONE);
                }

            }
        });
        commonOkhttp.Execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
