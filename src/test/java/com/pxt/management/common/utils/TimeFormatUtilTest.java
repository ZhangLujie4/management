package com.pxt.management.common.utils;

import com.pxt.management.ManagementApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

/**
 * @author tori
 * 2018/8/13 下午2:00
 */

@Slf4j
public class TimeFormatUtilTest extends ManagementApplicationTests {

    @Test
    public void getBeforeHour() {

        try {
            log.info("beforeHour = {}", TimeFormatUtil.getBeforeHour(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-01-02 00:00:00")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}