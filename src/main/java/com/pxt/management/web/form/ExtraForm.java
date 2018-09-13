package com.pxt.management.web.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @author tori
 * @description
 * @date 2018/8/30 下午11:35
 */

@Data
public class ExtraForm {

    @NotBlank(message = "trade不能为空")
    private String trade;

    @NotBlank(message = "平台不能为空")
    private String platform;

    @NotBlank(message = "账户不能为空")
    private String account;

    @NotBlank(message = "时间不能为空")
    private String time;

    @NotBlank(message = "币种不能为空")
    private String name;

    private BigDecimal value;

    private BigDecimal rate;
}
