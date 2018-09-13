package com.pxt.management.web.manager;

import com.pxt.management.common.utils.TimeFormatUtil;
import com.pxt.management.web.VO.DayCategoryVO;
import com.pxt.management.web.VO.DayTagVO;
import com.pxt.management.web.VO.HoldChangeLogVO;
import com.pxt.management.web.VO.ItemLogVO;
import com.pxt.management.web.dao.DataDetailJpaDAO;
import com.pxt.management.web.dataobject.DataDetailDO;
import com.pxt.management.web.dataobject.ItemLogDO;
import com.pxt.management.web.dto.SelectDTO;
import com.pxt.management.web.mapper.ItemLogMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tori
 * 2018/8/14 上午12:32
 */

@Service
public class UserFiveManager {

    @Autowired
    private ItemLogMapper itemLogMapper;

    @Autowired
    private DataDetailJpaDAO dataDetailJpaDAO;

    public List<DayCategoryVO> getDayLog(String trade) {

        List<SelectDTO> selectDTOList = itemLogMapper.selectItemLogList(trade);
        List<DayCategoryVO> dayCategoryVOList = new ArrayList<>();
        if (selectDTOList == null) {
            return dayCategoryVOList;
        }

        for (SelectDTO selectDTO : selectDTOList) {
            String account = selectDTO.getAccount();
            String platform = selectDTO.getPlatform();
            List<ItemLogDO> itemLogDOList = itemLogMapper.find48Log(trade, account, platform);
            DayCategoryVO dayCategoryVO = new DayCategoryVO();
            dayCategoryVO.setAccount(account);
            dayCategoryVO.setPlatform(platform);
            dayCategoryVO.setTrade(trade);
            List<DayTagVO> dayTagVOList = new ArrayList<>();
            if (itemLogDOList != null) {
                for (ItemLogDO itemLogDO : itemLogDOList) {
//                    if (itemLogDO.getAverage() != null) { //在犹豫这里的判断是不是要加
                        DayTagVO dayTagVO = new DayTagVO();
                        dayTagVO.setAverage(itemLogDO.getAverage());
                        dayTagVO.setTime(TimeFormatUtil.formatTime(itemLogDO.getTime()));
                        List<HoldChangeLogVO> holdChangeLogVOList = new ArrayList<>();
                        List<DataDetailDO> dataDetailDOList = dataDetailJpaDAO.findByDataIdOrderByName(itemLogDO.getDataId());
                        if (dataDetailDOList != null) {
                            for (DataDetailDO dataDetailDO : dataDetailDOList) {
                                HoldChangeLogVO holdChangeLogVO = new HoldChangeLogVO();
                                BeanUtils.copyProperties(dataDetailDO, holdChangeLogVO);
                                holdChangeLogVOList.add(holdChangeLogVO);
                            }
                        }
                        dayTagVO.setData(holdChangeLogVOList);
                        dayTagVOList.add(dayTagVO);
//                    }
                }
            }
            dayCategoryVO.setDayLog(dayTagVOList);
            dayCategoryVOList.add(dayCategoryVO);
        }

        return dayCategoryVOList;
    }
}
