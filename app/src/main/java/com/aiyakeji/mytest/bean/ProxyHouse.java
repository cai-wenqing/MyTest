package com.aiyakeji.mytest.bean;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class ProxyHouse implements IHouse {
    private House mHouse;

    public ProxyHouse(House house) {
        mHouse = house;
    }

    @Override
    public void searchHouse() {
        mHouse.searchHouse();
    }

    @Override
    public void lookHouse() {
        mHouse.lookHouse();
    }

    @Override
    public void priceHouse() {
        mHouse.priceHouse();
    }
}
