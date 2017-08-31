package com.aiyakeji.mytest.presenter;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public interface ILoginPresenter {
    void clearInputMsg();

    void doLogin(String account, String password);
}
