package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Author：CWQ
 * Date：2019/7/18
 * Desc：
 */
public class HorizontalScrollLayout extends ViewGroup {

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private float mTouchSlop;
    private float mLastX;
    private int mCurrentPage;
    private int mMiniVelocity;

    public HorizontalScrollLayout(Context context) {
        super(context);
        init(context);
    }

    public HorizontalScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        mMiniVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                child.layout(i * getWidth(), t, (i + 1) * getWidth(), b);
            }
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = mLastX - x;
                scrollBy((int) dx, 0);
                mLastX = x;
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (xVelocity > mMiniVelocity && mCurrentPage > 0) {
                    //右滑
                    scrollToPage(mCurrentPage - 1);
                } else if (xVelocity < -mMiniVelocity && mCurrentPage < (getChildCount() - 1)) {
                    scrollToPage(mCurrentPage + 1);
                } else {
                    int pageIndex = (getScrollX() + getWidth() / 2) / getWidth();
                    if (pageIndex > getChildCount() - 1) {
                        pageIndex = getChildCount() - 1;
                    }
                    scrollToPage(pageIndex);
                }
                if (mVelocityTracker != null){
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
        }
        return true;
    }


    private void scrollToPage(int page) {
        mCurrentPage = page;
        int disX = mCurrentPage * getWidth() - getScrollX();
        mScroller.startScroll(getScrollX(), 0, disX, 0, Math.abs(disX));
        invalidate();
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
