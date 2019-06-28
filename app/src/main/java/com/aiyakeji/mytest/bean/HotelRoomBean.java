package com.aiyakeji.mytest.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class HotelRoomBean{


    /**
     * flag : 0
     * msg : success
     * data : {"hotel_room_list":[{"id":"215977","product_id":"245642","room_type_id":"94","name":"高级大床房","en_name":"","channel":"2","size":"30","floor":"10-12","window":"2","bath":"","facilities":"","handy_facilities":"","media":"","food_drinks":"","other":"","intro":"","min_price":319,"bed_info":{"size":"1.8","number":"1","bed_type_id":"12","bed_type":"大床"},"img":{"title":"高级大床房","url":"http://dimg04.c-ctrip.com/images//200r050000000necz894F_R_400_400.jpg"},"img_list":[{"title":"高级大床房","url":"http://dimg04.c-ctrip.com/images//200r050000000necz894F_R_550_412.jpg"},{"title":"高级大床房","url":"http://dimg04.c-ctrip.com/images//2007050000000ned67075_R_550_412.jpg"}],"room_package":[{"room_package_id":872557,"room_id":215977,"name":"高级大床房","channel":2,"start_hour":"","end_hour":"","limit_adults":2,"limit_kids":2,"price":319,"is_min_price":1,"foods":"无餐","is_confirm":0,"has_stock":1,"cancel_rule":"不可取消"},{"room_package_id":872559,"room_id":215977,"name":"高级大床房","channel":2,"start_hour":"","end_hour":"","limit_adults":2,"limit_kids":2,"price":392,"is_min_price":0,"foods":"无餐","is_confirm":0,"has_stock":1,"cancel_rule":"不可取消"}]},{"id":"215980","product_id":"245642","room_type_id":"295","name":"双床房","en_name":"","channel":"2","size":"30","floor":"10-12","window":"2","bath":"","facilities":"","handy_facilities":"","media":"","food_drinks":"","other":"","intro":"","min_price":301,"bed_info":{"size":"1.2","number":"2","bed_type_id":"4","bed_type":"单人床"},"img":{"title":"双床房","url":"http://dimg04.c-ctrip.com/images//2008050000000ned5F051_R_400_400.jpg"},"img_list":[{"title":"双床房","url":"http://dimg04.c-ctrip.com/images//2008050000000ned5F051_R_550_412.jpg"},{"title":"双床房","url":"http://dimg04.c-ctrip.com/images//200a050000000ned3BF5C_R_550_412.jpg"}],"room_package":[{"room_package_id":872575,"room_id":215980,"name":"双床房","channel":2,"start_hour":"","end_hour":"","limit_adults":2,"limit_kids":2,"price":301,"is_min_price":1,"foods":"无餐","is_confirm":0,"has_stock":1,"cancel_rule":"不可取消"},{"room_package_id":872577,"room_id":215980,"name":"双床房","channel":2,"start_hour":"","end_hour":"","limit_adults":2,"limit_kids":2,"price":380,"is_min_price":0,"foods":"无餐","is_confirm":0,"has_stock":1,"cancel_rule":"不可取消"}]},{"id":"215982","product_id":"245642","room_type_id":"113","name":"高级双床房","en_name":"","channel":"2","size":"34","floor":"10-12","window":"2","bath":"","facilities":"","handy_facilities":"","media":"","food_drinks":"","other":"","intro":"","min_price":367,"bed_info":{"size":"1.2","number":"2","bed_type_id":"4","bed_type":"单人床"},"img":{"title":"高级双床房","url":"http://dimg04.c-ctrip.com/images//200k050000000ned79B0E_R_400_400.jpg"},"img_list":[{"title":"高级双床房","url":"http://dimg04.c-ctrip.com/images//200k050000000ned79B0E_R_550_412.jpg"}],"room_package":[{"room_package_id":872584,"room_id":215982,"name":"高级双床房","channel":2,"start_hour":"","end_hour":"","limit_adults":2,"limit_kids":2,"price":367,"is_min_price":1,"foods":"无餐","is_confirm":0,"has_stock":1,"cancel_rule":"不可取消"},{"room_package_id":872586,"room_id":215982,"name":"高级双床房","channel":2,"start_hour":"","end_hour":"","limit_adults":2,"limit_kids":2,"price":451,"is_min_price":0,"foods":"无餐","is_confirm":0,"has_stock":1,"cancel_rule":"不可取消"}]},{"id":"215988","product_id":"245642","room_type_id":"207","name":"零压高级大床房","en_name":"","channel":"2","size":"30","floor":"10-12","window":"2","bath":"","facilities":"","handy_facilities":"","media":"","food_drinks":"","other":"","intro":"","min_price":338,"bed_info":{"size":"1.8","number":"1","bed_type_id":"12","bed_type":"大床"},"img":{"title":"零压高级大床房","url":"http://dimg04.c-ctrip.com/images//200k14000000x7yhwE922_R_400_400.jpg"},"img_list":[{"title":"零压高级大床房","url":"http://dimg04.c-ctrip.com/images//200k14000000x7yhwE922_R_550_412.jpg"}],"room_package":[{"room_package_id":872609,"room_id":215988,"name":"零压高级大床房","channel":2,"start_hour":"","end_hour":"","limit_adults":2,"limit_kids":2,"price":338,"is_min_price":1,"foods":"无餐","is_confirm":0,"has_stock":1,"cancel_rule":"不可取消"}]}]}
     */

    private int flag;
    private String msg;
    private DataBean data;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public static class DataBean implements MultiItemEntity{
        private List<HotelRoomListBean> hotel_room_list;

        public List<HotelRoomListBean> getHotel_room_list() {
            return hotel_room_list;
        }

        public void setHotel_room_list(List<HotelRoomListBean> hotel_room_list) {
            this.hotel_room_list = hotel_room_list;
        }

        @Override
        public int getItemType() {
            return 0;
        }
    }
}
