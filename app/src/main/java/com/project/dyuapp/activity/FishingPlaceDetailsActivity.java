package com.project.dyuapp.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.imageloader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.FishingFriendDPAdapter;
import com.project.dyuapp.adapter.HomeFishingPlaceDetailsAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.FishingPlaceEntity;
import com.project.dyuapp.entity.SuccessBean;
import com.project.dyuapp.myutils.CompressImageUtil;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyPicMethodUtil;
import com.project.dyuapp.myutils.SPUtils;
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
import okhttp3.Call;

import static com.project.dyuapp.R.id.place_details_iv;
import static com.project.dyuapp.myutils.HttpUrl.IMAGE_URL;
import static com.project.dyuapp.myutils.PublicStaticData.ID_GET_FISH;
import static com.project.dyuapp.myutils.PublicStaticData.REQUEST_CODE_SELECT;

/**
 * @author 田亭亭
 * @description 钓场详情Activity
 * @created at 2017/11/25 0025
 * @change
 */
public class FishingPlaceDetailsActivity extends MyBaseActivity {

    @Bind(place_details_iv)
    ImageView placeDetailsIv;
    @Bind(R.id.place_details_tv_name)
    TextView tvName;//钓场名称
    @Bind(R.id.place_details_tv_place)
    TextView tvPlace;//钓场地点
    @Bind(R.id.place_details_tv_cost)
    TextView tvCost;//花费
    @Bind(R.id.place_details_tv_site_number)
    TextView tvSiteNumber;//钓位
    @Bind(R.id.place_details_tv_parking_number)
    TextView tvParkingNumber;//停车位
    @Bind(R.id.place_details_tv_address)
    TextView tvAddress;//详细地址
    @Bind(R.id.place_details_tv_phone)
    TextView tvPhone;//电话
    @Bind(R.id.place_details_tv_fishing)
    TextView placeDetailsTvFishing;
    @Bind(R.id.place_details_tv_brief_introduction)
    TextView tvBriefIntroduction;//简介
    @Bind(R.id.place_details_gv_photo_list)
    GridViewForScrollView gvPhotoList;//钓图列表
    @Bind(R.id.place_details_lv_fish_get_list)
    ListViewForScrollView lvFishGetList;//渔获列表
    @Bind(R.id.place_details_lv_around_list)
    ListViewForScrollView lvAroundList;//周边列表
    @Bind(R.id.place_details_tv_attention)
    TextView tvAttention;//关注

    @Bind(R.id.place_details_sv)
    PullToRefreshScrollView pullToRefreshScrollView;

    @Bind(R.id.place_details_lv_fish_dp_list)
    ListViewForScrollView placeDetailsLvFishDpList;
    @Bind(R.id.item_fishing_detail_ll_level)
    LinearLayout itemFishingDetailLlLevel;
    private List<FishingPlaceEntity.ImagesBean> photoList;
    private List<FishingPlaceEntity.FishForumBean> fishGetList;
    private List<FishingPlaceEntity.FishListBean> aroundList;

    private HomeFishingPlaceDetailsAdapter mFishingPlaceAdapter;
    private CustomDialog selDialog;
    private int i = 0;

    private String fishingPlaceId;//钓场id
    private String dp_score;//钓场评分
    private MyCommonAdapter mAdapter;//钓友上图适配器
    private MyCommonAdapter mFishGetAdapter;//钓鱼渔获适配器
    private List<ImageItem> upLoadList;//添加图片集合
    private String lat = "";
    private String lon = "";
    private String placePhone = "";
    private FishingPlaceEntity fishingPlaceEntity = new FishingPlaceEntity();
    private ArrayList<ImageItem> listPic = new ArrayList<ImageItem>();

    /*点评*/
    private List<FishingPlaceEntity.FishDianpingBean> fishDPList;
    private FishingFriendDPAdapter dpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_fishing_place_details);
        ButterKnife.bind(this);
        initImagePicker();
//        MyPicMethodUtil.initImagePicker();
        setTvTitle("详情");
        setIvBack();
//        getIvRight().setImageDrawable(this.getResources().getDrawable(R.mipmap.nav_share));
//        getIvRight().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new ShareUtil(FishingPlaceDetailsActivity.this, FishingPlaceDetailsActivity.this,"","","http://www.diaoyuba.com/index.php/Home/Wechat/fishPlaceDetails?gid="+fishingPlaceId);
//            }
//        });
        initData();
    }

    private void initData() {
        if (getIntent() != null && getIntent().getStringExtra("FishingPlaceId") != null)
            fishingPlaceId = getIntent().getStringExtra("FishingPlaceId");
        dp_score = getIntent().getStringExtra("dp_score");

        //星级
        if (!TextUtils.isEmpty(dp_score)) {
            SPUtils.setLevel(itemFishingDetailLlLevel, this, Integer.parseInt(dp_score));
        }

        photoList = new ArrayList<>();
        mAdapter = new MyCommonAdapter<FishingPlaceEntity.ImagesBean>(photoList, FishingPlaceDetailsActivity.this, R.layout.item_fishing_friends_photo) {
            @Override
            public void setDate(MyCommentViewHolder commentViewHolder, int position) {

                ImageView imageView = (ImageView) commentViewHolder.getConverView().findViewById(R.id.item_fishing_friends_img_photo);
                if (photoList.get(position).getImg_url() != null) {
                    GlideUtils.loadImageView(mContext, IMAGE_URL + photoList.get(position).getImg_url(), imageView);
                }
            }
        };
        gvPhotoList.setAdapter(mAdapter);
        fishGetList = new ArrayList<>();
        mFishGetAdapter = new MyCommonAdapter<FishingPlaceEntity.FishForumBean>(fishGetList, FishingPlaceDetailsActivity.this, R.layout.item_post_list) {
            @Override
            public void setDate(MyCommentViewHolder commentViewHolder, int position) {
                ImageView img = (ImageView) commentViewHolder.getConverView().findViewById(R.id.post_list_iv_pic);
                TextView title = (TextView) commentViewHolder.getConverView().findViewById(R.id.post_list_tv_title);
                TextView content = (TextView) commentViewHolder.getConverView().findViewById(R.id.post_list_tv_content);
                TextView label1 = (TextView) commentViewHolder.getConverView().findViewById(R.id.post_list_tv_label1);
                TextView label2 = (TextView) commentViewHolder.getConverView().findViewById(R.id.post_list_tv_label2);
                PorterShapeImageView img_head = (PorterShapeImageView) commentViewHolder.getConverView().findViewById(R.id.post_list_iv_head);
                TextView name = (TextView) commentViewHolder.getConverView().findViewById(R.id.post_list_tv_name);

                //封面图片
//                if (fishGetList.get(position).getThumb_img() != null)
                GlideUtils.loadImageView(mContext, IMAGE_URL + fishGetList.get(position).getThumb_img(), img);
                //标题
                if (fishGetList.get(position).getTitle() != null)
                    title.setText(fishGetList.get(position).getTitle());
                //描述
                if (fishGetList.get(position).getStr_content() != null)
                    content.setText(fishGetList.get(position).getStr_content());
                //是否是精品
                if (fishGetList.get(position).getIs_jinghua() != null)
                    if (TextUtils.equals("1", fishGetList.get(position).getIs_jinghua())) {
                        label1.setVisibility(View.VISIBLE);
                    } else {
                        label1.setVisibility(View.GONE);
                    }
                //是否是推荐
                if (fishGetList.get(position).getIs_tuijian() != null)
                    if (TextUtils.equals("1", fishGetList.get(position).getIs_tuijian())) {
                        label2.setVisibility(View.VISIBLE);
                    } else {
                        label2.setVisibility(View.GONE);
                    }
                //用户头像
                if (fishGetList.get(position).getMember_list_headpic() != null)
                    GlideUtils.loadImageView(mContext, IMAGE_URL + fishGetList.get(position).getMember_list_headpic(), img_head);
                //用户昵称
                if (fishGetList.get(position).getMember_list_nickname() != null)
                    name.setText(fishGetList.get(position).getMember_list_nickname());
            }
        };
        lvFishGetList.setAdapter(mFishGetAdapter);
        lvFishGetList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(FishingPlaceDetailsActivity.this, SkillDetailsActivity.class)
                        .putExtra("id", fishGetList.get(position).getF_id()));
            }
        });
        aroundList = new ArrayList<>();
        // aroundList.add("");
        mFishingPlaceAdapter = new HomeFishingPlaceDetailsAdapter(aroundList, FishingPlaceDetailsActivity.this, R.layout.item_home_fishing_place);
        lvAroundList.setAdapter(mFishingPlaceAdapter);
        lvAroundList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fishingPlaceId = aroundList.get(position).getF_gid();
                downLoadfishingPlace();
            }
        });

        fishDPList = new ArrayList<>();
        dpAdapter = new FishingFriendDPAdapter(fishDPList, FishingPlaceDetailsActivity.this);
        placeDetailsLvFishDpList.setAdapter(dpAdapter);

        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                downLoadfishingPlace();
            }
        });
        gvPhotoList.setFocusable(false);
        lvFishGetList.setFocusable(false);
        lvAroundList.setFocusable(false);
        placeDetailsLvFishDpList.setFocusable(false);
        downLoadfishingPlace();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        downLoadfishingPlace();
    }

    //弹框选择相册相机
    private void showPopPicture() {
        selDialog = new CustomDialog(this, R.layout.pop_sel_pic_way, R.style.CustomDialogTheme, true);
        selDialog.setCanceledOnTouchOutside(true);
        selDialog.show();
        selDialog.findViewById(R.id.tv_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册
                PermissionUtils.storage(FishingPlaceDetailsActivity.this, new PermissionUtils.OnPermissionResult() {
                    @Override
                    public void onGranted() {
                        album();
                    }
                });
                selDialog.dismiss();
            }
        });
        selDialog.findViewById(R.id.tv_carme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相机
                PermissionUtils.camera(FishingPlaceDetailsActivity.this, new PermissionUtils.OnPermissionResult() {
                    @Override
                    public void onGranted() {
                        camera();
                    }
                });
                selDialog.dismiss();
            }
        });
        selDialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                selDialog.dismiss();
            }
        });
    }

    //初始化图片选择器
    private void initImagePicker() {
      /*  int width = 720;
        int height = 448;*/
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                            //允许裁剪（单选才有效）
//        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setMultiMode(true);                      //多选
        imagePicker.setSelectLimit(9);                        //最多9张
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
  /*      imagePicker.setFocusWidth(width);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(height);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(width);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(height);                         //保存文件的高度。单位像素*/
    }

    //相机
    private void camera() {
        Intent intent = new Intent(FishingPlaceDetailsActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    //相册
    private void album() {
        Intent intent = new Intent(FishingPlaceDetailsActivity.this, ImageGridActivity.class);
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
    }


    @OnClick({R.id.place_details_ll_look_address, R.id.place_details_ll_take_phone
            , R.id.place_details_ll_fish_friends_photo, R.id.place_details_ll_fish_get
            , R.id.place_details_ll_fish_around, R.id.place_details_tv_error
            , R.id.place_details_tv_upload, R.id.place_details_tv_attention
            , R.id.place_details_ll_public, R.id.place_details_iv,
            R.id.place_details_ll_fish_dp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.place_details_iv:
                //图片放大
                MyPicMethodUtil.previewHead(this, listPic);
                break;
            case R.id.place_details_ll_look_address:
                //查看地址
                startActivity(new Intent(FishingPlaceDetailsActivity.this, SingleMapDistributionActivity.class).putExtra("whereFrom", "1")
                        .putExtra("lat", lat)
                        .putExtra("lon", lon)
                        .putExtra("pay_type", fishingPlaceEntity.getPay_type())
                        .putExtra("name", tvName.getText().toString())
                        .putExtra("address", tvAddress.getText().toString()));
                break;
            case R.id.place_details_ll_take_phone:
                //拨打电话
                if (!TextUtils.isEmpty(placePhone)) {
                    showTakePhoneDialog();
                }
                break;
            case R.id.place_details_ll_fish_friends_photo:
                //查看全部钓友上图

              /*  if (!TextUtils.isEmpty(SPUtils.getPreference(this,"userid"))) {
                }else {
                    startActivity(new Intent(this,LoginActivity.class));
                }*/

                startActivity(new Intent(FishingPlaceDetailsActivity.this, FishingFriendsUploadActivity.class)
                        .putExtra("whereFrom", "1")
                        .putExtra("fishing_shop_id", fishingPlaceId));
                break;
            case R.id.place_details_ll_fish_get:
                //查看全部渔获

              /*  if (!TextUtils.isEmpty(SPUtils.getPreference(this,"userid"))) {
                }else {
                    startActivity(new Intent(this,LoginActivity.class));
                }*/
                startActivity(new Intent(FishingPlaceDetailsActivity.this, PostListActivity.class)
                        .putExtra("title", "钓友渔获")
                        .putExtra("isPublish", false)
                        .putExtra("isHomeFishGet", false)
                        .putExtra("f_id", fishingPlaceEntity.getF_gid())
                        .putExtra("topId", ID_GET_FISH));
                break;
            case R.id.place_details_ll_fish_around:
                //查看周边全部钓场
                break;
            case R.id.place_details_tv_error:
                //报错
                if (!TextUtils.isEmpty(SPUtils.getPreference(this, "userid"))) {
                    startActivity(new Intent(FishingPlaceDetailsActivity.this, ReportErrorsActivity.class)
                            .putExtra("whereFrom", "1")
                            .putExtra("fishing_shop_id", fishingPlaceId));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.place_details_tv_upload:
                //传图
                if (!TextUtils.isEmpty(SPUtils.getPreference(this, "userid"))) {
                    //相册
                    PermissionUtils.storage(FishingPlaceDetailsActivity.this, new PermissionUtils.OnPermissionResult() {
                        @Override
                        public void onGranted() {
                            album();
                        }
                    });
//                    showPopPicture();
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }

                break;
            case R.id.place_details_tv_attention:
                //关注
                if (!TextUtils.isEmpty(SPUtils.getPreference(this, "userid"))) {
                    okhttpIsAttention();
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }

                break;
            case R.id.place_details_ll_public:
                //发布渔获 改为点评
                if (!TextUtils.isEmpty(SPUtils.getPreference(this, "userid"))) {
//                    startActivity(new Intent(this, PublishGetActivity.class));
                    startActivity(new Intent(this, FishingPlaceCommentActivity.class).putExtra("fishingPlaceId", fishingPlaceId));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }

                break;

            case R.id.place_details_ll_fish_dp:
                //钓友点评
                startActivity(new Intent(FishingPlaceDetailsActivity.this, FishingFriendsDPActivity.class)
                        .putExtra("fishing_shop_id", fishingPlaceId));
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
        tvPhone.setText(placePhone);
        phoneDialog.findViewById(R.id.dialog_tv_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //呼叫拨打
                PermissionUtils.phone(FishingPlaceDetailsActivity.this, new PermissionUtils.OnPermissionResult() {
                    @Override
                    public void onGranted() {
                        if (!TextUtils.isEmpty(placePhone)) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + placePhone));
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
     * 详情接口
     */
    private void downLoadfishingPlace() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.ANGLING_ANG_INFO);
        commonOkhttp.putParams("f_gid", fishingPlaceId);
        commonOkhttp.putCallback(new MyGenericsCallback<FishingPlaceEntity>(this) {
            @Override
            public void onSuccess(FishingPlaceEntity data, int d) {
                super.onSuccess(data, d);
                pullToRefreshScrollView.onRefreshComplete();
                fishingPlaceEntity = data;

                loadData(data);
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                pullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                pullToRefreshScrollView.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 传图接口
     */
    private void okhttpSpreadImgs() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.ANGLING_UPLOAD_IMAGES);
        commonOkhttp.putParams("f_gid", fishingPlaceId);
        for (int i = 0; i < upLoadList.size(); i++) {
            File file = new File(upLoadList.get(i).path);
            if (file.exists()) {
                commonOkhttp.putFile((i + 1) + "", file.getName(), file);
            }
        }
        commonOkhttp.putCallback(new MyGenericsCallback<SuccessBean>(this) {
            @Override
            public void onSuccess(SuccessBean data, int d) {
                super.onSuccess(data, d);
                if (data.getState() == 200) {
                    downLoadfishingPlace();
                }
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                ToastUtils.getInstance(FishingPlaceDetailsActivity.this).showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 关注接口
     */
    private void okhttpIsAttention() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.ANGLING_FOLLOW_FISH);
        commonOkhttp.putParams("f_gid", fishingPlaceId);
        commonOkhttp.putCallback(new MyGenericsCallback<SuccessBean>(this) {
            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                try {
                    ToastUtils.getInstance(FishingPlaceDetailsActivity.this).showMessage(message.toString());
                    if (TextUtils.equals(message, "取消关注成功！")) {
                        tvAttention.setCompoundDrawablesWithIntrinsicBounds(null, FishingPlaceDetailsActivity.this.getResources().getDrawable(R.mipmap.tab_fans_unselected), null, null);
                        tvAttention.setTextColor(FishingPlaceDetailsActivity.this.getResources().getColor(R.color.c_333333));
                        tvAttention.setText("关注");
                    } else if (TextUtils.equals(message, "关注成功！")) {
                        tvAttention.setCompoundDrawablesWithIntrinsicBounds(null, FishingPlaceDetailsActivity.this.getResources().getDrawable(R.mipmap.tab_fans_selected), null, null);
                        tvAttention.setTextColor(FishingPlaceDetailsActivity.this.getResources().getColor(R.color.c_269ceb));
                        tvAttention.setText("已关注");
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 加载数据
     */
    private void loadData(final FishingPlaceEntity fishingPlaceEntity) {

        final ScrollView scrollView = pullToRefreshScrollView.getRefreshableView();
        //滚动到顶部
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_UP);
            }
        });

        if (fishingPlaceEntity.getG_image() != null)
            GlideUtils.loadImageView(this, IMAGE_URL + fishingPlaceEntity.getG_image(), placeDetailsIv);
        listPic.clear();
        listPic.add(new ImageItem("", HttpUrl.IMAGE_URL + fishingPlaceEntity.getG_image()));
        if (fishingPlaceEntity.getG_name() != null)
            tvName.setText(fishingPlaceEntity.getG_name());//钓场名称
        if (fishingPlaceEntity.getName() != null)
            tvPlace.setText(fishingPlaceEntity.getName());//钓场类型
        if (fishingPlaceEntity.getPay_type() != null)
            if ("2".equals(fishingPlaceEntity.getPay_type())) {
                tvCost.setText("免费");//花费
            } else {
                if (fishingPlaceEntity.getPay_content() != null)
                    tvCost.setText(fishingPlaceEntity.getPay_content());
            }
        if (TextUtils.isEmpty(fishingPlaceEntity.getFishseat())) {
            tvSiteNumber.setText("0");
        } else
            tvSiteNumber.setText(fishingPlaceEntity.getFishseat());//钓位
        if (TextUtils.isEmpty(fishingPlaceEntity.getCarparks())) {
            tvParkingNumber.setText("0");
        } else
            tvParkingNumber.setText(fishingPlaceEntity.getCarparks());//停车位
        String position = "";
        String grounds_address = "";
        if (fishingPlaceEntity.getPosition() != null)
            position = fishingPlaceEntity.getPosition();
        if (fishingPlaceEntity.getGrounds_address() != null)
            grounds_address = fishingPlaceEntity.getGrounds_address();
        tvAddress.setText(position + grounds_address);//详细地址
        if (fishingPlaceEntity.getGround_phone() != null)
            tvPhone.setText(fishingPlaceEntity.getGround_phone());//电话
        placePhone = fishingPlaceEntity.getGround_phone();
        if (fishingPlaceEntity.getFish_type() != null)
            placeDetailsTvFishing.setText(fishingPlaceEntity.getFish_type());//鱼种
        if (fishingPlaceEntity.getG_intro() != null)
            tvBriefIntroduction.setText(fishingPlaceEntity.getG_intro());//简介

        if (!TextUtils.isEmpty(String.valueOf(fishingPlaceEntity.getIs_follow()))) {
            // 是否关注   1 已关注  0 未关注
            if (fishingPlaceEntity.getIs_follow() == 1) {
                tvAttention.setCompoundDrawablesWithIntrinsicBounds(null, FishingPlaceDetailsActivity.this.getResources().getDrawable(R.mipmap.tab_fans_selected), null, null);
                tvAttention.setTextColor(FishingPlaceDetailsActivity.this.getResources().getColor(R.color.c_269ceb));
                tvAttention.setText("已关注");
            } else {
                tvAttention.setCompoundDrawablesWithIntrinsicBounds(null, FishingPlaceDetailsActivity.this.getResources().getDrawable(R.mipmap.tab_fans_unselected), null, null);
                tvAttention.setTextColor(FishingPlaceDetailsActivity.this.getResources().getColor(R.color.c_333333));
                tvAttention.setText("关注");
            }
        }
        if (!TextUtils.isEmpty(fishingPlaceEntity.getLatitude())) {
            lat = fishingPlaceEntity.getLatitude();
        }
        if (!TextUtils.isEmpty(fishingPlaceEntity.getLatitude())) {
            lon = fishingPlaceEntity.getLongitude();
        }
        // 钓友上图
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        if (fishingPlaceEntity.getImages() != null) {
            photoList.clear();
            photoList.addAll(fishingPlaceEntity.getImages());
            mAdapter.notifyDataSetChanged();
            for (int i = 0; i < fishingPlaceEntity.getImages().size(); i++) {
                imageItems.add(new ImageItem("", HttpUrl.IMAGE_URL + fishingPlaceEntity.getImages().get(i).getImg_url()));
            }
            System.out.println("=====图片集合大小=====" + imageItems.size());
            gvPhotoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MyPicMethodUtil.preview(FishingPlaceDetailsActivity.this, imageItems, position);
                   /* Intent intentPreview = new Intent(FishingPlaceDetailsActivity.this, ImagePreviewDelActivity.class);
                    intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageItems);
                    intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                    intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                    intentPreview.putExtra("edit", false);
                    startActivityForResult(intentPreview, PublicStaticData.REQUEST_CODE_PREVIEW);*/
                }
            });
        }

        if (fishingPlaceEntity.getFish_list() != null) {
            fishGetList.clear();
            fishGetList.addAll(fishingPlaceEntity.getFish_forum());
            mFishGetAdapter.notifyDataSetChanged();
        }

        if (fishingPlaceEntity.getFish_list() != null) {
            aroundList.clear();
            aroundList.addAll(fishingPlaceEntity.getFish_list());
            mFishingPlaceAdapter.notifyDataSetChanged();
        }
        final ArrayList<ImageItem> dpImageItem = new ArrayList<>();
        if (fishingPlaceEntity.getFish_dianping() != null) {
            fishDPList.clear();
            fishDPList.addAll(fishingPlaceEntity.getFish_dianping());
            dpAdapter.notifyDataSetChanged();
//            for (int i = 0; i < fishingPlaceEntity.getFish_dianping().size(); i++) {
//                for (int j = 0; j < fishingPlaceEntity.getFish_dianping().get(i).getContent().size(); j++) {
//                    dpImageItem.add(new ImageItem("", HttpUrl.IMAGE_URL + fishingPlaceEntity.getFish_dianping().get(i).getContent().get(j).getStr_imgs()));
//                }
//            }
//            dpAdapter.setOnItemClickListener(new FishingFriendDPAdapter.onItemClickListener() {
//                @Override
//                public void onItemClick(int position) {
//                    MyPicMethodUtil.preview(FishingPlaceDetailsActivity.this, dpImageItem, position);
//                }
//            });
        }
    }
}
