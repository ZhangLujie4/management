package com.pxt.management.web.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tori
 * @description
 * @date 2018/8/21 下午11:49
 */

@Data
public class ChangePassForm {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;

}
