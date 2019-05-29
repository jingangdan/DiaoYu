package com.project.dyuapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.lzy.imagepicker.bean.ImageItem;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.MyQrcodeEntity;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyPicMethodUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author shipeiyun
 * @description 个人中心-设置-我的二维码
 * @created at 2017/11/28 0028
 */
public class MyCodeActivity extends MyBaseActivity {

    @Bind(R.id.my_code_iv_head)
    PorterShapeImageView myCodeIvHead;//头像
    @Bind(R.id.my_code_tv_name)
    TextView myCodeTvName;//昵称
    @Bind(R.id.my_code_tv_grade)
    TextView myCodeTvGrade;//等级
    @Bind(R.id.my_code_iv)
    ImageView myCodeIv;//二维码

    ArrayList<ImageItem> imageItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_my_code);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("我的二维码");
        okhttpMyQrcode();
    }

    /**
     * 15个人中心-设置-我的二维码
     */
    private void okhttpMyQrcode() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.myQrcode);
        commonOkhttp.putCallback(new MyGenericsCallback<MyQrcodeEntity>(MyCodeActivity.this) {
            @Override
            public void onSuccess(MyQrcodeEntity data, int d) {
                super.onSuccess(data, d);
                if (data != null) {
                    String imgHead = data.getMember_list_headpic();//头像
                    if (!TextUtils.isEmpty(imgHead)) {
                        Glide.with(MyCodeActivity.this).load(HttpUrl.IMAGE_URL + imgHead).into(myCodeIvHead);
                    } else {
                        myCodeIvHead.setImageResource(R.mipmap.mine_edit_head);
                    }
                    String name = data.getMember_list_nickname();//昵称
                    if (!TextUtils.isEmpty(name)) {
                        myCodeTvName.setText(name);
                    }
                    String grade = data.getLevel();//等级
                    if (!TextUtils.isEmpty(grade)) {
                        myCodeTvGrade.setText("Lv." + grade);
                    }
                    String img = data.getErcode();//二维码地址
                    if (!TextUtils.isEmpty(img)) {
                        Glide.with(MyCodeActivity.this).load(HttpUrl.IMAGE_URL + img).into(myCodeIv);
                        imageItems.clear();
                        imageItems.add(new ImageItem("", HttpUrl.IMAGE_URL + data.getMember_list_headpic()));
                    }
                }
            }
        });
        commonOkhttp.Execute();
    }

    @OnClick(R.id.my_code_iv_head)
    public void onViewClicked() {
        MyPicMethodUtil.previewHead(MyCodeActivity.this,imageItems);
 /*       Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageItems);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        intentPreview.putExtra("edit", false);
        intentPreview.putExtra("isShowNum", false);
    startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);*/
}
}
