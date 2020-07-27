package com.aiyakeji.mytest.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyakeji.mytest.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/14 0014.
 * slidingcard适配器
 */


public class SlidingCardAdapter extends RecyclerView.Adapter<SlidingCardAdapter.SlidingCardViewHolder> {
    private Context mContext;
    private List<String> mList;

    public SlidingCardAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public SlidingCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SlidingCardViewHolder holder = new SlidingCardViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_slidingcardlayout, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(SlidingCardViewHolder holder, int position) {
        holder.tv.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class SlidingCardViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public SlidingCardViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_slidingcard_tv);
        }
    }
}
