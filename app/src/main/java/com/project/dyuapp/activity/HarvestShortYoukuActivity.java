package com.project.dyuapp.activity;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshYoukuListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.HarvestShortYoukuAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.HarvestShortYoukuEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @创建者 任伟
 * @创建时间 2017/12/25 16:42
 * @描述 ${渔乐短片}
 */
public class HarvestShortYoukuActivity extends MyBaseActivity {

    @Bind(R.id.new_aa_lv_new_list)
    PullToRefreshYoukuListView videoListView;
//    @Bind(R.id.new_aa_lv_new_list)
//    ScrollListView videoListView;

    private HarvestShortYoukuAdapter listPlayerAdapter;
    private List<HarvestShortYoukuEntity> playerCovers;
    private int currentinx = 0;
    private boolean isHorizontal;
    private int page = 1;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_list_view_test);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {

        setTvTitle("渔乐短片");
        setIvBack();
        listView = videoListView.getRefreshableView();
        playerCovers = new ArrayList<>();
        listPlayerAdapter = new HarvestShortYoukuAdapter(this, playerCovers);
        videoListView.setAdapter(listPlayerAdapter);
        videoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listPlayerAdapter.onStop();
                startActivity(new Intent(HarvestShortYoukuActivity.this, VideoDetailsActivity.class).putExtra("article_id", playerCovers.get(i - 1).getArticle_id()));
            }
        });
        videoListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                getHttpList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getHttpList();
            }
        });

//        getIvBack().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isHorizontal) {
//                    ViewGroup listContainer = (ViewGroup) listPlayerAdapter.getCurrentYoukuPlayerView().getParent();
//                    listContainer.getLayoutParams().width = SPUtils.getRealWidth();
//                    listContainer.getLayoutParams().height = SPUtils.getRealHeight();
//                    listContainer.setLayoutParams(listContainer.getLayoutParams());
//                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (videoListView != null) {
//                                videoListView.setSelection(currentinx);
//                                videoListView.setCanScroll(false);
//                            }
//                        }
//                    }, 200);
//                    getRlTitle().setVisibility(View.VISIBLE);
//                } else {
//                    finish();
//                }
//            }
//        });

        getHttpList();
    }

    public void releaseLastPlayer(int inx) {
        for (HarvestShortYoukuEntity co : playerCovers) {
            co.setHasplayer(false);
        }
        playerCovers.get(inx).setHasplayer(true);
        currentinx = inx;
        listPlayerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getBaseContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ViewGroup listContainer = (ViewGroup) listPlayerAdapter.getCurrentYoukuPlayerView().getParent();
            listContainer.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            listContainer.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            listContainer.setLayoutParams(listContainer.getLayoutParams());
            videoListView.setCanScroll(true);
            listView.setEnabled(true);

            getRlTitle().setVisibility(View.VISIBLE);
            isHorizontal = false;
            videoListView.setMode(PullToRefreshBase.Mode.BOTH);//可以上下啦刷新
        } else if (getBaseContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ViewGroup listContainer = (ViewGroup) listPlayerAdapter.getCurrentYoukuPlayerView().getParent();
            listContainer.getLayoutParams().width = SPUtils.getRealWidth();
            listContainer.getLayoutParams().height = SPUtils.getRealHeight();
            listContainer.setLayoutParams(listContainer.getLayoutParams());

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    if (videoListView != null) {
                        listView.setSelection(currentinx + 1);
                        videoListView.setCanScroll(false);
                        listView.setEnabled(false);
                        listView.setClickable(false);
                    }
                }
            }, 200);
            videoListView.setMode(PullToRefreshBase.Mode.DISABLED);//禁用刷新
            getRlTitle().setVisibility(View.GONE);
            isHorizontal = true;
        }
    }

    public void getHttpList() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.INDEX_SHORT_VIDEOS);
        commonOkhttp.putParams("cc_id", "8");
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.HarvestShortYoukuListEntity>(this) {
            @Override
            public void onSuccess(NetBean.HarvestShortYoukuListEntity data, int d) {
                super.onSuccess(data, d);
                videoListView.onRefreshComplete();
                if (page == 1) {
                    playerCovers.clear();
                }
                playerCovers.addAll(data);
                listPlayerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                videoListView.onRefreshComplete();
                Toast.makeText(HarvestShortYoukuActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        commonOkhttp.Execute();
    }

    @Override
    protected void onPause() {
        if (listPlayerAdapter != null) {
            listPlayerAdapter.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (listPlayerAdapter != null) {
            listPlayerAdapter.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (listPlayerAdapter != null) {
            listPlayerAdapter.onDestroy();
        }
        super.onDestroy();
    }

//    /**
//     * 监听Back键按下事件
//     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            if (!isHorizontal) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
//            } else {
//                finish();
//                return false;
//            }
//        } else {
//            return super.onKeyDown(keyCode, event);
//        }
//        return false;
//    }
}
