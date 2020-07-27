package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
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
    private int cellNumber = 50;
    private boolean hasInit = false;
    private boolean firstShow = true;

    BigDecimal bigDecimal;

    private float mWidth;
    private float mHeight;
    private float maxLineWidth;

    private float lineStartX;
    private float lineEndX;
    private float downX, downY;
    private int flag = 0;
    String startNum;
    String endNum;

    private float defaultStartValue;
    private float defaultEndValue;

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

        startNum = "0";
        endNum = "不限";

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!hasInit && defaultStartValue == 0 && (defaultEndValue == 0 || defaultEndValue == maxNumber + 1)) {
            lineStartX = circleRadius + circleStrokeWidth;
            lineEndX = mWidth - circleRadius - circleStrokeWidth;
            hasInit = true;
        } else if (!hasInit) {
            lineStartX = defaultStartValue / maxNumber * maxLineWidth + circleRadius + circleStrokeWidth;
            if (defaultEndValue > maxNumber) {
                lineEndX = mWidth - circleRadius - circleStrokeWidth;
            } else {
                lineEndX = defaultEndValue / maxNumber * maxLineWidth + circleRadius + circleStrokeWidth;
            }
            hasInit = true;
        }

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

        if (!(defaultStartValue == 0 && defaultEndValue == 0) && mListener != null && firstShow) {
            mListener.onStartChange((int) defaultStartValue + "");
            if (defaultEndValue > maxNumber) {
                mListener.onEndChange("不限");
            } else {
                mListener.onEndChange((int) defaultEndValue + "");
            }

            defaultStartValue = 0;
            defaultEndValue = 0;
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
                    moveEvent(event, justInCircle(event.getX(), -1));
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }


    private void moveEvent(MotionEvent event, int flag) {
        if (flag == 1) {
            if (event.getX() >= circleRadius + circleStrokeWidth && event.getX() <= (lineEndX - maxLineWidth / 100)) {
                lineStartX = event.getX();
            } else if (event.getX() < circleRadius + circleStrokeWidth) {
                lineStartX = circleRadius + circleStrokeWidth;
            } else if (event.getX() > (lineEndX - maxLineWidth / 100)) {
                lineStartX = lineEndX - maxLineWidth / 100;
            }

            float startRate = (lineStartX - circleRadius - circleStrokeWidth) / (maxLineWidth - maxLineWidth / 100);
            bigDecimal = new BigDecimal(startRate);
            startRate = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

            if (startRate <= 1) {
                if ((int) (startRate * 100) % (100 / (maxNumber / cellNumber)) == 0) {
                    if (endNum.equals("不限")) {
                        if (mListener != null) {
                            startNum = "" + (int) (maxNumber * startRate);
                            mListener.onStartChange(startNum);
                        }
                    } else {
                        if ((int) (maxNumber * startRate) < Integer.valueOf(endNum)) {
                            startNum = "" + (int) (maxNumber * startRate);
                            if (mListener != null) {
                                mListener.onStartChange(startNum);
                            }

                        }
                    }

                }
            } else {
                startNum = "不限";
                if (mListener != null) {
                    mListener.onStartChange(startNum);
                }
            }
            firstShow = false;
            invalidate();
        } else if (flag == 2) {
            if (event.getX() >= (lineStartX + maxLineWidth / 100) && event.getX() <= mWidth - circleRadius - circleStrokeWidth) {
                lineEndX = event.getX();
            } else if (event.getX() < (lineStartX + maxLineWidth / 100)) {
                lineEndX = lineStartX + maxLineWidth / 100;
            } else if (event.getX() > mWidth - circleRadius - circleStrokeWidth) {
                lineEndX = mWidth - circleRadius - circleStrokeWidth;
            }

            float endRate = (lineEndX - circleRadius - circleStrokeWidth) / (maxLineWidth - maxLineWidth / 100);
            bigDecimal = new BigDecimal(endRate);
            endRate = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

            if (endRate <= 1) {
                if ((int) (endRate * 100) % (100 / (maxNumber / cellNumber)) == 0) {
                    if ((int) (maxNumber * endRate) > Integer.valueOf(startNum)) {
                        endNum = "" + (int) (maxNumber * endRate);
                        if (mListener != null) {
                            mListener.onEndChange(endNum);
                        }
                    }
                }
            } else {
                endNum = "不限";
                if (mListener != null) {
                    mListener.onEndChange(endNum);
                }
            }
            firstShow = false;
            invalidate();
        }
    }

    /**
     * 判断是否在起点圆或重点圆内
     *
     * @param x 触摸点x轴坐标
     * @param y y轴坐标，若y<0则不考虑y轴坐标
     * @return 1：在起点圆内，2：在终点圆内，0：不在圆内
     */
    private int justInCircle(float x, float y) {
        int result = 0;
        if ((y > 0 && Math.abs(mHeight / 2 - y) <= circleRadius) || y < 0) {
            if (Math.abs(lineStartX - x) <= circleRadius && Math.abs(lineEndX - x) <= circleRadius) {
                if (Math.abs(lineStartX - x) < Math.abs(lineEndX - x)) {
                    result = 1;
                } else if (Math.abs(lineStartX - x) == Math.abs(lineEndX - x)) {
                    result = 0;
                } else {
                    result = 2;
                }
            } else if (Math.abs(lineStartX - x) <= circleRadius) {
                result = 1;
            } else if (Math.abs(lineEndX - x) <= circleRadius) {
                result = 2;
            } else {
                result = 0;
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

    /**
     * 设置当前进度
     *
     * @param
     */
    public void setValue(String startValue, String endValue) {
        float start = Float.valueOf(startValue);
        float end;
        if (endValue.endsWith("+") || endValue.endsWith("*") || endValue.equals("不限")) {
            end = maxNumber + 1;
        } else {
            end = Float.valueOf(endValue);
            if (end > maxNumber + 1) {
                end = maxNumber + 1;
            }
        }

        if (start >= end || start < 0) {
            return;
        }
        defaultStartValue = start;
        defaultEndValue = end;
        hasInit = false;
        firstShow = true;
        invalidate();
    }
}
