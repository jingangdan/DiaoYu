package com.project.dyuapp.entity;

import java.util.List;

/**
 * @创建者 任伟
 * @创建时间 2017/12/09 16:00
 * @描述 ${}
 */

public class LIvingPlaceAllEntity {

    /**
     * cid : 110000
     * p : 北京市
     * c : [{"cid":"110100","n":"北京市","a":[{"cid":"110101","s":"东城区"},{"cid":"110102","s":"西城区"},{"cid":"110105","s":"朝阳区"},{"cid":"110106","s":"丰台区"},{"cid":"110107","s":"石景山区"},{"cid":"110108","s":"海淀区"},{"cid":"110109","s":"门头沟区"},{"cid":"110111","s":"房山区"},{"cid":"110112","s":"通州区"},{"cid":"110113","s":"顺义区"},{"cid":"110114","s":"昌平区"},{"cid":"110115","s":"大兴区"},{"cid":"110116","s":"怀柔区"},{"cid":"110117","s":"平谷区"},{"cid":"110228","s":"密云县"},{"cid":"110229","s":"延庆县"}]}]
     */

    private String cid;
    private String p;
    private List<CBean> c;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public List<CBean> getC() {
        return c;
    }

    public void setC(List<CBean> c) {
        this.c = c;
    }

    public static class CBean {
        /**
         * cid : 110100
         * n : 北京市
         * a : [{"cid":"110101","s":"东城区"},{"cid":"110102","s":"西城区"},{"cid":"110105","s":"朝阳区"},{"cid":"110106","s":"丰台区"},{"cid":"110107","s":"石景山区"},{"cid":"110108","s":"海淀区"},{"cid":"110109","s":"门头沟区"},{"cid":"110111","s":"房山区"},{"cid":"110112","s":"通州区"},{"cid":"110113","s":"顺义区"},{"cid":"110114","s":"昌平区"},{"cid":"110115","s":"大兴区"},{"cid":"110116","s":"怀柔区"},{"cid":"110117","s":"平谷区"},{"cid":"110228","s":"密云县"},{"cid":"110229","s":"延庆县"}]
         */

        private String cid;
        private String n;
        private List<ABean> a;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public List<ABean> getA() {
            return a;
        }

        public void setA(List<ABean> a) {
            this.a = a;
        }

        public static class ABean {
            /**
             * cid : 110101
             * s : 东城区
             */

            private String cid;
            private String s;

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getS() {
                return s;
            }

            public void setS(String s) {
                this.s = s;
            }
        }
    }
}
