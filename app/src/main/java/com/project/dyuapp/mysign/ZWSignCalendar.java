package com.project.dyuapp.mysign;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;

/**
 * 功能：
 */
class ZWSignCalendar extends View {

    private String[] weekTitles = new String[]{ "一", "二", "三", "四", "五", "六","日"};
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private Calendar calendar = Calendar.getInstance();
    //今天所在的年月日信息
    private int currentYear, currentMonth, currentDay;
    private GregorianCalendar date = new GregorianCalendar();
    private ZWSignCalendarView.Config config;
    private HashSet<String> signRecords;
    private HashSet<String> signRecords1;
    private Paint paint = new Paint();
    private Paint paint1 = new Paint();
    private LunarHelper lunarHelper;
    private int itemWidth, itemHeight;
    private float solarTextHeight;
    private int currentPosition;


    public ZWSignCalendar(Context context) {
        super(context);
    }

    public ZWSignCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZWSignCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void init(ZWSignCalendarView.Config config) {
        this.config = config;
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStrokeWidth(sp2px(1.0f));
        currentPosition = (currentYear - 1970) * 12 + currentMonth + 1;
        setClickable(true);
        if (config.isShowLunar) lunarHelper = new LunarHelper();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        itemWidth = getWidth() / 7;
        itemHeight = (getHeight() - (int) config.weekHeight) / 6;
        paint.setTextSize(config.calendarTextSize);
        solarTextHeight = getTextHeight();
        if (config.signSize == 0) config.signSize = Math.min(itemHeight, itemWidth);
    }

    final void selectDate(int position) {
        currentPosition = position - 1;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.LTGRAY);
        canvas.drawLine(0, config.weekHeight, 0, getHeight() - config.weekHeight, paint);
        //画日历顶部周的标题
        paint.setColor(config.weekBackgroundColor);
        canvas.drawRect(0, 0, getWidth(), config.weekHeight, paint);
        paint.setTextSize(config.weekTextSize);
        paint.setColor(config.weekTextColor);
        float delay = getTextHeight() / 4;
        for (int i = 0; i < 7; i++) {
            canvas.drawText(weekTitles[i], itemWidth * (i + 0.5f), config.weekHeight / 2 + delay, paint);
        }
        //画日历
        int year = 1970 + currentPosition / 12;
        int month = currentPosition % 12;
        calendar.set(year, month, 1);
        int firstDay = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        int selectMonthMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        delay = solarTextHeight / 4;
        if (config.isShowLunar) delay = 0;
        for (int i = 1; i <= selectMonthMaxDay; i++) {
            int position = i - 1 + firstDay;
            int x = (position % 7) * itemWidth + itemWidth / 2;
            int y = (position / 7) * itemHeight + itemHeight / 2 + (int) config.weekHeight + (int) delay;
            int day = i;
            //签到背景
            boolean isSign = false;
            if (signRecords != null) {
                date.set(year, month, day);
                String dateStr = format.format(date.getTime());
                if (signRecords.contains(dateStr)) {
                    paint.setColor(config.signColor);
                    canvas.drawText(String.valueOf(day), x, y, paint);
                    canvas.drawCircle(x, y-10, sp2px(config.signSize / 2-4), paint);
                    isSign = true;
                }
            }
            //农历
            if (config.isShowLunar) {
                paint.setColor(isSign ? config.signTextColor : config.lunarTextColor);
                String lunar = lunarHelper.SolarToLunarString(year, month + 1, day);
                paint.setTextSize(config.lunarTextSize);
                canvas.drawText(lunar, x, y + solarTextHeight * 2 / 3, paint);
            }
            //阳历
            if (isSign) {
                paint.setColor(config.signTextColor);
            } else if (year == currentYear && month == currentMonth && day == currentDay) {//今天
                paint.setColor(config.todayTextColor);
            } else {//其他天
                paint.setColor(config.calendarTextColor);
            }
            paint.setTextSize(config.calendarTextSize);
            canvas.drawText(String.valueOf(day), x, y, paint);

            if (signRecords1 != null) {
                date.set(year, month, day);
                String dateStr = format.format(date.getTime());
                if (signRecords1.contains(dateStr)) {
                    paint.setColor(config.signColor1);
                    canvas.drawCircle(x, y-6, config.signSize / 2, paint);

                    paint1.setColor(config.calendarTextColor);
                    paint1.setTextSize(config.calendarTextSize);
                    paint1.setAntiAlias(true);
                    paint1.setStyle(Paint.Style.FILL);
                    paint1.setTextAlign(Paint.Align.CENTER);
                    paint1.setStrokeWidth(sp2px(0.6f));
                    canvas.drawText(String.valueOf(day),x, y, paint1);
                    isSign = true;
                }
            }
        }
    }

    void setSignRecords(HashSet<String> signRecords) {
        this.signRecords = signRecords;
    }

    void setSignRecords1(HashSet<String> signRecords1) {
        this.signRecords1 = signRecords1;
    }

    private float sp2px(float spValue) {
        float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    private float getTextHeight() {
        return paint.descent() - paint.ascent();
    }


}
