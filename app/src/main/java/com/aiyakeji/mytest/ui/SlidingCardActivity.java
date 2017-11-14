package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.SlidingCardLayout;


/**
 * Created by Administrator on 2017/11/14 0014.
 * 滑动卡片
 */

public class SlidingCardActivity extends AppCompatActivity {
    private SlidingCardLayout card_one;
    private SlidingCardLayout card_two;
    private SlidingCardLayout card_three;
    private SlidingCardLayout card_four;
    private SlidingCardLayout card_five;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidingcard);

        card_one = (SlidingCardLayout) findViewById(R.id.slidingcard_one);
        card_two = (SlidingCardLayout) findViewById(R.id.slidingcard_two);
        card_three = (SlidingCardLayout) findViewById(R.id.slidingcard_three);
        card_four = (SlidingCardLayout) findViewById(R.id.slidingcard_four);
        card_five = (SlidingCardLayout) findViewById(R.id.slidingcard_five);
    }


}
