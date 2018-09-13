package com.pxt.management.web.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author tori
 * @description 合计类
 * @date 2018/8/30 下午5:01
 */

@Data
public class TotalVO {

    private String name;

    @JsonProperty(value = "hold")
    private BigDecimal total;
}
