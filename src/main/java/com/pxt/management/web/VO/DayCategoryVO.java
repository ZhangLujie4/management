package com.pxt.management.web.VO;

import lombok.Data;

import java.util.List;

/**
 * @author tori
 * 2018/8/13 下午11:54
 */

@Data
public class DayCategoryVO<T> {

    private String account;

    private String trade;

    private List<T> dayLog;

    private String platform;
}
