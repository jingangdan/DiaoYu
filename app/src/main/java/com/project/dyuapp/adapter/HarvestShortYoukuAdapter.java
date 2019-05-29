package com.project.dyuapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.dyuapp.R;
import com.project.dyuapp.activity.HarvestShortYoukuActivity;
import com.project.dyuapp.activity.VideoDetailsActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.callback.StateEntity;
import com.project.dyuapp.entity.HarvestShortYoukuEntity;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.youku.cloud.player.YoukuPlayerView;
import com.youku.cloud.utils.DeviceInfo;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * @创建者 任伟
 * @创建时间 2017/12/25 16:42
 * @描述 ${}
 */

public class HarvestShortYoukuAdapter extends BaseAdapter {

    HashMap<Integer,View> lmap = new HashMap<Integer,View>();

    private List<HarvestShortYoukuEntity> playerCovers;
    private LayoutInflater mInflater;
    private YoukuPlayerView currentYoukuPlayerView;
    private Activity activity;
    private Context context;
    private YoukuPlayerView youkuPlayerView = null;//首次播放时可播放第一条

    public HarvestShortYoukuAdapter(Context context, List<HarvestShortYoukuEntity> playerCovers) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.playerCovers = playerCovers;
        this.activity = (Activity) context;
    }

    @Override
    public int getCount() {
        return playerCovers.size();
    }

    @Override
    public Object getItem(int position) {
        return playerCovers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (lmap.get(position)==null) {
            holder = new ViewHolder();
            if (currentYoukuPlayerView == null) {
                convertView = mInflater.inflate(R.layout.layout_list_player_item, null);
                holder.youkuPlayerView = (YoukuPlayerView) convertView.findViewById(R.id.youkuplayerview);
                playerCovers.get(position).setYoukuPlayerView(holder.youkuPlayerView);
                holder.youkuPlayerView.attachActivity(activity);
                currentYoukuPlayerView = holder.youkuPlayerView;
                //currentYoukuPlayerView.setShowVideoQualityBtn(false);
                //currentYoukuPlayerView.setShowBackBtn(false);

            } else {
                convertView = mInflater.inflate(R.layout.layout_list_item, null);
            }
            holder.player_container = (ViewGroup) convertView.findViewById(R.id.player_container);
            holder.player_cover = (ImageView) convertView.findViewById(R.id.player_cover);//封面
            holder.playerCoverRl = (RelativeLayout) convertView.findViewById(R.id.player_cover_rl);//播放器和播放按钮布局
            holder.playerIconIv = (ImageView) convertView.findViewById(R.id.player_icon_iv);//播放按钮
            holder.harvesTitleTv = (TextView) convertView.findViewById(R.id.harvest_short_title);//标题
            holder.tagFlowLayout = (TagFlowLayout) convertView.findViewById(R.id.item_harvest_short_fishing_fl);//标签
            holder.itemHarvestShortContentTv = (TextView) convertView.findViewById(R.id.item_harvest_short_content_tv);//评论数
            holder.itemHarvestShortFabulousTv = (TextView) convertView.findViewById(R.id.item_harvest_short_fabulous_tv);//点赞数
            holder.itemHarvestShortFabulousIv = (ImageView) convertView.findViewById(R.id.item_harvest_short_fabulous_iv);//点赞图标
            holder.itemHarvestShortContentLl = (LinearLayout) convertView.findViewById(R.id.item_harvest_short_content_ll);//评论点击
            holder.itemHarvestShortFabulousLl= (LinearLayout) convertView.findViewById(R.id.item_harvest_short_fabulous_ll);//点赞点击

            holder.playerCoverRl.getLayoutParams().height = DeviceInfo.getScreenWidth() * 9 / 16;
            lmap.put(position,convertView);
            convertView.setTag(holder);
        } else {
            convertView = lmap.get(position);
            holder = (ViewHolder) convertView.getTag();
        }


        //判断是否为当前准备播放视频
        if (playerCovers.get(position).isHasplayer()) {
            ViewGroup viewGroup = (ViewGroup) currentYoukuPlayerView.getParent();
            if(viewGroup!=null){
                if(viewGroup.getId()!=R.id.fullPlayerContainer){
                    if(holder.player_container!=viewGroup || youkuPlayerView == null){
                        if(viewGroup!=null){
                            viewGroup.removeView(currentYoukuPlayerView);
                            currentYoukuPlayerView.release();
                        }
                        holder.player_container.addView(currentYoukuPlayerView);

                    }
                    if(!currentYoukuPlayerView.getCurrentVid().equals(playerCovers.get(position).getContent())){
                        currentYoukuPlayerView.playYoukuVideo(playerCovers.get(position).getContent());
                        currentYoukuPlayerView.hideControllerView();
                        youkuPlayerView = currentYoukuPlayerView;
                    }
                }
            }else{
                GlideUtils.loadImageView(context, playerCovers.get(position).getThumb(), holder.player_cover);
            }
        } else {
            GlideUtils.loadImageView(context, playerCovers.get(position).getThumb(), holder.player_cover);
        }
        //点击封面
//        holder.player_cover.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

//        //点击播放按钮
        holder.playerIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentYoukuPlayerView.setVisibility(View.VISIBLE);
                HarvestShortYoukuActivity harvestShortYoukuActivity = (HarvestShortYoukuActivity) context;
                harvestShortYoukuActivity.releaseLastPlayer(position);
            }
        });

        // 标题
        holder.harvesTitleTv.setText(playerCovers.get(position).getTitle());

        // 评论数量
        final int finalI = position;
        final ViewHolder finalHolder = holder;
        holder.itemHarvestShortContentTv.setText(playerCovers.get(position).getComments_num());
        holder.itemHarvestShortContentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStop();
                context.startActivity(new Intent(context, VideoDetailsActivity.class).putExtra("article_id", playerCovers.get(finalI).getArticle_id()));
            }
        });
        // 点赞数量
        holder.itemHarvestShortFabulousTv.setText(playerCovers.get(position).getZan_num());
        //当前点赞状态：1.已点赞；2.未点赞
        if (playerCovers.get(position).getIs_zan() == 1) {
            finalHolder.itemHarvestShortFabulousIv.setImageResource(R.mipmap.yule_dz_selected);
        } else {
            finalHolder.itemHarvestShortFabulousIv.setImageResource(R.mipmap.yule_dz_unselected);
        }
        holder.itemHarvestShortFabulousLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dianzanRun(playerCovers.get(finalI).getArticle_id(), finalHolder.itemHarvestShortFabulousTv, finalHolder.itemHarvestShortFabulousIv);
            }
        });

        // 标签
        if (playerCovers.get(position).getCate_name() != null) {
            final List<String> tagList = Arrays.asList(playerCovers.get(position).getCate_name().get(0));
            TagAdapter hotAdapter = new TagAdapter<String>(tagList) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.flow_layout_tag_fishing_tv, parent, false);
                    tv.setText(tagList.get(position));
                    return tv;
                }
            };
            holder.tagFlowLayout.setAdapter(hotAdapter);
        }
        return convertView;
    }

    public final class ViewHolder {
        public ImageView player_cover;
        public ViewGroup player_container;
        public ImageView playerIconIv;
        public RelativeLayout playerCoverRl;//播放器和播放按钮布局
        public YoukuPlayerView youkuPlayerView;//视频
        public TextView harvesTitleTv;//标题
        public TagFlowLayout tagFlowLayout;//标签
        public TextView itemHarvestShortContentTv;//评论数
        public TextView itemHarvestShortFabulousTv;//点赞数
        public ImageView itemHarvestShortFabulousIv;//点赞图标
        public LinearLayout itemHarvestShortContentLl;//评论点击
        public LinearLayout itemHarvestShortFabulousLl;//点赞点击

    }

    public YoukuPlayerView getCurrentYoukuPlayerView() {
        return currentYoukuPlayerView;
    }

    public void onPause() {
        if (currentYoukuPlayerView != null) {
            currentYoukuPlayerView.onPause();
        }
    }

    public void onStop() {
        if (currentYoukuPlayerView != null && currentYoukuPlayerView.isPlaying()) {
            currentYoukuPlayerView.stop();
            currentYoukuPlayerView.setVisibility(View.GONE);

            for (HarvestShortYoukuEntity co : playerCovers) {
                co.setHasplayer(false);
            }
        }
    }

    public void onResume() {
        if (currentYoukuPlayerView != null) {
            currentYoukuPlayerView.onResume();
        }
    }

    public void onDestroy() {
        if (currentYoukuPlayerView != null) {
            currentYoukuPlayerView.onDestroy();
        }
    }

    /**
     * 点赞、取消点赞
     *
     * @param article_id
     * @param itemHarvestShortFabulousTv
     * @param itemHarvestShortFabulousIv
     */
    private void dianzanRun(String article_id, final TextView itemHarvestShortFabulousTv, final ImageView itemHarvestShortFabulousIv) {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.dianzanRun);
        commonOkhttp.putParams("object_type", "2");//点赞对象：1帖子 2文章  3钓场  4渔具店
        commonOkhttp.putParams("object_id", article_id);//点赞对象ID
        commonOkhttp.putCallback(new MyGenericsCallback<StateEntity>(activity) {
            @Override
            public void onSuccess(StateEntity data, int d) {
                super.onSuccess(data, d);
                String zan = itemHarvestShortFabulousTv.getText().toString().trim();
                //当前点赞状态：1.已点赞；2.未点赞
                if (data.getIs_dianzan() == 1) {
                    itemHarvestShortFabulousIv.setImageResource(R.mipmap.yule_dz_selected);
                    itemHarvestShortFabulousTv.setText(Integer.parseInt(zan) + 1 + "");
                } else {
                    itemHarvestShortFabulousIv.setImageResource(R.mipmap.yule_dz_unselected);
                    itemHarvestShortFabulousTv.setText(Integer.parseInt(zan) - 1 + "");
                }
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
        commonOkhttp.Execute();
    }
}
