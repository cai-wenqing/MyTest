package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.ClockView;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class HuaWeiClockActivity extends AppCompatActivity implements View.OnClickListener {
    private ClockView mClockView;
    private Button btn_start;
    private Button btn_stop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        initView();
    }

    private void initView() {
        mClockView = (ClockView) findViewById(R.id.four_cv);
        btn_start = (Button) findViewById(R.id.four_btn_start);
        btn_stop = (Button) findViewById(R.id.four_btn_stop);

        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.four_btn_start:
                mClockView.startAnimation();
                break;
            case R.id.four_btn_stop:
                mClockView.stopAnimation();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mClockView.startAnimation();
    }

    @Override
    protected void onDestroy() {
        mClockView.stopAnimation();
        super.onDestroy();
    }
}
