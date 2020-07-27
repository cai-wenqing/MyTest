package com.aiyakeji.mytest.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import androidx.fragment.app.FragmentActivity;

public class StartForResultUtil {
    private static final String TAG = "StartForresultUtil";

    private StartForResultFragment mFragment;

    public StartForResultUtil(Activity activity) {
        mFragment = getStartForResultFragment(activity);
    }

    public StartForResultUtil(Fragment fragment) {
        mFragment = getStartForResultFragment(fragment.getActivity());
    }

    private StartForResultFragment getStartForResultFragment(Activity activity) {
        StartForResultFragment fragment = (StartForResultFragment) activity.getFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new StartForResultFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction()
                    .add(fragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }


    public void startForResult(Intent intent, int requestCode, Callback callback){
        mFragment.startForResult(intent, requestCode, callback);
    }





    public interface Callback {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
