package com.project.dyuapp.shop;

/**
 * Describe:
 * Created by jingang on 2019/7/23
 */
public class GoodsEntity {
    /**
     * goods_id : 5704385240
     * goods_name : 【9.68元抢20000件，抢完恢复12.9元】春季新款韩版时尚百搭女鞋跑步运动鞋网红轻便休闲鞋防滑软底单鞋
     * goods_thumbnail_url : https://t00img.yangkeduo.com/goods/images/2019-03-06/9b1af1db88734346aa3c0be0e1cd0308.jpeg
     * has_coupon : true
     * coupon_discount : 300
     * coupon_remain_quantity : 13000
     * min_group_price : 968
     * promotion_rate : 200
     * avg_desc : 450
     * avg_lgst : 463
     * avg_serv : 463
     */

    private String goods_id;
    private String goods_name;
    private String goods_thumbnail_url;
    private boolean has_coupon;
    private String coupon_discount;
    private String coupon_remain_quantity;
    private String min_group_price;
    private String promotion_rate;
    private String avg_desc;
    private String avg_lgst;
    private String avg_serv;

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

    public String getGoods_thumbnail_url() {
        return goods_thumbnail_url;
    }

    public void setGoods_thumbnail_url(String goods_thumbnail_url) {
        this.goods_thumbnail_url = goods_thumbnail_url;
    }

    public boolean isHas_coupon() {
        return has_coupon;
    }

    public void setHas_coupon(boolean has_coupon) {
        this.has_coupon = has_coupon;
    }

    public String getCoupon_discount() {
        return coupon_discount;
    }

    public void setCoupon_discount(String coupon_discount) {
        this.coupon_discount = coupon_discount;
    }

    public String getCoupon_remain_quantity() {
        return coupon_remain_quantity;
    }

    public void setCoupon_remain_quantity(String coupon_remain_quantity) {
        this.coupon_remain_quantity = coupon_remain_quantity;
    }

    public String getMin_group_price() {
        return min_group_price;
    }

    public void setMin_group_price(String min_group_price) {
        this.min_group_price = min_group_price;
    }

    public String getPromotion_rate() {
        return promotion_rate;
    }

    public void setPromotion_rate(String promotion_rate) {
        this.promotion_rate = promotion_rate;
    }

    public String getAvg_desc() {
        return avg_desc;
    }

    public void setAvg_desc(String avg_desc) {
        this.avg_desc = avg_desc;
    }

    public String getAvg_lgst() {
        return avg_lgst;
    }

    public void setAvg_lgst(String avg_lgst) {
        this.avg_lgst = avg_lgst;
    }

    public String getAvg_serv() {
        return avg_serv;
    }

    public void setAvg_serv(String avg_serv) {
        this.avg_serv = avg_serv;
    }
}
