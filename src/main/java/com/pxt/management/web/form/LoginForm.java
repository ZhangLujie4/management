package com.pxt.management.web.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tori
 * 2018/8/7 上午10:33
 */

@Data
public class LoginForm {

    @NotBlank(message = "账户不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
