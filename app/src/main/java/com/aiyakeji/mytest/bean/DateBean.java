package com.aiyakeji.mytest.bean;

/**
 * @author caiwenqing
 * @data 2018/5/17
 * description:
 */
public class DateBean {

    private int year;

    private int month;

    private int day;

    public DateBean(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }


    public void setEmpey() {
        year = month = day = 0;
    }

    public boolean isEmpty() {
        return (year == 0 || month == 0 || day == 0);
    }
}
