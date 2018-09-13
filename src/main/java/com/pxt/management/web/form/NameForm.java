package com.pxt.management.web.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tori
 * @description
 * @date 2018/8/30 下午11:26
 */

@Data
public class NameForm {

    @NotBlank(message = "trade不能为空")
    private String trade;

    @NotBlank(message = "平台不能为空")
    private String platform;

    @NotBlank(message = "账户不能为空")
    private String account;
}
