package com.aiyakeji.mytest.presenter;

import com.aiyakeji.mytest.ui.ILoginView;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class LoginPresenterImpl implements ILoginPresenter {
    private ILoginView iLoginView;

    public LoginPresenterImpl(ILoginView view) {
        iLoginView = view;
    }

    @Override
    public void clearInputMsg() {
        iLoginView.clearMsg();
    }

    @Override
    public void doLogin(String account, String password) {
        if ("caiwenqing".equals(account) && "woaini".equals(password))
            iLoginView.onLoginResult(true);
        else
            iLoginView.onLoginResult(false);
    }
}
