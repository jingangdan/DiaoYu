package com.project.dyuapp.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.imageloader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.EmptyResultBean;
import com.project.dyuapp.entity.MyInfoBean;
import com.project.dyuapp.myutils.CompressImageUtil;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PopSelectAddress;
import com.project.dyuapp.myutils.RegexUtils;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myviews.CustomDialog;
import com.project.dyuapp.myviews.CycleWheelView;
import com.project.dyuapp.runtimepermissions.PermissionUtils;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.project.dyuapp.myutils.PublicStaticData.REQUEST_CODE_SELECT;

/**
 * @author shipeiyun
 * @description 我的-个人信息
 * @created at 2017/11/27 0027
 */
public class PersonalInfoActivity extends MyBaseActivity {

    @Bind(R.id.personal_info_iv_head)
    PorterShapeImageView personalInfoIvHead;//头像
    @Bind(R.id.personal_info_tv_nickname)
    EditText personalInfoTvNickname;//昵称
    @Bind(R.id.personal_info_tv_sex)
    TextView personalInfoTvSex;//性别
    @Bind(R.id.personal_info_tv_name)
    EditText personalInfoTvName;//真实姓名
    @Bind(R.id.personal_info_tv_area)
    TextView personalInfoTvArea;//所在地区
    @Bind(R.id.personal_info_tv_qq)
    EditText personalInfoTvQq;//QQ
    @Bind(R.id.personal_info_tv_wechat)
    EditText personalInfoTvWechat;//微信
    @Bind(R.id.personal_info_tv_alipay)
    EditText personalInfoTvAlipay;//支付宝

    private CustomDialog selDialog;
    private ArrayList<ImageItem> imageBefore = new ArrayList<>();
    private String sex="";//性别
    private String provinceid,cityid = "";//省、市id
    private File headFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("个人信息");
        getTvRight().setText("提交");
        getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(personalInfoTvNickname.getText().toString())&&!RegexUtils.regexNickname(personalInfoTvNickname.getText().toString())){
                    showMessage("昵称由中文、英文、数字、下划线构成");
                }else {
                    commitData();
                }

            }
        });
        initImagePicker();
        getData();
    }

    /**
     * 获取编辑的信息
     */
    private void getData(){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.editData);
        commonOkhttp.putCallback(new MyGenericsCallback<MyInfoBean>(this){
            @Override
            public void onSuccess(MyInfoBean data, int d) {
                super.onSuccess(data, d);
                //头像
                Glide.with(PersonalInfoActivity.this).load(HttpUrl.IMAGE_URL+data.getMember_list_headpic()).placeholder(R.mipmap.mine_edit_head)
                        .error(R.mipmap.mine_edit_head).into(personalInfoIvHead);
                imageBefore.add(new ImageItem());
                //昵称
                personalInfoTvNickname.setText(data.getMember_list_nickname());
                //性别 1=男  2=女 3保密
                sex = data.getMember_list_sex();
                if(data.getMember_list_sex().equals("1")){
                    personalInfoTvSex.setText("男");
                }else if(data.getMember_list_sex().equals("2")){
                    personalInfoTvSex.setText("女");
                }else {
                    personalInfoTvSex.setText("保密");
                }
                //真实姓名
                personalInfoTvName.setText(data.getMember_list_realname());
                //所在地区
                provinceid = data.getProvinceid();
                cityid = data.getCityid();
                //countyid = data.getCountyid();
                personalInfoTvArea.setText(data.getAddress());
                //qq
                personalInfoTvQq.setText(data.getQq());
                //微信
                personalInfoTvWechat.setText(data.getWechat());
                //支付宝
                personalInfoTvAlipay.setText(data.getAlipay());
            }
        });
        commonOkhttp.Execute();
    }

    @OnClick({R.id.personal_info_ll_head,  R.id.personal_info_tv_sex, R.id.personal_info_tv_area})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_info_ll_head:
                //头像
                showPopPicture();
                break;
            case R.id.personal_info_tv_sex:
                //性别
                showSelectSex();
                break;
            case R.id.personal_info_tv_area:
                //所在地区
                selectAddress();
                break;
        }
    }


    /**
     * 提交编辑的信息
     */
    private void commitData(){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.editDatat);
        commonOkhttp.putParams("member_list_nickname",personalInfoTvNickname.getText().toString());
        commonOkhttp.putParams("member_list_sex",sex);
        commonOkhttp.putParams("member_list_realname",personalInfoTvName.getText().toString());
        commonOkhttp.putParams("provinceid",provinceid);
        commonOkhttp.putParams("cityid",cityid);
       // commonOkhttp.putParams("countyid",countyid);
        commonOkhttp.putParams("qq",personalInfoTvQq.getText().toString());
        commonOkhttp.putParams("alipay",personalInfoTvAlipay.getText().toString());
        commonOkhttp.putParams("wechat",personalInfoTvWechat.getText().toString());
        if(!TextUtils.isEmpty(imageBefore.get(0).path)){
            headFile = new File(imageBefore.get(0).path);
            commonOkhttp.putFile("member_list_headpic",headFile.getAbsolutePath(),headFile);
        }
        commonOkhttp.putCallback(new MyGenericsCallback<EmptyResultBean>(this){
            @Override
            public void onSuccess(EmptyResultBean data, int d) {
                super.onSuccess(data, d);
                ScreenManager.getInstance().removeActivity(PersonalInfoActivity.this);
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }


    /**
     * 选择地区弹窗
     */
    public void selectAddress() {
        PopSelectAddress popSelectAddress = new PopSelectAddress(PersonalInfoActivity.this, getWindow(),"","","");
        popSelectAddress.showHttpAddress(); // 网络下载地址数据
        //popSelectAddress.showLocalAddress(); // 加载本地地址数据
        popSelectAddress.setCallBack(new PopSelectAddress.CallBackAddress() {
            @Override
            public void onClickOk(String selProvince, String selCity, String selZone, String selProvinceCode, String selCityCode, String selZoneCode) {
                personalInfoTvArea.setText(selProvince+selCity);
                provinceid = selProvinceCode;
                cityid = selCityCode;
               // countyid = selZoneCode;
            }
        });
    }

    //弹框选择相册相机
    private void showPopPicture() {
        selDialog = new CustomDialog(this, R.layout.pop_sel_pic_way, R.style.CustomDialogTheme,true);
        selDialog.setCanceledOnTouchOutside(true);
        selDialog.show();
        selDialog.findViewById(R.id.tv_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册
                PermissionUtils.storage(PersonalInfoActivity.this, new PermissionUtils.OnPermissionResult() {
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
                PermissionUtils.camera(PersonalInfoActivity.this, new PermissionUtils.OnPermissionResult() {
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

    /**
     * 初始化图片选择器
     */
    private void initImagePicker() {
       /* int width = 400;
        int height = 400;*/
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                            //允许裁剪（单选才有效）
//        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setMultiMode(false);                      //多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
/*        imagePicker.setFocusWidth(width);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(height);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(width);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(height);                         //保存文件的高度。单位像素*/
    }

    /**
     * 相机
     */
    private void camera() {
        Intent intent = new Intent(PersonalInfoActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    /**
     * 相册
     */
    private void album() {
        Intent intent = new Intent(PersonalInfoActivity.this, ImageGridActivity.class);
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
                    imageBefore = images;
                    File file = CompressImageUtil.radioImage(new File(imageBefore.get(0).path));
                    GlideUtils.loadImageViewHead(PersonalInfoActivity.this,file.getAbsolutePath(),personalInfoIvHead);
                }
            }
        }
    }

    /**
     * 性别选择框
     */
    private void showSelectSex() {
        backgroundAlpha(0.8f);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_sex, null);
        final PopupWindow popSex = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        TextView tvOk = (TextView) view.findViewById(R.id.pop_sex_ok);

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSex.dismiss();
            }
        });
        CycleWheelView cwvSex = (CycleWheelView) view.findViewById(R.id.pop_sex_cwv_sex);
        ArrayList<String> arraySex = new ArrayList<>();
        arraySex.add("保密");
        arraySex.add("男");
        arraySex.add("女");
        cwvSex.setLabels(arraySex);

        int index = arraySex.indexOf(personalInfoTvSex.getText().toString());
        if (index != -1) {
            cwvSex.setSelection(index);
        } else {
            cwvSex.setSelection(0);
        }
        cwvSex.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, String label) {
                //选中
                personalInfoTvSex.setText(label);
                if(label.equals("男")){
                    sex ="1";
                }else if(label.equals("女")){
                    sex = "2";
                }else {
                    sex = "3";
                }
            }
        });

        popSex.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        popSex.setOutsideTouchable(true);

        popSex.setBackgroundDrawable(new BitmapDrawable());
        popSex.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    //设置屏幕透明度
    public void backgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

}
