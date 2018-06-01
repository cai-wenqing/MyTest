package com.aiyakeji.mytest.widgets.calendarview;

/**
 * @author caiwenqing
 * @data 2018/5/15
 * description:
 */
public class DayModel {

    private float centerX;

    private float centerY;

    private float width;

    private float height;

    private int day;

    private float price;

    private String underLabel;

    private DayState dayState;

    public DayModel(float centerX, float centerY, float width,float height,int day,float price, DayState dayState) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
        this.day = day;
        this.price = price;
        this.dayState = dayState;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public DayState getDayState() {
        return dayState;
    }

    public void setDayState(DayState dayState) {
        this.dayState = dayState;
    }
}
