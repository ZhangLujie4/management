package com.pxt.management.web.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author tori
 * 2018/8/13 上午12:52
 */

@Data
public class SelectDTO {

    private String account;

    private String platform;

    private Date time;

    private String trade;
}
