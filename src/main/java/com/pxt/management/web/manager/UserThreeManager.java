package com.pxt.management.web.manager;

import com.pxt.management.common.utils.TimeFormatUtil;
import com.pxt.management.web.VO.AveragePriceVO;
import com.pxt.management.web.VO.HoldChangeLogVO;
import com.pxt.management.web.VO.ItemLogVO;
import com.pxt.management.web.dao.DataDetailJpaDAO;
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
 * 2018/8/13 下午11:05
 */

@Slf4j
@Service
public class UserThreeManager {

    @Autowired
    private ItemLogMapper itemLogMapper;

    @Autowired
    private DataDetailJpaDAO dataDetailJpaDAO;

    public List<AveragePriceVO> getAveragePrice(String trade) {

        List<ItemLogDO> itemLogDOList = itemLogMapper.findLastLogList(trade);
        log.info("itemLogDOList = {}", itemLogDOList);
        List<AveragePriceVO> averagePriceVOList = new ArrayList<>();
        if (itemLogDOList != null) {
            for (ItemLogDO itemLogDO : itemLogDOList) {
//                if (itemLogDO.getAverage() != null) {
                    AveragePriceVO averagePriceVO = new AveragePriceVO();
                    averagePriceVO.setTrade(trade);
                    averagePriceVO.setPlatform(itemLogDO.getPlatform());
                    averagePriceVO.setAccount(itemLogDO.getAccount());
                    String time = TimeFormatUtil.formatTime(itemLogDO.getTime());
                    averagePriceVO.setTime(time);
                    averagePriceVO.setAverage(itemLogDO.getAverage());
                    String dataId = itemLogDO.getDataId();
                    List<HoldChangeLogVO> holdChangeLogVOList =
                            dataDetailJpaDAO.findByDataIdOrderByName(dataId)
                                    .stream().map(dataDetailDO -> {
                                HoldChangeLogVO holdChangeLogVO = new HoldChangeLogVO();
                                BeanUtils.copyProperties(dataDetailDO, holdChangeLogVO);
                                return holdChangeLogVO;
                            }).collect(Collectors.toList());
                    averagePriceVO.setData(holdChangeLogVOList);
                    averagePriceVOList.add(averagePriceVO);
//                }
            }
        }

        return averagePriceVOList;
    }
}
