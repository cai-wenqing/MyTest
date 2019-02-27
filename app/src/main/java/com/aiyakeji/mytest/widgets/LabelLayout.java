package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiyakeji.mytest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：CWQ
 * Date：2019/2/27
 * Desc:标签展示控件
 */
public class LabelLayout extends LinearLayout {

    private LinearLayout mRootView;
    private int maxNum = 5;//默认一行内标签最大数
    private List<String> mLabels = new ArrayList<>();
    private LinearLayout.LayoutParams params;
    private int[] labelBgIds = {R.drawable.label_green, R.drawable.label_orange, R.drawable.label_blue, R.drawable.label_pink, R.drawable.label_blue2};

    public LabelLayout(Context context) {
        this(context, null);
    }

    public LabelLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRootView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_label, this);
        params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);
        params.leftMargin = 8;
        params.rightMargin = 8;
    }


    public void setLabels(List<String> list) {
        if (list == null) {
            return;
        }
        mLabels.clear();
        mLabels.addAll(list);


        mRootView.removeAllViews();
        for (int i = 0; i < maxNum; i++) {
            if (i < mLabels.size()) {
                TextView tv = new TextView(getContext());
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(12f);
                tv.setBackgroundResource(labelBgIds[i]);
                tv.setText(mLabels.get(i));
                tv.setPadding(0, 10, 0, 10);
                tv.setLayoutParams(params);
                mRootView.addView(tv);
            } else {
                View emptyView = new View(getContext());
                emptyView.setLayoutParams(params);
                mRootView.addView(emptyView);
            }
        }
    }


    public void setMaxNum(int num) {
        if (num > 0 && num < 7) {
            maxNum = num;
        }
    }
}
