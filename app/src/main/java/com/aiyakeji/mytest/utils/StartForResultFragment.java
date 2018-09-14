package com.aiyakeji.mytest.utils;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class StartForResultFragment extends Fragment {

    private Map<Integer,StartForResultUtil.Callback> mCallbacks = new HashMap<>();

    public StartForResultFragment(){
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public void startForResult(Intent intent, int requestCode, StartForResultUtil.Callback callback){
        mCallbacks.put(requestCode,callback);
        startActivityForResult(intent,requestCode);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        StartForResultUtil.Callback callback = mCallbacks.remove(requestCode);
        if (callback != null){
            callback.onActivityResult(requestCode, resultCode, data);
        }
    }
}
