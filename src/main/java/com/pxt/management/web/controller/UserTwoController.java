package com.pxt.management.web.controller;

import com.pxt.management.common.VO.ResultVO;
import com.pxt.management.common.exception.ResultException;
import com.pxt.management.common.utils.ResultVOUtil;
import com.pxt.management.common.utils.SecurityUtil;
import com.pxt.management.enums.ResultEnum;
import com.pxt.management.web.VO.ItemLogVO;
import com.pxt.management.web.manager.UserTwoManager;
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
@RequestMapping("/api/user2")
public class UserTwoController {

    @Autowired
    private UserTwoManager userTwoManager;

//    @GetMapping("/test")
//    public String getString() {
//        return "user2";
//    }

    @GetMapping("/change")
    public ResultVO<List<ItemLogVO>> getHoldChange() {

        String trade = SecurityUtil.getCurrentTrade();
        if (StringUtils.isEmpty(trade)) {
            throw new ResultException(ResultEnum.NO_LOGIN);
        }
        return ResultVOUtil.success(userTwoManager.getHoldChange(trade));
    }
}
