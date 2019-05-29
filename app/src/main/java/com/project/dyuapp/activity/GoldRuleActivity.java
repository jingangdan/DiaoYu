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
 * @description 个人中心-金币商城-金币规则
 * @created at 2017/11/29 0029
 */
public class GoldRuleActivity extends MyBaseActivity {

    @Bind(R.id.gold_rule_lv)
    ListView goldRuleLv;

    private List<String> mList = new ArrayList<>();//规则集合
    private GoldRuleAdapter ruleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_gold_rule);
        ButterKnife.bind(this);
        initAdapter();
        setIvBack();
        setTvTitle("金币任务");
    }

    /**
     * 初始化列表
     */
    private void initAdapter(){
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        ruleAdapter = new GoldRuleAdapter(this,mList,R.layout.item_gold_rule);
        goldRuleLv.setAdapter(ruleAdapter);
    }

    /**
     * 规则列表适配
     */
    class GoldRuleAdapter extends CommonAdapter<String> {

        @Bind(R.id.item_rule_tv_name)
        TextView itemRuleTvName;
        @Bind(R.id.item_rule_tv_unit)
        TextView itemRuleTvUnit;
        @Bind(R.id.item_rule_tv_rule)
        TextView itemRuleTvRule;

        /**
         * 构造函数
         *
         * @param context   上下文
         * @param data      显示的数据
         * @param layout_id listview使用的条目的布局文件id
         */
        public GoldRuleAdapter(Context context, List<String> data, int layout_id) {
            super(context, data, R.layout.item_gold_rule);
        }

        @Override
        public void convert(CommonViewHolder h, String item, int position) {
            ButterKnife.bind(this, h.getConvertView());
        }
    }

}
