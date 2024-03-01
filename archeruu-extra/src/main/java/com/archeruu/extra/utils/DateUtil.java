package com.archeruu.extra.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author Archer
 */
public class DateUtil {

    /**
     * 返回string格式
     * @param dt 时间
     * @param format 格式
     * @return 日期
     * @throws ParseException 解析异常
     */
    public static Date parseDateTime(String dt, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(dt);
    }
}
