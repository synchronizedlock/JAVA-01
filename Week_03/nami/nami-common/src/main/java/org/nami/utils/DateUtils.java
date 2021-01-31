package org.nami.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateUtils
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatToYYYYMMDDHHmmss(LocalDateTime dateTime){
        return dateTime.format(FORMATTER);
    }
}
