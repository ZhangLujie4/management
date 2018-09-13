package com.pxt.management.web.VO;

import com.pxt.management.common.dataobject.UserRoleEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

/**
 * @author tori
 * @description
 * @date 2018/8/21 下午9:12
 */

@Data
public class TradeUserVO {

    private String trade;

    private String username;

    private Integer identity;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum type;
}
