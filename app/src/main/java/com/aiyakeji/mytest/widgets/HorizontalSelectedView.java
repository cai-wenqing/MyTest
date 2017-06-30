package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义左右滚动选择view
 * 参考博客 http://blog.csdn.net/iamdingruihaha/article/details/71422269
 */

public class HorizontalSelectedView extends View {
    private List<String> strings = new ArrayList<>();

    private float mWidth;
    private float mHeight;

    private Rect rect = new Rect();
    private TextPaint mTextPaint;
    private TextPaint mSelectedPaint;

    private float textWidth;//普通文字宽度
    private float textHeight;//普通文字高度
    private float disBetweenText = 30;//文字之间的间隔距离
    private float offset;

    private int selectedIndex;

    private float downX;


    public HorizontalSelectedView(Context context) {
        this(context, null);
    }

    public HorizontalSelectedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalSelectedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
        initPaint();
    }

    private void initPaint() {
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(40);
        mTextPaint.setColor(Color.BLUE);
        mSelectedPaint = new TextPaint();
        mSelectedPaint.setColor(Color.RED);
        mSelectedPaint.setTextSize(50);
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
        mSelectedPaint.getTextBounds(strings.get(selectedIndex), 0, strings.get(selectedIndex).length(), rect);
        float selectedTextWidth = rect.width();
        float selectedTextHeight = rect.height();
        //被选中的文字
        canvas.drawText(strings.get(selectedIndex), mWidth / 2 - selectedTextWidth / 2 + offset, mHeight / 2 + selectedTextHeight / 2, mSelectedPaint);

        textWidth = selectedTextWidth;
        //其他文字
        for (int i = 0; i < strings.size(); i++) {
            //计算选中值两侧文字宽度的平均值
            if (selectedIndex > 0 && selectedIndex < strings.size() - 1) {
                mTextPaint.getTextBounds(strings.get(selectedIndex - 1), 0, strings.get(selectedIndex - 1).length(), rect);
                float preWidth = rect.width();
                mTextPaint.getTextBounds(strings.get(selectedIndex + 1), 0, strings.get(selectedIndex + 1).length(), rect);
                float aftWidth = rect.width();
                textWidth = (preWidth + aftWidth) / 2;
            }

            if (0 == i) {
                mTextPaint.getTextBounds(strings.get(0), 0, strings.get(0).length(), rect);
                textHeight = rect.height();
            }

            if (i != selectedIndex)
                canvas.drawText(strings.get(i), mWidth / 2 + (textWidth + disBetweenText) * (i - selectedIndex) - textWidth / 2 + offset, mHeight / 2 + textHeight / 2, mTextPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float scrollX = ev.getX();
                offset = scrollX - downX;
                if (scrollX > downX) {//向右滑
                    if (scrollX - downX >= (textWidth + disBetweenText)) {
                        if (selectedIndex > 0) {
                            offset = 0;
                            selectedIndex -= 1;
                            downX = scrollX;
                        }
                    }
                } else {//向左滑
                    if (downX - scrollX >= (textWidth + disBetweenText)) {
                        if (selectedIndex < strings.size() - 1) {
                            offset = 0;
                            selectedIndex += 1;
                            downX = scrollX;
                        }
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                offset = 0;
                invalidate();
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 设置数据源
     *
     * @param list
     */
    public void setData(List<String> list) {
        if (null != list) {
            strings = list;
            selectedIndex = list.size() / 2;
            invalidate();
        } else {
            throw new IllegalArgumentException("list 不能为null！");
        }
    }

    /**
     * 向右移动一个单位
     */
    public void setStepRight() {
        if (selectedIndex > 0) {
            selectedIndex -= 1;
            invalidate();
        }
    }

    /**
     * 向左移动一个单位
     */
    public void setStepLeft() {
        if (selectedIndex < strings.size() - 1) {
            selectedIndex += 1;
            invalidate();
        }
    }

    /**
     * 获取选中的文字
     *
     * @return
     */
    public String getSelectValue() {
        if (strings.size() != 0)
            return strings.get(selectedIndex);
        return null;
    }
}
