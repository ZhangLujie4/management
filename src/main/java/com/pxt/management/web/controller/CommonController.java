package com.pxt.management.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pxt.management.common.VO.ResultVO;
import com.pxt.management.common.dataobject.SecurityUser;
import com.pxt.management.common.dataobject.UserAuthentication;
import com.pxt.management.common.exception.ResultException;
import com.pxt.management.common.token.TokenProvider;
import com.pxt.management.common.utils.Blowfish;
import com.pxt.management.common.utils.ResultVOUtil;
import com.pxt.management.common.utils.SecurityUtil;
import com.pxt.management.enums.ResultEnum;
import com.pxt.management.web.dataobject.UserDO;
import com.pxt.management.web.form.LoginForm;
import com.pxt.management.web.manager.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tori
 * 2018/8/12 下午10:49
 */

@Slf4j
@RestController
@RequestMapping("/api/common")
public class CommonController {


    @Autowired
    private UserManager userManager;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResultVO login(@RequestBody LoginForm loginForm) throws Exception {
        UserDO userDO = userManager.findByUsername(loginForm.getUsername());
        if (null == userDO) {
            throw new ResultException(ResultEnum.LOGIN_FAIL);
        }

        String encodePwd = Blowfish.encode(loginForm.getPassword());
        if (!userDO.getPassword().equals(encodePwd)) {
            throw new ResultException(ResultEnum.LOGIN_FAIL);
        }

        Map<String, Object> map = new HashMap<>();
        UserAuthentication userAuthentication = new UserAuthentication(SecurityUtil.convertUser(userDO));
        String token = tokenProvider.createToken(userAuthentication);
        map.put("authorization", token);
        map.put("identity", userDO.getIdentity());
        return ResultVOUtil.success(map);
    }

    @GetMapping("/current")
    public ResultVO<SecurityUser> getCurrentUser() {
        return ResultVOUtil.success(SecurityUtil.getCurrentUser());
    }

    @PostMapping("/python")
    public ResultVO savePythonLog(@RequestBody String param) {

        userManager.saveItemLog(param);

        return ResultVOUtil.success(true);
    }

    @GetMapping("/encode/{password}")
    public ResultVO<String> encodePwd(@PathVariable String password) {
        String encodePwd = null;
        try {
            encodePwd = Blowfish.encode(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success(encodePwd);
    }

    @GetMapping("/decode/{password}")
    public ResultVO<String> decodePwd(@PathVariable String password) {
        String decodePwd = null;
        try {
            decodePwd = Blowfish.decode(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultVOUtil.success(decodePwd);
    }
}
