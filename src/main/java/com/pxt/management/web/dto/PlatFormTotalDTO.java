package com.pxt.management.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tori
 * @description 平台总计中间类
 * @date 2018/8/30 下午4:45
 */

@Data
public class PlatFormTotalDTO {

    private String name;

    private Date time;

    private BigDecimal total;
}
