package com.pxt.management.web.dao;

import com.pxt.management.web.dataobject.DataDetailDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author tori
 * 2018/8/12 下午10:43
 */
public interface DataDetailJpaDAO extends JpaRepository<DataDetailDO, Long> {

    List<DataDetailDO> findByDataIdOrderByName(String dataId);

    DataDetailDO findByDataIdAndName(String dataId, String name);
}
