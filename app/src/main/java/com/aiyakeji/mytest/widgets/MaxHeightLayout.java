package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.aiyakeji.mytest.R;

/**
 * @author caiwenqing
 * @data 2018/6/8
 * description:动态 最大高度linearLayout
 */
public class MaxHeightLayout extends LinearLayout {

    private static final float DEFAULT_MAX_RATIO = 0.6f;
    private static final float DEFAULT_MAX_HEIGHT = 0f;

    private float mMaxRatio = DEFAULT_MAX_RATIO;
    private float mMaxHeight = DEFAULT_MAX_HEIGHT;

    public MaxHeightLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public MaxHeightLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MaxHeightLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.MaxHeightLayout);
            final int count = a.getIndexCount();
            for (int i = 0; i < count; ++i) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.MaxHeightLayout_mhl_HeightRatio) {
                    mMaxRatio = a.getFloat(attr, DEFAULT_MAX_RATIO);
                } else if (attr == R.styleable.MaxHeightLayout_mhl_HeightDimen) {
                    mMaxHeight = a.getDimension(attr, DEFAULT_MAX_HEIGHT);
                }
            }
            a.recycle();
        }

        if (mMaxHeight <= 0) {
            mMaxHeight = mMaxRatio * (float) getScreenHeight(getContext());
        } else {
            mMaxHeight = Math.min(mMaxHeight, mMaxRatio * (float) getScreenHeight(getContext()));
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        heightSize = heightSize <= mMaxHeight ? heightSize
                : (int) mMaxHeight;

        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
                heightMode);
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
    }

    private int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
}
