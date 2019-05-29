package com.project.dyuapp.entity;

/**
 * Created by ${田亭亭} on 2017/12/13 0013.
 *
 * @description 二级分类下的视频数据列表实体
 * @change
 */


public class NewestListBean {


        /**
         * article_id : 7
         * title : 商家发布文章社区显示
         * thumb : /data/upload/2017-09-23/59c5bf42513ec.jpg
         * content : 请问
         * addtime : 1506131778
         */

        private String article_id;
        private String title;
        private String thumb;
        private String content;
        private String addtime;

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
}
