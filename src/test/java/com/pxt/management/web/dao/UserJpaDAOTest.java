package com.pxt.management.web.dao;

import com.pxt.management.ManagementApplicationTests;
import com.pxt.management.common.dataobject.UserRoleEnum;
import com.pxt.management.common.utils.Blowfish;
import com.pxt.management.web.dataobject.UserDO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author tori
 * 2018/8/12 下午11:06
 */
public class UserJpaDAOTest extends ManagementApplicationTests {

    @Autowired
    private UserJpaDAO userJpaDAO;

    @Test
    public void saveSomeUser() {

        String encodePwd = null;
        try {
            encodePwd = Blowfish.encode("pxt123");
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserDO userDO1 = new UserDO();
        userDO1.setPassword(encodePwd);
        userDO1.setTrade("USER1");
        userDO1.setUsername("user111");
        userDO1.setType(UserRoleEnum.ROLE_USER_1);
        userJpaDAO.save(userDO1);

        UserDO userDO2 = new UserDO();
        userDO2.setPassword(encodePwd);
        userDO2.setTrade("USER2");
        userDO2.setUsername("user222");
        userDO2.setType(UserRoleEnum.ROLE_USER_2);
        userJpaDAO.save(userDO2);

        UserDO userDO3 = new UserDO();
        userDO3.setPassword(encodePwd);
        userDO3.setTrade("USER3");
        userDO3.setUsername("user333");
        userDO3.setType(UserRoleEnum.ROLE_USER_3);
        userJpaDAO.save(userDO3);

        UserDO userDO4 = new UserDO();
        userDO4.setPassword(encodePwd);
        userDO4.setTrade("USER4");
        userDO4.setUsername("user444");
        userDO4.setType(UserRoleEnum.ROLE_USER_4);
        userJpaDAO.save(userDO4);

        UserDO userDO5 = new UserDO();
        userDO5.setPassword(encodePwd);
        userDO5.setTrade("USER5");
        userDO5.setUsername("user555");
        userDO5.setType(UserRoleEnum.ROLE_USER_5);
        userJpaDAO.save(userDO5);

        UserDO userDO = new UserDO();
        userDO.setPassword(encodePwd);
        userDO.setTrade("ADMIN");
        userDO.setUsername("admin12345");
        userDO.setType(UserRoleEnum.ROLE_ADMIN);
        userJpaDAO.save(userDO);
    }

}