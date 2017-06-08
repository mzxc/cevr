package com.cevr.component.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 时间工具类
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-30]
 * @see #DateUtil()
 * @since 1.0
 */
public class DateUtil
{
    public interface DUF
    {
        public static final String CN_DATE_FORMAT = "yyyy-MM-dd";
        
        public static final String CN_SHORT_DATE_FORMAT = "yy-MM-dd";
        
        public static final String CN_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        
        public static final String CN_DATETIME_WITH_MILLISECOND_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
        
        public static final String ISO_TIME_FORMAT = "HH:mm:ss";
        
        public static final String ISO_TIME_WITH_MILLISECOND_FORMAT = "HH:mm:ss.SSS";
        
        public static final String US_DATE_FORMAT = "MM/dd/yyyy";
        
        public static final String US_SHORT_DATE_FORMAT = "MM/dd/yy";
        
        public static final String YYYY_MM_dd = "yyyy-MM-dd";
    }
    
    /**
     * 获取当前时间 带时分秒
     * 
     * @return Date
     */
    public static Date nowDate(final String formatter)
    {
        String str = "";
        final SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        Date date = new Date();
        try
        {
            str = sdf.format(date);
            date = sdf.parse(str);
        }
        catch (final ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 获取当前时间 带时分秒
     * 
     * @return String
     */
    public static String nowStr(final String formatter)
    {
        String str = "";
        final SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        final Date date = new Date();
        str = sdf.format(date);
        return str;
    }
    
    /**
     * 获取该日期为星期几
     */
    public static String getWeek(final Date date)
    {
        final String[] weekDays = {"SUNDAY", "MONDAY", "TUESDAY", "WEDENSDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    
    /**
     * 时间格式化
     * 
     * @param strDate String类型的时间
     * @param fromFormat 格式化类型
     * @return Date
     */
    public static Date getDate(final String strDate, final String fromFormat)
    {
        final DateFormat f = new SimpleDateFormat(fromFormat);
        Date date = null;
        try
        {
            date = f.parse(strDate);
        }
        catch (final ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 将Date型时间改为String类型，并格式化
     * 
     * @param date
     * 
     * @param fromFormat 时间格式化类型
     * @return String
     */
    public static String getStringDate(final Date date, final String fromFormat)
    {
        final DateFormat f = new SimpleDateFormat(fromFormat);
        return f.format(date);
    }
    
    /**
     * 获取当年年份
     * 
     * @return
     */
    public static String getYear()
    {
        String str = "";
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        final Date date = new Date();
        str = sdf.format(date);
        return str;
    }
    
    public static String monthAdd(final String strDate, final int month)
    {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try
        {
            date = simpleDateFormat.parse(strDate);
            final Calendar calender = Calendar.getInstance();
            calender.setTime(date);
            calender.add(Calendar.MONTH, month);
            return simpleDateFormat.format(calender.getTime());
        }
        catch (final ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
