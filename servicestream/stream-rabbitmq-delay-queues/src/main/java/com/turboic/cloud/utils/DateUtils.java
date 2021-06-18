package com.turboic.cloud.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liebe
 * 时间工具类
 */
public class DateUtils {

    private  final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /***
     * Date类型转换成字符串
     * @param date
     * @return
     */
    public static String dateConvertString(Date date){
        return format.format(date);
    }
}
