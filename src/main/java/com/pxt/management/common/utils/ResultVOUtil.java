package com.pxt.management.common.utils;


import com.pxt.management.common.VO.ResultVO;
import com.pxt.management.common.exception.ResultException;

/**
 * @author tori
 * 2018/7/30 上午10:51
 */
public class ResultVOUtil {

    public static ResultVO success(Object content) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus("success");
        resultVO.setCode(0);
        resultVO.setContent(content);
        return resultVO;
    }

    public static ResultVO success() {
        return ResultVOUtil.success(null);
    }

    public static ResultVO error(Integer code, String status) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatus(status);
        resultVO.setCode(code);
        resultVO.setContent(null);
        return resultVO;
    }

}
