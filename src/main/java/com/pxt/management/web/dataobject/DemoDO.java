package com.pxt.management.web.dataobject;

import java.util.List;

/**
 * @author tori
 * @description
 * @date 2018/9/3 下午3:40
 */
public class DemoDO {


    /**
     * status : success
     * code : 0
     * content : ["BTA","BTB","BTC"]
     */

    private String status;
    private int code;
    private List<String> content;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
