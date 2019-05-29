package com.project.dyuapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.callback.StateEntity;
import com.project.dyuapp.citychange.MyGridView;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.ForumEntity;
import com.project.dyuapp.entity.ForumListEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.project.dyuapp.myutils.PublicStaticData.ID_GET_FISH;

/**
 * @author gengqiufang
 * @describtion 帖子列表
 * 个人中心-我的帖子  个人中心-我的收藏
 * 论坛-问答 论坛-渔获战报
 * 首页-渔获杂谈 首页-路亚 首页-渔具diy  首页-饵料
 * @created at 2017/11/30 0030
 */
public class PostListActivity2 extends MyBaseActivity {

    @Bind(R.id.plv)
    PullToRefreshListView plv;
    @Bind(R.id.home_fish_get_header_ll)
    LinearLayout homeFishGetLl;//首页-渔获
    @Bind(R.id.post_list_tv_num)
    TextView tvFishGetNum;

    private PostListAdapter mAdapter;
    private ArrayList<ForumEntity> mData = new ArrayList<>();

    private boolean isPublish;//是否包含发布按钮
    private boolean isHomeFishGet;//是否是首页渔获
    private boolean isMyCollection;//我的收藏
    private boolean isMy;//我的帖子

    private int page = 1;

    private String title = "";
    private String topId = "";
    private String catId = "";

    private String f_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_post_list);
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
            title = getIntent().getStringExtra("title");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("topId"))) {
            topId = getIntent().getStringExtra("topId");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("f_id"))) {
            f_id = getIntent().getStringExtra("f_id");
        }

        isPublish = getIntent().getBooleanExtra("isPublish", false);
        isHomeFishGet = getIntent().getBooleanExtra("isHomeFishGet", false);
        isMyCollection = getIntent().getBooleanExtra("isMyCollection", false);
        isMy = getIntent().getBooleanExtra("isMy", false);

        initTitle();
        initPlv();
        if (isMyCollection || isMy) {
            okHttpMyCollect();
        } else {
            okHttpForumList();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        page = 1;
        if (isMyCollection || isMy) {
            okHttpMyCollect();
        } else {
            okHttpForumList();
        }
        if (isHomeFishGet) {
            //首页渔获
            homeFishGetLl.setVisibility(View.VISIBLE);
            okHttpYuhuoNum();
        }
    }

    private void initTitle() {
        setIvBack();
        setTvTitle(title);
        if (isPublish) {
            TextView tvRight = getTvRight();
            tvRight.setText("发布");
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(SPUtils.getPreference(PostListActivity2.this, "userid"))) {
                        //已登录
                        if (topId.equals(ID_GET_FISH)) {
                            //渔获
                            startActivity(new Intent(PostListActivity2.this, PublishGetActivity.class));
                        } else {
                            startActivity(new Intent(PostListActivity2.this, PublishPostActivity.class)
                                    .putExtra("title", title)
                                    .putExtra("topId", topId));
                        }
                    } else {
                        //未登录
                        goToActivity(LoginActivity.class);
                    }


                }
            });
        }
        if (isHomeFishGet) {
            //首页渔获
            homeFishGetLl.setVisibility(View.VISIBLE);
            okHttpYuhuoNum();
        }
    }

    private void initPlv() {
        mAdapter = new PostListAdapter(mData, this);
        plv.setAdapter(mAdapter);
        plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent skill = new Intent(PostListActivity2.this, SkillDetailsActivity.class);// 渔获详情
                if (!TextUtils.isEmpty(topId)) {
                    skill.putExtra("isPistList", topId.equals(ID_GET_FISH) ? true : false);
                }
                skill.putExtra("id", mData.get(position - 1).getF_id());
                startActivity(skill);
            }
        });
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                if (isMyCollection || isMy) {
                    okHttpMyCollect();
                } else {
                    okHttpForumList();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (isMyCollection || isMy) {
                    okHttpMyCollect();
                } else {
                    okHttpForumList();
                }
            }
        });
    }

    //1帖子-列表
    public void okHttpForumList() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.forumList);
        commonOkhttp.putParams("page", page + "");//分页
        commonOkhttp.putParams("rows", "6");//每页条数
        commonOkhttp.putParams("top_id", topId);//帖子顶级分类
        if (!TextUtils.isEmpty(catId)) {
            commonOkhttp.putParams("cat_id", catId);//分类id
        }
        if (!TextUtils.isEmpty(f_id)) {
            commonOkhttp.putParams("f_gid", f_id);//钓场id
        }
        commonOkhttp.putCallback(new MyGenericsCallback<ForumListEntity>(PostListActivity2.this) {
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
                plv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }

    //我的收藏 我的帖子
    public void okHttpMyCollect() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        if (isMy) {
            commonOkhttp.putUrl(HttpUrl.myTiezi);
        } else {
            commonOkhttp.putUrl(HttpUrl.myCollect);
        }
        commonOkhttp.putParams("page", page + "");//分页
        commonOkhttp.putCallback(new MyGenericsCallback<ForumListEntity>(PostListActivity2.this) {
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
                plv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }

    //渔获帖子统计
    public void okHttpYuhuoNum() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.yuhuo);
        commonOkhttp.putCallback(new MyGenericsCallback<StateEntity>(PostListActivity2.this) {
            @Override
            public void onSuccess(StateEntity data, int d) {
                super.onSuccess(data, d);
                tvFishGetNum.setText("帖数：" + data.getTotal() + "       今日：" + data.getToday());
            }
        });
        commonOkhttp.Execute();
    }


    public class PostListAdapter extends MyCommonAdapter<ForumEntity> {

        @Bind(R.id.post_list_tv_title)
        TextView tvTitle;
        @Bind(R.id.post_list_tv_label1)
        TextView ivLabel1;
        @Bind(R.id.post_list_tv_label2)
        TextView ivLabel2;
        @Bind(R.id.post_list_iv_head)
        PorterShapeImageView ivHead;
        @Bind(R.id.post_list_tv_name)
        TextView tvName;
        @Bind(R.id.gv)
        MyGridView gv;
        @Bind(R.id.ll_forum_postlist)
        LinearLayout llForumPostlist;

        public PostListAdapter(List<ForumEntity> list, Context mContext) {
            super(list, mContext, R.layout.item_post_list2);
        }

        @Override
        public void setDate(MyCommentViewHolder commentViewHolder, int position) {
            ButterKnife.bind(this, commentViewHolder.getConverView());

            MyAdapter myAdapter = new MyAdapter(list.get(position).getThumb_img_list(), mContext);
            gv.setAdapter(myAdapter);

            gv.setClickable(false);
            gv.setPressed(false);
            gv.setEnabled(false);

            //帖子图片
//            GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL + list.get(position).getThumb_img(), ivPic);
            //帖子标题
            if (!TextUtils.isEmpty(list.get(position).getTitle())) {
                tvTitle.setText(list.get(position).getTitle());
            }
            //帖子内容
//            if (!TextUtils.isEmpty(list.get(position).getContent())) {
//                tvContent.setText(list.get(position).getContent());
//            }
            //1精华  2不精华帖
            if ((!TextUtils.isEmpty(list.get(position).getIs_jinghua())) && list.get(position).getIs_jinghua().equals("1")) {
                ivLabel2.setVisibility(View.VISIBLE);
            } else {
                ivLabel2.setVisibility(View.GONE);
            }
            //1推荐  2不推荐
            if ((!TextUtils.isEmpty(list.get(position).getIs_jinghua())) && list.get(position).getIs_tuijian().equals("1")) {
                ivLabel1.setVisibility(View.VISIBLE);
            } else {
                ivLabel1.setVisibility(View.GONE);
            }
            //发帖人头像
            GlideUtils.loadImageViewHead(mContext, HttpUrl.IMAGE_URL + list.get(position).getMember_list_headpic(), ivHead);
            //发帖人昵称
            if (!TextUtils.isEmpty(list.get(position).getMember_list_nickname())) {
                tvName.setText(list.get(position).getMember_list_nickname());
            }
        }
    }


    public class MyAdapter extends MyCommonAdapter<String> {
        @Bind(R.id.item_img)
        ImageView itemImg;

        public MyAdapter(List<String> list, Context mContext) {
            super(list, mContext, R.layout.item_post_list_img);
        }

        @Override
        public void setDate(MyCommentViewHolder commentViewHolder, int position) {
            ButterKnife.bind(this, commentViewHolder.getConverView());

            GlideUtils.loadImageView(mContext, HttpUrl.IMAGE_URL + list.get(position), itemImg);

        }
    }

}
