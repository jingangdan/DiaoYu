package com.project.dyuapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ${田亭亭} on 2017/11/28 0028.
 *
 * @description
 * @change
 */


public class CommonPagerAdapter extends FragmentStatePagerAdapter {

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
    public CommonPagerAdapter(FragmentManager fm) {
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
