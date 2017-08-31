package com.aiyakeji.mytest.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.presenter.LoginPresenterImpl;

import static com.aiyakeji.mytest.R.id.mvplogin_et_account;

/**
 * 初试MVP
 */

public class MVPLoginActivity extends Activity implements ILoginView, View.OnClickListener {
    private EditText et_account;
    private EditText et_password;
    private Button btn_login;
    private Button btn_cancel;

    private LoginPresenterImpl mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvplogin);

        //findViews
        et_account = (EditText) findViewById(mvplogin_et_account);
        et_password = (EditText) findViewById(R.id.mvplogin_et_password);
        btn_login = (Button) findViewById(R.id.mvplogin_btn_login);
        btn_cancel = (Button) findViewById(R.id.mvplogin_btn_cancel);

        //setListener
        btn_login.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        mPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mvplogin_btn_login:
                mPresenter.doLogin(et_account.getText().toString(), et_password.getText().toString());
                break;
            case R.id.mvplogin_btn_cancel:
                mPresenter.clearInputMsg();
                break;
        }
    }


    @Override
    public void clearMsg() {
        et_account.setText("");
        et_password.setText("");
    }


    @Override
    public void onLoginResult(boolean isSuccess) {
        if (isSuccess)
            Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
    }
}
