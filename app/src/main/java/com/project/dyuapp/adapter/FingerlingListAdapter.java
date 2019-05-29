package com.project.dyuapp.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.FingerlingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/11/29 0029.
 *
 * @description 鱼种适配器
 * @change
 */


public class FingerlingListAdapter extends MyCommonAdapter<FingerlingEntity> {

    @Bind(R.id.item_fingerling_list_tv_name)
    TextView tvName;
    @Bind(R.id.item_fingerling_list_img_select)
    CheckBox imgSelect;
    private static HashMap<Integer, Boolean> checkList = new HashMap<>();

    private List<String> strings = new ArrayList();

    public FingerlingListAdapter(List<FingerlingEntity> list, Context mContext, int resid, List<String> strings) {
        super(list, mContext, R.layout.item_fingerling_list);
        this.strings = strings;
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        imgSelect.setChecked(getIsSelected().get(position));
        tvName.setText(list.get(position).getName());
    }

    public void notifyData() {
        initDate();
        for (int i = 0; i < strings.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (strings.get(i).equals(list.get(j).getName())) {
                    checkList.put(j, true);
                    break;
                }
            }
        }
        notifyDataSetChanged();
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return checkList;
    }


    public static void setIsSelected(int isSelected) {
        getIsSelected().put(isSelected, !checkList.get(isSelected));
    }

    // 初始化isSelected的数据
    private void initDate() {
        getIsSelected().clear();
        for (int i = 0; i < list.size(); i++) {
            getIsSelected().put(i, false);
        }
    }
}
