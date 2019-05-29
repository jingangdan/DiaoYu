package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.DrawerAreaListAdapter;
import com.project.dyuapp.adapter.DrawerShowTypeListAdapter;
import com.project.dyuapp.adapter.HomeFishingShopAdapter;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.DrawerListBean;
import com.project.dyuapp.entity.FishingGearAreaBean;
import com.project.dyuapp.entity.FishingGearEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myutils.ToastUtils;
import com.project.dyuapp.myviews.GridViewForScrollView;
import com.project.dyuapp.myviews.ListViewForScrollView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 田亭亭
 * @description 首页渔具店Activity
 * @created at 2017/11/25 0025
 * @change
 */
public class HomeFishingShopActivity extends AutoLayoutActivity {

    @Bind(R.id.fishing_shop_tv_distance)
    TextView tvDistance;//距离
    @Bind(R.id.fishing_shop_tv_comprehensive)
    TextView tvComprehensive;//综合
    @Bind(R.id.fishing_shop_plv)
    PullToRefreshListView homeFishingShopPlv;//钓场列表控件
    @Bind(R.id.title_tv_title)
    TextView titleTvTitle;//标题
    @Bind(R.id.title_tv_right)
    TextView titleTvRight;//右侧标题
    @Bind(R.id.title_ibtn_back)
    ImageButton titleIbtnBack;//返回按钮
    @Bind(R.id.drawer_screen_shop_gv_show_type)
    GridViewForScrollView gvShowType;//显示类型
    @Bind(R.id.drawer_screen_shop_lv_area)
    ListViewForScrollView lvArea;//区域列表
    @Bind(R.id.drawer_screen_shop)
    DrawerLayout drawerScreenShop;
    private HomeFishingShopAdapter mAdapter;//渔具店列表适配器
    private List<FishingGearEntity> homeFishingShopList;//渔具列表数据集合

    private DrawerShowTypeListAdapter mShowTypeAdapter;//显示类型适配器
    private DrawerAreaListAdapter mAreaListAdapter;//区域适配器
    private int selectShowTypePosition = 0;
    private int selectAreaListPosition = 0;
    private List<DrawerListBean> mShowTypeList;//显示类型集合
    private List<FishingGearAreaBean> mAreaList;//区域集合
    private int page = 1;
    private String order = "";
    private String countyid = "";
    private String city = "";
    private String lat = "";
    private String lng = "";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fishing_shop);
        ButterKnife.bind(this);
        setSlideWidth();
        initView();
        initData();
        initDrawerLayoutData();
    }

    /**
     * 设置抽屉宽度
     */
    private void setSlideWidth() {
        int windowsWight = getWindowManager().getDefaultDisplay().getWidth() * 3 / 4;
        View leftMenu = findViewById(R.id.drawer_screen_shop_fl_right);
        ViewGroup.LayoutParams leftParams = leftMenu.getLayoutParams();
        leftParams.width = windowsWight;
        leftMenu.setLayoutParams(leftParams);
    }

    /**
     * 设置渔具店列表数据
     */
    private void initData() {
        homeFishingShopList = new ArrayList<>();
        mAdapter = new HomeFishingShopAdapter(homeFishingShopList, HomeFishingShopActivity.this, R.layout.item_home_fishing_shop, "");
        homeFishingShopPlv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new HomeFishingShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivityForResult(new Intent(HomeFishingShopActivity.this, FishingShopDetailsActivity.class)
                        .putExtra("id", homeFishingShopList.get(position).getFishing_shop_id())
                        .putExtra("lat", homeFishingShopList.get(position).getLatitude())
                        .putExtra("lon", homeFishingShopList.get(position).getLongitude())
                        .putExtra("cityid", homeFishingShopList.get(position).getCityid()), PublicStaticData.HOME_FISHING_SHOP);
            }
        });
        homeFishingShopPlv.setMode(PullToRefreshBase.Mode.BOTH);
        homeFishingShopPlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                okhttpFishingGear();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                okhttpFishingGear();
            }
        });
        page = 1;
        okhttpFishingGear();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        titleTvTitle.setVisibility(View.VISIBLE);
        titleTvRight.setVisibility(View.VISIBLE);
        titleIbtnBack.setVisibility(View.VISIBLE);
        titleTvTitle.setText("渔具店");
        titleTvRight.setText("添加");
        titleIbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenManager.getInstance().removeActivity(HomeFishingShopActivity.this);
            }
        });
        titleTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtils.getPreference(HomeFishingShopActivity.this, "userid"))) {
                    //已登录
                    startActivity(new Intent(HomeFishingShopActivity.this, AppendFishingShopActivity.class));
                } else {
                    //未登录
                    startActivity(new Intent(HomeFishingShopActivity.this, LoginActivity.class));
                }

            }
        });
        //禁止抽屉收拾滑动
        drawerScreenShop.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    /**
     * 设置抽屉中的列表数据
     */
    private void initDrawerLayoutData() {
        mShowTypeList = new ArrayList<>();
        mShowTypeList.add(new DrawerListBean(false, "列表"));
        mShowTypeList.add(new DrawerListBean(false, "地图"));

        mAreaList = new ArrayList<>();
        mAreaList.add(0, new FishingGearAreaBean("", "不限"));
        lvArea.setFocusable(false);
        mAreaListAdapter = new DrawerAreaListAdapter(mAreaList, HomeFishingShopActivity.this, R.layout.item_fishing_place_type);
        lvArea.setAdapter(mAreaListAdapter);
        okhttpFishingGearArea();
        mAreaListAdapter.setSelectPosition(selectAreaListPosition);
        lvArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectAreaListPosition = position;
                mAreaListAdapter.setSelectPosition(selectAreaListPosition);
                mAreaListAdapter.notifyDataSetChanged();
            }
        });
        mShowTypeAdapter = new DrawerShowTypeListAdapter(mShowTypeList, HomeFishingShopActivity.this, R.layout.item_drawer_layout);
        gvShowType.setAdapter(mShowTypeAdapter);
        mShowTypeAdapter.setSelectPosition(selectShowTypePosition);
        mShowTypeList.get(selectShowTypePosition).setSelect(true);//点击的设为选中
        mShowTypeAdapter.setOnItemClickListener(new DrawerShowTypeListAdapter.OnItemClickListener() {
            @Override
            public void onCustomClick(View view, int position) {
                for (DrawerListBean person : mShowTypeList) { //遍历list集合中的数据
                    person.setSelect(false);//全部设为未选中
                }

                selectShowTypePosition = position;
                mShowTypeAdapter.setSelectPosition(selectShowTypePosition);
                mShowTypeList.get(position).setSelect(true);//点击的设为选中
                mShowTypeAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.fishing_shop_tv_distance, R.id.fishing_shop_tv_comprehensive, R.id.fishing_shop_ll_screen
            , R.id.drawer_screen_shop_tv_cancel, R.id.drawer_screen_shop_tv_determine
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fishing_shop_tv_distance:
                //按距离筛选
                tvDistance.setTextColor(this.getResources().getColor(R.color.c_269ceb));
                tvComprehensive.setTextColor(this.getResources().getColor(R.color.c_333333));
                page = 1;
                order = "";
                okhttpFishingGear();
                break;
            case R.id.fishing_shop_tv_comprehensive:
                //按时间
                tvDistance.setTextColor(this.getResources().getColor(R.color.c_333333));
                tvComprehensive.setTextColor(this.getResources().getColor(R.color.c_269ceb));
                page = 1;
                order = "time";
                okhttpFishingGear();
                break;
            case R.id.fishing_shop_ll_screen:
                //筛选
                drawerScreenShop.openDrawer(Gravity.RIGHT);//侧滑打开  不设置则不会默认打开
                break;
            case R.id.drawer_screen_shop_tv_cancel:
                //取消
                int confirmPosition = PublicStaticData.sharedPreferences.getInt("selectPosition", selectAreaListPosition);
                mAreaListAdapter.setSelectPosition(confirmPosition);
                mAreaListAdapter.notifyDataSetChanged();
                mShowTypeAdapter.setSelectPosition(0);
                mShowTypeAdapter.notifyDataSetChanged();
                showDrawerLayout();
                break;
            case R.id.drawer_screen_shop_tv_determine:
                //确定
                countyid = mAreaList.get(selectAreaListPosition).getCityid();
                showDrawerLayout();
                if (mShowTypeList.get(1).isSelect()) {
                    okhttpAllFishingShop();
                } else if (mShowTypeList.get(0).isSelect()) {
                    PublicStaticData.sharedPreferences.edit().putInt("selectPosition", 0).commit();
                    countyid = mAreaList.get(selectAreaListPosition).getCityid();
                    page = 1;
                    okhttpFishingGear();
                }
                break;
        }
    }

    /**
     * 渔具店列表接口数据
     */
    private void okhttpFishingGear() {
        lat = PublicStaticData.sharedPreferences.getString("latitude", "");
        lng = PublicStaticData.sharedPreferences.getString("longitude", "");
        if (PublicStaticData.sharedPreferences.getString("city", "").contains("市")) {
            city = PublicStaticData.sharedPreferences.getString("city", "").substring(0, PublicStaticData.sharedPreferences.getString("city", "").length() - 1);
        } else {
            city = PublicStaticData.sharedPreferences.getString("city", "");
        }
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.fishingGear);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putParams("order", order);
        commonOkhttp.putParams("countyid", countyid);
        commonOkhttp.putParams("city", city);
        commonOkhttp.putParams("lat", lat);
        commonOkhttp.putParams("lng", lng);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.FishingGearListEntity>(this) {
            @Override
            public void onSuccess(NetBean.FishingGearListEntity data, int d) {
                super.onSuccess(data, d);
                System.out.println("=====数据========" + new Gson().toJson(data));
                try {
                    if (page == 1) {
                        homeFishingShopList.clear();
                    }
                    if (data != null && data.size() != 0) {
                        homeFishingShopList.addAll(data);
                        ++page;
                    } else {
                        commonOkhttp.showNoData(HomeFishingShopActivity.this, page);
                    }
                    mAdapter.notifyDataSetChanged();
                    if (homeFishingShopPlv != null) {
                        homeFishingShopPlv.onRefreshComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(HomeFishingShopActivity.this).showMessage(HomeFishingShopActivity.this.getResources().getString(R.string.data_parsing_failed));
                }
            }

            @Override
            public void onOther(JsonResult<NetBean.FishingGearListEntity> data, int d) {
                super.onOther(data, d);
                System.out.println("=====数据========" + new Gson().toJson(data));
                System.out.println("=====数据========" + data.getMessage());
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 筛选区域接口
     */
    private void okhttpFishingGearArea() {
        String city;
        city = PublicStaticData.sharedPreferences.getString("selCity", "");
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.fishingGearArea);
        commonOkhttp.putParams("city", city);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.FishingGearAreaEntity>(this) {
            @Override
            public void onSuccess(NetBean.FishingGearAreaEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    if (data != null && data.size() != 0) {
                        mAreaList.addAll(data);
                        mAreaListAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(HomeFishingShopActivity.this).showMessage(HomeFishingShopActivity.this.getResources().getString(R.string.data_parsing_failed));
                }
            }
        });
        commonOkhttp.Execute();
    }

    private void okhttpAllFishingShop() {
        if (PublicStaticData.sharedPreferences.getString("city", "").contains("市")) {
            city = PublicStaticData.sharedPreferences.getString("city", "").substring(0, PublicStaticData.sharedPreferences.getString("city", "").length() - 1);
        } else {
            city = PublicStaticData.sharedPreferences.getString("city", "");
        }
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.allfishing_shop);
        commonOkhttp.putParams("countyid", countyid);
        commonOkhttp.putParams("city", city);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.FishingGearListEntity>(this) {
            @Override
            public void onSuccess(NetBean.FishingGearListEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    if (data != null && data.size() != 0) {
                        intent = new Intent(HomeFishingShopActivity.this, MapDistributionShowActivity.class);
                        intent.putExtra("whereFrom", "2");
                        intent.putExtra("countyid", countyid);
                        startActivityForResult(intent, PublicStaticData.HOME_FISHING_SHOP);
                    } else {
                        ToastUtils.getInstance(HomeFishingShopActivity.this).showMessage("没有数据");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(HomeFishingShopActivity.this).showMessage(HomeFishingShopActivity.this.getResources().getString(R.string.data_parsing_failed));
                }
            }
        });
        commonOkhttp.Execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PublicStaticData.HOME_FISHING_SHOP) {
            mShowTypeList.get(0).setSelect(true);
            mShowTypeList.get(1).setSelect(false);
            mShowTypeAdapter.setSelectPosition(0);
            mShowTypeAdapter.notifyDataSetChanged();
            mAreaListAdapter.setSelectPosition(0);
            mAreaListAdapter.notifyDataSetChanged();
        }
        if (requestCode == PublicStaticData.HOME_FISHING_SHOP) {
            if (resultCode == PublicStaticData.FISHING_SHOP_DETAILS) {
                page = 1;
                okhttpFishingGear();
            }
        }
    }

    /**
     * 设置抽屉开关
     */
    private void showDrawerLayout() {
        if (!drawerScreenShop.isDrawerOpen(Gravity.RIGHT)) {
            drawerScreenShop.openDrawer(Gravity.RIGHT);
        } else {
            drawerScreenShop.closeDrawer(Gravity.RIGHT);
        }
    }
}
