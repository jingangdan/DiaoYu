package com.project.dyuapp.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.FishingPlaceTypeEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${田亭亭} on 2017/11/29 0029.
 *
 * @description 钓场类型(发布钓场)适配器
 * @change
 */


public class FishingPlaceSelectAdapter extends MyCommonAdapter<FishingPlaceTypeEntity> {

    @Bind(R.id.item_fingerling_list_tv_name)
    TextView tvName;
    @Bind(R.id.item_fingerling_list_img_select)
    CheckBox imgSelect;
    private static HashMap<Integer, Boolean> checkList = new HashMap<>();

    private List<String> strings = new ArrayList();

    public FishingPlaceSelectAdapter(List<FishingPlaceTypeEntity> list, Context mContext, int resid, List<String> strings) {
        super(list, mContext, R.layout.item_fingerling_list);
        this.strings = strings;
    }

    @Override
    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
        ButterKnife.bind(this, commentViewHolder.getConverView());
        tvName.setText(list.get(position).getName());
        imgSelect.setChecked(getIsSelected().get(position));
    }

    public void notifyData() {
        initDate();
        for (int i = 0; i < strings.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (strings.get(i).equals(list.get(j).getName()))
                    checkList.put(j, true);
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
