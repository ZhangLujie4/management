package com.pxt.management.web.VO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tori
 * @description 账户持仓合计
 * @date 2018/8/30 下午3:35
 */

@Data
public class AccountTotalVO {

    private String platform;

    private List<TotalVO> data;
}
