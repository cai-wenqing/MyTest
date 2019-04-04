package com.aiyakeji.mytest.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;

import com.aiyakeji.mytest.listener.IPermissionListenerWrap;

/**
 * Author：CWQ
 * Date：2019/4/4
 * Desc:权限申请帮助类
 */
public class PermissionHelper {

    private static final String TAG_PERMISSION = "TAG_PERMISSION";

    private final FragmentActivity mActivity;

    private PermissionHelper(FragmentActivity activity) {
        mActivity = activity;
    }

    public static PermissionHelper init(FragmentActivity activity) {
        return new PermissionHelper(activity);
    }

    /**
     * 申请权限
     * @param permissions
     * @param listener
     */
    public void requestPermissions(String[] permissions, IPermissionListenerWrap.IPermissionListener listener) {
        getPermissionFragment(mActivity).requestPermissions(permissions, listener);
    }

    public void requestEachPermissions(String[] permissions, IPermissionListenerWrap.IEachPermissionListener listener) {
        getPermissionFragment(mActivity).requestEachPermission(permissions, listener);
    }


    private PermissionFragment getPermissionFragment(FragmentActivity activity) {
        PermissionFragment fragment = (PermissionFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG_PERMISSION);
        if (fragment == null) {
            fragment = PermissionFragment.newInstance();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(fragment, TAG_PERMISSION)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }


    public static boolean checkPermission(Context context, @NonNull String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        }
    }


    /**
     * 打开设置页面打开权限
     *
     * @param context
     */
    public static void startSettingActivity(@NonNull Activity context) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + context.getPackageName()));
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            context.startActivityForResult(intent, 10); //这里的requestCode和onActivityResult中requestCode要一致
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
