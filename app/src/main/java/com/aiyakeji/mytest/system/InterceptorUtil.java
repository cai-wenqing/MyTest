package com.aiyakeji.mytest.system;

import okhttp3.logging.HttpLoggingInterceptor;

public class InterceptorUtil {

    public static HttpLoggingInterceptor logInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
