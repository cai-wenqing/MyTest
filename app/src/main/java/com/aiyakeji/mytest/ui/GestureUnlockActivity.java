package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.GestureLockView;

/**
 * 自定义手势解锁view展示
 */

public class GestureUnlockActivity extends AppCompatActivity implements View.OnClickListener {
    private GestureLockView gestureLockView;
    private TextView tv_pwd;
    private Button btn_reset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six);
        initView();
    }

    private void initView() {
        gestureLockView = (GestureLockView) findViewById(R.id.six_glv);
        tv_pwd = (TextView) findViewById(R.id.six_tv_pwd);
        btn_reset = (Button) findViewById(R.id.six_btn_reset);

        gestureLockView.setOnFinishListener(new GestureLockView.onFinishListener() {
            @Override
            public void onFinish(String password) {
                tv_pwd.setText(password);
            }
        });
        btn_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.six_btn_reset:
                gestureLockView.reset();
                break;
        }
    }
}
