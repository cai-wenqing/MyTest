package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.adapters.NineViewAdapter;
import com.aiyakeji.mytest.bean.PicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CWQ
 * @date 2020/7/20
 */
public class NineViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nine_view);
        RecyclerView rv = findViewById(R.id.recycleView);


        List<List<PicBean>> datas = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<PicBean> list = new ArrayList<>();
            if (i % 2 == 1){
                list.add(new PicBean("",500,500,"NO." + i));
            }else {
                for (int j = 0; j < 9; j++) {
                    list.add(new PicBean("",90,90,"NO." + i + ",item:" + j));
                }
            }
            datas.add(list);
        }

        NineViewAdapter adapter = new NineViewAdapter(datas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }
}
