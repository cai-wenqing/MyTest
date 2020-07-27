package com.aiyakeji.mytest.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.listener.OnClickItemListener;

import java.util.List;

/**
 * Author：CWQ
 * Date：2019/2/28
 * Desc:
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

    private final Context mContext;
    private final List<String> mList;
    private OnClickItemListener mListener;

    public MainAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    public void setOnClickItemListener(OnClickItemListener listener) {
        mListener = listener;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_main, parent, false);
        return new MainHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MainHolder holder, final int position) {
        holder.textView.setText(mList.get(position));
        if (mListener != null) {
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickItem(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class MainHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MainHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
