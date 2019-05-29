package com.project.dyuapp.shop;

import java.util.List;

/**
 * Describe:
 * Created by jingang on 2019/5/20
 */
public class CateData {

    /**
     * data : [{"id":"17","cate_name":"女装","cate_img":null,"parent_id":"0","child":[{"id":"19","cate_name":"针织毛衣","cate_img":"/data/upload/2019-05-15/1557902389544.png","parent_id":"17"},{"id":"20","cate_name":"毛呢大衣","cate_img":"/data/upload/2019-05-15/1557902418122.png","parent_id":"17"},{"id":"21","cate_name":"棉衣","cate_img":"/data/upload/2019-05-15/1557902458489.png","parent_id":"17"},{"id":"22","cate_name":"裙装","cate_img":"/data/upload/2019-05-15/1557902505534.png","parent_id":"17"}]},{"id":"18","cate_name":"鞋包","cate_img":null,"parent_id":"0","child":[{"id":"23","cate_name":"女鞋","cate_img":"/data/upload/2019-05-15/1557902549287.png","parent_id":"18"},{"id":"24","cate_name":"男鞋","cate_img":"/data/upload/2019-05-15/1557902577283.png","parent_id":"18"},{"id":"25","cate_name":"箱包皮具","cate_img":"/data/upload/2019-05-15/1557902633985.png","parent_id":"18"}]}]
     * code : 0
     * message : 分类列表
     */

//    private int code;
//    private String message;
//    private List<DataBean> data;
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public List<DataBean> getData() {
//        return data;
//    }
//
//    public void setData(List<DataBean> data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
    /**
     * id : 17
     * cate_name : 女装
     * cate_img : null
     * parent_id : 0
     * child : [{"id":"19","cate_name":"针织毛衣","cate_img":"/data/upload/2019-05-15/1557902389544.png","parent_id":"17"},{"id":"20","cate_name":"毛呢大衣","cate_img":"/data/upload/2019-05-15/1557902418122.png","parent_id":"17"},{"id":"21","cate_name":"棉衣","cate_img":"/data/upload/2019-05-15/1557902458489.png","parent_id":"17"},{"id":"22","cate_name":"裙装","cate_img":"/data/upload/2019-05-15/1557902505534.png","parent_id":"17"}]
     */

    private String id;
    private String cate_name;
    private String cate_img;
    private String parent_id;
    private List<ChildBean> child;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getCate_img() {
        return cate_img;
    }

    public void setCate_img(String cate_img) {
        this.cate_img = cate_img;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public static class ChildBean {
        /**
         * id : 19
         * cate_name : 针织毛衣
         * cate_img : /data/upload/2019-05-15/1557902389544.png
         * parent_id : 17
         */

        private String id;
        private String cate_name;
        private String cate_img;
        private String parent_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }

        public String getCate_img() {
            return cate_img;
        }

        public void setCate_img(String cate_img) {
            this.cate_img = cate_img;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }
}
