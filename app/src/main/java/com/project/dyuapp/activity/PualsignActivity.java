package com.project.dyuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzy.imagepicker.bean.ImageItem;
import com.project.dyuapp.R;
import com.project.dyuapp.base.MyBaseActivity;
import com.project.dyuapp.callback.CommonOkhttp;
import com.project.dyuapp.callback.MyGenericsCallback;
import com.project.dyuapp.entity.NetBean;
import com.project.dyuapp.entity.SignInfoEntity;
import com.project.dyuapp.entity.StatusEntity;
import com.project.dyuapp.mysign.ZWSignCalendarView;
import com.project.dyuapp.myutils.GlideUtils;
import com.project.dyuapp.myutils.HttpUrl;
import com.project.dyuapp.myutils.MyPicMethodUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author shipeiyun
 * @description 个人中心-每日签到
 * @created at 2017/11/30 0030
 */
@SuppressWarnings("ResourceType")
public class PualsignActivity extends MyBaseActivity {

    @Bind(R.id.pualsign_civ_img)
    CircleImageView pualsignCivImg;//头像
    @Bind(R.id.pualsign_tv_name)
    TextView pualsignTvName;//昵称
    @Bind(R.id.pualsign_tv_grade)
    TextView pualsignTvGrade;//等级
    @Bind(R.id.pualsign_tv_gold)
    TextView pualsignTvGold;//金币
    @Bind(R.id.pualsign_tv_integral)
    TextView pualsignTvIntegral;//积分
    @Bind(R.id.pualsign_tv_from)
    TextView pualsignTvFrom;//现在等级
    @Bind(R.id.pualsign_bar)
    ProgressBar pualsignBar;//等级进度
    @Bind(R.id.pualsign_tv_to)
    TextView pualsignTvTo;//要升级的等级
    @Bind(R.id.pualsign_ll_integral)
    LinearLayout pualsignLlIntegral;
    @Bind(R.id.pualsign_ll_grade)
    LinearLayout pualsignLlGrade;
    @Bind(R.id.calendar_previous2)
    ImageView calendarPrevious2;
    @Bind(R.id.tv_calendar_show2)
    TextView tvCalendarShow2;
    @Bind(R.id.calendar_next2)
    ImageView calendarNext2;
    @Bind(R.id.calendarView2)
    ZWSignCalendarView calendarView2;
    @Bind(R.id.pualsign_tv_signin)
    TextView pualsignTvSignin;

    private int isSignIn; //是否签到
    private String level;//等级
    private String ym = "";//当前年月
    private String previousYm = "";//当前月份的上一个月
    HashSet<String> sign = new HashSet<>();//签到集合
    HashSet<String> signGray = new HashSet<>();//未签到集合
    private int days;
    private ArrayList<ImageItem> imageItems = new ArrayList<>();
    private String sdf;//当前年月
    private String ymd;//当前年月日
    private String signfrom;//当月签到的第一天
    private String str;//当月的所有日期

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_pualsign);
        ButterKnife.bind(this);
        setIvBack();
        setTvTitle("每日签到");
        initDate();
        okhttpSignInfo();
    }

    @OnClick({R.id.pualsign_civ_img, R.id.pualsign_ll_integral, R.id.pualsign_ll_grade, R.id.pualsign_tv_signin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pualsign_civ_img:
                preview();
                break;
            case R.id.pualsign_ll_integral:
                //积分规则
                startActivity(new Intent(this, CommenWebviewActivity.class)
                        .putExtra("title", "积分规则")
                        .putExtra("url", HttpUrl.integrationRule));
                break;
            case R.id.pualsign_ll_grade:
                //等级规则
                startActivity(new Intent(this, CommenWebviewActivity.class)
                        .putExtra("title", "等级规则")
                        .putExtra("url", HttpUrl.gradeRule));
                break;
            case R.id.pualsign_tv_signin:
                //签到
                if (isSignIn == 0) {
                    okhttpSignIn();
                }
                break;
        }
    }

    private void preview() {
        MyPicMethodUtil.previewHead(PualsignActivity.this,imageItems);
//        Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
//        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageItems);
//        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
//        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
//        intentPreview.putExtra("edit", false);
//        intentPreview.putExtra("isShowNum", false);
//        startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
    }

    private void initDate() {
        Calendar cal = Calendar.getInstance();
        int calYear = cal.get(Calendar.YEAR);//当前年
        int calMonth = (cal.get(Calendar.MONTH)) + 1;//当前月
        int calDay = cal.get(Calendar.DAY_OF_MONTH);//当前日
        sdf = calYear + "-" + calMonth;//当前年月
        ymd = calYear + "-" + calMonth + "-" + calDay;//当前年月日
        calendarView2.setDateListener(new ZWSignCalendarView.DateListener() {
            @Override
            public void change(int year, int month) {
                tvCalendarShow2.setText(String.format("%s 年 %s 月", year, month));
                ym = year + "-" + month;
                previousYm = year + "-" + (month - 1);//当前显示月份的上一个月
                days = getDaysByYearMonth(year, month);//当月的天数
            }
        });
        //上一个月
        findViewById(R.id.calendar_previous2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView2.showPreviousMonth();
                okhttpSignHistory();
                //是当前年月时，显示签到按钮
                if (!sdf.equals(ym)) {
                    pualsignTvSignin.setVisibility(View.GONE);
                } else {
                    pualsignTvSignin.setVisibility(View.VISIBLE);
                }
            }
        });
        //下一个月
        findViewById(R.id.calendar_next2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView2.showNextMonth();
                okhttpSignHistory();
                //是当前年月时，显示签到按钮
                if (!sdf.equals(ym)) {
                    pualsignTvSignin.setVisibility(View.GONE);
                } else {
                    pualsignTvSignin.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 11个人中心-每日签到-个人信息
     */
    private void okhttpSignInfo() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.signInfo);
        commonOkhttp.putCallback(new MyGenericsCallback<SignInfoEntity>(PualsignActivity.this) {
            @Override
            public void onSuccess(SignInfoEntity data, int d) {
                super.onSuccess(data, d);
                String img = data.getMember_list_headpic();//头像
                if (!TextUtils.isEmpty(img)) {
                    imageItems.clear();
                    imageItems.add(new ImageItem("", HttpUrl.IMAGE_URL + data.getMember_list_headpic()));
                    GlideUtils.loadImageViewHead(PualsignActivity.this, HttpUrl.IMAGE_URL + img, pualsignCivImg);
                } else {
                    pualsignCivImg.setImageResource(R.mipmap.mine_edit_head);
                }
                String name = data.getMember_list_nickname();//昵称
                if (!TextUtils.isEmpty(name)) {
                    pualsignTvName.setText(name);
                }
                level = data.getLevel();//等级
                if (!TextUtils.isEmpty(level)) {
                    pualsignTvGrade.setText("Lv." + level);
                    pualsignTvFrom.setText("Lv." + level);
                    pualsignTvTo.setText("Lv." + (Integer.parseInt(level) + 1) + "");//要升级的等级
                }
                String money = data.getMoney();//金币
                if (!TextUtils.isEmpty(money)) {
                    pualsignTvGold.setText(money);
                }
                String score = data.getScores();//积分
                if (!TextUtils.isEmpty(score)) {
                    pualsignTvIntegral.setText(score);
                }
                pualsignBar.setProgress(data.getSignPercentage());//设置等级进度
                isSignIn = data.getIs_sign();// 是否签到   1签到  0未签到
                if (isSignIn == 0) {
                    pualsignTvSignin.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_click_btn_blue));
                    pualsignTvSignin.setText("签到");
                } else {
                    pualsignTvSignin.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_gray_round1));
                    pualsignTvSignin.setText("今日已签到");
                }
                if ( data.getSign_log() != null&& data.getSign_log().size() >0) {
                    //签到集合
                    for (int i = 0; i < data.getSign_log().size(); i++) {
                        sign.add(data.getSign_log().get(i).getAdddate());
                    }
                    calendarView2.setSignRecords(sign);
                    //未签到
                    signfrom = data.getSign_log().get(0).getAdddate();//当月签到的第一天
                }
//                signHistory();//如果当前显示月份的上一个月有签到记录，则当前日期以前的都是未签到
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 12个人中心-每日签到-签到
     */
    private void okhttpSignIn() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.signIn);
        commonOkhttp.putParams("level", level);
        commonOkhttp.putCallback(new MyGenericsCallback<StatusEntity>(PualsignActivity.this) {
            @Override
            public void onSuccess(StatusEntity data, int d) {
                super.onSuccess(data, d);
                calendarView2.backCurrentMonth();
                okhttpSignInfo();
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 13个人中心-每日签到-查看历史记录
     */
    private void okhttpSignHistory() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.signHistory);
        commonOkhttp.putParams("ym", ym);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.SignHistoryListEntity>(PualsignActivity.this) {
            @Override
            public void onSuccess(NetBean.SignHistoryListEntity data, int d) {
                super.onSuccess(data, d);
                if (data != null && data.size() != 0) {
                    sign.clear();
                    signGray.clear();
                    //签到变蓝
                    for (int i = 0; i < data.size(); i++) {
                        sign.add(data.get(i).getAdddate());
                    }
                    calendarView2.setSignRecords(sign);
                    /*//未签到变灰
                    for (int j = 1; j <= days; j++) {
                        String str_day = String.format("%02d", j);
                        str = ym + "-" + str_day;//当月的所有日期
                        signfrom = data.get(0).getAdddate();//当月签到的第一天
                        if (!sign.contains(str) && str.compareTo(signfrom) >= 0 && str.compareTo(ymd) < 0) {
                            signGray.add(str);
                        }
                    }
                    calendarView2.setSignRecords1(signGray);*/
                }
            }
        });
        commonOkhttp.Execute();
    }

    /**
     * 13个人中心-每日签到-查看历史记录(预加载上一个月)
     */
    private void signHistory() {
        CommonOkhttp commonOkhttp = new CommonOkhttp();
        commonOkhttp.putUrl(HttpUrl.signHistory);
        commonOkhttp.putParams("ym", previousYm);
        commonOkhttp.putCallback(new MyGenericsCallback<NetBean.SignHistoryListEntity>(PualsignActivity.this) {
            @Override
            public void onSuccess(NetBean.SignHistoryListEntity data, int d) {
                super.onSuccess(data, d);
                //如果显示月份的上一个月有签到记录，当前日期以前的都是未签到
                if (!data.equals(null) && data.size() != 0 && !data.equals("")) {
                    for (int i = 1; i <= days; i++) {
                        String str_day = String.format("%02d", i);
                        String str = ym + "-" + str_day;//当月的所有日期
                        if (!sign.contains(str) && str.compareTo(ymd) < 0) {
                            signGray.add(str);
                        }
                    }
                    calendarView2.setSignRecords1(signGray);
                } else if (data.size() == 0 || data.equals(null) || data.equals("")) {
                    for (int j = 1; j <= days; j++) {
                        String str_day = String.format("%02d", j);
                        String str = ym + "-" + str_day;//当月的所有日期
                        if (sign!=null&&sign.size()>0){
                            if (!sign.contains(str) && str.compareTo(signfrom) >= 0 && str.compareTo(ymd) < 0) {
                                signGray.add(str);
                            }
                        }
                    }
                    calendarView2.setSignRecords1(signGray);
                }
            }
        });
        commonOkhttp.Execute();
    }

    //获取当月的天数
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

}
