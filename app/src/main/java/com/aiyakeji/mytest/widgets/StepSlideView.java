package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.aiyakeji.mytest.R;

/**
 * Author：CWQ
 * Date：2019/4/17
 * Desc:档位选择器
 */
public class StepSlideView extends View {
    private static final String TAG = "StepSlideView";

    private String[] levelsArr = {"小", "中", "大", "特大"};

    private int mWidth;
    private int mHeight;
    private float mSidePadding = 60;
    private float mNormalCircleRadius = 15;
    private float mSelectCircleRadius = 30;
    private int mStokeWidth = 4;
    private float mTextSize = 40;
    private float mSpaceInPoints;//圆心之间间距
    private float mSpaceInTextPoint = 15;//文字与最大圆点间距

    private Paint mPaint;
    private Rect mRect;
    private int mNormalColor = Color.GRAY;
    private int mSelectTextColor = Color.BLACK;

    private int mSelectPosition = 0;
    private float mSelectPointOffset = 0;
    private boolean isMoved = false;
    float downX = 0;
    private OnPointChangeListener mListener;

    public StepSlideView(Context context) {
        this(context, null);
    }

    public StepSlideView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepSlideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StepSlideView);
            mNormalColor = typedArray.getColor(R.styleable.StepSlideView_ssvNormalColor, Color.GRAY);
            mSelectTextColor = typedArray.getColor(R.styleable.StepSlideView_ssvSelectTextColor, Color.BLACK);
            mNormalCircleRadius = typedArray.getDimension(R.styleable.StepSlideView_ssvNormalPointRadius, 15);
            mSelectCircleRadius = typedArray.getDimension(R.styleable.StepSlideView_ssvSelectPointRadius, 30);
            mSpaceInTextPoint = typedArray.getDimension(R.styleable.StepSlideView_ssvSpaceInTextPoint, 15);
            mTextSize = typedArray.getDimension(R.styleable.StepSlideView_ssvTextSize, 40);
            typedArray.recycle();
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mNormalColor);
        mPaint.setStrokeWidth(mStokeWidth);
        mPaint.setTextSize(mTextSize);
        mRect = new Rect();

        if (mSidePadding < 2 * mSelectCircleRadius) {
            mSidePadding = 2 * mSelectCircleRadius;
        }
        if (mSidePadding < 2 * mNormalCircleRadius) {
            mSidePadding = 2 * mNormalCircleRadius;
        }
        if (mSidePadding < 2 * mTextSize) {
            mSidePadding = 2 * mTextSize;
        }
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
        canvas.drawLine(mSidePadding, mHeight / 2, mWidth - mSidePadding, mHeight / 2, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < levelsArr.length; i++) {
            if (mSelectPosition == i) {
                mPaint.setColor(mNormalColor);
                canvas.drawCircle(i * mSpaceInPoints + mSidePadding, mHeight / 2, mNormalCircleRadius, mPaint);

                mPaint.setColor(mSelectTextColor);
                mPaint.getTextBounds(levelsArr[i], 0, levelsArr[i].length(), mRect);
                canvas.drawText(levelsArr[i], i * mSpaceInPoints - mRect.width() / 2 + mSidePadding, mHeight / 2 - mSelectCircleRadius - mSpaceInTextPoint, mPaint);
            } else {
                mPaint.setColor(mNormalColor);
                canvas.drawCircle(i * mSpaceInPoints + mSidePadding, mHeight / 2, mNormalCircleRadius, mPaint);

                mPaint.getTextBounds(levelsArr[i], 0, levelsArr[i].length(), mRect);
                canvas.drawText(levelsArr[i], i * mSpaceInPoints - mRect.width() / 2 + mSidePadding, mHeight / 2 - mSelectCircleRadius - mSpaceInTextPoint, mPaint);
            }
        }
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mSelectPosition * mSpaceInPoints + mSidePadding + mSelectPointOffset, mHeight / 2, mSelectCircleRadius, mPaint);
        mPaint.setColor(mNormalColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mSelectPosition * mSpaceInPoints + mSidePadding + mSelectPointOffset, mHeight / 2, mSelectCircleRadius, mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float selectPointX = mSelectPosition * mSpaceInPoints + mSidePadding;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                if (Math.abs(selectPointX - downX) < mSelectCircleRadius && moveX >= mSidePadding && moveX <= (mWidth - mSidePadding)) {//校验点击范围
                    mSelectPointOffset = moveX - selectPointX;
                    invalidate();
                    isMoved = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float upX = event.getX();
                float upY = event.getY();
                mSelectPointOffset = 0;
                if (isMoved) {
                    float value = (upX - mSidePadding) % mSpaceInPoints;
                    int point = (int) ((upX - mSidePadding) / mSpaceInPoints);
                    if (value < mSpaceInPoints / 2) {
                        mSelectPosition = point;
                    } else {
                        mSelectPosition = point + 1;
                    }
                } else {
                    if (upY > mHeight / 4 && upY < mHeight - mHeight / 4) {
                        float value = (upX - mSidePadding) % mSpaceInPoints;
                        int point = (int) ((upX - mSidePadding) / mSpaceInPoints);
                        if (value < 2 * mSelectCircleRadius) {
                            mSelectPosition = point;
                        } else if (value > (mSpaceInPoints - 2 * mSelectCircleRadius)) {
                            mSelectPosition = point + 1;
                        }
                    }
                }
                if (mListener != null) {
                    mListener.onChange(mSelectPosition, levelsArr[mSelectPosition]);
                }
                isMoved = false;
                invalidate();
                break;
        }
        return true;
    }


    public interface OnPointChangeListener {
        void onChange(int position, String text);
    }

    public void setOnPointChangeListener(OnPointChangeListener listener) {
        mListener = listener;
    }
}
