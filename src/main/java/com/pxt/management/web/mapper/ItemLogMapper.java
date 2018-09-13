package com.pxt.management.web.mapper;

import com.pxt.management.web.dataobject.ItemLogDO;
import com.pxt.management.web.dto.SelectDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tori
 * 2018/8/13 上午12:56
 */

public interface ItemLogMapper {

    List<SelectDTO> selectItemLogList(String trade);

    List<ItemLogDO> findLastLogList(String trade);

    List<ItemLogDO> find48Log(@Param("trade") String trade, @Param("account") String account, @Param("platform") String platform);
}
