package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.math.BigDecimal;

/**
 * @author caiwenqing
 * @data 2018/5/24
 * description:价格区间选择
 */
public class SlideProgressView extends View {

    private int mainColor = Color.parseColor("#c31f9b");
    private int grayColor = Color.parseColor("#f2f4f7");
    private int circleInnerColor = Color.WHITE;

    private Paint mPaint;
    private Paint circleStrokePaint;
    private float circleRadius = 40;
    private float lineStrokeWidth = 5;
    private float circleStrokeWidth = 3;
    private int maxNumber = 1000;
    private boolean hasInit = false;

    BigDecimal bigDecimal;

    private float mWidth;
    private float mHeight;
    private float maxLineWidth;

    private float lineStartX;
    private float lineEndX;
    private float downX, downY;
    private int flag = 0;


    public SlideProgressView(Context context) {
        super(context);
        init();
    }

    public SlideProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(30);

        circleStrokePaint = new Paint();
        circleStrokePaint.setAntiAlias(true);
        circleStrokePaint.setColor(mainColor);
        circleStrokePaint.setStyle(Paint.Style.STROKE);
        circleStrokePaint.setStrokeWidth(circleStrokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        if (circleRadius > mHeight / 2 - circleStrokeWidth) {
            circleRadius = mHeight / 2 - circleStrokeWidth;
        }
        maxLineWidth = mWidth - 2 * circleRadius - 2 * circleStrokeWidth;
        if (!hasInit) {
            lineStartX = circleRadius + circleStrokeWidth;
            lineEndX = mWidth - circleRadius - circleStrokeWidth;
            hasInit = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //灰色条
        mPaint.setColor(grayColor);
        mPaint.setStrokeWidth(lineStrokeWidth);
        canvas.drawLine(circleRadius + circleStrokeWidth, mHeight / 2, mWidth - circleRadius - circleStrokeWidth, mHeight / 2, mPaint);

        //选中条
        mPaint.setColor(mainColor);
        canvas.drawLine(lineStartX, mHeight / 2, lineEndX, mHeight / 2, mPaint);

        //起点和终点圆
        mPaint.setColor(circleInnerColor);

        if (flag == 1) {
            canvas.drawCircle(lineEndX, mHeight / 2, circleRadius, mPaint);
            canvas.drawCircle(lineEndX, mHeight / 2, circleRadius, circleStrokePaint);
            canvas.drawCircle(lineStartX, mHeight / 2, circleRadius, mPaint);
            canvas.drawCircle(lineStartX, mHeight / 2, circleRadius, circleStrokePaint);
        } else {
            canvas.drawCircle(lineStartX, mHeight / 2, circleRadius, mPaint);
            canvas.drawCircle(lineStartX, mHeight / 2, circleRadius, circleStrokePaint);
            canvas.drawCircle(lineEndX, mHeight / 2, circleRadius, mPaint);
            canvas.drawCircle(lineEndX, mHeight / 2, circleRadius, circleStrokePaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                flag = justInCircle(downX, downY);
                break;
            case MotionEvent.ACTION_MOVE:
                if (flag != 0) {
                    if (flag == 1) {
                        if (event.getX() >= circleRadius + circleStrokeWidth && event.getX() <= lineEndX) {
                            lineStartX = event.getX();
                        } else if (event.getX() < circleRadius + circleStrokeWidth) {
                            lineStartX = circleRadius + circleStrokeWidth;
                        } else if (event.getX() > lineEndX) {
                            lineStartX = lineEndX;
                        }

                        float startRate = (lineStartX - circleRadius - circleStrokeWidth) / (maxLineWidth - maxLineWidth / 100);
                        bigDecimal = new BigDecimal(startRate);
                        startRate = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

                        String startNum;
                        if (maxNumber * startRate <= maxNumber) {
                            startNum = "" + (int) (maxNumber * startRate);
                        } else {
                            startNum = maxNumber + "+";
                        }
                        invalidate();
                        if (mListener != null) {
                            mListener.onStartChange(startNum);
                        }
                    } else if (flag == 2) {
                        if (event.getX() >= lineStartX && event.getX() <= mWidth - circleRadius - circleStrokeWidth) {
                            lineEndX = event.getX();
                        } else if (event.getX() < lineStartX) {
                            lineEndX = lineStartX;
                        } else if (event.getX() > mWidth - circleRadius - circleStrokeWidth) {
                            lineEndX = mWidth - circleRadius - circleStrokeWidth;
                        }

                        float endRate = (lineEndX - circleRadius - circleStrokeWidth) / (maxLineWidth - maxLineWidth / 100);
                        bigDecimal = new BigDecimal(endRate);
                        endRate = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                        String endNum;
                        if (maxNumber * endRate <= maxNumber) {
                            endNum = "" + (int) (maxNumber * endRate);
                        } else {
                            endNum = maxNumber + "+";
                        }
                        invalidate();
                        if (mListener != null) {
                            mListener.onEndChange(endNum);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                flag = 0;
                break;
            default:
                break;
        }
        return true;
    }


    /**
     * 判断是否在起点圆或重点圆内
     *
     * @param x 触摸点x轴坐标
     * @param y y轴坐标
     * @return 1：在起点圆内，2：在终点圆内，0：不在圆内
     */
    private int justInCircle(float x, float y) {
        int result = 0;
        if (Math.abs(mHeight / 2 - y) <= circleRadius) {
            if (Math.abs(lineStartX - x) <= circleRadius) {
                result = 1;
            }

            if (Math.abs(lineEndX - x) <= circleRadius) {
                if (result == 1) {
                    //同时在起点和终点圆内，判断距离更近
                    if (Math.abs(lineStartX - x) < Math.abs(lineEndX - x)) {
                        result = 1;
                    } else if (Math.abs(lineStartX - x) == Math.abs(lineEndX - x)) {
                        if (lineEndX == circleRadius) {
                            result = 2;
                        } else {
                            result = 1;
                        }
                    } else {
                        result = 2;
                    }
                } else {
                    result = 2;
                }
            }
        }
        return result;
    }


    public interface OnChangeListener {
        void onStartChange(String startNum);

        void onEndChange(String endNum);
    }

    private OnChangeListener mListener;

    public void setOnChangeListener(OnChangeListener listener) {
        mListener = listener;
    }

    /**
     * 设置最大值
     *
     * @param maxNumber 必须大于0
     */
    public void setMaxNumber(int maxNumber) {
        if (maxNumber > 0) {
            this.maxNumber = maxNumber;
            invalidate();
        }
    }


    /**
     * 重置，默认开始位置为0，结束位置为最大值
     */
    public void resetView() {
        lineStartX = circleRadius + circleStrokeWidth;
        lineEndX = mWidth - circleRadius - circleStrokeWidth;
        invalidate();
    }
}
