package com.pxt.management.web.dao;

import com.pxt.management.web.dataobject.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author tori
 * 2018/8/12 下午10:42
 */
public interface UserJpaDAO extends JpaRepository<UserDO, Long> {

    /**
     * 用户登录时查找用户
     * @param username
     * @return
     */
    UserDO findByUsername(String username);

    @Query(value = "select * from user where identity < 6 order by trade asc, identity asc", nativeQuery = true)
    List<UserDO> findTradeUser();

    @Query(value = "select distinct trade from user order by trade asc", nativeQuery = true)
    List<String> findTradeList();
}
