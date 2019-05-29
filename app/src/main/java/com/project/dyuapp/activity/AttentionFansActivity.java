package com.project.dyuapp.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.fragment.AttentionFragment;
import com.project.dyuapp.fragment.FansFragment;
import com.project.dyuapp.myutils.ScreenManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author shipeiyun
 * @description
 * @created at 2017/11/27 0027
 */
public class AttentionFansActivity extends MyBaseActivity {

    @Bind(R.id.attention_fans_lv_back)
    LinearLayout attentionFansIvBack;
    @Bind(R.id.attention_fans_xTabLayout)
    XTabLayout attentionFansXTabLayout;
    @Bind(R.id.attention_fans_vp)
    ViewPager attentionFansVp;

    //fragment存储器
    private List<Fragment> mFragments;
    //tab条目中的内容
    private String[] titles = {"关注", "粉丝"};
    private FixedPagerAdapter mAdapter;
    private AttentionFragment attentionFragment;
    private FansFragment fansFragment;
    private String wherefrom;//0 关注  1 粉丝

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_fans);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化数据
     */
    private void initView(){
        wherefrom = getIntent().getStringExtra("wherefrom");
        //返回
        attentionFansIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManager.getInstance().removeActivity(AttentionFansActivity.this);
            }
        });
        mAdapter = new FixedPagerAdapter(this.getSupportFragmentManager());
        mAdapter.setTitles(titles);//标题
        mFragments = new ArrayList<>();
        attentionFragment = new AttentionFragment();//关注
        fansFragment = new FansFragment();//粉丝
        mFragments.add(attentionFragment);
        mFragments.add(fansFragment);
        //把要显示的fragment集合传给adapter
        mAdapter.setFragments(mFragments);
        /**
         * 设置tablayout的模式
         *  模式一：MODE_SCROLLABLE  可以滑动，显示很多个tab中的几个展示出来
         *  模式二：MODE_FIXED Fixed tabs display all tabs concurrently   展示出所有的，适合三四个tab
         */
        attentionFansXTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //给viewPager设置适配器
        attentionFansVp.setAdapter(mAdapter);
        //TabLayout绑定ViewPager
        attentionFansXTabLayout.setupWithViewPager(attentionFansVp);
        if (wherefrom.equals("0")){
            attentionFansXTabLayout.getTabAt(0).select();
        } else if (wherefrom.equals("1")){
            attentionFansXTabLayout.getTabAt(1).select();
        }
    }

    public class FixedPagerAdapter extends FragmentStatePagerAdapter {

        private String[] titles;

        /**
         * 设置标题
         *
         * @param titles
         */
        public void setTitles(String[] titles) {
            this.titles = titles;
        }

        private List<Fragment> mFragments;

        /**
         * 这个是在继承FragmentStatePagerAdapter会强制写入的
         *
         * @param fm
         */
        public FixedPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        /**
         * Return the number of views available.
         * 返回一个可以用的view的个数
         *
         * @return
         */
        @Override
        public int getCount() {
            return mFragments.size();
        }

        /**
         * Create the page for the given position. The adapter is responsible for
         * adding the view to the container given here,
         * although it only must ensure this is done by the time it returns from finishUpdate(ViewGroup).
         * 这个同destroyItem（）相反，是对于给定的位置创建视图，适配器往container中添加
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = null;
            fragment = (Fragment) super.instantiateItem(container, position);
            return fragment;
        }

        public List<Fragment> getFragments() {
            return mFragments;
        }

        public void setFragments(List<Fragment> fragments) {
            mFragments = fragments;
        }

        /**
         * Remove a page for the given position. The adapter is responsible for
         * removing the view from its container,
         * although it only must ensure this is done by the time it returns from finishUpdate(View).
         * 移除给定位置的数据，适配器负责从container（容器）中取出，但是这个必须保证是在finishUpdate（view）
         * 返回的时间内完成
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        /**
         * 得到滑动页面的Title
         *
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
