package com.pxt.management.web.dao;

import com.pxt.management.web.dataobject.ExtraLogDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author tori
 * @description
 * @date 2018/8/31 上午12:07
 */
public interface ExtraLogJpaDAO extends JpaRepository<ExtraLogDO, Long> {

    /**
     * 获取冲提币记录
     * @param trade
     * @param platform
     * @param account
     * @param name
     * @param time
     * @return
     */
    @Query(value = "select * from extra_log " +
            "where trade = ?1 " +
            "and platform = ?2 " +
            "and account = ?3 " +
            "and `name` = ?4 " +
            "and `time` = ?5",nativeQuery = true)
    ExtraLogDO getExtraLog(String trade, String platform, String account, String name, String time);
}
