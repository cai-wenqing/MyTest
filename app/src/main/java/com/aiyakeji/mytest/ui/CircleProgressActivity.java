package com.aiyakeji.mytest.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.CircleProgressView;

/**
 * 三测试页面
 */

public class CircleProgressActivity extends AppCompatActivity implements View.OnClickListener {
    private CircleProgressView cpv1;
    private CircleProgressView cpv2;
    private CircleProgressView cpv3;
    private Button btn_start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        initView();
    }

    private void initView() {
        cpv1 = (CircleProgressView) findViewById(R.id.three_cpv1);
        cpv2 = (CircleProgressView) findViewById(R.id.three_cpv2);
        cpv3 = (CircleProgressView)findViewById(R.id.three_cpv3);
        btn_start = (Button) findViewById(R.id.three_btn_start);

        cpv1.setGradientColors(Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN);
        cpv3.setSweepAngle(270);
        cpv3.isShowAsPercent(false);
        btn_start.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.three_btn_start:
                cpv1.start(200f, 100f);
                cpv2.start(200f, 165f);
                cpv3.start(200f, 165f);
                break;
        }
    }
}
