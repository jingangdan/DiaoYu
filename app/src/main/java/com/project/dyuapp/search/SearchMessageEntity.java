package com.project.dyuapp.search;

import com.project.dyuapp.entity.FishingGearEntity;
import com.project.dyuapp.entity.ForumEntity;
import com.project.dyuapp.entity.HomeFishingPlaceEntity;
import com.project.dyuapp.entity.VideoListBean;
import com.project.dyuapp.shop.GoodsData;
import com.project.dyuapp.shop.GoodsEntity;

import java.util.List;

/**
 * Created by jingang on 2018/3/10.
 */

public class SearchMessageEntity {

    /**
     * data : {"fishing_grounds":{"count":16,"data":[{"f_gid":"1022","g_name":"大河池钓场","g_image":"/data/upload/2018-03-06/1520323040779.jpg","cat_id":"73","provinceid":"330000","cityid":"330500","position":"","countyid":"330521","pay_type":"1","pay_content":"50元/天","grounds_address":"浙江省湖州市德清县","longitude":"119.99655679910668","latitude":"30.574718601314046","name":"黑坑","distance":523.95},{"f_gid":"861","g_name":"黄河钓场","g_image":"/data/upload/2018-02-28/1519778759810.jpeg","cat_id":"77","provinceid":"370000","cityid":"371400","position":"晏城街道","countyid":"371425","pay_type":"2","pay_content":"","grounds_address":"山东省德州市齐河县晏城街道","longitude":"116.872220","latitude":"36.738297","name":"江河","distance":227.75},{"f_gid":"825","g_name":"雨龙钓场","g_image":"/data/upload/2018-02-27/1519744725414.jpeg","cat_id":"74","provinceid":"530000","cityid":"532300","position":"法脿镇","countyid":"532322","pay_type":"1","pay_content":"15元/斤","grounds_address":"云南省楚雄彝族自治州双柏县法脿镇","longitude":"101.777652","latitude":"24.561231","name":"斤塘","distance":1976.51}]},"fishing_shop":{"count":0,"data":[]},"videos":{"count":"0","data":[]},"forum":{"count":"0","data":[{"f_id":"306","content":"　　水库里的鱼和养鱼池里的鱼不同，觅食习性有着明显的季节区域。如果说到陌生水库选位时要注意的因素是多种的话，那么根据季节选位无疑就是首要的因素。因为只有熟悉鱼在不同季节喜欢在不同地点觅食的习性，我们在选位时才不会盲目，才有可能钓好鱼。?\n\n　　阳历四月中旬，在东北还算是早春，白天最高气温为18℃左右，水库里的主钓对象鱼鲤鱼、鲫鱼已经开始觅食，活动区域基本上在深水区附近的浅水。五月初，气温在20℃左右，水库上游较平坦的滩地由于水温较高，是鱼儿经常光顾的地方。春末夏初，气温26℃左右，水库中上游是钓鱼的好地方。盛夏至仲秋气温为26～29℃，水库中下游是鱼儿喜欢活动的地方。仲秋以后，随着气温渐渐下降，鱼又回到了深水区，此时选位垂钓情况和早春基本相似。?\n\n二、实地探测?\n\n　　实地探测的方法有多种：1.投竿探测。这是钓友们常用的一种有效方法。将钩饵分别投在25米、30米、40米甚至50米以外不等的距离，只要其中一点或者两点上鱼，根据鱼情以此选定钓点。2.水下探测。一根手线一端系重量合适的铅坠，用力甩向远处，然后慢慢收线，用手来感觉。如果水下有沟或者较洼的地方，把线系扣做为记号，量出线距，确定钓点。3.岸边地形走向。水库大多是在两山之间筑坝而成，岸边地形走向都是随山势而变化，而水下的地势基本上都和岸边的地形有着相似和相近的地方。我们仔细观察就会发现，岸边的地形多呈波浪形。通常我们把凸出的部分称为\u201c尖子\u201d，凹进的地方就是湾子。正常情况下，如果湾子30米处上鱼，而附近的\u201c尖子\u201d很有可能35米处或稍远的地方就是和湾子鱼道相连的地方。有经验的钓友都是根据\u201c尖子\u201d和湾子的距离做出判断的，因为这些自然特征常常会给我们一些启示。?\n\n三、根据岸边特殊情况选位?\n\n　　我们到陌生水库钓鱼，在水情地势不了解的情况下，选位时心里难免有种茫然失措的感觉。这时，我们如果能很好利用岸边一些有利因素，比如：岸边的猪舍、鸡场、牛圈、进水口等等。往往就成了我们选一个好钓位的有效手段。","title":"陌生钓场，水库如何选位","thumb_img":"/data/upload/2018-02-07/5a7a51e564176.jpeg","is_tuijian":"2","is_jinghua":"2","member_list_nickname":"aa","member_list_headpic":"/data/upload/2018-02-02/5a741b1481c9b.png"}]}}
     * code : 0
     * message : 综合搜索结果
     */

    private DataBeanXX data;
    private int code;
    private String message;

    public DataBeanXX getData() {
        return data;
    }

    public void setData(DataBeanXX data) {
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

    public static class DataBeanXX {
        /**
         * fishing_grounds : {"count":16,"data":[]}
         * fishing_shop : {"count":0,"data":[]}
         * videos : {"count":"0","data":[]}
         * forum : {"count":"0","data":[]}
         */

        private FishingGroundsBean fishing_grounds;
        private FishingShopBean fishing_shop;
        private VideosBean videos;
        private ForumBean forum;
        private GoodsBean goods;

        public FishingGroundsBean getFishing_grounds() {
            return fishing_grounds;
        }

        public void setFishing_grounds(FishingGroundsBean fishing_grounds) {
            this.fishing_grounds = fishing_grounds;
        }

        public FishingShopBean getFishing_shop() {
            return fishing_shop;
        }

        public void setFishing_shop(FishingShopBean fishing_shop) {
            this.fishing_shop = fishing_shop;
        }

        public VideosBean getVideos() {
            return videos;
        }

        public void setVideos(VideosBean videos) {
            this.videos = videos;
        }

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public ForumBean getForum() {
            return forum;
        }

        public void setForum(ForumBean forum) {
            this.forum = forum;
        }

        public static class FishingGroundsBean {
            /**
             * count : 16
             * data : []
             */

            private int count;
            private List<HomeFishingPlaceEntity> data;

            public List<HomeFishingPlaceEntity> getData() {
                return data;
            }

            public void setData(List<HomeFishingPlaceEntity> data) {
                this.data = data;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

        }

        public static class FishingShopBean {
            /**
             * count : 0
             * data : []
             */

            private int count;
            private List<FishingGearEntity> data;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<FishingGearEntity> getData() {
                return data;
            }

            public void setData(List<FishingGearEntity> data) {
                this.data = data;
            }
        }

        public static class VideosBean {
            /**
             * count : 0
             * data : []
             */

            private String count;
            private List<VideoListBean> data;

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public List<VideoListBean> getData() {
                return data;
            }

            public void setData(List<VideoListBean> data) {
                this.data = data;
            }
        }

        public static class ForumBean {
            /**
             * count : 0
             * data : []
             */

            private String count;
            private List<ForumEntity> data;

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public List<ForumEntity> getData() {
                return data;
            }

            public void setData(List<ForumEntity> data) {
                this.data = data;
            }

        }

        public static class GoodsBean {
            private String count;
            private List<GoodsEntity> data;

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public List<GoodsEntity> getData() {
                return data;
            }

            public void setData(List<GoodsEntity> data) {
                this.data = data;
            }
        }
    }
}
