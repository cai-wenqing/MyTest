package com.aiyakeji.mytest.system;

import android.app.Application;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class App extends Application {

    private static App mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static App getInstance() {
        return mContext;
    }

}
