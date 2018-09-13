package com.pxt.management.common.handler;

import com.pxt.management.common.VO.ResultVO;
import com.pxt.management.common.exception.ResultException;
import com.pxt.management.common.utils.ResultVOUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author tori
 * @description
 * @date 2018/8/25 下午4:03
 */

@ControllerAdvice
public class ResultExceptionHandler {

    @ExceptionHandler(value = ResultException.class)
    @ResponseBody
    public ResultVO handlerSchoolException(ResultException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
