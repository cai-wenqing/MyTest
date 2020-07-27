package com.aiyakeji.mytest.adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.event.DateEvent;
import com.aiyakeji.mytest.ui.CalendarListFragment;
import com.aiyakeji.mytest.widgets.calendarview.DateTimeUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * @author caiwenqing
 * @data 2018/5/17
 * description:
 */
public class CalendarItemAdapter extends RecyclerView.Adapter<CalendarItemAdapter.CalendarItemHolder> {

    private Context mContext;
    private int mYear, mMonth, mStartEnableDay;
    private int daysInMonth;
    private int startDayWeek;

    public CalendarItemAdapter(Context context, int year, int month, int day) {
        mContext = context;
        mYear = year;
        mMonth = month;
        mStartEnableDay = day;
        daysInMonth = DateTimeUtil.getDaysFromYearMonth(year + "年" + month + "月" + day + "日");
        startDayWeek = DateTimeUtil.formatDateGetWeekindex(year + "年" + month + "月" + day + "日");
    }

    @Override
    public CalendarItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CalendarItemHolder holder = new CalendarItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item_calendaritem, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CalendarItemHolder holder, final int position) {
        if (position >= startDayWeek) {
            int curPosition = position - startDayWeek;
            final int day = curPosition + 1;
            holder.tv_out_day.setText(day + "");
            holder.tv_inside_day.setText(day + "");

            if (position % 7 == 0 || position % 7 == 6) {
                holder.tv_out_day.setTextColor(Color.parseColor("#00ffff"));
            } else if (curPosition < mStartEnableDay - 1) {
                holder.tv_out_day.setTextColor(Color.GRAY);
            } else {
                holder.tv_out_day.setTextColor(Color.BLACK);
            }

            if (mYear == CalendarListFragment.startDate.getYear()
                    && mMonth == CalendarListFragment.startDate.getMonth()
                    && day == CalendarListFragment.startDate.getDay()) {
                holder.tv_state.setText("入住");
                holder.rl_bg.setBackgroundColor(Color.BLUE);
                holder.tv_out_day.setVisibility(View.GONE);
            } else if (mYear == CalendarListFragment.endDate.getYear()
                    && mMonth == CalendarListFragment.endDate.getMonth()
                    && day == CalendarListFragment.endDate.getDay()) {
                holder.tv_state.setText("离店");
                holder.rl_bg.setBackgroundColor(Color.BLUE);
                holder.tv_out_day.setVisibility(View.GONE);
            } else if (DateTimeUtil.compare2date(DateTimeUtil.formatYearMonthDay1(mYear + "年" + mMonth + "月" + day + "日"),
                    DateTimeUtil.formatYearMonthDay1(CalendarListFragment.startDate.getYear() + "年" +
                            CalendarListFragment.startDate.getMonth() + "月" + CalendarListFragment.startDate.getDay() + "日")) == 1
                    && DateTimeUtil.compare2date(DateTimeUtil.formatYearMonthDay1(CalendarListFragment.endDate.getYear() + "年" +
                            CalendarListFragment.endDate.getMonth() + "月" + CalendarListFragment.endDate.getDay() + "日"),
                    DateTimeUtil.formatYearMonthDay1(mYear + "年" + mMonth + "月" + day + "日")) == 1) {
                holder.tv_state.setText("");
                holder.rl_bg.setBackgroundColor(Color.GREEN);
                holder.ll_selected.setVisibility(View.INVISIBLE);
                holder.tv_out_day.setVisibility(View.VISIBLE);
                holder.tv_out_day.setTextColor(Color.BLACK);
            } else {
                holder.tv_state.setText("");
                holder.rl_bg.setBackgroundColor(Color.WHITE);
                holder.ll_selected.setVisibility(View.VISIBLE);
                holder.tv_out_day.setVisibility(View.VISIBLE);
            }

            if (curPosition >= mStartEnableDay - 1) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!CalendarListFragment.startDate.isEmpty() && CalendarListFragment.endDate.isEmpty()) {

                            if (DateTimeUtil.compare2date(DateTimeUtil.formatYearMonthDay1(mYear + "年" + mMonth + "月" + day + "日")
                                    , DateTimeUtil.formatYearMonthDay1(CalendarListFragment.startDate.getYear() + "年" + CalendarListFragment.startDate.getMonth() + "月"
                                            + CalendarListFragment.startDate.getDay() + "日")) == 1) {
                                CalendarListFragment.endDate.setYear(mYear);
                                CalendarListFragment.endDate.setMonth(mMonth);
                                CalendarListFragment.endDate.setDay(day);
                            } else {
                                CalendarListFragment.startDate.setYear(mYear);
                                CalendarListFragment.startDate.setMonth(mMonth);
                                CalendarListFragment.startDate.setDay(day);
                            }
                            EventBus.getDefault().post(new DateEvent(1));
                        } else {
                            CalendarListFragment.startDate.setYear(mYear);
                            CalendarListFragment.startDate.setMonth(mMonth);
                            CalendarListFragment.startDate.setDay(day);
                            CalendarListFragment.endDate.setEmpey();
                            EventBus.getDefault().post(new DateEvent(1));
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return daysInMonth + startDayWeek;
    }

    class CalendarItemHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl_bg;
        private TextView tv_inside_day;
        private TextView tv_state;
        private LinearLayout ll_selected;
        private TextView tv_out_day;

        public CalendarItemHolder(View itemView) {
            super(itemView);
            rl_bg = itemView.findViewById(R.id.item_calendaritem_rl_bg);
            tv_inside_day = itemView.findViewById(R.id.item_calendaritem_tv_insideday);
            tv_state = itemView.findViewById(R.id.item_calendaritem_tv_state);
            ll_selected = itemView.findViewById(R.id.item_calendaritem_ll_selected);
            tv_out_day = itemView.findViewById(R.id.item_calendaritem_tv_centerdday);
        }
    }
}
