package com.pxt.management.web.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pxt.management.common.exception.ResultException;
import com.pxt.management.common.utils.Blowfish;
import com.pxt.management.common.utils.KeyUtil;
import com.pxt.management.common.utils.SecurityUtil;
import com.pxt.management.common.utils.TimeFormatUtil;
import com.pxt.management.enums.ResultEnum;
import com.pxt.management.web.VO.TradeUserVO;
import com.pxt.management.web.dao.*;
import com.pxt.management.web.dataobject.*;
import com.pxt.management.web.form.ChangePassForm;
import com.pxt.management.web.form.ChmodForm;
import com.pxt.management.web.form.ExtraForm;
import com.pxt.management.web.form.RegisterForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tori
 * 2018/8/12 下午10:56
 */

@Slf4j
@Service
public class UserManager {

    @Autowired
    private UserJpaDAO userJpaDAO;

    @Autowired
    private ItemLogJpaDAO itemLogJpaDAO;

    @Autowired
    private DataDetailJpaDAO dataDetailJpaDAO;

    @Autowired
    private AveragePriceLogJpaDAO averagePriceLogJpaDAO;

    @Autowired
    private DataDetailEveryJpaDAO dataDetailEveryJpaDAO;

    @Autowired
    private ExtraLogJpaDAO extraLogJpaDAO;

    public UserDO findByUsername(String username) {
        return userJpaDAO.findByUsername(username);
    }

    //@Transactional
    public void saveItemLog(String param) {
//        try {
        JSONObject jsonObject = JSON.parseObject(param);
        String dataId = KeyUtil.genUniqueKey();
        ItemLogDO itemLogDO = new ItemLogDO();
        String account = jsonObject.getString("account");
        String platform = jsonObject.getString("platform");
        Date time = TimeFormatUtil.getSomeHour(jsonObject.getLong("time"));
        String trade = jsonObject.getString("trade");
        itemLogDO.setAccount(account);
        itemLogDO.setDataId(dataId);
        itemLogDO.setPlatform(platform);
        itemLogDO.setTime(time);
        itemLogDO.setTrade(trade);
        BigDecimal calculateA = BigDecimal.ZERO;
        BigDecimal calculateB = BigDecimal.ZERO;

        ItemLogDO beforeItemLog = itemLogJpaDAO
                .findFirstByAccountAndPlatformAndTradeAndTime(account, platform, trade, TimeFormatUtil.getBeforeHour(time));
        //log.info("before = {}", beforeItemLog);
        if (beforeItemLog == null || dataDetailJpaDAO.findByDataIdOrderByName(beforeItemLog.getDataId()) == null) {
            JSONArray array = jsonObject.getJSONArray("data");
            for (int i = 0; i < array.size(); i++) {
                JSONObject json = (JSONObject) array.get(i);
                DataDetailDO dataDetailDO = new DataDetailDO();
                String name = json.getString("name");
                BigDecimal rate = json.getBigDecimal("rate");
                BigDecimal value = json.getBigDecimal("value");
                dataDetailDO.setDataId(dataId);
                dataDetailDO.setName(name);
                dataDetailDO.setRate(rate);
                dataDetailDO.setValue(value);
                dataDetailDO.setTime(time);
                dataDetailDO.setHoldChange(new BigDecimal(0));
                dataDetailJpaDAO.save(dataDetailDO);
                if (name.equals(trade)) {
                    calculateB = rate.multiply(value);
                } else {
                    calculateA = calculateA.add(rate.multiply(value));
                }
            }
            itemLogDO.setCalculateB(calculateB);
            itemLogDO.setCalculateA(calculateA);
            itemLogDO.setAverage(BigDecimal.ZERO);
        } else {
            log.info("dataId = {}", beforeItemLog.getDataId());
            List<DataDetailDO> dataDetailDOList = dataDetailJpaDAO.findByDataIdOrderByName(beforeItemLog.getDataId());
            log.info("dataDetailDOList = {}", dataDetailDOList);
            JSONArray array = jsonObject.getJSONArray("data");
            for (int i = 0; i < array.size(); i++) {
                JSONObject json = (JSONObject) array.get(i);
                DataDetailDO dataDetailDO = new DataDetailDO();
                dataDetailDO.setHoldChange(BigDecimal.ZERO);
                String name = json.getString("name");
                BigDecimal rate = json.getBigDecimal("rate");
                BigDecimal value = json.getBigDecimal("value");
                log.info("rate={}, value={}, name={}", rate, value, name);
                //记录持仓变动值
                for (DataDetailDO dataDetail : dataDetailDOList) {
                    if (dataDetail.getName().equals(name)) {
                        log.info("sub = {}", value.subtract(dataDetail.getValue()));
                        dataDetailDO.setHoldChange(value.subtract(dataDetail.getValue()));
                        break;
                    }
                }
                dataDetailDO.setDataId(dataId);
                dataDetailDO.setName(name);
                dataDetailDO.setRate(rate);
                dataDetailDO.setValue(value);
                dataDetailDO.setTime(time);
                dataDetailJpaDAO.save(dataDetailDO);
                if (name.equals(trade)) {
                    calculateB = rate.multiply(value);
                } else {
                    calculateA = calculateA.add(rate.multiply(value));
                }
            }
            itemLogDO.setCalculateA(calculateA);
            itemLogDO.setCalculateB(calculateB);
            if (beforeItemLog.getCalculateA() != null && beforeItemLog.getCalculateB() != null) {
                BigDecimal fenZi = beforeItemLog.getCalculateA().subtract(calculateA);
                BigDecimal fenMu = beforeItemLog.getCalculateB().subtract(calculateB);
                try {
                    itemLogDO.setAverage(fenZi.divide(fenMu, 9, BigDecimal.ROUND_HALF_EVEN));
                } catch (Exception e) {
                    itemLogDO.setAverage(BigDecimal.ZERO);
                }
            }
        }

        if (TimeFormatUtil.isDefaultTime(time)) {

            Date earlyDay = TimeFormatUtil.earlyDayDefaultTime(time);

            ItemLogDO itemLog = itemLogJpaDAO.findFirstByAccountAndPlatformAndTradeAndTime(
                            account, platform, trade, earlyDay);
            if (itemLog != null && itemLog.getCalculateA() != null && itemLog.getCalculateB() != null) {
                AveragePriceLogDO averagePriceLogDO = new AveragePriceLogDO();
                averagePriceLogDO.setAccount(account);
                averagePriceLogDO.setPlatform(platform);
                averagePriceLogDO.setTrade(trade);
                averagePriceLogDO.setTime(earlyDay);
                averagePriceLogDO.setDataId(dataId);

                List<DataDetailDO> dataDetailDOEarly = dataDetailJpaDAO.findByDataIdOrderByName(itemLog.getDataId());
                List<DataDetailDO> dataDetailDONow = dataDetailJpaDAO.findByDataIdOrderByName(itemLogDO.getDataId());
                for (DataDetailDO now : dataDetailDONow) {
                    DataDetailEveryDO dataDetailEveryDO = new DataDetailEveryDO();
                    for (DataDetailDO early : dataDetailDOEarly) {
                        if (early.getName().equals(now.getName())) {
                            dataDetailEveryDO.setDataId(dataId);
                            dataDetailEveryDO.setName(now.getName());
                            dataDetailEveryDO.setValue(now.getValue());
                            dataDetailEveryDO.setHoldChange(now.getValue().subtract(early.getValue()));
                            dataDetailEveryDO.setTime(earlyDay);
                            dataDetailEveryJpaDAO.save(dataDetailEveryDO);
                            break;
                        }
                    }
                }

                BigDecimal fenZi = itemLog.getCalculateA().subtract(itemLogDO.getCalculateA());
                BigDecimal fenMu = itemLog.getCalculateB().subtract(itemLogDO.getCalculateB());
                try {
                    averagePriceLogDO.setAveragePrice(fenZi.divide(fenMu, 9, BigDecimal.ROUND_HALF_EVEN));
                } catch (Exception e) {
                    averagePriceLogDO.setAveragePrice(BigDecimal.ZERO);
                }
                averagePriceLogJpaDAO.save(averagePriceLogDO);
            }
        }

        itemLogJpaDAO.save(itemLogDO);
//        } catch (Exception e) {
//            log.info("json转换错误，itemLog={}", param);
//            throw new ResultException(ResultEnum.PARAM_ERROR);
//        }
    }

    public void createNewUser(RegisterForm registerForm) {

        if (null != userJpaDAO.findByUsername(registerForm.getUsername())) {
            throw new ResultException(ResultEnum.PARAM_ERROR.getCode(), "用户名重复,请换一个");
        }

        UserDO user = new UserDO();
        BeanUtils.copyProperties(registerForm, user);
        String encodePwd;
        try {
            encodePwd = Blowfish.encode(registerForm.getPassword());
        } catch (Exception e) {
            log.error("密码加密失败");
            throw new ResultException(ResultEnum.ENCODE_ERROR);
        }
        user.setPassword(encodePwd);
        user.setType(SecurityUtil.integer2Enum(user.getIdentity()));
        userJpaDAO.save(user);
    }

    /**
     * 找到所有的用户列表
     * @return
     */
    public List<TradeUserVO> findTradeUsers() {

        List<TradeUserVO> tradeUserVOList = userJpaDAO.findTradeUser().stream()
                .map(userDO -> {
                    TradeUserVO tradeUserVO = new TradeUserVO();
                    BeanUtils.copyProperties(userDO, tradeUserVO);
                    return tradeUserVO;
                }).collect(Collectors.toList());

        return tradeUserVOList;
    }

    public List<String> findTradeList() {

        return userJpaDAO.findTradeList();
    }

    public TradeUserVO getUserInfo(String username) {

        UserDO userDO = userJpaDAO.findByUsername(username);
        TradeUserVO tradeUserVO = new TradeUserVO();
        BeanUtils.copyProperties(userDO, tradeUserVO);
        return tradeUserVO;
    }

    public void updateChmod(ChmodForm chmodForm) {

        UserDO userDO = userJpaDAO.findByUsername(chmodForm.getUsername());
        if (userDO == null) {
            throw new ResultException(ResultEnum.PARAM_ERROR.getCode(), "找不到该用户");
        }
        if (chmodForm.getIdentity() != null && chmodForm.getIdentity() < 7) {
            userDO.setIdentity(chmodForm.getIdentity());
            userDO.setType(SecurityUtil.integer2Enum(chmodForm.getIdentity()));
        }
        if (!StringUtils.isEmpty(chmodForm.getTrade())) {
            userDO.setTrade(chmodForm.getTrade());
        }
        userJpaDAO.save(userDO);
    }

    public void changePassword(ChangePassForm changePassForm) {

        UserDO userDO = userJpaDAO.findByUsername(changePassForm.getUsername());
        if (userDO == null) {
            throw new ResultException(ResultEnum.PARAM_ERROR.getCode(), "找不到该用户");
        }
        String encodePwd = null;
        try {
            encodePwd = Blowfish.encode(changePassForm.getNewPassword());
        } catch (Exception e) {
            log.error("密码加密失败");
            throw new ResultException(ResultEnum.ENCODE_ERROR);
        }
        userDO.setPassword(encodePwd);
        userJpaDAO.save(userDO);
    }

    public void saveExtraLogDO(ExtraForm extraForm) {

        String trade = extraForm.getTrade();
        String platform = extraForm.getPlatform();
        String account = extraForm.getAccount();
        String time = extraForm.getTime();
        String name = extraForm.getName();
        BigDecimal value = extraForm.getValue();
        BigDecimal rate = extraForm.getRate();
        AveragePriceLogDO averagePriceLogDO = averagePriceLogJpaDAO.findExistLog(trade, platform, account, time);
        Date x = TimeFormatUtil.getTodayDefaultHour(time);
        ItemLogDO itemLog = itemLogJpaDAO.findFirstByAccountAndPlatformAndTradeAndTime(account, platform, trade, x);
        if (null != itemLog) {
            if (name.equals(trade)) {
                itemLog.setCalculateB(itemLog.getCalculateB().add(rate.multiply(value)));
            } else {
                itemLog.setCalculateA(itemLog.getCalculateA().add(rate.multiply(value)));
            }
            itemLogJpaDAO.save(itemLog);
        }
        if (null != averagePriceLogDO) {
            Date y = TimeFormatUtil.getAfterDefaultHour(time);
            ItemLogDO itemLogDO = itemLogJpaDAO.findFirstByAccountAndPlatformAndTradeAndTime(account, platform, trade, y);

            if (null != itemLogDO && null != itemLog) {
                BigDecimal fenZi = itemLog.getCalculateA().subtract(itemLogDO.getCalculateA());
                BigDecimal fenMu = itemLog.getCalculateB().subtract(itemLogDO.getCalculateB());
                try {
                    averagePriceLogDO.setAveragePrice(fenZi.divide(fenMu, 9, BigDecimal.ROUND_HALF_EVEN));
                } catch (Exception e) {
                    averagePriceLogDO.setAveragePrice(BigDecimal.ZERO);
                }
                averagePriceLogJpaDAO.save(averagePriceLogDO);
            }
        }
        ExtraLogDO extraLogDO = extraLogJpaDAO.getExtraLog(trade, platform, account, name, time);
        if (null == extraLogDO) {
            extraLogDO = new ExtraLogDO();
            BeanUtils.copyProperties(extraForm, extraLogDO);
        } else {
            extraLogDO.setValue(extraLogDO.getValue().add(value));
        }
        extraLogJpaDAO.save(extraLogDO);
    }
}
