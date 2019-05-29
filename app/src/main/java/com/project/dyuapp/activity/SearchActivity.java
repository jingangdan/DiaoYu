package com.project.dyuapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CommonAdapter;
import com.project.dyuapp.adapter.CommonViewHolder;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.EmptyResultBean;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.entity.SearchHistoryBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.search.SearchMessageActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.project.dyuapp.R.id.search_tv_type;


/**
 * @author litongtong
 * @created on 2017/12/5 11:23
 * @description 首页-搜索
 * @change ${}
 */

public class SearchActivity extends MyBaseActivity {
    @Bind(search_tv_type)
    TextView searchTvType;//搜索类型
    @Bind(R.id.search_et_content)
    EditText searchEtContent;//搜索内容
    @Bind(R.id.search_flowlayout)
    TagFlowLayout searchFlowlayout;//搜索关键词
    @Bind(R.id.search_lv_history)
    ListView searchLvHistory;//搜索历史
    @Bind(R.id.search_rl_title)
    LinearLayout searchRlTitle;
    @Bind(R.id.search_tv_cancle)
    TextView tvSearch;//搜索按钮
    @Bind(R.id.search_ll_history)
    LinearLayout searchLlHistory;

    private List<String> keyList = new ArrayList<>();//关键词集合
    private ArrayList<SearchHistoryBean> historyList = new ArrayList<>();//历史集合
    private TagAdapter hotAdapter;
    private CommonAdapter hAdapter;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHot();
        getHistory();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        //去掉搜索左侧的选项
        searchTvType.setVisibility(View.GONE);

        //搜索关键字
        hotAdapter = new TagAdapter<String>(keyList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.flowlayout_tag_tv, parent, false);
                tv.setText(keyList.get(position));
                return tv;
            }
        };
        searchFlowlayout.setAdapter(hotAdapter);
        searchFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                searchEtContent.setText(keyList.get(position));
                startActivity(new Intent(SearchActivity.this, SearchMessageActivity.class)
                        .putExtra("type", searchTvType.getText().toString())
                        .putExtra("word", keyList.get(position)));
                return false;
            }
        });

        //搜索历史
        hAdapter = new CommonAdapter<SearchHistoryBean>(this, historyList, R.layout.item_search_history_lv) {
            @Override
            public void convert(CommonViewHolder h, SearchHistoryBean item, final int position) {
                //搜索内容
                ((TextView) h.getConvertView().findViewById(R.id.history_tv_content)).setText(historyList.get(position).getKeywords());
                //删除
                ((ImageView) h.getConvertView().findViewById(R.id.history_iv_delete)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteHistory(position, historyList.get(position).getS_id());
                    }
                });
            }
        };
        searchLvHistory.setAdapter(hAdapter);
        searchLvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchEtContent.setText(historyList.get(i).getKeywords());
                startActivity(new Intent(SearchActivity.this, SearchMessageActivity.class)
                        .putExtra("type", searchTvType.getText().toString())
                        .putExtra("word", historyList.get(i).getKeywords()));
            }
        });

    }

    /**
     * 获取搜索-热门关键词
     */
    private void getHot(){
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.get_hot_words);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.HotEntity>(this){
            @Override
            public void onSuccess(NetBean.HotEntity data, int d) {
                super.onSuccess(data, d);
                keyList.clear();
                keyList.addAll(data);
                hotAdapter.notifyDataChanged();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 获取搜索历史
     */
    private void getHistory() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.get_searchHistory);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.SearchHistoryEntity>(this) {
            @Override
            public void onSuccess(NetBean.SearchHistoryEntity data, int d) {
                super.onSuccess(data, d);
                historyList.clear();
                historyList.addAll(data);
                hAdapter.notifyDataSetChanged();
                if(historyList.size()>0){
                    searchLlHistory.setVisibility(View.VISIBLE);
                }else {
                    searchLlHistory.setVisibility(View.GONE);
                }
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 删除搜索历史
     *
     * @param position
     */
    private void deleteHistory(final int position, final String id) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.del_searchHistory);
        commonOkhttp.putParams("s_id", id);//清空所有则传空即可
        commonOkhttp.putCallback(new MyGenericsCallback<EmptyResultBean>(this) {
            @Override
            public void onSuccess(EmptyResultBean data, int d) {
                super.onSuccess(data, d);
                if (id.equals("")) {
                    historyList.clear();
                } else {
                    historyList.remove(position);
                }
                hAdapter.notifyDataSetChanged();
                if(historyList.size()>0){
                    searchLlHistory.setVisibility(View.VISIBLE);
                }else {
                    searchLlHistory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }

    @OnClick({R.id.search_tv_type, R.id.search_iv_search, R.id.search_tv_cancle, R.id.search_tv_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case search_tv_type:
                //搜索类型
                Drawable img = getResources().getDrawable(R.mipmap.more_triangle_on);
                // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
                img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                searchTvType.setCompoundDrawables(null, null, img, null); //设置左图标
                showTypeDialog();
                break;
            case R.id.search_iv_search:
                //搜索
                if (TextUtils.isEmpty(searchEtContent.getText().toString().trim())) {
                    showMessage("请输入搜索内容");
                } else {
                    startActivity(new Intent(this, SearchMessageActivity.class)
                            .putExtra("type", searchTvType.getText().toString())
                            .putExtra("word", searchEtContent.getText().toString()));
                    //finishActivity();
                }
                break;
            case R.id.search_tv_cancle:
                //取消
                finishActivity();
                break;
            case R.id.search_tv_clear:
                //清空历史
                deleteHistory(-1, "");
                break;
        }
    }

    /**
     * 选择搜索类型弹框
     */
    private void showTypeDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_lv, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
//        if (popupWindow.isShowing()) {
//            return;
//        }
        ListView listView = (ListView) view.findViewById(R.id.dialog_lv);
        //适配数据
        final List<String> list = new ArrayList<>();
        list.add("钓场");
        list.add("渔具店");
        list.add("视频");
        list.add("帖子");
        listView.setAdapter(new CommonAdapter<String>(this, list, R.layout.item_lv_tv) {
            @Override
            public void convert(CommonViewHolder h, String item, int position) {
                ((TextView) h.getConvertView().findViewById(R.id.lv_tv_text)).setText(list.get(position));
            }
        });
        //列表点击监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                popupWindow.dismiss();
                searchTvType.setText(list.get(i));
            }
        });
        //弹框背景色
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        popupWindow.setOutsideTouchable(true);//可以触摸框外区域
        popupWindow.showAsDropDown(searchRlTitle);//显示位置
        //弹框消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Drawable img = getResources().getDrawable(R.mipmap.more_triangle_down);
                // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
                img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                searchTvType.setCompoundDrawables(null, null, img, null); //设置左图标
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
