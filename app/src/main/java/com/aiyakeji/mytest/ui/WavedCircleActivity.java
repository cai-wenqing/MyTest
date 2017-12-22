package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.WavedCircleView;

/**
 * Created by Administrator on 2017/11/28 0028.
 * 波动圆
 */

public class WavedCircleActivity extends AppCompatActivity {
    private WavedCircleView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wavedcircle);
        view = (WavedCircleView) findViewById(R.id.wavedcircle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        view.destroy();
    }
}
