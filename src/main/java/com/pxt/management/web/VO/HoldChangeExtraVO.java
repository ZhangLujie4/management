package com.pxt.management.web.VO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author tori
 * @description
 * @date 2018/8/31 上午12:20
 */

@Data
public class HoldChangeExtraVO extends HoldChangeLogVO {

    private BigDecimal extra;
}
