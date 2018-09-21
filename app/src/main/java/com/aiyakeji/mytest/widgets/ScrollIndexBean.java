package com.aiyakeji.mytest.widgets;

/**
 * Author：Caiwenqing
 * Date：2018/9/21
 * Desc:
 */
public class ScrollIndexBean {
    private String value;
    private float width;
    private float height;

    public ScrollIndexBean() {
    }

    public ScrollIndexBean(String value, float width, float height) {
        this.value = value;
        this.width = width;
        this.height = height;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
