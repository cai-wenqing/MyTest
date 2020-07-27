package com.aiyakeji.mytest.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.calendarview.MonthViewModel;
import com.aiyakeji.mytest.widgets.calendarview.TrainMonthView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author caiwenqing
 * @data 2018/5/18
 * description:
 */
public class TrainMonthListAdapter extends RecyclerView.Adapter<TrainMonthListAdapter.MonthHolder> {

    private Context mContext;
    private OnClickMonthViewDayListener mListener;
    private ArrayList<TrainMonthView> refreshViews = new ArrayList<>();
    private LinkedHashMap<String, List<MonthViewModel>> mMap;
    private List<String> yearMonthList;

    public interface OnClickMonthViewDayListener {
        void onClickMonthDay(MonthViewModel model);
    }

    public void setOnClickMonthViewDayListener(OnClickMonthViewDayListener listener) {
        mListener = listener;
    }


    public TrainMonthListAdapter(Context context, LinkedHashMap<String, List<MonthViewModel>> map) {
        mContext = context;
        mMap = map;
        yearMonthList = new ArrayList<>();
        yearMonthList.addAll(mMap.keySet());
    }


    @Override
    public MonthHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MonthHolder holder = new MonthHolder(LayoutInflater.from(mContext).inflate(R.layout.item_trainmonthlistadapter, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MonthHolder holder, int position) {
        String s = yearMonthList.get(position);
        String year = s.split("-")[0];
        String month = s.split("-")[1];
        holder.tv_yearMonth.setText(year + "年" + month + "月");
        holder.itemView.setContentDescription(year + "年" + month + "月");
        holder.mv.setDayPriceList(mMap.get(s));
        holder.mv.doRefush();

        holder.mv.setOnDayClickListener(new TrainMonthView.OnDayClickListener() {
            @Override
            public void onDayClick(MonthViewModel model) {
                if (!refreshViews.isEmpty()) {
                    for (TrainMonthView monthView : refreshViews) {
                        monthView.doRefush();
                    }
                }
                if (mListener != null) {
                    mListener.onClickMonthDay(model);
                }
            }
        });
        refreshViews.add(holder.mv);
    }

    @Override
    public int getItemCount() {
        return yearMonthList.size();
    }

    class MonthHolder extends RecyclerView.ViewHolder {

        private TextView tv_yearMonth;
        private TrainMonthView mv;

        public MonthHolder(View itemView) {
            super(itemView);
            tv_yearMonth = itemView.findViewById(R.id.monthlist_tv_date);
            mv = itemView.findViewById(R.id.monthlist_mv);
        }
    }
}
