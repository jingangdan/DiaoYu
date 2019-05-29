package com.project.dyuapp.myutils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.project.dyuapp.R;
import com.project.dyuapp.activity.VideoDetailsActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.EmptyResultBean;
import com.project.dyuapp.myviews.CustomDialog;
import com.project.dyuapp.runtimepermissions.PermissionUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * @describe：分享功能通用工具类
 * @author：刘晓丽
 * @createdate：2017/9/26 16:04
 */
public class ShareUtil {

    private Context context;
    private Activity activity;
    private String title = "";
    private String content = "";
    private String shareUrl = "";
    private String pic = "";
    private CustomDialog mDialog;

    public ShareUtil(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        checkPermission();
    }

    public ShareUtil(Context context, Activity activity, String title, String content, String shareUrl) {
        this.context = context;
        this.activity = activity;
        this.title = title;
        this.content = content;
        this.shareUrl = shareUrl;
        checkPermission();
    }

    public ShareUtil(Context context, Activity activity, String title, String content, String shareUrl,String pic) {
        this.context = context;
        this.activity = activity;
        this.title = title;
        this.content = content;
        this.shareUrl = shareUrl;
        this.pic = pic;
        checkPermission();
    }

    private void checkPermission() {
        PermissionUtils.multiplePermission(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, "您需要同意存储权限以使用分享功能", new PermissionUtils.OnPermissionResult() {
            @Override
            public void onGranted() {
                //请求权限成功
                showShareDialog();
            }
        });
    }

    /**
     * 转发弹框
     */
    private void showShareDialog() {
        mDialog = new CustomDialog(context, R.layout.dialog_share, R.style.CustomDialogTheme,true);
        mDialog.setCanceledOnTouchOutside(true);
        TextView tvSina = (TextView) mDialog.findViewById(R.id.share_tv_sina);
        TextView tvFriends = (TextView) mDialog.findViewById(R.id.share_tv_weixin);
        TextView tvQq = (TextView) mDialog.findViewById(R.id.share_tv_qq);
        TextView tvWeChat = (TextView) mDialog.findViewById(R.id.share_tv_circle);
        TextView tvQzone = (TextView) mDialog.findViewById(R.id.share_tv_qzone);
        //新浪微博
        tvSina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(SHARE_MEDIA.SINA);
            }
        });
        //微信朋友圈
        tvWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UMShareAPI.get(context).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
                    Toast.makeText(context, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                    return;
                }
                share(SHARE_MEDIA.WEIXIN_CIRCLE);
            }
        });
        //QQ好友
        tvQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UMShareAPI.get(context).isInstall(activity, SHARE_MEDIA.QQ)) {
                    Toast.makeText(context, "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                    return;
                }
                share(SHARE_MEDIA.QQ);
            }
        });
        //微信好友
        tvFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UMShareAPI.get(context).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
                    Toast.makeText(context, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                    return;
                }
                share(SHARE_MEDIA.WEIXIN);
            }
        });
        //QQ空间
        tvQzone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UMShareAPI.get(context).isInstall(activity, SHARE_MEDIA.QQ)) {
                    Toast.makeText(context, "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
                    return;
                }
                share(SHARE_MEDIA.QZONE);
            }
        });
        mDialog.show();
    }

    private void share(SHARE_MEDIA mPlatform) {
        UMImage image = null;
        UMImage thumb = null;
        if(!TextUtils.isEmpty(pic)){
            if(activity.getClass().getName().equals("com.project.dyuapp.activity.VideoDetailsActivity")){
                image = new UMImage(context, pic);
                thumb = new UMImage(context, pic);
            }else {
                image = new UMImage(context, HttpUrl.IMAGE_URL+pic);
                thumb = new UMImage(context, HttpUrl.IMAGE_URL+pic);
            }
        }else {
            image = new UMImage(context, R.drawable.logo);
            thumb = new UMImage(context, R.drawable.logo);
        }
        image.setThumb(thumb);
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图

        UMWeb web = new UMWeb(shareUrl);
        web.setTitle(title);//标题
        web.setThumb(thumb);//缩略图
        web.setDescription(content);//描述

        new ShareAction(activity)
                .setPlatform(mPlatform)//传入平台
                //.withText("withText")//分享内容 这个没用了
                .withMedia(web)
                .setCallback(umShareListener)//回调监听器
                .share();
        mDialog.dismiss();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            MyLogUtils.logE("=====","========");
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            showShareRsult(platform, "分享成功");
            CommonOkhttp commonOkhttp = new CommonOkhttp();
            commonOkhttp.putUrl(HttpUrl.fshare);
            commonOkhttp.putParams("memberid",SPUtils.getPreference(context,"userid"));
            if(platform == SHARE_MEDIA.WEIXIN_CIRCLE){
                commonOkhttp.putParams("share_type","1");//1朋友圈  2新浪微博 3QQ空间
            }else if(platform == SHARE_MEDIA.SINA){
                commonOkhttp.putParams("share_type","2");//1朋友圈  2新浪微博 3QQ空间
            }else if(platform == SHARE_MEDIA.QZONE){
                commonOkhttp.putParams("share_type","3");//1朋友圈  2新浪微博 3QQ空间
            }
            commonOkhttp.putCallback(new MyGenericsCallback<EmptyResultBean>((Activity) context){
                @Override
                public void onSuccess(EmptyResultBean data, int d) {
                    super.onSuccess(data, d);

                }
            });
            commonOkhttp.Execute();

        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            showShareRsult(platform, "分享失败:" + t.getMessage());
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            //showShareRsult(platform, "分享取消");
            MyLogUtils.logE("++++++","++++++++++++");
        }
    };

    private void showShareRsult(SHARE_MEDIA platform, String result) {
        String mPlatform = "";
        switch (platform) {
            case QQ://QQ
                mPlatform = "QQ";
                break;
            case QZONE://QQ空间
                mPlatform = "QQ空间";
                break;
            case WEIXIN://微信
                mPlatform = "微信";
                break;
            case WEIXIN_CIRCLE://微信朋友圈
                mPlatform = "微信朋友圈";
                break;
            case SINA://新浪微博
                mPlatform = "新浪微博";
                break;
        }
        Toast.makeText(context, mPlatform + result, Toast.LENGTH_SHORT).show();
    }
}
