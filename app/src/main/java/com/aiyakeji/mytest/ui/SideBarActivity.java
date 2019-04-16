package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.SideBar;

/**
 * Author：CWQ
 * Date：2019/4/16
 * Desc:侧滑SideBar
 */
public class SideBarActivity extends AppCompatActivity {

    private static final String TAG = "SideBarActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidebar);

        SideBar mSideBar = findViewById(R.id.sideBar);
        mSideBar.setOnIndexChangeListener(new SideBar.OnIndexChangeListener() {
            @Override
            public void indexChange(String word, int position) {
                Log.e(TAG, "word:" + word + ",position:" + position);
                Toast.makeText(SideBarActivity.this, word, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
