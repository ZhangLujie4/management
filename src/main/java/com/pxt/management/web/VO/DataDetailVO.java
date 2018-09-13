package com.pxt.management.web.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author tori
 * 2018/8/13 上午1:05
 */

@Data
public class DataDetailVO {

    private String name;

    @JsonProperty("hold")
    private BigDecimal value;
}
