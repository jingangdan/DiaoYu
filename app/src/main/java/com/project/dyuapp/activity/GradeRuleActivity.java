package com.project.dyuapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CommonAdapter;
import com.project.dyuapp.adapter.CommonViewHolder;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.LvlRuleEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author shipeiyun
 * @description 个人中心-每日签到-等级规则
 * @created at 2017/12/2 0002
 */
public class GradeRuleActivity extends MyBaseActivity {

    @Bind(R.id.grade_rule_lv)
    ListView gradeRuleLv;

    private List<LvlRuleEntity> mList = new ArrayList<>();//列表集合
    private GradeRuleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_grade_rule);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("等级规则");
        initAdapter();
        okhttpLvlRule();
    }

    /**
     * 初始化列表适配
     */
    private void initAdapter() {
        View headView = getLayoutInflater().inflate(R.layout.item_grade_rule_head, null);
        gradeRuleLv.addHeaderView(headView);

        adapter = new GradeRuleAdapter(this, mList, R.layout.item_grade_rule);
        gradeRuleLv.setAdapter(adapter);
    }

    /**
     * 14个人中心-每日签到-等级规则
     */
    private void okhttpLvlRule(){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.lvlRule);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.LvlRuleListEntity>(GradeRuleActivity.this){
            @Override
            public void onSuccess(NetBean.LvlRuleListEntity data, int d) {
                super.onSuccess(data, d);
                if (data != null){
                    mList.addAll(data);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 列表适配
     */
    class GradeRuleAdapter extends CommonAdapter<LvlRuleEntity> {

        @Bind(R.id.item_grade_rule_tv_grade)
        TextView tvGrade;//等级
        @Bind(R.id.item_grade_rule_tv_honor)
        TextView tvHonor;//头衔
        @Bind(R.id.item_grade_rule_tv_need)
        TextView tvNeed;//所需积分
        @Bind(R.id.item_grade_rule_tv_gold)
        TextView tvGold;//金币

        /**
         * 构造函数
         *
         * @param context   上下文
         * @param data      显示的数据
         * @param layout_id listview使用的条目的布局文件id
         */
        public GradeRuleAdapter(Context context, List<LvlRuleEntity> data, int layout_id) {
            super(context, data, R.layout.item_grade_rule);
        }

        @Override
        public void convert(CommonViewHolder h, LvlRuleEntity item, int position) {
            ButterKnife.bind(this, h.getConvertView());
            String lvl = item.getMember_lvl_id();//等级
            if (!TextUtils.isEmpty(lvl)){
                tvGrade.setText(lvl);
            }
            String name = item.getMember_lvl_name();//头衔
            if (!TextUtils.isEmpty(name)){
                tvHonor.setText(name);
            }
            String score = item.getMax_score();//所需积分
            if (!TextUtils.isEmpty(score)){
                tvNeed.setText(score);
            }
            String gold = item.getGold();//金币
            if (!TextUtils.isEmpty(gold)) {
                tvGold.setText(gold);
            }
        }
    }

}
