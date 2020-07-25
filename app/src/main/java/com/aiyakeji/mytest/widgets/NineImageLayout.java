package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.bean.PicBean;

import java.util.ArrayList;
import java.util.List;


/**
 * @author houxiaokang
 * @date 2020/7/15
 * description
 */
public class NineImageLayout extends ViewGroup {

    private static final float DEFUALT_SPACING = 6f;
    private static final int MAX_COUNT = 9;

    private float mSpacing = DEFUALT_SPACING;
    private int mTotalWidth;
    private int mTotalHeight;
    private int mSingleWidth;

    private ArrayList<PicBean> mUrlList = new ArrayList<>();

    public NineImageLayout(Context context) {
        super(context);
    }

    public NineImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NineImageLayout);

        mSpacing = typedArray.getDimension(R.styleable.NineImageLayout_sapcing, DEFUALT_SPACING);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mUrlList.size() == 0){
            return;
        }
        if (mUrlList.size() == 1){
            mTotalWidth = mTotalHeight = mSingleWidth = mUrlList.get(0).getWidth();
        }else {
            mSingleWidth = (int) ((getMeasuredWidth() - 2 * mSpacing) / 3);

            if (mUrlList.size() <= 3) {
                mTotalWidth = (int) (mUrlList.size() * mSingleWidth + (mUrlList.size() - 1) * mSpacing);
                mTotalHeight = mSingleWidth;
            } else if (mUrlList.size() <= 6) {
                mTotalWidth = getMeasuredWidth();
                mTotalHeight = (int) (2 * mSingleWidth + mSpacing);
            } else {
                mTotalHeight = mTotalWidth = getMeasuredWidth();
            }
        }
        setMeasuredDimension(mTotalWidth, mTotalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (mUrlList.size() == 0){
            return;
        }
        int mLeft, mTop;
        for (int i = 0; i < mUrlList.size(); i++) {
            if (i == 0 && mUrlList.size() == 1){
                mLeft = getPaddingLeft();
                mTop = getPaddingTop();
            }else if (i <= 2) {
                mLeft = (int) (getPaddingLeft() + i * (mSingleWidth + mSpacing));
                mTop = getPaddingTop();
            } else if (i <= 5) {
                mLeft = (int) (getPaddingLeft() + (i - 3) * (mSingleWidth + mSpacing));
                mTop = (int) (getPaddingLeft() + mSingleWidth + mSpacing);
            } else {
                mLeft = (int) (getPaddingLeft() + (i - 6) * (mSingleWidth + mSpacing));
                mTop = (int) (getPaddingLeft() + 2 * (mSingleWidth + mSpacing));
            }
            TextView tv = createTextView(mUrlList.get(i).getName());
            tv.layout(mLeft, mTop, mLeft + mSingleWidth, mTop + mSingleWidth);
            addView(tv);
        }
    }


    public void updateData(List<PicBean> list) {
        if (list != null && !list.isEmpty()) {
            mUrlList.clear();
            mUrlList.addAll(list);
            invalidate();
        }
    }


    private TextView createTextView(String text) {
        TextView tv = new TextView(getContext());
        tv.setBackgroundColor(Color.BLUE);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.WHITE);
        tv.setText(text);
        return tv;
    }
}
