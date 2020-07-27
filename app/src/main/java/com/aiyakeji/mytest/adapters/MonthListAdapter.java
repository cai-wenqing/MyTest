package com.aiyakeji.mytest.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.widgets.calendarview.MonthView;
import com.aiyakeji.mytest.widgets.calendarview.MonthViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caiwenqing
 * @data 2018/5/18
 * description:
 */
public class MonthListAdapter extends RecyclerView.Adapter<MonthListAdapter.MonthHolder> {

    private Context mContext;

    private List<MonthViewModel> mDatas;

    private OnClickMonthViewDayListener mListener;

    private ArrayList<MonthView> refreshViews = new ArrayList<>();

    public interface OnClickMonthViewDayListener {
        void onClickMonthDay(MonthViewModel model);
    }

    public void setOnClickMonthViewDayListener(OnClickMonthViewDayListener listener) {
        mListener = listener;
    }


    public MonthListAdapter(Context context, List<MonthViewModel> list) {
        mContext = context;
        mDatas = list;
    }

    @Override
    public MonthHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MonthHolder holder = new MonthHolder(LayoutInflater.from(mContext).inflate(R.layout.item_monthlistadapter, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MonthHolder holder, int position) {
        MonthViewModel model = mDatas.get(position);
        holder.mv.setEnableDate(model.year, model.month, model.day);
        holder.tv_yearMonth.setText(model.year + "年" + model.month + "月");
        holder.itemView.setContentDescription(model.year + "年" + model.month + "月");
        holder.mv.doRefush();

        holder.mv.setOnDayClickListener(new MonthView.OnDayClickListener() {
            @Override
            public void onDayClick(MonthViewModel model) {
                if (!refreshViews.isEmpty()) {
                    for (MonthView monthView : refreshViews) {
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
        return mDatas.size();
    }

    class MonthHolder extends RecyclerView.ViewHolder {

        private TextView tv_yearMonth;
        private MonthView mv;

        public MonthHolder(View itemView) {
            super(itemView);
            tv_yearMonth = itemView.findViewById(R.id.monthlist_tv_date);
            mv = itemView.findViewById(R.id.monthlist_mv);
        }
    }
}
