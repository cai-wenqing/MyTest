package com.aiyakeji.mytest.ui;

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

public class ThreeActivity extends AppCompatActivity implements View.OnClickListener {
    private CircleProgressView cpv;
    private Button btn_start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        initView();
    }

    private void initView() {
        cpv = (CircleProgressView) findViewById(R.id.three_cpv);
        btn_start = (Button) findViewById(R.id.three_btn_start);

        btn_start.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.three_btn_start:
                cpv.start(200f, 165f);
                break;
        }
    }
}
