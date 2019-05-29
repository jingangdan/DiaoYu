package com.project.dyuapp.entity;

import java.util.List;

/**
 * @author ${shipeiyun}
 * @created on 2017/12/8 0008
 * @description 金币商城
 * @change ${}
 */
public class GoldMallEntity {

    /**
     * money : 2006
     */
    private String money;
    /**
     * goods_id : 7
     * goods_name : 1000元现金红包
     * pic_url : /data/upload/2017-11-25/5a191e593b391.png
     * price : 500
     */

    private List<GoodsBean> goods;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        private String goods_id;
        private String goods_name;
        private String pic_url;
        private String price;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

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
    }
}
