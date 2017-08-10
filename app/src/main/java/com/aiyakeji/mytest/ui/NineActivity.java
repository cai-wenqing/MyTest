package com.aiyakeji.mytest.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.bean.Student;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 仿探探滑动卡片
 */

public class NineActivity extends Activity {
    private RecyclerView mRecycleView;
    private ArrayList<Student> dataList;
    private SwipeCardAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nine);
        initView();
        initData();
    }

    private void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.nine_recycleview);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void initData() {
        dataList = Student.getSampleData(45);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(mRecycleView);

        mAdapter = new SwipeCardAdapter(R.layout.item_nine_recycleview, dataList);
        mRecycleView.setAdapter(mAdapter);
    }


    ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(dataList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(dataList, i, i - 1);
                }
            }
            mAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            dataList.remove(position);
            mAdapter.notifyItemRemoved(position);
        }
    };


    public class SwipeCardAdapter extends BaseQuickAdapter<Student, BaseViewHolder> {

        public SwipeCardAdapter(@LayoutRes int layoutResId, @Nullable List<Student> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Student item) {
            helper.setText(R.id.item_nine_tv_name, item.getName())
                    .setText(R.id.item_nine_tv_age, "" + item.getAge());
        }
    }
}
