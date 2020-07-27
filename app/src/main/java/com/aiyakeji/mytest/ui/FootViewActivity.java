package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.adapters.FootViewAdapter;

import java.util.Arrays;

/**
 * @author caiwenqing
 * @data 2018/5/31
 * description:recycleview动态脚布局测试
 */
public class FootViewActivity extends AppCompatActivity {

    private RecyclerView rv;
    private LinearLayoutManager manager;

    private String[] datas = {"夏", "商", "西周", "东周", "春秋", "齐",
            "楚", "燕", "韩", "赵", "魏", "秦", "西汉", "东汉"
            , "魏", "蜀", "吴", "西晋", "东晋", "宋", "元", "明", "清"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footview);
        rv = findViewById(R.id.footview_rv);

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);
        FootViewAdapter adapter = new FootViewAdapter(this, Arrays.asList(datas),rv,manager);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
