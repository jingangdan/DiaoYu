package com.project.dyuapp.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.ClassifyDownAdapter;
import com.project.dyuapp.adapter.MyFragmentPagerAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.BannerForumEntity;
import com.project.dyuapp.entity.BannerForumListEntity;
import com.project.dyuapp.entity.SiteCategoryEntity;
import com.project.dyuapp.entity.SiteCategoryListEntity;
import com.project.dyuapp.fragment.HomeSkillFragment;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myviews.Banner;
import com.project.dyuapp.myviews.CustomViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.project.dyuapp.myutils.PublicStaticData.ID_SKILL;

/**
 * @author gengqiufang
 * @describtion 首页技巧
 * @created at 2017/11/29 0029
 */
public class HomeSkillActivity extends MyBaseActivity {
    @Bind(R.id.home_skill_banner)
    Banner banner;
    @Bind(R.id.home_skill_ll_search)
    LinearLayout llSearch;
    @Bind(R.id.home_skill_xTablayout)
    XTabLayout xTablayout;
    @Bind(R.id.home_skill_iv_more)
    ImageView homeSkillIvMore;
    @Bind(R.id.home_skill_vp)
    CustomViewPager vp;
    @Bind(R.id.home_skill_psv)
    PullToRefreshScrollView psv;
    @Bind(R.id.home_skill_rl_tab)
    RelativeLayout rlTab;
    @Bind(R.id.home_skill_xTablayout_top)
    XTabLayout xTablayoutTop;
    @Bind(R.id.home_skill_iv_more_top)
    ImageView ivMoreTop;
    @Bind(R.id.home_skill_rl_tab_top)
    RelativeLayout rlTabTop;

    private MyFragmentPagerAdapter mAdapter;
    private ArrayList<Fragment> mDataFragment = new ArrayList(); //frgment 集合
    private ArrayList<String> mDataIndicator = new ArrayList<>();//导航标题
    private ArrayList<SiteCategoryEntity> mDataCategory = new ArrayList<>();

    private PopupWindow popAll;//分类
    private int page = 1;

    private int currentItem = 0; // 当前图片的索引号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_home_skill);
        ButterKnife.bind(this);
        okHttpGetSiteCategory();//获取分类
        okHttpBannerForum();
        initTitle();
        initView();
    }

    //初始化标题
    private void initTitle() {
        setIvBack();
        setTvTitle("技巧");
        TextView tvRight = getTvRight();
        tvRight.setText("发布");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtils.getPreference(HomeSkillActivity.this,"userid"))) {
                    //已登录
                    startActivity(new Intent(HomeSkillActivity.this, PublishPostActivity.class)
                            .putExtra("title", "技巧")
                            .putExtra("classify", "技巧分类")
                            .putExtra("topId", ID_SKILL));
                }else {
                    //未登录
                    goToActivity(LoginActivity.class);
                }

            }
        });
    }

    @OnClick({R.id.home_skill_ll_search, R.id.home_skill_iv_more, R.id.home_skill_iv_more_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_skill_ll_search:
                //搜索
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.home_skill_iv_more:
                //更多分类
                showPopMore(rlTab);
                break;
            case R.id.home_skill_iv_more_top:
                //更多分类
                showPopMore(rlTabTop);
                break;
        }
    }

    private void initView() {
        psv.getRefreshableView().getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (psv.getRefreshableView().getScrollY() >= rlTab.getTop()) {
                    rlTabTop.setVisibility(View.VISIBLE);
                } else {
                    rlTabTop.setVisibility(View.GONE);
                }
            }
        });
        psv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                ((HomeSkillFragment) mDataFragment.get(currentItem)).refresh(mDataCategory.get(currentItem).getD_id(), true, refreshView);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                ((HomeSkillFragment) mDataFragment.get(currentItem)).refresh(mDataCategory.get(currentItem).getD_id(), false, refreshView);
            }
        });
    }

    //轮播图
    public void okHttpBannerForum() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.bannerForum);
        commonOkhttp.putCallback(new MyGenericsCallback<BannerForumListEntity>(this) {
            @Override
            public void onSuccess(BannerForumListEntity data, int d) {
                super.onSuccess(data, d);
                initBanner(data);
            }
        });
        commonOkhttp.Execute();
    }

    //初始化轮播图
    private void initBanner(final BannerForumListEntity data) {
        ArrayList mDataPic = new ArrayList();//轮播图数据
        for (BannerForumEntity item : data) {
            mDataPic.add(HttpUrl.IMAGE_URL + item.getThumb_img());
        }
        // banner.getLayoutParams().height = DensityUtil.getScreenSize(getActivity()).x * 5 / 9;
        //设置样式
        banner.setBannerStyle(Banner.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(Banner.CENTER);
        banner.setDelayTime(3000);//设置轮播间隔时间
        banner.setImages(mDataPic);
        //点击轮播图查看详情
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
                startActivity(new Intent(HomeSkillActivity.this,SkillDetailsActivity.class)
                        .putExtra("id",data.get(position-1).getF_id()));
            }
        });
    }
    //初始化ViewPager
    private void initViewPager() {
        for (int i = 0; i < mDataCategory.size(); i++) {
            mDataFragment.add(HomeSkillFragment.newInstance(mDataCategory.get(i).getD_id()));
            mDataIndicator.add(mDataCategory.get(i).getName());
        }
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mDataFragment, mDataIndicator);
        vp.setAdapter(mAdapter);
        xTablayout.setupWithViewPager(vp);
        xTablayoutTop.setupWithViewPager(vp);
//        psv.getRefreshableView().smoothScrollTo(0, 0);//布局加载完调用
        banner.requestFocus();// 解决viewpager滑动，定位不准问题http://blog.csdn.net/gs12software/article/details/47978337
        banner.setFocusable(true);
        banner.setFocusableInTouchMode(true);
        vp.setOnPageChangeListener(new MyPageChangeListener());
    }

    private void showPopMore(View view) {
        View viewPop = LayoutInflater.from(this).inflate(R.layout.pop_classify, null);
        GridView gv = (GridView) viewPop.findViewById(R.id.pop_classify_gv);
        final ClassifyDownAdapter adpter = new ClassifyDownAdapter(mDataCategory, this);
        gv.setAdapter(adpter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (SiteCategoryEntity item : mDataCategory) {
                    item.setSel(false);
                }
                xTablayout.getTabAt(position).select();
                xTablayoutTop.getTabAt(position).select();
                mDataCategory.get(position).setSel(true);
                adpter.notifyDataSetChanged();
                popAll.dismiss();
            }
        });
        popAll = new PopupWindow(viewPop, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popAll.setOutsideTouchable(true);
        popAll.setBackgroundDrawable(new BitmapDrawable());
        popAll.showAsDropDown(view);
    }

    //4站点分类 集合接口
    public void okHttpGetSiteCategory() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.getSiteCategory);
        commonOkhttp.putParams("cid", ID_SKILL);
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

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author Administrator
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        public void onPageSelected(int position) {
            currentItem = position;
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
    }
}
