package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.ScalePanelView;

/**
 * 自定义圆盘
 */

public class RotatePlateActivity extends AppCompatActivity implements View.OnClickListener {
    private ScalePanelView ccv;
    private TextView tv;
    private Button btn_forbit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven);
        initView();
    }

    private void initView() {
        ccv = (ScalePanelView) findViewById(R.id.seven_ccv);
        tv = (TextView) findViewById(R.id.seven_tv);
        btn_forbit = (Button) findViewById(R.id.seven_btn_forbit);


        ccv.setOnRotate(new ScalePanelView.onRotateListener() {
            @Override
            public void onRotate(float angle) {
                tv.setText("角度：" + angle);
            }
        });
        btn_forbit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.seven_btn_forbit) {
            if (ccv.getForbidRotate()) {
                ccv.setForbidRotate(false);
                btn_forbit.setText("禁止");
            } else {
                ccv.setForbidRotate(true);
                btn_forbit.setText("开启");
            }
        }
    }
}
