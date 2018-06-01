package com.aiyakeji.mytest.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyakeji.mytest.R;

import java.util.List;

/**
 * @author caiwenqing
 * @data 2018/5/31
 * description:
 */
public class FootViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "FootAdapter";
    private static final int TYPE_CONTENT = 1;
    private static final int TYPE_FOOT = 2;
    private boolean needFoot = false;
    private int footViewHeight = 0;

    private Context mContext;
    private List<String> mList;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;


    public FootViewAdapter(Context context, List<String> list, RecyclerView recyclerView,
                           LinearLayoutManager layoutManager) {
        mContext = context;
        mList = list;
        mRecyclerView = recyclerView;
        mLayoutManager = layoutManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT) {
            return new FootViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_rv_footactivity_foot, parent, false));
        } else {
            return new ContentViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_rv_footactivity_content, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FootViewHolder) {
            if (footViewHeight > 0) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) ((FootViewHolder) holder).itemView.getLayoutParams();
                layoutParams.height = footViewHeight;
                ((FootViewHolder) holder).itemView.setLayoutParams(layoutParams);
            }
            ((FootViewHolder) holder).tv.setText("这是脚布局啊");
        } else if (holder instanceof ContentViewHolder) {
            if (position < mList.size()) {
                ((ContentViewHolder) holder).tv.setText(mList.get(position));
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position < mList.size()) {
                    int n1 = position - mLayoutManager.findFirstVisibleItemPosition();
                    if (0 <= n1 && n1 < mRecyclerView.getChildCount()) {
                        int top1 = mRecyclerView.getChildAt(n1).getTop();
                        mRecyclerView.scrollBy(0, top1);
                        int firstVisiblePosition = mLayoutManager.findFirstVisibleItemPosition();
                        int lastVisiblePosition = mLayoutManager.findLastVisibleItemPosition();
                        Log.i(TAG, "firstVisiblePosition:" + firstVisiblePosition + ",position:" + position);
                        if (firstVisiblePosition < position) {
                            int n2 = position - firstVisiblePosition;
                            int top2;
                            if (!needFoot) {
                                top2 = mRecyclerView.getChildAt(n2).getTop();
                            } else {
                                top2 = mRecyclerView.getChildAt(n2).getTop() + footViewHeight;
                            }
                            needFoot = true;
                            footViewHeight = top2;
                            Log.i(TAG, "footHeight:" + footViewHeight);
                            notifyDataSetChanged();
                            mRecyclerView.scrollToPosition(getItemCount() - 1);

                            int n3 = position - mLayoutManager.findFirstVisibleItemPosition();
                            int top3 = mRecyclerView.getChildAt(n3).getTop();
                            mRecyclerView.scrollBy(0, top3);
                        } else if (needFoot && lastVisiblePosition < mList.size()) {
                            needFoot = false;
                            footViewHeight = 0;
                            notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (needFoot && position >= mList.size()) {
            return TYPE_FOOT;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return needFoot ? mList.size() + 1 : mList.size();
    }


    class ContentViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public ContentViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_footview_tv);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public FootViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_footactivity_foot_tv);
        }
    }
}
