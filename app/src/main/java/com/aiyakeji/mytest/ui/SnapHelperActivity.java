package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.adapters.TestLayoutAdapter;
import com.aiyakeji.mytest.widgets.AutoPollRecyclerView;

import java.util.Arrays;

/**
 * @author CWQ
 * @date 2019-11-27
 */
public class SnapHelperActivity extends AppCompatActivity {

    String[] strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snaphelper);

        RecyclerView rv1 = findViewById(R.id.recycleView1);
        AutoPollRecyclerView rv2 = findViewById(R.id.recycleView2);


        rv1.setLayoutManager(new LinearLayoutManager(this));
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(rv1);
        rv1.setAdapter(new TestLayoutAdapter(this, Arrays.asList(strings)));


        rv2.setLayoutManager(new LinearLayoutManager(this));
        rv2.setAdapter(new TestLayoutAdapter(this, Arrays.asList(strings)));
//        rv2.setMaxHeight(200);
        rv2.setCanRun(true);
        rv2.start();
    }
}
