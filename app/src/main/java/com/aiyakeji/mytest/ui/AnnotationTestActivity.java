package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.annotation.MethodInfo1;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/3/9 0009.
 * 测试注解
 */

public class AnnotationTestActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_content;
    private Button btn_runtime, btn_class;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation_test);

        tv_content = findViewById(R.id.annotation_tv_content);
        btn_runtime = findViewById(R.id.annotation_btn_analyse_runtime);
        btn_class = findViewById(R.id.annotation_btn_analyse_class);

        btn_runtime.setOnClickListener(this);
        btn_class.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.annotation_btn_analyse_runtime:
                analysisRuntimeAnnotation();
                break;
            case R.id.annotation_btn_analyse_class:
                break;
        }
    }


    //解析运行时自定义注解
    private void analysisRuntimeAnnotation() {
        try {
            Class cls = Class.forName("com.aiyakeji.mytest.annotation.ApplyMethod");
            for (Method method : cls.getMethods()) {
                MethodInfo1 methodInfo1 = method.getAnnotation(MethodInfo1.class);
                if (methodInfo1 != null) {
                    tv_content.setText("name:" + methodInfo1.name() + ",date:" + methodInfo1.date() + ",version:" + methodInfo1.version());
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
