package com.project.dyuapp.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.HomeVideosBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 搜索结果-综合
 * Created by jingang on 2018/3/10.
 */

public class SynthesizSearchFragment extends MyBaseFragment implements SearchInterface {
    @Bind(R.id.rv_search)
    RecyclerView recyclerView;

    private String keyWords = "", lat = "", lng = "";
    private String type = "";//搜索类型

    private List<SearchMessageEntity.DataBeanXX> dataList = new ArrayList<>();
    private SearchAdapter mAdapter;

    private SearchInterface searchInterface;

    public void setSearchInterface(SearchInterface searchInterface) {
        this.searchInterface = searchInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_synthsize, null);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getArguments() != null) {
                keyWords = getArguments().getString("keyWords");
                lat = getArguments().getString("lat");
                lng = getArguments().getString("lng");
                type = getArguments().getString("type");
                if (!TextUtils.isEmpty(keyWords)) {
                    okhttpSearch();
                }
            }
        }
    }

    /*综合*/
    private void okhttpSearch() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.search_object_new);
        commonOkhttp.putParams("object_id", "5");//分类id 1钓场、2渔具店、3视频、4帖子
        commonOkhttp.putParams("keyWords", keyWords);
        commonOkhttp.putParams("lat", lat);//纬度  钓场、渔具店需要
        commonOkhttp.putParams("lng", lng);//经度  钓场、渔具店需要

        commonOkhttp.putCallback(new MyGenericsCallback<SearchMessageEntity.DataBeanXX>(getActivity()) {
            @Override
            public void onSuccess(SearchMessageEntity.DataBeanXX data, int d) {
                super.onSuccess(data, d);

                dataList.clear();
                dataList.add(data);
                mAdapter = new SearchAdapter(getActivity(), dataList);
                mAdapter.setSearchInterface(searchInterface);

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(mAdapter);

            }
        });
        commonOkhttp.Execute();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void doTabCode(int code) {
        searchInterface.doTabCode(code);
    }
}

