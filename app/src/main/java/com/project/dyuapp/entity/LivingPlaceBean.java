package com.project.dyuapp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 任伟
 * @创建时间 2017/09/07 17:49
 * @描述 ${地址选择器实体}
 */
public class LivingPlaceBean {
    /**
     * cid : 110000
     * p : 北京市
     * c : [{"cid":"110100","pid":"110000","n":"北京市"}]
     */

    private String cid;
    private String p;
    private List<CBean> c = new ArrayList<>();

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
         * pid : 110000
         * n : 北京市
         */

        private String cid;
        private String pid;
        private String n;
        private String sortLetters;  //显示数据拼音的首字母

        public String getSortLetters() {
            return sortLetters;
        }

        public void setSortLetters(String sortLetters) {
            this.sortLetters = sortLetters;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        @Override
        public String toString() {
            return "CBean{" +
                    "cid='" + cid + '\'' +
                    ", pid='" + pid + '\'' +
                    ", n='" + n + '\'' +
                    ", sortLetters='" + sortLetters + '\'' +
                    '}';
        }
    }
}
