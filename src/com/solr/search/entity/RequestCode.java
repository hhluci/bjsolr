package com.solr.search.entity;


public class RequestCode {

    private Integer code;
    private String stuts;

    public RequestCode(Integer code, String stuts) {
        this.code = code;
        this.stuts = stuts;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStuts() {
        return stuts;
    }

    public void setStuts(String stuts) {
        this.stuts = stuts;
    }
}
