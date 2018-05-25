package com.java1234.util;

/**
 * Created by Administrator on 2018/1/30.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String MM_DD_HH_MM = "MM-dd HH:mm";
    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public DateUtil() {
    }

    public static String format(Date date, String format) {
        return (new SimpleDateFormat(format)).format(date);
    }

    public static String format(Long timpstamp, String format) {
        return (new SimpleDateFormat(format)).format(new Date(timpstamp.longValue()));
    }

    public static Long date2Long(Date date) {
        return date == null?null:Long.valueOf(date.getTime());
    }

    public static Date toDate(String dataStr, String format) {
        try {
            return (new SimpleDateFormat(format)).parse(dataStr);
        } catch (ParseException var3) {
            logger.error("toDate error", var3);
            return null;
        }
    }

    public static Date getYesterdayDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, -1);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(format(toDate("20147", "yyyyM"), "yyyy-MM-dd"));
        System.out.println(format(toDate("201412", "yyyyM"), "yyyy-MM-dd"));
    }
}
