package com.aiyakeji.mytest.ui.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.adapters.CalendarListAdapter;
import com.aiyakeji.mytest.bean.DateBean;
import com.aiyakeji.mytest.event.DateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author caiwenqing
 * @data 2018/5/17
 * description:
 */
public class CalendarListFragment extends Fragment{

    public static DateBean startDate = new DateBean(2018, 5, 19);
    public static DateBean endDate = new DateBean(2018, 5, 28);

    private DateBean firstEnableDate = new DateBean(2018, 5, 18);

    private RecyclerView mRecycleView;
    private CalendarListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendarlist, container, false);
        mRecycleView = rootView.findViewById(R.id.calendarfragment_recycleview);

        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new CalendarListAdapter(getContext(), firstEnableDate, 5);
        mRecycleView.setAdapter(adapter);

        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DateEvent event) {
        if (event != null) {
            if (event.flag == 1) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
