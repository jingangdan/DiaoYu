package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.imageloader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.FishingShopEntity;
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

import static com.project.dyuapp.myutils.PublicStaticData.REQUEST_CODE_SELECT;


/**
 * @author 田亭亭
 * @description 添加渔具店Activity
 * @created at 2017/11/24 0024
 * @change
 */
public class AppendFishingShopActivity extends MyBaseActivity {

    @Bind(R.id.fishing_shop_ll_select)
    LinearLayout llSelect;
    @Bind(R.id.fishing_shop_img_select)
    ImageView imgSelect;
    @Bind(R.id.fishing_shop_edt_name)
    EditText edtName;//店铺名称
    @Bind(R.id.fishing_shop_tv_position)
    TextView tvPosition;//选择位置
    @Bind(R.id.fishing_shop_edt_address)
    EditText edtAddress;//详细地址
    @Bind(R.id.fishing_shop_tv_boss)
    CheckBox tvBoss;//渔具店老板
    @Bind(R.id.fishing_shop_tv_people)
    CheckBox tvPeople;//钓鱼人
    @Bind(R.id.fishing_shop_edt_phone)
    EditText edtPhone;//渔具店电话
    @Bind(R.id.fishing_shop_edt_brief_introduction)
    EditText edtBriefIntroduction;//简介;
    private String identityType = "0";
    private CustomDialog selDialog;
    private ArrayList<ImageItem> imageBefore;

    private FishingShopEntity fishingShopEntity = new FishingShopEntity();
    private String latitude = "";
    private String longitude = "";
    private String province = "";
    private String city = "";
    private String county = "";
    private String position = "";
    private String street_number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_append_fishing_shop);
        ButterKnife.bind(this);
        setTvTitle("添加渔具店");
        setIvBack();
        getTvRight().setText("提交");
        initImagePicker();
//        MyPicMethodUtil.initImagePicker();
//        if (TextUtils.equals(identityType, "0")) {
//            //钓场老板
//            tvBoss.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_select), null, null, null);
//            tvBoss.setTextColor(this.getResources().getColor(R.color.c_269ceb));
//            tvPeople.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_unselect), null, null, null);
//            tvPeople.setTextColor(this.getResources().getColor(R.color.c_333333));
//            fishingShopEntity.setShenfen("1");
//        } else {
//            //钓鱼人
//            tvPeople.setTextColor(this.getResources().getColor(R.color.c_269ceb));
//            tvPeople.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_select), null, null, null);
//            tvBoss.setTextColor(this.getResources().getColor(R.color.c_333333));
//            tvBoss.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_unselect), null, null, null);
//            fishingShopEntity.setShenfen("2");
//        }
        tvBoss.setChecked(true);
        tvPeople.setChecked(false);
        fishingShopEntity.setShenfen("1");
        if (tvBoss.isChecked()) {
            tvBoss.setChecked(true);
            tvPeople.setChecked(false);
            fishingShopEntity.setShenfen("1");
        } else if (tvPeople.isChecked()) {
            tvPeople.setChecked(true);
            tvBoss.setChecked(false);
            fishingShopEntity.setShenfen("2");
        } else {
            tvPeople.setChecked(false);
            tvBoss.setChecked(false);
            fishingShopEntity.setShenfen("");
        }
        tvBoss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fishingShopEntity.setShenfen("1");
                    tvBoss.setChecked(true);
                    tvPeople.setChecked(false);
                } else {
                    fishingShopEntity.setShenfen("");
                    tvBoss.setChecked(false);
                }

            }
        });
        tvPeople.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fishingShopEntity.setShenfen("2");
                    tvPeople.setChecked(true);
                    tvBoss.setChecked(false);
                } else {
                    fishingShopEntity.setShenfen("");
                    tvPeople.setChecked(false);
                }

            }
        });
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
                PermissionUtils.storage(AppendFishingShopActivity.this, new PermissionUtils.OnPermissionResult() {
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
                PermissionUtils.camera(AppendFishingShopActivity.this, new PermissionUtils.OnPermissionResult() {
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
//        int width = 720;
//        int height = 448;
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setMultiMode(false);                      //多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
/*        imagePicker.setFocusWidth(width);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(height);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(width);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(height);                         //保存文件的高度。单位像素*/
    }

    //相机
    private void camera() {
        Intent intent = new Intent(AppendFishingShopActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    //相册
    private void album() {
        Intent intent = new Intent(AppendFishingShopActivity.this, ImageGridActivity.class);
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
                    GlideUtils.loadImageViewNoPlaceHolder(AppendFishingShopActivity.this,file.getAbsolutePath(),imgSelect);
                    imageBefore = new ArrayList<>();
                    imageBefore.add(new ImageItem("",file.getAbsolutePath()));
                    llSelect.setVisibility(View.GONE);
                }
            }
        }
        if (requestCode == PublicStaticData.APPEND_SHOP) {
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

    @OnClick({R.id.fishing_shop_ll_select, R.id.fishing_shop_img_select
            , R.id.fishing_shop_tv_location, R.id.fishing_shop_ll_location
            , R.id.fishing_shop_tv_boss
            , R.id.fishing_shop_tv_people})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fishing_shop_ll_select:
                //选择图片
            case R.id.fishing_shop_img_select:
                //更换图片
                showPopPicture();
                break;
            case R.id.fishing_shop_tv_location:
            case R.id.fishing_shop_ll_location:
                //定位（选择位置）
                startActivityForResult(new Intent(AppendFishingShopActivity.this, LocationMapActivity.class), PublicStaticData.APPEND_SHOP);
                break;
//            case R.id.fishing_shop_tv_boss:
//                //渔具店老板
//                identityType = "0";
//                tvBoss.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_select), null, null, null);
//                tvBoss.setTextColor(this.getResources().getColor(R.color.c_269ceb));
//                tvPeople.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_unselect), null, null, null);
//                tvPeople.setTextColor(this.getResources().getColor(R.color.c_333333));
//                fishingShopEntity.setShenfen("1");
//                break;
//            case R.id.fishing_shop_tv_people:
//                //钓鱼人
//                identityType = "1";
//                tvPeople.setTextColor(this.getResources().getColor(R.color.c_269ceb));
//                tvPeople.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_select), null, null, null);
//                tvBoss.setTextColor(this.getResources().getColor(R.color.c_333333));
//                tvBoss.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.check_button_unselect), null, null, null);
//                fishingShopEntity.setShenfen("2");
//                break;
        }
    }

    //点击提交
    private void onClickContent() {
        fishingShopEntity.setShop_name(edtName.getText().toString().trim());//店铺名称
        fishingShopEntity.setTv_osition(tvPosition.getText().toString().trim());//选择地址
        fishingShopEntity.setShop_address(edtAddress.getText().toString().trim());//详细地址
        fishingShopEntity.setShop_phone(edtPhone.getText().toString().trim());//店主电话
        fishingShopEntity.setShop_intro(edtBriefIntroduction.getText().toString().trim());//店铺简介

        if (imageBefore.size() == 0) {
            showMessage("请选择图片");
            return;
        }
        if (TextUtils.isEmpty(fishingShopEntity.getShop_name())) {
            showMessage("请输入店铺名称");
            return;
        }
        if (TextUtils.isEmpty(fishingShopEntity.getTv_osition())) {
            showMessage("请选择渔具店地址");
            return;
        }
//        }
//        if (TextUtils.isEmpty(fishingShopEntity.getShop_address())) {
//            showMessage("请输入详细地址");
//            return;
//        }
//        if (TextUtils.isEmpty(fishingShopEntity.getShop_phone())) {
//            showMessage("请输入店主电话");
//            return;
//        }
            if (!Utils.isPhoneNumberValid(fishingShopEntity.getShop_phone())) {
                showMessage("请输入正确的联系方式");
                return;
            }
//        if (TextUtils.isEmpty(fishingShopEntity.getShop_intro())) {
//            showMessage("请输入店铺简介");
//            return;
//        }
            appendFishShop();
        }

        /**
         * 提交数据
         */

    public void appendFishShop() {

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
        commonOkhttp.putUrl(HttpUrl.ANGLING_ADDFISHINGSHOP);
        commonOkhttp.putFile("image", fileHead.getName(), fileHead);//图片
        commonOkhttp.putParams("shop_name", fishingShopEntity.getShop_name());//渔具店名称
        commonOkhttp.putParams("shop_phone", fishingShopEntity.getShop_phone());//渔具店电话
        commonOkhttp.putParams("shop_intro", fishingShopEntity.getShop_intro());//渔具店简介
        commonOkhttp.putParams("shop_address", fishingShopEntity.getShop_address());//详细地址
        commonOkhttp.putParams("shenfen", fishingShopEntity.getShenfen());//发布者身份 1渔具店老板2钓鱼人

        commonOkhttp.putParams("province", province);//省
        commonOkhttp.putParams("city", city);//市
        commonOkhttp.putParams("county", county);//区县
        commonOkhttp.putParams("longitude", longitude);//经度
        commonOkhttp.putParams("latitude", latitude);//纬度
        commonOkhttp.putParams("position", street_number);//详细地址
//
//        commonOkhttp.putParams("province", "河北省");//省id
//        commonOkhttp.putParams("city", "石家庄市");//市id
//        commonOkhttp.putParams("county", "裕华区");//区县id
//        commonOkhttp.putParams("longitude", "114.52297");//经度
//        commonOkhttp.putParams("latitude", "38.021264");//纬度

        commonOkhttp.putCallback(new MyGenericsCallback<WeatherEntity>(AppendFishingShopActivity.this) {
            @Override
            public void onSuccess(WeatherEntity data, int d) {
                super.onSuccess(data, d);
                ScreenManager.getInstance().removeActivity(AppendFishingShopActivity.this);
                showMessage(getString(R.string.content_success));
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                if (code != 0)
                    showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }
}
