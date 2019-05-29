package com.project.dyuapp.myutils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.project.dyuapp.R;
import com.project.dyuapp.myviews.CycleWheelView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author 任伟
 * @describtion 选择时间（年月日时）
 * @used 时间选择器工具
 * @created at 2017/4/2 0002
 */
public class SelTimeTodayBeforeDialog {

    public static final float BACKGROUND_ALPHA = 0.8F;//背景透明度
    public static final int YEAR = 67;//时间选择器 年份范围 当前年-YEAR -- 当前年+YEAR
    private InputMethodManager imm;  // 键盘管理器
    private Window window;
    private Context context;
    private View view;

    private View clickView;
    private PopupWindow popDate;
    private CycleWheelView cwvYear, cwvMonth, cwvDay, cwvHour, cwvMinute, cwvSecond;

    //选择器要填充的数据
    private ArrayList<String> arryYear = new ArrayList<>();
    private ArrayList<String> arryMonth = new ArrayList<>();
    private ArrayList<String> arryDay = new ArrayList<>();
    private ArrayList<String> arryHour = new ArrayList<>();
    private ArrayList<String> arryMinute = new ArrayList<>();
    private ArrayList<String> arrySecond = new ArrayList<>();

    //滑动选择的时间 int类型
    public int intYearSel, intMonthSel, intDaySel, intHourSel, intMinuteSel, intSecondSel;
    public int todayYear, todayMonth, todayDay;

    private CallBack callBack;//完成按钮的回调方法
    boolean isNoDismiss;//pop去activity 消失

    public SelTimeTodayBeforeDialog(Context context, Window window, View clickView) {
        this.window = window;
        this.context = context;
        this.clickView = clickView;
        init();
    }

    public SelTimeTodayBeforeDialog(Context context, Window window, View clickView, boolean isNoDismiss) {
        this.window = window;
        this.context = context;
        this.clickView = clickView;
        this.isNoDismiss = isNoDismiss;
        init();
    }

    private void init() {
        initDefaltData();
        // 强制隐藏键盘
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(clickView.getWindowToken(), 0);

        backgroundAlpha(BACKGROUND_ALPHA);
        view = LayoutInflater.from(context).inflate(R.layout.pop_choice_time, null);
        if (popDate != null && popDate.isShowing()) {
            return;
        }
        popDate = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, window.getWindowManager().getDefaultDisplay().getHeight() * 1 / 3, true);
        initView();
        // 强制隐藏键盘
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        popDate.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        popDate.setOutsideTouchable(true);
        popDate.setBackgroundDrawable(new BitmapDrawable());
        popDate.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    public PopupWindow getPopDate() {
        return popDate;
    }

    private void initView() {
        //完成按钮
        TextView tvOk = (TextView) view.findViewById(R.id.pop_choice_time_tv_ok);
        tvOk.setVisibility(View.VISIBLE);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isNoDismiss) {
                    popDate.dismiss();
                }
                String year = "", month = "", day = "";
                year = "" + intYearSel;

                if (intMonthSel < 10) {
                    month = "0" + intMonthSel;
                } else {
                    month = "" + intMonthSel;
                }
                if (intDaySel < 10) {
                    day = "0" + intDaySel;
                } else {
                    day = "" + intDaySel;
                }

                callBack.onClick(view, year + "年" + month + "月" + day + "日");
                callBack.onClick(view, intYearSel, intMonthSel, intDaySel, intHourSel, intMinuteSel, intSecondSel);
            }
        });

        //初始化
        cwvYear = (CycleWheelView) view.findViewById(R.id.pop_choice_time_cwv_year);
        cwvMonth = (CycleWheelView) view.findViewById(R.id.pop_choice_time_cwv_month);
        cwvDay = (CycleWheelView) view.findViewById(R.id.pop_choice_time_cwv_day);
        cwvHour = (CycleWheelView) view.findViewById(R.id.pop_choice_time_cwv_hour);
        cwvMinute = (CycleWheelView) view.findViewById(R.id.pop_choice_time_cwv_minute);
        cwvSecond = (CycleWheelView) view.findViewById(R.id.pop_choice_time_cwv_second);


        //年
        setYear();
        cwvYear.setSelection(YEAR);
        cwvYear.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, String label) {

                intYearSel = Integer.parseInt(label.contains("年") ? label.substring(0, label.length() - 1) : label);
                Calendar c = Calendar.getInstance();
                intDaySel = c.get(Calendar.DAY_OF_MONTH);
                setDay();
                cwvDay.setSelection(intDaySel - 1);
            }
        });

        //月
        setMonth();
        cwvMonth.setSelection(intMonthSel);
        cwvMonth.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, String label) {
                intMonthSel = Integer.parseInt(label.contains("月") ? label.substring(0, label.length() - 1) : label);
                Calendar c = Calendar.getInstance();
                intDaySel = c.get(Calendar.DAY_OF_MONTH);
                setDay();
                cwvDay.setSelection(intDaySel - 1);
            }
        });

        //日
        setDay();

        cwvDay.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, String label) {
                intDaySel = Integer.parseInt(label.contains("日") ? label.substring(0, label.length() - 1) : label);
            }
        });


        //时
        setHour();
        cwvHour.setSelection(intHourSel);
        cwvHour.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, String label) {
                intHourSel = Integer.parseInt(label.contains("时") ? label.substring(0, label.length() - 1) : label);
            }
        });

        //分
        setMinute();
        cwvMinute.setSelection(intMinuteSel);
        cwvMinute.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, String label) {
                intMinuteSel = Integer.parseInt(label.contains("分") ? label.substring(0, label.length() - 1) : label);
            }
        });

        //秒
        setSecond();
        cwvSecond.setSelection(intSecondSel);
        cwvSecond.setOnWheelItemSelectedListener(new CycleWheelView.WheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, String label) {
                intSecondSel = Integer.parseInt(label.contains("秒") ? label.substring(0, label.length() - 1) : label);
            }
        });

    }

    //默认选择 系统时间
    private void initDefaltData() {
        Calendar c = Calendar.getInstance();
        intDaySel = c.get(Calendar.DAY_OF_MONTH);
        intMinuteSel = c.get(Calendar.MINUTE);
        intSecondSel = c.get(Calendar.SECOND);

        intYearSel = c.get(Calendar.YEAR);
        intMonthSel = c.get(Calendar.MONTH);
        intHourSel = c.get(Calendar.HOUR_OF_DAY);

        todayYear = c.get(Calendar.YEAR);
        todayMonth = c.get(Calendar.MONTH);
        todayDay = c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 设置年份数据
     */
    public void setYear() {
        arryYear.clear();
        for (int i = intYearSel - YEAR; i <= intYearSel + YEAR; i++) {
            if (i > todayYear) {
                break;
            }
            arryYear.add(i + "年");
        }
        cwvYear.setLabels(arryYear);
    }

    /**
     * 设置月份数据
     */
    public void setMonth() {
        arryMonth = new ArrayList<>();
        arryMonth.clear();
        for (int i = 1; i <= 12; i++) {
            String month = "";
            if (i > todayMonth+1) {
                break;
            }
            if (i < 10) {
                month = "0" + i;
            } else {
                month = "" + i;
            }
            arryMonth.add(month + "月");
        }
        cwvMonth.setLabels(arryMonth);
    }

    /**
     * 设置出天数
     */
    public void setDay() {
        int dayNum = 0;
        int month = intMonthSel;
         /*大月31天 小月30天 闰年2月28天 其余29天*/
        if (month == 1 | month == 3 | month == 5 | month == 7 | month == 8 | month == 10 | month == 12) {
            dayNum = 31;
        } else if (month == 4 | month == 6 | month == 9 | month == 11) {
            dayNum = 30;
        } else if (month == 2 && intYearSel % 4 == 0) {
            dayNum = 28;
        } else if (month == 2 && intYearSel % 4 != 0) {
            dayNum = 29;
        }
        arryDay.clear();
        for (int i = 1; i <= dayNum; i++) {
            String day = "";
            if (i > todayDay) {
                break;
            }
            if (i < 10) {
                day = "0" + i;
            } else {
                day = "" + i;
            }
            arryDay.add(day + "日");
        }
        cwvDay.setLabels(arryDay);
    }

    /**
     * 设置出小时数
     */
    public void setHour() {
        arryHour.clear();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                arryHour.add("0" + i);
            } else {
                arryHour.add(i + "时");
            }
        }
        cwvHour.setLabels(arryHour);
    }

    /**
     * 设置出分钟数
     */
    public void setMinute() {
        arryMinute.clear();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                arryMinute.add("0" + i);
            } else {
                arryMinute.add(i + "分");
            }
        }
        cwvMinute.setLabels(arryMinute);
    }

    /**
     * 设置出秒数
     */
    public void setSecond() {
        arrySecond.clear();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                arrySecond.add("0" + i);
            } else {
                arrySecond.add(i + "秒");
            }
        }
        cwvSecond.setLabels(arrySecond);
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void onClick(View view, String str);

        void onClick(View view, int intYearSel, int intMonthSel, int intDaySel, int intHourSel, int intMinuteSel, int intSecondSel);
    }

    //设置屏幕透明度
    public void backgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = alpha; //0.0-1.0
        window.setAttributes(lp);
    }

}
