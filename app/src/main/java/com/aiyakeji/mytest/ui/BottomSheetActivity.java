package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.BottomSheet;

/**
 * Author：CWQ
 * Date：2019-09-09
 * Desc：底部弹窗
 */
public class BottomSheetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomsheet);

        TextView tvButton1 = findViewById(R.id.tv_button1);

        tvButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheet.BottomSheetBuilder(BottomSheetActivity.this)
                        .setContentId(R.layout.item_dialog_test)
                        .build()
                        .show();
            }
        });
    }
}
