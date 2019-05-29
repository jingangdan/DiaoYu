package com.project.dyuapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.activity.FansMainPagerActivity;
import com.project.dyuapp.adapter.AttentionFansAdapter;
import com.project.dyuapp.base.MyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.EmptyResultBean;
import com.project.dyuapp.entity.FansEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author ${shipeiyun}
 * @created on 2017/11/27 0027
 * @description 我的-关注
 * @change ${}
 */
public class AttentionFragment extends MyBaseFragment {
    @Bind(R.id.attention_lv)
    PullToRefreshListView attentionLv;

    private List<FansEntity> mList = new ArrayList<>();//列表集合
    private AttentionFansAdapter attentionAdapter;
    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attention, null);
        ButterKnife.bind(this, view);
        initView();
        page = 1;
        okhttpFollower();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initView() {
        attentionLv.setMode(PullToRefreshBase.Mode.BOTH);
        attentionAdapter = new AttentionFansAdapter(getActivity(), mList, R.layout.item_attention_fans, "1");
        attentionLv.setAdapter(attentionAdapter);
        //条目点击事件
        attentionLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), FansMainPagerActivity.class)
                        .putExtra("id", mList.get(i - 1).getMember_list_id()));
            }
        });
        //按钮点击事件
        attentionAdapter.setOnItemClickListener(new AttentionFansAdapter.OnItemClickListener() {
            @Override
            public void onClickview(View view, int position) {
                okhttpAttention(position);
            }
        });
        attentionLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                okhttpFollower();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                okhttpFollower();
            }
        });
    }

    /**
     * 10个人中心-关注
     */
    private void okhttpFollower() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.follower);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.FansListEntity>(getActivity()) {
            @Override
            public void onSuccess(NetBean.FansListEntity data, int d) {
                super.onSuccess(data, d);
                if (page == 1) {
                    mList.clear();
                }
                if (data != null && data.size() > 0) {
                    mList.addAll(data);
                    ++page;
                } else {
                    if (page == 1) {
                        Toast.makeText(getActivity(), getString(R.string.list_no_data), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.list_bottom), Toast.LENGTH_SHORT).show();
                    }
                }
                attentionLv.onRefreshComplete();
                attentionAdapter.notifyDataSetChanged();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 关注、取消关注
     */
    private void okhttpAttention(int position) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.attention);
        commonOkhttp.putParams("memberid", mList.get(position).getMember_list_id());
        commonOkhttp.putCallback(new MyGenericsCallback<EmptyResultBean>(getActivity()) {
            @Override
            public void onSuccess(EmptyResultBean data, int d) {
                super.onSuccess(data, d);
                page = 1;
                okhttpFollower();
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
        commonOkhttp.Execute();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
