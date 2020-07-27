package com.aiyakeji.mytest.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.bean.Student;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 测试recycleview万能适配器，左右滑动删除
 */

public class UniversalAdapterActivity extends Activity {
    private RecyclerView mRecycleView;
    private StudentAdapter adapter;
    private ArrayList<Student> dataList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eight);
        mRecycleView = (RecyclerView) findViewById(R.id.eight_recycleView);

        initData();
    }


    private void initData() {
        dataList = Student.getSampleData(50);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(mRecycleView);

        adapter = new StudentAdapter(R.layout.item_eight_recycleview, dataList);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.isFirstOnly(false);

        mRecycleView.setAdapter(adapter);

    }


    ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
            int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
            if (fromPosition < toPosition) {
                //分别把中间所有的item的位置重新交换
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(dataList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(dataList, i, i - 1);
                }
            }
            adapter.notifyItemMoved(fromPosition, toPosition);
            //返回true表示执行拖动
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            dataList.remove(position);
            adapter.notifyItemRemoved(position);
        }
    };


    public class StudentAdapter extends BaseQuickAdapter<Student, BaseViewHolder> {

        public StudentAdapter(@LayoutRes int layoutResId, @Nullable List<Student> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Student student) {
            helper.setText(R.id.item_eight_tv_name, student.getName())
                    .setText(R.id.item_eight_tv_age, "" + student.getAge());
        }
    }
}
