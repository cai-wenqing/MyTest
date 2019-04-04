package com.aiyakeji.mytest.listener;

import com.aiyakeji.mytest.utils.Permission;

/**
 * Author：CWQ
 * Date：2019/4/4
 * Desc:权限申请回调
 */
public interface IPermissionListenerWrap {

    interface IPermissionListener{

        void onAccepted(boolean b);
    }

    interface IEachPermissionListener{

        void onAccepted(Permission permission);
    }
}
