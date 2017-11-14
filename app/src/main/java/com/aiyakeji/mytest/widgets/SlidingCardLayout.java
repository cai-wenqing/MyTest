package com.aiyakeji.mytest.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.adapters.SlidingCardAdapter;
import com.aiyakeji.mytest.behavior.SlidingCardBehavior;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/11/14 0014.
 * 滑动卡片
 */

@CoordinatorLayout.DefaultBehavior(SlidingCardBehavior.class)
public class SlidingCardLayout extends FrameLayout {
    private int headBackgroundColor = Color.BLUE;
    private int headTextColor = Color.GREEN;
    private String text;

    private int headHeight;

    private String[] strings = new String[]{"盖伦", "光辉", "火男", "男枪", "提莫", "加里奥", "奥巴马",
            "炼金", "猪女", "蜘蛛", "皇子", "赵信", "盲僧", "蛮王"};


    public SlidingCardLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlidingCardLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.slidingcardlayout, this);

        TypedArray attribute = getContext().obtainStyledAttributes(attrs, R.styleable.SlidingCardLayout);
        headBackgroundColor = attribute.getColor(R.styleable.SlidingCardLayout_sliding_card_head_background, Color.BLUE);
        headTextColor = attribute.getColor(R.styleable.SlidingCardLayout_sliding_card_head_text_color, Color.GREEN);
        text = attribute.getString(R.styleable.SlidingCardLayout_sliding_card_head_text);

        TextView headText = (TextView) findViewById(R.id.slidingcard_tv_title);
        headText.setBackgroundColor(headBackgroundColor);
        headText.setTextColor(headTextColor);
        headText.setText(text);

        RecyclerView mRecycleView = (RecyclerView) findViewById(R.id.slidingcard_recycleview);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.setAdapter(new SlidingCardAdapter(getContext(), Arrays.asList(strings)));
        attribute.recycle();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            headHeight = findViewById(R.id.slidingcard_tv_title).getMeasuredHeight();
        }
    }


    public int getHeadHeight() {
//        Log.i("SlidingCardLayout测试", "getHeadHeight:" + headHeight + ",tag:" + toString());
        return headHeight;
    }
}
