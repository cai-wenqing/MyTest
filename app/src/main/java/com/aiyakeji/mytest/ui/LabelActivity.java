package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.LabelLayout;

import java.util.Arrays;

/**
 * Author：CWQ
 * Date：2019/2/26
 * Desc:标签控件测试类
 */
public class LabelActivity extends AppCompatActivity {

    String[] labelArr = {"积极", "内向", "活泼", "开朗", "卖萌"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);

        LabelLayout labelLayout = findViewById(R.id.labelLayout);
        labelLayout.setLabels(Arrays.asList(labelArr));
    }
}
