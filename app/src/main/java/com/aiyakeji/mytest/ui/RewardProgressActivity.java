package com.aiyakeji.mytest.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.RewardProgress;
import com.aiyakeji.mytest.widgets.TextProgressBar;

/**
 * @author CWQ
 * @date 2020-02-11
 * 奖金进度条测试
 */
public class RewardProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_progress);

        final RewardProgress rp = findViewById(R.id.rp);
        rp.setMax(100);
        rp.setProgress(5);

        TextView tvReset = findViewById(R.id.tv_reset);
        TextView tvStart = findViewById(R.id.tv_start);

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rp.setProgress(5);
            }
        });
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rp.setProgressWithAnim(100);
            }
        });


        //第二个进度条
        TextProgressBar progressBar = findViewById(R.id.pb);
        TextView tvReset1 = findViewById(R.id.tv_reset1);
        TextView tvStart1 = findViewById(R.id.tv_start1);

        tvReset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressBar.setProgress(0, true);
                }
            }
        });
        tvStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressBar.setProgress(100, true);
                }
            }
        });
    }
}
