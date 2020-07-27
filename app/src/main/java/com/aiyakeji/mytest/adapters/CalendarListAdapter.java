package com.aiyakeji.mytest.adapters;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.bean.DateBean;

/**
 * @author caiwenqing
 * @data 2018/5/17
 * description:
 */
public class CalendarListAdapter extends RecyclerView.Adapter<CalendarListAdapter.CalendarListHolder> {

    private Context mContext;
    //显示几个月的日历
    private int mMonths;

    private int mStartYearInt;
    private int mStartMonthInt;
    private int mStartDayInt;

    /**
     * 初始化数据
     *
     * @param context
     * @param dateBean 第一个有效日期
     * @param months    一共展示几个月
     */
    public CalendarListAdapter(Context context, DateBean dateBean, int months) {
        mContext = context;
        mMonths = months;

        mStartYearInt = dateBean.getYear();
        mStartMonthInt = dateBean.getMonth();
        mStartDayInt = dateBean.getDay();
    }

    @Override
    public CalendarListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CalendarListHolder holder = new CalendarListHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_calendarlist, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CalendarListHolder holder, int position) {
        int currentYear;
        int currentMonth;
        int startEnableDay;

        if (position == 0) {
            startEnableDay = mStartDayInt;
        } else {
            startEnableDay = 1;
        }

        if (mStartMonthInt + position <= 12) {
            currentMonth = mStartMonthInt + position;
            currentYear = mStartYearInt;
        } else {
            currentYear = mStartYearInt + (mStartMonthInt + position) / 12;
            currentMonth = (mStartMonthInt + position) % 12;
        }

        holder.tv_date.setText(currentYear + "年" + currentMonth + "月");
        GridLayoutManager manager = new GridLayoutManager(mContext, 7);
        holder.list_recycleview.setLayoutManager(manager);
        CalendarItemAdapter itemAdapter = new CalendarItemAdapter(mContext, currentYear, currentMonth, startEnableDay);
        holder.list_recycleview.setAdapter(itemAdapter);
    }

    @Override
    public int getItemCount() {
        return mMonths;
    }

    class CalendarListHolder extends RecyclerView.ViewHolder {

        TextView tv_date;
        RecyclerView list_recycleview;

        public CalendarListHolder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.item_calendarlist_tv_date);
            list_recycleview = itemView.findViewById(R.id.item_calendarlist_recycleview);
        }
    }
}
