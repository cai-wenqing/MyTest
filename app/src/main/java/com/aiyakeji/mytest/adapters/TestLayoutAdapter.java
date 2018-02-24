package com.aiyakeji.mytest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyakeji.mytest.R;

import java.util.List;

/**
 * Created by Administrator on 2018/2/24 0024.
 */

public class TestLayoutAdapter extends RecyclerView.Adapter<TestLayoutAdapter.MyViewHolder> {
    Context mContext;
    List<String> mList;

    public TestLayoutAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.waiter_table_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_content.setText(mList.get(position));
        if (position % 2 == 0)
            holder.view.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_pic;
        ImageView iv_flag;
        TextView tv_content;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.item_tv);
            view = itemView.findViewById(R.id.item_view);
        }
    }
}
