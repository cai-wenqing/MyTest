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

    private int mInitOffset;


    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, SlidingCardLayout child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        int offset = getChildMeasureOffset(parent, child);
        int height = View.MeasureSpec.getSize(parentHeightMeasureSpec) - offset;
        child.measure(parentWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        return true;
    }

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
        SlidingCardLayout preChild = getPreviousChild(parent, child);
        if (null != preChild) {
            int offset = preChild.getTop() + preChild.getHeadHeight();
            child.offsetTopAndBottom(offset);
            Log.i("SlidingBehavior测试", "allOffset:" + offset + ",getTop:" + preChild.getTop() + ",getHead:" + preChild.getHeadHeight() + ",position:" + parent.indexOfChild(child));
        }
        mInitOffset = child.getTop();
        return true;
    }

    //获取上一个同等级同类型子控件
    private SlidingCardLayout getPreviousChild(CoordinatorLayout parent, SlidingCardLayout child) {
        int index = parent.indexOfChild(child);
        for (int i = index - 1; i >= 0; i--) {
            View v = parent.getChildAt(i);
            if (v instanceof SlidingCardLayout)
                return (SlidingCardLayout) v;
        }
        return null;
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
        child.offsetTopAndBottom(offset);

        consumed[1] = -offset;
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

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, SlidingCardLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }
}
