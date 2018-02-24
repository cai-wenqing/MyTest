package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.adapters.TestLayoutAdapter;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/2/24 0024.
 */

public class TestLayoutActivity extends AppCompatActivity {
    RecyclerView mRecycleView;
    String[] strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testlayout);

        mRecycleView = findViewById(R.id.test_recycleView);

        mRecycleView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecycleView.setAdapter(new TestLayoutAdapter(this, Arrays.asList(strings)));
    }

}
