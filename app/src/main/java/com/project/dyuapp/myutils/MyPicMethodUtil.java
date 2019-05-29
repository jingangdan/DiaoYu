package com.project.dyuapp.myutils;

import android.app.Activity;
import android.content.Intent;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.imageloader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

/**
 * @author gengqiufang
 * @describtion 帖子图片选择器初始化
 * @created at 2017/11/30 0030
 */

public class MyPicMethodUtil {
    //初始化图片选择器
    public static void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(false);                   //是否按矩形区域保存
        imagePicker.setMultiMode(false);                      //多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
 /*       imagePicker.setFocusWidth(width);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(height);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(width);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(height);                         //保存文件的高度。单位像素*/
    }

    public static void preview(Activity activity, ArrayList<ImageItem> list, int position) {
        Intent intentPreview = new Intent(activity, ImagePreviewDelActivity.class);
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, list);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        intentPreview.putExtra("edit", false);
        intentPreview.putExtra("isSave", true);
        activity.startActivityForResult(intentPreview, PublicStaticData.REQUEST_CODE_PREVIEW);
    }

    public static void previewHead(Activity activity, ArrayList<ImageItem> list) {
        Intent intentPreview = new Intent(activity, ImagePreviewDelActivity.class);
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, list);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        intentPreview.putExtra("edit", false);
        intentPreview.putExtra("isShowNum", false);
        intentPreview.putExtra("isSave", true);
        activity.startActivityForResult(intentPreview, PublicStaticData.REQUEST_CODE_PREVIEW);
    }
}
