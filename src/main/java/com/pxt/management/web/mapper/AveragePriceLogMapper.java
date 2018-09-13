package com.pxt.management.web.mapper;

import com.pxt.management.web.dto.AccountTotalDTO;
import com.pxt.management.web.dto.PlatFormTotalDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tori
 * @description
 * @date 2018/8/30 下午4:39
 */
public interface AveragePriceLogMapper {

    /**
     * 获取平台合计
     * @param trade
     * @return
     */
    List<PlatFormTotalDTO> getPlatformTotalList(@Param("trade") String trade);

    /**
     * 获取账户合计数据
     * @param trade
     * @param platform
     * @return
     */
    List<AccountTotalDTO> getAccountTotalList(@Param("trade") String trade, @Param("platform") String platform);

    /**
     * 获取平台账号下的币种列表
     * @param trade
     * @param platform
     * @param account
     * @return
     */
    List<String> getNameList(@Param("trade") String trade, @Param("platform") String platform, @Param("account") String account);
}
