package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.ExchangeInfoEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.ScreenManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.project.dyuapp.myutils.PublicStaticData.EXCHANGE_INFO;
import static com.project.dyuapp.myutils.PublicStaticData.SELECT_ADDRESS;

/**
 * @author shipeiyun
 * @description 个人中心-金币商城-商品兑换-兑换信息
 * @created at 2017/12/6 0006
 */
public class ExchangeInfoActivity extends MyBaseActivity {

    @Bind(R.id.exchange_info_ll_empty)
    LinearLayout exchangeInfoLlEmpty;//没有收货地址
    @Bind(R.id.exchange_info_tv_name)
    TextView exchangeInfoTvName;//收货人
    @Bind(R.id.exchange_info_tv_phone)
    TextView exchangeInfoTvPhone;//收货电话
    @Bind(R.id.exchange_info_tv_site)
    TextView exchangeInfoTvSite;//收货地址
    @Bind(R.id.exchange_info_rl_address)
    RelativeLayout exchangeInfoRlAddress;//有收货地址
    @Bind(R.id.exchange_into_tv_count)
    TextView exchangeInfoTvCount;//数量
    @Bind(R.id.exchange_into_edt)
    EditText exchangeInfoEdt;//留言
    @Bind(R.id.exchange_info_tv_num)
    TextView exchangeInfoTvNum;//共计商品数量
    @Bind(R.id.exchange_info_tv_gold)
    TextView exchangeInfoTvGold;//金币

    private int number = 1;//数量
    private String goods_id = "";//商品id
    private int money;//金币
    private String addressid = "";//地址id
    private String goodsnum = "";//数量
    private String content = "";//留言
    private String myMoney="";//我的金币

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_exchange_info);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("兑换信息");
        goods_id = getIntent().getStringExtra("goods_id");
        myMoney = getIntent().getStringExtra("myMoney");
        okhttpGoodsExchanges();
    }

    /**
     * 个人中心-金币商城-兑换商品
     */
    private void okhttpGoodsExchanges() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.goodsExchanges);
        commonOkhttp.putParams("goods_id", goods_id);
        commonOkhttp.putCallback(new MyGenericsCallback<ExchangeInfoEntity>(ExchangeInfoActivity.this) {
            @Override
            public void onSuccess(ExchangeInfoEntity data, int d) {
                super.onSuccess(data, d);
                money = data.getTotal_money();//金币
                exchangeInfoTvGold.setText(money+"");
                //为空即新增地址  不为空则显示收货信息
                if (data.getAddress()!=null){
                    exchangeInfoLlEmpty.setVisibility(View.GONE);
                    exchangeInfoRlAddress.setVisibility(View.VISIBLE);
                    addressid = data.getAddress().getAddressid();//地址id
                    String name = data.getAddress().getUsername();//收货人
                    if (!TextUtils.isEmpty(name)){
                        exchangeInfoTvName.setText(name);
                    }
                    String phone = data.getAddress().getPhone();//收货电话
                    if (!TextUtils.isEmpty(phone)){
                        exchangeInfoTvPhone.setText(phone);
                    }
                    String province = data.getAddress().getProvince();//省名称
                    String city = data.getAddress().getCity();//市名称
                    String country = data.getAddress().getCounty();//县区名称
                    String detail = data.getAddress().getAddress();//详细地址
                    if (!TextUtils.isEmpty(province) || !TextUtils.isEmpty(city)||
                            !TextUtils.isEmpty(country) || !TextUtils.isEmpty(detail)){
                        exchangeInfoTvSite.setText(province+" "+city+" "+country+" "+detail);
                    }
                } else {
                    exchangeInfoLlEmpty.setVisibility(View.VISIBLE);
                    exchangeInfoRlAddress.setVisibility(View.GONE);
                }
            }
        });
        commonOkhttp.Execute();
    }

    @OnClick({R.id.exchange_info_ll_empty, R.id.exchange_info_rl_address, R.id.exchange_into_tv_subtract,
            R.id.exchange_into_tv_plus, R.id.exchange_info_tv_convert})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exchange_info_ll_empty:
                //没有收货地址
                startActivityForResult(new Intent(ExchangeInfoActivity.this, SelectAddressActivity.class), EXCHANGE_INFO);
                break;
            case R.id.exchange_info_rl_address:
                //有收货地址
                startActivityForResult(new Intent(ExchangeInfoActivity.this, SelectAddressActivity.class), EXCHANGE_INFO);
                break;
            case R.id.exchange_into_tv_subtract:
                //减
                if (number == 1) {
                    return;
                }
                number--;
                exchangeInfoTvCount.setText(number + "");
                exchangeInfoTvNum.setText(number + "");
                exchangeInfoTvGold.setText((number*money)+"");
                break;
            case R.id.exchange_into_tv_plus:
                //加
                number++;
                exchangeInfoTvCount.setText(number + "");
                exchangeInfoTvNum.setText(number + "");
                exchangeInfoTvGold.setText((number*money)+"");
                break;
            case R.id.exchange_info_tv_convert:
                //兑换按钮
                goodsnum = exchangeInfoTvCount.getText().toString();
                content = exchangeInfoEdt.getText().toString();
                if (Integer.parseInt(myMoney)<(number*money)){
                    showMessage("您的金币不足");
                    return;
                }
                if (TextUtils.isEmpty(addressid)){
                    showMessage("请选择收货地址");
                    return;
                }
                okhttpGoodsExchangeAction();
                break;
        }
    }

    /**
     * 个人中心-金币商城-兑换商品动作
     */
    private void okhttpGoodsExchangeAction(){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.goodsExchangeAction);
        commonOkhttp.putParams("goods_id",goods_id);
        commonOkhttp.putParams("goods_num",goodsnum);
        commonOkhttp.putParams("order_remarks",content);
        commonOkhttp.putParams("addressid",addressid);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.EmptytEntity>(ExchangeInfoActivity.this){
            @Override
            public void onSuccess(NetBean.EmptytEntity data, int d) {
                super.onSuccess(data, d);
                ScreenManager.getInstance().removeActivity(ExchangeInfoActivity.this);
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);

            }

            @Override
            public void onOther(JsonResult<NetBean.EmptytEntity> data, int d) {
                super.onOther(data, d);
                if (data.getCode() == 1){
                    showMessage(data.getMessage());
                }
            }
        });
        commonOkhttp.Execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EXCHANGE_INFO) {
            if (resultCode == SELECT_ADDRESS) {
                if (data != null) {
                    exchangeInfoLlEmpty.setVisibility(View.GONE);
                    exchangeInfoRlAddress.setVisibility(View.VISIBLE);
                    exchangeInfoTvName.setText(data.getStringExtra("name"));//收货人
                    exchangeInfoTvPhone.setText(data.getStringExtra("phone"));//收货电话
                    exchangeInfoTvSite.setText(data.getStringExtra("site"));//收货地址
                    addressid = data.getStringExtra("addressid");//地址ID
                }
            }
        }
    }
}
