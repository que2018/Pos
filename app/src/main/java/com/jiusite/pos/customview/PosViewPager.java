package com.jiusite.pos.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class PosViewPager extends ViewPager {

    private boolean pageingEnabled = true;

    public PosViewPager(Context context) {
        super(context);
    }

    public PosViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.pageingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.pageingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean pageingEnabled) {
        this.pageingEnabled = pageingEnabled;
    }
}