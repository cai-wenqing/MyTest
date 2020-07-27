package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiyakeji.mytest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author：CWQ
 * Date：2019/2/27
 * Desc:标签展示控件
 */
public class LabelLayout extends LinearLayout {

    private LinearLayout mRootView;
    private int mPaddingTop;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mMarginLeft;
    private int mMarginRight;
    //是否充满一行
    private boolean mFullLine;
    private float mTextSize;
    private Random mRandom;
    private List<String> mLabels = new ArrayList<>();
    private LinearLayout.LayoutParams params;
    private int[] mLabelBgIds = {R.drawable.label_green, R.drawable.label_orange, R.drawable.label_blue, R.drawable.label_pink, R.drawable.label_blue2};

    public LabelLayout(Context context) {
        this(context, null);
    }

    public LabelLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LabelLayout);
        mPaddingTop = (int) typedArray.getDimension(R.styleable.LabelLayout_labelPaddingTop, 8);
        mPaddingBottom = (int) typedArray.getDimension(R.styleable.LabelLayout_labelPaddingBottom, 8);
        mPaddingLeft = (int) typedArray.getDimension(R.styleable.LabelLayout_labelPaddingLeft, 0);
        mPaddingRight = (int) typedArray.getDimension(R.styleable.LabelLayout_labelPaddingRight, 0);
        mMarginLeft = (int) typedArray.getDimension(R.styleable.LabelLayout_labelMarginLeft, 8);
        mMarginRight = (int) typedArray.getDimension(R.styleable.LabelLayout_labelMarginRight, 8);
        mFullLine = typedArray.getBoolean(R.styleable.LabelLayout_labelFullLine, true);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.LabelLayout_labelTextSize, 12);
        typedArray.recycle();

        init();
    }


    private void init() {
        mRootView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_label, this);
        if (mFullLine) {
            params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
        } else {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        params.leftMargin = mMarginLeft;
        params.rightMargin = mMarginRight;
        mRandom = new Random();
    }


    /**
     * 设置数据源
     *
     * @param list 数据源集合
     */
    public void setLabels(List<String> list) {
        if (list == null) {
            return;
        }
        mLabels.clear();
        mLabels.addAll(list);

        mRootView.removeAllViews();

        for (int i = 0; i < mLabels.size(); i++) {
            if (i < mLabels.size()) {
                TextView tv = new TextView(getContext());
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                tv.getPaint().setTextSize(mTextSize);
                tv.setBackgroundResource(mLabelBgIds[mRandom.nextInt(mLabelBgIds.length)]);
                tv.setText(mLabels.get(i));
                tv.setLines(1);
                tv.setEllipsize(TextUtils.TruncateAt.END);
                tv.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
                tv.setLayoutParams(params);
                mRootView.addView(tv);
            } else {
                View emptyView = new View(getContext());
                emptyView.setLayoutParams(params);
                mRootView.addView(emptyView);
            }
        }
    }


    /**
     * 设置数据源和对应标签背景
     * 数据源和标签必须一一对应
     *
     * @param labelList 数据源
     * @param bgList    标签背景
     */
    public void setLabels(List<String> labelList, List<Integer> bgList) {
        if (labelList == null || bgList == null || labelList.size() != bgList.size()) {
            return;
        }

        mLabels.clear();
        mLabels.addAll(labelList);

        mRootView.removeAllViews();

        for (int i = 0; i < mLabels.size(); i++) {
            if (i < mLabels.size()) {
                TextView tv = new TextView(getContext());
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(mTextSize);
                tv.setLines(1);
                tv.setEllipsize(TextUtils.TruncateAt.END);
                tv.setBackgroundResource(bgList.get(i));
                tv.setText(mLabels.get(i));
                tv.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
                tv.setLayoutParams(params);
                mRootView.addView(tv);
            } else {
                View emptyView = new View(getContext());
                emptyView.setLayoutParams(params);
                mRootView.addView(emptyView);
            }
        }
    }
}
