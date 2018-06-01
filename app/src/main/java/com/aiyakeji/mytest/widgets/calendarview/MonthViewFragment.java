package com.aiyakeji.mytest.widgets.calendarview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.adapters.MonthListAdapter;

import java.util.ArrayList;

/**
 * @author caiwenqing
 * @data 2018/5/18
 * description:
 */
public class MonthViewFragment extends Fragment implements MonthListAdapter.OnClickMonthViewDayListener {
    public static MonthViewModel firstEnableDay;
    public static MonthViewModel firstSelectedDay;
    public static MonthViewModel secondSelectedDay;

    private RecyclerView mRecycleView;
    private TextView tv_head;

    ArrayList<MonthViewModel> list;
    private MonthListAdapter adapter;


    public static MonthViewFragment newInstance(MonthViewModel firstDay, MonthViewModel endDay) {
        MonthViewFragment fragment = new MonthViewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("firstDay", firstDay);
        bundle.putSerializable("endDay", endDay);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monthview, container, false);
        mRecycleView = rootView.findViewById(R.id.monthview_recycleview);
        tv_head = rootView.findViewById(R.id.monthview_tv_head);

        MonthViewModel firstDayModel = (MonthViewModel) getArguments().getSerializable("firstDay");
        MonthViewModel endDayModel = (MonthViewModel) getArguments().getSerializable("endDay");
        if (firstDayModel != null && endDayModel != null) {
            firstSelectedDay = firstDayModel;
            secondSelectedDay = endDayModel;
            firstEnableDay = new MonthViewModel(DateTimeUtil.getCurrentYear(), DateTimeUtil.getCurrentMonth(), DateTimeUtil.getCurrentDay());
        } else {
            int year = DateTimeUtil.getCurrentYear();
            int month = DateTimeUtil.getCurrentMonth();
            int day = DateTimeUtil.getCurrentDay();

            firstSelectedDay = new MonthViewModel(year, month, day);
            firstEnableDay = new MonthViewModel(year, month, day);

            int days = DateTimeUtil.getDaysFromYearMonth(year + "年" + month + "月" + day + "日");
            if (day + 1 <= days) {
                secondSelectedDay = new MonthViewModel(year, month, day + 1);
            } else {
                if (month == 12) {
                    secondSelectedDay = new MonthViewModel(year + 1, 1, 1);
                } else {
                    secondSelectedDay = new MonthViewModel(year, month + 1, 1);
                }
            }
        }


        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                list.add(new MonthViewModel(firstEnableDay.year, firstEnableDay.month, firstEnableDay.day));
            } else {
                list.add(new MonthViewModel(firstEnableDay.year + (firstEnableDay.month + i == 12 ? 0 : (firstEnableDay.month + i) / 12),
                        (firstEnableDay.month + i) % 12 == 0 ? 12 : (firstEnableDay.month + i) % 12, 1));
            }
        }

        adapter = new MonthListAdapter(getContext(), list);
        adapter.setOnClickMonthViewDayListener(this);
        mRecycleView.setAdapter(adapter);
        mRecycleView.addOnScrollListener(new RvScrollListener());
        return rootView;
    }

    @Override
    public void onClickMonthDay(MonthViewModel model) {
        if (firstSelectedDay != null && secondSelectedDay != null) {
            Toast.makeText(getContext(), "起始:" + firstSelectedDay.toString() + ",截止：" + secondSelectedDay.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private class RvScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            View stickyInfoView = recyclerView.getChildAt(0);
            if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                tv_head.setText(String.valueOf(stickyInfoView.getContentDescription()));
            }
            View transInfoView = recyclerView.findChildViewUnder(tv_head.getMeasuredWidth() / 2
                    , tv_head.getMeasuredHeight() + 1);
            if (transInfoView != null) {
                int deltaY = transInfoView.getTop() - tv_head.getMeasuredHeight();
                if (transInfoView.getTop() > 0) {
                    tv_head.setTranslationY(deltaY);
                } else {
                    tv_head.setTranslationY(0);
                }
            }
        }
    }
}
