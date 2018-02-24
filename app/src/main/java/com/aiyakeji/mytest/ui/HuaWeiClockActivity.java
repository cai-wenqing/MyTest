package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.ClockView;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class HuaWeiClockActivity extends AppCompatActivity {
    private ClockView mClockView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        mClockView = (ClockView) findViewById(R.id.four_cv);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mClockView.start();
    }

    @Override
    protected void onDestroy() {
        mClockView.stop();
        super.onDestroy();
    }
}
