package com.pxt.management.web.manager;

import com.pxt.management.common.utils.TimeFormatUtil;
import com.pxt.management.web.VO.HoldChangeLogVO;
import com.pxt.management.web.VO.ItemLogVO;
import com.pxt.management.web.dao.DataDetailJpaDAO;
import com.pxt.management.web.dataobject.DataDetailDO;
import com.pxt.management.web.dataobject.ItemLogDO;
import com.pxt.management.web.mapper.ItemLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tori
 * 2018/8/13 下午5:39
 */

@Service
@Slf4j
public class UserTwoManager {

    @Autowired
    private ItemLogMapper itemLogMapper;

    @Autowired
    private DataDetailJpaDAO dataDetailJpaDAO;

    public List<ItemLogVO> getHoldChange(String trade) {

        List<ItemLogDO> itemLogDOList = itemLogMapper.findLastLogList(trade);
        log.info("itemLogDOList = {}", itemLogDOList);
        List<ItemLogVO> itemLogVOList = new ArrayList<>();
        if (itemLogDOList != null) {
            for (ItemLogDO itemLogDO : itemLogDOList) {
                ItemLogVO itemLogVO = new ItemLogVO();
                itemLogVO.setTrade(trade);
                itemLogVO.setPlatform(itemLogDO.getPlatform());
                itemLogVO.setAccount(itemLogDO.getAccount());
                String time = TimeFormatUtil.formatTime(itemLogDO.getTime());
                itemLogVO.setTime(time);
                String dataId = itemLogDO.getDataId();
                List<HoldChangeLogVO> holdChangeLogVOList =
                        dataDetailJpaDAO.findByDataIdOrderByName(dataId)
                        .stream().map(dataDetailDO -> {
                            HoldChangeLogVO holdChangeLogVO = new HoldChangeLogVO();
                            BeanUtils.copyProperties(dataDetailDO, holdChangeLogVO);
                            return holdChangeLogVO;
                        }).collect(Collectors.toList());
                itemLogVO.setData(holdChangeLogVOList);
                itemLogVOList.add(itemLogVO);
            }
        }

        return itemLogVOList;
    }
}
