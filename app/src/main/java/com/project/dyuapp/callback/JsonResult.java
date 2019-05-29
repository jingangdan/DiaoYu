package com.project.dyuapp.callback;

/**
 * @创建者 任伟
 * @创建时间 2017/05/10 15:28
 * @描述 ${网络请求实体类}
 */

public class JsonResult<T> {
    private  String message;
    private  int code ;
    private PageBean page;
    private Exception exception ;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }
    public static class PageBean {
        /**
         * total_datas : 35
         * page_no : 1
         */

        private String total_datas;
        private int page_no;

        public String getTotal_datas() {
            return total_datas;
        }

        public void setTotal_datas(String total_datas) {
            this.total_datas = total_datas;
        }

        public int getPage_no() {
            return page_no;
        }

        public void setPage_no(int page_no) {
            this.page_no = page_no;
        }
    }
    public JsonResult(){
        code = -1 ;
        message = "解析失败！";
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private  T data;

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

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }


}
