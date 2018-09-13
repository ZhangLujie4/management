package com.pxt.management.web.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tori
 * 2018/8/11 上午12:19
 */

@Data
@Entity
@Table(name = "data_detail_every")
public class DataDetailEveryDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataId;

    private String name;

    private BigDecimal value;

    private BigDecimal holdChange;

    private Date time;

//    private BigDecimal rate;
}
