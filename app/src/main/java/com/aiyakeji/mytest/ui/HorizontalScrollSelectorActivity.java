package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.HorizontalSelectedView;

import java.util.ArrayList;

/**
 * 页面五
 */

public class HorizontalScrollSelectorActivity extends AppCompatActivity implements OnClickListener {
    private HorizontalSelectedView horizontalSelectedView;
    private TextView tv_msg;
    private Button btn_left;
    private Button btn_getMsg;
    private Button btn_right;

    private String[] strings = {"990", "991", "992", "993", "994", "99", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
        initView();
        initData();
    }

    private void initView() {
        horizontalSelectedView = (HorizontalSelectedView) findViewById(R.id.five_hsv);
        tv_msg = (TextView) findViewById(R.id.five_tv_msg);
        btn_left = (Button) findViewById(R.id.five_btn_left);
        btn_getMsg = (Button) findViewById(R.id.five_btn_gettext);
        btn_right = (Button) findViewById(R.id.five_btn_right);

        btn_left.setOnClickListener(this);
        btn_getMsg.setOnClickListener(this);
        btn_right.setOnClickListener(this);
    }

    private void initData() {
        ArrayList<String> strs = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            strs.add(strings[i]);
        }
        horizontalSelectedView.setData(strs);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.five_btn_left:
                horizontalSelectedView.setStepLeft();
                break;
            case R.id.five_btn_gettext:
                tv_msg.setText(horizontalSelectedView.getSelectValue());
                break;
            case R.id.five_btn_right:
                horizontalSelectedView.setStepRight();
                break;
        }
    }
}
