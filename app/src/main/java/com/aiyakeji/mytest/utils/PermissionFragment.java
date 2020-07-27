package com.aiyakeji.mytest.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.util.SparseArray;

import com.aiyakeji.mytest.listener.IPermissionListenerWrap;

import java.util.Random;

/**
 * Author：CWQ
 * Date：2019/4/4
 * Desc:权限申请中转
 */
public class PermissionFragment extends Fragment {

    private static final String TAG = "PermissionFragment";

    private SparseArray<IPermissionListenerWrap.IPermissionListener> mCallbacks = new SparseArray<>();
    private SparseArray<IPermissionListenerWrap.IEachPermissionListener> mEachCallbacks = new SparseArray<>();
    private Random mCodeGenerator = new Random();
    private Activity mActivity;

    public PermissionFragment() {

    }

    public static PermissionFragment newInstance() {
        return new PermissionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置为 true，表示 configuration change 的时候，fragment 实例不会背重新创建
        setRetainInstance(true);
        mActivity = getActivity();
    }


    public void requestPermissions(@NonNull String[] permissions, IPermissionListenerWrap.IPermissionListener callback) {
        int requestCode = makeRequestCode();
        mCallbacks.put(requestCode, callback);
        requestPermissions(permissions, requestCode);
    }


    public void requestEachPermission(@NonNull String[] permissions, IPermissionListenerWrap.IEachPermissionListener callback) {
        int requestCode = makeEachRequestCode();
        mEachCallbacks.put(requestCode, callback);
        requestPermissions(permissions, requestCode);
    }


    /**
     * 随机生成唯一的requestCode，最多尝试10次
     *
     * @return 随机数
     */
    private int makeRequestCode() {
        int requestCode;
        int tryCount = 0;
        do {
            requestCode = mCodeGenerator.nextInt(0x0000FFFF);
            tryCount++;
        } while (mCallbacks.indexOfKey(requestCode) >= 0 && tryCount < 10);
        return requestCode;
    }


    private int makeEachRequestCode() {
        int requestCode;
        int tryCount = 0;
        do {
            requestCode = mCodeGenerator.nextInt(0x0000FFFF);
            tryCount++;
        } while (mEachCallbacks.indexOfKey(requestCode) >= 0 && tryCount < 10);
        return requestCode;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        handlePermissionCallBack(requestCode, grantResults);
        handleEachPermissionCallBack(requestCode, permissions, grantResults);
    }


    private void handlePermissionCallBack(int requestCode, int[] grantResults) {
        IPermissionListenerWrap.IPermissionListener callBack = mCallbacks.get(requestCode);
        if (callBack == null) {
            return;
        }
        mCallbacks.remove(requestCode);
        boolean allGranted = false;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
            allGranted = true;
        }
        callBack.onAccepted(allGranted);
    }


    private void handleEachPermissionCallBack(int requestCode, String[] permissions, int[] grantResults) {
        IPermissionListenerWrap.IEachPermissionListener eachCallBack = mEachCallbacks.get(requestCode);
        if (eachCallBack == null) {
            return;
        }

        mEachCallbacks.remove(requestCode);

        for (int i = 0; i < grantResults.length; i++) {
            int grantResult = grantResults[i];
            Permission mPermission;
            String name = permissions[i];
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                mPermission = new Permission(name, true);
                eachCallBack.onAccepted(mPermission);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, name)) {
                    mPermission = new Permission(name, false, true);
                } else {
                    mPermission = new Permission(name, false, false);
                }
                eachCallBack.onAccepted(mPermission);
            }
        }
    }

}
