package com.project.dyuapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CommonAdapter;
import com.project.dyuapp.adapter.CommonViewHolder;
import com.project.dyuapp.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author shipeiyun
 * @description 个人中心-每日签到-积分规则
 * @created at 2017/12/2 0002
 */
public class IntegrationRuleActivity extends MyBaseActivity {

    @Bind(R.id.integration_rule_lv)
    ListView integrationRuleLv;

    private List<String> mList = new ArrayList<>();
    private IntegrationRuleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_integration_rule);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("积分规则");
        initAdapter();
    }

    /**
     * 初始化列表
     */
    private void initAdapter() {
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        adapter = new IntegrationRuleAdapter(this, mList, R.layout.item_gold_rule);
        integrationRuleLv.setAdapter(adapter);
    }

    /**
     * 规则列表适配
     */
    class IntegrationRuleAdapter extends CommonAdapter<String> {

        @Bind(R.id.item_rule_tv_name)
        TextView itemRuleTvName;//名称
        @Bind(R.id.item_rule_tv_unit)
        TextView itemRuleTvUnit;//单位
        @Bind(R.id.item_rule_tv_rule)
        TextView itemRuleTvRule;//积分

        /**
         * 构造函数
         *
         * @param context   上下文
         * @param data      显示的数据
         * @param layout_id listview使用的条目的布局文件id
         */
        public IntegrationRuleAdapter(Context context, List<String> data, int layout_id) {
            super(context, data, R.layout.item_gold_rule);
        }

        @Override
        public void convert(CommonViewHolder h, String item, int position) {
            ButterKnife.bind(this, h.getConvertView());
        }
    }

}
