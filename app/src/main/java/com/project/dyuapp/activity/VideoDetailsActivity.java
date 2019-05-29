package com.project.dyuapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CommentAdapter;
import com.project.dyuapp.adapter.VideoRelevantAdapter;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.ForumCommentsEntity;
import com.project.dyuapp.entity.ForumCommentsListEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.entity.NewestDetailBean;
import com.project.dyuapp.entity.SuccessBean;
import com.project.dyuapp.entity.VideoListBean;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.ScreenManager;
import com.project.dyuapp.myutils.ShareUtil;
import com.project.dyuapp.myutils.ToastUtils;
import com.project.dyuapp.myviews.ListViewForScrollView;
import com.umeng.socialize.UMShareAPI;
import com.youku.cloud.player.PlayerListener;
import com.youku.cloud.player.VideoDefinition;
import com.youku.cloud.player.YoukuPlayerView;
import com.youku.cloud.player.YoukuUIListener;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author 田亭亭
 * @description 视频详情
 * @created at 2017/12/5 0005
 * @change
 */
public class VideoDetailsActivity extends AutoLayoutActivity {

    /*  @Bind(R.id.video_details_web)
      WebView web;*/
    @Bind(R.id.video_details_ypv)
    YoukuPlayerView youkuPlayerView;

    @Bind(R.id.video_details_tv_content)
    TextView tvContent;
    @Bind(R.id.video_details_tv_name)
    TextView tvName;
    @Bind(R.id.video_details_tv_brief_introduction)
    TextView tvBriefIntroduction;
    @Bind(R.id.video_details_lv)
    ListViewForScrollView videoDetailsLv;
    @Bind(R.id.video_details_prsv)
    PullToRefreshScrollView videoDetailsPrsv;
    @Bind(R.id.video_detail_civ1)
    PorterShapeImageView videoDetailCiv1;
    @Bind(R.id.video_detail_civ2)
    PorterShapeImageView videoDetailCiv2;
    @Bind(R.id.video_detail_civ3)
    PorterShapeImageView videoDetailCiv3;
    @Bind(R.id.video_detail_civ4)
    PorterShapeImageView videoDetailCiv4;
    @Bind(R.id.video_detail_civ5)
    PorterShapeImageView videoDetailCiv5;
    @Bind(R.id.video_detail_tv_no_zan)
    TextView videoDetailTvNoZan;
    @Bind(R.id.video_detail_lvfsv_comment)
    ListViewForScrollView lvfsvComment;
    @Bind(R.id.video_detail_fabulous_tv)
    TextView fabulousTv;
    @Bind(R.id.video_detail_content_tv)
    TextView contentTv;
    @Bind(R.id.video_details_edt_input_content)
    EditText videoDetailsEdt;
    @Bind(R.id.video_detail_fabulous_rl)
    RelativeLayout videoDetailFabulousRl;
    @Bind(R.id.video_detail_send_rl)
    RelativeLayout videoDetailSendRl;
    @Bind(R.id.video_detail_tv_all)
    TextView tvAll;
    @Bind(R.id.video_detail_rl_allcomment)
    RelativeLayout rlAllcomment;
    @Bind(R.id.video_details_ll_bottom)
    LinearLayout llComment;

    @Bind(R.id.video_details_iv)
    ImageView iv;

    String videoUrl = "";
    @Bind(R.id.video_details_player_cover_rl)
    RelativeLayout videoDetailsPlayerCoverRl;
    private List<VideoListBean> relevantVideoList;
    private CommentAdapter mAdapterComment;

    //    InWebView inWeb;
    private String articleId = "";
    private String content = "";
    private String returnId = "";
    private String type = "1";
    private int page = 1;
    private List<ForumCommentsEntity> mDataComments;
    private VideoRelevantAdapter mRelevantAdapter;
    private String thumb = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        ButterKnife.bind(this);
        articleId = getIntent().getStringExtra("article_id");

        // 初始化播放器
        youkuPlayerView.attachActivity(this);
        youkuPlayerView.setPreferVideoDefinition(VideoDefinition.VIDEO_HD);
        youkuPlayerView.setPlayerListener(new MyPlayerListener());
        youkuPlayerView.setUseOrientation(true);
        //设置对横竖屏按钮的监听
        youkuPlayerView.setUIListener(new YoukuUIListener() {
            @Override
            public void onBackBtnClick() {
                goBack();
            }

            @Override
            public void onFullBtnClick() {
                if (youkuPlayerView.isFullScreen()) {
                    youkuPlayerView.goSmallScreen();
                    //隐藏评价布局
                    llComment.setVisibility(View.VISIBLE);
                } else {
                    youkuPlayerView.goFullScreen();
                    //显示评价布局
                    llComment.setVisibility(View.GONE);
                }
            }
        });
        // 播放器视频


        okhttpNewestDetail();
        okhttpVideosRelevant();

        videoDetailsLv.setFocusable(false);

        setRecommendVideoView();//相关视频
        setCommentsView();//评价

        okHttpForumComments();
    }


    // 添加播放器的监听器
    private class MyPlayerListener extends PlayerListener {
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 必须重写的onPause()
        youkuPlayerView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 必须重写的onResume()
        youkuPlayerView.onResume();
    }

    //相关视频
    private void setRecommendVideoView() {
        relevantVideoList = new ArrayList<>();
        mRelevantAdapter = new VideoRelevantAdapter(relevantVideoList, VideoDetailsActivity.this, R.layout.item_relevant_video);
        videoDetailsLv.setAdapter(mRelevantAdapter);
        videoDetailsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(VideoDetailsActivity.this, VideoDetailsActivity.class)
//                        .putExtra("article_id", );
                articleId = relevantVideoList.get(position).getArticle_id();
                okhttpNewestDetail();
                relevantVideoList.clear();
                okhttpVideosRelevant();
                page = 1;
                okHttpForumComments();
            }
        });
        videoDetailsPrsv.setMode(PullToRefreshBase.Mode.BOTH);
        videoDetailsPrsv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page = 1;
                okHttpForumComments();
                okhttpNewestDetail();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                okHttpForumComments();
            }
        });
    }

    //评价
    private void setCommentsView() {
        mDataComments = new ArrayList<>();
        mAdapterComment = new CommentAdapter(mDataComments, this);
        lvfsvComment.setAdapter(mAdapterComment);
        mAdapterComment.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onReplyClick(View view, int position) {
                type = "2";
                videoDetailsEdt.setHint("写下你的回复...");
                returnId = mDataComments.get(position).getC_id();
                videoDetailsEdt.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        videoDetailsEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    handled = true;
                    okHttpForumComments();
                    videoDetailsEdt.setText("");
                    // 滑动到界面底部
                    videoDetailsPrsv.post(new Runnable() {
                        @Override
                        public void run() {
                            videoDetailsPrsv.getRefreshableView().fullScroll(View.FOCUS_DOWN);
                        }
                    });
                     /*隐藏软键盘*/
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(VideoDetailsActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                }
                return handled;
            }
        });

        //编辑框输入时显示发送按钮,无输入内容时显示点赞和收藏按钮
        videoDetailsEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    videoDetailFabulousRl.setVisibility(View.VISIBLE);
                    videoDetailSendRl.setVisibility(View.GONE);
                } else {
                    videoDetailFabulousRl.setVisibility(View.GONE);
                    videoDetailSendRl.setVisibility(View.VISIBLE);
                }
                videoDetailsEdt.setHint("写下你的评论...");
            }
        });
    }

    /**
     * 首页-视频详情接口
     */
    private void okhttpNewestDetail() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.newestDetail);
        commonOkhttp.putParams("article_id", articleId);
        commonOkhttp.putCallback(new MyGenericsCallback<NewestDetailBean>(VideoDetailsActivity.this) {
            @Override
            public void onSuccess(NewestDetailBean data, int d) {
                super.onSuccess(data, d);
                try {
                    if (data != null) {
                        videoUrl = data.getContent();
                        String introduction = data.getIntroduction();
                        String name = data.getName();
                        thumb = data.getThumb();
                        String title = data.getTitle();
                        int isZan = data.getIs_zan();//是否点赞
                        String count = data.getCount();//点赞数量
                        String contentSum = data.getContentSum();//评论数量
                        GlideUtils.loadImageView(VideoDetailsActivity.this,
                                thumb,
                                iv);
                        if (!TextUtils.isEmpty(introduction)) {
                            tvBriefIntroduction.setText(introduction);
                        }
                        if (!TextUtils.isEmpty(name)) {
                            tvName.setText(name);
                        }
                        if (!TextUtils.isEmpty(title)) {
                            tvContent.setText(title);
                        }
                        if (videoDetailsPrsv != null) {
                            videoDetailsPrsv.onRefreshComplete();
                        }
                        if (!TextUtils.isEmpty(String.valueOf(isZan)))
                            if (isZan == 1) {
                                // 1.已点赞
                                fabulousTv.setCompoundDrawablesWithIntrinsicBounds(null, VideoDetailsActivity.this.getResources().getDrawable(R.mipmap.yule_dz_selected), null, null);

                            } else if (isZan == 2) {
                                //2.未点赞
                                fabulousTv.setCompoundDrawablesWithIntrinsicBounds(null, VideoDetailsActivity.this.getResources().getDrawable(R.mipmap.yule_dz_unselected), null, null);
                            }
                        if (!TextUtils.isEmpty(count)) {
                            fabulousTv.setText(count);
                        }
                        if (!TextUtils.isEmpty(contentSum)) {
                            contentTv.setText(contentSum);
                        }
                        List<String> memberListHeadpic = data.getMember_list_headpic();
                        if (memberListHeadpic != null && memberListHeadpic.size() != 0) {
                            for (int i = 0; i < memberListHeadpic.size(); i++) {
                                ImageView iv = videoDetailCiv1;
                                if (i == 0) {
                                    iv = videoDetailCiv1;
                                    videoDetailCiv1.setVisibility(View.VISIBLE);
                                } else if (i == 1) {
                                    iv = videoDetailCiv2;
                                    videoDetailCiv2.setVisibility(View.VISIBLE);
                                } else if (i == 2) {
                                    iv = videoDetailCiv3;
                                    videoDetailCiv3.setVisibility(View.VISIBLE);
                                } else if (i == 3) {
                                    iv = videoDetailCiv4;
                                    videoDetailCiv4.setVisibility(View.VISIBLE);
                                } else if (i == 4) {
                                    iv = videoDetailCiv5;
                                    videoDetailCiv5.setVisibility(View.VISIBLE);
                                }
                                GlideUtils.loadImageViewHead(VideoDetailsActivity.this,
                                        HttpUrl.IMAGE_URL + memberListHeadpic.get(i),
                                        iv);//点赞头像
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(VideoDetailsActivity.this).showMessage(VideoDetailsActivity.this.getResources().getString(R.string.data_parsing_failed));
                }
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 相关-视频接口
     */
    private void okhttpVideosRelevant() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.videosRelevant);
        commonOkhttp.putParams("article_id", articleId);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.VideoListEntity>(VideoDetailsActivity.this) {
            @Override
            public void onSuccess(NetBean.VideoListEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    if (data != null && data.size() != 0) {
                        relevantVideoList.addAll(data);
                    }
                    mRelevantAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(VideoDetailsActivity.this).showMessage(VideoDetailsActivity.this.getResources().getString(R.string.data_parsing_failed));
                }
            }
        });
        commonOkhttp.Execute();
    }

    @OnClick({R.id.video_details_iv, R.id.video_details_img_share, R.id.video_detail_tv_all, R.id.video_detail_rl_allcomment
            , R.id.video_detail_fabulous_rl, R.id.video_details_edt_input_content
            , R.id.video_detail_tv_send_rl, R.id.video_detail_send_rl
            , R.id.video_detail_content_tv, R.id.video_detail_fabulous_tv, R.id.video_detail_rl_dianzan
            , R.id.video_details_player_icon_iv, R.id.video_details_player_iv_back})
    public void onViewClicked(View view) {
        content = videoDetailsEdt.getText().toString();
        switch (view.getId()) {
//            case R.id.video_details_iv:
//                iv.setVisibility(View.GONE);
//                youkuPlayerView.setVisibility(View.VISIBLE);
//                if (!TextUtils.isEmpty(videoUrl)) {
//                    youkuPlayerView.playYoukuVideo(videoUrl);
//                }
//                break;
            case R.id.video_details_img_share:
                if (!TextUtils.isEmpty(SPUtils.getPreference(VideoDetailsActivity.this, "userid"))) {
                    //已登录
                    new ShareUtil(VideoDetailsActivity.this, VideoDetailsActivity.this, tvContent.getText().toString(), " ", HttpUrl.URL + "/Home/Wechat/videoDetail?gid=" + articleId, thumb);
                } else {
                    //未登录
                    startActivity(new Intent(VideoDetailsActivity.this, LoginActivity.class));
                }
                break;
            case R.id.video_detail_tv_all:
            case R.id.video_detail_rl_allcomment:
                //全部评论
                startActivity(new Intent(VideoDetailsActivity.this, AllCommentActivity.class)
                        .putExtra("object_id", articleId)
                        .putExtra("whereFrom", "3")
                        .putExtra("object_type", "2"));
                break;
            case R.id.video_detail_fabulous_tv:
            case R.id.video_detail_fabulous_rl:
                //点赞
                if (!TextUtils.isEmpty(SPUtils.getPreference(VideoDetailsActivity.this, "userid"))) {
                    //已登录
                    okHttpDianZanRun();
                } else {
                    //未登录
                    startActivity(new Intent(VideoDetailsActivity.this, LoginActivity.class));
                }
                break;
            case R.id.video_detail_tv_send_rl:
            case R.id.video_detail_send_rl:
                //发送评论
                if (!TextUtils.isEmpty(SPUtils.getPreference(VideoDetailsActivity.this, "userid"))) {
                    if (TextUtils.isEmpty(content)) {
                        ToastUtils.getInstance(VideoDetailsActivity.this).showMessage("请写下你的评论");
                        return;
                    }
                    //已登录
                    okHttpActionComments();
                } else {
                    //未登录
                    startActivity(new Intent(VideoDetailsActivity.this, LoginActivity.class));
                }
                break;
            case R.id.video_detail_rl_dianzan:
                //查看全部
                if (!TextUtils.isEmpty(SPUtils.getPreference(VideoDetailsActivity.this, "userid"))) {
                    startActivity(new Intent(VideoDetailsActivity.this, DianzanPeopleActivity.class)
                            .putExtra("f_id", articleId)
                            .putExtra("object_type", "2")
                    );
                } else {
                    //未登录
                    startActivity(new Intent(VideoDetailsActivity.this, LoginActivity.class));
                }
                break;
            case R.id.video_details_player_icon_iv:
                //开始播放
                youkuPlayerView.setVisibility(View.VISIBLE);
                videoDetailsPlayerCoverRl.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(videoUrl)) {
                    youkuPlayerView.playYoukuVideo(videoUrl);
                }
                break;
            case R.id.video_details_player_iv_back:
                //返回
                ScreenManager.getInstance().removeActivity(VideoDetailsActivity.this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goBack();
    }

    private void goBack() {
        if (youkuPlayerView.isFullScreen()) {
            youkuPlayerView.goSmallScreen();
            //隐藏评价布局
            llComment.setVisibility(View.VISIBLE);
        } else {
            finish();
        }
    }

    /**
     * 视频详情-点赞的网络请求
     */
    public void okHttpDianZanRun() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.dianzanRun);
        commonOkhttp.putParams("object_type", "2");//1帖子 2视频  3钓场  4渔具店
        commonOkhttp.putParams("object_id", articleId);//点评对象ID
        commonOkhttp.putCallback(new MyGenericsCallback<SuccessBean>(this) {
            @Override
            public void onSuccess(SuccessBean data, int d) {
                super.onSuccess(data, d);
                try {
                    if (data.getState() == 200) {
                        if (data.getIs_dianzan() == 1) {
                            // 1.已点赞
                            fabulousTv.setCompoundDrawablesWithIntrinsicBounds(null, VideoDetailsActivity.this.getResources().getDrawable(R.mipmap.yule_dz_selected), null, null);
                        } else if (data.getIs_dianzan() == 2) {
                            //2.未点赞
                            fabulousTv.setCompoundDrawablesWithIntrinsicBounds(null, VideoDetailsActivity.this.getResources().getDrawable(R.mipmap.yule_dz_unselected), null, null);
                        }
                        okhttpNewestDetail();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(VideoDetailsActivity.this).showMessage(VideoDetailsActivity.this.getResources().getString(R.string.data_parsing_failed));

                }
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 视频详情-评论接口
     */
    public void okHttpActionComments() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.actionComments);
        commonOkhttp.putParams("object_type", "2");//1帖子 2视频  3钓场  4渔具店
        commonOkhttp.putParams("object_id", articleId);//点评对象ID
        commonOkhttp.putParams("content", content);
        if (TextUtils.equals(type, "1")) {

        } else if (TextUtils.equals(type, "2")) {
            commonOkhttp.putParams("return_id", returnId);

        }
        commonOkhttp.putCallback(new MyGenericsCallback<SuccessBean>(this) {
            @Override
            public void onSuccess(SuccessBean data, int d) {
                super.onSuccess(data, d);
                try {
                    if (data.getState() == 200) {
                        videoDetailsEdt.setText("");
                        mDataComments.clear();
                        page = 1;
                        okHttpForumComments();
                        okhttpNewestDetail();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(VideoDetailsActivity.this).showMessage(VideoDetailsActivity.this.getResources().getString(R.string.data_parsing_failed));

                }
            }
        });
        commonOkhttp.Execute();
    }

    //2-1视频-评论列表
    public void okHttpForumComments() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.forumComments);
        commonOkhttp.putParams("object_type", "2");//1帖子 2视频  3钓场  4渔具店
        commonOkhttp.putParams("object_id", articleId);//点评对象ID
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putCallback(new MyGenericsCallback<ForumCommentsListEntity>(this) {
            @Override
            public void onSuccess(ForumCommentsListEntity data, int d) {
                super.onSuccess(data, d);
//                if (data == null || data.size() == 0) {
//                    tvAll.setText("暂无");
//                    rlAllcomment.setClickable(false);
//                } else {
//                    rlAllcomment.setClickable(true);
//                    tvAll.setText("查看全部");
//                    mDataComments.addAll(data);
//                    mAdapterComment.notifyDataSetChanged();
//                }
                try {
                    if (page == 1) {
                        mDataComments.clear();
                    }
                    if (data != null && data.size() != 0) {
                        mDataComments.addAll(data);
                        ++page;
                    } else {
                        if (page == 1) {
//                            ToastUtils.getInstance(VideoDetailsActivity.this).showMessage(getString(R.string.list_no_data));
                        } else {
                            ToastUtils.getInstance(VideoDetailsActivity.this).showMessage(getString(R.string.list_bottom));
                        }
//                        commonOkhttp.showNoData(VideoDetailsActivity.this, page);
                    }
                    mAdapterComment.notifyDataSetChanged();
                    if (videoDetailsPrsv != null) {
                        videoDetailsPrsv.onRefreshComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(VideoDetailsActivity.this).showMessage(VideoDetailsActivity.this.getResources().getString(R.string.data_parsing_failed));
                }
            }
        });
        commonOkhttp.Execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        // 必须重写的onDestroy()
//        youkuPlayerView.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
