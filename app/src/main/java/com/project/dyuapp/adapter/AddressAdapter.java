package com.project.dyuapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.entity.SelectAddressEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author ${shipeiyun}
 * @created on 2017/11/28 0028
 * @description 收货地址适配
 * @change ${}
 */
public class AddressAdapter extends CommonAdapter<SelectAddressEntity> {
    @Bind(R.id.item_address_tv_name)
    TextView itemAddressTvName;//收货人
    @Bind(R.id.item_address_tv_phone)
    TextView itemAddressTvPhone;//收货电话
    @Bind(R.id.item_address_tv_site)
    TextView itemAddressTvSite;//收货地址
    @Bind(R.id.item_address_iv_default)
    ImageView itemAddressIvDefault;//默认地址
    @Bind(R.id.item_address_ll_default)
    LinearLayout itemAddressLlDefault;//默认地址
    @Bind(R.id.item_address_tv_delete)
    TextView itemAddressTvDelete;//删除
    @Bind(R.id.item_address_tv_edit)
    TextView itemAddressTvEdit;//编辑

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onSetDefaultClick(View view, int position);

        void onDeleteClick(View view, int position);

        void onEditClick(View view, int position);
    }

    public OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 构造函数
     *
     * @param context   上下文
     * @param data      显示的数据
     * @param layout_id listview使用的条目的布局文件id
     */
    public AddressAdapter(Context context, List<SelectAddressEntity> data, int layout_id) {
        super(context, data, R.layout.item_address);
    }

    @Override
    public void convert(CommonViewHolder h, SelectAddressEntity item, final int position) {
        ButterKnife.bind(this, h.getConvertView());
        String isDefault = item.getIs_default();//是否默认地址1是0否
        if (isDefault.equals("1")){
            itemAddressIvDefault.setImageDrawable(context.getResources().getDrawable(R.mipmap.check_button_select));
        } else {
            itemAddressIvDefault.setImageDrawable(context.getResources().getDrawable(R.mipmap.check_button_unselect));
        }
        String name = item.getUsername();//收货人
        if (!TextUtils.isEmpty(name)){
            itemAddressTvName.setText(name);
        }
        String phone = item.getPhone();//收货电话
        if (!TextUtils.isEmpty(phone)){
            itemAddressTvPhone.setText(phone);
        }
        String site = item.getXqaddress();//收货地址
        if (!TextUtils.isEmpty(site)){
            itemAddressTvSite.setText(site);
        }

        //默认地址
        if (mOnItemClickListener != null) {
            itemAddressLlDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onSetDefaultClick(itemAddressLlDefault, position-1);
                }
            });
        }
        //编辑
        if (mOnItemClickListener != null) {
            itemAddressTvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onEditClick(itemAddressTvEdit, position-1);
                }
            });
        }
        //删除
        if (mOnItemClickListener != null) {
            itemAddressTvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onDeleteClick(itemAddressTvDelete, position-1);
                }
            });
        }

    }
}
