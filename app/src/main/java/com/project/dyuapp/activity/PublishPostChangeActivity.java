package com.project.dyuapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.project.dyuapp.R;
import com.project.dyuapp.adapter.PublishPostAdapter2;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.JsonResult;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.callback.PostEditCallBack;
import com.project.dyuapp.callback.StateEntity;
import com.project.dyuapp.entity.ContentBean;
import com.project.dyuapp.entity.ReleaseSkillShowEntity;
import com.project.dyuapp.myutils.CompressImageUtil;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyFileUtils;
import com.project.dyuapp.myutils.MyPicMethodUtil;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myviews.MyProgressDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.project.dyuapp.myutils.PublicStaticData.CODE_CLASSIFY;
import static com.project.dyuapp.myutils.PublicStaticData.REQUEST_CODE_SELECT;

/**
 * @author gengqiufang
 * @describtion 修改帖子（问答，渔具DIY，晒渔具，钓鱼杂谈，海钓路亚）（饵料，技巧）
 * from 草稿箱
 * @created at 2017/11/28 0028
 */
public class PublishPostChangeActivity extends MyBaseActivity {

    @Bind(R.id.publish_post_lv)
    ListView lv;
    @Bind(R.id.publish_post_tv_save)
    TextView tvSave;
    @Bind(R.id.publish_post_tv_publish)
    TextView tvPublish;

    private ArrayList<ContentBean> mData;
    private PublishPostAdapter2 mAdapter;
    private HeaderViewHolder headerViewHolder;


    private String topId = "";
    private String catId = "";
    private String fId = "";
    private boolean isCaogao;

    private String classify = "";
    private String classifyContent = "";
    private MyProgressDialog loadingDialog;
    private String postTitle = "";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            okHttpReleaseSkill();
        }
    };

    private boolean isClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_publish_post);
        ButterKnife.bind(this);
        MyPicMethodUtil.initImagePicker();
        loadingDialog = new MyProgressDialog(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("id"))) {
            fId = getIntent().getStringExtra("id");
            okHttpReleaseSkillShow();
        }
        initTitle();
        initLv();
    }

    private void initTitle() {
        setIvBack();
        TextView tvRight = getTvRight();
        tvRight.setText("规则");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PublishPostChangeActivity.this, CommenWebviewActivity.class)
                        .putExtra("title", "规则")
                        .putExtra("url", "https://www.baidu.com"));
            }
        });
    }

    //头布局
    class HeaderViewHolder {
        @Bind(R.id.publish_post_head_edt_title)
        EditText edtTitle;
        @Bind(R.id.publish_post_head_tv_classify)
        TextView tvClassify;
        @Bind(R.id.publish_post_head_tv_classify_content)
        TextView tvClassifyContent;
        @Bind(R.id.publish_post_head_rl_sel_classify)
        RelativeLayout rlSelClassify;

        public HeaderViewHolder(View headerRootView) {
            ButterKnife.bind(this, headerRootView);
        }

        @OnClick(R.id.publish_post_head_rl_sel_classify)
        public void onViewClicked() {
            //分类
            startActivityForResult(new Intent(PublishPostChangeActivity.this, SkillAndBaitTypeActivity.class)
                    .putExtra("title", classify)
                    .putExtra("topId", topId)
                    .putExtra("sel_id", catId), CODE_CLASSIFY);
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
            tvAddress.setText(SPUtils.getPreference(PublishPostChangeActivity.this, "province") + SPUtils.getPreference(PublishPostChangeActivity.this, "city"));
        }

        @OnClick({R.id.publish_post_tv_add_text, R.id.publish_post_tv_add_pic, R.id.publish_post_tv_camera})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.publish_post_tv_add_text:
                    //添加文字
                    mData.add(new ContentBean(false));
                    mAdapter.notifyDataSetChanged();
                    break;
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

    @OnClick({R.id.publish_post_tv_save, R.id.publish_post_tv_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish_post_tv_save:
                //保存草稿
                isCaogao = true;
                judgePublish();
                break;
            case R.id.publish_post_tv_publish:
                //立即发布(技巧和配饵最少一张  其他可以无图片)
                judgePublish();
                break;
        }
    }

    private void judgePublish() {
        loadingDialog.show();
        postTitle = headerViewHolder.edtTitle.getText().toString().trim();
        boolean isHavePic = false;
//        boolean isHaveContent = false;
        if (TextUtils.isEmpty(postTitle)) {
            showMessage("请输入标题");
            loadingDialog.dismiss();
            return;
        }
        for (ContentBean item : mData) {
            if (!item.isPic()) {
//                isHaveContent = true;
                if (TextUtils.isEmpty(item.getStr_content())) {
                    showMessage("请输入内容");
                    loadingDialog.dismiss();
                    return;
                }
                if (item.getStr_content().length() < 3) {
                    showMessage("每段内容最少3字");
                    loadingDialog.dismiss();
                    return;
                }
            } else {
                isHavePic = true;
            }
        }
        if (!TextUtils.isEmpty(classify) && !isHavePic) {
            showMessage("至少上传一张图");
            loadingDialog.dismiss();
            return;
        }
        if (!isClick) {
            isClick = true;
            change();
        }

    }

    private void change() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mData.size(); i++) {
                    if (!TextUtils.isEmpty(mData.get(i).getStr_imgs())) {
                        if (mData.get(i).getStr_imgs().contains("upload")) {
                            File file = savePicture(getHttpBitmap(HttpUrl.IMAGE_URL + mData.get(i).getStr_imgs()));
                            mData.get(i).setStr_imgs(file.getAbsolutePath());
                        }
                    }
                }
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    private void initLv() {
        mData = new ArrayList<>();
        mData.add(new ContentBean(false));
        View viewHeader = LayoutInflater.from(this).inflate(R.layout.layout_publish_post_header, null);
        View viewFooter = LayoutInflater.from(this).inflate(R.layout.layout_publish_post_footer, null);
        lv.addHeaderView(viewHeader);
        lv.addFooterView(viewFooter);
        headerViewHolder = new HeaderViewHolder(viewHeader);
        new FooterViewHolder(viewFooter);
        mAdapter = new PublishPostAdapter2(mData, this, new PostEditCallBack() {
            @Override
            public void delContent(int position) {
                mData.remove(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void delPic(int position) {
                mData.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        lv.setAdapter(mAdapter);
    }

    //相机
    private void camera() {
        Intent intent = new Intent(PublishPostChangeActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    //相册
    private void album() {
        Intent intent = new Intent(PublishPostChangeActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ContentBean entity = new ContentBean(true);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                File file = CompressImageUtil.radioImage(new File(images.get(0).path));
                entity.setStr_imgs(file.getAbsolutePath());
                mData.add(entity);
                mAdapter.notifyDataSetChanged();
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == CODE_CLASSIFY) {
            classifyContent = data.getStringExtra("sel");
            catId = data.getStringExtra("sel_id");
            headerViewHolder.tvClassifyContent.setText(classifyContent);
        }
    }

    //技巧和配饵最少一张  其他可以无图片
    public void okHttpReleaseSkill() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.releaseSkillSave);
        commonOkhttp.putParams("title", postTitle);
        for (int i = 0; i < mData.size(); i++) {
            commonOkhttp.putParams("str_content" + i, mData.get(i).getStr_content());
            if (TextUtils.isEmpty(mData.get(i).getStr_imgs())) {
                commonOkhttp.putParams("str_imgs" + i, mData.get(i).getStr_imgs());
            } else {
                commonOkhttp.putFile("str_imgs" + i, mData.get(i).getStr_imgs(), new File(mData.get(i).getStr_imgs()));
            }
        }
        commonOkhttp.putParams("top_id", topId);
        commonOkhttp.putParams("f_id", fId);
        commonOkhttp.putParams("cat_id", catId);
        commonOkhttp.putParams("sum", mData.size() + "");

        commonOkhttp.putParams("province", SPUtils.getPreference(this, "province"));
        commonOkhttp.putParams("city", SPUtils.getPreference(this, "city"));
        commonOkhttp.putParams("county", SPUtils.getPreference(this, "county"));

        commonOkhttp.putParams("is_caogao", isCaogao ? "2" : "1");//是否是草稿 2是 1否
        commonOkhttp.putCallback(new MyGenericsCallback<StateEntity>(PublishPostChangeActivity.this, false) {
            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);

                MyFileUtils.deleteDir(PublicStaticData.picFilePath + "draft/");

                finishActivity();
                isClick=false;
                loadingDialog.dismiss();
              /*  if (isCaogao) {
                    startActivity(new Intent(PublishPostChangeActivity.this, DraftsActivity.class));
                }*/

            }

            @Override
            public void onOther(JsonResult<StateEntity> data, int d) {
                super.onOther(data, d);
                isClick=false;
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                isClick=false;
                loadingDialog.dismiss();
            }
        });
        commonOkhttp.Execute();
    }

    public void okHttpReleaseSkillShow() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.releaseSkillShow);
        commonOkhttp.putParams("f_id", fId);
        commonOkhttp.putCallback(new MyGenericsCallback<ReleaseSkillShowEntity>(PublishPostChangeActivity.this) {
            @Override
            public void onSuccess(final ReleaseSkillShowEntity data, int d) {
                super.onSuccess(data, d);
                setTvTitle(data.getName());
                topId = data.getTop_id();
                if (topId.equals(PublicStaticData.ID_BAIT)) {
                    classify = "饵料分类";
                } else if (topId.equals(PublicStaticData.ID_SKILL)) {
                    classify = "技巧分类";
                }
                if (!TextUtils.isEmpty(data.getCname())) {
                    classifyContent = data.getCname();//二级分类名称
                    headerViewHolder.tvClassifyContent.setText(classifyContent);
                }
                if (TextUtils.isEmpty(classify)) {
                    headerViewHolder.rlSelClassify.setVisibility(View.GONE);
                } else {
                    headerViewHolder.rlSelClassify.setVisibility(View.VISIBLE);
                    headerViewHolder.tvClassify.setText(classify);
                }
                catId = data.getCat_id();
                postTitle = data.getTitle();
                headerViewHolder.edtTitle.setText(postTitle);
                mData.clear();
                mData.addAll(data.getContent());
                for (int i = 0; i < data.getContent().size(); i++) {
                    if (!TextUtils.isEmpty(data.getContent().get(i).getStr_imgs())) {
                        mData.get(i).setPic(true);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        commonOkhttp.Execute();
    }

    public Bitmap getHttpBitmap(String url) {
        Bitmap bitmap = null;
        try {
            URL pictureUrl = new URL(url);
            InputStream in = pictureUrl.openStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    int i = 0;

    public File savePicture(Bitmap bitmap) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        i++;
        String filename = "IMG_" + dateFormat.format(new Date(System.currentTimeMillis())) + "_" + i + ".jpg";
        File file1 = new File(PublicStaticData.picFilePath + "draft/");
        if (!file1.exists() || !file1.isDirectory()) file1.mkdirs();
        File file = new File(PublicStaticData.picFilePath + "draft/", filename);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
