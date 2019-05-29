package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.CommentPlaceAdapter;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.callback.PostEditCallBack;
import com.project.dyuapp.callback.StateEntity;
import com.project.dyuapp.entity.ContentBean;
import com.project.dyuapp.myutils.CompressImageUtil;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myviews.MyProgressDialog;
import com.project.dyuapp.myviews.StarBarTouch;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.project.dyuapp.myutils.PublicStaticData.ID_BAIT;
import static com.project.dyuapp.myutils.PublicStaticData.ID_SKILL;
import static com.project.dyuapp.myutils.PublicStaticData.ID_TALk;
import static com.project.dyuapp.myutils.PublicStaticData.REQUEST_CODE_SELECT;

/**
 * 钓场-评价
 * Created by jingang on 2018/2/27.
 */

public class FishingPlaceCommentActivity extends MyBaseActivity {
    @Bind(R.id.comment_fishing_place_lv_add)
    ListView lv;
    @Bind(R.id.comment_tv_public)
    TextView commentTvPublic;

    private ArrayList<ContentBean> mData;
    private CommentPlaceAdapter mAdapter;
    private HeaderViewHolder headerViewHolder;

    private MyProgressDialog myProgressDialog;

    private String score = "", content = "";

    private boolean isClick;

    private String fishingPlaceId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_fishing_place_comment);
        ButterKnife.bind(this);

        myProgressDialog = new MyProgressDialog(this);
        fishingPlaceId = getIntent().getStringExtra("fishingPlaceId");
        initView();
        initLv();
    }

    private void initView() {
        setTvTitle("点评");
        setIvBack();
    }

    //初始化列表
    private void initLv() {
        mData = new ArrayList<>();
        mData.add(new ContentBean(false));
        View viewHeader = LayoutInflater.from(this).inflate(R.layout.layout_comment_fishing_place_header, null);
        View viewFooter = LayoutInflater.from(this).inflate(R.layout.layout_publish_post_footer, null);
        lv.addHeaderView(viewHeader);
        lv.addFooterView(viewFooter);
        headerViewHolder = new HeaderViewHolder(viewHeader);
        new FooterViewHolder(viewFooter);
        mAdapter = new CommentPlaceAdapter(mData, this, new PostEditCallBack() {
            @Override
            public void delContent(int position) {
                //删除文字
                mData.remove(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void delPic(int position) {
                //删除图片
                mData.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        lv.setAdapter(mAdapter);
    }

    @OnClick({R.id.comment_tv_public})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_tv_public:
                judgePublish();
                break;
        }
    }

    private void judgePublish() {
        myProgressDialog.show();
        content = headerViewHolder.commentEdtContent.getText().toString().trim();

        boolean isHavePic = false;
        if (TextUtils.isEmpty(score)) {
            showMessage("请打分");
            myProgressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(content)) {
            showMessage("请输入评价内容");
            myProgressDialog.dismiss();
            return;
        }

//        if (topId.equals(ID_BAIT) || topId.equals(ID_SKILL) || topId.equals(ID_TALk)) {
//            if (!isHavePic) {
//                showMessage("至少上传一张图");
//                myProgressDialog.dismiss();
//                return;
//            }
//        }
        if (!isClick) {
            isClick = true;
            if (!TextUtils.isEmpty(fishingPlaceId)) {
                okHttpReleaseSkill();
            }

//            showMessage("点评成功");
//            myProgressDialog.dismiss();
//            finishActivity();
            return;
        }

    }

    //技巧和配饵最少一张  其他可以无图片
    public void okHttpReleaseSkill() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.dianPing);
        commonOkhttp.putParams("score", score);
        commonOkhttp.putParams("str_content0", content);
        for (int i = 0; i < mData.size(); i++) {
            if (TextUtils.isEmpty(mData.get(i).getStr_imgs())) {
                commonOkhttp.putParams("str_imgs" + i, mData.get(i).getStr_imgs());
            } else {
                commonOkhttp.putFile("str_imgs" + i, mData.get(i).getStr_imgs(), new File(mData.get(i).getStr_imgs()));
            }
        }
        commonOkhttp.putParams("f_gid", fishingPlaceId);//钓场id
        commonOkhttp.putParams("sum", (mData.size() ) + "");

        commonOkhttp.putCallback(new MyGenericsCallback<StateEntity>(FishingPlaceCommentActivity.this, false) {
            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
                finishActivity();
                isClick = false;
                myProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                isClick = false;
                myProgressDialog.dismiss();
            }

            @Override
            public void onOther(JsonResult<StateEntity> data, int d) {
                super.onOther(data, d);
                isClick = false;
                myProgressDialog.dismiss();
            }
        });
        commonOkhttp.Execute();
    }

    class HeaderViewHolder {
        @Bind(R.id.comment_star_bar_touch_count)
        StarBarTouch commentStarBarTouchCount;
        @Bind(R.id.comment_score_tv_fishing_place)
        TextView commentScoreTvFishingPlace;
        @Bind(R.id.comment_edt_content)
        EditText commentEdtContent;

        public HeaderViewHolder(View viewHeader) {
            ButterKnife.bind(this, viewHeader);
            commentStarBarTouchCount.setIntegerMark(true);
            commentStarBarTouchCount.setOnStarChangeListener(new StarBarTouch.OnStarChangeListener() {
                @Override
                public void onStarChange(float mark) {
                    score = "" + mark;
                }
            });
        }

    }

    //尾布局
    class FooterViewHolder {
        @Bind(R.id.publish_post_tv_add_text)
        TextView tvAddText;
        @Bind(R.id.publish_post_tv_add_pic)
        TextView tvAddPic;
        @Bind(R.id.publish_post_tv_camera)
        TextView tvCamera;
        @Bind(R.id.publish_post_tv_address)
        TextView tvAddress;

        public FooterViewHolder(View footerRootView) {
            ButterKnife.bind(this, footerRootView);
            tvAddText.setVisibility(View.GONE);
            tvAddress.setText(SPUtils.getPreference(FishingPlaceCommentActivity.this, "province") + SPUtils.getPreference(FishingPlaceCommentActivity.this, "city"));
        }

        @OnClick({R.id.publish_post_tv_add_pic, R.id.publish_post_tv_camera})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.publish_post_tv_add_pic:
                    //添加图片
                    album();
                    break;
                case R.id.publish_post_tv_camera:
                    //拍照
                    camera();
                    break;
            }
        }
    }

    //相机
    private void camera() {
        Intent intent = new Intent(FishingPlaceCommentActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    //相册
    private void album() {
        Intent intent = new Intent(FishingPlaceCommentActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            ContentBean entity = new ContentBean(true);
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                File file = CompressImageUtil.radioImage(new File(images.get(0).path));
                entity.setStr_imgs(file.getAbsolutePath());
                mData.add(entity);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
