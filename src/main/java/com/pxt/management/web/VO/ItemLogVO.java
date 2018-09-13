package com.pxt.management.web.VO;

import lombok.Data;

import java.util.List;

/**
 * @author tori
 * 2018/8/13 上午1:05
 */

@Data
public class ItemLogVO<T> {

    private String account;

    private String trade;

    private List<T> data;

    private String time;

    private String platform;
}
