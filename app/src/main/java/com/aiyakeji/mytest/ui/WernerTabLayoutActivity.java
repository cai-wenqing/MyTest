package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aiyakeji.mytest.R;
import com.example.wernertablayoutlib.YKTabLayout;

import java.util.ArrayList;

/**
 * Author：Caiwenqing
 * Date：2018/10/12
 * Desc:横向ViewPager滑动指示器
 */
public class WernerTabLayoutActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private YKTabLayout ykTabLayout;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "热门", "iOS", "Android"
            , "前端", "后端", "设计", "工具资源"
    };
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wernertablayout);

        mViewPager = findViewById(R.id.viewPager);
        ykTabLayout = findViewById(R.id.tl);

        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        ykTabLayout.setViewPager(mViewPager);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
