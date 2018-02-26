package com.aiyakeji.mytest.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.aiyakeji.mytest.R;

/**
 * 自定义进度条显示
 * 可设置圆弧宽度、固定圆颜色（纯色），进度圆颜色（纯色或渐变色）、中间文字大小和颜色、是否显示文字
 */

public class CircleProgressView extends View {
    private int mViewWidth;
    private int mViewHeight;
    private int centerX;
    private int centerY;
    private int radius;

    private Paint mPaint = new Paint();
    private int mCircleColor = Color.BLUE;//大圆颜色
    private int mProgressColor = Color.RED;//进度圆颜色
    private int mRoundWidth = 1;//描边宽度
    private boolean isShowText = true;//是否显示中间文字

    public int[] SWEEP_GRADIENT_COLORS;//渐变色值集合
    private SweepGradient mColorShader;

    private long mDuration = 2000;
    private int mTextColor = 0XFFFC00D1;
    private int mTextSize = 20;

    private float mMaxNum;
    private float process;//百分值

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray attribute = getContext().obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        mCircleColor = attribute.getColor(R.styleable.CircleProgressView_circle_process_stroke_color, Color.BLUE);
        mProgressColor = attribute.getColor(R.styleable.CircleProgressView_circle_process_progress_color, Color.RED);
        mRoundWidth = (int) attribute.getDimension(R.styleable.CircleProgressView_circle_process_stroke_width, 1f);
        mTextColor = attribute.getColor(R.styleable.CircleProgressView_circle_process_text_color, 0XFFFC00D1);
        mTextSize = (int) attribute.getDimension(R.styleable.CircleProgressView_circle_process_text_size, 20f);
        mDuration = (long) attribute.getDimension(R.styleable.CircleProgressView_circle_process_duration, 2000f);
        isShowText = attribute.getBoolean(R.styleable.CircleProgressView_circle_precess_if_show_text, true);
        attribute.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        centerX = mViewWidth / 2;
        centerY = mViewHeight / 2;
        radius = Math.min(centerX, centerY) - mRoundWidth;//半径
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.reset();
        //画外圈大圆
        mPaint.setColor(mCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mRoundWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawCircle(centerX, centerY, radius, mPaint);

//        //缺口圆用
//        RectF bigRectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
//        canvas.drawArc(bigRectF, 135f, 270f, false, mPaint);

        //画进度圆
        mPaint.setColor(mProgressColor);
        if (null != SWEEP_GRADIENT_COLORS && SWEEP_GRADIENT_COLORS.length > 1) {
            mColorShader = new SweepGradient(radius, radius, SWEEP_GRADIENT_COLORS, null);//渐变色
            mPaint.setShader(mColorShader);
        }
        RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        float curRadius = process * 360;
        canvas.drawArc(rectF, 0f, curRadius, false, mPaint);

//        //缺口圆用
//        float curRadius = process * 270;
//        canvas.drawArc(rectF, 135f, curRadius, false, mPaint);

        //画文字
        if (isShowText) {
            mPaint.reset();
            mPaint.setAntiAlias(true);//抗锯齿
            mPaint.setColor(mTextColor);
            mPaint.setTextSize(mTextSize);
            String text = (int) (process * mMaxNum) + "/" + (int) mMaxNum;
            Rect textBound = new Rect();
            mPaint.getTextBounds(text, 0, text.length(), textBound);
            int textStart = (mViewWidth - textBound.width()) / 2;
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            //计算文字基线
            int baseLine = (int) (mViewHeight / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
            canvas.drawText(text, textStart, baseLine, mPaint);
        }
    }

    /**
     * 开始动画
     *
     * @param maxNum 最大值
     * @param curNum 当前值
     */
    public synchronized void start(final float maxNum, float curNum) {
        if (maxNum <= 0)
            throw new IllegalArgumentException("maxNum 必须大于0");
        if (curNum < 0)
            throw new IllegalArgumentException("curNum 不能小于0");
        if (curNum > maxNum)
            throw new IllegalArgumentException("curNum 不能大于maxNum");
        mMaxNum = maxNum;
        ValueAnimator animator = ValueAnimator.ofFloat(0, curNum);
        animator.setDuration(mDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                process = value / maxNum;
                invalidate();
            }
        });
        animator.start();
    }

    /**
     * 设置描边宽度
     *
     * @param width
     */
    public synchronized void setRoundWidth(int width) {
        if (width > 0) {
            mRoundWidth = width;
        }
    }

    /**
     * 设置动画执行时间，单位ms
     *
     * @param duration
     */
    public synchronized void setDuration(long duration) {
        if (duration > 0) {
            mDuration = duration;
        }
    }

    /**
     * 设置外圈大圆颜色
     *
     * @param color
     */
    public synchronized void setCircleColor(int color) {
        mCircleColor = color;
    }

    /**
     * 设置进度圆颜色
     *
     * @param color
     */
    public synchronized void setProgressColor(int color) {
        mProgressColor = color;
    }

    /**
     * 设置文字颜色
     *
     * @param color
     */
    public synchronized void setTextColor(int color) {
        mTextColor = color;
    }

    /**
     * 设置是否显示中间文字，默认显示
     *
     * @param b
     */
    public synchronized void isShowText(boolean b) {
        isShowText = b;
    }

    /**
     * 设置渐变色值
     *
     * @param colors
     */
    public synchronized void setGradientColors(int... colors) {
        if (colors.length >= 2) {
            SWEEP_GRADIENT_COLORS = new int[colors.length];
            for (int i = 0; i < colors.length; i++) {
                SWEEP_GRADIENT_COLORS[i] = colors[i];
            }
        } else {
            throw new IllegalArgumentException("渐变色值颜色设置不得少于2个");
        }
    }
}
