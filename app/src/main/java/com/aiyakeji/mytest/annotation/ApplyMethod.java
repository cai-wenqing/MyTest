package com.aiyakeji.mytest.annotation;


/**
 * Created by Administrator on 2018/3/9 0009.
 * 调用自定义注解
 */

public class ApplyMethod {

    @MethodInfo1(name = "ZCrain1", date = "2018/3/9", version = 1)
    public static String getInfo1() {
        return "cwq1";
    }


}
