package com.project.dyuapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.dyuapp.R;
import com.project.dyuapp.activity.HomeVideoTypeActivity;
import com.project.dyuapp.activity.VideoDetailsActivity;
import com.project.dyuapp.base.MyBaseFragment;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.HomeVideosBean;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.ToastUtils;
import com.project.dyuapp.myviews.GridViewForScrollView;
import com.project.dyuapp.myviews.InWebView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author ${tiantingting}
 * @created on 2017/12/4 0027
 * @description 视频--首页
 * @change ${}
 */
public class HomeVideoFragment extends MyBaseFragment {
    @Bind(R.id.home_video_newest_tv_name)
    TextView newestTvName;
    @Bind(R.id.home_video_newest_gv)
    GridViewForScrollView newestGv;
    @Bind(R.id.home_video_daquan_tv_name)
    TextView daquanTvName;
    @Bind(R.id.home_video_daquan_gv)
    GridViewForScrollView daquanGv;
    @Bind(R.id.home_video_fishingTeaching_tv_name)
    TextView fishingTeachingTvName;
    @Bind(R.id.home_video_fishingTeaching_gv)
    GridViewForScrollView fishingTeachingGv;
    @Bind(R.id.home_video_fishingSeas_tv_name)
    TextView fishingSeasTvName;
    @Bind(R.id.home_video_fishingSeas_gv)
    GridViewForScrollView fishingSeasGv;
    @Bind(R.id.home_video_happyFishingtv_name)
    TextView happyFishingtvName;
    @Bind(R.id.home_video_happyFishing_gv)
    GridViewForScrollView happyFishingGv;

    private List<HomeVideosBean.DaquanBean> daquanList;
    private List<HomeVideosBean.FishingSeasBean> fishingSeasList;
    private List<HomeVideosBean.FishingTeachingBean> fishingTeachingList;
    private List<HomeVideosBean.HappyFishingBean> happyFishingList;
    private List<HomeVideosBean.NewestBean> newestList;
    private InWebView inWeb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_video, null);
        ButterKnife.bind(this, view);
        okhttpVideos();
        return view;
    }

    /**
     * 首页-视频接口
     */
    private void okhttpVideos() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.videos);
        commonOkhttp.putCallback(new MyGenericsCallback<HomeVideosBean>(getActivity()) {
            @Override
            public void onSuccess(HomeVideosBean data, int d) {
                super.onSuccess(data, d);
                try {
                    setNewestAdapter(data);
                    setDaQuanAdapter(data);
                    setFishingTeachingAdapter(data);
                    setFishingSeasAdapter(data);
                    setHappyFishingAdapter(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(getActivity()).showMessage(getActivity().getResources().getString(R.string.data_parsing_failed));
                }

            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 四海垂钓列表
     *
     * @param data
     */
    private void setFishingSeasAdapter(HomeVideosBean data) {
        fishingSeasList = new ArrayList<>();
        fishingSeasList.addAll(data.getFishingSeas());
        fishingSeasTvName.setText("四海垂钓");
        fishingSeasGv.setAdapter(new MyCommonAdapter<HomeVideosBean.FishingSeasBean>(fishingSeasList, getActivity(), R.layout.item_home_gv_video) {
            @Override
            public void setDate(MyCommentViewHolder commentViewHolder, int position) {

                TextView tvTitle = (TextView) commentViewHolder.FindViewById(R.id.item_video_tv_title);
                ImageView imgIcon = (ImageView) commentViewHolder.FindViewById(R.id.item_video_img_icon);
                String content = fishingSeasList.get(position).getContent();
                String title = fishingSeasList.get(position).getTitle();
                String thumb = fishingSeasList.get(position).getThumb();
                if (!TextUtils.isEmpty(title)) {
                    tvTitle.setText(title);
                }
                if (!TextUtils.isEmpty(thumb)) {
                    GlideUtils.loadImageView(mContext, thumb, imgIcon);
                } else {
                    imgIcon.setBackgroundResource(R.mipmap.mine_img);
                }

            }
        });
        fishingSeasGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), VideoDetailsActivity.class).putExtra("article_id", fishingSeasList.get(position).getArticle_id()));
            }
        });
    }

    /**
     * 钓技大全列表
     *
     * @param data
     */
    private void setDaQuanAdapter(HomeVideosBean data) {
        daquanList = new ArrayList<>();
        daquanList.addAll(data.getDaquan());
        daquanTvName.setText("钓技大全");
        daquanGv.setAdapter(new MyCommonAdapter<HomeVideosBean.DaquanBean>(daquanList, getActivity(), R.layout.item_home_gv_video) {
            @Override
            public void setDate(MyCommentViewHolder commentViewHolder, int position) {

                TextView tvTitle = (TextView) commentViewHolder.FindViewById(R.id.item_video_tv_title);
                ImageView imgIcon = (ImageView) commentViewHolder.FindViewById(R.id.item_video_img_icon);
                String content = daquanList.get(position).getContent();
                String title = daquanList.get(position).getTitle();
                String thumb = daquanList.get(position).getThumb();
                if (!TextUtils.isEmpty(title)) {
                    tvTitle.setText(title);
                }
                if (!TextUtils.isEmpty(thumb)) {
                    GlideUtils.loadImageView(mContext, thumb, imgIcon);
                } else {
                    imgIcon.setBackgroundResource(R.mipmap.mine_img);
                }
            }
        });
        daquanGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), VideoDetailsActivity.class).putExtra("article_id", daquanList.get(position).getArticle_id()));

            }
        });
    }

    /**
     * 钓鱼教学列表
     *
     * @param data
     */
    private void setFishingTeachingAdapter(HomeVideosBean data) {
        fishingTeachingList = new ArrayList<>();
        fishingTeachingList.addAll(data.getFishingTeaching());
        fishingTeachingTvName.setText("钓鱼教学");
        fishingTeachingGv.setAdapter(new MyCommonAdapter<HomeVideosBean.FishingTeachingBean>(fishingTeachingList, getActivity(), R.layout.item_home_gv_video) {
            @Override
            public void setDate(MyCommentViewHolder commentViewHolder, int position) {

                TextView tvTitle = (TextView) commentViewHolder.FindViewById(R.id.item_video_tv_title);
                ImageView imgIcon = (ImageView) commentViewHolder.FindViewById(R.id.item_video_img_icon);
                String content = fishingTeachingList.get(position).getContent();
                String title = fishingTeachingList.get(position).getTitle();
                String thumb = fishingTeachingList.get(position).getThumb();
                if (!TextUtils.isEmpty(title)) {
                    tvTitle.setText(title);
                }
                if (!TextUtils.isEmpty(thumb)) {
                    GlideUtils.loadImageView(mContext, thumb, imgIcon);
                } else {
                    imgIcon.setBackgroundResource(R.mipmap.mine_img);
                }
            }
        });
        fishingTeachingGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), VideoDetailsActivity.class).putExtra("article_id", fishingTeachingList.get(position).getArticle_id()));

            }
        });
    }

    /**
     * 快乐垂钓列表
     *
     * @param data
     */
    private void setHappyFishingAdapter(HomeVideosBean data) {
        happyFishingList = new ArrayList<>();
        happyFishingList.addAll(data.getHappyFishing());
        happyFishingtvName.setText("快乐垂钓");
        happyFishingGv.setAdapter(new MyCommonAdapter<HomeVideosBean.HappyFishingBean>(happyFishingList, getActivity(), R.layout.item_home_gv_video) {
            @Override
            public void setDate(MyCommentViewHolder commentViewHolder, int position) {

                TextView tvTitle = (TextView) commentViewHolder.FindViewById(R.id.item_video_tv_title);
                ImageView imgIcon = (ImageView) commentViewHolder.FindViewById(R.id.item_video_img_icon);
                String content = happyFishingList.get(position).getContent();

                String title = happyFishingList.get(position).getTitle();
                String thumb = happyFishingList.get(position).getThumb();
                if (!TextUtils.isEmpty(title)) {
                    tvTitle.setText(title);
                }
                if (!TextUtils.isEmpty(thumb)) {
                    GlideUtils.loadImageView(mContext, thumb, imgIcon);
                } else {
                    imgIcon.setBackgroundResource(R.mipmap.mine_img);
                }
            }
        });
        happyFishingGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), VideoDetailsActivity.class).putExtra("article_id", happyFishingList.get(position).getArticle_id()));

            }
        });
    }

    /**
     * 最新视频列表
     *
     * @param data
     */
    private void setNewestAdapter(HomeVideosBean data) {
        newestList = new ArrayList<>();
        newestList.addAll(data.getNewest());
        newestTvName.setText("最新视频");
        newestGv.setAdapter(new MyCommonAdapter<HomeVideosBean.NewestBean>(newestList, getActivity(), R.layout.item_home_gv_video) {
            @Override
            public void setDate(MyCommentViewHolder commentViewHolder, int position) {
                TextView tvTitle = (TextView) commentViewHolder.FindViewById(R.id.item_video_tv_title);
                ImageView imgIcon = (ImageView) commentViewHolder.FindViewById(R.id.item_video_img_icon);
                String thumb = newestList.get(position).getThumb();
                if (!TextUtils.isEmpty(thumb)) {
                    GlideUtils.loadImageView(mContext, thumb, imgIcon);
                } else {
                    imgIcon.setBackgroundResource(R.mipmap.mine_img);
                }
                String title = newestList.get(position).getTitle();
                if (!TextUtils.isEmpty(title)) {
                    tvTitle.setText(title);
                }
            }
        });
        newestGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), VideoDetailsActivity.class).putExtra("article_id", newestList.get(position).getArticle_id()));
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (inWeb != null) {
            inWeb.myDestroy();
        }
    }

    @OnClick({R.id.home_video_newest_tv_more, R.id.home_video_ll_newest_more, R.id.home_video_daquan_tv_more, R.id.home_video_daquan_ll_more, R.id.home_video_fishingTeaching_tv_more, R.id.home_video_fishingTeaching_ll_more, R.id.home_video_fishingSeas_tv_more, R.id.home_video_fishingSeas_ll_more, R.id.home_video_happyFishing_tv_more, R.id.home_video_itemhappyFishing_ll_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_video_newest_tv_more:
            case R.id.home_video_ll_newest_more:
                startActivity(new Intent(getActivity(), HomeVideoTypeActivity.class)
                        .putExtra("item_title", newestTvName.getText().toString())
                        .putExtra("type", "1")
                );
                break;
            case R.id.home_video_daquan_tv_more:
            case R.id.home_video_daquan_ll_more:
                startActivity(new Intent(getActivity(), HomeVideoTypeActivity.class)
                        .putExtra("item_title", daquanTvName.getText().toString())
                        .putExtra("type", "2"));
                break;
            case R.id.home_video_fishingTeaching_tv_more:
            case R.id.home_video_fishingTeaching_ll_more:
                startActivity(new Intent(getActivity(), HomeVideoTypeActivity.class)
                        .putExtra("item_title", fishingTeachingTvName.getText().toString())
                        .putExtra("type", "3"));
                break;
            case R.id.home_video_fishingSeas_tv_more:
            case R.id.home_video_fishingSeas_ll_more:
                startActivity(new Intent(getActivity(), HomeVideoTypeActivity.class)
                        .putExtra("item_title", fishingSeasTvName.getText().toString())
                        .putExtra("type", "4"));
                break;
            case R.id.home_video_happyFishing_tv_more:
            case R.id.home_video_itemhappyFishing_ll_more:
                startActivity(new Intent(getActivity(), HomeVideoTypeActivity.class)
                        .putExtra("item_title", happyFishingtvName.getText().toString())
                        .putExtra("type", "5"));
                break;
        }
    }
}
