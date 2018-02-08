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

    private TextView mTextView;
    private RecyclerView mRecycleView;

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

        mTextView = (TextView) findViewById(R.id.slidingcard_tv_title);
        mTextView.setBackgroundColor(headBackgroundColor);
        mTextView.setTextColor(headTextColor);
        mTextView.setText(text);


        mRecycleView = (RecyclerView) findViewById(R.id.slidingcard_recycleview);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.setAdapter(new SlidingCardAdapter(getContext(), Arrays.asList(strings)));
        attribute.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        headHeight = findViewById(R.id.slidingcard_tv_title).getMeasuredHeight();
    }


    /**
     * 获取头部高度
     *
     * @return
     */
    public int getHeadHeight() {
        return headHeight;
    }


    /**
     * 能否继续向下滑动，true能滚动，false已经滚动到顶部
     *
     * @return
     */
    public boolean listCanScrollTop() {
        return mRecycleView.canScrollVertically(-1);
    }


    /**
     * 能否继续向上滑动，true能滚动，false已经滚到底部
     *
     * @return
     */
    public boolean listCanScrollBottom() {
        return mRecycleView.canScrollVertically(1);
    }


    /**
     * 获取头部view
     *
     * @return
     */
    public TextView getTitleView() {
        return mTextView;
    }


    /**
     * 获取列表控件RecycleView
     *
     * @return
     */
    public RecyclerView getRecycleView() {
        return mRecycleView;
    }
}
