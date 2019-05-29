package com.project.dyuapp.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.activity.FishingPlaceDetailsActivity;
import com.project.dyuapp.activity.FishingShopDetailsActivity;
import com.project.dyuapp.activity.SkillDetailsActivity;
import com.project.dyuapp.activity.VideoDetailsActivity;

import java.util.List;

/**
 * 搜索-综合（1）
 * Created by jingang on 2018/3/11.
 */

public class SearchAdapter extends RecyclerView.Adapter {

    /**
     * 类型1：fishing_grounds 钓场
     */
    public static final int TYPE_SEARCG = 0;

    /**
     * 类型2：fishing_shop  渔具店
     */
    public static final int TYPE_BANNER = 1;
    /**
     * 类型3：videos 视频
     */
    public static final int TYPE_MENU = 2;
    /**
     * 类型4：forum 帖子
     */
    public static final int TYPE_IMGLIST = 3;


    /**
     * 当前类型
     */
    public int currentType = TYPE_MENU;

    private final Context mContext;
    private List<SearchMessageEntity.DataBeanXX> dataList;
    /**
     * 以后用它来初始化布局
     */
    private final LayoutInflater mLayoutInflater;

    GridLayoutManager mManager;
    private Intent intent;

    private SearchInterface searchInterface;

    public SearchAdapter(Context mContext, List<SearchMessageEntity.DataBeanXX> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;

        //以后用它来初始化布局
        mLayoutInflater = LayoutInflater.from(mContext);

        mManager = null;
        intent = null;
    }

    public void setSearchInterface(SearchInterface searchInterface) {
        this.searchInterface = searchInterface;
    }

    /**
     * 相当于getView创建ViewHolder布局
     *
     * @param parent
     * @param viewType 当前的类型
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SEARCG) {
            return new VideosViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_search_synthsize, parent, false));
        } else if (viewType == TYPE_BANNER) {
            return new ForumViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_search_synthsize, parent, false));
        } else if (viewType == TYPE_MENU) {
            return new FishingGroundsViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_search_synthsize, parent, false));
        } else if (viewType == TYPE_IMGLIST) {
            return new FishingShopViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_search_synthsize, parent, false));
        }
        return null;
    }

    /**
     * 相当于getView中的绑定数据模块
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_SEARCG) {
            VideosViewHolder menuViewHolder = (VideosViewHolder) holder;
            menuViewHolder.setData(dataList.get(0).getVideos());
        } else if (getItemViewType(position) == TYPE_BANNER) {
            ForumViewHolder imgListViewHolder = (ForumViewHolder) holder;
            imgListViewHolder.setData(dataList.get(0).getForum());
        } else if (getItemViewType(position) == TYPE_MENU) {
            FishingGroundsViewHolder searchViewHolder = (FishingGroundsViewHolder) holder;
            searchViewHolder.setData(dataList.get(0).getFishing_grounds());
        } else if (getItemViewType(position) == TYPE_IMGLIST) {
            FishingShopViewHolder bannerViewHolder = (FishingShopViewHolder) holder;
            bannerViewHolder.setData(dataList.get(0).getFishing_shop());
        }
    }

    /**
     * 总共有多少个item
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return 4;
    }

    /**
     * 得到类型
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case TYPE_SEARCG:
                currentType = TYPE_SEARCG;
                break;
            case TYPE_BANNER:
                currentType = TYPE_BANNER;
                break;
            case TYPE_MENU:
                currentType = TYPE_MENU;
                break;
            case TYPE_IMGLIST:
                currentType = TYPE_IMGLIST;
                break;
        }
        return currentType;
    }

    /*钓场*/
    class FishingGroundsViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private final Context mContext;
        private RecyclerView recyclerView;
        private TextView title, count;
        private SearchFishingGroundsAdapter mAdapter;

        public FishingGroundsViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            linearLayout = (LinearLayout) itemView.findViewById(R.id.lin_search_synthsize);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_item_search_synthsize);
            title = (TextView) itemView.findViewById(R.id.tv_item_search_synthsize);
            count = (TextView) itemView.findViewById(R.id.tv_item_search_synthsize_size);
        }

        public void setData(final SearchMessageEntity.DataBeanXX.FishingGroundsBean data) {
            if (data.getData() != null && data.getData().size() > 0) {
                linearLayout.setVisibility(View.VISIBLE);
                title.setText("相关钓场");
                count.setText("相关钓场" + data.getCount() + "个");

                mAdapter = new SearchFishingGroundsAdapter(data.getData(), mContext);

                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                recyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mContext.startActivity(new Intent(mContext, FishingPlaceDetailsActivity.class).putExtra("FishingPlaceId", data.getData().get(position).getF_gid()));
                    }
                });

                count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchInterface.doTabCode(3);
                    }
                });

            } else {
                linearLayout.setVisibility(View.GONE);
            }

        }
    }

    /*渔具店*/
    class FishingShopViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private Context mContext;
        private RecyclerView recyclerView;
        private TextView title, count;
        private SearchFishingShopAdapter mAdapter;


        public FishingShopViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            linearLayout = (LinearLayout) itemView.findViewById(R.id.lin_search_synthsize);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_item_search_synthsize);
            title = (TextView) itemView.findViewById(R.id.tv_item_search_synthsize);
            count = (TextView) itemView.findViewById(R.id.tv_item_search_synthsize_size);
        }

        public void setData(final SearchMessageEntity.DataBeanXX.FishingShopBean data) {
            if (data.getData() != null && data.getData().size() > 0) {
                linearLayout.setVisibility(View.VISIBLE);
                title.setText("相关渔具店");
                count.setText("相关渔具店" + data.getCount() + "个");

                mAdapter = new SearchFishingShopAdapter(data.getData(), mContext, "SearchMessageActivity");
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                recyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mContext.startActivity(new Intent(mContext, FishingShopDetailsActivity.class).putExtra("lat", data.getData().get(position).getLatitude())
                                .putExtra("lon", data.getData().get(position).getLongitude()).putExtra("id", data.getData().get(position).getFishing_shop_id())
                                .putExtra("cityid", data.getData().get(position).getCityid()));
                    }
                });

                count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchInterface.doTabCode(4);
                    }
                });

            } else {
                linearLayout.setVisibility(View.GONE);
            }

        }

    }

    /*视频*/
    class VideosViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private final Context mContext;
        private RecyclerView recyclerView;
        private TextView title, count;
        private SearchVideosAdapter mAdapter;

        public VideosViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            linearLayout = (LinearLayout) itemView.findViewById(R.id.lin_search_synthsize);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_item_search_synthsize);
            title = (TextView) itemView.findViewById(R.id.tv_item_search_synthsize);
            count = (TextView) itemView.findViewById(R.id.tv_item_search_synthsize_size);

        }

        public void setData(final SearchMessageEntity.DataBeanXX.VideosBean data) {
            if (data.getData() != null && data.getData().size() > 0) {
                linearLayout.setVisibility(View.VISIBLE);
                title.setText("相关视频");
                count.setText("相关视频" + data.getCount() + "条");

                mAdapter = new SearchVideosAdapter(data.getData(), mContext);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                recyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mContext.startActivity(new Intent(mContext, VideoDetailsActivity.class).putExtra("article_id", data.getData().get(position).getArticle_id()));
                    }
                });

                count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchInterface.doTabCode(1);
                    }
                });

            } else {
                linearLayout.setVisibility(View.GONE);
            }

        }
    }

    /*帖子*/
    class ForumViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private Context mContext;
        private RecyclerView recyclerView;
        private TextView title, count;
        private SearchForumAdapter mAdapter;

        public ForumViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            linearLayout = (LinearLayout) itemView.findViewById(R.id.lin_search_synthsize);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_item_search_synthsize);
            title = (TextView) itemView.findViewById(R.id.tv_item_search_synthsize);
            count = (TextView) itemView.findViewById(R.id.tv_item_search_synthsize_size);
        }

        public void setData(final SearchMessageEntity.DataBeanXX.ForumBean data) {
            if (data.getData() != null && data.getData().size() > 0) {
                linearLayout.setVisibility(View.VISIBLE);
                title.setText("相关帖子");
                count.setText("相关帖子" + data.getCount() + "条");

                mAdapter = new SearchForumAdapter(data.getData(), mContext);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                recyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mContext.startActivity(new Intent(mContext, SkillDetailsActivity.class).putExtra("id", data.getData().get(position).getF_id()));
                    }
                });
                count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchInterface.doTabCode(2);
                    }
                });
            } else {
                linearLayout.setVisibility(View.GONE);
            }


        }
    }

}
