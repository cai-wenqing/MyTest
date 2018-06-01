package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.calendarview.DateTimeUtil;
import com.aiyakeji.mytest.widgets.calendarview.MonthViewModel;

/**
 * @author caiwenqing
 * @data 2018/5/15
 * description:日历控件测试
 */
public class CalendarActivity extends AppCompatActivity {

    public static MonthViewModel firstSelectedDay;
    public static MonthViewModel secondSelectedDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        TextView tv = findViewById(R.id.calendar_tv);
        tv.setText(DateTimeUtil.formatDateGetWeek("2018-05-15"));
    }
}
