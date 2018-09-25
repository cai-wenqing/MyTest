package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Caiwenqing
 * Date：2018/9/21
 * Desc:
 */
public class ScrollIndexView extends View {
    private List<ScrollIndexBean> mValueList;
    private List<Float> mValueStartList;
    private Paint mNormalPaint;
    private Paint mSelectPaint;
    private int mBGColor = Color.WHITE;
    private int mNormalColor = Color.BLACK;
    private int mSelectColor = Color.RED;
    private float mNormalTextSize = 40;
    private Rect rect = new Rect();
    private float mWidth;
    private float mHeight;
    private float mTextOffset = 40;
    private int selectedIndex = 0;
    private float allTextWidth;
    private float moveX;
    private float downX;
    private float disX;

    private boolean overRange = false;

    public ScrollIndexView(Context context) {
        this(context, null);
    }

    public ScrollIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        init();
    }

    private void init() {
        mValueList = new ArrayList<>();
        mValueStartList = new ArrayList<>();
        mNormalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectPaint.setStrokeWidth(5);
        mNormalPaint.setTextSize(mNormalTextSize);
        mSelectPaint.setTextSize(mNormalTextSize);
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
        if (mValueList == null || mValueList.size() == 0) {
            return;
        }

        canvas.drawColor(mBGColor);

        allTextWidth = mTextOffset;
        for (int i = 0; i < mValueList.size(); i++) {
            ScrollIndexBean bean = mValueList.get(i);
            if (selectedIndex == i) {
                canvas.drawText(bean.getValue(), allTextWidth + disX, mHeight / 2 + bean.getHeight() / 2, mSelectPaint);
                canvas.drawLine(allTextWidth + disX, mHeight / 2 + bean.getHeight() / 2 + 20,
                        allTextWidth + disX + bean.getWidth(), mHeight / 2 + bean.getHeight() / 2 + 20, mSelectPaint);
            } else {
                canvas.drawText(bean.getValue(), allTextWidth + disX, mHeight / 2 + bean.getHeight() / 2, mNormalPaint);
            }
            if (i < mValueList.size() - 1) {
                allTextWidth += bean.getWidth() + mTextOffset;
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveX = event.getX();
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float between = event.getX() - moveX;
                moveX = event.getX();
                float tempDisX = disX + between;
                if (tempDisX <= 0 && tempDisX + allTextWidth >= mWidth) {
                    disX = tempDisX;
                    invalidate();
                } else if (tempDisX > 0) {
                    overRange = true;
                    disX = (float) (tempDisX * 0.8);
                    invalidate();
                } else if (tempDisX + allTextWidth < mWidth) {
                    overRange = true;
                    disX = (float) (tempDisX * 0.8);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (overRange) {
                    disX = 0;
                    invalidate();
                    overRange = false;
                }

                if (Math.abs(downX - event.getX()) < 20) {
                    float upDis = Math.abs(disX) + event.getX();
                    for (int i = 0; i < mValueStartList.size(); i++) {
                        if (upDis >= mValueStartList.get(i) && upDis <= (mValueStartList.get(i) + mValueList.get(i).getWidth())) {
                            selectedIndex = i;
                            if (mListener != null) {
                                mListener.onSelectChange(i, mValueList.get(i).getValue());
                            }
                            invalidate();
                            break;
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setData(List<String> list) {
        if (list != null) {
            float temp = mTextOffset;
            for (int i = 0; i < list.size(); i++) {
                ScrollIndexBean bean;
                mValueStartList.add(temp);
                if (i == 0) {
                    mSelectPaint.getTextBounds(list.get(i), 0, list.get(i).length(), rect);
                    bean = new ScrollIndexBean(list.get(i), rect.width(), rect.height());
                    temp += rect.width() + mTextOffset;
                } else {
                    mNormalPaint.getTextBounds(list.get(i), 0, list.get(i).length(), rect);
                    bean = new ScrollIndexBean(list.get(i), rect.width(), rect.height());
                    temp += rect.width() + mTextOffset;
                }
                mValueList.add(bean);
            }
            invalidate();
        }
    }


    public interface OnSelectChangeListener {
        void onSelectChange(int position, String value);
    }

    private OnSelectChangeListener mListener;

    public void setOnSelectChangeListener(OnSelectChangeListener listener) {
        mListener = listener;
    }
}
