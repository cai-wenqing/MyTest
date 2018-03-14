package com.aiyakeji.mytest.bean;

import android.util.Log;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class House implements IHouse {
    private static final String TAG = "House";

    @Override
    public void searchHouse() {
        Log.i(TAG, "searchHouse...");
    }

    @Override
    public void lookHouse() {
        Log.i(TAG, "lookHouse...");
    }

    @Override
    public void priceHouse() {
        Log.i(TAG, "priceHouse...");
    }
}
