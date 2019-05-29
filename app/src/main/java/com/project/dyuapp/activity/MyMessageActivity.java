package com.project.dyuapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.MymsgBean;
import com.project.dyuapp.myutils.HttpUrl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author litongtong
 * @created on 2017/11/27 18:21
 * @description 我的-我的消息
 * @change ${}
 */

public class MyMessageActivity extends MyBaseActivity {
    @Bind(R.id.mymsg_iv_myletterred)
    ImageView mymsgIvMyletterred;
    @Bind(R.id.mymsg_iv_relpyred)
    ImageView mymsgIvRelpyred;
    @Bind(R.id.mymsg_iv_likemered)
    ImageView mymsgIvLikemered;
    @Bind(R.id.mymsg_iv_rewardred)
    ImageView mymsgIvRewardred;
    @Bind(R.id.mymsg_iv_sysred)
    ImageView mymsgIvSysred;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_mymessage);
        ButterKnife.bind(this);
        setIvBack();//返回
        setTvTitle("我的消息");//标题

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    /**
     * 获取未读消息数
     */
    private void getData() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.mymsg);
        commonOkhttp.putCallback(new MyGenericsCallback<MymsgBean>(this) {
            @Override
            public void onSuccess(MymsgBean data, int d) {
                super.onSuccess(data, d);
                if(!TextUtils.isEmpty(data.getSixin())&&!data.getSixin().equals("0")){
                    mymsgIvMyletterred.setVisibility(View.VISIBLE);
                }else {
                    mymsgIvMyletterred.setVisibility(View.GONE);
                }
                if(!TextUtils.isEmpty(data.getReply())&&!data.getReply().equals("0")){
                    mymsgIvRelpyred.setVisibility(View.VISIBLE);
                }else {
                    mymsgIvRelpyred.setVisibility(View.GONE);
                }
                if(!TextUtils.isEmpty(data.getFabulous())&&!data.getFabulous().equals("0")){
                    mymsgIvLikemered.setVisibility(View.VISIBLE);
                }else {
                    mymsgIvLikemered.setVisibility(View.GONE);
                }
                if(!TextUtils.isEmpty(data.getReward())&&!data.getReward().equals("0")){
                    mymsgIvRewardred.setVisibility(View.VISIBLE);
                }else {
                    mymsgIvRewardred.setVisibility(View.GONE);
                }
                if(!TextUtils.isEmpty(data.getSystem())&&!data.getSystem().equals("0")){
                    mymsgIvSysred.setVisibility(View.VISIBLE);
                }else {
                    mymsgIvSysred.setVisibility(View.GONE);
                }
            }
        });
        commonOkhttp.Execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.mymsg_ll_myletter, R.id.mymsg_ll_nearby, R.id.mymsg_ll_relpy, R.id.mymsg_ll_likeme, R.id.mymsg_ll_reward, R.id.mymsg_ll_sysmsg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mymsg_ll_myletter:
                //我的私信
                goToActivity(MyPrivateLetterActivity.class);
                break;
            case R.id.mymsg_ll_nearby:
                //发现周边钓友
                goToActivity(FindNearbyFrendActivity.class);
                break;
            case R.id.mymsg_ll_relpy:
                //回复
                goToActivity(ReplyActivity.class);
                break;
            case R.id.mymsg_ll_likeme:
                //赞过我
                goToActivity(PraiseMeActivity.class);
                break;
            case R.id.mymsg_ll_reward:
                //打赏
                goToActivity(RewardActivity.class);
                break;
            case R.id.mymsg_ll_sysmsg:
                //系统消息
                goToActivity(SystemMsgActivity.class);
                break;
        }
    }
}
