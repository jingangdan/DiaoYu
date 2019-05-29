package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lzy.imagepicker.bean.ImageItem;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.PostListAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.callback.StateEntity;
import com.project.dyuapp.entity.FansIndexEntity;
import com.project.dyuapp.entity.ForumEntity;
import com.project.dyuapp.entity.ForumListEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyPicMethodUtil;
import com.project.dyuapp.myviews.ListViewForScrollView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * @author gengqiufang
 * @describtion 个人中心-粉丝-主页
 * @created at 2017/12/1 0001
 */
public class FansMainPagerActivity extends MyBaseActivity {
    @Bind(R.id.fans_main_pager_header_civ_head)
    CircleImageView civHead;
    @Bind(R.id.fans_main_pager_header_tv_name)
    TextView tvName;
    @Bind(R.id.fans_main_pager_header_tv_grade)
    TextView tvGrade;
    @Bind(R.id.fans_main_pager_header_tv_num)
    TextView tvNum;
    @Bind(R.id.fans_main_pager_header_tv_attend)
    TextView tvAttend;
    @Bind(R.id.fans_main_pager_header_tv_send_message)
    TextView tvSendMessage;

    @Bind(R.id.nlv)
    ListViewForScrollView nlv;
    @Bind(R.id.fans_main_psv)
    PullToRefreshScrollView psv;

    private PostListAdapter mAdapter;
    private ArrayList<ForumEntity> mData = new ArrayList<>();
    private ArrayList<ImageItem> imageItems = new ArrayList<>();//头像

    private String memberId = "";
    private String membernName = "";
    private int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_fans_main_pager);
        ButterKnife.bind(this);
        setIvBack();
        initLv();
        if (!TextUtils.isEmpty(getIntent().getStringExtra("id"))) {
            memberId = getIntent().getStringExtra("id");
            okHttpFansIndex(); //粉丝ID
            okHttpForumList();
        }
    }

    @OnClick({R.id.fans_main_pager_header_tv_attend, R.id.fans_main_pager_header_tv_send_message, R.id.fans_main_pager_header_civ_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fans_main_pager_header_tv_attend:
                //关注
                okHttpAttention();
                break;
            case R.id.fans_main_pager_header_tv_send_message:
                //发私信
                startActivity(new Intent(this, SendMessageActivity.class)
                        .putExtra("toMemberId",memberId)
                        .putExtra("title",membernName));
                break;
            case R.id.fans_main_pager_header_civ_head:
                //头像放大
                preview();
                break;

        }
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
                okHttpFansIndex();
            }
        });
        commonOkhttp.Execute();
    }
    private void preview() {
        MyPicMethodUtil.previewHead(FansMainPagerActivity.this,imageItems);
//        Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
//        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) imageItems);
//        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
//        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
//        intentPreview.putExtra("edit", false);
//        intentPreview.putExtra("isShowNum", false);
//        intentPreview.putExtra("isSave", true);
//        startActivityForResult(intentPreview, PublicStaticData.REQUEST_CODE_PREVIEW);
    }

    private void initLv() {
        mAdapter = new PostListAdapter(mData, this);
        nlv.setAdapter(mAdapter);
        nlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(FansMainPagerActivity.this, SkillDetailsActivity.class)
                        .putExtra("id",mData.get(position).getF_id()));
            }
        });
        psv.setMode(PullToRefreshBase.Mode.BOTH);
        psv.getRefreshableView().smoothScrollTo(0, 0);//布局加载完调用
        psv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page=1;
                okHttpForumList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                okHttpForumList();
            }
        });
    }

    //7个人中心-粉丝-主页
    public void okHttpFansIndex() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.fansIndex);
        commonOkhttp.putParams("member_list_id", memberId);//粉丝ID
        commonOkhttp.putCallback(new MyGenericsCallback<FansIndexEntity>(this) {
            @Override
            public void onSuccess(FansIndexEntity data, int d) {
                super.onSuccess(data, d);
                GlideUtils.loadImageViewNo(FansMainPagerActivity.this, HttpUrl.IMAGE_URL + data.getMember_list_headpic(), civHead);
                imageItems.add(new ImageItem("", HttpUrl.IMAGE_URL + data.getMember_list_headpic()));
                membernName=data.getMember_list_nickname();
                tvName.setText(data.getMember_list_nickname());
                tvGrade.setText("Lv." + data.getLevel());
                tvNum.setText("帖子:" + data.getTienum() + "   关注:" + data.getGuannum() + "    粉丝:" + data.getFennum());
                tvAttend.setText(data.getIs_hxguanzhu().equals("1") ? "已关注" : "关注"); //是否互相关注  1是 2否
            }
        });
        commonOkhttp.Execute();
    }

    //1帖子-列表
    public void okHttpForumList() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.forumList);
        commonOkhttp.putParams("page", page + "");//分页
        commonOkhttp.putParams("rows", "6");//每页条数
        commonOkhttp.putParams("member_list_id", memberId);//每页条数

        commonOkhttp.putCallback(new MyGenericsCallback<ForumListEntity>(FansMainPagerActivity.this) {
            @Override
            public void onSuccess(ForumListEntity data, int d) {
                super.onSuccess(data, d);
                if (page == 1) {
                    mData.clear();
                }
                if (data.size() == 0) {
                    if (page == 1) {
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                } else {
                    mData.addAll(data);
                    page += 1;
                }
                mAdapter.notifyDataSetChanged();
                psv.onRefreshComplete();
            }

            @Override
            public void onOther(JsonResult<ForumListEntity> data, int d) {
                super.onOther(data, d);
                psv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                psv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }
}
