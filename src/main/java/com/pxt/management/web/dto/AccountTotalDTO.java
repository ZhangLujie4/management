package com.pxt.management.web.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tori
 * @description accountTotal中间类
 * @date 2018/8/30 下午4:46
 */

@Data
public class AccountTotalDTO {

    private String platform;

    private String name;

    private Date time;

    private BigDecimal total;
}
