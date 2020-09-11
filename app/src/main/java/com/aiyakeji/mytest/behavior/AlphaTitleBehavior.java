package com.aiyakeji.mytest.behavior;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aiyakeji.mytest.R;

/**
 * @author caiwenqing
 * @data 2018/5/9
 * description:
 */
public class AlphaTitleBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "AlphaBehavior测试";

    public AlphaTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        boolean isVertical = (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        return isVertical;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
//        TextView left_TextView = child.findViewById(R.id.tv_address);
//        TextView center_TextView = child.findViewById(R.id.tv_search);
//
//        int scrollY = target.getScrollY();
//        Log.i(TAG, "scrollY:" + scrollY);
//        Log.i(TAG, "dx:" + dx + ",dy:" + dy);
//        if (scrollY >= 0 && scrollY <= 255) {
//            float rate = scrollY / 255f;
//            //滑动到顶部
//            if (scrollY == 0 && dy < 0) {
//                //设置背景颜色
//                child.setBackgroundColor(Color.argb(0, 255, 255, 0));
//
//                //左侧文字颜色
//                if (left_TextView != null) {
//                    left_TextView.setTextColor(Color.argb(255, 255, 255, 255));
//                }
//
//                //中间背景颜色
//                if (center_TextView != null) {
//                    center_TextView.setBackgroundColor(Color.argb(255, 255, 255, 255));
//                }
//            }else if (scrollY>0){
//                //设置背景颜色
//                child.setBackgroundColor(Color.argb(scrollY, 255, 255, 0));
//
//                //左侧文字颜色
//                if (left_TextView != null) {
//                    left_TextView.setTextColor(Color.argb(255, 255 - scrollY, 255 - scrollY, 255 - scrollY));
//                }
//
//                //中间背景颜色
//                int temp = 255 - (int) (rate * (255 - 240));
//                if (center_TextView != null) {
//                    center_TextView.setBackgroundColor(Color.argb(255, temp, temp, temp));
//                }
//            }
//        } else if (scrollY > 255) {
//            child.setBackgroundColor(Color.argb(255, 255, 255, 0));
//
//            if (left_TextView != null) {
//                left_TextView.setTextColor(Color.argb(255, 0, 0, 0));
//            }
//
//            if (center_TextView != null) {
//                center_TextView.setBackgroundColor(Color.argb(255, 240, 240, 240));
//            }
//        }
    }
}
