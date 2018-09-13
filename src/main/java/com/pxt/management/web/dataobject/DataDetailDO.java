package com.pxt.management.web.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tori
 * 2018/8/6 下午9:42
 */

@Data
@Entity
@Table(name = "data_detail")
public class DataDetailDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataId;

    private String name;

    private BigDecimal value;

    private BigDecimal rate;

    private BigDecimal holdChange;

    private Date time;
}
