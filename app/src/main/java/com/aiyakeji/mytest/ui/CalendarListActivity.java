package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.calendarview.AirTicketMonthViewFragment;
import com.aiyakeji.mytest.widgets.calendarview.MonthViewFragment;
import com.aiyakeji.mytest.widgets.calendarview.MonthViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caiwenqing
 * @data 2018/5/17
 * description:日历列表测试
 */
public class CalendarListActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout fl;
    private TextView tv_one;
    private TextView tv_two;
    private TextView tv_three;
    private Fragment[] fragments = new Fragment[3];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarlist);
        fl = findViewById(R.id.calendarlist_fl);
        tv_one = findViewById(R.id.calendarlist_tv_one);
        tv_two = findViewById(R.id.calendarlist_tv_two);
        tv_three = findViewById(R.id.calendarlist_tv_three);
        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);
        tv_three.setOnClickListener(this);


        List<MonthViewModel> list = new ArrayList<>();
        for (int i = 20; i <= 31; i++) {
            MonthViewModel model = new MonthViewModel(2019, 3, i);
            model.price = i + 0.5 + "";
            list.add(model);
        }
        for (int i = 4; i < 7; i++) {
            for (int j = 1; j <= 30; j++) {
                MonthViewModel model = new MonthViewModel(2019, i, j);
                model.price = j + 0.5 + "";
                list.add(model);
            }
        }


        fragments[0] = new CalendarListFragment();
        fragments[1] = MonthViewFragment.newInstance(null, null);
        fragments[2] = AirTicketMonthViewFragment.newInstance(null, null, list);
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.add(R.id.calendarlist_fl, fragments[0]);
        trx.add(R.id.calendarlist_fl, fragments[1]);
        trx.add(R.id.calendarlist_fl, fragments[2]);
        trx.hide(fragments[1]);
        trx.hide(fragments[2]);
        trx.show(fragments[0]).commit();
    }


    @Override
    public void onClick(View view) {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.calendarlist_tv_one:
                trx.show(fragments[0]);
                trx.hide(fragments[1]);
                trx.hide(fragments[2]);
                break;
            case R.id.calendarlist_tv_two:
                trx.hide(fragments[0]);
                trx.show(fragments[1]);
                trx.hide(fragments[2]);
                break;
            case R.id.calendarlist_tv_three:
                trx.hide(fragments[0]);
                trx.hide(fragments[1]);
                trx.show(fragments[2]);
                break;
            default:
                break;
        }
        trx.commit();
    }
}
