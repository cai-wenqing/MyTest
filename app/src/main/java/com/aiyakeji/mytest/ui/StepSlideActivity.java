package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.StepSlideView;

/**
 * Author：CWQ
 * Date：2019/4/17
 * Desc:档位选择器测试
 */
public class StepSlideActivity extends AppCompatActivity {
    private static final String TAG = "StepSlideActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepslide);

        StepSlideView stepSlideView = findViewById(R.id.stepSlideView);
        final TextView tv = findViewById(R.id.textView);
        stepSlideView.setOnPointChangeListener(new StepSlideView.OnPointChangeListener() {
            @Override
            public void onChange(int position, String text) {
                Log.e(TAG, "position:" + position + ",text:" + text);
                tv.setText(text);
            }
        });
    }
}
