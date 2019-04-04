package com.aiyakeji.mytest.utils;

/**
 * Author：CWQ
 * Date：2019/4/4
 * Desc:
 */
public class Permission {

    public Permission(String name, boolean granted) {
        this.name = name;
        this.granted = granted;
    }

    public Permission(String name, boolean granted, boolean shouldShowRequestPermissionRationale) {
        this.name = name;
        this.granted = granted;
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale;
    }

    private String name;
    private boolean granted;
    //是否可以再次显示权限请求框。false：选择了不再询问
    private boolean shouldShowRequestPermissionRationale;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }

    public boolean isShouldShowRequestPermissionRationale() {
        return shouldShowRequestPermissionRationale;
    }

    public void setShouldShowRequestPermissionRationale(boolean shouldShowRequestPermissionRationale) {
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale;
    }
}
