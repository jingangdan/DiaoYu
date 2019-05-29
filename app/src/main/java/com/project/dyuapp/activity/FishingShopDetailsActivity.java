package com.project.dyuapp.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.imageloader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.AroundShopAdapter;
import com.project.dyuapp.adapter.CommentListAdapter;
import com.project.dyuapp.adapter.FishingFriendsPhotoAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.FishingGearDetailsBean;
import com.project.dyuapp.entity.SuccessBean;
import com.project.dyuapp.myutils.CompressImageUtil;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyPicMethodUtil;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myutils.ToastUtils;
import com.project.dyuapp.myviews.CustomDialog;
import com.project.dyuapp.myviews.GridViewForScrollView;
import com.project.dyuapp.myviews.ListViewForScrollView;
import com.project.dyuapp.runtimepermissions.PermissionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.project.dyuapp.R.id.shop_details_img_icon_big;
import static com.project.dyuapp.myutils.PublicStaticData.REQUEST_CODE_SELECT;

/**
 * @author 田亭亭
 * @description 渔具店详情Activity
 * @created at 2017/11/25 0025
 * @change
 */
public class FishingShopDetailsActivity extends MyBaseActivity {

    @Bind(R.id.shop_details_tv_name)
    TextView tvName;//渔具店名称
    //    @Bind(R.id.shop_details_details_sbt_level)
//    StarBar sbtLevel;//等级
    @Bind(R.id.item_shop_ll_level)
    LinearLayout itemShopLlLevel;
    @Bind(R.id.shop_details_tv_comment_number)
    TextView tvCommentNumber;//点评数
    @Bind(R.id.shop_details_tv_photo_number)
    TextView tvPhotoNumber;//图片数
    @Bind(R.id.shop_details_tv_address)
    TextView tvAddress;//详细地址
    @Bind(R.id.shop_details_tv_phone)
    TextView tvPhone;//电话
    @Bind(R.id.shop_details_tv_brief_introduction)
    TextView tvBriefIntroduction;//简介
    @Bind(R.id.shop_details_gv_photo_list)
    GridViewForScrollView gvPhotoList;//钓图列表控件
    @Bind(R.id.shop_details_lv_fish_friends_comments)
    ListViewForScrollView lvFishFriendsComments;//钓友点评
    @Bind(R.id.shop_details_lv_around_list)
    ListViewForScrollView lvAroundList;//周边渔具店
    @Bind(R.id.shop_details_tv_attention)
    TextView tvAttention;//是否关注
    @Bind(shop_details_img_icon_big)
    ImageView imgIconBig;
    @Bind(R.id.shop_details_details_sv)
    PullToRefreshScrollView shopDetailsDetailsSv;
    private List<FishingGearDetailsBean.CommentsBean> commentsList;//评论列表
    private List<FishingGearDetailsBean.AmbitusBean> aroundList;//周边渔具店列表
    private List<FishingGearDetailsBean.ImgsBean> photoList;//图片列表


    private AroundShopAdapter mAroundAdapter;//周边渔具店适配器
    private CommentListAdapter mCommentAdapter;//渔获适配器
    private FishingFriendsPhotoAdapter mAdapter;//钓友上图适配器显示
    private CustomDialog mDialog;
    private CustomDialog selDialog;
    private String lat = "";
    private String lon = "";
    private String cityId = "";
    private String fishingShopId = "";
    private List<ImageItem> upLoadList;
    private String shopPhone;//电话
    private boolean isReview;
    private ArrayList<ImageItem> listPic = new ArrayList<ImageItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_fishing_shop_details);
        ButterKnife.bind(this);
        initImagePicker();
        setTvTitle("详情");
        isReview = false;
//        setIvBack();
//        getIvRight().setImageDrawable(this.getResources().getDrawable(R.mipmap.nav_share));
//        getIvRight().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(SPUtils.getPreference(FishingShopDetailsActivity.this, "userid"))) {
//                    //已登录
//                    new ShareUtil(FishingShopDetailsActivity.this, FishingShopDetailsActivity.this,"","","http://www.diaoyuba.com/index.php/Home/Wechat/shopDetail?gid="+fishingShopId);
//                } else {
//                    //未登录
//                    startActivity(new Intent(FishingShopDetailsActivity.this, LoginActivity.class));
//                }
//            }
//        });
        getIvBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReview) {
                    setResult(PublicStaticData.FISHING_SHOP_DETAILS);
                }
                ScreenManager.getInstance().removeActivity(FishingShopDetailsActivity.this);
            }
        });
        initData();
        okhttpFishingGearDetails();

    }

    private void initData() {
        lat = getIntent().getStringExtra("lat");
        lon = getIntent().getStringExtra("lon");
        fishingShopId = getIntent().getStringExtra("id");
        cityId = getIntent().getStringExtra("cityid");

        //图片
        gvPhotoList.setFocusable(false);
        photoList = new ArrayList<>();
        mAdapter = new FishingFriendsPhotoAdapter(photoList, FishingShopDetailsActivity.this, R.layout.item_fishing_friends_photo);
        gvPhotoList.setAdapter(mAdapter);
        //评论
        lvFishFriendsComments.setFocusable(false);
        commentsList = new ArrayList<>();
        mCommentAdapter = new CommentListAdapter(commentsList, FishingShopDetailsActivity.this, R.layout.item_fishing_friends_comment);
        lvFishFriendsComments.setAdapter(mCommentAdapter);
        //周边
        lvAroundList.setFocusable(false);
        aroundList = new ArrayList<>();
        mAroundAdapter = new AroundShopAdapter(aroundList, FishingShopDetailsActivity.this, R.layout.item_home_fishing_shop);
        lvAroundList.setAdapter(mAroundAdapter);
        lvAroundList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                fishingShopId = aroundList.get(position).getFishing_shop_id();
                lat = aroundList.get(position).getLatitude();
                lon = aroundList.get(position).getLongitude();
                cityId = aroundList.get(position).getCityid();
                okhttpFishingGearDetails();
                shopDetailsDetailsSv.getRefreshableView().scrollTo(0, 0);

            }
        });

        shopDetailsDetailsSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                okhttpFishingGearDetails();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });
    }

    /**
     * 渔具店详情接口
     */
    private void okhttpFishingGearDetails() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.fishingGearDetails);
        commonOkhttp.putParams("fishing_shop_id", fishingShopId);
        commonOkhttp.putParams("cityid", cityId);
        commonOkhttp.putParams("lat", lat);
        commonOkhttp.putParams("lng", lon);
        commonOkhttp.putCallback(new MyGenericsCallback<FishingGearDetailsBean>(FishingShopDetailsActivity.this) {
            @Override
            public void onSuccess(FishingGearDetailsBean data, int d) {
                super.onSuccess(data, d);
                try {
                    if (data.getFishing_shop() != null) {
                        String count = data.getFishing_shop().getCount();//评论数量
                        int isCollect = data.getFishing_shop().getIs_collect();//是否已关注
                        String shopImage = data.getFishing_shop().getShop_image();//渔具店图片

                        String shopIntro = data.getFishing_shop().getShop_intro();//简介
                        String shopName = data.getFishing_shop().getShop_name();//渔具店名称
                        String star = data.getFishing_shop().getStar();//评分
                        String imgCount = data.getFishing_shop().getImg_count();//图片数量
                        String shopAddress = data.getFishing_shop().getShop_address();//地址
                        shopPhone = data.getFishing_shop().getShop_phone();
                        lat = data.getFishing_shop().getLatitude();
                        lon = data.getFishing_shop().getLongitude();
                        if (!TextUtils.isEmpty(String.valueOf(isCollect))) {
                            //是否已关注  1是 2否
                            if (TextUtils.equals(String.valueOf(isCollect), "1")) {
                                tvAttention.setCompoundDrawablesWithIntrinsicBounds(null, FishingShopDetailsActivity.this.getResources().getDrawable(R.mipmap.tab_fans_selected), null, null);
                                tvAttention.setTextColor(FishingShopDetailsActivity.this.getResources().getColor(R.color.c_269ceb));
                                tvAttention.setText("已关注");
                            } else if (TextUtils.equals(String.valueOf(isCollect), "2")) {
                                tvAttention.setCompoundDrawablesWithIntrinsicBounds(null, FishingShopDetailsActivity.this.getResources().getDrawable(R.mipmap.tab_fans_unselected), null, null);
                                tvAttention.setTextColor(FishingShopDetailsActivity.this.getResources().getColor(R.color.c_333333));
                                tvAttention.setText("关注");
                            }
                        }

                        if (!TextUtils.isEmpty(star)) {
                            SPUtils.setLevel(itemShopLlLevel, FishingShopDetailsActivity.this, Integer.parseInt(star));
                        }
                        if (!TextUtils.isEmpty(imgCount)) {
                            tvPhotoNumber.setText(imgCount);
                        }
                        if (!TextUtils.isEmpty(count)) {
                            tvCommentNumber.setText(count);
                        }
                        if (!TextUtils.isEmpty(shopImage)) {
                            GlideUtils.loadImageView(FishingShopDetailsActivity.this, HttpUrl.IMAGE_URL + shopImage, imgIconBig);
                            listPic.clear();
                            listPic.add(new ImageItem("", HttpUrl.IMAGE_URL + shopImage));
                        }
                        if (!TextUtils.isEmpty(shopIntro)) {
                            tvBriefIntroduction.setText(shopIntro);
                        }
                        if (!TextUtils.isEmpty(shopName)) {
                            tvName.setText(shopName);
                        }
                        if (!TextUtils.isEmpty(shopAddress)) {
                            tvAddress.setText(shopAddress);
                        }
                        if (!TextUtils.isEmpty(shopPhone)) {
                            tvPhone.setText(shopPhone);
                        }
                    }
                    aroundList.clear();
                    commentsList.clear();
                    photoList.clear();
                    mAroundAdapter.notifyDataSetChanged();
                    mCommentAdapter.notifyDataSetChanged();
                    mAdapter.notifyDataSetChanged();
                    if (data.getAmbitus() != null && data.getAmbitus().size() != 0) {
                        aroundList.addAll(data.getAmbitus());
                        mAroundAdapter.notifyDataSetChanged();
                    }
                    if (data.getComments() != null && data.getComments().size() != 0) {
                        commentsList.addAll(data.getComments());
                        mCommentAdapter.notifyDataSetChanged();

                    }
                    final ArrayList<ImageItem> imageItems = new ArrayList<>();
                    if (data.getImgs() != null && data.getImgs().size() != 0) {
                        photoList.addAll(data.getImgs());
                        mAdapter.notifyDataSetChanged();
                        photoList.clear();
                        photoList.addAll(data.getImgs());
                        mAdapter.notifyDataSetChanged();
                        for (int i = 0; i < data.getImgs().size(); i++) {
                            imageItems.add(new ImageItem("", HttpUrl.IMAGE_URL + data.getImgs().get(i).getImg_url()));
                        }
                        System.out.println("=====图片集合大小=====" + imageItems.size());
                        gvPhotoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                MyPicMethodUtil.preview(FishingShopDetailsActivity.this, imageItems, position);

                            }
                        });
                    }
                    if (shopDetailsDetailsSv != null) {
                        shopDetailsDetailsSv.onRefreshComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(FishingShopDetailsActivity.this).showMessage(FishingShopDetailsActivity.this.getResources().getString(R.string.data_parsing_failed));
                }


            }
        });
        commonOkhttp.Execute();
    }

    //初始化图片选择器
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());  //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                            //允许裁剪（单选才有效）
        imagePicker.setMultiMode(true);                       //多选
        imagePicker.setSelectLimit(9);                          //最多9张
    }

    //相机
    private void camera() {
        Intent intent = new Intent(FishingShopDetailsActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    //相册
    private void album() {
        Intent intent = new Intent(FishingShopDetailsActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            upLoadList = new ArrayList<>();
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    int i = 0;
                    for (ImageItem item : images) {
                        File file = CompressImageUtil.radioImage(new File(item.path));
                        upLoadList.add(new ImageItem(i + "", file.getAbsolutePath()));
                        i++;
                    }
                    okhttpSpreadImgs();
                }
            }
        }
        if (requestCode == PublicStaticData.FISHING_SHOP_DETAILS) {
            if (resultCode == PublicStaticData.RESULT_CODE_RREVIEW) {
                if (data != null) {
                    isReview = true;
                    lon = data.getStringExtra("lon");
                    lat = data.getStringExtra("lat");
                    okhttpFishingGearDetails();
                }
            }
        }
    }


    @OnClick({R.id.shop_details_img_icon_big, R.id.shop_details_ll_look_address, R.id.shop_details_ll_take_phone, R.id.shop_details_ll_fish_friends_photo, R.id.shop_details_ll_fish_friends_comments, R.id.shop_details_ll_around_fish, R.id.shop_details_ll_input_comment, R.id.shop_details_tv_error, R.id.shop_details_tv_upload, R.id.shop_details_tv_attention})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shop_details_img_icon_big:
                //预览
                MyPicMethodUtil.previewHead(this, listPic);
                break;
            case R.id.shop_details_ll_look_address:
                //查看地址
                startActivity(new Intent(FishingShopDetailsActivity.this, SingleMapDistributionActivity.class).putExtra("whereFrom", "2")
                        .putExtra("lat", lat)
                        .putExtra("lon", lon)
                        .putExtra("pay_type", "")
                        .putExtra("name", tvName.getText().toString())
                        .putExtra("address", tvAddress.getText().toString())
                );
                break;
            case R.id.shop_details_ll_take_phone:
                //拨打电话
                if (!TextUtils.isEmpty(SPUtils.getPreference(FishingShopDetailsActivity.this, "userid"))) {
                    //已登录
                    if (!TextUtils.isEmpty(shopPhone)) {
                        showTakePhoneDialog();
                    }
                } else {
                    //未登录
                    startActivity(new Intent(FishingShopDetailsActivity.this, LoginActivity.class));
                }
                break;
            case R.id.shop_details_ll_fish_friends_photo:
                //钓友上图
                startActivity(new Intent(FishingShopDetailsActivity.this, FishingFriendsUploadActivity.class).putExtra("whereFrom", "2").putExtra("fishing_shop_id", fishingShopId));
                break;
            case R.id.shop_details_ll_fish_friends_comments:
                //钓友点评
                startActivity(new Intent(FishingShopDetailsActivity.this, AllCommentActivity.class)
                        .putExtra("object_id", fishingShopId)
                        .putExtra("whereFrom", "1")
                        .putExtra("object_type", "4"));
                break;
            case R.id.shop_details_ll_around_fish:
                //周边渔具店
                break;
            case R.id.shop_details_ll_input_comment:
                //写下评论
                if (!TextUtils.isEmpty(SPUtils.getPreference(FishingShopDetailsActivity.this, "userid"))) {
                    //已登录
                    startActivityForResult(new Intent(FishingShopDetailsActivity.this, ReviewActivity.class)
                            .putExtra("object_id", fishingShopId)
                            .putExtra("object_type", "4")
                            .putExtra("lat", lat)
                            .putExtra("lon", lon), PublicStaticData.FISHING_SHOP_DETAILS);
                } else {
                    //未登录
                    startActivity(new Intent(FishingShopDetailsActivity.this, LoginActivity.class));
                }
                break;
            case R.id.shop_details_tv_error:
                //报错
                if (!TextUtils.isEmpty(SPUtils.getPreference(FishingShopDetailsActivity.this, "userid"))) {
                    //已登录
                    startActivity(new Intent(FishingShopDetailsActivity.this, ReportErrorsActivity.class)
                            .putExtra("whereFrom", "2")
                            .putExtra("fishing_shop_id", fishingShopId));
                } else {
                    //未登录
                    startActivity(new Intent(FishingShopDetailsActivity.this, LoginActivity.class));
                }

                break;
            case R.id.shop_details_tv_upload:
                //传图
                if (!TextUtils.isEmpty(SPUtils.getPreference(FishingShopDetailsActivity.this, "userid"))) {
                    //已登录
                    //相册
                    PermissionUtils.storage(FishingShopDetailsActivity.this, new PermissionUtils.OnPermissionResult() {
                        @Override
                        public void onGranted() {
                            album();
                        }
                    });
                } else {
                    //未登录
                    startActivity(new Intent(FishingShopDetailsActivity.this, LoginActivity.class));
                }
                break;
            case R.id.shop_details_tv_attention:
                //关注
                if (!TextUtils.isEmpty(SPUtils.getPreference(FishingShopDetailsActivity.this, "userid"))) {
                    //已登录
                    okhttpIsAttention();
                } else {
                    //未登录
                    startActivity(new Intent(FishingShopDetailsActivity.this, LoginActivity.class));
                }

                break;
        }
    }

    /**
     * 拨打电话弹框
     */
    public void showTakePhoneDialog() {
        final CustomDialog phoneDialog = new CustomDialog(this, R.layout.dialog_take_phone, R.style.CustomDialogTheme);
        phoneDialog.setCanceledOnTouchOutside(true);
        phoneDialog.show();
        TextView tvPhone = (TextView) phoneDialog.findViewById(R.id.dialog_tv_phone);
        tvPhone.setText(shopPhone);
        phoneDialog.findViewById(R.id.dialog_tv_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //呼叫
                //拨打
                PermissionUtils.phone(FishingShopDetailsActivity.this, new PermissionUtils.OnPermissionResult() {
                    @Override
                    public void onGranted() {
                        if (!TextUtils.isEmpty(shopPhone)) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + shopPhone));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
                phoneDialog.dismiss();
            }
        });
        phoneDialog.findViewById(R.id.dialog_tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                phoneDialog.dismiss();
            }
        });
    }

    /**
     * 关注接口
     */
    private void okhttpIsAttention() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.is_attention);
        commonOkhttp.putParams("fishing_shop_id", fishingShopId);
        commonOkhttp.putCallback(new MyGenericsCallback<SuccessBean>(this) {
            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                try {
                    ToastUtils.getInstance(FishingShopDetailsActivity.this).showMessage(message.toString());
                    if (TextUtils.equals(message, "取消关注")) {
                        tvAttention.setCompoundDrawablesWithIntrinsicBounds(null, FishingShopDetailsActivity.this.getResources().getDrawable(R.mipmap.tab_fans_unselected), null, null);
                        tvAttention.setTextColor(FishingShopDetailsActivity.this.getResources().getColor(R.color.c_333333));
                        tvAttention.setText("关注");
                        setResult(PublicStaticData.FISHING_SHOP_DETAILS);
                    } else if (TextUtils.equals(message, "关注成功")) {
                        tvAttention.setCompoundDrawablesWithIntrinsicBounds(null, FishingShopDetailsActivity.this.getResources().getDrawable(R.mipmap.tab_fans_selected), null, null);
                        tvAttention.setTextColor(FishingShopDetailsActivity.this.getResources().getColor(R.color.c_269ceb));
                        tvAttention.setText("已关注");
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(FishingShopDetailsActivity.this).showMessage(FishingShopDetailsActivity.this.getResources().getString(R.string.data_parsing_failed));
                }
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 传图接口
     */
    private void okhttpSpreadImgs() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.spreadImgs);
        commonOkhttp.putParams("fishing_shop_id", fishingShopId);
        for (int i = 0; i < upLoadList.size(); i++) {
            File file = new File(upLoadList.get(i).path);
            if (file.exists()) {
                commonOkhttp.putFile((i + 1) + "", file.getName(), file);
//                commonOkhttp.putFile("image" + (i + 1), file.getName(), file);
            }
        }
        commonOkhttp.putCallback(new MyGenericsCallback<SuccessBean>(this) {
            @Override
            public void onSuccess(SuccessBean data, int d) {
                super.onSuccess(data, d);
                try {
                    if (data.getState() == 200) {
                        okhttpFishingGearDetails();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }

    public void sendCommentDialog() {
        mDialog = new CustomDialog(FishingShopDetailsActivity.this, R.layout.dialog_input_comment, true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //TODO something
            if (isReview) {
                setResult(PublicStaticData.FISHING_SHOP_DETAILS);
            }
            ScreenManager.getInstance().removeActivity(FishingShopDetailsActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
