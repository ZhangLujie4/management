package com.pxt.management.web.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tori
 * 2018/8/13 下午9:37
 */

@Data
public class HoldChangeLogVO {

    private String name;

    @JsonProperty("hold")
    private BigDecimal value;

    @JsonProperty("change")
    private BigDecimal holdChange;


}
