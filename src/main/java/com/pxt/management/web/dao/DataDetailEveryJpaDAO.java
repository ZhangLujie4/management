package com.pxt.management.web.dao;

import com.pxt.management.web.dataobject.DataDetailEveryDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author tori
 * 2018/8/12 下午10:43
 */
public interface DataDetailEveryJpaDAO extends JpaRepository<DataDetailEveryDO, Long> {

    List<DataDetailEveryDO> findByDataId(String dataId);
}
