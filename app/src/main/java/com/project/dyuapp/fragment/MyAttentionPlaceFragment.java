package com.project.dyuapp.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.activity.FishingPlaceDetailsActivity;
import com.project.dyuapp.adapter.CenterFishingPlaceAdapter;
import com.project.dyuapp.base.MyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.AttentionPlaceEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.entity.SuccessBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author 田亭亭
 * @description 个人中心-我的钓场-我关注的
 * @created at 2017/11/28 0028
 * @change
 */
public class MyAttentionPlaceFragment extends MyBaseFragment {

    @Bind(R.id.my_attention_place_lv)
    PullToRefreshListView myAttentionPlaceLv;

    private List<AttentionPlaceEntity> mList = new ArrayList<>();//列表集合
    private CenterFishingPlaceAdapter mAdapter;

    private int page = 1;//分页
    private String latitude = "";//纬度
    private String longitude = "";//经度

    private String fishingPlaceId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_attention_place, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initView() {
        myAttentionPlaceLv.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new CenterFishingPlaceAdapter(mList, getActivity(), R.layout.item_center_fishing_place, "1");
        myAttentionPlaceLv.setAdapter(mAdapter);
        //条目点击事件
        myAttentionPlaceLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), FishingPlaceDetailsActivity.class);
                intent.putExtra("FishingPlaceId", mList.get(i - 1).getObject_id());
                startActivity(intent);
            }
        });
        //按钮点击事件
        mAdapter.setOnItemClickListener(new CenterFishingPlaceAdapter.OnItemClickListener() {
            @Override
            public void onClickview(View view, int position) {
                fishingPlaceId = mList.get(position).getObject_id();
                okhttpIsAttention();
            }
        });
        myAttentionPlaceLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                attentionPlaceList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                attentionPlaceList();
            }
        });

        attentionPlaceList();
    }

    /**
     * 我关注的
     */
    private void attentionPlaceList() {

        latitude = PublicStaticData.sharedPreferences.getString("latitude", "");//纬度
        longitude = PublicStaticData.sharedPreferences.getString("longitude", "");//经度
        if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude))
            ToastUtils.getInstance(getActivity()).showMessage("定位信息异常");

        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.PERSONCENTER_MY_FISHG);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putParams("lng", longitude);//经度
        commonOkhttp.putParams("lat", latitude);//纬度
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.AttentionPlaceListEntity>(getActivity()) {
            @Override
            public void onSuccess(NetBean.AttentionPlaceListEntity data, int d) {
                super.onSuccess(data, d);
                myAttentionPlaceLv.onRefreshComplete();
                if (page <= 1) {
                    mList.clear();
                    mList.addAll(data);
                } else {
                    mList.addAll(data);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                myAttentionPlaceLv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                myAttentionPlaceLv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();

    }

    /**
     * 关注接口
     */
    private void okhttpIsAttention() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.ANGLING_FOLLOW_FISH);
        commonOkhttp.putParams("f_gid", fishingPlaceId);
        commonOkhttp.putCallback(new MyGenericsCallback<SuccessBean>(getActivity()) {
            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                try {
                    ToastUtils.getInstance(getActivity()).showMessage(message.toString());
                    attentionPlaceList();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
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
