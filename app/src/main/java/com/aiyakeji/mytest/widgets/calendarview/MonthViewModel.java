package com.aiyakeji.mytest.widgets.calendarview;

import java.io.Serializable;

/**
 * @author caiwenqing
 * @data 2018/5/17
 * description:
 */
public class MonthViewModel implements Serializable{

    public float centerX;

    public float centerY;

    public float width;

    public float height;

    public int year;

    public int month;

    public int day;

    public String price = "";

    //选中的状态，1第一个选中，2第二个选中
    public int selectState;

    public DayState state;

    public MonthViewModel(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public MonthViewModel(float centerX, float centerY, float width, float height,
                          int year, int month, int day, int selectState, DayState state) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
        this.year = year;
        this.month = month;
        this.day = day;
        this.selectState = selectState;
        this.state = state;
    }

    /**
     * 比较年月日
     *
     * @param model
     * @return 比另一个日期晚返回1，比另一个早返回-1,相同返回0，错误返回-2
     */
    public int compare(MonthViewModel model) {
        int resutl = -2;
        if (model == null) {
            return resutl;
        }
        if (year > model.year) {
            resutl = 1;
        } else if (year < model.year) {
            resutl = -1;

        } else if (month > model.month) {
            //同年 比较月
            resutl = 1;
        } else if (month < model.month) {
            resutl = -1;
        } else if (day > model.day) {
            //同年同月 比较日
            resutl = 1;
        } else if (day < model.day) {
            resutl = -1;
        } else {
            resutl = 0;
        }
        return resutl;
    }

    @Override
    public String toString() {
        return "MonthViewModel{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
