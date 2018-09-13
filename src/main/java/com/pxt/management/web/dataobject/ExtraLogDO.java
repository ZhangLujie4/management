package com.pxt.management.web.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tori
 * @description
 * @date 2018/8/31 上午12:01
 */

@Data
@Entity
@Table(name = "extra_log")
public class ExtraLogDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trade;

    private String platform;

    private String account;

    private String time;

    private String name;

    private BigDecimal value;

    private BigDecimal rate;
}
