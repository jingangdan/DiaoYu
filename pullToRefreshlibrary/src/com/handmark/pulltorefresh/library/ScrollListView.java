package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by liyunhan on 2017/7/13.
 */

public class ScrollListView extends ListView {
    private boolean canScroll;
    public ScrollListView(Context context) {
        super(context);
    }

    public ScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(canScroll){
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }

//    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int expandSpec = MeasureSpec.makeMeasureSpec(
//                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }


    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }
}
