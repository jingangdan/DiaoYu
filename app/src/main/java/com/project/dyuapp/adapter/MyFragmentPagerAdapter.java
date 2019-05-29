package com.project.dyuapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 任伟
 * @创建时间 2017/03/27 15:37
 * @描述
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mList = new ArrayList<Fragment>();
    private List<String> mListIndex = new ArrayList<>();

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> mListIndex) {
        super(fm);
        this.mList = list;
        this.mListIndex = mListIndex;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mListIndex.get(position);
    }


}