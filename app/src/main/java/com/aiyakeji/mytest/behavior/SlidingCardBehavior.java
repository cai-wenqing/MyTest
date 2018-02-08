package com.aiyakeji.mytest.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;

import com.aiyakeji.mytest.widgets.SlidingCardLayout;

/**
 * Created by Administrator on 2017/11/14 0014.
 * 滑动卡片Behavior
 */

public class SlidingCardBehavior extends CoordinatorLayout.Behavior<SlidingCardLayout> {
    private static final String TAG = "SlidingCardBehavior测试";
    private int mInitOffset;

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, SlidingCardLayout child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        int offset = getChildMeasureOffset(parent, child);
        int height = View.MeasureSpec.getSize(parentHeightMeasureSpec) - offset;
        child.measure(parentWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        return true;
    }

    //获取此页面上除自身外所有同等级view的头部高度之和
    private int getChildMeasureOffset(CoordinatorLayout parent, SlidingCardLayout child) {
        int offset = 0;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (view != child && view instanceof SlidingCardLayout) {
                offset += ((SlidingCardLayout) view).getHeadHeight();
            }
        }
        return offset;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, SlidingCardLayout child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        int offset = getBeforeChildOffset(parent, child);
        child.offsetTopAndBottom(offset);
        mInitOffset = child.getTop();
        return true;
    }

    //获取此view之前同等级view的头部高度之和
    private int getBeforeChildOffset(CoordinatorLayout parent, SlidingCardLayout child) {
        int offset = 0;
        int index = parent.indexOfChild(child);
        for (int i = index - 1; i >= 0; i--) {
            View v = parent.getChildAt(i);
            if (v instanceof SlidingCardLayout)
                offset += ((SlidingCardLayout) v).getHeadHeight();
        }
        return offset;
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, SlidingCardLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        boolean isVertical = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        return isVertical && child == directTargetChild;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, SlidingCardLayout child, View target, int dx, int dy, int[] consumed) {
        int minOffset = mInitOffset;
        int maxOffset = mInitOffset + child.getHeight() - child.getHeadHeight();
        int iniatialOffset = child.getTop();
        int offset = clamp(iniatialOffset - dy, minOffset, maxOffset) - iniatialOffset;
//        Log.i(TAG, "dy:" + dy + ",offset:" + offset);
        if (offset > 0 && !child.listCanScrollTop()) {//向下滑动且list已滑动到顶部时
            child.offsetTopAndBottom(offset);
        } else if (offset < 0) {//向上滑动时
            child.offsetTopAndBottom(offset);
            //修正view消耗的dy偏移量
            consumed[1] = -offset;
        }
        //多个卡片联动
        preScrollShiftSliding(-offset, coordinatorLayout, child);
    }

    //限制最大值和最小值
    private int clamp(int i, int minOffset, int maxOffset) {
        if (i > maxOffset)
            return maxOffset;
        else if (i < minOffset)
            return minOffset;
        else
            return i;
    }


    private void preScrollShiftSliding(int i, CoordinatorLayout parent, SlidingCardLayout child) {
        if (i == 0)
            return;
        else if (i > 0) {//上推
            SlidingCardLayout current = child;
            SlidingCardLayout previous = getPreChild(parent, current);
            while (null != previous) {
                int offset = previous.getTop() + previous.getHeadHeight() - current.getTop();
                if (offset > 0)
                    previous.offsetTopAndBottom(-offset);
                current = previous;
                previous = getPreChild(parent, current);
            }
        } else {//下拉
            SlidingCardLayout current = child;
            SlidingCardLayout next = getNextChild(parent, current);
            while (null != next) {
                int offset = current.getTop() + current.getHeadHeight() - next.getTop();
                if (offset > 0)
                    next.offsetTopAndBottom(offset);
                current = next;
                next = getNextChild(parent, current);
            }
        }
    }


    //获取上一个子view
    private SlidingCardLayout getPreChild(CoordinatorLayout parent, SlidingCardLayout child) {
        int index = parent.indexOfChild(child);
        for (int i = index - 1; i >= 0; i--) {
            View v = parent.getChildAt(i);
            if (v instanceof SlidingCardLayout)
                return (SlidingCardLayout) v;
        }
        return null;
    }

    //获取下一个子view
    private SlidingCardLayout getNextChild(CoordinatorLayout parent, SlidingCardLayout child) {
        int index = parent.indexOfChild(child);
        for (int i = index + 1; i < parent.getChildCount(); i++) {
            View v = parent.getChildAt(i);
            if (v instanceof SlidingCardLayout)
                return (SlidingCardLayout) v;
        }
        return null;
    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, SlidingCardLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }


    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, SlidingCardLayout child, View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }
}
