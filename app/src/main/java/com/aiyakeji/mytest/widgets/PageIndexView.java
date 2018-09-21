package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author：Caiwenqing
 * Date：2018/9/21
 * Desc:Page切换指示器
 */
public class PageIndexView extends View {
    private float mWidth;
    private float mHeight;
    private Paint mNormalPaint;
    private Paint mSelectPaint;
    private int mNormalColor = Color.GRAY;
    private int mSelectColor = Color.RED;
    private float normalSize = 15;
    private float selectSize = 20;
    private float dis = 70;//圆点圆心之间距离

    private int mPointCount;
    private int mSelectIndex;

    public PageIndexView(Context context) {
        this(context, null);
    }

    public PageIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mNormalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNormalPaint.setColor(mNormalColor);
        mSelectPaint.setColor(mSelectColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPointCount > 0) {
            float allPointWidth = (mPointCount - 1) * dis;
            float firstPointX = mWidth / 2 - allPointWidth / 2;
            for (int i = 0; i < mPointCount; i++) {
                if (i == mSelectIndex) {
                    canvas.drawCircle(firstPointX + i * dis, mHeight / 2, selectSize, mSelectPaint);
                } else {
                    canvas.drawCircle(firstPointX + i * dis, mHeight / 2, normalSize, mNormalPaint);
                }
            }
        }
    }

    /**
     * 设置点数
     *
     * @param count
     */
    public void setPointCount(int count) {
        if (count > 0) {
            mPointCount = count;
            invalidate();
        }
    }

    /**
     * 设置当前选中位置
     *
     * @param index
     */
    public void setIndex(int index) {
        if (index >= 0 && index < mPointCount) {
            mSelectIndex = index;
            invalidate();
        }
    }


    /**
     * 设置圆点圆心之间距离
     *
     * @param dis
     */
    public void setPointCenterDis(int dis) {
        if (dis > 0) {
            this.dis = dis;
            invalidate();
        }
    }

    /**
     * 设置未选中颜色
     *
     * @param color
     */
    public void setNormalColor(int color) {
        mNormalColor = color;
        invalidate();
    }

    /**
     * 设置选中颜色
     *
     * @param color
     */
    public void setSelectColor(int color) {
        mSelectColor = color;
        invalidate();
    }


    /**
     * 设置圆点半径
     *
     * @param normalRadius 未选中圆点半径
     * @param selectRadius 选中圆点半径
     */
    public void setPointRadius(float normalRadius, float selectRadius) {
        if (normalRadius > 0 && selectRadius > 0) {
            normalSize = normalRadius;
            selectSize = selectRadius;
            invalidate();
        }
    }
}
