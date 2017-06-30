package com.aiyakeji.mytest.widgets;

/**
 * 手势锁每个点model
 */

public class GestureLockPoint {

    public float centerX;

    public float centerY;

    public int flag;

    public boolean isSelected = false;

    public GestureLockPoint(float centerX, float centerY, int flag) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.flag = flag;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
