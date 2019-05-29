package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.MyFragmentPagerAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.SiteCategoryEntity;
import com.project.dyuapp.entity.SiteCategoryListEntity;
import com.project.dyuapp.fragment.PostListFragment;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.project.dyuapp.myutils.PublicStaticData.ID_BAIT;

/**
 * @author gengqiufang
 * @describtion 首页-饵料
 * @created at 2017/11/30 0030
 * .
 */
public class HomeBaitActivity extends MyBaseActivity {

    @Bind(R.id.home_bait_xTablayout)
    XTabLayout xTablayout;
    @Bind(R.id.home_bait_vp)
    ViewPager vp;

    private MyFragmentPagerAdapter mAdapter;
    private ArrayList<Fragment> mDataFragment = new ArrayList(); //frgment 集合
    private ArrayList<String> mDataIndicator = new ArrayList<>();//导航标题
    private ArrayList<SiteCategoryEntity> mDataCategory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_home_bait);
        ButterKnife.bind(this);
        initTitle();
        okHttpGetSiteCategory();
    }

    private void initTitle() {
        setIvBack();
        setTvTitle("饵料");
        TextView tvRight = getTvRight();
        tvRight.setText("发布");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtils.getPreference(HomeBaitActivity.this,"userid"))) {
                    //已登录
                    startActivity(new Intent(HomeBaitActivity.this, PublishPostActivity.class)
                            .putExtra("title", "饵料").putExtra("classify", "饵料分类")
                            .putExtra("topId", ID_BAIT));
                }else {
                    //未登录
                    goToActivity(LoginActivity.class);
                }
            }
        });
    }

    //4站点分类 集合接口
    public void okHttpGetSiteCategory() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.getSiteCategory);
        commonOkhttp.putParams("cid", ID_BAIT);
        commonOkhttp.putCallback(new MyGenericsCallback<SiteCategoryListEntity>(this) {
            @Override
            public void onSuccess(SiteCategoryListEntity data, int d) {
                super.onSuccess(data, d);
                mDataCategory.clear();
                mDataCategory.add(new SiteCategoryEntity("", "首页", true));
                mDataCategory.addAll(data);
                initViewPager();
            }
        });
        commonOkhttp.Execute();
    }

    //初始化ViewPager
    private void initViewPager() {
        for (int i = 0; i < mDataCategory.size(); i++) {
            mDataFragment.add(PostListFragment.newInstance(mDataCategory.get(i).getD_id()));
            mDataIndicator.add(mDataCategory.get(i).getName());
        }
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mDataFragment, mDataIndicator);
        vp.setAdapter(mAdapter);
        xTablayout.setupWithViewPager(vp);
//        psv.getRefreshableView().smoothScrollTo(0, 0);//布局加载完调用
    }
}
