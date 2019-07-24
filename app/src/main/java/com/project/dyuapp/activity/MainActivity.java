package com.project.dyuapp.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.appupdate.APKVersionCodeUtils;
import com.project.dyuapp.appupdate.ApkUpdateUtils;
import com.project.dyuapp.appupdate.DialogUtils;
import com.project.dyuapp.appupdate.SDCardUtils;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.MymsgBean;
import com.project.dyuapp.entity.VersionBean;
import com.project.dyuapp.fragment.CityWideFragment;
import com.project.dyuapp.fragment.ForumFragment;
import com.project.dyuapp.fragment.HomeFragment;
import com.project.dyuapp.fragment.MeFragment;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.SPUtils;
import com.project.dyuapp.myutils.ScreenManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MyBaseActivity {

    @Bind(R.id.main_iv_1)
    ImageView mainIv1;
    @Bind(R.id.main_tv_1)
    TextView mainTv1;
    @Bind(R.id.main_ll_1)
    LinearLayout mainLl1;

    @Bind(R.id.main_iv_2)
    ImageView mainIv2;
    @Bind(R.id.main_tv_2)
    TextView mainTv2;
    @Bind(R.id.main_ll_2)
    LinearLayout mainLl2;

    @Bind(R.id.main_iv_3)
    ImageView mainIv3;
    @Bind(R.id.main_ll_3)
    LinearLayout mainLl3;

    @Bind(R.id.main_iv_4)
    ImageView mainIv4;
    @Bind(R.id.main_tv_4)
    TextView mainTv4;
    @Bind(R.id.main_ll_4)
    LinearLayout mainLl4;

    @Bind(R.id.main_iv_5)
    ImageView mainIv5;
    @Bind(R.id.main_tv_5)
    TextView mainTv5;
    @Bind(R.id.main_ll_5)
    LinearLayout mainLl5;
    @Bind(R.id.main_iv_red_dot)
    ImageView mainIvRedDot;

    private HomeFragment homeFragment;
    private CityWideFragment cityWideFragment;
    private ForumFragment forumFragment;
    private MeFragment meFragment;
    private Fragment[] fragments;

    private int index = 0;//点击的页卡索引
    private int currentTabIndex = 0;//当前的页卡索引
    private long exitTime = 0;//记录上次点击返回按钮的时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initData();

        getVersion();
    }

    @Subscribe
    public void onEventMainThread(Integer flag) {
        if (flag == 0) {
            getMessage();
            meFragment.getMessage();
        }
    }

    //判断当前是否为退出操作后进入首页
    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (SPUtils.getPreference(this, "isOut") != null) {
            if ("1".equals(SPUtils.getPreference(this, "isOut"))) {
                index = 0;
                fragmentControl();
                SPUtils.savePreference(this, "isOut", "0");//1 是退出操作
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

    public void initData() {
        homeFragment = new HomeFragment();
        cityWideFragment = new CityWideFragment();
        forumFragment = new ForumFragment();
        meFragment = new MeFragment();
        fragments = new Fragment[]{homeFragment, cityWideFragment, forumFragment, meFragment};
        setBottomColor();
        getSupportFragmentManager().beginTransaction().add(R.id.main_fl_content, fragments[index]).show(fragments[index]).commit();
    }


    @SuppressLint("NewApi")
    @OnClick({R.id.main_ll_1, R.id.main_ll_2, R.id.main_ll_3, R.id.main_ll_4, R.id.main_ll_5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_ll_1:
                //首页
                index = 0;
                fragmentControl();
                break;
            case R.id.main_ll_2:
                //同城
                index = 1;
                fragmentControl();
                break;
            case R.id.main_ll_3:
                //发布
                if (!TextUtils.isEmpty(SPUtils.getPreference(this, "userid"))) {
                    goToActivity(PublishActivity.class);
                    overridePendingTransition(R.anim.anim_in, 0);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.main_ll_4:
                //论坛
                index = 2;
                fragmentControl();
                break;
            case R.id.main_ll_5:
                //我的
                if (!TextUtils.isEmpty(SPUtils.getPreference(MainActivity.this, "userid"))) {
                    //已登录
                    index = 3;
                    fragmentControl();
                } else {
                    //未登录
                    goToActivity(LoginActivity.class);
                }
                break;
        }
    }

    /**
     * 控制fragment的变化
     */
    public void fragmentControl() {
        if (currentTabIndex != index) {
            removeBottomColor();
            setBottomColor();

            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.main_fl_content, fragments[index]);
            }
            trx.show(fragments[index]).commit();
            currentTabIndex = index;
        }
    }

    /**
     * 设置底部栏按钮变色
     */
    private void setBottomColor() {
        switch (index) {
            case 0:
                mainIv1.setImageResource(R.mipmap.tab_home_selected);
                mainTv1.setTextColor(getResources().getColor(R.color.c_269ceb));
                break;
            case 1:
                mainIv2.setImageResource(R.mipmap.tab_find_selected);
                mainTv2.setTextColor(getResources().getColor(R.color.c_269ceb));
                break;
            case 2:
                mainIv4.setImageResource(R.mipmap.tab_forum_selected);
                mainTv4.setTextColor(getResources().getColor(R.color.c_269ceb));
                break;
            case 3:
                mainIv5.setImageResource(R.mipmap.tab_mine_selected);
                mainTv5.setTextColor(getResources().getColor(R.color.c_269ceb));
                break;
        }
    }

    /**
     * 清除底部栏颜色
     */
    private void removeBottomColor() {
        switch (currentTabIndex) {
            case 0:
                mainIv1.setImageResource(R.mipmap.tab_home_unselected);
                mainTv1.setTextColor(getResources().getColor(R.color.c_666666));
                break;
            case 1:
                mainIv2.setImageResource(R.mipmap.tab_find_unselected);
                mainTv2.setTextColor(getResources().getColor(R.color.c_666666));
                break;
            case 2:
                mainIv4.setImageResource(R.mipmap.tab_forum_unselected);
                mainTv4.setTextColor(getResources().getColor(R.color.c_666666));
                break;
            case 3:
                mainIv5.setImageResource(R.mipmap.tab_mine_unseleected);
                mainTv5.setTextColor(getResources().getColor(R.color.c_666666));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showMessage("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                ScreenManager.getInstance().finishAllActivityAndClose();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(SPUtils.getPreference(MainActivity.this, "userid"))) {
            //已登录
            getMessage();
        } else {
            //未登录
//            goToActivity(LoginActivity.class);
        }
    }

    /**
     * 获取未读消息数
     */
    private void getMessage() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.mymsg);
        commonOkhttp.putCallback(new MyGenericsCallback<MymsgBean>(this) {
            @Override
            public void onSuccess(MymsgBean data, int d) {
                super.onSuccess(data, d);
                //有消息时“我的”显示红点
                if ((!TextUtils.isEmpty(data.getSixin()) && !data.getSixin().equals("0")) ||
                        (!TextUtils.isEmpty(data.getReply()) && !data.getReply().equals("0")) ||
                        (!TextUtils.isEmpty(data.getFabulous()) && !data.getFabulous().equals("0")) ||
                        (!TextUtils.isEmpty(data.getReward()) && !data.getReward().equals("0")) ||
                        (!TextUtils.isEmpty(data.getSystem()) && !data.getSystem().equals("0"))) {
                    mainIvRedDot.setVisibility(View.VISIBLE);
                } else {
                    mainIvRedDot.setVisibility(View.GONE);
                }

            }
        });
        commonOkhttp.Execute();
    }

    public void getVersion() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.version);
        commonOkhttp.putCallback(new MyGenericsCallback<VersionBean>(this) {
            @Override
            public void onSuccess(final VersionBean data, int d) {
                super.onSuccess(data, d);

                if (data.getVersion().equals(APKVersionCodeUtils.getVerName(MainActivity.this))) {
                    Log.e("sssssssssss", "ok");
                } else {
                    Log.e("sssssssssss", "data = " + data.getLoad_url());
                    // showDialog(APKVersionCodeUtils.getVerName(MainActivity.this), data.getVersion(), data.getUpinfo(), data.getLoad_url());

                    DialogUtils.showDialog(MainActivity.this,
                            "发现新版本",
                            "当前版本：" + APKVersionCodeUtils.getVerName(MainActivity.this) + "\n" +
                                    "最新版本：" + data.getVersion() + "\n" + "更新内容：" + data.getUpinfo(),
                            new DialogUtils.OnDialogListener() {
                                @Override
                                public void confirm() {
                                    ApkUpdateUtils.download(MainActivity.this, "http://www.diaoyu8.com" + data.getLoad_url(), getResources().getString(R.string.app_name));
                                }

                                @Override
                                public void cancel() {

                                }
                            });
                }

                // String versionName = APKVersionCodeUtils.getVerName(MainActivity.this);
            }
        });
        commonOkhttp.Execute();
    }

    public void showDialog(String nowVersion, String newVersion, String content, final String url) {
        final Dialog dialog = new AlertDialog.Builder(MainActivity.this,
                R.style.CustomProgressDialog).create();
        final File file = new File(SDCardUtils.getRootDirectory() + "/diaoyu/diaoyu.apk");
        dialog.setCancelable(true);
        // 可以用“返回键”取消
        dialog.setCanceledOnTouchOutside(false);//
        dialog.show();
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.version_update_dialog, null);
        dialog.setContentView(view);

        final Button btnOk = (Button) view.findViewById(R.id.btn_update_id_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_update_id_cancel);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_update_content);
        TextView tvUpdateTile = (TextView) view.findViewById(R.id.tv_update_title);
        final TextView tvUpdateMsgSize = (TextView) view.findViewById(R.id.tv_update_msg_size);

        tvContent.setText("更新内容：" + content);//更新内容
        tvUpdateTile.setText("当前版本：" + nowVersion);
        tvUpdateMsgSize.setText("新版本：" + newVersion);

        if (file.exists() && file.getName().equals("diaoyu.apk")) {
            //tvUpdateMsgSize.setText("新版本已经下载，是否安装？");
        } else {
            //tvUpdateMsgSize.setText("新版本大小：" + versionInfo.getVersionSize());
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (v.getId() == R.id.btn_update_id_ok) {
                    ApkUpdateUtils.download(MainActivity.this, "http://www.diaoyu8.com/index.php" + url, getResources().getString(R.string.app_name));
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
