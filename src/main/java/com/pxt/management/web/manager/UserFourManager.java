package com.pxt.management.web.manager;

import com.pxt.management.common.utils.TimeFormatUtil;
import com.pxt.management.web.VO.*;
import com.pxt.management.web.dao.AveragePriceLogJpaDAO;
import com.pxt.management.web.dao.DataDetailEveryJpaDAO;
import com.pxt.management.web.dao.ExtraLogJpaDAO;
import com.pxt.management.web.dataobject.AveragePriceLogDO;
import com.pxt.management.web.dataobject.DataDetailEveryDO;
import com.pxt.management.web.dataobject.ExtraLogDO;
import com.pxt.management.web.dto.PlatFormTotalDTO;
import com.pxt.management.web.dto.SelectDTO;
import com.pxt.management.web.form.NameForm;
import com.pxt.management.web.mapper.AveragePriceLogMapper;
import com.pxt.management.web.mapper.ItemLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tori
 * 2018/8/14 上午12:32
 */

@Slf4j
@Service
public class UserFourManager {

    @Autowired
    private ItemLogMapper itemLogMapper;

    @Autowired
    private AveragePriceLogMapper averagePriceLogMapper;

    @Autowired
    private AveragePriceLogJpaDAO averagePriceLogJpaDAO;

    @Autowired
    private DataDetailEveryJpaDAO dataDetailEveryJpaDAO;

    @Autowired
    private ExtraLogJpaDAO extraLogJpaDAO;


    public List<DayCategoryVO> getDaysAverageLog(String trade) {

        List<SelectDTO> selectDTOList = itemLogMapper.selectItemLogList(trade);
        List<DayCategoryVO> dayCategoryVOList = new ArrayList<>();

        if (selectDTOList == null) {
            return dayCategoryVOList;
        }

        for (SelectDTO selectDTO : selectDTOList) {
            String account = selectDTO.getAccount();
            String platform = selectDTO.getPlatform();
            List<AveragePriceLogDO> averagePriceLogDOList = averagePriceLogJpaDAO.findByAccountAndPlatformAndTrade(account, platform, trade);
            DayCategoryVO dayCategoryVO = new DayCategoryVO();
            dayCategoryVO.setTrade(trade);
            dayCategoryVO.setAccount(account);
            dayCategoryVO.setPlatform(platform);
            List<DayTagVO> dayTagVOList = new ArrayList<>();
            if (averagePriceLogDOList != null) {
                for (AveragePriceLogDO averagePriceLogDO : averagePriceLogDOList) {
//                        if (averagePriceLogDO != null) {
                    DayTagVO dayTagVO = new DayTagVO();
                    dayTagVO.setAverage(averagePriceLogDO.getAveragePrice());
                    String time = TimeFormatUtil.formatDay(averagePriceLogDO.getTime());
                    dayTagVO.setTime(time);
                    List<HoldChangeExtraVO> holdChangeExtraVOList = new ArrayList<>();
                    List<DataDetailEveryDO> dataDetailEveryDOList =
                            dataDetailEveryJpaDAO.findByDataId(averagePriceLogDO.getDataId());
                    if (dataDetailEveryDOList != null) {
                        for (DataDetailEveryDO dataDetailEveryDO : dataDetailEveryDOList) {
                            HoldChangeExtraVO holdChangeExtraVO = new HoldChangeExtraVO();
                            BeanUtils.copyProperties(dataDetailEveryDO, holdChangeExtraVO);
                            ExtraLogDO extraLogDO = extraLogJpaDAO.getExtraLog(trade, platform, account, dataDetailEveryDO.getName(), time);
                            if (null != extraLogDO) {
                                holdChangeExtraVO.setExtra(extraLogDO.getValue());
                            }
                            holdChangeExtraVOList.add(holdChangeExtraVO);
                        }
                    }
                    dayTagVO.setData(holdChangeExtraVOList);
                    dayTagVOList.add(dayTagVO);
//                        }
                }
            }
            dayCategoryVO.setDayLog(dayTagVOList);
            dayCategoryVOList.add(dayCategoryVO);
        }

        return dayCategoryVOList;
    }

    public List<PlatformTotalVO> getDayTotal(String trade) {

        List<PlatformTotalVO> platformTotalVOList = new ArrayList<>();

        //获得日期list
        List<Date> dateList = averagePriceLogJpaDAO.getTradeDayList(trade);
        log.info("dateList = {}", dateList);

        if (!CollectionUtils.isEmpty(dateList)) {
            for (Date date : dateList) {
                PlatformTotalVO platformTotalVO = new PlatformTotalVO();
                platformTotalVO.setTime(TimeFormatUtil.formatDay(date));
                List<TotalVO> totalVOList = averagePriceLogMapper.getPlatformTotalList(trade)
                        .stream().map(platFormTotalDTO -> {
                            TotalVO totalVO = new TotalVO();
                            BeanUtils.copyProperties(platFormTotalDTO, totalVO);
                            return totalVO;
                        }).collect(Collectors.toList());
                platformTotalVO.setData(totalVOList);
                List<AccountTotalVO> accountTotalVOList = averagePriceLogJpaDAO.getTradePlatformList(trade)
                        .stream().map(platform -> {
                            AccountTotalVO accountTotalVO = new AccountTotalVO();
                            accountTotalVO.setPlatform(platform);
                            List<TotalVO> totalVOS = averagePriceLogMapper.getAccountTotalList(trade, platform)
                                    .stream().map(accountTotalDTO -> {
                                        TotalVO totalVO = new TotalVO();
                                        BeanUtils.copyProperties(accountTotalDTO, totalVO);
                                        return totalVO;
                                    }).collect(Collectors.toList());
                            accountTotalVO.setData(totalVOS);
                            return accountTotalVO;
                        }).collect(Collectors.toList());
                platformTotalVO.setAccount(accountTotalVOList);
                platformTotalVOList.add(platformTotalVO);
            }
        }

        return platformTotalVOList;
    }

    public List<String> getNameList(NameForm nameForm) {

        return averagePriceLogMapper.getNameList(nameForm.getTrade(), nameForm.getPlatform(), nameForm.getAccount());
    }
}
