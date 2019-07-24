package com.project.dyuapp.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.project.dyuapp.R;
import com.project.dyuapp.activity.CommenWebviewActivity;
import com.project.dyuapp.activity.GoldMallActivity;
import com.project.dyuapp.activity.HomeBaitActivity;
import com.project.dyuapp.activity.HomeFishingPlaceActivity;
import com.project.dyuapp.activity.HomeFishingShopActivity;
import com.project.dyuapp.activity.HomeSkillActivity;
import com.project.dyuapp.activity.HomeVideoActivity;
import com.project.dyuapp.activity.LoginActivity;
import com.project.dyuapp.activity.PostListActivity;
import com.project.dyuapp.activity.PostListActivity2;
import com.project.dyuapp.activity.SearchActivity;
import com.project.dyuapp.activity.WeatherActivity;
import com.project.dyuapp.adapter.CommonAdapter;
import com.project.dyuapp.adapter.CommonViewHolder;
import com.project.dyuapp.base.MyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.citys.CityActivity;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.EmptyResultBean;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.entity.NoticeBean;
import com.project.dyuapp.entity.WeatherEntity;
import com.project.dyuapp.myutils.CheckPermissionUtils;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyLogUtils;
import com.project.dyuapp.myutils.PopSelectAddress;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.ToastUtils;
import com.project.dyuapp.myviews.GridViewForScrollView;
import com.project.dyuapp.myviews.ListViewForScrollView;
import com.project.dyuapp.shop.CateActivity;
import com.project.dyuapp.shop.CateGoodsAdapter;
import com.project.dyuapp.shop.GoodsActivity;
import com.project.dyuapp.shop.GoodsData;
import com.project.dyuapp.shop.GoodsDetailActivity;
import com.project.dyuapp.shop.GsonUtils;
import com.project.dyuapp.shop.OnItemClickListener;
import com.project.dyuapp.shop.PermissionUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.alibaba.fastjson.util.IOUtils.UTF8;
import static com.project.dyuapp.myutils.PublicStaticData.CITY_CHANGE_CODE;
import static com.project.dyuapp.myutils.PublicStaticData.HOME_CODE;

/**
 * @describe：首页
 * @author：刘晓丽
 * @createdate：2017/8/18 11:00
 */
public class HomeFragment extends MyBaseFragment implements EasyPermissions.PermissionCallbacks {

    @Bind(R.id.home_tv_area)
    TextView homeTvArea;
    @Bind(R.id.home_ll_search)
    LinearLayout homeLlSearch;
    @Bind(R.id.home_tv_weather)
    TextView homeTvWeather;

    @Bind(R.id.home_scrollview)
    PullToRefreshScrollView pullRefreshScrollview;

    @Bind(R.id.home_gv1)
    GridViewForScrollView homeGv1;
    @Bind(R.id.home_gv2)
    GridViewForScrollView homeGv2;

    @Bind(R.id.home_post_tv_tuijian)
    TextView homePostTvTuijian;
    @Bind(R.id.home_post_view_tuijian)
    View homePostViewTuijian;
    @Bind(R.id.home_post_tv_zuixin)
    TextView homePostTvZuixin;
    @Bind(R.id.home_post_view_zuixin)
    View homePostViewZuixin;
    @Bind(R.id.home_post_tv_remen)
    TextView homePostTvRemen;
    @Bind(R.id.home_post_view_remen)
    View homePostViewRemen;
    @Bind(R.id.home_post_tv_bendi)
    TextView homePostTvBendi;
    @Bind(R.id.home_post_view_bendi)
    View homePostViewBendi;

    @Bind(R.id.home_post_tv_tuijian1)
    TextView homePostTvTuijian1;
    @Bind(R.id.home_post_view_tuijian1)
    View homePostViewTuijian1;
    @Bind(R.id.home_post_tv_zuixin1)
    TextView homePostTvZuixin1;
    @Bind(R.id.home_post_view_zuixin1)
    View homePostViewZuixin1;
    @Bind(R.id.home_post_tv_remen1)
    TextView homePostTvRemen1;
    @Bind(R.id.home_post_view_remen1)
    View homePostViewRemen1;
    @Bind(R.id.home_post_tv_bendi1)
    TextView homePostTvBendi1;
    @Bind(R.id.home_post_view_bendi1)
    View homePostViewBendi1;
    @Bind(R.id.home_ll_xuanfu)
    LinearLayout homeLlXuanfu;
    @Bind(R.id.home_ll_xuanfu1)
    LinearLayout homeLlXuanfu1;
    @Bind(R.id.home_tv_gg1)
    TextView homeTvGg1;
    @Bind(R.id.home_tv_gg2)
    TextView homeTvGg2;
    @Bind(R.id.home_tv_gg3)
    TextView homeTvGg3;
    @Bind(R.id.home_iv_weather)
    ImageView ivWeather;
    @Bind(R.id.home_lv_gg)
    ListViewForScrollView homeLvGg;

    @Bind(R.id.rv_cate_recommend)
    RecyclerView rvCateRecommend;
    @Bind(R.id.relCate)
    RelativeLayout relCate;

    private GVAdapter1 gvAdapter1;
    private GVAdapter2 gvAdapter2;

    private List<Fragment> mFragments;
    private int index = 0;//点击的页卡索引
    private int currentTabIndex = 0;//当前的页卡索引
    private int mScrollY = 0;
    private List<Integer> scrolly;
    private String nowProvince, nowCity = "", nowCityCode = "", county = "";//当前省市
    private static final int BAIDU_READ_PHONE_STATE = 100;
    private CommonAdapter noticeAdapter;
    private List<NoticeBean> noticeList = new ArrayList<>();
    private String type = "1";

    private boolean isGetInfo;
    private double latitude;
    private double longitude;

    //多个权限
    private final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private final int REQUEST_CODE_PERMISSIONS = 0x01;

    private CateGoodsAdapter cateGoodsAdapter;
    private LinearLayoutManager mManager2;
    private String price = "";
    DecimalFormat df;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x05:
                    Bundle bundle = msg.getData();
                    String date = bundle.getString("msg");
                    Log.e("ssssss", "date=" + date);
                    if (!TextUtils.isEmpty(date)) {
                        GoodsData data = GsonUtils.gsonIntance().gsonToBean(date, GoodsData.class);
                        if (data.getCode() == 0) {
                            cateGoodsAdapter.clear();
                            cateGoodsAdapter.addAll(data.getData().getGoods_list());
                        }
                    }
                    break;
            }
        }
    };

    @SuppressLint("ApplySharedPref")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);

        df = new DecimalFormat("0.00");
        initData();
        //initPermission();

        if (TextUtils.isEmpty(SPUtils.getPreference(getActivity(), "homeCity"))) {
            initLocation();
        } else {
            homeTvArea.setText(SPUtils.getPreference(getActivity(), "homeCity"));

            PublicStaticData.sharedPreferences.edit().putString("latitude", SPUtils.getPreference(getActivity(), "latitude")).commit();
            PublicStaticData.sharedPreferences.edit().putString("longitude", SPUtils.getPreference(getActivity(), "longitude")).commit();
            PublicStaticData.sharedPreferences.edit().putString("province", SPUtils.getPreference(getActivity(), "nowProvince")).commit();
            PublicStaticData.sharedPreferences.edit().putString("city", SPUtils.getPreference(getActivity(), "homeCity")).commit();
            PublicStaticData.sharedPreferences.edit().putString("county", SPUtils.getPreference(getActivity(), "county")).commit();
        }

        // initLocation();
        //getNotice();

        return view;
    }

    /**
     * 获取公告
     */
    private void getNotice() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.notice);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.NoticeEntity>(getActivity()) {
            @Override
            public void onSuccess(NetBean.NoticeEntity data, int d) {
                super.onSuccess(data, d);
                if (data.size() > 0) {
                    homeLvGg.setVisibility(View.VISIBLE);
                } else {
                    homeLvGg.setVisibility(View.GONE);
                }
                noticeList.clear();
//                int size = 0;
//                if (data.size() > 3) {
//                    size = 3;
//                } else {
//                    size = data.size();
//                }
//                for (int i = 0; i < size; i++) {
//                    noticeList.add(data.get(i));
//                }

                noticeList.addAll(data);
                noticeAdapter.notifyDataSetChanged();
            }
        });
        commonOkhttp.Execute();
    }

    @SuppressLint("NewApi")
    private void initData() {
        scrolly = new ArrayList<Integer>() {{
            add(homeLlXuanfu.getTop());
            add(homeLlXuanfu.getTop());
            add(homeLlXuanfu.getTop());
            add(homeLlXuanfu.getTop());
        }};

        pullRefreshScrollview.getRefreshableView().getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (pullRefreshScrollview.getRefreshableView().getScrollY() >= homeLlXuanfu.getTop()) {
                    //homeLlXuanfu1.setVisibility(View.VISIBLE);
                } else {
                    //homeLlXuanfu1.setVisibility(View.GONE);
                }
                mScrollY = pullRefreshScrollview.getRefreshableView().getScrollY();
            }
        });
        //上方三个矩形图片
        gvAdapter1 = new GVAdapter1(new ArrayList<Integer>() {{
            add(R.mipmap.home_img_01);
            add(R.mipmap.home_img_04);
            add(R.mipmap.home_img_02);
        }}, getActivity(), 0);
        homeGv1.setAdapter(gvAdapter1);
        homeGv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //渔获
                        startActivity(new Intent(getActivity(), PostListActivity2.class)
                                .putExtra("title", "渔获")
                                .putExtra("isPublish", true)
                                .putExtra("isHomeFishGet", true)
                                .putExtra("topId", PublicStaticData.ID_GET_FISH));
                        break;
                    case 1:
                        //导购商城
                        startActivity(new Intent(getActivity(), CateActivity.class));
                        break;
                    case 2:
                        //钓场
                        startActivity(new Intent(getActivity(), HomeFishingPlaceActivity.class));
//                        //渔乐短片
////                        startActivity(new Intent(getActivity(), HarvestShortActivity.class));
//                        startActivity(new Intent(getActivity(), HarvestShortYoukuActivity.class));
                        break;
                }
            }
        });
        //中间10个分类
        gvAdapter2 = new GVAdapter2(new ArrayList<FenLei>() {{
            add(new FenLei(R.mipmap.home_icon_01, "钓鱼技巧"));
            add(new FenLei(R.mipmap.home_icon_02, "金币兑换"));
            // add(new FenLei(R.mipmap.home_icon_03, "钓鱼杂谈"));
            add(new FenLei(R.mipmap.home_icon_04, "问答"));
            add(new FenLei(R.mipmap.home_icon_05, "路亚海钓"));
            add(new FenLei(R.mipmap.home_icon_06, "饵料配方"));
            // add(new FenLei(R.mipmap.home_icon_07, "渔具DIY"));
            // add(new FenLei(R.mipmap.home_icon_08, "渔具店"));
            //  add(new FenLei(R.mipmap.home_icon_09, "商城"));
            //add(new FenLei(R.mipmap.home_icon_10, "更多"));
        }}, getActivity(), 0);
        homeGv2.setAdapter(gvAdapter2);
        homeGv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //技巧  暂时跳转的是技巧详情  列表还没搭  测试了一下加载HTML标签
                        startActivity(new Intent(getActivity(), HomeSkillActivity.class));
                        break;
                    case 1:
                        //视频
//                        startActivity(new Intent(getActivity(), HomeVideoActivity.class));
                       // startActivity(new Intent(getActivity(), GoldMallActivity.class));
                        if (!TextUtils.isEmpty(SPUtils.getPreference(getActivity(), "userid"))) {
                            startActivity(new Intent(getActivity(), GoldMallActivity.class));
                        } else {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }

                        break;
                    case 2:
                        //钓鱼杂谈
                        gotoPostList("钓鱼杂谈", PublicStaticData.ID_TALk);
                        break;
                    case 3:
                        //问答
                        gotoPostList("问答", PublicStaticData.ID_QUESTION);
                        break;
                    case 4:
                        //路亚
                        gotoPostList("路亚", PublicStaticData.ID_LUYA);
                        break;
                    case 5:
                        //饵料
                        startActivity(new Intent(getActivity(), HomeBaitActivity.class));
                        break;
                    case 6:
                        //渔具DIY
                        gotoPostList("渔具DIY", PublicStaticData.ID_FISH_TOOL);
                        break;
                    case 7:
                        //渔具店
                        startActivity(new Intent(getActivity(), HomeFishingShopActivity.class));
                        break;
                    case 8:
                        //商城
                        if (!TextUtils.isEmpty(SPUtils.getPreference(getActivity(), "userid"))) {
                            startActivity(new Intent(getActivity(), GoldMallActivity.class));
                        } else {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                        break;
                    case 9:
                        //更多
                        ToastUtils.getInstance(getActivity()).showMessage("敬请期待");
                        break;
                }
            }
        });

        //homeNv.setNoticeList(list_string);
        //公告列表
        noticeAdapter = new CommonAdapter(getActivity(), noticeList, R.layout.item_notice_lv) {
            @Override
            public void convert(CommonViewHolder h, Object item, int position) {
                ((TextView) h.getConvertView().findViewById(R.id.home_tv_gg)).setText(noticeList.get(position).getTitle());
            }
        };
        homeLvGg.setAdapter(noticeAdapter);
        homeLvGg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击公告查看详情
                startActivity(new Intent(getActivity(), CommenWebviewActivity.class).putExtra("title", noticeList.get(i).getTitle()).putExtra("url", HttpUrl.URL + "?m=Home&c=Index&a=noticeIndex&n_id=" + noticeList.get(i).getN_id()));
            }
        });

        //商品推荐
        mManager2 = new LinearLayoutManager(getActivity());
        mManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvCateRecommend.setLayoutManager(mManager2);
        cateGoodsAdapter = new CateGoodsAdapter(getActivity());
        rvCateRecommend.setAdapter(cateGoodsAdapter);
        cateGoodsAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (cateGoodsAdapter.getDataList().get(position).isHas_coupon()) {
                    price = df.format(((Integer.valueOf(cateGoodsAdapter.getDataList().get(position).getMin_group_price())
                            -
                            Integer.valueOf(cateGoodsAdapter.getDataList().get(position).getCoupon_discount())))
                            / 100);
                } else {
                    price = df.format(Integer.valueOf(cateGoodsAdapter.getDataList().get(position).getMin_group_price()) / 100);
                }

                startActivity(new Intent(getActivity(), GoodsDetailActivity.class)
                        .putExtra("goods_id", cateGoodsAdapter.getDataList().get(position).getGoods_id())
                        .putExtra("price", price)
                        .putExtra("module", "recommend")
                );
            }
        });

        //下方帖子分类列表
        mFragments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mFragments.add(HomePostFragment.newInstance(i));
        }
        setHomeBottomColor();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.homeFragment_fl_content, mFragments.get(index)).show(mFragments.get(index)).commit();

        pullRefreshScrollview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //刷新
                //getNotice();
                getWeather();
                getRecommendData();
                ((HomePostFragment) mFragments.get(index)).refresh(index, true, refreshView);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //下载
                ((HomePostFragment) mFragments.get(index)).refresh(index, false, refreshView);
            }
        });
        // getWeather();//设置天气

        getRecommendData();//获取推荐商品
    }

    /**
     * 获取推荐数据
     */
    public void getRecommendData() {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("a", "recommend");
        formBody.add("page", "1");//传递键值对参数
        formBody.add("pagesize", "9");
        Request request = new Request.Builder()//创建Request 对象。
                .url(HttpUrl.shop_goods_list)
                .post(formBody.build())//传递请求体
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                String bodyString = buffer.clone().readString(charset);

                //传递的数据
                Bundle bundle = new Bundle();
                bundle.putString("msg", bodyString);
                //发送数据
                Message message = Message.obtain();
                message.setData(bundle);   //message.obj=bundle  传值也行
                message.what = 0x05;
                mHandler.sendMessage(message);

            }
        });
    }

    //跳转PostList帖子列表
    private void gotoPostList(String title, String topId) {
        startActivity(new Intent(getActivity(), PostListActivity.class)
                .putExtra("title", title)
                .putExtra("isPublish", true)
                .putExtra("topId", topId));
    }

    /**
     * 设置底部栏按钮变色
     */
    private void setHomeBottomColor() {
        switch (index) {
            case 0:
                homePostTvTuijian.setTextColor(getResources().getColor(R.color.c_269ceb));
                homePostViewTuijian.setVisibility(View.VISIBLE);
                homePostTvTuijian1.setTextColor(getResources().getColor(R.color.c_269ceb));
                homePostViewTuijian1.setVisibility(View.VISIBLE);
                break;
            case 1:
                homePostTvZuixin.setTextColor(getResources().getColor(R.color.c_269ceb));
                homePostViewZuixin.setVisibility(View.VISIBLE);
                homePostTvZuixin1.setTextColor(getResources().getColor(R.color.c_269ceb));
                homePostViewZuixin1.setVisibility(View.VISIBLE);
                break;
            case 2:
                homePostTvRemen.setTextColor(getResources().getColor(R.color.c_269ceb));
                homePostViewRemen.setVisibility(View.VISIBLE);
                homePostTvRemen1.setTextColor(getResources().getColor(R.color.c_269ceb));
                homePostViewRemen1.setVisibility(View.VISIBLE);
                break;
            case 3:
                homePostTvBendi.setTextColor(getResources().getColor(R.color.c_269ceb));
                homePostViewBendi.setVisibility(View.VISIBLE);
                homePostTvBendi1.setTextColor(getResources().getColor(R.color.c_269ceb));
                homePostViewBendi1.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 清除底部栏颜色
     */
    private void removeBottomColor() {
        switch (currentTabIndex) {
            case 0:
                homePostTvTuijian.setTextColor(getResources().getColor(R.color.c_333333));
                homePostViewTuijian.setVisibility(View.GONE);
                homePostTvTuijian1.setTextColor(getResources().getColor(R.color.c_333333));
                homePostViewTuijian1.setVisibility(View.GONE);
                break;
            case 1:
                homePostTvZuixin.setTextColor(getResources().getColor(R.color.c_333333));
                homePostViewZuixin.setVisibility(View.GONE);
                homePostTvZuixin1.setTextColor(getResources().getColor(R.color.c_333333));
                homePostViewZuixin1.setVisibility(View.GONE);
                break;
            case 2:
                homePostTvRemen.setTextColor(getResources().getColor(R.color.c_333333));
                homePostViewRemen.setVisibility(View.GONE);
                homePostTvRemen1.setTextColor(getResources().getColor(R.color.c_333333));
                homePostViewRemen1.setVisibility(View.GONE);
                break;
            case 3:
                homePostTvBendi.setTextColor(getResources().getColor(R.color.c_333333));
                homePostViewBendi.setVisibility(View.GONE);
                homePostTvBendi1.setTextColor(getResources().getColor(R.color.c_333333));
                homePostViewBendi1.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 控制fragment的变化
     */
    public void homeFragmentControl() {
        if (currentTabIndex != index) {

            /*if (mScrollY > homeLlXuanfu.getTop()) {
                scrolly.set(currentTabIndex, mScrollY);
                if (scrolly.get(index) == 0) {
                    pullRefreshScrollview.getRefreshableView().scrollTo(0, homeLlXuanfu.getTop());
                } else {
                    pullRefreshScrollview.getRefreshableView().scrollTo(0, scrolly.get(index));
                }
            }*/

            removeBottomColor();
            setHomeBottomColor();

            FragmentTransaction trx = getActivity().getSupportFragmentManager().beginTransaction();
            trx.hide(mFragments.get(currentTabIndex));
            if (!mFragments.get(index).isAdded()) {
                trx.add(R.id.homeFragment_fl_content, mFragments.get(index));
            }
            trx.show(mFragments.get(index)).commit();
            currentTabIndex = index;
            //切换城市后点击切换至“本地”时刷新数据
            if (index == 3 && type.equals("2")) {
                ((HomePostFragment) mFragments.get(index)).refresh(index, true, pullRefreshScrollview);
            }

            if (mScrollY > homeLlXuanfu.getTop()) {
                pullRefreshScrollview.getRefreshableView().scrollTo(0, homeLlXuanfu.getTop());
            }
        }
    }


    @OnClick({R.id.home_post_tv_tuijian, R.id.home_post_tv_zuixin, R.id.home_post_tv_remen, R.id.home_post_tv_bendi})
    public void onViewClickedPost(View view) {
        switch (view.getId()) {
            case R.id.home_post_tv_tuijian:
                //推荐
                index = 0;
                break;
            case R.id.home_post_tv_zuixin:
                //最新
                index = 1;
                break;
            case R.id.home_post_tv_remen:
                //最热
                index = 2;
                break;
            case R.id.home_post_tv_bendi:
                //本地
                index = 3;
                break;
        }
        homeFragmentControl();
    }

    @OnClick({R.id.home_post_tv_tuijian1, R.id.home_post_tv_zuixin1, R.id.home_post_tv_remen1, R.id.home_post_tv_bendi1})
    public void onViewClickedXuanFu(View view) {
        switch (view.getId()) {
            case R.id.home_post_tv_tuijian1:
                //推荐
                index = 0;
                break;
            case R.id.home_post_tv_zuixin1:
                //最新
                index = 1;
                break;
            case R.id.home_post_tv_remen1:
                //最热
                index = 2;
                break;
            case R.id.home_post_tv_bendi1:
                //本地
                index = 3;
                break;
        }
        homeFragmentControl();
    }

    @OnClick({R.id.home_tv_gg1, R.id.home_tv_gg2, R.id.home_tv_gg3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_tv_gg1:
                //公告1
                startActivity(new Intent(getActivity(), CommenWebviewActivity.class).putExtra("title", "公告").putExtra("url", ""));
                break;
            case R.id.home_tv_gg2:
                startActivity(new Intent(getActivity(), CommenWebviewActivity.class).putExtra("title", "公告").putExtra("url", ""));
                break;
            case R.id.home_tv_gg3:
                startActivity(new Intent(getActivity(), CommenWebviewActivity.class).putExtra("title", "公告").putExtra("url", ""));
                break;
        }
    }

    private class GVAdapter1 extends MyCommonAdapter<Integer> {

        public GVAdapter1(List<Integer> list, Context mContext, int resid) {
            super(list, mContext, R.layout.item_home_gv1);
        }

        @Override
        public void setDate(MyCommentViewHolder commentViewHolder, int position) {
            int iv_width = (PublicStaticData.width - 56) / 3;
            double dd = (PublicStaticData.width - 56) / 3 / 1.934;
            int iv_height = (int) dd;
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(iv_width, iv_height);
            ImageView item_home_gv1_iv = (ImageView) commentViewHolder.FindViewById(R.id.item_home_gv1_iv);
            item_home_gv1_iv.setLayoutParams(lp);
            item_home_gv1_iv.setImageResource(list.get(position));
        }
    }

    private class GVAdapter2 extends MyCommonAdapter<FenLei> {

        public GVAdapter2(List<FenLei> list, Context mContext, int resid) {
            super(list, mContext, R.layout.item_home_gv2);
        }

        @Override
        public void setDate(MyCommentViewHolder commentViewHolder, int position) {
            ImageView item_home_gv1_iv = (ImageView) commentViewHolder.FindViewById(R.id.item_home_gv2_iv);
            TextView item_home_gv2_tv = (TextView) commentViewHolder.FindViewById(R.id.item_home_gv2_tv);
            item_home_gv1_iv.setImageResource(list.get(position).getImg());
            item_home_gv2_tv.setText(list.get(position).getName());
        }
    }

    private class FenLei {
        private int img;
        private String name;

        public FenLei(int img, String name) {
            this.img = img;
            this.name = name;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.home_tv_area, R.id.home_ll_search, R.id.home_ll_weather, R.id.relCate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_tv_area:
                //选择地区
                //selectAddress();
                //startActivityForResult(new Intent(getActivity(), CityChangeActivity.class), HOME_CODE);
                startActivityForResult(new Intent(getActivity(), CityActivity.class), HOME_CODE);
                break;
            case R.id.home_ll_search:
                //搜索
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.home_ll_weather:
                //天气
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                startActivity(intent);
                break;
            case R.id.relCate:
                //精品推荐
                startActivity(new Intent(getActivity(), GoodsActivity.class)
                        .putExtra("a", "recommend")
                        .putExtra("cate_id", "")
                        .putExtra("keyword", "")
                        .putExtra("tag", 2)
                );
                break;
        }
    }

    /**
     * 选择地区弹窗
     */
    public void selectAddress() {
        //设置默认地址()
        String province = nowProvince;
        String city = nowCity;
        MyLogUtils.logE("当前位置=======", nowCity + "======" + nowProvince);
        PopSelectAddress popSelectAddress = new PopSelectAddress(getActivity(), getActivity().getWindow(), province, city, "");
        popSelectAddress.showHttpAddress(); // 网络下载地址数据
        popSelectAddress.setCallBack(new PopSelectAddress.CallBackAddress() {
            @Override
            public void onClickOk(String selProvince, String selCity, String selZone, String selProvinceCode, String selCityCode, String selZoneCode) {
                nowProvince = selProvince;
                nowCity = selCity;
                type = "2";
                setPosition(selCity, selCityCode);
            }
        });
    }

    /**
     * 设置定位、切换定位
     *
     * @param selCity
     * @param selCityCode
     */
    private void setPosition(final String selCity, String selCityCode) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.setPosition);
        commonOkhttp.putParams("cityid", selCityCode);
        commonOkhttp.putParams("cityname", selCity);
        commonOkhttp.putCallback(new MyGenericsCallback<EmptyResultBean>(getActivity()) {
            @Override
            public void onSuccess(EmptyResultBean data, int d) {
                super.onSuccess(data, d);
                homeTvArea.setText(selCity);
                SPUtils.savePreference(getActivity(), "selCity", selCity);
                //切换城市后，如果当前是“本地”，刷新数据
                if (currentTabIndex == 3 && type.equals("2")) {
                    ((HomePostFragment) mFragments.get(currentTabIndex)).refresh(currentTabIndex, true, pullRefreshScrollview);
                }

                getWeather();
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                if (TextUtils.equals(type, "1")) {
                    if (!TextUtils.isEmpty(SPUtils.getPreference(getActivity(), "userid"))) {
                        //已登录
                        updatePosition(latitude, longitude);
                    }
                    return;
                }
                ToastUtils.getInstance(getActivity()).showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 唤醒定位
     *
     * @param latitude
     * @param longitude
     */
    private void updatePosition(final double latitude, double longitude) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.updatePosition);
        commonOkhttp.putParams("lat", latitude + "");
        commonOkhttp.putParams("lng", longitude + "");
        commonOkhttp.putCallback(new MyGenericsCallback<EmptyResultBean>(getActivity()) {
            @Override
            public void onSuccess(EmptyResultBean data, int d) {
                super.onSuccess(data, d);

            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
//                ToastUtils.getInstance(getActivity()).showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 获取天气
     */
    public void getWeather() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.COMMON_GETWEATHER);
        commonOkhttp.putCallback(new MyGenericsCallback<WeatherEntity>(getActivity()) {
            @Override
            public void onSuccess(WeatherEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    if (data.getHeWeather6().get(0).getDaily_forecast().get(0).getCond_code_d() != null)
                        GlideUtils.loadImageView(getActivity(), "https://cdn.heweather.com/cond_icon/" +
                                data.getHeWeather6().get(0).getDaily_forecast().get(0).getCond_code_d() + ".png", ivWeather);
                    if (data.getHeWeather6().get(0).getDaily_forecast().get(0).getCond_txt_d() != null)
                        homeTvWeather.setText(data.getHeWeather6().get(0).getDaily_forecast().get(0).getCond_txt_d());
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }
        });
        commonOkhttp.Execute();
    }


    private LocationClient mLocationClient;

    private void initLocation() {
        //声明LocationClient类
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        //注册监听函数
        LocationListener myListener = new LocationListener();
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        //开始定位
        //locationTask();
        requestMorePermissions();
        LocationManager locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
            ToastUtils.getInstance(getActivity()).showMessage("请打开GPS,否则可能导致定位失败或定位不准");
        }
    }

    private boolean isFirstLocation = true;

    public class LocationListener implements BDLocationListener {

        //接受位置信息
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            latitude = bdLocation.getLatitude();
            longitude = bdLocation.getLongitude();
            nowProvince = bdLocation.getProvince();
            nowCity = bdLocation.getCity();
            county = bdLocation.getDistrict();
            MyLogUtils.logE("当前位置=======", nowProvince + "======" + nowCity + "======" + county);
            PublicStaticData.sharedPreferences.edit().putString("latitude", latitude + "").commit();
            PublicStaticData.sharedPreferences.edit().putString("longitude", longitude + "").commit();
            PublicStaticData.sharedPreferences.edit().putString("province", nowProvince).commit();
            PublicStaticData.sharedPreferences.edit().putString("city", nowCity).commit();
            PublicStaticData.sharedPreferences.edit().putString("county", county).commit();

            SPUtils.savePreference(getActivity(), "homeCity", nowCity);
            SPUtils.savePreference(getActivity(), "nowProvince", nowProvince);
            SPUtils.savePreference(getActivity(), "county", county);
            homeTvArea.setText(nowCity);
            type = "1";
            if (isFirstLocation) {
                setPosition(nowCity, "");
                isFirstLocation = false;
                mLocationClient.stop();
            }

        }
    }

    /**
     * 初始化权限事件
     */
    private void initPermission() {
        //检查权限
        String[] permissions = CheckPermissionUtils.checkPermission(getActivity());
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
            Log.e("ssssss", "权限都申请了");
        } else {
            //申请权限
            Log.e("ssssss", "申请权限");
            ActivityCompat.requestPermissions(getActivity(), permissions, 100);
        }
    }

    // 普通申请多个权限
    private void requestMorePermissions() {
        if (Build.VERSION.SDK_INT >= 23
                && getActivity().getApplicationInfo().targetSdkVersion >= 23) {
            PermissionUtils.checkAndRequestMorePermissions(getActivity(),
                    PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS,
                    new PermissionUtils.PermissionRequestSuccessCallBack() {
                        @Override
                        public void onHasPermission() {
                            // 权限已被授予
                            if (mLocationClient != null) {
                                mLocationClient.start();
                            }
                        }
                    });
        } else {
            if (mLocationClient != null) {
                mLocationClient.start();
            }
        }
    }

    /**
     * 显示前往应用设置Dialog
     */
    private void showToAppSettingDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("需要权限")
                .setMessage("我们需要相关权限，才能实现功能，点击前往，将转到应用的设置界面，请开启应用的相关权限。")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.toAppSetting(getActivity());
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSIONS:
                PermissionUtils.onRequestMorePermissionsResult(getActivity(),
                        PERMISSIONS,
                        new PermissionUtils.PermissionCheckCallBack() {
                            @Override
                            public void onHasPermission() {
                                if (mLocationClient != null) {
                                    mLocationClient.start();
                                }
                            }

                            @Override
                            public void onUserHasAlreadyTurnedDown(String... permission) {
                                ToastUtils.getInstance(getActivity()).
                                        showMessage("我们需要" + Arrays.toString(permission) + "权限");
                            }

                            @Override
                            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                                ToastUtils.getInstance(getActivity()).
                                        showMessage("我们需要" + Arrays.toString(permission) + "权限");
                                showToAppSettingDialog();
                            }
                        });


        }
    }

//    /**
//     * EsayPermissions接管权限处理逻辑
//     *
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Log.e("ssssss", "接管权限处理");
//        mLocationClient.restart();
//        // Forward results to EasyPermissions
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // 权限被用户同意。
//                    initLocation();
//                } else {
//                    // 权限被用户拒绝了。
//                    ToastUtils.getInstance(getActivity()).showMessage("定位权限被禁止");
//                }
//                break;
//            case 100:
//                Log.e("ssssss", "这是啥啊");
//                break;
//        }
//    }

    @AfterPermissionGranted(PublicStaticData.REQUEST_LOCATION_PERM)
    public void locationTask() {
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Have permission, do the thing!
            mLocationClient.start();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "需要请求定位权限",
                    PublicStaticData.REQUEST_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        Toast.makeText(getActivity(),"执行onPermissionsGranted()..." , Toast.LENGTH_SHORT).show();
        System.out.println("===============执行onPermissionsGranted()...===================");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(getActivity(), "执行onPermissionsDenied()...", Toast.LENGTH_SHORT).show();
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "当前App需要申请定位权限,需要打开设置页面么?")
                    .setTitle("权限申请")
                    .setPositiveButton("确认")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(PublicStaticData.REQUEST_LOCATION_PERM)
                    .build()
                    .show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HOME_CODE) {
            if (resultCode == CITY_CHANGE_CODE) {
                Bundle bundle = data.getExtras();
                String selCity = bundle.getString("selCity");
                String selCityCode = bundle.getString("selCityCode");
                nowCity = selCity;
                type = "2";
                setPosition(selCity, selCityCode);
            }
        }
    }

}
