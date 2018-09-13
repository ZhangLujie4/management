package com.pxt.management.web.VO;

import lombok.Data;

import java.util.List;

/**
 * @author tori
 * @description 平台持仓总计
 * @date 2018/8/30 下午3:35
 */

@Data
public class PlatformTotalVO {

    private String time;

    private List<TotalVO> data;

    private List<AccountTotalVO> account;
}
