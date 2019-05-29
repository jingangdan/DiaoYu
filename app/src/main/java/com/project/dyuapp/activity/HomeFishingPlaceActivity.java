package com.project.dyuapp.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.DrawerAreaListAdapter;
import com.project.dyuapp.adapter.DrawerCostListAdapter;
import com.project.dyuapp.adapter.DrawerModelListAdapter;
import com.project.dyuapp.adapter.DrawerPostListAdapter;
import com.project.dyuapp.adapter.DrawerShowTypeListAdapter;
import com.project.dyuapp.adapter.HomeFishingAdapter;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.DrawerListBean;
import com.project.dyuapp.entity.FishingGearAreaBean;
import com.project.dyuapp.entity.HomeFishingDataEntity;
import com.project.dyuapp.entity.HomeFishingPlaceEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myutils.ToastUtils;
import com.project.dyuapp.myviews.GridViewForScrollView;
import com.project.dyuapp.myviews.ListViewForScrollView;
import com.project.dyuapp.myviews.SupportPopupWindow;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.project.dyuapp.myutils.HttpUrl.ANGLING_ANG_LIST;

/**
 * @author 田亭亭
 * @description 首页钓场Activity
 * @created at 2017/11/25 0025
 * @change
 */
public class HomeFishingPlaceActivity extends AutoLayoutActivity {

    @Bind(R.id.fishing_place_tv_distance)
    TextView tvDistance;//离我最近
    @Bind(R.id.fishing_place_tv_comprehensive)
    TextView tvComprehensive;//人气最多
    @Bind(R.id.fishing_place_plv)
    PullToRefreshListView homeFishingPlacePlv;//钓场列表控件
    @Bind(R.id.drawer_screen_place_tv_place)
    TextView drawerScreenPlaceTvPlace;//选择的区域
    @Bind(R.id.drawer_screen_place_gv_models)
    GridViewForScrollView gvModels;//类型
    @Bind(R.id.drawer_screen_place_gv_cost)
    GridViewForScrollView gvCost;//收费
    @Bind(R.id.drawer_screen_place_gv_stick)
    GridViewForScrollView gvStick;//渔获帖数
    @Bind(R.id.drawer_screen_place_gv_show_type)
    GridViewForScrollView gvShowType;//显示方式

    @Bind(R.id.title_tv_title)
    TextView titleTvTitle;//标题
    @Bind(R.id.title_tv_right)
    TextView titleTvRight;//右侧标题
    @Bind(R.id.title_ibtn_back)
    ImageButton titleIbtnBack;//返回
    @Bind(R.id.drawer_screen_place)
    DrawerLayout drawerScreenPlace;//抽屉
    @Bind(R.id.top_line)
    View topLine;


    private HomeFishingAdapter mAdapter;//钓场列表适配器
    private List<HomeFishingPlaceEntity> homeFishingPlaceList;//钓场列表数据集合

    private DrawerModelListAdapter mModelAdapter;//类型适配器
    private DrawerCostListAdapter mCostAdapter;//收费列表适配器
    private DrawerPostListAdapter mPostAdapter;//渔获帖子适配器
    private DrawerShowTypeListAdapter mShowTypeAdapter;//显示类型适配器
    private DrawerAreaListAdapter mAreaListAdapter;//区域适配器

    private List<FishingGearAreaBean> mAreaList;//区域集合
    private List<DrawerListBean> mModelList;//类型集合
    private List<DrawerListBean> mCostList;//收费列表集合
    private List<DrawerListBean> mPostList;//渔获帖子集合
    private List<DrawerListBean> mShowTypeList;//显示类型集合

    private int selectModePosition = 0;
    private int selectCostPosition = 0;
    private int selectPostPosition = 0;
    private int selectShowTypePosition = 0;
    private int selectAreaListPosition = 0;

    private HomeFishingDataEntity homeFishingDataEntity = new HomeFishingDataEntity();
    private int page = 1;
    private String order = "1";// 记录当前选中的是离我最近还是人气最多
    private String city = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fishing_place);
        ButterKnife.bind(this);
        setSlideWidth();
        initView();
        initData();
        initDrawerLayoutData();
    }

    /**
     * 设置弹出抽屉的宽度
     */
    private void setSlideWidth() {
        int windowsWight = getWindowManager().getDefaultDisplay().getWidth() * 3 / 4;
        View leftMenu = findViewById(R.id.drawer_screen_place_fl_right);
        ViewGroup.LayoutParams leftParams = leftMenu.getLayoutParams();
        leftParams.width = windowsWight;
        leftMenu.setLayoutParams(leftParams);
    }

    /**
     * 设置钓场列表数据
     */
    private void initData() {
        homeFishingPlaceList = new ArrayList<>();
        mAdapter = new HomeFishingAdapter(homeFishingPlaceList, HomeFishingPlaceActivity.this, R.layout.item_home_fishing_place);
        homeFishingPlacePlv.setAdapter(mAdapter);
        order = "1";
        homeFishingDataEntity.setOrder(order);
        homeFishingDataEntity.setPage(page + "");
        getfishingPlace();//钓场列表
    }

    /**
     * 初始化控件
     */
    private void initView() {
        titleTvTitle.setVisibility(View.VISIBLE);
        titleTvRight.setVisibility(View.VISIBLE);
        titleIbtnBack.setVisibility(View.VISIBLE);
        titleTvTitle.setText("钓场");
        titleTvRight.setText("添加");
        titleIbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenManager.getInstance().removeActivity(HomeFishingPlaceActivity.this);
            }
        });
        titleTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtils.getPreference(HomeFishingPlaceActivity.this, "userid"))) {
                    startActivity(new Intent(HomeFishingPlaceActivity.this, AppendFishingPlaceActivity.class));
                } else {
                    startActivity(new Intent(HomeFishingPlaceActivity.this, LoginActivity.class));
                }

            }
        });
        homeFishingPlacePlv.setMode(PullToRefreshBase.Mode.BOTH);
        homeFishingPlacePlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeFishingPlaceActivity.this, FishingPlaceDetailsActivity.class);
                intent.putExtra("FishingPlaceId", homeFishingPlaceList.get(position - 1).getF_gid());
                intent.putExtra("dp_score",""+homeFishingPlaceList.get(position-1).getDp_score());
                startActivity(intent);
            }
        });
        homeFishingPlacePlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                homeFishingDataEntity.setPage(page + "");
                getfishingPlace();//钓场列表
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                homeFishingDataEntity.setPage(page + "");
                getfishingPlace();//钓场列表
            }
        });
        //禁止抽屉手势滑动
        drawerScreenPlace.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    /**
     * 设置抽屉开关
     */
    private void showDrawerLayout() {
        if (!drawerScreenPlace.isDrawerOpen(Gravity.RIGHT)) {
            drawerScreenPlace.openDrawer(Gravity.RIGHT);
        } else {
            drawerScreenPlace.closeDrawer(Gravity.RIGHT);
        }
    }

    /**
     * 设置抽屉中的列表数据
     */
    private void initDrawerLayoutData() {
        mModelList = new ArrayList<>();
        mCostList = new ArrayList<>();
        mPostList = new ArrayList<>();
        mShowTypeList = new ArrayList<>();

        mCostList.add(new DrawerListBean(false, "不限"));
        mCostList.add(new DrawerListBean(false, "免费"));
        mCostList.add(new DrawerListBean(false, "收费"));

        mPostList.add(new DrawerListBean(false, "不限"));
        mPostList.add(new DrawerListBean(false, "由多到少"));
        mPostList.add(new DrawerListBean(false, "由少到多"));

        mShowTypeList.add(new DrawerListBean(false, "列表"));
        mShowTypeList.add(new DrawerListBean(false, "地图"));

        mModelAdapter = new DrawerModelListAdapter(mModelList, HomeFishingPlaceActivity.this, R.layout.item_drawer_layout);
        gvModels.setAdapter(mModelAdapter);
        mModelAdapter.setSelectPosition(selectModePosition);
        mModelAdapter.setOnItemClickListener(new DrawerModelListAdapter.OnItemClickListener() {
            @Override
            public void onCustomClick(View view, int position) {
                for (DrawerListBean person : mModelList) { //遍历list集合中的数据
                    person.setSelect(false);//全部设为未选中
                }
                selectModePosition = position;
                mModelAdapter.setSelectPosition(position);
                mModelList.get(position).setSelect(true);//点击的设为选中
                if (position == 0) {
                    homeFishingDataEntity.setCat_id("");
                } else {
                    homeFishingDataEntity.setCat_id(mModelList.get(position).getD_id());
                }
                mModelAdapter.notifyDataSetChanged();
            }
        });
        mCostAdapter = new DrawerCostListAdapter(mCostList, HomeFishingPlaceActivity.this, R.layout.item_drawer_layout);
        gvCost.setAdapter(mCostAdapter);
        mCostAdapter.setSelectPosition(selectCostPosition);
        mCostAdapter.setOnItemClickListener(new DrawerCostListAdapter.OnItemClickListener() {
            @Override
            public void onCustomClick(View view, int position) {
                for (DrawerListBean person : mCostList) { //遍历list集合中的数据
                    person.setSelect(false);//全部设为未选中
                }
                selectCostPosition = position;
                mCostAdapter.setSelectPosition(position);
                mCostList.get(position).setSelect(true);//点击的设为选中
                if (position == 0) {
                    homeFishingDataEntity.setPay_type("");
                } else {
                    homeFishingDataEntity.setPay_type(position == 1 ? "2" : "1");
                }
                mCostAdapter.notifyDataSetChanged();
            }
        });
        mPostAdapter = new DrawerPostListAdapter(mPostList, HomeFishingPlaceActivity.this, R.layout.item_drawer_layout);
        gvStick.setAdapter(mPostAdapter);
        mPostAdapter.setSelectPosition(selectPostPosition);
        mPostAdapter.setOnItemClickListener(new DrawerPostListAdapter.OnItemClickListener() {
            @Override
            public void onCustomClick(View view, int position) {
                for (DrawerListBean person : mPostList) { //遍历list集合中的数据
                    person.setSelect(false);//全部设为未选中
                }
                selectPostPosition = position;
                mPostAdapter.setSelectPosition(selectPostPosition);
                mPostList.get(position).setSelect(true);//点击的设为选中
                homeFishingDataEntity.setOrder(position==0?order:position+2+"");
                mPostAdapter.notifyDataSetChanged();
//                if (position == 0) {
//                    setCondition(order);
//                } else {
//                    setCondition(position + 2 + "");
//                }
            }
        });
        mShowTypeAdapter = new DrawerShowTypeListAdapter(mShowTypeList, HomeFishingPlaceActivity.this, R.layout.item_drawer_layout);
        gvShowType.setAdapter(mShowTypeAdapter);
        mShowTypeAdapter.setSelectPosition(selectShowTypePosition);
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

        fishingPlace();//钓场类型
    }

    private void setClearData() {
        selectModePosition = 0;
        selectCostPosition = 0;
        selectPostPosition = 0;
        selectShowTypePosition = 0;
        selectAreaListPosition = 0;
        if (mModelAdapter != null) {
            mModelAdapter.setSelectPosition(0);//类型适配器
            mModelAdapter.notifyDataSetChanged();
        }
        if (mCostAdapter != null) {
            mCostAdapter.setSelectPosition(0);//收费列表适配器
            mCostAdapter.notifyDataSetChanged();
        }
        if (mPostAdapter != null) {
            mPostAdapter.setSelectPosition(0);//渔获帖子适配器
            mPostAdapter.notifyDataSetChanged();
        }
        if (mShowTypeAdapter != null) {
            mShowTypeAdapter.setSelectPosition(0);//显示类型适配器
            mShowTypeAdapter.notifyDataSetChanged();
        }
        if (mAreaListAdapter != null) {
            mAreaListAdapter.setSelectPosition(0);//区域适配器
            mAreaListAdapter.notifyDataSetChanged();
        }
        homeFishingDataEntity.setOrder("");
        homeFishingDataEntity.setCountyid("");
        homeFishingDataEntity.setCat_id("");
        homeFishingDataEntity.setPay_type("");
        drawerScreenPlaceTvPlace.setText("");
        drawerScreenPlaceTvPlace.setHint("选择区域");

        mShowTypeList.get(0).setSelect(true);
        mShowTypeList.get(1).setSelect(false);
    }

    /**
     * 选择区域弹窗
     */
    private void popScreen(View view) {
        mAreaList = new ArrayList<>();
        mAreaList.add(0, new FishingGearAreaBean("", "不限"));
        View popupView = LayoutInflater.from(HomeFishingPlaceActivity.this).inflate(R.layout.pop_select_area, null);//dialog_screen_findcar
        final SupportPopupWindow popDate = new SupportPopupWindow(popupView, getWindowManager().getDefaultDisplay().getWidth() * 3 / 4, ViewGroup.LayoutParams.MATCH_PARENT);
        if (popDate.isShowing()) {
            return;
        }
        ListViewForScrollView lvArea = (ListViewForScrollView) popupView.findViewById(R.id.dialog_lv_area);
        lvArea.setFocusable(false);
        mAreaListAdapter = new DrawerAreaListAdapter(mAreaList, HomeFishingPlaceActivity.this, R.layout.item_fishing_place_type);
        lvArea.setAdapter(mAreaListAdapter);
        mAreaListAdapter.setSelectPosition(selectAreaListPosition);
        lvArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectAreaListPosition = position;
                mAreaListAdapter.setSelectPosition(selectAreaListPosition);
                mAreaListAdapter.notifyDataSetChanged();
                homeFishingDataEntity.setCountyid(mAreaList.get(position).getCityid());
                drawerScreenPlaceTvPlace.setText(mAreaList.get(position).getName());
                popDate.dismiss();
            }
        });
        popDate.setOutsideTouchable(true);
        popDate.setBackgroundDrawable(new BitmapDrawable());
        popDate.showAsDropDown(view, getWindowManager().getDefaultDisplay().getWidth(), 0);
    }


    @OnClick({R.id.fishing_place_tv_distance, R.id.fishing_place_tv_comprehensive, R.id.fishing_place_ll_screen
            , R.id.drawer_screen_place_tv_place, R.id.drawer_screen_place_tv_cancel, R.id.drawer_screen_place_tv_determine
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fishing_place_tv_distance:
                //按距离筛选
                tvDistance.setTextColor(this.getResources().getColor(R.color.c_269ceb));
                tvComprehensive.setTextColor(this.getResources().getColor(R.color.c_333333));
                setCondition("1");
                break;
            case R.id.fishing_place_tv_comprehensive:
                //按人气
                tvDistance.setTextColor(this.getResources().getColor(R.color.c_333333));
                tvComprehensive.setTextColor(this.getResources().getColor(R.color.c_269ceb));
                setCondition("2");
                break;
            case R.id.fishing_place_ll_screen:
                //筛选
                setClearData();
                drawerScreenPlace.openDrawer(Gravity.RIGHT);//侧滑打开  不设置则不会默认打开
                break;
            case R.id.drawer_screen_place_tv_place:
                //选择区域
                popScreen(topLine);
                okhttpFishingGearArea();
                break;
            case R.id.drawer_screen_place_tv_cancel:
                //取消
                homeFishingDataEntity.setOrder(order);
                showDrawerLayout();
                homeFishingDataEntity = new HomeFishingDataEntity();
                break;
            case R.id.drawer_screen_place_tv_determine:
                //确定
                showDrawerLayout();
                //判断是否选中地图显示
                if (mShowTypeList.get(1).isSelect()) {
                    okhttpAllFishingShop();
                } else {
                    //判断当前是否全部选中不限
                    if (TextUtils.isEmpty(homeFishingDataEntity.getCountyid()) &&
                            TextUtils.isEmpty(homeFishingDataEntity.getCat_id()) &&
                            TextUtils.isEmpty(homeFishingDataEntity.getPay_type()) &&
                            TextUtils.isEmpty(homeFishingDataEntity.getOrder())) {
                        // 全部选中不限后显示之前的排序
                        if ("1".equals(order)) {
                            tvDistance.setTextColor(this.getResources().getColor(R.color.c_269ceb));
                            tvComprehensive.setTextColor(this.getResources().getColor(R.color.c_333333));
                        } else {
                            tvDistance.setTextColor(this.getResources().getColor(R.color.c_333333));
                            tvComprehensive.setTextColor(this.getResources().getColor(R.color.c_269ceb));
                        }
                        homeFishingDataEntity.setOrder(order);
                    }else{
                        tvDistance.setTextColor(this.getResources().getColor(R.color.c_333333));
                        tvComprehensive.setTextColor(this.getResources().getColor(R.color.c_333333));
                    }
                    getfishingPlace();
                }
                //为跟IOS一致修改：只要点击确定，即使全部选中不限，也要清空距离和人气的选择状态
                tvDistance.setTextColor(this.getResources().getColor(R.color.c_333333));
                tvComprehensive.setTextColor(this.getResources().getColor(R.color.c_333333));
                break;
        }
    }

    private void setCondition(String order) {
        homeFishingDataEntity.setOrder(order);
        if ("1".equals(order) || "2".equals(order)) {
            this.order = order;
            homeFishingDataEntity.setCountyid("");
            homeFishingDataEntity.setCat_id("");
            homeFishingDataEntity.setPay_type("");
            getfishingPlace();
        }
    }

    private void okhttpAllFishingShop() {
        if (PublicStaticData.sharedPreferences.getString("city", "").contains("市")) {
            city = PublicStaticData.sharedPreferences.getString("city", "").substring(0, PublicStaticData.sharedPreferences.getString("city", "").length() - 1);
        } else {
            city = PublicStaticData.sharedPreferences.getString("city", "");
        }
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.allfishing_shop);
        commonOkhttp.putParams("countyid", homeFishingDataEntity.getCountyid());
        commonOkhttp.putParams("city", city);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.FishingGearListEntity>(this) {
            @Override
            public void onSuccess(NetBean.FishingGearListEntity data, int d) {
                super.onSuccess(data, d);
                try {

                    if (data != null && data.size() != 0) {
                        startActivity(new Intent(HomeFishingPlaceActivity.this, MapDistributionShowActivity.class)
                                .putExtra("whereFrom", "1")
                                .putExtra("pay_type", homeFishingDataEntity.getPay_type())
                                .putExtra("cat_id", homeFishingDataEntity.getCat_id())
                                .putExtra("countyid", homeFishingDataEntity.getCountyid())
                        );
                    } else {
                        ToastUtils.getInstance(HomeFishingPlaceActivity.this).showMessage("没有数据");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(HomeFishingPlaceActivity.this).showMessage(HomeFishingPlaceActivity.this.getResources().getString(R.string.data_parsing_failed));
                }
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
        if (TextUtils.isEmpty(city))
            ToastUtils.getInstance(this).showMessage("定位信息异常");

        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.USER_GET_COUNTY);
        commonOkhttp.putParams("city_name", city);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.FishingGearAreaEntity>(this) {
            @Override
            public void onSuccess(NetBean.FishingGearAreaEntity data, int d) {
                super.onSuccess(data, d);
                mAreaList.clear();
                mAreaList.addAll(data);
                mAreaListAdapter.notifyDataSetChanged();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 获取类型
     */
    public void fishingPlace() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.ANGLING_ANG_TYPE);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.DrawerListEntity>(this) {
            @Override
            public void onSuccess(NetBean.DrawerListEntity data, int d) {
                super.onSuccess(data, d);
                mModelList.clear();
                mModelList.add(new DrawerListBean(false, "不限"));
                mModelList.addAll(data);
                mModelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }
        });
        commonOkhttp.Execute();
    }

    private void getfishingPlace() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(ANGLING_ANG_LIST);
        commonOkhttp.putParams("city_name", PublicStaticData.sharedPreferences.getString("city", ""));
        commonOkhttp.putParams("longitude", PublicStaticData.sharedPreferences.getString("longitude", ""));
        commonOkhttp.putParams("latitude", PublicStaticData.sharedPreferences.getString("latitude", ""));
        commonOkhttp.putParams("order", homeFishingDataEntity.getOrder());
        commonOkhttp.putParams("page", homeFishingDataEntity.getPage());
        commonOkhttp.putParams("countyid", homeFishingDataEntity.getCountyid());
        commonOkhttp.putParams("cat_id", homeFishingDataEntity.getCat_id());
        commonOkhttp.putParams("pay_type", homeFishingDataEntity.getPay_type());
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.HomeFishingPlaceListEntity>(HomeFishingPlaceActivity.this) {
            @Override
            public void onSuccess(NetBean.HomeFishingPlaceListEntity data, int d) {
                super.onSuccess(data, d);
                homeFishingPlacePlv.onRefreshComplete();
                if (page == 1) {
                    homeFishingPlaceList.clear();
                    homeFishingPlaceList.addAll(data);
                } else {
                    homeFishingPlaceList.addAll(data);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                homeFishingPlacePlv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                homeFishingPlacePlv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }

}
