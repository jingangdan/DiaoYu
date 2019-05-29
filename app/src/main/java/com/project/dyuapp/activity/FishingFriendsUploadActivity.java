package com.project.dyuapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.lzy.imagepicker.bean.ImageItem;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.common.MyCommentViewHolder;
import com.project.dyuapp.common.MyCommonAdapter;
import com.project.dyuapp.entity.FIshingFriendsImageBean;
import com.project.dyuapp.entity.FishingPlaceEntity;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyPicMethodUtil;
import com.project.dyuapp.myutils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.project.dyuapp.myutils.HttpUrl.IMAGE_URL;


/**
 * @author 田亭亭
 * @description 钓友上图（渔具店，钓场）
 * @created at 2017/12/7 0007
 * @change
 */
public class FishingFriendsUploadActivity extends MyBaseActivity {

    @Bind(R.id.fishing_friends_gv_upload)
    PullToRefreshGridView gvUpload;
    private List<FishingPlaceEntity.ImagesBean> uploadPlaceList;
    private List<FIshingFriendsImageBean> uploadShopList;
    private String whereFrom = "";//1.钓场详情，2.渔具店详情
    private String fishingShopId = ""; //钓场id

    private MyCommonAdapter uploadPlaceAdapter;//钓场详情
    private MyCommonAdapter uploadShopAdapter;//渔具店详情
    private int page = 1;
    private ArrayList<ImageItem> imageItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_fishing_friends_upload);
        ButterKnife.bind(this);
        setTvTitle("钓友上图");
        setIvBack();
        whereFrom = getIntent().getStringExtra("whereFrom");
        initData();
    }

    private void initData() {
        whereFrom = getIntent().getStringExtra("whereFrom");
        fishingShopId = getIntent().getStringExtra("fishing_shop_id");
        gvUpload.setMode(PullToRefreshBase.Mode.BOTH);

        if (!TextUtils.isEmpty(whereFrom)) {
            if (TextUtils.equals(whereFrom, "1")) {
                //钓场详情
                uploadPlaceList = new ArrayList<>();
                uploadPlaceAdapter = new MyCommonAdapter<FishingPlaceEntity.ImagesBean>(uploadPlaceList, FishingFriendsUploadActivity.this, R.layout.item_fishing_friends_upload) {
                    @Override
                    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
                        ImageView img = (ImageView) commentViewHolder.getConverView().findViewById(R.id.item_fishing_friends_img_upload);
                        if (uploadPlaceList.get(position).getImg_url() != null)
                            GlideUtils.loadImageView(mContext, IMAGE_URL + uploadPlaceList.get(position).getImg_url(), img);
                    }
                };
                gvUpload.setAdapter(uploadPlaceAdapter);
                okhttpSpreadImgs();

            } else if (TextUtils.equals(whereFrom, "2")) {
                //渔具店详情
                uploadShopList = new ArrayList<>();
                uploadShopAdapter = new MyCommonAdapter<FIshingFriendsImageBean>(uploadShopList, FishingFriendsUploadActivity.this, R.layout.item_fishing_friends_upload) {
                    @Override
                    public void setDate(MyCommentViewHolder commentViewHolder, int position) {
                        ImageView img = (ImageView) commentViewHolder.getConverView().findViewById(R.id.item_fishing_friends_img_upload);
                        if (uploadShopList.get(position).getImg_url() != null)
                            GlideUtils.loadImageView(mContext, IMAGE_URL + uploadShopList.get(position).getImg_url(), img);
                    }
                };
                gvUpload.setAdapter(uploadShopAdapter);
                page = 1;
                okhttpAllImages();

            }
        }
        gvUpload.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyPicMethodUtil.preview(FishingFriendsUploadActivity.this,imageItems,position);
            /*    Intent intentPreview = new Intent(FishingFriendsUploadActivity.this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageItems);
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                intentPreview.putExtra("edit", false);
                startActivityForResult(intentPreview, PublicStaticData.REQUEST_CODE_PREVIEW);*/
            }
        });
        gvUpload.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                page = 1;
                if (TextUtils.equals(whereFrom, "1")) {
                    okhttpSpreadImgs();
                } else if (TextUtils.equals(whereFrom, "2")) {
                    okhttpAllImages();
                }


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {

                if (TextUtils.equals(whereFrom, "1")) {
                    page++;
                    okhttpSpreadImgs();
                } else if (TextUtils.equals(whereFrom, "2")) {
                    okhttpAllImages();
                }
            }
        });
    }

    /**
     * 传图接口（钓场）
     */
    private void okhttpSpreadImgs() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.ANGLING_ALL_IMAGES);
        commonOkhttp.putParams("f_gid", fishingShopId);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.FishingPlaceImageListEntity>(this) {
            @Override
            public void onSuccess(NetBean.FishingPlaceImageListEntity data, int d) {
                super.onSuccess(data, d);
                gvUpload.onRefreshComplete();
                imageItems = new ArrayList<>();
                if (page <= 1) {
                    uploadPlaceList.clear();
                    uploadPlaceList.addAll(data);
                    for (int i = 0; i < uploadPlaceList.size(); i++) {
                        imageItems.add(new ImageItem("", HttpUrl.IMAGE_URL + uploadPlaceList.get(i).getImg_url()));
                    }
                } else {
                    uploadPlaceList.addAll(data);
                }
                uploadPlaceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                gvUpload.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                gvUpload.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 全部钓友上图-渔具店接口
     */
    private void okhttpAllImages() {
        final CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.allImages);
        commonOkhttp.putParams("fishing_shop_id", fishingShopId);
        commonOkhttp.putParams("page", page + "");
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.FIshingFriendsImageEntity>(this) {
            @Override
            public void onSuccess(NetBean.FIshingFriendsImageEntity data, int d) {
                super.onSuccess(data, d);
                try {
                    imageItems = new ArrayList<>();
                    if (page == 1) {
                        uploadShopList.clear();
                    }
                    if (data != null && data.size() != 0) {
                        uploadShopList.addAll(data);
                        for (int i = 0; i < uploadShopList.size(); i++) {
                            imageItems.add(new ImageItem("", HttpUrl.IMAGE_URL + uploadShopList.get(i).getImg_url()));
                        }
                        ++page;
                    } else {
                        commonOkhttp.showNoData(FishingFriendsUploadActivity.this, page);
                    }
                    uploadShopAdapter.notifyDataSetChanged();
                    if (gvUpload != null) {
                        gvUpload.onRefreshComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance(FishingFriendsUploadActivity.this).showMessage(FishingFriendsUploadActivity.this.getResources().getString(R.string.data_parsing_failed));
                }
            }

            @Override
            public void onOther(JsonResult<NetBean.FIshingFriendsImageEntity> data, int d) {
                super.onOther(data, d);
                gvUpload.onRefreshComplete();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                gvUpload.onRefreshComplete();
            }
        });
        commonOkhttp.Execute();
    }
}
