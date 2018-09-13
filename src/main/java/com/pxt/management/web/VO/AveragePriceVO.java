package com.pxt.management.web.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tori
 * 2018/8/13 下午11:02
 */

@Data
public class AveragePriceVO<T> {

    private String account;

    private String trade;

    private List<T> data;

    private String time;

    private String platform;

    private BigDecimal average;
}
