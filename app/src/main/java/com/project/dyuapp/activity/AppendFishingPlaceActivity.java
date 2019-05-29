package com.project.dyuapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.imageloader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.ContentFishingEntity;
import com.project.dyuapp.entity.WeatherEntity;
import com.project.dyuapp.myutils.CompressImageUtil;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myutils.Utils;
import com.project.dyuapp.myviews.CustomDialog;
import com.project.dyuapp.runtimepermissions.PermissionUtils;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.project.dyuapp.myutils.PublicStaticData.CODE_COST_TYPE;
import static com.project.dyuapp.myutils.PublicStaticData.CODE_FISH_TYPE;
import static com.project.dyuapp.myutils.PublicStaticData.CODE_PLACE_TYPE;
import static com.project.dyuapp.myutils.PublicStaticData.REQUEST_CODE_SELECT;

/**
 * @author 田亭亭
 * @description 添加钓场Activity
 * @created at 2017/11/24 0024
 * @change
 */
public class AppendFishingPlaceActivity extends MyBaseActivity {

    private AppendFishingPlaceActivity TAG = AppendFishingPlaceActivity.this;


    @Bind(R.id.fishing_place_ll_select)
    LinearLayout llSelect;//上传图片
    @Bind(R.id.fishing_place_img_select)
    ImageView imgSelect;//显示图片
    @Bind(R.id.fishing_place_edt_name)
    EditText edtName;//钓场名称
    @Bind(R.id.fishing_place_tv_position)
    TextView tvPosition;//钓场位置
    @Bind(R.id.fishing_place_edt_address)
    EditText edtAddress;//详细地址
    @Bind(R.id.fishing_place_tv_site_type)
    TextView tvSiteType;//钓场类型
    @Bind(R.id.fishing_place_tv_charge_type)
    TextView tvChargeType;//收费类型
    @Bind(R.id.fishing_place_edt_charge_describe)
    TextView edtChargeDescribe;//收费描述
    @Bind(R.id.fishing_place_tv_fingerling)
    TextView tvFingerling;//鱼种
    @Bind(R.id.fishing_place_edt_post_number)
    EditText edtPostNumber;//钓场数量
    @Bind(R.id.fishing_place_edt_parking_number)
    EditText edtParkingNumber;//停车场数量
    @Bind(R.id.fishing_place_tv_boss)
    TextView tvBoss;//钓场老板
    @Bind(R.id.fishing_place_tv_people)
    TextView tvPeople;//钓鱼人
    @Bind(R.id.fishing_place_edt_phone)
    EditText edtPhone;//钓场电话
    @Bind(R.id.fishing_place_edt_brief_introduction)
    EditText edtBriefIntroduction;//简介
    private String identityType = "0";
    private CustomDialog selDialog;
    private ArrayList<ImageItem> imageBefore;

    private ContentFishingEntity contentFishingEntity = new ContentFishingEntity();
    private String latitude = "";
    private String longitude = "";
    private String province = "";
    private String city = "";
    private String county = "";
    private String position = "";
    private String street_number = "";

    private String charge_type;
    private String charge_type_code, charge_type_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_append_fishing_place);
        ButterKnife.bind(this);
        initImagePicker();
//        MyPicMethodUtil.initImagePicker();
        setTvTitle("添加钓场");
        setIvBack();
        getTvRight().setText("提交");
        if (TextUtils.equals(identityType, "0")) {
            //钓场老板
            tvBoss.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_select), null, null, null);
            tvBoss.setTextColor(this.getResources().getColor(R.color.c_269ceb));
            tvPeople.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_unselect), null, null, null);
            tvPeople.setTextColor(this.getResources().getColor(R.color.c_333333));
            contentFishingEntity.setShenfen("1");
        } else {
            //钓鱼人
            tvPeople.setTextColor(this.getResources().getColor(R.color.c_269ceb));
            tvPeople.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_select), null, null, null);
            tvBoss.setTextColor(this.getResources().getColor(R.color.c_333333));
            tvBoss.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_unselect), null, null, null);
            contentFishingEntity.setShenfen("2");
        }
        //提交
        getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickContent();
            }
        });
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
                PermissionUtils.storage(AppendFishingPlaceActivity.this, new PermissionUtils.OnPermissionResult() {
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
                PermissionUtils.camera(AppendFishingPlaceActivity.this, new PermissionUtils.OnPermissionResult() {
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
    /*    int width = 720;
        int height = 448;*/
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setMultiMode(false);                      //多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
    /*    imagePicker.setFocusWidth(width);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(height);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(width);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(height);                         //保存文件的高度。单位像素*/
    }

    //相机
    private void camera() {
        Intent intent = new Intent(AppendFishingPlaceActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    //相册
    private void album() {
        Intent intent = new Intent(AppendFishingPlaceActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    File file = CompressImageUtil.radioImage(new File(images.get(0).path));
                    GlideUtils.loadImageViewNoPlaceHolder(AppendFishingPlaceActivity.this, file.getAbsolutePath(), imgSelect);
                    imageBefore = new ArrayList<>();
                    imageBefore.add(new ImageItem("", file.getAbsolutePath()));
                    llSelect.setVisibility(View.GONE);
                }
            }
        } else if (requestCode == CODE_PLACE_TYPE && resultCode == Activity.RESULT_OK) {
            //钓场类型
            tvSiteType.setText(data.getStringExtra("sel"));
            contentFishingEntity.setCat_id(data.getStringExtra("sel_id"));
        } else if (requestCode == CODE_COST_TYPE && resultCode == Activity.RESULT_OK) {
            //收费类型
//            String charge_type = data.getStringExtra("charge_type");
//            String charge_type_code = data.getStringExtra("charge_type_code");

            charge_type = data.getStringExtra("charge_type");
            charge_type_code = data.getStringExtra("charge_type_code");
            charge_type_edit = data.getStringExtra("charge_type_edit");

            contentFishingEntity.setPay_type(charge_type_code);
            contentFishingEntity.setPay_content(charge_type);

            Toast.makeText(this,"收费类型 = "+contentFishingEntity.getPay_type()+"   收费描述 = "+contentFishingEntity.getPay_content(),Toast.LENGTH_LONG).show();

            switch (charge_type_code) {
                case "1":
                    tvChargeType.setText("收费");
                    edtChargeDescribe.setText(charge_type + "/斤");
                    break;
                case "2":
                    tvChargeType.setText("免费");
                    edtChargeDescribe.setText("免费");
                    break;
                case "3":
                    tvChargeType.setText("收费");
                    edtChargeDescribe.setText(charge_type + "/天");
                    break;
                case "4":
                    tvChargeType.setText("收费");
                    edtChargeDescribe.setText(charge_type + "/每小时");
                    break;
                case "5":
                    tvChargeType.setText("收费");
                    edtChargeDescribe.setText(charge_type + "/每杆");
                    break;

            }
//
//            switch (charge_type_code) {
//                case "0":
//                    tvChargeType.setText("免费");
//                    edtChargeDescribe.setText("免费");
//                    break;
//                case "1":
//                    tvChargeType.setText("收费");
//                    edtChargeDescribe.setText(charge_type + "/斤");
//                    break;
//                case "2":
//                    tvChargeType.setText("收费");
//                    edtChargeDescribe.setText(charge_type + "/天");
//                    break;
//                case "3":
//                    tvChargeType.setText("收费");
//                    edtChargeDescribe.setText(charge_type + "元/每小时");
//                    break;
//                case "4":
//                    tvChargeType.setText("收费");
//                    edtChargeDescribe.setText(charge_type + "元/每杆");
//                    break;
//
//            }
        } else if (requestCode == CODE_FISH_TYPE && resultCode == Activity.RESULT_OK) {
            //选择鱼种
            tvFingerling.setText(data.getStringExtra("fingerling_name"));
            contentFishingEntity.setFishcats(data.getStringExtra("fingerling_name_id"));
        }
        if (requestCode == PublicStaticData.APPEND_PLACE) {
            if (resultCode == PublicStaticData.LOCATION_MAP) {
                if (data != null) {

                    latitude = data.getStringExtra("lat");
                    longitude = data.getStringExtra("lon");
                    province = data.getStringExtra("province");
                    city = data.getStringExtra("city");
                    county = data.getStringExtra("county");
                    position = data.getStringExtra("address");
                    street_number = data.getStringExtra("street_number");
                    tvPosition.setText(data.getStringExtra("address"));
                }
            }
        }
    }


    @OnClick({R.id.fishing_place_ll_select, R.id.fishing_place_img_select
            , R.id.fishing_place_tv_site_type_more, R.id.fishing_place_ll_site_type_more
            , R.id.fishing_place_tv_charge_type_more, R.id.fishing_place_ll_charge_type_more
            , R.id.fishing_place_tv_fingerling_more, R.id.fishing_place_ll_fingerling_more
            , R.id.fishing_place_tv_location, R.id.fishing_place_ll_position
            , R.id.fishing_place_tv_boss, R.id.fishing_place_tv_people})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fishing_place_ll_select:
            case R.id.fishing_place_img_select:
                showPopPicture();
                break;
            case R.id.fishing_place_tv_location:
            case R.id.fishing_place_ll_position:
                //定位
                if (Build.VERSION.SDK_INT >= 23) {
                    showContacts();
                } else {
                    setIntent();
                }

                break;
            case R.id.fishing_place_tv_site_type_more:
            case R.id.fishing_place_ll_site_type_more:
                //选择钓场类型
                startActivityForResult(new Intent(AppendFishingPlaceActivity.this, FishingPlaceSelectActivity.class)
                                .putExtra("title", "钓场类型")
                                .putExtra("selectList", tvSiteType.getText().toString().trim())
                        , CODE_PLACE_TYPE);
                break;
            case R.id.fishing_place_tv_charge_type_more:
            case R.id.fishing_place_ll_charge_type_more:
                //选择收费类型
//                startActivityForResult(new Intent(AppendFishingPlaceActivity.this, ChargeType2Activity.class)
//                                .putExtra("charge_type_code", contentFishingEntity.getPay_type())
//                                .putExtra("charge_type", contentFishingEntity.getPay_content())
//                        , CODE_COST_TYPE);

                if (TextUtils.isEmpty(charge_type) && TextUtils.isEmpty(charge_type_code) && TextUtils.isEmpty(charge_type_edit)) {
                    startActivityForResult(new Intent(AppendFishingPlaceActivity.this, ChargeType2Activity.class)
                                    .putExtra("charge_type_code", "5")
                                    .putExtra("charge_type", "")
                                    .putExtra("charge_type_edit", "2")
                            , CODE_COST_TYPE);
                } else {
                    startActivityForResult(new Intent(AppendFishingPlaceActivity.this, ChargeType2Activity.class)
                                    .putExtra("charge_type_code", charge_type_code)
                                    .putExtra("charge_type", charge_type)
                                    .putExtra("charge_type_edit", charge_type_edit)
                            , CODE_COST_TYPE);
                }

                break;
            case R.id.fishing_place_tv_fingerling_more:
            case R.id.fishing_place_ll_fingerling_more:
                //选择鱼种
                startActivityForResult(new Intent(AppendFishingPlaceActivity.this, FingerlingListActivity.class)
                                .putExtra("selectList", tvFingerling.getText().toString().trim())
                        , CODE_FISH_TYPE);
                break;
            case R.id.fishing_place_tv_boss:
                //钓场老板
                identityType = "0";
                tvBoss.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_select), null, null, null);
                tvBoss.setTextColor(this.getResources().getColor(R.color.c_269ceb));
                tvPeople.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_unselect), null, null, null);
                tvPeople.setTextColor(this.getResources().getColor(R.color.c_333333));
                contentFishingEntity.setShenfen("1");
                break;
            case R.id.fishing_place_tv_people:
                //钓鱼人
                identityType = "1";
                tvPeople.setTextColor(this.getResources().getColor(R.color.c_269ceb));
                tvPeople.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_select), null, null, null);
                tvBoss.setTextColor(this.getResources().getColor(R.color.c_333333));
                tvBoss.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_unselect), null, null, null);
                contentFishingEntity.setShenfen("2");
                break;
        }
    }

    //跳转定位页面
    public void setIntent() {
        startActivityForResult(new Intent(AppendFishingPlaceActivity.this, LocationMapActivity.class), PublicStaticData.APPEND_PLACE);
    }

    public void showContacts() {
        if (ActivityCompat.checkSelfPermission(TAG, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(TAG, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(TAG, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            showMessage("没有定位权限，请手动开启定位权限");
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(TAG,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE},
                    1);
        } else {
            setIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 1:
                if (grantResults.length >0&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    setIntent();
                } else {
                    // 没有获取到权限，做特殊处理
                    showMessage("获取位置权限失败，请手动开启");
                }
                break;
            default:
                break;
        }
    }

    //点击提交
    private void onClickContent() {
        contentFishingEntity.setG_name(edtName.getText().toString().trim());//钓场名称
        contentFishingEntity.setAddress(tvPosition.getText().toString().trim());//钓场地址
        contentFishingEntity.setGrounds_address(edtAddress.getText().toString().trim());//详细地址

        contentFishingEntity.setFishseat(edtPostNumber.getText().toString().trim());//钓位数量
        contentFishingEntity.setCarparks(edtParkingNumber.getText().toString().trim());//停车位数量

        contentFishingEntity.setGround_phone(edtPhone.getText().toString().trim());//钓场电话
        contentFishingEntity.setG_intro(edtBriefIntroduction.getText().toString().trim());//钓场简介

        if (imageBefore != null) {
            if (imageBefore.size() == 0) {
                showMessage("请选择图片");
                return;
            }
        } else {
            showMessage("请选择图片");
            return;
        }

        if (TextUtils.isEmpty(contentFishingEntity.getG_name())) {
            showMessage("请输入钓场名称");
            return;
        }
        if (TextUtils.isEmpty(contentFishingEntity.getAddress())) {
            showMessage("请选择钓场地址");
            return;
        }
        if (TextUtils.isEmpty(contentFishingEntity.getGrounds_address())) {
            showMessage("请输入详细地址");
            return;
        }
        if (TextUtils.isEmpty(tvSiteType.getText().toString().trim())) {
            showMessage("请选择钓场类型");
            return;
        }
        if (TextUtils.isEmpty(tvChargeType.getText().toString().trim())) {
            showMessage("请选择收费类型");
            return;
        }
        if (TextUtils.isEmpty(tvFingerling.getText().toString().trim())) {
            showMessage("请选择鱼种");
            return;
        }
//        if (TextUtils.isEmpty(contentFishingEntity.getGround_phone())) {
//            showMessage("请输入钓场电话");
//            return;
//        }
        if (!Utils.isPhoneNumberValid(contentFishingEntity.getGround_phone())) {
            showMessage("请输入正确的联系方式");
            return;
        }
//        if (TextUtils.isEmpty(contentFishingEntity.getG_intro())) {
//            showMessage("请输入钓场简介");
//            return;
//        }

        appendFish();
    }

    /**
     * 提交数据
     */
    public void appendFish() {

//        latitude = PublicStaticData.sharedPreferences.getString("latitude", "");
//        longitude = PublicStaticData.sharedPreferences.getString("longitude", "");
//        province = PublicStaticData.sharedPreferences.getString("province", "");
//        city = PublicStaticData.sharedPreferences.getString("city", "");
//        county = PublicStaticData.sharedPreferences.getString("county", "");
//        if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)
//                || TextUtils.isEmpty(province) || TextUtils.isEmpty(city) || TextUtils.isEmpty(county))
//            showMessage("定位信息异常");

        File fileHead = new File(imageBefore.get(0).path);
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.ANGLING_PUBLISH_ANG);
        commonOkhttp.putFile("icon", fileHead.getName(), fileHead);//图片
        commonOkhttp.putParams("shenfen", contentFishingEntity.getShenfen());//发布者身份
        commonOkhttp.putParams("g_name", contentFishingEntity.getG_name());//钓场名称
        commonOkhttp.putParams("cat_id", contentFishingEntity.getCat_id());//钓场类型id
        commonOkhttp.putParams("pay_type", contentFishingEntity.getPay_type());//收费类型
        commonOkhttp.putParams("pay_content", contentFishingEntity.getPay_content());//收费详情
        commonOkhttp.putParams("fishcats", contentFishingEntity.getFishcats());//鱼种类
        commonOkhttp.putParams("fishseat", contentFishingEntity.getFishseat());//钓位数量
        commonOkhttp.putParams("carparks", contentFishingEntity.getCarparks());//车位数量
        commonOkhttp.putParams("g_intro", contentFishingEntity.getG_intro());//钓场简介
        commonOkhttp.putParams("ground_phone", contentFishingEntity.getGround_phone());//钓场电话
        commonOkhttp.putParams("grounds_address", contentFishingEntity.getGrounds_address());//钓场具体位置

        commonOkhttp.putParams("province", province);//省
        commonOkhttp.putParams("city", city);//市
        commonOkhttp.putParams("county", county);//区县
        commonOkhttp.putParams("longitude", longitude);//经度
        commonOkhttp.putParams("latitude", latitude);//纬度

        commonOkhttp.putParams("position", street_number);//详细地址

//        commonOkhttp.putParams("province", "河北省");//省id
//        commonOkhttp.putParams("city", "石家庄市");//市id
//        commonOkhttp.putParams("county", "裕华区");//区县id
//        commonOkhttp.putParams("longitude", "114.52297");//经度
//        commonOkhttp.putParams("latitude", "38.021264");//纬度
        commonOkhttp.putCallback(new MyGenericsCallback<WeatherEntity>(AppendFishingPlaceActivity.this) {
            @Override
            public void onSuccess(WeatherEntity data, int d) {
                super.onSuccess(data, d);
                ScreenManager.getInstance().removeActivity(AppendFishingPlaceActivity.this);
                showMessage(getString(R.string.content_success));
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }
        });
        commonOkhttp.Execute();
    }
}

