package com.project.dyuapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.activity.HomeBaitActivity;
import com.project.dyuapp.activity.HomeSkillActivity;
import com.project.dyuapp.activity.PostListActivity;
import com.project.dyuapp.base.MyBaseFragment;
import com.project.dyuapp.myutils.PublicStaticData;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @describe：论坛
 * @author：刘晓丽
 * @createdate：2017/8/18 11:00
 */

public class ForumFragment extends MyBaseFragment {

    @Bind(R.id.title_tv_title)
    TextView titleTvTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum, null);
        ButterKnife.bind(this, view);
        titleTvTitle.setVisibility(View.VISIBLE);
        titleTvTitle.setText("论坛");
        return view;
    }

    @OnClick({R.id.forum_ll_catch, R.id.forum_ll_ask, R.id.forum_ll_diy, R.id.forum_ll_fishing,
            R.id.forum_ll_bait, R.id.forum_ll_skill, R.id.forum_ll_talk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forum_ll_catch:
                //渔获战报
                gotoPostList("渔获战报", PublicStaticData.ID_GET_FISH);
                break;
            case R.id.forum_ll_ask:
                //问答
                gotoPostList("问答", PublicStaticData.ID_QUESTION);
                break;
            case R.id.forum_ll_diy:
                //渔具DIY
                gotoPostList("渔具DIY", PublicStaticData.ID_FISH_TOOL);
                break;
            case R.id.forum_ll_fishing:
                //路亚海钓
                gotoPostList("路亚海钓", PublicStaticData.ID_LUYA);
                break;
            case R.id.forum_ll_bait:
                //鱼饵配方
                startActivity(new Intent(getActivity(), HomeBaitActivity.class));
                break;
            case R.id.forum_ll_skill:
                //技巧
                startActivity(new Intent(getActivity(), HomeSkillActivity.class));
                break;
            case R.id.forum_ll_talk:
                //杂谈
                gotoPostList("杂谈", PublicStaticData.ID_TALk);
                break;
        }
    }

    //跳转PostList帖子列表
    private void gotoPostList(String title, String topId) {
        startActivity(new Intent(getActivity(), PostListActivity.class)
                .putExtra("title", title)
                .putExtra("isPublish", true)
                .putExtra("topId", topId));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
