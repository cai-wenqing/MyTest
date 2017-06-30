package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.ControlableCircleView;

import static com.aiyakeji.mytest.R.id.seven_btn_right;

/**
 * 自定义圆盘
 */

public class SevenActivity extends AppCompatActivity implements View.OnClickListener {
    private ControlableCircleView ccv;
    private Button btn_left;
    private TextView tv;
    private Button btn_right;
    private Button btn_forbit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven);
        initView();
    }

    private void initView() {
        ccv = (ControlableCircleView) findViewById(R.id.seven_ccv);
        btn_left = (Button) findViewById(R.id.seven_btn_left);
        tv = (TextView) findViewById(R.id.seven_tv);
        btn_right = (Button) findViewById(seven_btn_right);
        btn_forbit = (Button) findViewById(R.id.seven_btn_forbit);


        ccv.setOnRotate(new ControlableCircleView.onRotateListener() {
            @Override
            public void onRotateLeft(int num) {
                tv.setText("向左旋转" + num + "格");
            }

            @Override
            public void onRotateRight(int num) {
                tv.setText("向右旋转" + num + "格");
            }
        });
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        btn_forbit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seven_btn_left:
                ccv.rotateLeft();
                break;
            case R.id.seven_btn_right:
                ccv.rotateRight();
                break;
            case R.id.seven_btn_forbit:
                if (ccv.getForbitRotate()) {
                    ccv.setForbitRotate(false);
                    btn_forbit.setText("禁止");
                } else {
                    ccv.setForbitRotate(true);
                    btn_forbit.setText("开启");
                }
                break;
        }
    }
}
