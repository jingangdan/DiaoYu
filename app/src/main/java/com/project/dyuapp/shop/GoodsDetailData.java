package com.project.dyuapp.shop;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Describe:
 * Created by jingang on 2019/5/23
 */
public class GoodsDetailData {

    /**
     * data : []
     * code : 0
     * message : {"0":{"weibo_app_web_view_short_url":null,"mobile_url":"https://mobile.yangkeduo.com/app.html?launch_url=duo_coupon_landing.html%3Fgoods_id%3D2567271551%26pid%3D1763821_66044415%26cpsSign%3DCC_190523_1763821_66044415_ac10a071bea48d17b430022a79994fb8%26duoduo_type%3D3","we_app_info":null,"weibo_app_web_view_url":null,"mobile_short_url":"https://p.pinduoduo.com/jCsiOtVs","we_app_web_view_url":"https://mobile.yangkeduo.com/duo_coupon_landing.html?goods_id=2567271551&pid=1763821_66044415&cpsSign=CC_190523_1763821_66044415_ac10a071bea48d17b430022a79994fb8&duoduo_type=3&launch_wx=1","goods_detail":null,"url":"https://mobile.yangkeduo.com/duo_coupon_landing.html?goods_id=2567271551&pid=1763821_66044415&cpsSign=CC_190523_1763821_66044415_ac10a071bea48d17b430022a79994fb8&duoduo_type=3","short_url":"https://p.pinduoduo.com/nqoiTTnC","we_app_web_view_short_url":"https://p.pinduoduo.com/ywmi1qj6"},"goods_list":[]}
     */

    private int code;
    private MessageBean message;
    private List<?> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public static class MessageBean {
        /**
         * 0 : {"weibo_app_web_view_short_url":null,"mobile_url":"https://mobile.yangkeduo.com/app.html?launch_url=duo_coupon_landing.html%3Fgoods_id%3D2567271551%26pid%3D1763821_66044415%26cpsSign%3DCC_190523_1763821_66044415_ac10a071bea48d17b430022a79994fb8%26duoduo_type%3D3","we_app_info":null,"weibo_app_web_view_url":null,"mobile_short_url":"https://p.pinduoduo.com/jCsiOtVs","we_app_web_view_url":"https://mobile.yangkeduo.com/duo_coupon_landing.html?goods_id=2567271551&pid=1763821_66044415&cpsSign=CC_190523_1763821_66044415_ac10a071bea48d17b430022a79994fb8&duoduo_type=3&launch_wx=1","goods_detail":null,"url":"https://mobile.yangkeduo.com/duo_coupon_landing.html?goods_id=2567271551&pid=1763821_66044415&cpsSign=CC_190523_1763821_66044415_ac10a071bea48d17b430022a79994fb8&duoduo_type=3","short_url":"https://p.pinduoduo.com/nqoiTTnC","we_app_web_view_short_url":"https://p.pinduoduo.com/ywmi1qj6"}
         * goods_list : []
         */

        @SerializedName("0")
        private _$0Bean _$0;
        private List<?> goods_list;

        public _$0Bean get_$0() {
            return _$0;
        }

        public void set_$0(_$0Bean _$0) {
            this._$0 = _$0;
        }

        public List<?> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<?> goods_list) {
            this.goods_list = goods_list;
        }

        public static class _$0Bean {
            /**
             * weibo_app_web_view_short_url : null
             * mobile_url : https://mobile.yangkeduo.com/app.html?launch_url=duo_coupon_landing.html%3Fgoods_id%3D2567271551%26pid%3D1763821_66044415%26cpsSign%3DCC_190523_1763821_66044415_ac10a071bea48d17b430022a79994fb8%26duoduo_type%3D3
             * we_app_info : null
             * weibo_app_web_view_url : null
             * mobile_short_url : https://p.pinduoduo.com/jCsiOtVs
             * we_app_web_view_url : https://mobile.yangkeduo.com/duo_coupon_landing.html?goods_id=2567271551&pid=1763821_66044415&cpsSign=CC_190523_1763821_66044415_ac10a071bea48d17b430022a79994fb8&duoduo_type=3&launch_wx=1
             * goods_detail : null
             * url : https://mobile.yangkeduo.com/duo_coupon_landing.html?goods_id=2567271551&pid=1763821_66044415&cpsSign=CC_190523_1763821_66044415_ac10a071bea48d17b430022a79994fb8&duoduo_type=3
             * short_url : https://p.pinduoduo.com/nqoiTTnC
             * we_app_web_view_short_url : https://p.pinduoduo.com/ywmi1qj6
             */

            private String weibo_app_web_view_short_url;
            private String mobile_url;
            private Object we_app_info;
            private Object weibo_app_web_view_url;
            private String mobile_short_url;
            private String we_app_web_view_url;
            private Object goods_detail;
            private String url;
            private String short_url;
            private String we_app_web_view_short_url;

            public String getWeibo_app_web_view_short_url() {
                return weibo_app_web_view_short_url;
            }

            public void setWeibo_app_web_view_short_url(String weibo_app_web_view_short_url) {
                this.weibo_app_web_view_short_url = weibo_app_web_view_short_url;
            }

            public String getMobile_url() {
                return mobile_url;
            }

            public void setMobile_url(String mobile_url) {
                this.mobile_url = mobile_url;
            }

            public Object getWe_app_info() {
                return we_app_info;
            }

            public void setWe_app_info(Object we_app_info) {
                this.we_app_info = we_app_info;
            }

            public Object getWeibo_app_web_view_url() {
                return weibo_app_web_view_url;
            }

            public void setWeibo_app_web_view_url(Object weibo_app_web_view_url) {
                this.weibo_app_web_view_url = weibo_app_web_view_url;
            }

            public String getMobile_short_url() {
                return mobile_short_url;
            }

            public void setMobile_short_url(String mobile_short_url) {
                this.mobile_short_url = mobile_short_url;
            }

            public String getWe_app_web_view_url() {
                return we_app_web_view_url;
            }

            public void setWe_app_web_view_url(String we_app_web_view_url) {
                this.we_app_web_view_url = we_app_web_view_url;
            }

            public Object getGoods_detail() {
                return goods_detail;
            }

            public void setGoods_detail(Object goods_detail) {
                this.goods_detail = goods_detail;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getShort_url() {
                return short_url;
            }

            public void setShort_url(String short_url) {
                this.short_url = short_url;
            }

            public String getWe_app_web_view_short_url() {
                return we_app_web_view_short_url;
            }

            public void setWe_app_web_view_short_url(String we_app_web_view_short_url) {
                this.we_app_web_view_short_url = we_app_web_view_short_url;
            }
        }
    }
}
