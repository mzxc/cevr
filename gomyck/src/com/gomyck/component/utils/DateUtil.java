package com.gomyck.component.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 
 * 时间工具类
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-30]
 * @see #DateUtil()
 * @since 1.0
 */
public class DateUtil {
    
    public interface DUF {
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
    public static Date now2Date(final String formatter) {
        String str = "";
        final SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        Date date = new Date();
        try {
            str = sdf.format(date);
            date = sdf.parse(str);
        }
        catch (final ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 获取当前时间 带时分秒
     * 
     * @return String
     */
    public static String now2Str(final String formatter) {
        String str = "";
        final SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        final Date date = new Date();
        str = sdf.format(date);
        return str;
    }
    
    /**
     * 获取该日期为星期几
     */
    public static String getWeek(final Date date) {
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
    public static Date convertStr2Date(final String strDate, final String fromFormat) {
        final DateFormat f = new SimpleDateFormat(fromFormat);
        Date date = null;
        try {
            date = f.parse(strDate);
        }
        catch (final ParseException e) {
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
    public static String convertDate2Str(final Date date, final String fromFormat) {
        final DateFormat f = new SimpleDateFormat(fromFormat);
        return f.format(date);
    }
    
    /**
     * 获取当年年份
     * 
     * @return
     */
    public static String getYear() {
        String str = "";
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        final Date date = new Date();
        str = sdf.format(date);
        return str;
    }
    
    public static String monthAdd(final String strDate, final int month) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = simpleDateFormat.parse(strDate);
            final Calendar calender = Calendar.getInstance();
            calender.setTime(date);
            calender.add(Calendar.MONTH, month);
            return simpleDateFormat.format(calender.getTime());
        }
        catch (final ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 
     * 获取date是星期几
     * 
     * @param date
     * @return 星期x
     * @see [类、类#方法、类#成员]
     */
    public static String getDay(String date) {
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
        final String pattern = "星期";
        String ret = "";
        int day = 0;
        String zhou = null;
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sformat.parse(date));
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        day = cal.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                zhou = "日";
                break;
            case 2:
                zhou = "一";
                break;
            case 3:
                zhou = "二";
                break;
            case 4:
                zhou = "三";
                break;
            case 5:
                zhou = "四";
                break;
            case 6:
                zhou = "五";
                break;
            case 7:
                zhou = "六";
                break;
            default:
                zhou = "日";
        }
        ret = pattern + zhou;
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    private static List getYmd(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List list = new ArrayList();
        try {
            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);
            Calendar c = Calendar.getInstance();
            c.setTime(d1);
            list.add(sdf.format(c.getTime()));
            while (!c.getTime().equals(d2)) {
                c.add(Calendar.DATE, 1);
                list.add(sdf.format(c.getTime()));
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * 工作日与非工作日、星期计算
     * 
     * @param year 年份
     * @return
     */
    public static List<String> getWeekends(int year) {
        List<String> list = new ArrayList<String>();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.set(year, 0, 1);
        for (int day = 1; day <= cal.getActualMaximum(Calendar.DAY_OF_YEAR); day++) {
            cal.set(Calendar.DAY_OF_YEAR, day);
            int weekDay = cal.get(Calendar.DAY_OF_WEEK);
            if (weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY) {
                list.add(sdf.format(cal.getTime()) + " " + getDay(sdf.format(cal.getTime())));
            }
            else {
                list.add(sdf.format(cal.getTime()) + " " + getDay(sdf.format(cal.getTime())));
            }
        }
        return list;
    }
}
