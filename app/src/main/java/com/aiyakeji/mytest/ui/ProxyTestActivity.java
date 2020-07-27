package com.aiyakeji.mytest.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aiyakeji.mytest.R;
import com.aiyakeji.mytest.bean.House;
import com.aiyakeji.mytest.bean.IHouse;
import com.aiyakeji.mytest.bean.ProxyHandler;
import com.aiyakeji.mytest.bean.ProxyHouse;

import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2018/3/14 0014.
 * 测试代理模式
 */

public class ProxyTestActivity extends AppCompatActivity {
    private static final String TAG = "ProxyTestActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxytest);

        //静态代理
//        House house = new House();
//        ProxyHouse proxyHouse = new ProxyHouse(house);
//        proxyHouse.searchHouse();
//        proxyHouse.lookHouse();
//        proxyHouse.priceHouse();


        //动态代理
        House house = new House();
        ProxyHandler proxyHandler = new ProxyHandler(house);
        ClassLoader classLoader = house.getClass().getClassLoader();
        IHouse iHouse = (IHouse) Proxy.newProxyInstance(classLoader, new Class[]{IHouse.class}, proxyHandler);
        iHouse.searchHouse();
        iHouse.lookHouse();
        iHouse.priceHouse();
    }
}
