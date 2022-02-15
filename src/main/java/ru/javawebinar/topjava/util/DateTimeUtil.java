package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// fixme: костыль
public class DateTimeUtil {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(LocalDateTime lt, T startTime, T endTime) {
        if (startTime == null || endTime == null)
            return true;

        if (startTime instanceof LocalDateTime)
            return isBetweenLocalDate(lt.toLocalDate(), toLocalDate(startTime), toLocalDate(endTime))
                    && isBetweenLocalTime(lt.toLocalTime(), toLocalTime(startTime), toLocalTime(endTime));
        else if (startTime instanceof LocalDate)
            return isBetweenLocalDate(lt.toLocalDate(), (LocalDate) startTime, (LocalDate) endTime);
        else
            return isBetweenLocalTime(lt.toLocalTime(), (LocalTime) startTime, (LocalTime) endTime);
    }

    // fixme: костыль
    public static LocalDateTime convertToLocalDateTime(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    public static LocalDate parseLocalDate(String date, boolean start) {
        if (start)
            return date.equals("") ? LocalDate.MIN : LocalDate.parse(date);
        else
            return date.equals("") ? LocalDate.MAX : LocalDate.parse(date);
    }

    public static LocalTime parseLocalTime(String time, boolean start) {
        if (start)
            return time.equals("") ? LocalTime.MIN : LocalTime.parse(time);
        else
            return time.equals("") ? LocalTime.MAX : LocalTime.parse(time);
    }

    private static boolean isBetweenLocalTime(LocalTime current, LocalTime from, LocalTime to) {
        return current.compareTo(from) >= 0 && current.compareTo(to) < 0;
    }

    private static boolean isBetweenLocalDate(LocalDate current, LocalDate from, LocalDate to) {
        return current.compareTo(from) >= 0 && current.compareTo(to) <= 0;
    }

    private static <T> LocalDate toLocalDate(T time) {
        return ((LocalDateTime) time).toLocalDate();
    }

    private static <T> LocalTime toLocalTime(T time) {
        return ((LocalDateTime) time).toLocalTime();
    }

    public static String toString(LocalDateTime localDateTime) {
        return FORMATTER.format(localDateTime);
    }
}
