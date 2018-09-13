package com.pxt.management.web.controller;

import com.pxt.management.common.VO.ResultVO;
import com.pxt.management.common.exception.ResultException;
import com.pxt.management.common.utils.ResultVOUtil;
import com.pxt.management.enums.ResultEnum;
import com.pxt.management.web.VO.*;
import com.pxt.management.web.form.*;
import com.pxt.management.web.manager.UserFiveManager;
import com.pxt.management.web.manager.UserFourManager;
import com.pxt.management.web.manager.UserManager;
import com.pxt.management.web.manager.UserThreeManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;

/**
 * @author tori
 * 2018/8/12 下午10:49
 */

@Slf4j
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserThreeManager userThreeManager;

    @Autowired
    private UserFourManager userFourManager;

    @Autowired
    private UserFiveManager userFiveManager;

//    @GetMapping("/test")
//    public String getString() {
//        return "admin";
//    }

    @PostMapping("/user/create")
    public ResultVO createUser(@RequestBody @Valid RegisterForm registerForm,
                               BindingResult bindingResult,
                               HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.error("参数错误,用户注册失败");
            throw new ResultException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        userManager.createNewUser(registerForm);

        return ResultVOUtil.success(true);
    }

    @GetMapping("/trade/user")
    public ResultVO<List<TradeUserVO>> findTradeUsers() {

        return ResultVOUtil.success(userManager.findTradeUsers());
    }

    @GetMapping("/trade/list")
    public ResultVO<List<String>> findTradeList() {

        return ResultVOUtil.success(userManager.findTradeList());
    }

    @PostMapping("/user/info")
    public ResultVO<TradeUserVO> getUserInfo(@RequestBody UserInfoForm userInfoForm) {

        return ResultVOUtil.success(userManager.getUserInfo(userInfoForm.getUsername()));
    }

    @PostMapping("/update/chmod")
    public ResultVO updateChmod(@RequestBody ChmodForm chmodForm) {

        userManager.updateChmod(chmodForm);
        return ResultVOUtil.success(true);
    }

    @PostMapping("/change/pass")
    public ResultVO changePassword(@RequestBody ChangePassForm changePassForm) {

        userManager.changePassword(changePassForm);
        return ResultVOUtil.success(true);
    }

    @GetMapping("/average/{trade}")
    public ResultVO<List<AveragePriceVO>> getAveragePrice(@PathVariable String trade) {

        return ResultVOUtil.success(userThreeManager.getAveragePrice(trade));
    }

    @GetMapping("/day/{trade}")
    public ResultVO<List<DayCategoryVO>> findDaysAverageLog(@PathVariable String trade) {

        return ResultVOUtil.success(userFourManager.getDaysAverageLog(trade));
    }

    @GetMapping("/hour/{trade}")
    public ResultVO<List<ItemLogVO>> getLastLogList(@PathVariable String trade) {

        return ResultVOUtil.success(userFiveManager.getDayLog(trade));
    }

    @GetMapping("/total/{trade}")
    public ResultVO<List<PlatformTotalVO>> getTotal(@PathVariable String trade) {

        return ResultVOUtil.success(userFourManager.getDayTotal(trade));
    }

    @PostMapping("/name/list")
    public ResultVO<List<String>> getNameList(@RequestBody NameForm nameForm) {

        return ResultVOUtil.success(userFourManager.getNameList(nameForm));
    }

    @PostMapping("/extra")
    public ResultVO addExtraData(@RequestBody ExtraForm extraForm) {

        userManager.saveExtraLogDO(extraForm);

        return ResultVOUtil.success(true);
    }
}
