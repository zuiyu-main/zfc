package com.zuiyu.boot.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author zuiyu
 * @Date 2023/4/5 12:18
 */
public class ZDateUtils {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String LocalDateTime2String(LocalDateTime localDateTime){
        return localDateTime.format(formatter);
    }
}
