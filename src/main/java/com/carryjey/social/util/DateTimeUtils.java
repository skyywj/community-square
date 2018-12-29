package com.carryjey.social.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * 日期时间相关工具类
 *
 * @author CarryJey
 * @since 2018-12-27
 */
public abstract class DateTimeUtils {

    private DateTimeUtils() {}

    /**
     * epoch milli to LocalDateTime, use UTC zone offset
     */
    public static LocalDateTime milliToUtcLdt(long millis) {
        return milliToLdt(millis, ZoneOffset.UTC);
    }

    /**
     * epoch milli to LocalDateTime
     */
    public static LocalDateTime milliToLdt(long millis, ZoneId zoneId) {
        return Instant.ofEpochMilli(millis).atZone(zoneId).toLocalDateTime();
    }

    /**
     * LocalDateTime to epoch milli, use UTC zone offset
     */
    public static long utcLdtToMilli(LocalDateTime localDateTime) {
        return ldtToMilli(localDateTime, ZoneOffset.UTC);
    }

    /**
     * LocalDateTime to epoch milli
     */
    public static long ldtToMilli(LocalDateTime localDateTime, ZoneId zoneId) {
        if (localDateTime == null) {
            throw new NullPointerException();
        }
        return localDateTime.atZone(zoneId).toInstant().toEpochMilli();
    }

    /**
     * LocalDateTime with utc zone offset
     */
    public static LocalDateTime utcNow() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}
