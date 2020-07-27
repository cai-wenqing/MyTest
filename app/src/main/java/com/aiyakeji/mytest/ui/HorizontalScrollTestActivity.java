package com.aiyakeji.mytest.ui;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.aiyakeji.mytest.R;

/**
 * Author：CWQ
 * Date：2019/7/18
 * Desc：仿viewpager水平滑动layout
 */
public class HorizontalScrollTestActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontalscroll);
        textView = findViewById(R.id.tvTest);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "translationY", 0, 150);
                animator.setDuration(400);
                animator.start();
            }
        });
    }
}
