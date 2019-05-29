package com.project.dyuapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.project.dyuapp.myutils.CompressImageUtil;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyPicMethodUtil;
import com.project.dyuapp.myutils.PublicStaticData;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.SelTimeTodayBeforeDialog;
import com.project.dyuapp.myviews.MyProgressDialog;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.project.dyuapp.myutils.PublicStaticData.CODE_BAIT;
import static com.project.dyuapp.myutils.PublicStaticData.CODE_FISH_METHOD;
import static com.project.dyuapp.myutils.PublicStaticData.CODE_FISH_SEED;
import static com.project.dyuapp.myutils.PublicStaticData.CODE_LENGHT;
import static com.project.dyuapp.myutils.PublicStaticData.CODE_PLACE_NAME;
import static com.project.dyuapp.myutils.PublicStaticData.CODE_PLACE_TYPE;
import static com.project.dyuapp.myutils.PublicStaticData.REQUEST_CODE_SELECT;

/**
 * @author gengqiufang
 * @describtion 发渔获
 * @created at 2017/11/29 0029
 */
public class PublishGetActivity extends MyBaseActivity {

    @Bind(R.id.publish_get_lv_add)
    ListView lv;

    @Bind(R.id.publish_post_tv_save)
    TextView tvSave;
    @Bind(R.id.publish_post_tv_publish)
    TextView tvPublish;

    private ArrayList<ContentBean> mData;
    private PublishPostAdapter2 mAdapter;
    private HeaderViewHolder headerViewHolder;

    private String postTitle = "", line = "", brand = "", time = "";
    private String selLenghId = "", selMethodId = "", selSeedId = "", selBaitId = "", selNameId = "", selTypeId = "";
    private boolean isCaogao;
    private boolean isHavePic;
    private MyProgressDialog myProgressDialog;
    private boolean isClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_publish_get);
        ButterKnife.bind(this);
        myProgressDialog = new MyProgressDialog(this);
        MyPicMethodUtil.initImagePicker();
        initTitle();
        initLv();
    }

    //初始化标题
    private void initTitle() {
        setIvBack();
        setTvTitle("发渔获");
        TextView tvRight = getTvRight();
        tvRight.setText("规则");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PublishGetActivity.this, CommenWebviewActivity.class)
                        .putExtra("title", "规则")
                        .putExtra("url", HttpUrl.URL + "?m=Home&c=Index&a=hfive&content_id=6"));
            }
        });
    }

    //初始化列表
    private void initLv() {
        mData = new ArrayList<>();
        mData.add(new ContentBean(false));
        View viewHeader = LayoutInflater.from(this).inflate(R.layout.layout_publish_post_get_header, null);
        View viewFooter = LayoutInflater.from(this).inflate(R.layout.layout_publish_post_footer, null);
        lv.addHeaderView(viewHeader);
        lv.addFooterView(viewFooter);
        headerViewHolder = new HeaderViewHolder(viewHeader);
        new FooterViewHolder(viewFooter);
        mAdapter = new PublishPostAdapter2(mData, this, new PostEditCallBack() {
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

    @OnClick({R.id.publish_post_tv_save, R.id.publish_post_tv_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish_post_tv_save:
                //保存草稿
                isCaogao = true;
                judgePublish();
                break;
            case R.id.publish_post_tv_publish:
                //立即发布
                judgePublish();
                break;
        }
    }

    private void judgePublish() {
        myProgressDialog.show();
        postTitle = headerViewHolder.edtTitle.getText().toString().trim();
        time = headerViewHolder.tvTimeContent.getText().toString().trim();
        String placeType = headerViewHolder.tvTypeContent.getText().toString().trim();
//        String placeName = headerViewHolder.tvNameContent.getText().toString().trim();
        String placeName = headerViewHolder.edtName.getText().toString().trim();
        String bait = headerViewHolder.tvBaitContent.getText().toString().trim();
        String seed = headerViewHolder.tvFishSeedContent.getText().toString().trim();
        String method = headerViewHolder.tvFishingMethodContent.getText().toString().trim();
        line = headerViewHolder.edtLine.getText().toString().trim();
        String lengh = headerViewHolder.tvLenghContent.getText().toString().trim();
        brand = headerViewHolder.edtBrand.getText().toString().trim();
//        boolean isHaveContent = false;
        if (TextUtils.isEmpty(postTitle)) {
            showMessage("请输入标题");
            myProgressDialog.dismiss();
            return;
        }
      /*  if (TextUtils.isEmpty(time)) {
            showMessage("请选择出钓时间");
            return;
        }
        if (TextUtils.isEmpty(placeType)) {
            showMessage("请选择钓场类型");
            return;
        }
        if (TextUtils.isEmpty(placeName)) {
            showMessage("请选择钓场");
            return;
        }
        if (TextUtils.isEmpty(bait)) {
            showMessage("请选择饵料");
            return;
        }
        if (TextUtils.isEmpty(seed)) {
            showMessage("请选择鱼种");
            return;
        }
        if (TextUtils.isEmpty(method)) {
            showMessage("请选择钓法");
            return;
        }
        if (TextUtils.isEmpty(line)) {
            showMessage("请输入线组");
            return;
        }
        if (TextUtils.isEmpty(lengh)) {
            showMessage("请选择钓竿长度");
            return;
        }
        if (TextUtils.isEmpty(brand)) {
            showMessage("请输入钓竿品牌");
            return;
        }*/
        for (ContentBean item : mData) {
            if (!item.isPic()) {
//                isHaveContent = true;
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
        if (!isHavePic) {
            showMessage("至少上传一张图");
            myProgressDialog.dismiss();
            return;
        }
        if (!isClick) {
            isClick = true;
            okHttpPublishFishing();
        }

    }

    //头布局
    class HeaderViewHolder {
        @Bind(R.id.publish_post_head_edt_title)
        EditText edtTitle;
        @Bind(R.id.publish_get_tv_time_content)
        TextView tvTimeContent;
        @Bind(R.id.publish_get_rl_sel_time)
        RelativeLayout rlSelTime;
        @Bind(R.id.publish_get_tv_type_content)
        TextView tvTypeContent;
        @Bind(R.id.publish_get_rl_type)
        RelativeLayout rlType;
        //        @Bind(R.id.publish_get_tv_name_content)
//        TextView tvNameContent;
        @Bind(R.id.publish_get_edt_name)
        EditText edtName;

        @Bind(R.id.publish_get_rl_name)
        RelativeLayout rlName;
        @Bind(R.id.publish_get_tv_bait_content)
        TextView tvBaitContent;
        @Bind(R.id.publish_get_rl_bait)
        RelativeLayout rlBait;
        @Bind(R.id.publish_get_tv_fish_seed_content)
        TextView tvFishSeedContent;
        @Bind(R.id.publish_get_rl_fish_seed)
        RelativeLayout rlFishSeed;
        @Bind(R.id.publish_get_tv_fishing_method_content)
        TextView tvFishingMethodContent;
        @Bind(R.id.publish_get_rl_fishing_method)
        RelativeLayout rlFishingMethod;
        @Bind(R.id.publish_get_edt_line)
        EditText edtLine;
        @Bind(R.id.publish_get_tv_lengh_content)
        TextView tvLenghContent;
        @Bind(R.id.publish_get_rl_lengh)
        RelativeLayout rlLengh;
        @Bind(R.id.publish_get_edt_brand)
        EditText edtBrand;

        public HeaderViewHolder(View headerRootView) {
            ButterKnife.bind(this, headerRootView);
        }

        @OnClick({R.id.publish_get_rl_sel_time, R.id.publish_get_rl_type, R.id.publish_get_rl_bait, R.id.publish_get_rl_fish_seed, R.id.publish_get_rl_fishing_method, R.id.publish_get_rl_lengh})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.publish_get_rl_sel_time:
                    //出钓时间
                    selectTime();
                    break;
                case R.id.publish_get_rl_type:
                    //钓场类型
                    startActivityForResult(new Intent(PublishGetActivity.this, SkillAndBaitTypeActivity.class)
                                    .putExtra("title", "钓场类型")
                                    .putExtra("topId", "72")
                                    .putExtra("isMulti", true)
                                    .putExtra("sel_id", selTypeId),
                            CODE_PLACE_TYPE);
                    break;
//                case R.id.publish_get_rl_name:
//                    //钓场名称
//                    startActivityForResult(new Intent(PublishGetActivity.this, SkillAndBaitTypeActivity.class)
//                                    .putExtra("title", "钓场名称")
//                                    .putExtra("isPlaceName", true)
//                                    .putExtra("sel_id", selNameId),
//                            CODE_PLACE_NAME);
//                    break;
                case R.id.publish_get_rl_bait:
                    //饵料
                    startActivityForResult(new Intent(PublishGetActivity.this, SkillAndBaitTypeActivity.class)
                                    .putExtra("title", "饵料")
                                    .putExtra("topId", "91")
                                    .putExtra("isMulti", true)
                                    .putExtra("sel_id", selBaitId),
                            CODE_BAIT);
                    break;
                case R.id.publish_get_rl_fish_seed:
                    //鱼种
                    startActivityForResult(new Intent(PublishGetActivity.this, SkillAndBaitTypeActivity.class)
                                    .putExtra("title", "鱼种")
                                    .putExtra("topId", "5")
                                    .putExtra("isMulti", true)
                                    .putExtra("sel_id", selSeedId),
                            CODE_FISH_SEED);
                    break;
                case R.id.publish_get_rl_fishing_method:
                    //钓法
                    startActivityForResult(new Intent(PublishGetActivity.this, SkillAndBaitTypeActivity.class)
                                    .putExtra("title", "钓法")
                                    .putExtra("topId", "6")
                                    .putExtra("isMulti", true)
                                    .putExtra("sel_id", selMethodId),
                            CODE_FISH_METHOD);
                    break;
                case R.id.publish_get_rl_lengh:
                    //钓竿长度
                    startActivityForResult(new Intent(PublishGetActivity.this, SkillAndBaitTypeActivity.class)
                                    .putExtra("title", "钓竿长度")
                                    .putExtra("topId", "79")
                                    .putExtra("isMulti", true)
                                    .putExtra("sel_id", selLenghId),
                            CODE_LENGHT);
                    break;
            }
        }
    }

    /**
     * 时间选择器弹出框
     */
    public void selectTime() {
        SelTimeTodayBeforeDialog dialog = new SelTimeTodayBeforeDialog(this, getWindow(), headerViewHolder.tvTimeContent);
        dialog.setCallBack(new SelTimeTodayBeforeDialog.CallBack() {
            @Override
            public void onClick(View view, String str) {
//                headerViewHolder.tvTimeContent.setText(str);
            }

            @Override
            public void onClick(View view, int intYearSel, int intMonthSel, int intDaySel, int intHourSel, int intMinuteSel, int intSecondSel) {
                headerViewHolder.tvTimeContent.setText(intYearSel + "-" + intMonthSel + "-" + intDaySel);
            }
        });
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
            tvAddress.setText(SPUtils.getPreference(PublishGetActivity.this, "province") + SPUtils.getPreference(PublishGetActivity.this, "city"));
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

    //相机
    private void camera() {
        Intent intent = new Intent(PublishGetActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    //相册
    private void album() {
        Intent intent = new Intent(PublishGetActivity.this, ImageGridActivity.class);
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
        } else if (requestCode == CODE_PLACE_TYPE && resultCode == Activity.RESULT_OK) {
            //钓场类型
            String selTypeContent = data.getStringExtra("sel");
            selTypeId = data.getStringExtra("sel_id");
            headerViewHolder.tvTypeContent.setText(selTypeContent);
        }
//        else if (requestCode == CODE_PLACE_NAME && resultCode == Activity.RESULT_OK) {
//            //钓场名称
//            String selNameContent = data.getStringExtra("sel");
//            selNameId = data.getStringExtra("sel_id");
//            headerViewHolder.tvNameContent.setText(selNameContent);
//        }
        else if (requestCode == CODE_BAIT && resultCode == Activity.RESULT_OK) {
            //饵料
            String selBaitContent = data.getStringExtra("sel");
            selBaitId = data.getStringExtra("sel_id");
            headerViewHolder.tvBaitContent.setText(selBaitContent);
        } else if (requestCode == CODE_FISH_SEED && resultCode == Activity.RESULT_OK) {
            //鱼种
            String selSeedContent = data.getStringExtra("sel");
            selSeedId = data.getStringExtra("sel_id");
            headerViewHolder.tvFishSeedContent.setText(selSeedContent);
        } else if (requestCode == CODE_FISH_METHOD && resultCode == Activity.RESULT_OK) {
            //钓法
            String selMethodContent = data.getStringExtra("sel");
            selMethodId = data.getStringExtra("sel_id");
            headerViewHolder.tvFishingMethodContent.setText(selMethodContent);
        } else if (requestCode == CODE_LENGHT && resultCode == Activity.RESULT_OK) {
            //钓竿长度
            String selLenghContent = data.getStringExtra("sel");
            selLenghId = data.getStringExtra("sel_id");
            headerViewHolder.tvLenghContent.setText(selLenghContent);
        }
    }

    //发布渔获
    public void okHttpPublishFishing() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.publishFishing);
        for (int i = 0; i < mData.size(); i++) {
            commonOkhttp.putParams("str_content" + i, mData.get(i).getStr_content());
            if (TextUtils.isEmpty(mData.get(i).getStr_imgs())) {
                commonOkhttp.putParams("str_imgs" + i, mData.get(i).getStr_imgs());
            } else {
                commonOkhttp.putFile("str_imgs" + i, mData.get(i).getStr_imgs(), new File(mData.get(i).getStr_imgs()));
            }
        }
        commonOkhttp.putParams("title", postTitle);
        commonOkhttp.putParams("fishing_time", time);//出钓时间
        commonOkhttp.putParams("ground_id", selNameId);//钓场id
        commonOkhttp.putParams("ground_type", selTypeId);//钓场类型
        commonOkhttp.putParams("erliao", selBaitId);//饵料id
        commonOkhttp.putParams("yuzhong", selSeedId);//鱼种id
        commonOkhttp.putParams("diaofa", selMethodId);//钓法id
        commonOkhttp.putParams("xiancu", line);//线粗
        commonOkhttp.putParams("diaogan_long", selLenghId);//吊杆长度id
        commonOkhttp.putParams("diaogan_brand", brand);//品牌

        commonOkhttp.putParams("province", SPUtils.getPreference(this, "province"));
        commonOkhttp.putParams("city", SPUtils.getPreference(this, "city"));
        commonOkhttp.putParams("county", SPUtils.getPreference(this, "county"));

        commonOkhttp.putParams("top_id", PublicStaticData.ID_GET_FISH);
//        commonOkhttp.putParams("cat_id", catId);
        commonOkhttp.putParams("sum", mData.size() + "");
        commonOkhttp.putParams("is_caogao", isCaogao ? "2" : "1");//是否是草稿 2是 1否
        commonOkhttp.putCallback(new MyGenericsCallback<StateEntity>(PublishGetActivity.this, false) {
            @Override
            public void onSuccessMessage(int code, String message, int d) {
                super.onSuccessMessage(code, message, d);
                showMessage(message);
                isClick = false;
                myProgressDialog.dismiss();
                finishActivity();
             /*   if (isCaogao) {
                    startActivity(new Intent(PublishGetActivity.this, DraftsActivity.class));
                }*/
            }

            @Override
            public void onOther(JsonResult<StateEntity> data, int d) {
                super.onOther(data, d);
                isClick = false;
                myProgressDialog.dismiss();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                isClick = false;
                myProgressDialog.dismiss();
            }
        });
        commonOkhttp.Execute();
    }

}
