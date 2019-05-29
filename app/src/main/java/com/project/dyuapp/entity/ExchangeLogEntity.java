package com.project.dyuapp.entity;

import java.util.List;

/**
 * @author ${shipeiyun}
 * @created on 2017/12/8 0008
 * @description 兑换记录
 * @change ${}
 */
public class ExchangeLogEntity {

    /**
     * total_money : 40
     */

    private String total_money;
    /**
     * goods_name : 50元现金红包
     * pic_url : /data/upload/2017-11-20/5a123f2482970.jpg
     * price : 5000
     * addtime : 1508980212
     * goods_num : 3
     * order_status : 5
     */

    private List<GoodsBean> goods;

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        private String goods_name;
        private String pic_url;
        private String price;
        private String addtime;
        private String goods_num;
        private String order_status;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }
    }
}
