package com.aiyakeji.mytest.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.aiyakeji.mytest.R;

import java.util.Random;

/**
 * Created by Administrator on 2017/11/28 0028.
 * 波动圆
 */

public class WavedCircleView extends View {
    private int mViewWidth;
    private int mViewHeight;
    private int centerX;
    private int centerY;
    private int mStrokeRadius;

    private int mStrokeWidth = 1;
    private int mWaveWidth = 2;
    private int mStrokeColor = Color.BLUE;
    private int mWaveColor = Color.GREEN;
    private int mInnerColor = Color.GRAY;

    private WavePoint minStartPoint;
    private WavePoint maxStartPoint;
    private WavePoint curMinStartPoint1;
    private WavePoint controlPoint1;
    private WavePoint endPoint1;
    private WavePoint curMinStartPoint2;
    private WavePoint controlPoint2;
    private WavePoint endPoint2;
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private ValueAnimator animator;
    private float rate = 0;
    private boolean isFirst = true;
    private float random = (float) Math.random();


    public WavedCircleView(Context context) {
        this(context, null);
    }

    public WavedCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WavedCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WavedCircleView);
        mStrokeWidth = (int) typedArray.getDimension(R.styleable.WavedCircleView_waved_circle_stroke_width, 1f);
        mWaveWidth = (int) typedArray.getDimension(R.styleable.WavedCircleView_waved_circle_wave_width, 2f);
        mStrokeColor = typedArray.getColor(R.styleable.WavedCircleView_waved_circle_stroke_color, Color.BLUE);
        mWaveColor = typedArray.getColor(R.styleable.WavedCircleView_waved_circle_wave_color, Color.GREEN);
        mInnerColor = typedArray.getColor(R.styleable.WavedCircleView_waved_circle_wave_inner_color, Color.GRAY);
        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        centerX = mViewWidth / 2;
        centerY = mViewHeight / 2;
        mStrokeRadius = Math.min(centerX, centerY) - mWaveWidth - mStrokeWidth;
        minStartPoint = new WavePoint(centerX - mStrokeRadius - mStrokeWidth / 2, centerY);
        maxStartPoint = new WavePoint(0, centerY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        //画曲线块
        curMinStartPoint1 = getPointFromAngle(minStartPoint, mStrokeRadius + mStrokeWidth / 2, 2 * Math.PI * rate);
        controlPoint1 = getPointFromAngle(maxStartPoint, Math.min(centerX, centerY), Math.PI / 6 + 2 * Math.PI * rate);
        endPoint1 = getPointFromAngle(minStartPoint, mStrokeRadius + mStrokeWidth / 2, Math.PI / 3 + 2 * Math.PI * rate);
        mPath.reset();
        mPath.moveTo(curMinStartPoint1.x, curMinStartPoint1.y);
        mPath.quadTo(controlPoint1.x, controlPoint1.y, endPoint1.x, endPoint1.y);
        mPath.close();
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mWaveColor);
        canvas.drawPath(mPath, mPaint);

        rate = 2 * rate + random;
        curMinStartPoint2 = getPointFromAngle(minStartPoint, mStrokeRadius + mStrokeWidth / 2, 2 * Math.PI * rate);
        controlPoint2 = getPointFromAngle(maxStartPoint, Math.min(centerX, centerY), Math.PI / 6 + 2 * Math.PI * rate);
        endPoint2 = getPointFromAngle(minStartPoint, mStrokeRadius + mStrokeWidth / 2, Math.PI / 3 + 2 * Math.PI * rate);
        mPath.reset();
        mPath.moveTo(curMinStartPoint2.x, curMinStartPoint2.y);
        mPath.quadTo(controlPoint2.x, controlPoint2.y, endPoint2.x, endPoint2.y);
        mPath.close();
        mPaint.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mWaveColor);
        canvas.drawPath(mPath, mPaint);


        //画圆环
        mPaint.reset();
        mPaint.setColor(mStrokeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawCircle(centerX, centerY, mStrokeRadius, mPaint);

        //填充内圆
        mPaint.reset();
        mPaint.setColor(mInnerColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, mStrokeRadius - mStrokeWidth / 2, mPaint);

        if (isFirst) {
            isFirst = false;
            startAnimator();
        }
    }


    private WavePoint getPointFromAngle(WavePoint startPoint, int radius, double angle) {
        WavePoint point = new WavePoint();
        point.x = (float) (startPoint.x + (radius - radius * Math.cos(angle)));
        point.y = (float) (startPoint.y - radius * Math.sin(angle));
        return point;
    }


    private synchronized void startAnimator() {
        animator = ValueAnimator.ofInt(0, 360);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                rate = value / 360f;
//                Log.i("WavedCircleView测试", "value:" + value + ",rate:" + rate);
                invalidate();
            }
        });
        animator.setRepeatCount(Animation.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000 * 10);
        animator.start();
    }

    public synchronized void destroy() {
        if (null != animator && animator.isRunning()) {
            animator.removeAllUpdateListeners();
            animator.end();
            animator = null;
        }
    }
}
