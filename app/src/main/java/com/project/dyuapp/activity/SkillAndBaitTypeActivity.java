package com.project.dyuapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.SkillAndBaitAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.HomeFishingPlaceEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.entity.SiteCategoryEntity;
import com.project.dyuapp.entity.SiteCategoryListEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.project.dyuapp.myutils.HttpUrl.ANGLING_ANG_LIST;

/**
 * @author gengqiufang
 * @describtion 技巧 饵料  分类
 * @created at 2017/12/9 0009
 */
public class SkillAndBaitTypeActivity extends MyBaseActivity {
    @Bind(R.id.plv)
    PullToRefreshListView plv;
    @Bind(R.id.place_name_ll_search_show)
    LinearLayout llSearchShow;
    @Bind(R.id.place_name_edt_search)
    EditText edtSearch;
    @Bind(R.id.place_name_ll_search)
    LinearLayout llSearch;
    @Bind(R.id.place_name_tv_search)
    TextView tvSearch;
    @Bind(R.id.place_tv_add)
    ImageView tvAdd;


    private List<SiteCategoryEntity> typeList = new ArrayList<>();
    private SkillAndBaitAdapter mAdapter;

    private String title = "";
    private String topId = "";

    private boolean isMulti;//是否是多选
    private boolean isPlaceName;//钓场名

    private String selContent = "";
    private String selId = "";

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_skill_and_bait_type);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
            title = getIntent().getStringExtra("title");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("topId"))) {
            topId = getIntent().getStringExtra("topId");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("sel_id"))) {
            selId = getIntent().getStringExtra("sel_id");
        }
        isPlaceName = getIntent().getBooleanExtra("isPlaceName", false);
        isMulti = getIntent().getBooleanExtra("isMulti", false);

        initView();
        initData();
        if (isPlaceName) {
            getfishingPlace();
            llSearch.setVisibility(View.GONE);
            llSearchShow.setVisibility(View.VISIBLE);
            tvAdd.setVisibility(View.VISIBLE);
        } else {
            okHttpGetSiteCategory();
        }

    }

    @OnClick({R.id.place_name_tv_search, R.id.place_name_ll_search_show, R.id.place_tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.place_name_ll_search_show:
                llSearch.setVisibility(View.VISIBLE);
                llSearchShow.setVisibility(View.GONE);
                edtSearch.requestFocus();
                break;
            case R.id.place_name_tv_search:
                if (TextUtils.isEmpty(edtSearch.getText())){
                    showMessage("请输入内容");
                }else {
                    page=1;
                    getData();
                }
                break;
            case R.id.place_tv_add:
                startActivity(new Intent(this, AppendFishingPlaceActivity.class));
                break;

        }

    }

    /**
     * 初始化控件
     */
    private void initView() {
        setTvTitle(title);
        setIvBack();
        getTvRight().setText("确定");
        getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (SiteCategoryEntity item : typeList) {
                    if (item.isSel()) {
                        selId = selId + item.getD_id() + ",";
                        selContent = selContent + item.getName() + ",";
                    }
                }
                if (TextUtils.isEmpty(selId)) {
                    showMessage("请选择");
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("sel", selContent.substring(0, selContent.length() - 1));
                    intent.putExtra("sel_id", selId.substring(0, selId.length() - 1));
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mAdapter = new SkillAndBaitAdapter(typeList, this);
        plv.setAdapter(mAdapter);
        plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position - 1;
                if (isMulti) {
                    //多项
                    typeList.get(position).setSel(!typeList.get(position).isSel());
                } else {
                    //单项
                    for (SiteCategoryEntity item : typeList) {
                        item.setSel(false);
                    }
                    typeList.get(position).setSel(true);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                if (isPlaceName) {
                    getfishingPlace();
                } else {
                    okHttpGetSiteCategory();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (isPlaceName) {
                    getfishingPlace();
                }
            }
        });
    }

    //4站点分类 集合接口
    public void okHttpGetSiteCategory() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.getSiteCategory);
        commonOkhttp.putParams("cid", topId);
        commonOkhttp.putCallback(new MyGenericsCallback<SiteCategoryListEntity>(this) {
            @Override
            public void onSuccess(SiteCategoryListEntity data, int d) {
                super.onSuccess(data, d);
                typeList.clear();
                typeList.addAll(data);
                if (!TextUtils.isEmpty(selId)) {
                    if (selId.contains(",")) {
                        //多项
                        String[] str = selId.split(",");
                        for (String item : str) {
                            for (SiteCategoryEntity item2 : typeList) {
                                if (item.equals(item2.getD_id())) {
                                    item2.setSel(true);
                                }
                            }
                        }
                    } else {
                        //单项
                        for (SiteCategoryEntity item2 : typeList) {
                            if (selId.equals(item2.getD_id())) {
                                item2.setSel(true);
                            }
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        commonOkhttp.Execute();
    }

    //获取钓场名
    private void getfishingPlace() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(ANGLING_ANG_LIST);
        commonOkhttp.putParams("city_name", SPUtils.getPreference(this, "city"));
        commonOkhttp.putParams("longitude", SPUtils.getPreference(this, "longitude"));
        commonOkhttp.putParams("latitude", SPUtils.getPreference(this, "latitude"));
        commonOkhttp.putParams("order", "1");
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.HomeFishingPlaceListEntity>(this) {
            @Override
            public void onSuccess(NetBean.HomeFishingPlaceListEntity data, int d) {
                super.onSuccess(data, d);
                plv.onRefreshComplete();
                if (page == 1) {
                    typeList.clear();
                }
                if (data.size() == 0) {
                    if (page == 1) {
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                } else {
                    for (HomeFishingPlaceEntity item : data) {
                        if ((!TextUtils.isEmpty(selId)) && selId.equals(item.getF_gid())) {
                            typeList.add(new SiteCategoryEntity(item.getF_gid(), item.getG_name(), true));
                        } else {
                            typeList.add(new SiteCategoryEntity(item.getF_gid(), item.getG_name()));
                        }
                    }
                    page += 1;
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                plv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                plv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 搜索钓场 获取搜索结果列表
     */
    private void getData() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.search_object);
        commonOkhttp.putParams("object_id", "1");//分类id 1钓场、2渔具店、3视频、4帖子
        commonOkhttp.putParams("keyWords", edtSearch.getText().toString());
        commonOkhttp.putParams("lat", SPUtils.getPreference(this, "latitude"));//纬度  钓场、渔具店需要
        commonOkhttp.putParams("lng", SPUtils.getPreference(this, "longitude"));//经度  钓场、渔具店需要
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putParams("rows", "10");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.HomeFishingPlaceListEntity>(this) {
            @Override
            public void onSuccess(NetBean.HomeFishingPlaceListEntity data, int d) {
                super.onSuccess(data, d);
                plv.onRefreshComplete();
                if (page == 1) {
                    typeList.clear();
                }
                if (data.size() == 0) {
                    if (page == 1) {
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                } else {
                    for (HomeFishingPlaceEntity item : data) {
                        if ((!TextUtils.isEmpty(selId)) && selId.equals(item.getF_gid())) {
                            typeList.add(new SiteCategoryEntity(item.getF_gid(), item.getG_name(), true));
                        } else {
                            typeList.add(new SiteCategoryEntity(item.getF_gid(), item.getG_name()));
                        }
                    }
                    page += 1;
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                plv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                plv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();

    }
}
