package com.aiyakeji.mytest.widgets;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * @author CWQ
 * @date 2019-10-30
 * 可拖动layout
 */
public class FloatingLayout extends FrameLayout {

    private static final String TAG = "slideLayout";

    private int screenWidth;
    private int screenHeight;
    private int downY;
    private int downX;
    private int lastX;
    private int lastY;
    private boolean isDrag;

    public FloatingLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public FloatingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int rawX = (int) ev.getRawX();
        int rawY = (int) ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                downY = (int) ev.getRawY();
                isDrag = false;
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                isDrag = true;
                int dx = rawX - lastX;
                int dy = rawY - lastY;

                float x = getX() + dx;
                float y = getY() + dy;
                //边缘检测
                x = x < 0 ? 0 : x > screenWidth - getWidth() ? screenWidth - getWidth() : x;
                y = y < 0 ? 0 : y > screenHeight - getHeight() ? screenHeight - getHeight() : y;
                setX(x);
                setY(y);
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int distance = Math.max(Math.abs(rawX - downX), Math.abs(rawY - downY));
                if (distance < 8) {
                    isDrag = false;
                }
                break;
        }
        return isDrag || super.dispatchTouchEvent(ev);
    }
}
