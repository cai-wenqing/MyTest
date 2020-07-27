package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.LinearLayout;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.adapters.TestLayoutAdapter;

import java.util.Arrays;

/**
 * @author caiwenqing
 * @data 2018/5/9
 * description:
 */
public class AlphaScrollTitleActivity extends AppCompatActivity {

    private LinearLayout ll_title;
    private NestedScrollView mNstedScrollView;
    private RecyclerView mRecycleView;

    String[] strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphascroll);

        ll_title = findViewById(R.id.alpha_title);
        mNstedScrollView = findViewById(R.id.alpha_nestedScrollView);
        mRecycleView = findViewById(R.id.alpha_recycleView);

        mRecycleView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecycleView.setAdapter(new TestLayoutAdapter(this, Arrays.asList(strings)));
    }
}
