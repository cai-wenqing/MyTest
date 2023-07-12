package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Author：CWQ
 * Date：2019/4/17
 * Desc:档位选择器
 */
public class StepNumberView extends View {
    private static final String TAG = "StepNumberView";

    private String[] levelsArr = {"0", "50", "100", "150", "200", "250", "300", "350", "400", "450", "500", "550", "不限"};

    private int mWidth;
    private int mHeight;
    private final float mTextSize = 40f;
    private float mSidePadding = mTextSize * 2;
    private final float mNormalCircleRadius = 10;
    private final float mSelectCircleRadius = 20;
    private final float mStrokeWidth = 6f;
    private float mSpaceInPoints;//圆心之间间距
    private final float mSpaceInTextPoint = 15f;//文字与最大圆点间距
    private Paint mPaint;
    private Rect mRect;
    private final int mNormalColor = Color.GRAY;
    private final int mSelectColor = Color.parseColor("#2ED3AF");
    private int mSelectPosition1 = 0;
    private int mSelectPosition2 = levelsArr.length - 1;
    private float mSelectPointOffset1 = 0;
    private float mSelectPointOffset2 = 0;
    private boolean isMoved1 = false;
    private boolean isMoved2 = false;
    float downX = 0;
    private OnPointChangeListener mListener;

    public StepNumberView(Context context) {
        this(context, null);
    }

    public StepNumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mNormalColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setTextSize(mTextSize);
        mRect = new Rect();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mSpaceInPoints = (mWidth - 2 * mSidePadding) / (levelsArr.length - 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(mNormalColor);
        canvas.drawLine(mSidePadding, mHeight / 2f, mWidth - mSidePadding, mHeight / 2f, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mNormalColor);
        //默认起点
        canvas.drawCircle(mSidePadding, mHeight / 2f, mNormalCircleRadius, mPaint);
        //默认终点
        canvas.drawCircle((levelsArr.length - 1) * mSpaceInPoints + mSidePadding, mHeight / 2f, mNormalCircleRadius, mPaint);
        //数字
        for (int i = 0; i < levelsArr.length; i = i + 2) {
            mPaint.setColor(mNormalColor);
            mPaint.getTextBounds(levelsArr[i], 0, levelsArr[i].length(), mRect);
            canvas.drawText(levelsArr[i], i * mSpaceInPoints - mRect.width() / 2f + mSidePadding, mHeight / 2f + mSelectCircleRadius + mRect.height() + mSpaceInTextPoint, mPaint);
        }
        //选中点1
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mSelectPosition1 * mSpaceInPoints + mSidePadding + mSelectPointOffset1, mHeight / 2f, mSelectCircleRadius, mPaint);
        mPaint.setColor(mSelectColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.drawCircle(mSelectPosition1 * mSpaceInPoints + mSidePadding + mSelectPointOffset1, mHeight / 2f, mSelectCircleRadius, mPaint);
        //选中点2
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mSelectPosition2 * mSpaceInPoints + mSidePadding + mSelectPointOffset2, mHeight / 2f, mSelectCircleRadius, mPaint);
        mPaint.setColor(mSelectColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.drawCircle(mSelectPosition2 * mSpaceInPoints + mSidePadding + mSelectPointOffset2, mHeight / 2f, mSelectCircleRadius, mPaint);

        //选中连接线
        float startCenterX = mSidePadding + mSelectPosition1 * mSpaceInPoints + mSelectPointOffset1;
        float stopCenterX = mSidePadding + mSelectPosition2 * mSpaceInPoints + mSelectPointOffset2;
        if (startCenterX < stopCenterX - 2 * mSelectCircleRadius) {
            canvas.drawLine(startCenterX + mSelectCircleRadius, mHeight / 2f, stopCenterX - mSelectCircleRadius, mHeight / 2f, mPaint);
        } else if (startCenterX > stopCenterX + 2 * mSelectCircleRadius) {
            canvas.drawLine(stopCenterX + mSelectCircleRadius, mHeight / 2f, startCenterX - mSelectCircleRadius, mHeight / 2f, mPaint);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float selectPoint1X = mSelectPosition1 * mSpaceInPoints + mSidePadding;
        float selectPoint2X = mSelectPosition2 * mSpaceInPoints + mSidePadding;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                if (Math.abs(selectPoint1X - downX) < 2 * mSelectCircleRadius && moveX >= mSidePadding && moveX <= (mWidth - mSidePadding)) {//校验点击范围
                    mSelectPointOffset1 = moveX - selectPoint1X;
                    invalidate();
                    isMoved1 = true;
                } else if (Math.abs(selectPoint2X - downX) < 2 * mSelectCircleRadius && moveX >= mSidePadding && moveX <= (mWidth - mSidePadding)) {
                    mSelectPointOffset2 = moveX - selectPoint2X;
                    invalidate();
                    isMoved2 = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float upX = event.getX();
                float upY = event.getY();
                mSelectPointOffset1 = 0;
                mSelectPointOffset2 = 0;
                if (isMoved1) {
                    float value = (upX - mSidePadding) % mSpaceInPoints;
                    int point = (int) ((upX - mSidePadding) / mSpaceInPoints);
                    if (value < mSpaceInPoints / 2) {
                        mSelectPosition1 = point;
                    } else {
                        mSelectPosition1 = point + 1;
                    }
                } else if (isMoved2) {
                    float value = (upX - mSidePadding) % mSpaceInPoints;
                    int point = (int) ((upX - mSidePadding) / mSpaceInPoints);
                    if (value < mSpaceInPoints / 2) {
                        mSelectPosition2 = point;
                    } else {
                        mSelectPosition2 = point + 1;
                    }
                } else {//点击时
                    if (upY > mHeight / 4f && upY < mHeight - mHeight / 4f) {
                        float value = (upX - mSidePadding) % mSpaceInPoints;
                        int point = (int) ((upX - mSidePadding) / mSpaceInPoints);
                        if (value < 2 * mSelectCircleRadius) {
                            //判断当前点击位置距离哪个点较近
                            if (Math.abs(downX - mSelectPosition1 * mSpaceInPoints) <= Math.abs(downX - mSelectPosition2 * mSpaceInPoints)) {
                                mSelectPosition1 = point;
                            } else {
                                mSelectPosition2 = point;
                            }
                        } else if (value > (mSpaceInPoints - 2 * mSelectCircleRadius)) {
                            if (Math.abs(downX - mSelectPosition1 * mSpaceInPoints) <= Math.abs(downX - mSelectPosition2 * mSpaceInPoints)) {
                                mSelectPosition1 = point + 1;
                            } else {
                                mSelectPosition2 = point + 1;
                            }
                        }
                    }
                }
                if (mListener != null) {
                    if (mSelectPosition1 < mSelectPosition2) {
                        mListener.onChange(levelsArr[mSelectPosition1], levelsArr[mSelectPosition2]);
                    } else if (mSelectPosition1 == mSelectPosition2 && mSelectPosition1 == levelsArr.length - 1) {
                        //两个点都在最右边
                        mListener.onChange(levelsArr[levelsArr.length - 2], levelsArr[mSelectPosition1]);
                    } else {
                        mListener.onChange(levelsArr[mSelectPosition2], levelsArr[mSelectPosition1]);
                    }
                }
                isMoved1 = false;
                isMoved2 = false;
                invalidate();
                break;
        }
        return true;
    }

    public void reset() {
        mSelectPosition1 = 0;
        mSelectPosition2 = levelsArr.length - 1;
        mSelectPointOffset1 = 0;
        mSelectPointOffset2 = 0;
        invalidate();
    }


    public interface OnPointChangeListener {
        void onChange(String startNum, String endNum);
    }

    public void setOnPointChangeListener(OnPointChangeListener listener) {
        mListener = listener;
    }
}
