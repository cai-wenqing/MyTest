package com.aiyakeji.mytest.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.utils.DBUtil;
import com.aiyakeji.mytest.widgets.EditInputMoneyFilter;
import com.aiyakeji.mytest.widgets.LabelLayout;
import com.aiyakeji.mytest.widgets.SlideImageView;

import java.io.IOException;
import java.util.Arrays;

/**
 * Author：CWQ
 * Date：2019/2/26
 * Desc:标签控件测试类
 */
public class LabelActivity extends AppCompatActivity {

    String[] labelArr = {"积极", "内向", "活泼", "开朗", "卖萌"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);

        final LabelLayout labelLayout = findViewById(R.id.labelLayout);
        TextView textView = findViewById(R.id.tvReadDatabase);
        SlideImageView slideImageView = findViewById(R.id.slideImageView);


        labelLayout.setLabels(Arrays.asList(labelArr));

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DBUtil.copyAssetsToDB(LabelActivity.this, "meituan_cities2.db");
                    String destPath = "data/data/com.aiyakeji.mytest/databases/" + "meituan_cities2.db";
                    SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(destPath, null);
                    Cursor cursor1 = database.rawQuery("select name from sqlite_master where type='table' order by name", null);
                    while (cursor1.moveToNext()) {
                        for (int i = 0; i < cursor1.getColumnCount(); i++) {
                            String name = cursor1.getString(i);
                            Log.e("LabelActivity测试", "System.out:" + name);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        slideImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LabelActivity.this,"click",Toast.LENGTH_SHORT).show();
            }
        });


        EditText etMoney = findViewById(R.id.etInputMoney);
        etMoney.setFilters(new InputFilter[]{new EditInputMoneyFilter()});
    }
}
