package com.project.dyuapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.project.dyuapp.myutils.CompressImageUtil;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyPicMethodUtil;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myviews.MyProgressDialog;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.project.dyuapp.myutils.PublicStaticData.CODE_CLASSIFY;
import static com.project.dyuapp.myutils.PublicStaticData.ID_BAIT;
import static com.project.dyuapp.myutils.PublicStaticData.ID_FISH_TOOL;
import static com.project.dyuapp.myutils.PublicStaticData.ID_LUYA;
import static com.project.dyuapp.myutils.PublicStaticData.ID_SKILL;
import static com.project.dyuapp.myutils.PublicStaticData.ID_TALk;
import static com.project.dyuapp.myutils.PublicStaticData.REQUEST_CODE_SELECT;

/**
 * @author gengqiufang
 * @describtion 发布帖子（问答，渔具DIY，晒渔具，钓鱼杂谈，海钓路亚）（饵料，技巧）
 * @created at 2017/11/28 0028
 */
public class PublishPostActivity extends MyBaseActivity {

    @Bind(R.id.publish_post_lv)
    ListView lv;
    @Bind(R.id.publish_post_tv_save)
    TextView tvSave;
    @Bind(R.id.publish_post_tv_publish)
    TextView tvPublish;

    private ArrayList<ContentBean> mData;
    private PublishPostAdapter2 mAdapter;
    private HeaderViewHolder headerViewHolder;

    private String title = "";//Activity标题
    private String classify = "";
    private String topId = "";
    private String catId = "";
    private boolean isCaogao;
    private String classifyContent = "";
    private String postTitle = "";
    private MyProgressDialog myProgressDialog;

    private boolean isClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_publish_post);
        ButterKnife.bind(this);
        myProgressDialog = new MyProgressDialog(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
            title = getIntent().getStringExtra("title");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("classify"))) {
            classify = getIntent().getStringExtra("classify");//有分类（饵料，技巧）传分类名称
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("topId"))) {
            topId = getIntent().getStringExtra("topId");
        }
        MyPicMethodUtil.initImagePicker();
        initTitle();
        initLv();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("gqf******", "----------onRestart---------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("gqf******", "----------onDestroy---------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("gqf******", "----------onResume---------");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d("gqf******", "----------onSaveInstanceState---------");
    }

    private void initTitle() {
        //setIvBack();
        setTvTitle(title);
        getIvBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回键
                isCaogao = true;
                backJugePublish();
            }
        });
        TextView tvRight = getTvRight();
        tvRight.setText("规则");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PublishPostActivity.this, CommenWebviewActivity.class)
                        .putExtra("title", "规则")
                        .putExtra("url", HttpUrl.URL + "?m=Home&c=Index&a=hfive&content_id=6"));
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
            startActivityForResult(new Intent(PublishPostActivity.this, SkillAndBaitTypeActivity.class)
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
            tvAddress.setText(SPUtils.getPreference(PublishPostActivity.this, "province") + SPUtils.getPreference(PublishPostActivity.this, "city"));
        }

        @OnClick({R.id.publish_post_tv_add_text, R.id.publish_post_tv_add_pic, R.id.publish_post_tv_camera})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.publish_post_tv_add_text:
                    //添加文字
                    mData.add(new ContentBean(false));
                    mAdapter.notifyDataSetChanged();
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(PublishPostActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

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

    private void backJugePublish() {
        //myProgressDialog.show();
        postTitle = headerViewHolder.edtTitle.getText().toString().trim();
        boolean isHavePic = false;
        if (TextUtils.isEmpty(postTitle)) {
//            showMessage("请输入标题");
//            myProgressDialog.dismiss();
            finish();
            return;
        }
        if ((!TextUtils.isEmpty(classify)) && TextUtils.isEmpty(classifyContent)) {
//            showMessage("请选择分类");
//            myProgressDialog.dismiss();
            finish();
            return;
        }
        for (ContentBean item : mData) {
            if (!item.isPic()) {
                if (TextUtils.isEmpty(item.getStr_content())) {
//                    showMessage("请输入内容");
//                    myProgressDialog.dismiss();
                    finish();
                    return;
                }
                if (item.getStr_content().length() < 3) {
//                    showMessage("每段内容最少3字");
//                    myProgressDialog.dismiss();
                    finish();
                    return;
                }
            } else {
                isHavePic = true;
            }
        }
        if (topId.equals(ID_BAIT) || topId.equals(ID_SKILL) || topId.equals(ID_TALk)) {
            if (!isHavePic) {
//                showMessage("至少上传一张图");
//                myProgressDialog.dismiss();
                finish();
                return;
            }
        }
        if (!isClick) {
            //isClick = true;
            //okHttpReleaseSkill();
//            showMessage("保存草稿");
//            myProgressDialog.dismiss();

            dialog();
            return;

        }
    }

    private void judgePublish() {
        myProgressDialog.show();
        postTitle = headerViewHolder.edtTitle.getText().toString().trim();
        boolean isHavePic = false;
        if (TextUtils.isEmpty(postTitle)) {
            showMessage("请输入标题");
            myProgressDialog.dismiss();
            return;
        }
        if ((!TextUtils.isEmpty(classify)) && TextUtils.isEmpty(classifyContent)) {
            showMessage("请选择分类");
            myProgressDialog.dismiss();
            return;
        }
        for (ContentBean item : mData) {
            if (!item.isPic()) {
                if (TextUtils.isEmpty(item.getStr_content())) {
                    showMessage("请输入内容");
                    myProgressDialog.dismiss();
                    return;
                }
                if (item.getStr_content().length() < 3) {
                    showMessage("每段内容最少3字");
                    myProgressDialog.dismiss();
                    return;
                }
            } else {
                isHavePic = true;
            }
        }
        if (topId.equals(ID_BAIT) || topId.equals(ID_SKILL) || topId.equals(ID_TALk)
                || topId.equals(ID_FISH_TOOL) || topId.equals(ID_LUYA)) {
            if (!isHavePic) {
                showMessage("至少上传一张图");
                myProgressDialog.dismiss();
                return;
            }
        }
        if (!isClick) {
            isClick = true;
            okHttpReleaseSkill();
        }

    }

    private void initLv() {
        mData = new ArrayList<>();
        mData.add(new ContentBean(false));
        View viewHeader = LayoutInflater.from(this).inflate(R.layout.layout_publish_post_header, null);
        View viewFooter = LayoutInflater.from(this).inflate(R.layout.layout_publish_post_footer, null);
        lv.addHeaderView(viewHeader);
        lv.addFooterView(viewFooter);
        headerViewHolder = new HeaderViewHolder(viewHeader);
        if (TextUtils.isEmpty(classify)) {
            headerViewHolder.rlSelClassify.setVisibility(View.GONE);
        } else {
            headerViewHolder.rlSelClassify.setVisibility(View.VISIBLE);
            headerViewHolder.tvClassify.setText(classify);
        }
        new FooterViewHolder(viewFooter);
        mAdapter = new PublishPostAdapter2(mData, PublishPostActivity.this, new PostEditCallBack() {
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
        Intent intent = new Intent(PublishPostActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    //相册
    private void album() {
        Intent intent = new Intent(PublishPostActivity.this, ImageGridActivity.class);
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
        commonOkhttp.putUrl(HttpUrl.releaseSkill);
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
        if (!TextUtils.isEmpty(catId)) {
            commonOkhttp.putParams("cat_id", catId);
        }
        commonOkhttp.putParams("sum", mData.size() + "");
        commonOkhttp.putParams("province", SPUtils.getPreference(this, "province"));
        commonOkhttp.putParams("city", SPUtils.getPreference(this, "city"));
        commonOkhttp.putParams("county", SPUtils.getPreference(this, "county"));
        commonOkhttp.putParams("is_caogao", isCaogao ? "2" : "1");//是否是草稿 2是 1否
        commonOkhttp.putCallback(new MyGenericsCallback<StateEntity>(PublishPostActivity.this, false) {
            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
                finishActivity();
                isClick = false;
                myProgressDialog.dismiss();
               /* if (isCaogao) {
                    startActivity(new Intent(PublishPostActivity.this, DraftsActivity.class));
                }*/
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

    /*是否保存草稿*/
    public void dialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(PublishPostActivity.this);

        dialog.setTitle("提示");
        dialog.setMessage("编辑未完成，是否保存草稿?");
        dialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isClick = true;
                        okHttpReleaseSkill();
                        dialog.dismiss();
                    }
                });
        dialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        PublishPostActivity.this.finish();
                    }
                });
        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            isCaogao = true;
            backJugePublish();
            //dialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
