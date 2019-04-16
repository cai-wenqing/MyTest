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
 * Date：2019/4/16
 * Desc:侧滑
 */
public class SideBar extends View {

    private static final String TAG = "SideBar测试";

    private String[] indexStrs = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z"};
    private Paint mPaint;
    private int mNormalTextColor = Color.parseColor("#A50B73");
    private float mTextSize = 40;

    private int mSelectPosition = -1;
    private int mItemWidth;
    private int mItemHeight;
    private Rect mRect;
    private OnIndexChangeListener mListener;


    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SideBar);
        mNormalTextColor = typedArray.getColor(R.styleable.SideBar_sideBarTextColor, Color.parseColor("#A50B73"));
        mTextSize = typedArray.getDimension(R.styleable.SideBar_sideBarTextSize, 40);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mNormalTextColor);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mRect = new Rect();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mItemWidth = getMeasuredWidth();
        mItemHeight = getMeasuredHeight() / indexStrs.length;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < indexStrs.length; i++) {
            mPaint.getTextBounds(indexStrs[i], 0, 1, mRect);
            int textWidth = mRect.width();
            int textHeight = mRect.height();

            float wordX = mItemWidth / 2 - textWidth / 2;
            float wordY = i * mItemHeight + mItemHeight / 2 + textHeight / 2;
            canvas.drawText(indexStrs[i], wordX, wordY, mPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float touchY = event.getY();
                int index = (int) (touchY / mItemHeight);
                if (index != mSelectPosition) {
                    mSelectPosition = index;
                    if (mListener != null && mSelectPosition >= 0 && mSelectPosition <= indexStrs.length - 1) {
                        mListener.indexChange(indexStrs[mSelectPosition], mSelectPosition);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }


    public interface OnIndexChangeListener {
        void indexChange(String word, int position);
    }

    public void setOnIndexChangeListener(OnIndexChangeListener listener) {
        mListener = listener;
    }
}
