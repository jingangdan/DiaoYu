package com.project.dyuapp.myutils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @功能描述： 压缩图片工具类
 * @Author： 刘晓丽
 * @data： 2017/4/5 16:47
 */

public class CompressImageUtil {

    /**
     * 压缩图片
     * @param tempPic 原始文件
     * @return
     */
    public static File radioImage(final File tempPic) {
        //文件原始路径
        final String pic_path = tempPic.getAbsolutePath();
        //压缩后文件的路径
        String targetPath = PublicStaticData.picFilePath + tempPic.getAbsolutePath() + ".jpg";
        //调用压缩图片的方法，返回压缩后的图片path
        final String compressImage = compressImage(pic_path, targetPath, 80);
        final File compressedPic = new File(compressImage);
        //如果压缩成功上传压缩后的图片
        if (compressedPic.exists()) {
            return compressedPic;
        } else {
            //如果压缩不成功上传压缩前的图片
            return tempPic;
        }
    }

    /**
     * 压缩图片
     * @param filePath 文件原始路径
     * @param targetPath targetPath
     * @param quality 压缩质量
     * @return
     */
    public static String compressImage(String filePath, String targetPath, int quality) {
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                //outputFile.createNewFile();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            //质量压缩
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (Exception e) {
        }
        return outputFile.getPath();
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     * @param filePath 原始文件路径
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算最佳缩放比
     * @param options 原始图片的参数
     * @param reqWidth 缩小后的宽
     * @param reqHeight 缩小后的高
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        //获取最佳缩放比
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 得到文件的真实路径
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
