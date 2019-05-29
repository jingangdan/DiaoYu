package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.DraftsAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.DraftsCallBack;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.DraftBoxBean;
import com.project.dyuapp.entity.EmptyResultBean;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.PublicStaticData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author gengqiufang
 * @describtion 草稿箱
 * @created at 2017/11/30 0030
 */
public class DraftsActivity extends MyBaseActivity {

    @Bind(R.id.plv)
    PullToRefreshListView plv;

    private ArrayList<DraftBoxBean> mData = new ArrayList<>();
    private DraftsAdapter mAdapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_drafts);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("草稿箱");
        initPlv();
        getData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getData();
    }

    /**
     * 适配数据
     */
    private void initPlv() {
        mAdapter = new DraftsAdapter(mData, this, new DraftsCallBack() {
            @Override
            public void onClickDel(int position) {
                //删除
                delete(position);
            }

            @Override
            public void onClickRepeat(int position) {
                //重发
                resend(position);
            }

            @Override
            public void onClickChange(int position) {
                //修改
                Intent intent;
                if (mData.get(position).getTop_id().equals(PublicStaticData.ID_GET_FISH)) {
                    intent = new Intent(DraftsActivity.this, PublishGetChangeActivity.class);
                } else {
                    intent = new Intent(DraftsActivity.this, PublishPostChangeActivity.class);
                }
                intent.putExtra("id", mData.get(position).getF_id());
                startActivity(intent);
            }
        });
        plv.setAdapter(mAdapter);

        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                page=1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                plv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        plv.onRefreshComplete();
                    }
                }, 1000);
            }
        });
    }


    /**
     * 获取草稿箱列表
     */
    private void getData() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.draftBox);
//        commonOkhttp.putParams("page",page+"");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.DraftBoxEntity>(this) {
            @Override
            public void onSuccess(NetBean.DraftBoxEntity data, int d) {
                super.onSuccess(data, d);
           /*     if (page == 1) {
                    mData.clear();
                }
                if (data != null && data.size() > 0) {
                    mData.addAll(data);
                    ++page;
                } else {
                    if (page == 1) {
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                }*/
                mData.clear();
                mData.addAll(data);
                mAdapter.notifyDataSetChanged();
                plv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                plv.onRefreshComplete();
            }

            @Override
            public void onOther(JsonResult<NetBean.DraftBoxEntity> data, int d) {
                super.onOther(data, d);
                plv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 删除草稿
     *
     * @param position
     */
    private void delete(final int position) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.caogaoDel);
        commonOkhttp.putParams("f_id", mData.get(position).getF_id());
        commonOkhttp.putCallback(new MyGenericsCallback<EmptyResultBean>(this) {
            @Override
            public void onSuccess(EmptyResultBean data, int d) {
                super.onSuccess(data, d);
                mData.remove(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 重发草稿
     *
     * @param position
     */
    private void resend(final int position) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.caogaoCf);
        commonOkhttp.putParams("f_id", mData.get(position).getF_id());
        commonOkhttp.putCallback(new MyGenericsCallback<EmptyResultBean>(this) {
            @Override
            public void onSuccess(EmptyResultBean data, int d) {
                super.onSuccess(data, d);
                getData();
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }
}
