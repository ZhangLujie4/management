package com.pxt.management.web.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tori
 * 2018/8/7 下午2:07
 */

@Entity
@Data
@Table(name = "average_price_log")
public class AveragePriceLogDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String account;

    private String trade;

    private BigDecimal averagePrice;

    private Date time;

    private String platform;

    private String dataId;

}
