package com.project.dyuapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseFragment;
import com.project.dyuapp.myviews.GridViewForScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 搜索—内容
 * Created by jingang on 2018/2/26.
 */

public class SearchFragment extends MyBaseFragment {
    @Bind(R.id.search_tv)
    TextView searchTv;
    @Bind(R.id.search_ll)
    LinearLayout searchLl;
    @Bind(R.id.search_gv)
    GridViewForScrollView searchGv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
