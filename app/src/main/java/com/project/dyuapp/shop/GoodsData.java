package com.project.dyuapp.shop;

import java.util.List;

/**
 * Describe:
 * Created by jingang on 2019/5/21
 */
public class GoodsData {

    /**
     * data : {"goods_list":[{"goods_id":"5704385240","goods_name":"【9.68元抢20000件，抢完恢复12.9元】春季新款韩版时尚百搭女鞋跑步运动鞋网红轻便休闲鞋防滑软底单鞋","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-03-06/9b1af1db88734346aa3c0be0e1cd0308.jpeg","has_coupon":true,"coupon_discount":"300","coupon_remain_quantity":"13000","min_group_price":"968","promotion_rate":"200","avg_desc":"450","avg_lgst":"463","avg_serv":"463"},{"goods_id":"6473348623","goods_name":"龙江人家山核桃500g  老树核桃  薄皮野生原味核桃原味无漂白","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-03-12/5241650c0fb94dfd9abae44d4670c688.jpeg","has_coupon":true,"coupon_discount":"1000","coupon_remain_quantity":"6000","min_group_price":"1990","promotion_rate":"500","avg_desc":"472","avg_lgst":"480","avg_serv":"478"},{"goods_id":"8644389489","goods_name":"【14.9元抢100000件，抢完恢复24.9元】网面小白鞋女2019春夏新款透气网鞋百搭网红厚底休闲运动鞋","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-05-18/20fc9dd467659e55236f30a9cd7cf5ce.jpeg","has_coupon":true,"coupon_discount":"700","coupon_remain_quantity":"19000","min_group_price":"1490","promotion_rate":"200","avg_desc":"450","avg_lgst":"463","avg_serv":"463"},{"goods_id":"8569489138","goods_name":"【11.9元抢100000件，抢完恢复24.9元】星星运动鞋潮韩版百搭休闲鞋2019新款春季款女鞋子跑步鞋","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-05-08/665211e383fee359fe0ebfd24cd9c83d.jpeg","has_coupon":true,"coupon_discount":"500","coupon_remain_quantity":"19000","min_group_price":"1190","promotion_rate":"200","avg_desc":"450","avg_lgst":"463","avg_serv":"463"},{"goods_id":"327928429","goods_name":"【4.9元抢20000件，抢完恢复7.8元】黑色垃圾袋家用加厚中大号手提背心式拉圾袋批发一次性塑料袋厨房","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-03-01/d20b9569e0da4c349458a43b37a309ee.jpeg","has_coupon":true,"coupon_discount":"300","coupon_remain_quantity":"5000","min_group_price":"490","promotion_rate":"200","avg_desc":"470","avg_lgst":"476","avg_serv":"475"},{"goods_id":"1309428697","goods_name":"【7.9元限时抢，抢完恢复10.9元】【买一次用半年】6层加厚卫生纸卷纸家用厕纸无芯卷筒纸巾批发纸","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-04-24/964bbd5ce21cb4edadc35424c610322e.jpeg","has_coupon":true,"coupon_discount":"100","coupon_remain_quantity":"33000","min_group_price":"790","promotion_rate":"200","avg_desc":"463","avg_lgst":"472","avg_serv":"472"},{"goods_id":"8940114617","goods_name":"熊客原味早餐速食正宗手抓饼50片家庭装煎饼面饼皮手抓饼皮批发","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-05-19/100254373b143ef7cadfa1f069f8389c.jpeg","has_coupon":true,"coupon_discount":"500","coupon_remain_quantity":"9000","min_group_price":"1680","promotion_rate":"260","avg_desc":"471","avg_lgst":"463","avg_serv":"467"},{"goods_id":"6869428690","goods_name":"【买1送1】漏水拖鞋女夏居家浴室防滑按摩男士家用室内家居凉拖鞋","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-05-06/6f1e9354bab39379207df0d9477cceec.jpeg","has_coupon":true,"coupon_discount":"200","coupon_remain_quantity":"7000","min_group_price":"990","promotion_rate":"200","avg_desc":"459","avg_lgst":"470","avg_serv":"469"},{"goods_id":"8732591976","goods_name":"抹布不沾油洗碗布家用厨房清洁巾吸水擦桌小毛巾加厚不掉毛擦手巾","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-05-13/3c96d5351b795260185813efed925e1f.jpeg","has_coupon":true,"coupon_discount":"200","coupon_remain_quantity":"600","min_group_price":"490","promotion_rate":"200","avg_desc":"475","avg_lgst":"476","avg_serv":"478"},{"goods_id":"5582768793","goods_name":"【32包整箱】8包300张/包纸巾抽纸家用面巾纸抽纸批发整箱","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-05-18/79167245912e3005973ca9dddd4d6cdb.jpeg","has_coupon":true,"coupon_discount":"200","coupon_remain_quantity":"19000","min_group_price":"790","promotion_rate":"200","avg_desc":"463","avg_lgst":"470","avg_serv":"470"}],"total_count":535763,"page":1,"pagesize":10}
     * code : 0
     * message : 商品列表
     */

    private DataBean data;
    private int code;
    private String message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * goods_list : [{"goods_id":"5704385240","goods_name":"【9.68元抢20000件，抢完恢复12.9元】春季新款韩版时尚百搭女鞋跑步运动鞋网红轻便休闲鞋防滑软底单鞋","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-03-06/9b1af1db88734346aa3c0be0e1cd0308.jpeg","has_coupon":true,"coupon_discount":"300","coupon_remain_quantity":"13000","min_group_price":"968","promotion_rate":"200","avg_desc":"450","avg_lgst":"463","avg_serv":"463"},{"goods_id":"6473348623","goods_name":"龙江人家山核桃500g  老树核桃  薄皮野生原味核桃原味无漂白","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-03-12/5241650c0fb94dfd9abae44d4670c688.jpeg","has_coupon":true,"coupon_discount":"1000","coupon_remain_quantity":"6000","min_group_price":"1990","promotion_rate":"500","avg_desc":"472","avg_lgst":"480","avg_serv":"478"},{"goods_id":"8644389489","goods_name":"【14.9元抢100000件，抢完恢复24.9元】网面小白鞋女2019春夏新款透气网鞋百搭网红厚底休闲运动鞋","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-05-18/20fc9dd467659e55236f30a9cd7cf5ce.jpeg","has_coupon":true,"coupon_discount":"700","coupon_remain_quantity":"19000","min_group_price":"1490","promotion_rate":"200","avg_desc":"450","avg_lgst":"463","avg_serv":"463"},{"goods_id":"8569489138","goods_name":"【11.9元抢100000件，抢完恢复24.9元】星星运动鞋潮韩版百搭休闲鞋2019新款春季款女鞋子跑步鞋","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-05-08/665211e383fee359fe0ebfd24cd9c83d.jpeg","has_coupon":true,"coupon_discount":"500","coupon_remain_quantity":"19000","min_group_price":"1190","promotion_rate":"200","avg_desc":"450","avg_lgst":"463","avg_serv":"463"},{"goods_id":"327928429","goods_name":"【4.9元抢20000件，抢完恢复7.8元】黑色垃圾袋家用加厚中大号手提背心式拉圾袋批发一次性塑料袋厨房","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-03-01/d20b9569e0da4c349458a43b37a309ee.jpeg","has_coupon":true,"coupon_discount":"300","coupon_remain_quantity":"5000","min_group_price":"490","promotion_rate":"200","avg_desc":"470","avg_lgst":"476","avg_serv":"475"},{"goods_id":"1309428697","goods_name":"【7.9元限时抢，抢完恢复10.9元】【买一次用半年】6层加厚卫生纸卷纸家用厕纸无芯卷筒纸巾批发纸","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-04-24/964bbd5ce21cb4edadc35424c610322e.jpeg","has_coupon":true,"coupon_discount":"100","coupon_remain_quantity":"33000","min_group_price":"790","promotion_rate":"200","avg_desc":"463","avg_lgst":"472","avg_serv":"472"},{"goods_id":"8940114617","goods_name":"熊客原味早餐速食正宗手抓饼50片家庭装煎饼面饼皮手抓饼皮批发","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-05-19/100254373b143ef7cadfa1f069f8389c.jpeg","has_coupon":true,"coupon_discount":"500","coupon_remain_quantity":"9000","min_group_price":"1680","promotion_rate":"260","avg_desc":"471","avg_lgst":"463","avg_serv":"467"},{"goods_id":"6869428690","goods_name":"【买1送1】漏水拖鞋女夏居家浴室防滑按摩男士家用室内家居凉拖鞋","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-05-06/6f1e9354bab39379207df0d9477cceec.jpeg","has_coupon":true,"coupon_discount":"200","coupon_remain_quantity":"7000","min_group_price":"990","promotion_rate":"200","avg_desc":"459","avg_lgst":"470","avg_serv":"469"},{"goods_id":"8732591976","goods_name":"抹布不沾油洗碗布家用厨房清洁巾吸水擦桌小毛巾加厚不掉毛擦手巾","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-05-13/3c96d5351b795260185813efed925e1f.jpeg","has_coupon":true,"coupon_discount":"200","coupon_remain_quantity":"600","min_group_price":"490","promotion_rate":"200","avg_desc":"475","avg_lgst":"476","avg_serv":"478"},{"goods_id":"5582768793","goods_name":"【32包整箱】8包300张/包纸巾抽纸家用面巾纸抽纸批发整箱","goods_thumbnail_url":"https://t00img.yangkeduo.com/goods/images/2019-05-18/79167245912e3005973ca9dddd4d6cdb.jpeg","has_coupon":true,"coupon_discount":"200","coupon_remain_quantity":"19000","min_group_price":"790","promotion_rate":"200","avg_desc":"463","avg_lgst":"470","avg_serv":"470"}]
         * total_count : 535763
         * page : 1
         * pagesize : 10
         */

        private int total_count;
        private int page;
        private int pagesize;
        private List<GoodsListBean> goods_list;

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPagesize() {
            return pagesize;
        }

        public void setPagesize(int pagesize) {
            this.pagesize = pagesize;
        }

        public List<GoodsListBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<GoodsListBean> goods_list) {
            this.goods_list = goods_list;
        }

        public static class GoodsListBean {
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
    }
}
