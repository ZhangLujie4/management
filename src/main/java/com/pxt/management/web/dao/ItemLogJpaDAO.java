package com.pxt.management.web.dao;

import com.pxt.management.web.dataobject.ItemLogDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author tori
 * 2018/8/12 下午10:42
 */
public interface ItemLogJpaDAO extends JpaRepository<ItemLogDO, Long> {

    ItemLogDO findFirstByAccountAndPlatformAndTradeAndTime(String account, String platform, String trade, Date time);
}
