package com.aiyakeji.mytest.system;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpHelper {

    private static HttpHelper mInstance;
    private static API mApi;

    private HttpHelper() {
        String baseUrl = "https://api.yktour.com.cn/";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(InterceptorUtil.logInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())//这一步是把请求到的json字符串直接转换成Bean类
                .client(okHttpClient)
                .build();
        mApi = retrofit.create(API.class);
    }


    public static HttpHelper getInstance() {
        if (mInstance == null) {
            mInstance = new HttpHelper();
        }
        return mInstance;
    }


    public API getApi() {
        return mApi;
    }
}
