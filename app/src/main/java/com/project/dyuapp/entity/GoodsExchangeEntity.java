package com.project.dyuapp.entity;

/**
 * @author ${shipeiyun}
 * @created on 2017/12/8 0008
 * @description 兑换商品
 * @change ${}
 */
public class GoodsExchangeEntity {

    /**
     * goods_id : 7
     * goods_name : 1000元现金红包
     * pic_url : /data/upload/2017-11-25/5a191e593b391.png
     * content : 100元支付宝现金红包，兑换时请留言支付宝帐号，或者在个人信息处点击头像，绑定支付宝。
     * <p>
     * price : 500
     */
    private String goods_id;
    private String goods_name;
    private String pic_url;
    private String content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
