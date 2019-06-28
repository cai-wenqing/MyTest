package com.aiyakeji.mytest.bean;

import java.util.List;

public class HotelRoomListBean {
    /**
     * id : 215977
     * product_id : 245642
     * room_type_id : 94
     * name : 高级大床房
     * en_name :
     * channel : 2
     * size : 30
     * floor : 10-12
     * window : 2
     * bath :
     * facilities :
     * handy_facilities :
     * media :
     * food_drinks :
     * other :
     * intro :
     * min_price : 319
     * bed_info : {"size":"1.8","number":"1","bed_type_id":"12","bed_type":"大床"}
     * img : {"title":"高级大床房","url":"http://dimg04.c-ctrip.com/images//200r050000000necz894F_R_400_400.jpg"}
     * img_list : [{"title":"高级大床房","url":"http://dimg04.c-ctrip.com/images//200r050000000necz894F_R_550_412.jpg"},{"title":"高级大床房","url":"http://dimg04.c-ctrip.com/images//2007050000000ned67075_R_550_412.jpg"}]
     * room_package : [{"room_package_id":872557,"room_id":215977,"name":"高级大床房","channel":2,"start_hour":"","end_hour":"","limit_adults":2,"limit_kids":2,"price":319,"is_min_price":1,"foods":"无餐","is_confirm":0,"has_stock":1,"cancel_rule":"不可取消"},{"room_package_id":872559,"room_id":215977,"name":"高级大床房","channel":2,"start_hour":"","end_hour":"","limit_adults":2,"limit_kids":2,"price":392,"is_min_price":0,"foods":"无餐","is_confirm":0,"has_stock":1,"cancel_rule":"不可取消"}]
     */

    private String id;
    private String product_id;
    private String room_type_id;
    private String name;
    private String en_name;
    private String channel;
    private String size;
    private String floor;
    private String window;
    private String bath;
    private String facilities;
    private String handy_facilities;
    private String media;
    private String food_drinks;
    private String other;
    private String intro;
    private int min_price;
//    private HotelRoomListBean.BedInfoBean bed_info;
//    private HotelRoomListBean.ImgBean img;
//    private List<HotelRoomListBean.ImgListBean> img_list;
    private List<HotelRoomListBean.RoomPackageBean> room_package;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(String room_type_id) {
        this.room_type_id = room_type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    public String getBath() {
        return bath;
    }

    public void setBath(String bath) {
        this.bath = bath;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getHandy_facilities() {
        return handy_facilities;
    }

    public void setHandy_facilities(String handy_facilities) {
        this.handy_facilities = handy_facilities;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getFood_drinks() {
        return food_drinks;
    }

    public void setFood_drinks(String food_drinks) {
        this.food_drinks = food_drinks;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getMin_price() {
        return min_price;
    }

    public void setMin_price(int min_price) {
        this.min_price = min_price;
    }

//    public HotelRoomListBean.BedInfoBean getBed_info() {
//        return bed_info;
//    }
//
//    public void setBed_info(HotelRoomListBean.BedInfoBean bed_info) {
//        this.bed_info = bed_info;
//    }
//
//    public HotelRoomListBean.ImgBean getImg() {
//        return img;
//    }
//
//    public void setImg(HotelRoomListBean.ImgBean img) {
//        this.img = img;
//    }
//
//    public List<HotelRoomListBean.ImgListBean> getImg_list() {
//        return img_list;
//    }
//
//    public void setImg_list(List<HotelRoomListBean.ImgListBean> img_list) {
//        this.img_list = img_list;
//    }

    public List<HotelRoomListBean.RoomPackageBean> getRoom_package() {
        return room_package;
    }

    public void setRoom_package(List<HotelRoomListBean.RoomPackageBean> room_package) {
        this.room_package = room_package;
    }


    public static class BedInfoBean {
        /**
         * size : 1.8
         * number : 1
         * bed_type_id : 12
         * bed_type : 大床
         */

        private String size;
        private String number;
        private String bed_type_id;
        private String bed_type;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getBed_type_id() {
            return bed_type_id;
        }

        public void setBed_type_id(String bed_type_id) {
            this.bed_type_id = bed_type_id;
        }

        public String getBed_type() {
            return bed_type;
        }

        public void setBed_type(String bed_type) {
            this.bed_type = bed_type;
        }
    }

    public static class ImgBean {
        /**
         * title : 高级大床房
         * url : http://dimg04.c-ctrip.com/images//200r050000000necz894F_R_400_400.jpg
         */

        private String title;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class ImgListBean {
        /**
         * title : 高级大床房
         * url : http://dimg04.c-ctrip.com/images//200r050000000necz894F_R_550_412.jpg
         */

        private String title;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class RoomPackageBean{
        /**
         * room_package_id : 872557
         * room_id : 215977
         * name : 高级大床房
         * channel : 2
         * start_hour :
         * end_hour :
         * limit_adults : 2
         * limit_kids : 2
         * price : 319
         * is_min_price : 1
         * foods : 无餐
         * is_confirm : 0
         * has_stock : 1
         * cancel_rule : 不可取消
         */

        private int room_package_id;
        private int room_id;
        private String name;
        private int channel;
        private String start_hour;
        private String end_hour;
        private int limit_adults;
        private int limit_kids;
        private int price;
        private int is_min_price;
        private String foods;
        private int is_confirm;
        private int has_stock;
        private String cancel_rule;

        public int getRoom_package_id() {
            return room_package_id;
        }

        public void setRoom_package_id(int room_package_id) {
            this.room_package_id = room_package_id;
        }

        public int getRoom_id() {
            return room_id;
        }

        public void setRoom_id(int room_id) {
            this.room_id = room_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public String getStart_hour() {
            return start_hour;
        }

        public void setStart_hour(String start_hour) {
            this.start_hour = start_hour;
        }

        public String getEnd_hour() {
            return end_hour;
        }

        public void setEnd_hour(String end_hour) {
            this.end_hour = end_hour;
        }

        public int getLimit_adults() {
            return limit_adults;
        }

        public void setLimit_adults(int limit_adults) {
            this.limit_adults = limit_adults;
        }

        public int getLimit_kids() {
            return limit_kids;
        }

        public void setLimit_kids(int limit_kids) {
            this.limit_kids = limit_kids;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getIs_min_price() {
            return is_min_price;
        }

        public void setIs_min_price(int is_min_price) {
            this.is_min_price = is_min_price;
        }

        public String getFoods() {
            return foods;
        }

        public void setFoods(String foods) {
            this.foods = foods;
        }

        public int getIs_confirm() {
            return is_confirm;
        }

        public void setIs_confirm(int is_confirm) {
            this.is_confirm = is_confirm;
        }

        public int getHas_stock() {
            return has_stock;
        }

        public void setHas_stock(int has_stock) {
            this.has_stock = has_stock;
        }

        public String getCancel_rule() {
            return cancel_rule;
        }

        public void setCancel_rule(String cancel_rule) {
            this.cancel_rule = cancel_rule;
        }

    }
}
