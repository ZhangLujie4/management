package com.pxt.management.web.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author tori
 * 2018/8/7 下午3:59
 */

@Data
public class RegisterForm {

    @NotBlank(message = "货币类别不能为空")
    private String trade;

    private Integer identity;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
