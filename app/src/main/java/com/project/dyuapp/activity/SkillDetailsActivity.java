package com.project.dyuapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.lzy.imagepicker.bean.ImageItem;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CommentAdapter;
import com.project.dyuapp.adapter.PostDetailAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.callback.StateEntity;
import com.project.dyuapp.entity.ContentBean;
import com.project.dyuapp.entity.EmptyResultBean;
import com.project.dyuapp.entity.ForumCommentsEntity;
import com.project.dyuapp.entity.ForumCommentsListEntity;
import com.project.dyuapp.entity.ForumDetailEntity;
import com.project.dyuapp.entity.MemberInfoEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyPicMethodUtil;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.ShareUtil;
import com.project.dyuapp.myutils.ToastUtils;
import com.project.dyuapp.myutils.Utils;
import com.project.dyuapp.myutils.WindowUtils;
import com.project.dyuapp.myviews.CustomDialog;
import com.project.dyuapp.myviews.ListViewForScrollView;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


/**
 * @describe：首页-技巧-技巧详情&&首页-渔获-渔获详情
 * @author：
 * @createdate：2017/11/28 16:34
 */
public class SkillDetailsActivity extends MyBaseActivity {

    @Bind(R.id.skill_detail_tv_title)
    TextView tvTitle;
    @Bind(R.id.skill_detail_tv_name)
    TextView tvName;
    @Bind(R.id.skill_detail_tv_grade)
    TextView tvGrade;
    @Bind(R.id.skill_detail_tv_num)
    TextView tvNum;
    @Bind(R.id.skill_detail_iv_head)
    PorterShapeImageView ivHead;
    @Bind(R.id.skill_detail_iv_attend)
    ImageView ivAttend;

    @Bind(R.id.skill_details_describe)
    LinearLayout skillDetailsDescribe;//详情布局
 /*   @Bind(R.id.skill_detail_lv)
    ListViewForScrollView skillDetailLv;*///帖子内容

    @Bind(R.id.skill_detail_lvfsv_comment)
    ListViewForScrollView lvfsvComment;//评论列表
    @Bind(R.id.skill_detail_fabulous_iv)
    ImageView ivZan;//赞
    @Bind(R.id.skill_detail_collection_iv)
    ImageView ivCollection;//收藏
    @Bind(R.id.skill_details_edt)
    EditText edtComment;//评论输入框
    @Bind(R.id.skill_details_sv)
    PullToRefreshScrollView skill_details_sv;
    @Bind(R.id.skill_detail_rl_allcomment)
    RelativeLayout rlAllcomment;
    @Bind(R.id.skill_detail_tv_all)
    TextView tvCommentAll;

    @Bind(R.id.skill_detail_tv_no_zan)
    TextView tvNoZan;

    @Bind(R.id.skill_detail_ll_right)
    LinearLayout skillDetailLlRight;
    @Bind(R.id.skill_detail_tv_send)
    TextView skillDetailTvSend;

    @Bind(R.id.skill_details_tv_get_bait)
    TextView tvGetBait;
    @Bind(R.id.skill_details_tv_get_seed)
    TextView tvGetSeed;
    @Bind(R.id.skill_details_tv_get_type)
    TextView tvGeType;
    @Bind(R.id.skill_details_tv_get_data)
    TextView tvGetData;

    @Bind(R.id.skill_detail_civ1)
    PorterShapeImageView skillDetailCiv1;
    @Bind(R.id.skill_detail_civ2)
    PorterShapeImageView skillDetailCiv2;
    @Bind(R.id.skill_detail_civ3)
    PorterShapeImageView skillDetailCiv3;
    @Bind(R.id.skill_detail_civ4)
    PorterShapeImageView skillDetailCiv4;
    @Bind(R.id.skill_detail_civ5)
    PorterShapeImageView skillDetailCiv5;

    @Bind(R.id.skill_detail_ll_content)
    LinearLayout llContent;

    @Bind(R.id.skill_detail_dashang1)
    PorterShapeImageView skillDetailDashang1;
    @Bind(R.id.skill_detail_dashang2)
    PorterShapeImageView skillDetailDashang2;
    @Bind(R.id.skill_detail_dashang3)
    PorterShapeImageView skillDetailDashang3;
    @Bind(R.id.skill_detail_dashang4)
    PorterShapeImageView skillDetailDashang4;
    @Bind(R.id.skill_detail_dashang5)
    PorterShapeImageView skillDetailDashang5;
    @Bind(R.id.skill_detail_rl_dashang)
    RelativeLayout rlAlldashang;
    @Bind(R.id.skill_detail_tv_no_dashang)
    TextView tvNoDaShang;

    private List<ContentBean> mData = new ArrayList<>();
    private PostDetailAdapter mAdapter;

    private CommentAdapter mAdapterComment;
    private List<ForumCommentsEntity> mDataComments = new ArrayList<>();
    private boolean isYuhuo;//是否是渔获
    private boolean isNotice = true;//是否通知作者
    private String fId = "";
    private String memberId = "";
    private CustomDialog dialog;
    private String money;//金币数量
    private String count;//打赏金币数量
    private String desc;//打赏理由
    private String isInform = "1";//是否通知作者 1是2否
    private int type = 1;//1.评论  2.回复
    private int replyposition;
    private int page = 1;
    private String shareContent = "";
    private String shareImg = "";
    private ForumDetailEntity data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_skill_details);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("id"))) {
            fId = getIntent().getStringExtra("id");
            okHttpForumDetail();
            okHttpForumComments();
        }
        initData();
        initView();
    }

    private void initView() {
        setTvTitle("详情");
        setIvBack();
        getIvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享
                if (mData.size() > 0) {
                    for (int i = 0; i < mData.size(); i++) {
                        if (!TextUtils.isEmpty(mData.get(i).getStr_content())) {
                            shareContent = mData.get(i).getStr_content();
                            break;
                        }
                    }
                    for (int j = 0; j < mData.size(); j++) {
                        if (!TextUtils.isEmpty(mData.get(j).getStr_imgs())) {
                            shareImg = mData.get(j).getStr_imgs();
                            break;
                        }
                    }
                }
                String city = SPUtils.getPreference(SkillDetailsActivity.this, "selCity");
                if (isYuhuo) {
                    //渔获
                    new ShareUtil(SkillDetailsActivity.this,
                            SkillDetailsActivity.this,
                            tvTitle.getText().toString(),
                            shareContent,
                            HttpUrl.URL + "/Home/Wechat/fisheryDetail?gid=" + fId + "&cityid=" + city,
                            shareImg);
                } else {
                    new ShareUtil(SkillDetailsActivity.this,
                            SkillDetailsActivity.this,
                            tvTitle.getText().toString(),
                            shareContent,
                            HttpUrl.URL + "/Home/Wechat/skillDetail?gid=" + fId + "&cityid=" + city,
                            shareImg);
                }

            }
        });

        skill_details_sv.setMode(PullToRefreshBase.Mode.BOTH);
        skill_details_sv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //刷新
                okHttpForumDetail();
                page = 1;
                okHttpForumComments();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //加载
                okHttpForumComments();
            }
        });
    }

    private void initData() {
        // 若是非渔获页面跳转，隐藏布局
        skillDetailsDescribe.setVisibility(View.GONE);
        mAdapter = new PostDetailAdapter(mData, this);
       /* skillDetailLv.setFocusable(false);//默认不会滑动到底部
        skillDetailLv.setAdapter(mAdapter);*/
        mAdapterComment = new CommentAdapter(mDataComments, this);
        lvfsvComment.setAdapter(mAdapterComment);
        edtComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    handled = true;
                    //发送
                    if (!TextUtils.isEmpty(SPUtils.getPreference(SkillDetailsActivity.this, "userid"))) {
                        //已登录
                        if (TextUtils.isEmpty(edtComment.getText().toString())) {
                            showMessage("请写下你的评论");
                        } else {
                            sendComment();
                        }

                    } else {
                        //未登录
                        goToActivity(LoginActivity.class);
                    }
                }
                return handled;
            }
        });

        //编辑框输入时显示发送按钮,无输入内容时显示点赞和收藏按钮
        edtComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(edtComment.getText().toString())) {
                    skillDetailLlRight.setVisibility(View.VISIBLE);
                    skillDetailTvSend.setVisibility(View.GONE);
                } else {
                    skillDetailLlRight.setVisibility(View.GONE);
                    skillDetailTvSend.setVisibility(View.VISIBLE);
                }
            }
        });

        lvfsvComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                type = 2;
                replyposition = i;
                edtComment.setHint("写下你的回复...");
                edtComment.requestFocus();
                 /*吊起键盘*/
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    @OnClick({R.id.skill_detail_iv_head, R.id.skill_detail_iv_attend, R.id.skill_detail_tv_support, R.id.skill_detail_rl_dianzan,R.id.skill_detail_rl_dashang, R.id.skill_detail_rl_allcomment, R.id.skill_detail_fabulous_rl, R.id.skill_detail_collection_rl, R.id.skill_detail_tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skill_detail_iv_head:
                //跳转主页
                if (!TextUtils.isEmpty(SPUtils.getPreference(SkillDetailsActivity.this, "userid"))) {
                    //已登录
                    startActivity(new Intent(this, FansMainPagerActivity.class)
                            .putExtra("id", memberId));
                } else {
                    //未登录
                    goToActivity(LoginActivity.class);
                }
                break;
            case R.id.skill_detail_iv_attend:
                //关注
                if (!TextUtils.isEmpty(SPUtils.getPreference(SkillDetailsActivity.this, "userid"))) {
                    //已登录
                    okHttpAttention();
                } else {
                    //未登录
                    goToActivity(LoginActivity.class);
                }
                break;
            case R.id.skill_detail_tv_support:
                //赞赏支持
                if (!TextUtils.isEmpty(SPUtils.getPreference(SkillDetailsActivity.this, "userid"))) {
                    //已登录
                    if (memberId.equals(SPUtils.getPreference(SkillDetailsActivity.this, "userid"))) {
                        showMessage("不能给自己打赏");
                        return;
                    }
                    okhttpMemberInfo();
                } else {
                    //未登录
                    goToActivity(LoginActivity.class);
                }
                break;

            case R.id.skill_detail_rl_dashang:
                //打赏
//                if (!TextUtils.isEmpty(SPUtils.getPreference(SkillDetailsActivity.this, "userid"))) {
//                    //已登录
//                    startActivity(new Intent(SkillDetailsActivity.this, DianzanPeopleActivity.class)
//                            .putExtra("f_id", fId)
//                            .putExtra("object_type", "1"));
//                } else {
//                    //未登录
//                    goToActivity(LoginActivity.class);
//                }
                break;

            case R.id.skill_detail_rl_dianzan:
                //点赞
                if (!TextUtils.isEmpty(SPUtils.getPreference(SkillDetailsActivity.this, "userid"))) {
                    //已登录
                    startActivity(new Intent(SkillDetailsActivity.this, DianzanPeopleActivity.class)
                            .putExtra("f_id", fId)
                            .putExtra("object_type", "1"));
                } else {
                    //未登录
                    goToActivity(LoginActivity.class);
                }
                break;
            case R.id.skill_detail_rl_allcomment:
                if (!TextUtils.isEmpty(SPUtils.getPreference(SkillDetailsActivity.this, "userid"))) {
                    //已登录
                    startActivity(new Intent(SkillDetailsActivity.this, AllCommentActivity.class)
                            .putExtra("object_id", fId)
                            .putExtra("whereFrom", "2")
                            .putExtra("object_type", "1"));
                } else {
                    //未登录
                    goToActivity(LoginActivity.class);
                }
                break;
            case R.id.skill_detail_fabulous_rl://赞
                if (!TextUtils.isEmpty(SPUtils.getPreference(SkillDetailsActivity.this, "userid"))) {
                    //已登录
                    dianzanRun();
                } else {
                    //未登录
                    goToActivity(LoginActivity.class);
                }
                break;
            case R.id.skill_detail_collection_rl://收藏
                if (!TextUtils.isEmpty(SPUtils.getPreference(SkillDetailsActivity.this, "userid"))) {
                    //已登录
                    collect();
                } else {
                    //未登录
                    goToActivity(LoginActivity.class);
                }
                break;
            case R.id.skill_detail_tv_send:
                //发送
                if (!TextUtils.isEmpty(SPUtils.getPreference(SkillDetailsActivity.this, "userid"))) {
                    //已登录
                    if (TextUtils.isEmpty(edtComment.getText().toString())) {
                        showMessage("请写下你的评论");
                    } else {
                        sendComment();
                    }
                } else {
                    //未登录
                    goToActivity(LoginActivity.class);
                }
                break;
        }
    }

    //赞赏支持
    private void showSupport() {
        dialog = new CustomDialog(this, R.layout.dialog_admire_support, R.style.CustomDialogTheme);
        dialog.setCanceledOnTouchOutside(true);
        TextView tvMygold = (TextView) dialog.findViewById(R.id.dialog_support_tv_mygold);
        final EditText tvGold = (EditText) dialog.findViewById(R.id.dialog_support_tv_gold);
        final EditText edt = (EditText) dialog.findViewById(R.id.dialog_support_edt);
        final ImageView ivAuto = (ImageView) dialog.findViewById(R.id.dialog_support_iv);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.dialog_support_tv_cancel);
        TextView tvConfirm = (TextView) dialog.findViewById(R.id.dialog_support_tv_confirm);
        if (!TextUtils.isEmpty(money)) {
            tvMygold.setText(money);//我的金币
        } else {
            tvMygold.setText("0");
            return;
        }
        //是否通知作者 1是2否
        ivAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNotice) {
                    isNotice = false;
                    ivAuto.setImageResource(R.mipmap.sign_selected_not);
                    isInform = "2";
                } else {
                    isNotice = true;
                    ivAuto.setImageResource(R.mipmap.sign_selected);
                    isInform = "1";
                }
            }
        });
        //取消
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //确认
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = tvGold.getText().toString();//打赏金币数量
                desc = edt.getText().toString();//打赏理由
                if (TextUtils.isEmpty(count)) {
                    showMessage("请输入打赏金币数量");
                    return;
                }
                if (Integer.parseInt(money) < Integer.parseInt(count)) {
                    showMessage("金币数量不足");
                    return;
                }
                if (money.compareTo("0") <= 0) {
                    showMessage("金币数量不足");
                    return;
                }
                okhttpFishingReward();
            }
        });
        dialog.show();
    }

    //5获取当前用户金币、积分数量
    private void okhttpMemberInfo() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.memberInfo);
        commonOkhttp.putCallback(new MyGenericsCallback<MemberInfoEntity>(SkillDetailsActivity.this) {
            @Override
            public void onSuccess(MemberInfoEntity data, int d) {
                super.onSuccess(data, d);
                money = data.getMoney();//金币数量
                showSupport();
            }
        });
        commonOkhttp.Execute();
    }

    //6首页-渔获-详情-赞赏支持
    private void okhttpFishingReward() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.fishingReward);
        commonOkhttp.putParams("coin_num", count);//打赏金币数量
        commonOkhttp.putParams("desc", desc);//打赏理由
        commonOkhttp.putParams("f_id", fId);//渔获帖子id
        commonOkhttp.putParams("is_inform", isInform);//是否通知作者 1是2否
        commonOkhttp.putCallback(new MyGenericsCallback<EmptyResultBean>(SkillDetailsActivity.this) {
            @Override
            public void onSuccess(EmptyResultBean data, int d) {
                super.onSuccess(data, d);
                dialog.dismiss();
                okHttpForumDetail();
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
            }
        });
        commonOkhttp.Execute();
    }

    //帖子-详情
    public void okHttpForumDetail() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.forumDetail);
        commonOkhttp.putParams("f_id", fId);
        commonOkhttp.putCallback(new MyGenericsCallback<ForumDetailEntity>(this) {
            @Override
            public void onSuccess(ForumDetailEntity data, int d) {
                super.onSuccess(data, d);
                memberId = data.getMember_list_id();
                mData = data.getContent();
                setTopView(data);
                setIconState(data);
                setGetFishView(data);
                setContentView(data);
                setZanHeadView(data);
                setDaShangView(data);
            }
        });
        commonOkhttp.Execute();
    }

    //信息view
    private void setTopView(ForumDetailEntity data) {
        tvTitle.setText(data.getTitle());
        GlideUtils.loadImageViewHead(SkillDetailsActivity.this, HttpUrl.IMAGE_URL + data.getMember_list_headpic(), ivHead);
        tvName.setText(data.getMember_list_nickname());
        tvGrade.setText("Lv." + data.getMember_list_level().getMember_lvl_id());
        tvNum.setText("发布于" + Utils.convertDate(data.getAddtime(), "yyyy年MM月dd日") + " · " +
                data.getConment_sum() + "评论 · " + data.getZan_sum() + "点赞 · " + data.getDashang_sum() + "打赏");
    }

    //渔获view
    private void setGetFishView(ForumDetailEntity data) {
        if (!TextUtils.isEmpty(data.getTop_id())) {
            isYuhuo = data.getTop_id().equals(PublicStaticData.ID_GET_FISH) ? true : false;
            if (isYuhuo) {
                skillDetailsDescribe.setVisibility(View.VISIBLE);
                //渔获
                String erliao = data.getErliao();
                if (TextUtils.isEmpty(erliao)) {
                    erliao = "";
                }
                tvGetBait.setText(Html.fromHtml("<b>饵料:</b>" + erliao));
                String yuzhong = data.getYuzhong();
                if (TextUtils.isEmpty(yuzhong) || yuzhong.equals("false")) {
                    yuzhong = "";
                }
                tvGetSeed.setText(Html.fromHtml("<b>鱼种:</b>" + yuzhong));
                String type = data.getGround_id();
                if (TextUtils.isEmpty(type)) {
                    type = "";
                }
                tvGeType.setText(Html.fromHtml("<b>钓场类型:</b>" + type));
                String time = data.getFishing_time();
                if (TextUtils.isEmpty(time)) {
                    time = "";
                } else {
                    time = Utils.convertDate(time, "yyyy-MM-dd");
                }
                tvGetData.setText(Html.fromHtml("<b>出钓时间:</b>" + time));
            }
        }
    }

    //设置图标状态
    private void setIconState(ForumDetailEntity data) {
        if (!TextUtils.isEmpty(data.getShoucang())) {
            //0未收藏   1已收藏
            if (data.getShoucang().equals("1")) {
                ivCollection.setImageResource(R.mipmap.tab_collect_selected);
            } else {
                ivCollection.setImageResource(R.mipmap.tab_collect_unselected);
            }
        }
        if (!TextUtils.isEmpty(data.getZan())) {
            //0未点赞  1已点赞
            if (data.getZan().equals("1")) {
                ivZan.setImageResource(R.mipmap.tab_praise_selected);
            } else {
                ivZan.setImageResource(R.mipmap.tab_praise_unselected);
            }
        }
        if (!TextUtils.isEmpty(data.getGuanzhu())) {
            int resouceId = R.mipmap.button_attention;
            // 0未关注   1关注  2相互关注
            if (data.getGuanzhu().equals("0")) {
                resouceId = R.mipmap.button_attention;
            } else if (data.getGuanzhu().equals("1")) {
                resouceId = R.mipmap.button_cancel;
            } else if (data.getGuanzhu().equals("2")) {
                resouceId = R.mipmap.button_mutual;
            }
            ivAttend.setImageResource(resouceId);
        }
    }

    //点赞头像view
    private void setZanHeadView(ForumDetailEntity data) {
        if (data.getZan_headpic() == null || data.getZan_headpic().size() == 0) {
            tvNoZan.setVisibility(View.VISIBLE);
        } else {
            tvNoZan.setVisibility(View.GONE);
            for (int i = 0; i < data.getZan_headpic().size(); i++) {
                if (i == 0) {
                    GlideUtils.loadImageViewHead(SkillDetailsActivity.this,
                            HttpUrl.IMAGE_URL + data.getZan_headpic().get(i).getMember_list_headpic(),
                            skillDetailCiv5);//点赞头像
                    skillDetailCiv5.setVisibility(View.VISIBLE);
                    skillDetailCiv4.setVisibility(View.GONE);
                    skillDetailCiv3.setVisibility(View.GONE);
                    skillDetailCiv2.setVisibility(View.GONE);
                    skillDetailCiv1.setVisibility(View.GONE);

                } else if (i == 1) {
                    GlideUtils.loadImageViewHead(SkillDetailsActivity.this,
                            HttpUrl.IMAGE_URL + data.getZan_headpic().get(i).getMember_list_headpic(),
                            skillDetailCiv4);//点赞头像
                    skillDetailCiv4.setVisibility(View.VISIBLE);
                    skillDetailCiv3.setVisibility(View.GONE);
                    skillDetailCiv2.setVisibility(View.GONE);
                    skillDetailCiv1.setVisibility(View.GONE);
                } else if (i == 2) {
                    GlideUtils.loadImageViewHead(SkillDetailsActivity.this,
                            HttpUrl.IMAGE_URL + data.getZan_headpic().get(i).getMember_list_headpic(),
                            skillDetailCiv3);//点赞头像
                    skillDetailCiv3.setVisibility(View.VISIBLE);
                    skillDetailCiv2.setVisibility(View.GONE);
                    skillDetailCiv1.setVisibility(View.GONE);
                } else if (i == 3) {
                    GlideUtils.loadImageViewHead(SkillDetailsActivity.this,
                            HttpUrl.IMAGE_URL + data.getZan_headpic().get(i).getMember_list_headpic(),
                            skillDetailCiv2);//点赞头像
                    skillDetailCiv2.setVisibility(View.VISIBLE);
                    skillDetailCiv1.setVisibility(View.GONE);
                } else if (i == 4) {
                    GlideUtils.loadImageViewHead(SkillDetailsActivity.this,
                            HttpUrl.IMAGE_URL + data.getZan_headpic().get(i).getMember_list_headpic(),
                            skillDetailCiv1);//点赞头像
                    skillDetailCiv1.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /*打赏头像view*/
    private void setDaShangView(ForumDetailEntity data) {
        if (data.getDashang_list() == null || data.getDashang_list().size() == 0) {
            tvNoDaShang.setVisibility(View.VISIBLE);
//            tvNoZan.setVisibility(View.VISIBLE);
        } else {
            tvNoDaShang.setVisibility(View.GONE);
//            tvNoZan.setVisibility(View.GONE);
            for (int i = 0; i < data.getDashang_list().size(); i++) {
                if (i == 0) {
                    GlideUtils.loadImageViewHead(SkillDetailsActivity.this,
                            HttpUrl.IMAGE_URL + data.getZan_headpic().get(i).getMember_list_headpic(),
                            skillDetailDashang5);//点赞头像
                    skillDetailDashang5.setVisibility(View.VISIBLE);
                    skillDetailDashang4.setVisibility(View.GONE);
                    skillDetailDashang3.setVisibility(View.GONE);
                    skillDetailDashang2.setVisibility(View.GONE);
                    skillDetailDashang1.setVisibility(View.GONE);

                } else if (i == 1) {
                    GlideUtils.loadImageViewHead(SkillDetailsActivity.this,
                            HttpUrl.IMAGE_URL + data.getZan_headpic().get(i).getMember_list_headpic(),
                            skillDetailDashang4);//点赞头像
                    skillDetailDashang4.setVisibility(View.VISIBLE);
                    skillDetailDashang3.setVisibility(View.GONE);
                    skillDetailDashang2.setVisibility(View.GONE);
                    skillDetailDashang1.setVisibility(View.GONE);
                } else if (i == 2) {
                    GlideUtils.loadImageViewHead(SkillDetailsActivity.this,
                            HttpUrl.IMAGE_URL + data.getZan_headpic().get(i).getMember_list_headpic(),
                            skillDetailDashang3);//点赞头像
                    skillDetailDashang3.setVisibility(View.VISIBLE);
                    skillDetailDashang2.setVisibility(View.GONE);
                    skillDetailDashang1.setVisibility(View.GONE);
                } else if (i == 3) {
                    GlideUtils.loadImageViewHead(SkillDetailsActivity.this,
                            HttpUrl.IMAGE_URL + data.getZan_headpic().get(i).getMember_list_headpic(),
                            skillDetailDashang2);//点赞头像
                    skillDetailDashang2.setVisibility(View.VISIBLE);
                    skillDetailDashang1.setVisibility(View.GONE);
                } else if (i == 4) {
                    GlideUtils.loadImageViewHead(SkillDetailsActivity.this,
                            HttpUrl.IMAGE_URL + data.getZan_headpic().get(i).getMember_list_headpic(),
                            skillDetailDashang1);//点赞头像
                    skillDetailDashang1.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    //设置内容view
    private void setContentView(ForumDetailEntity data) {
        llContent.removeAllViews();
        if (data.getContent() != null && data.getContent().size() > 0) {
            for (int i = 0; i < data.getContent().size(); i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_skill_details, null);
                new ContentViewHolder(view, data.getContent().get(i));
                llContent.addView(view);
            }
        }
    }

    ArrayList<ImageItem> mDataImage = new ArrayList<>();
    int picPosition = -1;

    //单项内容布局
    class ContentViewHolder {
        @Bind(R.id.item_skill_details_tv)
        TextView itemSkillDetailsTv;
        @Bind(R.id.item_skill_details_iv)
        ImageView itemSkillDetailsIv;

        public ContentViewHolder(View view, final ContentBean item) {
            ButterKnife.bind(this, view);
            if (!TextUtils.isEmpty(item.getStr_imgs())) {
                mDataImage.add(new ImageItem("", HttpUrl.IMAGE_URL + item.getStr_imgs()));
                picPosition++;
                item.setPicPosition(picPosition);

                if (TextUtils.isEmpty(item.getStr_content())) {
                    itemSkillDetailsTv.setVisibility(View.GONE);
                } else {
                    itemSkillDetailsTv.setVisibility(View.VISIBLE);
                }
                itemSkillDetailsIv.setVisibility(View.VISIBLE);
                //使图片根据高度自适应宽度
                int screenWidth = WindowUtils.getScreenWidth(SkillDetailsActivity.this); //获得屏幕宽度
                ViewGroup.LayoutParams lp = itemSkillDetailsIv.getLayoutParams(); //获取布局参数管理器
                lp.width = screenWidth; //设置宽度为获取屏幕宽度
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT; //设置宽度为自适应宽度

                itemSkillDetailsIv.setLayoutParams(lp);  //将布局参数设置到控件上
                GlideUtils.loadImageView(SkillDetailsActivity.this, HttpUrl.IMAGE_URL + item.getStr_imgs(), itemSkillDetailsIv);
                itemSkillDetailsIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //预览
                        MyPicMethodUtil.preview(SkillDetailsActivity.this, mDataImage, item.getPicPosition());
                        Log.d("88888", "----path----" + mDataImage.get(item.getPicPosition()).path);
                    }
                });
            }
            if (!TextUtils.isEmpty(item.getStr_content())) {
                if (TextUtils.isEmpty(item.getStr_imgs())) {
                    itemSkillDetailsIv.setVisibility(View.GONE);
                } else {
                    itemSkillDetailsIv.setVisibility(View.VISIBLE);
                }
                itemSkillDetailsTv.setVisibility(View.VISIBLE);
                itemSkillDetailsTv.setText(item.getStr_content());
            }
        }
    }

    //2-1帖子-评论
    public void okHttpForumComments() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.forumComments);
        commonOkhttp.putParams("object_type", "1");//1帖子 2文章  3钓场  4渔具店
        commonOkhttp.putParams("object_id", fId);//点评对象ID
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putParams("rows", "3");
        commonOkhttp.putCallback(new MyGenericsCallback<ForumCommentsListEntity>(this) {
            @Override
            public void onSuccess(ForumCommentsListEntity data, int d) {
                super.onSuccess(data, d);
                if (page == 1) {
                    mDataComments.clear();
                }
                if (data != null && data.size() > 0) {
                    rlAllcomment.setClickable(true);
                    tvCommentAll.setText("查看全部");
                    mDataComments.addAll(data);
                    ++page;
                } else {
                    if (page == 1) {
                        tvCommentAll.setText("暂无");
                        rlAllcomment.setClickable(false);
//                        ToastUtils.getInstance(SkillDetailsActivity.this).showMessage(getString(R.string.list_no_data));
                    } else {
                        ToastUtils.getInstance(SkillDetailsActivity.this).showMessage(getString(R.string.list_bottom));
                    }
                }
                mAdapterComment.notifyDataSetChanged();
                skill_details_sv.onRefreshComplete();
            }

            @Override
            public void onOther(JsonResult<ForumCommentsListEntity> data, int d) {
                super.onOther(data, d);
                skill_details_sv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                skill_details_sv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 关注、取消关注
     */
    private void okHttpAttention() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.attention);
        commonOkhttp.putParams("memberid", memberId);
        commonOkhttp.putCallback(new MyGenericsCallback<StateEntity>(this) {
            @Override
            public void onSuccess(StateEntity data, int d) {
                super.onSuccess(data, d);
                okHttpForumDetail();
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
     * 点赞、取消点赞
     */
    private void dianzanRun() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.dianzanRun);
        commonOkhttp.putParams("object_type", "1");//点赞对象：1帖子 2文章  3钓场  4渔具店
        commonOkhttp.putParams("object_id", fId);//点赞对象ID
        commonOkhttp.putCallback(new MyGenericsCallback<StateEntity>(this) {
            @Override
            public void onSuccess(StateEntity data, int d) {
                super.onSuccess(data, d);
                //当前点赞状态：1.已点赞；2.未点赞
                if (data.getIs_dianzan() == 1) {
                    ivZan.setImageResource(R.mipmap.tab_praise_selected);
                } else {
                    ivZan.setImageResource(R.mipmap.tab_praise_unselected);
                }
                okHttpForumDetail();
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
     * 收藏、取消收藏
     */
    private void collect() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.follow);
        commonOkhttp.putParams("object_type", "1");//关注对象：1帖子  2钓场  3渔具店 4用户
        commonOkhttp.putParams("object_id", fId);
        commonOkhttp.putCallback(new MyGenericsCallback<StateEntity>(this) {
            @Override
            public void onSuccess(StateEntity data, int d) {
                super.onSuccess(data, d);
                //当前关注状态：1.已关注/已收藏；2.未关注/未收藏
                if (data.getIs_follow() == 1) {
                    showMessage("收藏成功");
                    ivCollection.setImageResource(R.mipmap.tab_collect_selected);
                } else {
                    showMessage("取消收藏成功");
                    ivCollection.setImageResource(R.mipmap.tab_collect_unselected);
                }
                okHttpForumDetail();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 评论
     */
    private void sendComment() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.actionComments);
        commonOkhttp.putParams("object_type", "1");//1帖子 2视频   4渔具店
        commonOkhttp.putParams("object_id", fId);//点评对象ID
        if (type == 2) {
            commonOkhttp.putParams("return_id", mDataComments.get(replyposition).getC_id());//回复的评论id（回复评论用）
        } else {
            commonOkhttp.putParams("return_id", "");//回复的评论id（回复评论用）
        }
        commonOkhttp.putParams("c_codes", "");//渔具店评分（渔具店评价用）
        commonOkhttp.putParams("content", edtComment.getText().toString());//评论内容
        commonOkhttp.putCallback(new MyGenericsCallback<EmptyResultBean>(this) {
            @Override
            public void onSuccess(EmptyResultBean data, int d) {
                super.onSuccess(data, d);
                if (type == 2) {
                    edtComment.setHint("写下你的评论...");
                    type = 1;
                }
                edtComment.setText("");
                page = 1;
                okHttpForumComments();
                okHttpForumDetail();
                showMessage("发送成功");
                // 滑动到界面底部
                skill_details_sv.post(new Runnable() {
                    @Override
                    public void run() {
                        skill_details_sv.getRefreshableView().fullScroll(View.FOCUS_DOWN);
                    }
                });
                     /*隐藏软键盘*/
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(SkillDetailsActivity.this.getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
        commonOkhttp.Execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
