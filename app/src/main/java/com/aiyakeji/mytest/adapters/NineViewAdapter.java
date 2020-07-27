package com.aiyakeji.mytest.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.bean.PicBean;
import com.aiyakeji.mytest.widgets.NineImageLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CWQ
 * @date 2020/7/20
 */
public class NineViewAdapter extends RecyclerView.Adapter<NineViewAdapter.NineViewHolder> {

    private List<List<PicBean>> mList = new ArrayList<>();

    public NineViewAdapter(List<List<PicBean>> list) {
        if (list != null) {
            mList.clear();
            mList.addAll(list);
        }
    }


    @NonNull
    @Override
    public NineViewAdapter.NineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_adapter_nine_view, viewGroup, false);
        return new NineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NineViewAdapter.NineViewHolder viewHolder, int i) {
        viewHolder.nineImageLayout.updateData(mList.get(i));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class NineViewHolder extends RecyclerView.ViewHolder {

        NineImageLayout nineImageLayout;

        public NineViewHolder(@NonNull View itemView) {
            super(itemView);
            nineImageLayout = itemView.findViewById(R.id.item_nil);
        }
    }
}
