package com.project.dyuapp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CommonPagerAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.SiteCategoryEntity;
import com.project.dyuapp.entity.SiteCategoryListEntity;
import com.project.dyuapp.fragment.HomeVideoFragment;
import com.project.dyuapp.fragment.OtherVideoFragment;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 田亭亭
 * @description 首页-视频
 * @created at 2017/12/2 0002
 * @change
 */
public class HomeVideoActivity extends MyBaseActivity {


    @Bind(R.id.home_video_xTabLayout)
    XTabLayout xTabLayout;
    @Bind(R.id.home_video_vp)
    ViewPager homeVideoVp;
    //四海钓鱼，快乐垂钓，鱼种，钓技，学习，自拍
    private List<String> titles;
    private List<Fragment> mFragments;
    private HomeVideoFragment mFragment;
    private OtherVideoFragment mOtherFragment;
    private CommonPagerAdapter mAdapter;

    private List<SiteCategoryEntity> siteCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_home_video);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("视频");
        okHttpGetSiteCategory();
    }

    //4站点分类 集合接口
    public void okHttpGetSiteCategory() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.getSiteCategory);
        commonOkhttp.putParams("cid", PublicStaticData.ID_VIDEO);
        commonOkhttp.putCallback(new MyGenericsCallback<SiteCategoryListEntity>(HomeVideoActivity.this) {
            @Override
            public void onSuccess(SiteCategoryListEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    siteCategoryList = new ArrayList<SiteCategoryEntity>();
                    siteCategoryList.add(new SiteCategoryEntity("", "首页"));
                    if (data != null && data.size() != 0) {
                        siteCategoryList.addAll(data);
                        titles = new ArrayList<>();
                        titles.add(0, "首页");
                        for (int i = 0; i < data.size(); i++) {
                            titles.add(data.get(i).getName());
                        }

                        String[] array = new String[titles.size()];
                        // List转换成数组
                        for (int i = 0; i < titles.size(); i++) {
                            array[i] = titles.get(i);
                        }
                        mAdapter = new CommonPagerAdapter(HomeVideoActivity.this.getSupportFragmentManager());
                        mAdapter.setTitles(array);//标题
                        mFragments = new ArrayList<>();
                        for (int i = 0; i < titles.size(); i++) {
                            if (i == 0) {
                                mFragment = new HomeVideoFragment();//首页
                                mFragments.add(mFragment);
                            } else {
                                mOtherFragment = new OtherVideoFragment();//其他
                                Bundle bundle = new Bundle();
                                bundle.putString("cid", siteCategoryList.get(i).getD_id());
                                mOtherFragment.setArguments(bundle);
                                mFragments.add(mOtherFragment);
                            }
                        }
                        //把要显示的fragment集合传给adapter
                        mAdapter.setFragments(mFragments);
                        //给viewPager设置适配器
                        homeVideoVp.setAdapter(mAdapter);
                        setXTabLayout();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        commonOkhttp.Execute();
    }

    private void setXTabLayout() {
        /**
         * 设置tablayout的模式
         *  模式一：MODE_SCROLLABLE  可以滑动，显示很多个tab中的几个展示出来
         *  模式二：MODE_FIXED Fixed tabs display all tabs concurrently   展示出所有的，适合三四个tab
         */
        xTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //TabLayout绑定ViewPager
        xTabLayout.setupWithViewPager(homeVideoVp);
        homeVideoVp.setOffscreenPageLimit(0);
    }
}
