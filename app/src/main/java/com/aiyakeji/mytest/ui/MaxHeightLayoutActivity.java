package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.adapters.TestLayoutAdapter;

import java.util.Arrays;

/**
 * @author caiwenqing
 * @data 2018/6/8
 * description:
 */
public class MaxHeightLayoutActivity extends AppCompatActivity {
    private RecyclerView rv;

    String[] strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"};
//    String[] strings = {"1", "2", "3", "4"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maxheightlayout);

        rv = findViewById(R.id.maxheight_rv);

        rv.setLayoutManager(new GridLayoutManager(this, 4));
        rv.setAdapter(new TestLayoutAdapter(this, Arrays.asList(strings)));
    }
}
