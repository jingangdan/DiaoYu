package com.project.dyuapp.search;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.xtablayout.XTabLayout;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CommonPagerAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.fragment.OtherVideoFragment;
import com.project.dyuapp.myutils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索结果
 * Created by jingang on 2018/3/10.
 */
public class SearchMessageActivity extends MyBaseActivity implements SearchInterface {

    @Bind(R.id.search_tv_type)
    TextView searchTvType;
    @Bind(R.id.search_et_content)
    EditText searchEtContent;

    @Bind(R.id.search_message_xTabLayout)
    XTabLayout xTabLayout;
    @Bind(R.id.search_message_vp)
    ViewPager searchMessageVp;

    /*综合 钓场 渔具店 视频 帖子*/
    private String[] titles = new String[]{"综合", "商品", "帖子", "钓场", "渔具店"};

    private List<Fragment> mFragments;
    // private HomeVideoFragment mFragment;
    private SynthesizSearchFragment mFragment;
    private OtherSearchFragment mOtherFragment;
    private CommonPagerAdapter mAdapter;

    private List<SearchMessageEntity> siteCategoryList;

    private String type = "";//搜索类型
    private String word = "";//搜索字

    private String keyWords = "";
    private String lat = "", lng = "";//经纬度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_search_message);
        ButterKnife.bind(this);

        //去掉搜索左侧的选项
        searchTvType.setVisibility(View.GONE);

        lat = SPUtils.getPreference(this, "latitude");
        lng = SPUtils.getPreference(this, "longitude");

        //获取传参
        if (!TextUtils.isEmpty(getIntent().getStringExtra("type"))) {
            type = getIntent().getStringExtra("type");
            searchTvType.setText(type);
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("word"))) {
            word = getIntent().getStringExtra("word");
            searchEtContent.setText(word);
        }


        setTabLayout();
    }

    public void setTabLayout() {
        mAdapter = new CommonPagerAdapter(getSupportFragmentManager());
        mAdapter.setTitles(titles);//标题
        mFragments = new ArrayList<>();
        keyWords = searchEtContent.getText().toString();
        for (int i = 0; i < titles.length; i++) {
            if (i == 0) {
                type = "综合";
                mFragment = new SynthesizSearchFragment();//首页
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                bundle.putString("keyWords", keyWords);
                bundle.putString("lat", lat);
                bundle.putString("lng", lng);
                mFragment.setArguments(bundle);
                mFragments.add(mFragment);
                mFragment.setSearchInterface(this);

            } else {
                mOtherFragment = new OtherSearchFragment();//其他
                if (i == 1) {
                    type = "商品";
                }
                if (i == 2) {
                    type = "帖子";
                }
                if (i == 3) {
                    type = "钓场";
                }
                if (i == 4) {
                    type = "渔具店";
                }
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                bundle.putString("keyWords", keyWords);
                bundle.putString("lat", lat);
                bundle.putString("lng", lng);
                mOtherFragment.setArguments(bundle);
                mFragments.add(mOtherFragment);
            }
        }
        //把要显示的fragment集合传给adapter
        mAdapter.setFragments(mFragments);
        //给viewPager设置适配器
        searchMessageVp.setAdapter(mAdapter);
        setXTabLayout(0);
    }

    private void setXTabLayout(int tab) {
        /**
         * 设置tablayout的模式
         *  模式一：MODE_SCROLLABLE  可以滑动，显示很多个tab中的几个展示出来
         *  模式二：MODE_FIXED Fixed tabs display all tabs concurrently   展示出所有的，适合三四个tab
         */
        xTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //TabLayout绑定ViewPager
        xTabLayout.setupWithViewPager(searchMessageVp);
        searchMessageVp.setOffscreenPageLimit(titles.length);

        searchMessageVp.setCurrentItem(tab);
    }

    @OnClick({R.id.search_tv_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_tv_cancle:
                //取消
                finishActivity();
                // startActivity(new Intent(this, SearchActivity.class));
                break;
        }
    }

    @Override
    public void doTabCode(int code) {
        setXTabLayout(code);
    }
}

