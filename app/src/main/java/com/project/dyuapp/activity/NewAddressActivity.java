package com.project.dyuapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.entity.AddressInfoEntity;
import com.project.dyuapp.myutils.PopSelectAddress_3;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.StatusEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.RegexUtils;
import com.project.dyuapp.myutils.ScreenManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shipeiyun
 * @description 个人中心-服务地址-新地址
 * @created at 2017/11/28 0028
 */
public class NewAddressActivity extends MyBaseActivity {

    @Bind(R.id.new_address_edt_name)
    EditText newAddressEdtName;//联系人
    @Bind(R.id.new_address_edt_phone)
    EditText newAddressEdtPhone;//联系电话
    @Bind(R.id.new_address_tv_area)
    TextView newAddressTvArea;//收货地址
    @Bind(R.id.new_address_edt_detail)
    EditText newAddressEdtDetail;//详细地址

    private String name;//联系人
    private String phone;//联系电话
    private String area;//收货地址
    private String detailSite;//详细地址
    private String flag = "";//flag 0编辑  1新增
    private String addressid = "";//地址id
    private String provinceid = "";//省id
    private String cityid = "";//市id
    private String countyid = "";//区/县id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_new_address);
        ButterKnife.bind(this);
        setIvBack();
        flag = getIntent().getStringExtra("flag");
        if (flag.equals("0")) {
            addressid = getIntent().getStringExtra("addressid");
            setTvTitle("编辑地址");
            okhttpAddressInfo();
        } else {
            setTvTitle("新地址");
        }
    }

    @OnClick({R.id.new_address_tv_area, R.id.new_address_tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_address_tv_area:
                //收货地址
                if (isInputMethodOpened(this)){
                    hidenInputMethod(newAddressTvArea,this);
                }
                selectAddress();
                break;
            case R.id.new_address_tv_save:
                //保存
                name = newAddressEdtName.getText().toString();
                phone = newAddressEdtPhone.getText().toString();
                area = newAddressTvArea.getText().toString();
                detailSite = newAddressEdtDetail.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    showMessage("请输入姓名");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    showMessage("请输入电话");
                    return;
                }
                if (!RegexUtils.isMobilePhoneNumber(phone)) {
                    showMessage("请输入正确手机号码");
                    return;
                }
                if (TextUtils.isEmpty(area)) {
                    showMessage("请选择地区");
                    return;
                }
                if (TextUtils.isEmpty(detailSite)) {
                    showMessage("请输入详细地址");
                    return;
                }
                okhttpAddAddress();
                break;
        }
    }

    /**
     * 个人中心-服务地址（返回信息）
     */
    private void okhttpAddressInfo() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.addressInfo);
        commonOkhttp.putParams("addressid", addressid);
        commonOkhttp.putCallback(new MyGenericsCallback<AddressInfoEntity>(NewAddressActivity.this) {
            @Override
            public void onSuccess(AddressInfoEntity data, int d) {
                super.onSuccess(data, d);
                String name = data.getUsername();//联系人
                if (!TextUtils.isEmpty(name)) {
                    newAddressEdtName.setText(name);
                }
                String phone = data.getPhone();//联系电话
                if (!TextUtils.isEmpty(phone)) {
                    newAddressEdtPhone.setText(phone);
                }
                String area = data.getAddres();//收货地址
                if (!TextUtils.isEmpty(area)) {
                    newAddressTvArea.setText(area);
                }
                String detail = data.getAddress();//详细地址
                if (!TextUtils.isEmpty(detail)) {
                    newAddressEdtDetail.setText(detail);
                }
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 9个人中心-服务地址-新地址（编辑）
     */
    private void okhttpAddAddress() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.addAddress);
        commonOkhttp.putParams("addressid", addressid);
        commonOkhttp.putParams("username", name);
        commonOkhttp.putParams("phone", phone);
        commonOkhttp.putParams("provinceid", provinceid);
        commonOkhttp.putParams("cityid", cityid);
        commonOkhttp.putParams("countyid", countyid);
        commonOkhttp.putParams("address", detailSite);
        commonOkhttp.putCallback(new MyGenericsCallback<StatusEntity>(NewAddressActivity.this) {
            @Override
            public void onSuccess(StatusEntity data, int d) {
                super.onSuccess(data, d);
                ScreenManager.getInstance().removeActivity(NewAddressActivity.this);
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 选择地区弹窗
     */
    public void selectAddress() {
        //设置默认地址(本地读取)
        String province = "河北省";
        String city = "唐山市";
        String zone = "路南区";
        PopSelectAddress_3 popSelectAddress = new PopSelectAddress_3(NewAddressActivity.this, getWindow(), province, city, zone);
        popSelectAddress.showHttpAddress(); // 网络下载地址数据
//        popSelectAddress.showLocalAddress(); // 加载本地地址数据
        popSelectAddress.setCallBack(new PopSelectAddress_3.CallBackAddress() {
            @Override
            public void onClickOk(String selProvince, String selCity, String selZone) {
                newAddressTvArea.setText(selProvince + " " + selCity + " " + selZone);
            }

            @Override
            public void onClickCode(String selProvinceCode, String selCityCode, String selZoneCode) {
//                Toast.makeText(NewAddressActivity.this, selProvinceCode+"-"+selCityCode+"-"+selZoneCode, Toast.LENGTH_SHORT).show();
                provinceid = selProvinceCode;
                cityid = selCityCode;
                countyid = selZoneCode;
            }
        });
    }

    // 显示输入法
    public static void showInputMethod(View view,Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        //同时再使用该方法之前，view需要获得焦点，可以通过requestFocus()方法来设定。
        view.requestFocus();
        inputMethodManager.showSoftInput(view, inputMethodManager.SHOW_FORCED);
    }
    //隐藏输入法
    public static void hidenInputMethod(View view,Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
    //判断输入法是否已经打开
    public static boolean isInputMethodOpened(Context context){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManager.isActive();
    }

}
