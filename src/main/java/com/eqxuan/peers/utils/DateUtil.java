package com.eqxuan.peers.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: zheng guangjing.
 * @Date: 2018/8/9 12:29
 * @Description: 时间格式转换 Date转String
 */
public class DateUtil {
    public static String dateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
