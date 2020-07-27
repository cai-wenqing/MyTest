package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.aiyakeji.mytest.R;

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
    private float normalRadius = 15;
    private float selectRadius = 20;
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
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PageIndexView);
        normalRadius = typedArray.getDimension(R.styleable.PageIndexView_piv_normal_point_radius, 15);
        selectRadius = typedArray.getDimension(R.styleable.PageIndexView_piv_select_point_radius, 20);
        mNormalColor = typedArray.getColor(R.styleable.PageIndexView_piv_normal_point_color, Color.GRAY);
        mSelectColor = typedArray.getColor(R.styleable.PageIndexView_piv_select_point_color, Color.RED);
        dis = typedArray.getDimension(R.styleable.PageIndexView_piv_point_center_distance, 70);
        typedArray.recycle();
    }


    private void init() {
        if (dis <= 2 * normalRadius) {
            dis = 2 * normalRadius + 30;
        }
        if (dis <= 2 * selectRadius) {
            dis = 2 * selectRadius + 30;
        }
        mNormalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNormalPaint.setColor(mNormalColor);
        mSelectPaint.setColor(mSelectColor);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int w = widthSpecSize;
        int h = heightSpecSize;

        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            h = (int) (2.1f * (normalRadius > selectRadius ? normalRadius : selectRadius));
        }
        setMeasuredDimension(w, h);

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
                    canvas.drawCircle(firstPointX + i * dis, mHeight / 2, selectRadius, mSelectPaint);
                } else {
                    canvas.drawCircle(firstPointX + i * dis, mHeight / 2, normalRadius, mNormalPaint);
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
}
