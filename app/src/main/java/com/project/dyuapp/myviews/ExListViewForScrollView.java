package com.project.dyuapp.myviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * @author Administrator
 * @created on 2017/8/10 17:18
 * @description GDP
 * @change ${}
 */

public class ExListViewForScrollView extends ExpandableListView{
    public ExListViewForScrollView(Context context) {
        super(context);
    }

    public ExListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExListViewForScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
