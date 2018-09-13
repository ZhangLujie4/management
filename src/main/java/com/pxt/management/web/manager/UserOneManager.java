package com.pxt.management.web.manager;

import com.pxt.management.common.utils.TimeFormatUtil;
import com.pxt.management.web.VO.DataDetailVO;
import com.pxt.management.web.VO.ItemLogVO;
import com.pxt.management.web.dao.DataDetailJpaDAO;
import com.pxt.management.web.dao.ItemLogJpaDAO;
import com.pxt.management.web.dataobject.ItemLogDO;
import com.pxt.management.web.dto.SelectDTO;
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
 * 2018/8/13 上午1:07
 */

@Service
@Slf4j
public class UserOneManager {

    @Autowired
    private ItemLogMapper itemLogMapper;

    @Autowired
    private ItemLogJpaDAO itemLogJpaDAO;

    @Autowired
    private DataDetailJpaDAO dataDetailJpaDAO;

    public List<ItemLogVO> getLastLogList(String trade) {

//        List<SelectDTO> selectDTOList =  itemLogMapper.selectLastLogList(trade);
//        log.info("selectDTOList = {}", selectDTOList);

        List<ItemLogVO> itemLogVOList = new ArrayList<>();
        List<ItemLogDO> itemLogDOList = itemLogMapper.findLastLogList(trade);
        if (itemLogDOList != null) {
            for (ItemLogDO itemLogDO : itemLogDOList) {
//                ItemLogDO itemLogDO = itemLogJpaDAO
//                        .findByAccountAndPlatformAndTradeAndTime(s.getAccount(), s.getPlatform(), trade, s.getTime());
                log.info("itemLog = {}", itemLogDO);
                ItemLogVO itemLogVO = new ItemLogVO();
                itemLogVO.setAccount(itemLogDO.getAccount());
                itemLogVO.setPlatform(itemLogDO.getPlatform());
                itemLogVO.setTime(TimeFormatUtil.formatTime(itemLogDO.getTime()));
                itemLogVO.setTrade(trade);
                List<DataDetailVO> dataDetailVOList = dataDetailJpaDAO.findByDataIdOrderByName(itemLogDO.getDataId())
                        .stream().map(dataDetailDO -> {
                            DataDetailVO dataDetailVO = new DataDetailVO();
                            BeanUtils.copyProperties(dataDetailDO, dataDetailVO);
                            return dataDetailVO;
                        }).collect(Collectors.toList());
                itemLogVO.setData(dataDetailVOList);
                itemLogVOList.add(itemLogVO);
            }
        }
        return itemLogVOList;
    }
}
