package com.aiyakeji.mytest.system;

import com.aiyakeji.mytest.bean.HotelRoomBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {

    /**
     * 酒店房间列表
     *
     */
    @POST("/v1/hotel/hotel-room-list")
    Call<HotelRoomBean> getHotelRoomList(@Body RequestBody body);

}
