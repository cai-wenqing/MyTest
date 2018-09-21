package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.HorizontalSelectedView;
import com.aiyakeji.mytest.widgets.PageIndexView;
import com.aiyakeji.mytest.widgets.ScrollIndexView;
import com.aiyakeji.mytest.widgets.SlideProgressView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 页面五
 */

public class HorizontalScrollSelectorActivity extends AppCompatActivity implements OnClickListener {
    private HorizontalSelectedView horizontalSelectedView;
    private TextView tv_msg;
    private Button btn_left;
    private Button btn_getMsg;
    private Button btn_right;

    private TextView tv_startNum;
    private TextView tv_endNum;
    private SlideProgressView slideProgressView;
    private ScrollIndexView scrollIndexView;
    private PageIndexView pageIndexView;
    private EditText et_pointCount;
    private Button btn_submit;

    private String[] strings = {"990", "991", "992", "993", "994", "99", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003"};
    private String[] values = {"盖伦", "光辉", "火男", "男枪", "提莫", "加里奥", "奥巴马", "炼金", "猪女", "蜘蛛", "皇子", "赵信", "盲僧", "蛮王"};

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

        tv_startNum = findViewById(R.id.five_tv_startNum);
        tv_endNum = findViewById(R.id.five_tv_endNum);
        slideProgressView = findViewById(R.id.five_spv);
        slideProgressView.setMaxNumber(1000);
        slideProgressView.setOnChangeListener(new SlideProgressView.OnChangeListener() {
            @Override
            public void onStartChange(String startNum) {
                tv_startNum.setText("¥" + startNum);
            }

            @Override
            public void onEndChange(String endNum) {
                tv_endNum.setText("¥" + endNum);
            }
        });

        btn_left.setOnClickListener(this);
        btn_getMsg.setOnClickListener(this);
        btn_right.setOnClickListener(this);

        scrollIndexView = findViewById(R.id.five_siv);
        scrollIndexView.setData(Arrays.asList(values));
        scrollIndexView.setOnSelectChangeListener(new ScrollIndexView.OnSelectChangeListener() {
            @Override
            public void onSelectChange(int position, String value) {
                Log.e("Horizontal测试", "position:" + position + ",value:" + value);
            }
        });


        pageIndexView = findViewById(R.id.piv);
        pageIndexView.setPointCount(5);

        et_pointCount = findViewById(R.id.et_pointCount);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
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
            case R.id.btn_submit:
                String s = et_pointCount.getText().toString();
                pageIndexView.setIndex(Integer.valueOf(s));
                break;
        }
    }
}
