package com.aiyakeji.mytest.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import java.util.Calendar;

/**
 * 自定义时钟
 */

public class ClockView extends View {
    private Paint mPaint;
    private float mWidth;
    private float mHeight;
    private float mRadius;
    private float mCenterX;
    private float mCenterY;

    private float mColorLineLenght = 40;
    private int mColorLineNum = 150;
    private float mPointRadius = 15;//红点半径
    private float outPadding = 40;//外圈大圆与边界距离（突出部分高度）

    private static final float[] CLOCK_SCALE_LINE_BASE_LEN_ARRAY = new float[]{
            0, 0.1F, 0.21F, 0.32F, 0.452F,
            0.551F, 0.6827F, 0.75F, 0.6827F, 0.551F,
            0.452F, 0.32F, 0.21F, 0.1F, 0};
    private String mTimeStr;
    private Calendar mCalendar;
    private float mInitPointAngle;

    private ValueAnimator animator;
    private float mPreLineAngle;
    private float mNowPointAngle;
    private float innerCircleRadius;//内圈圆半径
    private float mRemainderOfNowClockAngle;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mCalendar = Calendar.getInstance();
        mTimeStr = String.format("%02d:%02d", mCalendar.get(Calendar.HOUR), mCalendar.get(Calendar.MINUTE));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mRadius = Math.min(mWidth, mHeight) / 2 - outPadding;//半径
        // 中心点
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        mPreLineAngle = 360f / mColorLineNum;//旋转角度
        innerCircleRadius = mRadius - mColorLineLenght;//内圈圆半径
        float normalizedTimePeriod = mRemainderOfNowClockAngle / mPreLineAngle;
        //画红点
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.RED);
        canvas.rotate(mNowPointAngle, mCenterX, mCenterY);
        canvas.drawCircle(mCenterX, mCenterY - innerCircleRadius + mPointRadius + 10, mPointRadius, mPaint);

        //使canvas对准刻度线
        canvas.rotate(-mRemainderOfNowClockAngle - (CLOCK_SCALE_LINE_BASE_LEN_ARRAY.length - 3) / 2 * mPreLineAngle, mCenterX, mCenterY);

        //画特殊刻度
        mPaint.setColor(Color.WHITE);
        for (int index = CLOCK_SCALE_LINE_BASE_LEN_ARRAY.length - 2; index >= 0; index--) {
            //动态计算刻度长度
            float specialLineNowLen = (mColorLineLenght * (CLOCK_SCALE_LINE_BASE_LEN_ARRAY[index] + normalizedTimePeriod * (CLOCK_SCALE_LINE_BASE_LEN_ARRAY[index + 1] - CLOCK_SCALE_LINE_BASE_LEN_ARRAY[index])));
            canvas.drawLine(mCenterX, mCenterY - innerCircleRadius, mCenterX, mCenterY - mRadius - specialLineNowLen, mPaint);
            canvas.rotate(mPreLineAngle, mCenterX, mCenterY);
        }

        //画普通刻度
        for (int i = 0; i < mColorLineNum; i++) {
            canvas.drawLine(mCenterX, mCenterY - innerCircleRadius, mCenterX, mCenterY - mRadius, mPaint);
            canvas.rotate(mPreLineAngle, mCenterX, mCenterY);
        }

        canvas.restore();
        //画时间数字
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(150);
        Rect textBound = new Rect();
        mPaint.getTextBounds(mTimeStr, 0, mTimeStr.length(), textBound);
        float textStart = (mWidth - textBound.width()) / 2;
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float baseLine = (mHeight / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);

        if (mNowPointAngle >= 350) {
            long currentTimeMillis = System.currentTimeMillis();
            mCalendar.setTimeInMillis(currentTimeMillis);
            mTimeStr = String.format("%02d:%02d", mCalendar.get(Calendar.HOUR), mCalendar.get(Calendar.MINUTE));
        }
        canvas.drawText(mTimeStr, textStart, baseLine, mPaint);
    }

    /**
     * 开始动画
     */
    public synchronized void start() {
        animator = ValueAnimator.ofFloat(0, 360);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mNowPointAngle = value + mInitPointAngle;
                mNowPointAngle = mNowPointAngle % 360;
                mRemainderOfNowClockAngle = mNowPointAngle % mPreLineAngle;
                invalidate();
            }
        });

        animator.setRepeatCount(Animation.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000 * 60);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                long currentTimeMillis = System.currentTimeMillis();
                mCalendar.setTimeInMillis(currentTimeMillis);
                mTimeStr = String.format("%02d:%02d", mCalendar.get(Calendar.HOUR), mCalendar.get(Calendar.MINUTE));
                mInitPointAngle = (mCalendar.get(Calendar.SECOND) + (float) (mCalendar.get(Calendar.MILLISECOND) / 1000)) * 360 / 60;
            }
        });
        animator.start();
    }

    public synchronized void stop() {
        if (null != animator) {
            animator.removeAllUpdateListeners();
            animator.cancel();
            animator = null;
        }
    }
}
