package com.pxt.management.web.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author tori
 * 2018/8/13 下午11:55
 */

@Data
public class DayTagVO<T> {

    private List<T> data;

    private BigDecimal average;

    private String time;
}
