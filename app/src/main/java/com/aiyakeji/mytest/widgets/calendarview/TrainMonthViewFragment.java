package com.aiyakeji.mytest.widgets.calendarview;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.adapters.TrainMonthListAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author caiwenqing
 * @data 2018/5/18
 * description:火车票价格日历
 */
public class TrainMonthViewFragment extends Fragment implements TrainMonthListAdapter.OnClickMonthViewDayListener {
    public static MonthViewModel selectedDay;

    private RecyclerView mRecycleView;
    private TextView tv_head;

    private TrainMonthListAdapter adapter;
    private LinkedHashMap<String, List<MonthViewModel>> mMap;

    public static TrainMonthViewFragment newInstance(MonthViewModel firstDay, MonthViewModel endDay, List<MonthViewModel> list) {
        TrainMonthViewFragment fragment = new TrainMonthViewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("firstDay", firstDay);
        bundle.putSerializable("endDay", endDay);
        bundle.putSerializable("dayPriceList", (Serializable) list);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_airticketmonthview, container, false);
        mRecycleView = rootView.findViewById(R.id.monthview_recycleview);
        tv_head = rootView.findViewById(R.id.monthview_tv_head);

        MonthViewModel firstDayModel = (MonthViewModel) getArguments().getSerializable("firstDay");
        MonthViewModel endDayModel = (MonthViewModel) getArguments().getSerializable("endDay");
        List<MonthViewModel> dayPriceList = (List<MonthViewModel>) getArguments().getSerializable("dayPriceList");


        mMap = new LinkedHashMap<>();
        for (MonthViewModel model : dayPriceList) {
            String yearMonth = model.year + "-" + model.month;
            if (!mMap.containsKey(yearMonth)) {
                List<MonthViewModel> list = new ArrayList<>();
                list.add(model);
                mMap.put(yearMonth, list);
            } else {
                List<MonthViewModel> list = mMap.get(yearMonth);
                list.add(model);
                mMap.put(yearMonth, list);
            }
        }

        adapter = new TrainMonthListAdapter(getContext(), mMap);
        adapter.setOnClickMonthViewDayListener(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecycleView.setAdapter(adapter);
        mRecycleView.addOnScrollListener(new RvScrollListener());
        return rootView;
    }

    @Override
    public void onClickMonthDay(MonthViewModel model) {
        if (selectedDay != null) {
            Toast.makeText(getContext(), "起始:" + selectedDay.toString(), Toast.LENGTH_SHORT).show();
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
