package com.base.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 時間日期工具類
 * @author zhejun
 *
 */
public class DateUtil {
    /**
     * 获取指定个月之后的事件
     */
    public static Date getAfterSomeMonth(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    /*
    * 获取一天的最后时间
    */
    public static Date getEndTimeOfDay(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.set(Calendar.HOUR_OF_DAY, 23);
        cl.set(Calendar.MINUTE, 59);
        cl.set(Calendar.SECOND, 59);
        cl.set(Calendar.MILLISECOND, 997);
        return cl.getTime();
    }

    /**
     * 获取一天的开始时间
     */
    public static Date getBeginTimeOfDay(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.set(Calendar.HOUR_OF_DAY, 00);
        cl.set(Calendar.MINUTE, 00);
        cl.set(Calendar.SECOND, 00);
        cl.set(Calendar.MILLISECOND, 0);
        return cl.getTime();
    }

    /**
     * 指定时间格式化
     */
    public static Date parase(String dateStr) {
        String style = "yyyy-MM-dd HH:mm:ss";
        return parase(dateStr, style);
    }

    /**
     * 字符串转换成相应格式的日期
     */
    public static Date parase(String dateStr, String style) {

        if (null == dateStr || "".equals(dateStr.trim())) {
            throw new IllegalArgumentException("the dateStr must not be empty");
        }

        if (null == style || "".equals(style.trim())) {
            throw new IllegalArgumentException("the style must not be empty");
        }

        Date date = null;

        SimpleDateFormat format = new SimpleDateFormat(style);
        try {
            date = format.parse(dateStr);
        } catch (java.text.ParseException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    /**
     * 指定年份累加几年
     */
    public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    /**
     * 指定月份累加几月
     */
    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 指定周累加几周
     */
    public static Date addWeeks(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    /**
     * 指定日期累加几天
     */
    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DATE, amount);
    }

    /**
     * 指定时间累加几小时
     */
    public static Date addHours(Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 指定时间累加几分钟
     */
    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    /**
     * 指定时间加上给定的毫秒数
     */
    public static Date addSeconds(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    /**
     * 指定时间累加多少毫秒
     */
    public static Date addMilliseconds(Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    /**
     * 指定时间累加时间
     */
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(calendarField, amount);
            return c.getTime();
        }
    }

    /**
     * 按默认格式返回字符串日期
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getStrDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 计算查询条件的时间维度  今天 昨天 七天前 30天前 本月
     *
     * @param time
     * @param queryMap 开始时间 startTime 结束时间 endTime
     */
    public static void getTime(Integer time, Map<String, Object> queryMap) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date startTime = null;
        Date endTime = null;
        switch (time) {
            case 1: { //今天
                calendar.setTime(new Date());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                startTime = calendar.getTime();
                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
                endTime = calendar.getTime();
                break;
            }
            case 2: { //昨天
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                startTime = calendar.getTime();
                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
                endTime = calendar.getTime();
                break;
            }
            case 3: { //最近7天
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                endTime = calendar.getTime();
                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 7);
                startTime = calendar.getTime();
                break;
            }
            case 4: { //最近30天
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                endTime = calendar.getTime();
                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 30);
                startTime = calendar.getTime();
                break;
            }
            case 5: { //当月
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                endTime = calendar.getTime();
                calendar.set(Calendar.DAY_OF_MONTH, 0);
                startTime = calendar.getTime();
                break;
            }
        }
        queryMap.put("startTime", format.format(startTime));
        queryMap.put("endTime", format.format(endTime));
    }


    /**
     * 获取两个时间间隔天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getDaysBetween(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        Integer day = (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
        return day;
    }
}
