package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.project.dyuapp.R;
import com.project.dyuapp.activity.ChargeTypeActivity;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.ChargeTypeCostBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/11/29 0029.
 *
 * @description 收费类型花费适配器
 * @change
 */


public class ChargeTypeCostAdapter extends MyCommonAdapter<ChargeTypeCostBean> {


    @Bind(R.id.item_charge_type_chb_cost)
    CheckBox chbCost;

    @Bind(R.id.item_charge_type_edt_cost)
    EditText itemChargeTypeEdtCost;
    @Bind(R.id.item_charge_type_rl_cost)
    RelativeLayout itemChargetypeRlcost;
    private OnItemClickListener onItemClickListener;

    private int charge_type = 0;// 1.斤塘 2.天塘
    ChargeTypeActivity chargeTypeActivity;

    public EditText custom_jintang;
    public EditText custom_tiantang;

    public String charge_type_codesj;
    public String charge_types;

    int t1 = 0;
    int t2 = 0;

    private EditText edt;//记录当前输入框

    public interface OnItemClickListener {
        void onCustomClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ChargeTypeCostAdapter(List<ChargeTypeCostBean> list, Context mContext, int resid, int i) {
        super(list, mContext, resid);
        charge_type = i;
    }

    public ChargeTypeCostAdapter(List<ChargeTypeCostBean> list, Context mContext, int resid, int i, String charge_type_codes, String charge_types) {
        super(list, mContext, resid);
        charge_type = i;
        this.charge_type_codesj = charge_type_codes;
        this.charge_types = charge_types;
        //创建适配器时记录当前自定义数值
        if ("1".equals(charge_type_codes)) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getName().equals(charge_types)) {
                    t1++;
                    list.get(j).setSelect(true);
                } else {
                    list.get(j).setSelect(false);
                }
            }
            if (t1 == 0) {
                list.get(list.size() - 1).setFocus(true);
                list.get(list.size() - 1).setShowBraw(true);
            }
        } else if ("3".equals(charge_type_codes)) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getName().equals(charge_types)) {
                    t2++;
                    list.get(j).setSelect(true);
                    list.get(j).setFocus(true);
                } else {
                    list.get(j).setSelect(false);
                }
            }
            if (t2 == 0) {
                list.get(list.size() - 1).setFocus(true);
                list.get(list.size() - 1).setShowBraw(true);
            }
        }
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, final int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        chargeTypeActivity = (ChargeTypeActivity) mContext;
        if ("自定义".equals(list.get(position).getName())) {
            itemChargeTypeEdtCost.setVisibility(View.VISIBLE);
            itemChargeTypeEdtCost.setText("");
            chbCost.setText("");
            chbCost.setVisibility(View.GONE);
            if (1 == charge_type) {
                custom_jintang = itemChargeTypeEdtCost;
            } else if (2 == charge_type) {
                custom_tiantang = itemChargeTypeEdtCost;
            }
            //判断显示自定义数值
            if (!TextUtils.isEmpty(charge_types) && list.get(position).isFocus()) {
                int jin_int = 0;
                int tian_int = 0;
                int time_int = 0;
                int gan_int = 0;
                for (int i = 0; i < chargeTypeActivity.jintangList.size(); i++) {
                    if (chargeTypeActivity.jintangList.get(i).isSelect())
                        jin_int++;
                }
                for (int i = 0; i < chargeTypeActivity.tiantangList.size(); i++) {
                    if (chargeTypeActivity.tiantangList.get(i).isSelect())
                        tian_int++;
                }

                if (jin_int == 0 && tian_int == 0 && time_int == 0 && gan_int == 0)
                    if ("1".equals(charge_type_codesj)) {
                        if (t1 == 0) {
                            itemChargeTypeEdtCost.setText(charge_types.substring(0, charge_types.length() - 1));
                        }
                    } else if ("3".equals(charge_type_codesj)) {
                        if (t2 == 0) {
                            itemChargeTypeEdtCost.setText(charge_types.substring(0, charge_types.length() - 1));
                        }
                    }
            }
            //显示输入框背景颜色
            if (list.get(position).isShowBraw()) {
                itemChargetypeRlcost.setBackgroundResource(R.drawable.bg_click_btn_shallow_blue);
                itemChargeTypeEdtCost.setTextColor(mContext.getResources().getColor(R.color.share_view));
                itemChargeTypeEdtCost.setHintTextColor(mContext.getResources().getColor(R.color.share_view));
            } else {
                itemChargetypeRlcost.setBackgroundResource(R.drawable.bg_click_btn_gray);
                itemChargeTypeEdtCost.setTextColor(mContext.getResources().getColor(R.color.share_text));
                itemChargeTypeEdtCost.setHintTextColor(mContext.getResources().getColor(R.color.c_666666));
            }
        } else {
            chbCost.setText(list.get(position).getName());
            chbCost.setVisibility(View.VISIBLE);
            itemChargeTypeEdtCost.setVisibility(View.GONE);
        }
        edt = itemChargeTypeEdtCost;
        itemChargeTypeEdtCost.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //获得焦点
                if (hasFocus) {
                    chargeTypeActivity.clearSelect();
                    //清空选中状态
                    chargeTypeActivity.clearSelectOne(charge_type == 1 ? 2 : 1);
                    list.get(list.size() - 1).setShowBraw(true);//设置背景颜色
                    setRlBackGround(charge_type);
                    //将光标移至文字末尾
                    itemChargeTypeEdtCost.setSelection(itemChargeTypeEdtCost.getText().toString().length());
                } else {
                    list.get(list.size() - 1).setShowBraw(false);//设置背景颜色
                    //输入框置空
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            itemChargeTypeEdtCost.setText("");
                        }
                    }).start();
                    chargeTypeActivity.mJinAdapter.notifyDataSetChanged();
                    chargeTypeActivity.mTianAdapter.notifyDataSetChanged();
                }
                notifyDataSetChanged();
            }
        });

        //当输入框获取焦点时弹出输入法&清空其他选项选中状态
        itemChargeTypeEdtCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使输入框获得焦点并弹出输入法
                chargeTypeActivity.clearAdapterEdtFocusable(charge_type);
                InputMethodManager inputManager = (InputMethodManager) itemChargeTypeEdtCost.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(itemChargeTypeEdtCost, 0);
            }
        });

        if (list.get(position).isSelect()) {//状态选中
            chbCost.setChecked(true);
        } else {
            chbCost.setChecked(false);
        }

        if (onItemClickListener != null) {
            chbCost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onCustomClick(chbCost, position);
                }
            });
        }
        if (position == list.size() - 1) {
            chbCost.setTextColor(mContext.getResources().getColor(R.color.c_999999));
        }
    }

    /**
     * 设置当前自定义数字是否展示
     *
     * @param charge
     */
    public void setRlBackGround(int charge) {
        if (charge == 1) {
            chargeTypeActivity.jintangList.get(chargeTypeActivity.jintangList.size() - 1).setFocus(true);
            chargeTypeActivity.tiantangList.get(chargeTypeActivity.tiantangList.size() - 1).setFocus(false);
        } else if (charge == 2) {
            chargeTypeActivity.tiantangList.get(chargeTypeActivity.tiantangList.size() - 1).setFocus(true);
            chargeTypeActivity.jintangList.get(chargeTypeActivity.jintangList.size() - 1).setFocus(false);
        }
    }

    public EditText getEdt(int type) {
        if (type == 1) {
            return custom_jintang;
        } else {
            return custom_tiantang;
        }
    }

    public RelativeLayout getRl() {
        return itemChargetypeRlcost;
    }
}
