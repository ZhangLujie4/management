package com.pxt.management.web.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tori
 * 2018/8/6 下午8:58
 */

@Data
@Entity
@Table(name = "item_log")
public class ItemLogDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String account;

    private String trade;

    private String dataId;

    private String platform;

    private Date time;

    /**
     * 每小时均价
     */
    private BigDecimal average;

    /**
     * 计算过程中间值
     */
    @Column(name = "calculate_a")
    private BigDecimal calculateA;


    @Column(name = "calculate_b")
    private BigDecimal calculateB;
}
