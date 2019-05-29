package com.project.dyuapp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CommonPagerAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.fragment.MyAppendPlaceFragment;
import com.project.dyuapp.fragment.MyAttentionPlaceFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonalCenterFishingPlaceActivity extends MyBaseActivity {

    @Bind(R.id.center_fishing_place_xTabLayout)
    XTabLayout xTabLayout;
    @Bind(R.id.center_fishing_place_vp)
    ViewPager fishingPlaceVp;
    //fragment存储器
    private List<Fragment> mFragments;
    //tab条目中的内容
    private String[] titles = {"我关注的", "我添加的"};
    private CommonPagerAdapter mAdapter;
    private MyAttentionPlaceFragment myAttentionFragment;
    private MyAppendPlaceFragment myAppendFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_personal_center_fishing_place);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("我的钓场");
        mAdapter = new CommonPagerAdapter(this.getSupportFragmentManager());
        mAdapter.setTitles(titles);//标题
        mFragments = new ArrayList<>();
        myAttentionFragment = new MyAttentionPlaceFragment();//关注
        myAppendFragment = new MyAppendPlaceFragment();//粉丝
        mFragments.add(myAttentionFragment);
        mFragments.add(myAppendFragment);
        //把要显示的fragment集合传给adapter
        mAdapter.setFragments(mFragments);
        /**
         * 设置tablayout的模式
         *  模式一：MODE_SCROLLABLE  可以滑动，显示很多个tab中的几个展示出来
         *  模式二：MODE_FIXED Fixed tabs display all tabs concurrently   展示出所有的，适合三四个tab
         */
        xTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //给viewPager设置适配器
        fishingPlaceVp.setAdapter(mAdapter);
        //TabLayout绑定ViewPager
        xTabLayout.setupWithViewPager(fishingPlaceVp);
        xTabLayout.getTabAt(0).select();
    }
}
