package com.project.dyuapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CommonAdapter;
import com.project.dyuapp.adapter.CommonViewHolder;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.callback.StateEntity;
import com.project.dyuapp.entity.FishingDianzanEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @author shipeiyun
 * @description 首页-渔获-详情-点赞的人
 * @created at 2017/12/4 0004
 */
public class DianzanPeopleActivity extends MyBaseActivity {

    @Bind(R.id.dianzan_people_lv)
    PullToRefreshListView dianzanPeopleLv;

    private List<FishingDianzanEntity> mList = new ArrayList<>();//列表集合
    private DianzanAdapter mAdapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_dianzan_people);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("点赞的人");
        initView();
        okhttpDianzanList();
    }

    /**
     * 初始化数据
     */
    private void initView() {
        mAdapter = new DianzanAdapter(DianzanPeopleActivity.this, mList, R.layout.item_attention_fans);
        dianzanPeopleLv.setAdapter(mAdapter);
        //条目点击事件
        dianzanPeopleLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String memberid = mList.get(i-1).getMember_list_id();
                if (!memberid.equals(SPUtils.getPreference(DianzanPeopleActivity.this, "userid"))) {
                    startActivity(new Intent(DianzanPeopleActivity.this, FansMainPagerActivity.class)
                            .putExtra("id", memberid));
                }
            }
        });
        //按钮点击事件
        mAdapter.setOnItemClickListener(new DianzanAdapter.OnItemClickListener() {
            @Override
            public void onClickview(View view, int position) {
                String object_id = mList.get(position).getMember_list_id();
                okHttpAttention(object_id);
            }
        });
        dianzanPeopleLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                okhttpDianzanList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                okhttpDianzanList();
            }
        });
    }

    /**
     * 3首页-渔获-详情-点赞
     */
    private void okhttpDianzanList() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.dianzanList);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putParams("rows", "16");
        commonOkhttp.putParams("f_id", getIntent().getStringExtra("f_id"));
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.DianzanListEntity>(DianzanPeopleActivity.this) {
            @Override
            public void onSuccess(NetBean.DianzanListEntity data, int d) {
                super.onSuccess(data, d);
                if (page == 1) {
                    mList.clear();
                }
                if (data != null && data.size() > 0) {
                    mList.clear();
                    mList.addAll(data);
                    ++page;
                } else {
                    if (page == 1) {
                        showMessage(getResources().getString(R.string.list_no_data));
                    } else {
                        showMessage(getResources().getString(R.string.list_bottom));
                    }
                }
                mAdapter.notifyDataSetChanged();
                dianzanPeopleLv.onRefreshComplete();
            }

            @Override
            public void onOther(JsonResult<NetBean.DianzanListEntity> data, int d) {
                super.onOther(data, d);
                dianzanPeopleLv.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                dianzanPeopleLv.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 关注、取消关注
     */
    private void okHttpAttention(String objectId) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.follow);
        commonOkhttp.putParams("object_type", "4");//关注对象：1帖子  2钓场  3渔具店 4用户
        commonOkhttp.putParams("object_id", objectId);
        commonOkhttp.putCallback(new MyGenericsCallback<StateEntity>(this) {
            @Override
            public void onSuccess(StateEntity data, int d) {
                super.onSuccess(data, d);
                page = 1;
                okhttpDianzanList();
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
     * 点赞列表适配
     */
    static class DianzanAdapter extends CommonAdapter<FishingDianzanEntity> {

        @Bind(R.id.item_iv_head)
        PorterShapeImageView itemIvHead;//头像
        @Bind(R.id.item_tv_name)
        TextView itemTvName;//会员昵称
        @Bind(R.id.item_tv_grade)
        TextView itemTvGrade;//会员级别
        @Bind(R.id.item_tv_post)
        TextView itemTvPost;//会员帖子数
        @Bind(R.id.item_tv_attention)
        TextView itemTvAttention;//会员关注数量
        @Bind(R.id.item_tv_fans)
        TextView itemTvFans;//会员粉丝数量
        @Bind(R.id.item_iv)
        ImageView itemIv;// 点赞人和当前登录用户的关系:1.互粉；2.我关注的；3.未关注；

        private OnItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public interface OnItemClickListener {
            void onClickview(View view, int position);
        }

        /**
         * 构造函数
         *
         * @param context   上下文
         * @param data      显示的数据
         * @param layout_id listview使用的条目的布局文件id
         */
        public DianzanAdapter(Context context, List<FishingDianzanEntity> data, int layout_id) {
            super(context, data, R.layout.item_attention_fans);
        }

        @Override
        public void convert(CommonViewHolder h, FishingDianzanEntity item, final int position) {
            ButterKnife.bind(this, h.getConvertView());
            String img = item.getMember_list_headpic();//头像
            if (!TextUtils.isEmpty(img)){
                GlideUtils.loadImageViewHead(context,HttpUrl.IMAGE_URL+img,itemIvHead);
            } else {
                itemIvHead.setImageResource(R.mipmap.mine_edit_head);
            }
            String name = item.getMember_list_nickname();//会员昵称
            if (!TextUtils.isEmpty(name)){
                itemTvName.setText(name);
            }
            String grade = item.getMember_lvl_id();//会员级别
            if (!TextUtils.isEmpty(grade)){
                itemTvGrade.setText("Lv."+grade);
            } else {
                itemTvGrade.setText("Lv.0");
            }
            String tie = item.getMember_tiezi();//会员帖子数
            if (!TextUtils.isEmpty(tie)){
                itemTvPost.setText(tie);
            }
            String attention = item.getMember_guanzhu();//会员关注数量
            if (!TextUtils.isEmpty(attention)){
                itemTvAttention.setText(attention);
            }
            String fans = item.getMember_fensi();//会员粉丝数量
            if (!TextUtils.isEmpty(fans)){
                itemTvFans.setText(fans);
            }
            int status = item.getRelationship();// 点赞人和当前登录用户的关系:1.互粉；2.我关注的；3.未关注；；4. 发帖人自己点赞；5.当前用户未登录
            if (status == 1){
                itemIv.setVisibility(View.VISIBLE);
                itemIv.setImageResource(R.mipmap.button_mutual);
            } else if (status == 2){
                itemIv.setVisibility(View.VISIBLE);
                itemIv.setImageResource(R.mipmap.button_cancel);
            } else if (status == 3){
                itemIv.setVisibility(View.VISIBLE);
                itemIv.setImageResource(R.mipmap.button_attention);
            } else if (status == 4){
                itemIv.setVisibility(View.GONE);
            } else if (status == 5){
                Toast.makeText(context,"当前用户未登录",Toast.LENGTH_SHORT).show();
            }
            //按钮点击事件
            if (onItemClickListener != null){
                itemIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onClickview(itemIv,position);
                    }
                });
            }
        }
    }

}
