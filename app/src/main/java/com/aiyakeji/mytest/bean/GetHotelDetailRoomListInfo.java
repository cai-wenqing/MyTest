package com.aiyakeji.mytest.bean;

public class GetHotelDetailRoomListInfo {
    /**
     * data : {"hotel_code":1,"check_in_date":"2019-06-15","check_out_date":"2019-06-16"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * hotel_code : 1
         * check_in_date : 2019-06-15
         * check_out_date : 2019-06-16
         */

        private int hotel_code;
        private String check_in_date;
        private String check_out_date;

        public int getHotel_code() {
            return hotel_code;
        }

        public void setHotel_code(int hotel_code) {
            this.hotel_code = hotel_code;
        }

        public String getCheck_in_date() {
            return check_in_date;
        }

        public void setCheck_in_date(String check_in_date) {
            this.check_in_date = check_in_date;
        }

        public String getCheck_out_date() {
            return check_out_date;
        }

        public void setCheck_out_date(String check_out_date) {
            this.check_out_date = check_out_date;
        }
    }
}
