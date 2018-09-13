package com.pxt.management.web.dao;

import com.pxt.management.web.dataobject.AveragePriceLogDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author tori
 * 2018/8/12 下午10:44
 */
public interface AveragePriceLogJpaDAO extends JpaRepository<AveragePriceLogDO, Long> {

    List<AveragePriceLogDO> findByAccountAndPlatformAndTrade(String account, String platform, String trade);

    /**
     * 获取trade下的所有平台
     * @param trade
     * @return
     */
    @Query(value = "select platform from average_price_log where trade = ?1 GROUP BY platform order by platform", nativeQuery = true)
    List<String> getTradePlatformList(String trade);

    /**
     * 获取时间列表
     * @param trade
     * @return
     */
    @Query(value = "select o.time from average_price_log o where o.trade=?1 GROUP BY o.time order by o.time desc", nativeQuery = true)
    List<Date> getTradeDayList(String trade);

    /**
     * 查询是否生成该日均价
     * @param trade
     * @param platform
     * @param account
     * @param time
     * @return
     */
    @Query(value = "select * from average_price_log " +
            "where trade = ?1 " +
            "and platform = ?2 " +
            "and account = ?3 " +
            "and date_format(`time`,'%Y-%m-%d') = ?4", nativeQuery = true)
    AveragePriceLogDO findExistLog(String trade, String platform, String account, String time);
}
