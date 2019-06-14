package com.project.dyuapp.search;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.activity.FishingPlaceDetailsActivity;
import com.project.dyuapp.activity.FishingShopDetailsActivity;
import com.project.dyuapp.activity.SkillDetailsActivity;
import com.project.dyuapp.activity.VideoDetailsActivity;
import com.project.dyuapp.adapter.CommonAdapter;
import com.project.dyuapp.adapter.CommonViewHolder;
import com.project.dyuapp.adapter.HomeFishingAdapter;
import com.project.dyuapp.adapter.HomeFishingShopAdapter;
import com.project.dyuapp.adapter.PostListAdapter;
import com.project.dyuapp.adapter.VideoAdapter;
import com.project.dyuapp.base.LazyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.FishingGearEntity;
import com.project.dyuapp.entity.ForumEntity;
import com.project.dyuapp.entity.ForumListEntity;
import com.project.dyuapp.entity.HomeFishingPlaceEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.entity.VideoListBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 搜索结果-
 * Created by jingang on 2018/3/10.
 */

public class OtherSearchFragment extends LazyBaseFragment {
    @Bind(R.id.search_rl_title)
    LinearLayout searchRlTitle;
    @Bind(R.id.plv)
    PullToRefreshListView plv;
    @Bind(R.id.search_result_ll)
    LinearLayout searchResultLl;
    @Bind(R.id.search_result_lv)
    LinearLayout searchResultLv;

    private PopupWindow popupWindow;
    private String type = "";//搜索类型

    private HomeFishingAdapter adapterPlace;//钓场适配器
    private HomeFishingShopAdapter adapterShop;//渔具店适配器
    private VideoAdapter adapterVideo;//视频适配器
    private PostListAdapter adapterPost;//帖子适配器

    private ArrayList<HomeFishingPlaceEntity> data1 = new ArrayList<>();//钓场集合
    private ArrayList<FishingGearEntity> data2 = new ArrayList<>();//渔具店集合
    private ArrayList<VideoListBean> data3 = new ArrayList<>();//视频集合
    private ArrayList<ForumEntity> data4 = new ArrayList<>();//帖子列表集合
    private String object_id = "";//搜索类型
    private int page = 1;//当前页

    private String keyWords = "", lat = "", lng = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_result, null);
        ButterKnife.bind(this, view);
        searchRlTitle.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getArguments() != null) {
                type = getArguments().getString("type");
                keyWords = getArguments().getString("keyWords");
                lat = getArguments().getString("lat");
                lng = getArguments().getString("lng");
                if (!TextUtils.isEmpty(type)) {
                    setAdapter();
                    getData();
                }
            }
        }
    }

    /**
     * 适配数据
     */
    private void setAdapter() {
        //根据搜索类型选择适配器
        if (type.equals("钓场")) {
            object_id = "1";
            adapterPlace = new HomeFishingAdapter(data1, getActivity());
            plv.setAdapter(adapterPlace);
        } else if (type.equals("渔具店")) {
            object_id = "2";
            adapterShop = new HomeFishingShopAdapter(data2, getActivity(), "SearchResultActivity");
            plv.setAdapter(adapterShop);
        } else if (type.equals("商品")) {
            object_id = "3";
            adapterVideo = new VideoAdapter(data3, getActivity());
            plv.setAdapter(adapterVideo);
        } else if (type.equals("帖子")) {
            object_id = "4";
            adapterPost = new PostListAdapter(data4, getActivity());
            plv.setAdapter(adapterPost);
        }

        //列表点击事件监听 跳转详情
        plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type.equals("钓场")) {
                    startActivity(new Intent(getActivity(), FishingPlaceDetailsActivity.class).putExtra("FishingPlaceId", data1.get(position - 1).getF_gid()));
                } else if (type.equals("渔具店")) {
                    startActivity(new Intent(getActivity(), FishingShopDetailsActivity.class).putExtra("lat", data2.get(position - 1).getLatitude())
                            .putExtra("lon", data2.get(position - 1).getLongitude()).putExtra("id", data2.get(position - 1).getFishing_shop_id())
                            .putExtra("cityid", data2.get(position - 1).getCityid()));
                } else if (type.equals("商品")) {
                    startActivity(new Intent(getActivity(), VideoDetailsActivity.class).putExtra("article_id", data3.get(position - 1).getArticle_id()));
                } else if (type.equals("帖子")) {
                    startActivity(new Intent(getActivity(), SkillDetailsActivity.class).putExtra("id", data4.get(position - 1).getF_id()));
                }
            }
        });

        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新
                page = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载
                getData();
            }
        });
    }

    /**
     * 获取搜索结果列表
     */
    private void getData() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.search_object);
        commonOkhttp.putParams("object_id", object_id);//分类id 1钓场、2渔具店、3视频、4帖子
        commonOkhttp.putParams("keyWords", keyWords);
        if (object_id.equals("1") || object_id.equals("2")) {
            commonOkhttp.putParams("lat", SPUtils.getPreference(getActivity(), "latitude"));//纬度  钓场、渔具店需要
            commonOkhttp.putParams("lng", SPUtils.getPreference(getActivity(), "longitude"));//经度  钓场、渔具店需要
        }
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putParams("rows", "10");
        if (object_id.equals("1")) {
            commonOkhttp.putCallback(dcCallBack());
        } else if (object_id.equals("2")) {
            commonOkhttp.putCallback(yjdCallBack());
        } else if (object_id.equals("3")) {
            commonOkhttp.putCallback(spCallBack());
        } else if (object_id.equals("4")) {
            commonOkhttp.putCallback(tzCallBack());
        }
        commonOkhttp.Execute();

    }

    /**
     * 钓场列表
     *
     * @return
     */
    private MyGenericsCallback dcCallBack() {
        return new MyGenericsCallback<NetBean.HomeFishingPlaceListEntity>(getActivity()) {
            @Override
            public void onSuccess(NetBean.HomeFishingPlaceListEntity data, int d) {
                super.onSuccess(data, d);
                if (page == 1) {
                    data1.clear();
                }
                if (data != null && data.size() > 0) {
                    searchResultLv.setVisibility(View.VISIBLE);
                    searchResultLl.setVisibility(View.GONE);
                    data1.addAll(data);
                    ++page;
                } else {
                    if (page == 1) {
                        showMessage(getResources().getString(R.string.list_no_data));
                        searchResultLv.setVisibility(View.GONE);
                        searchResultLl.setVisibility(View.VISIBLE);
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                }
                adapterPlace.notifyDataSetChanged();
                plv.onRefreshComplete();
            }

            @Override
            public void onResponse(JsonResult<NetBean.HomeFishingPlaceListEntity> response, int id) {
                super.onResponse(response, id);
                plv.onRefreshComplete();
            }
        };
    }

    /**
     * 渔具店列表
     *
     * @return
     */
    private MyGenericsCallback yjdCallBack() {
        return new MyGenericsCallback<NetBean.FishingGearListEntity>(getActivity()) {
            @Override
            public void onSuccess(NetBean.FishingGearListEntity data, int d) {
                super.onSuccess(data, d);
                if (page == 1) {
                    data2.clear();
                }
                if (data != null && data.size() > 0) {
                    searchResultLv.setVisibility(View.VISIBLE);
                    searchResultLl.setVisibility(View.GONE);
                    data2.addAll(data);
                    ++page;
                } else {
                    if (page == 1) {
                        searchResultLv.setVisibility(View.GONE);
                        searchResultLl.setVisibility(View.VISIBLE);
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                }
                adapterShop.notifyDataSetChanged();
                plv.onRefreshComplete();
            }

            @Override
            public void onResponse(JsonResult<NetBean.FishingGearListEntity> response, int id) {
                super.onResponse(response, id);
                plv.onRefreshComplete();
            }
        };
    }

    /**
     * 视频列表
     *
     * @return
     */
    private MyGenericsCallback spCallBack() {
        return new MyGenericsCallback<NetBean.VideoListEntity>(getActivity()) {
            @Override
            public void onSuccess(NetBean.VideoListEntity data, int d) {
                super.onSuccess(data, d);
                if (page == 1) {
                    data3.clear();
                }
                if (data != null && data.size() > 0) {
                    searchResultLv.setVisibility(View.VISIBLE);
                    searchResultLl.setVisibility(View.GONE);
                    data3.addAll(data);
                    ++page;
                } else {
                    if (page == 1) {
                        searchResultLv.setVisibility(View.GONE);
                        searchResultLl.setVisibility(View.VISIBLE);
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                }
                adapterVideo.notifyDataSetChanged();
                plv.onRefreshComplete();

            }

            @Override
            public void onResponse(JsonResult<NetBean.VideoListEntity> response, int id) {
                super.onResponse(response, id);
                plv.onRefreshComplete();
            }
        };
    }


    /**
     * 帖子列表
     *
     * @return
     */
    private MyGenericsCallback tzCallBack() {
        return new MyGenericsCallback<ForumListEntity>(getActivity()) {
            @Override
            public void onSuccess(ForumListEntity data, int d) {
                super.onSuccess(data, d);
                if (page == 1) {
                    data4.clear();
                }
                if (data != null && data.size() > 0) {
                    searchResultLv.setVisibility(View.VISIBLE);
                    searchResultLl.setVisibility(View.GONE);
                    data4.addAll(data);
                    ++page;
                } else {
                    if (page == 1) {
                        searchResultLv.setVisibility(View.GONE);
                        searchResultLl.setVisibility(View.VISIBLE);
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                }
                adapterPost.notifyDataSetChanged();
                plv.onRefreshComplete();
            }

            @Override
            public void onResponse(JsonResult<ForumListEntity> response, int id) {
                super.onResponse(response, id);
                plv.onRefreshComplete();
            }
        };
    }

    /**
     * 选择搜索类型弹框
     */
    private void showTypeDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_lv, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        if (popupWindow.isShowing()) {
            return;
        }
        ListView listView = (ListView) view.findViewById(R.id.dialog_lv);
        //适配数据
        final List<String> list = new ArrayList<>();
        list.add("钓场");
        list.add("渔具店");
        list.add("商品");
        list.add("帖子");
        listView.setAdapter(new CommonAdapter<String>(getActivity(), list, R.layout.item_lv_tv) {
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
                //searchTvType.setText(list.get(i));
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
                //searchTvType.setCompoundDrawables(null, null, img, null); //设置左图标
            }
        });
    }

}
