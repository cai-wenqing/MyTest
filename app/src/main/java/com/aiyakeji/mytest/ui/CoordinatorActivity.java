package com.aiyakeji.mytest.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.utils.StartForResultUtil;

/**
 * Created by Administrator on 2017/5/12 0012.
 */

public class CoordinatorActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        LinearLayout ll = findViewById(R.id.main_ll_jiuba);

        //测试跳转获取数据工具
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoordinatorActivity.this,AnimatorActivity.class);
                new StartForResultUtil(CoordinatorActivity.this)
                        .startForResult(intent, 1, new StartForResultUtil.Callback() {
                            @Override
                            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                                Log.e("coordinator测试","收到返回，requestCode:"+requestCode+",resultCode:"+resultCode);
                            }
                        });
            }
        });


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult","收到返回，requestCode:"+requestCode+",resultCode:"+resultCode);
    }
}
