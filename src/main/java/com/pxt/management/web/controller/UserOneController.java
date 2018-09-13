package com.pxt.management.web.controller;

import com.pxt.management.common.VO.ResultVO;
import com.pxt.management.common.exception.ResultException;
import com.pxt.management.common.utils.ResultVOUtil;
import com.pxt.management.common.utils.SecurityUtil;
import com.pxt.management.enums.ResultEnum;
import com.pxt.management.web.VO.ItemLogVO;
import com.pxt.management.web.manager.UserOneManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tori
 * 2018/8/12 下午10:48
 */

@Slf4j
@RestController
@RequestMapping("/api/user1")
public class UserOneController {

    @Autowired
    private UserOneManager userOneManager;

//    @GetMapping("/test")
//    public String getString() {
//        return "user1";
//    }

    @GetMapping("/last")
    public ResultVO<List<ItemLogVO>> getLastLogList() {

        String trade = SecurityUtil.getCurrentTrade();
        if (StringUtils.isEmpty(trade)) {
            throw new ResultException(ResultEnum.NO_LOGIN);
        }
        return ResultVOUtil.success(userOneManager.getLastLogList(trade));
    }
}
