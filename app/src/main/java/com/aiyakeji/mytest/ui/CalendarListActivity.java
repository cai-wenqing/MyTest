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
import com.aiyakeji.mytest.widgets.calendarview.MonthViewFragment;

/**
 * @author caiwenqing
 * @data 2018/5/17
 * description:日历列表测试
 */
public class CalendarListActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout fl;
    private TextView tv_one;
    private TextView tv_two;
    private Fragment[] fragments = new Fragment[2];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarlist);
        fl = findViewById(R.id.calendarlist_fl);
        tv_one = findViewById(R.id.calendarlist_tv_one);
        tv_two = findViewById(R.id.calendarlist_tv_two);
        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);

        fragments[0] = new CalendarListFragment();
        fragments[1] = MonthViewFragment.newInstance(null, null);
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.add(R.id.calendarlist_fl, fragments[0]);
        trx.add(R.id.calendarlist_fl, fragments[1]);
        trx.hide(fragments[1]);
        trx.show(fragments[0]).commit();
    }


    @Override
    public void onClick(View view) {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.calendarlist_tv_one:
                trx.hide(fragments[1]);
                trx.show(fragments[0]);
                break;
            case R.id.calendarlist_tv_two:
                trx.hide(fragments[0]);
                trx.show(fragments[1]);
                break;
            default:
                break;
        }
        trx.commit();
    }
}
