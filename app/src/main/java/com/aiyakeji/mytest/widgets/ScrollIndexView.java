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
    private Paint mIndicatorPaint;
//    private int mBGColor = Color.WHITE;
    private int mNormalColor = Color.BLACK;
    private int mSelectTextColor = Color.BLUE;
    private int mIndicatorColor = Color.BLUE;
    private float mTextSize = 40;
    private float mIndicatorHeight = 5f;
    private float mIndicatorDistance = 20;//下划线与文字间距
    private boolean rebound = false;//是否回弹效果

    private Rect rect = new Rect();
    private float mWidth;
    private float mHeight;
    private float mTextOffset = 40;
    private int selectedIndex = 0;
    private float allTextWidth;
    private float moveX;
    private float downX;
    private float disX;

    //超出范围的方向，左侧超出为-1，右侧超出为1
    private int overRange = 0;

    public ScrollIndexView(Context context) {
        this(context, null);
    }

    public ScrollIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ScrollIndexView);
        mTextSize = typedArray.getDimension(R.styleable.ScrollIndexView_siv_text_size, 40f);
        mNormalColor = typedArray.getColor(R.styleable.ScrollIndexView_siv_normal_text_color, Color.BLACK);
        mSelectTextColor = typedArray.getColor(R.styleable.ScrollIndexView_siv_select_text_color, Color.BLUE);
        mIndicatorColor = typedArray.getColor(R.styleable.ScrollIndexView_siv_indicator_color, Color.BLUE);
        mIndicatorHeight = typedArray.getDimension(R.styleable.ScrollIndexView_siv_indicator_height, 5f);
        mIndicatorDistance = typedArray.getDimension(R.styleable.ScrollIndexView_siv_indicator_distance, 20);
        rebound = typedArray.getBoolean(R.styleable.ScrollIndexView_siv_rebound, false);
        typedArray.recycle();
    }

    private void init() {
        mValueList = new ArrayList<>();
        mValueStartList = new ArrayList<>();
        mNormalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNormalPaint.setTextSize(mTextSize);
        mNormalPaint.setColor(mNormalColor);

        mSelectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectPaint.setTextSize(mTextSize);
        mSelectPaint.setColor(mSelectTextColor);

        mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mIndicatorPaint.setColor(mIndicatorColor);
        mIndicatorPaint.setStrokeWidth(mIndicatorHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int w = widthSpecSize;
        int h = heightSpecSize;

        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            h = (int) (2 * (mTextSize / 2 + mIndicatorDistance + mIndicatorHeight));
        }
        setMeasuredDimension(w, h);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mValueList == null || mValueList.size() == 0) {
            return;
        }

//        canvas.drawColor(mBGColor);

        allTextWidth = mTextOffset;
        for (int i = 0; i < mValueList.size(); i++) {
            ScrollIndexBean bean = mValueList.get(i);
            if (selectedIndex == i) {
                canvas.drawText(bean.getValue(), allTextWidth + disX, mHeight / 2 + bean.getHeight() / 2, mSelectPaint);
                canvas.drawLine(allTextWidth + disX, mHeight / 2 + bean.getHeight() / 2 + mIndicatorDistance,
                        allTextWidth + disX + bean.getWidth(), mHeight / 2 + bean.getHeight() / 2 + mIndicatorDistance, mIndicatorPaint);
            } else {
                canvas.drawText(bean.getValue(), allTextWidth + disX, mHeight / 2 + bean.getHeight() / 2, mNormalPaint);
            }
            allTextWidth += bean.getWidth() + mTextOffset;
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
                    overRange = -1;
                    if (rebound) {
                        disX = (float) (tempDisX * 0.8);
                    } else {
                        disX = 0;
                    }
                    invalidate();
                } else if (tempDisX + allTextWidth < mWidth) {
                    overRange = 1;
                    if (rebound) {
                        disX = (float) (mWidth - allTextWidth + (tempDisX + allTextWidth - mWidth) * 0.8);
                    } else {
                        disX = mWidth - allTextWidth;
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (overRange == -1) {
                    //左侧超出
                    disX = 0;
                    invalidate();
                    overRange = 0;
                } else if (overRange == 1) {
                    //右侧超出
                    disX = mWidth - allTextWidth;
                    invalidate();
                    overRange = 0;
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


    /**
     * 设置当前选中的index
     *
     * @param index
     */
    public void setCurrentIndex(int index) {
        if (index >= 0 && index < mValueList.size()) {
            selectedIndex = index;
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
