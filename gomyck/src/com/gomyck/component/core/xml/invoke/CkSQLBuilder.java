package com.gomyck.component.core.xml.invoke;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * SQL组装工具
 * 
 * @author medness
 *
 */
public abstract class CkSQLBuilder {
    
    private final static String FLAG = "#";
    
    private final static String REGEX = FLAG + "(.+?)(?=" + FLAG + ")";
    
    private final static ThreadLocal<Integer> TAG_NUM_CONTAINER = new ThreadLocal<Integer>();
    
    public static String initSql(String s, Object obj) {
        StringBuffer sql = new StringBuffer(s);
        return initSql(sql, null, obj).toString();
    }
    
    public static String initSql(String s, Class<?> clazz, Object obj) {
        StringBuffer sql = new StringBuffer(s);
        return initSql(sql, clazz, obj).toString();
    }
    
    private static StringBuffer initSql(StringBuffer s, Class<?> clazz, Object obj) {
        if (countStr(FLAG, s.toString()) % 2 != 0) {
            throw new RuntimeException("please check " + FLAG + "  " + FLAG + " flag num (is odd)");
        }
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(s);
        while (m.find()) {
            String paramName = s.substring(m.start() + 1, m.end());
            s.replace(m.start(), m.end() + 1, getEntityValue(clazz, obj, paramName));
            initSql(s, clazz, obj);
            break;
        }
        return s;
    }
    
    private static String getEntityValue(Class<?> clazz, Object obj, String paramName) {
        Assert.notNull(obj, "param entity must be not null");
        String fieldName = paramName;
        String headChar = fieldName.substring(0, 1);
        char secondChar = fieldName.substring(1, 2).toCharArray()[0];
        if (secondChar <= 64 || secondChar >= 90) {
            headChar = headChar.toUpperCase();
        }
        String getFieldValueFunc = "get" + headChar + fieldName.substring(1, fieldName.length());
        try {
            if (clazz != null) {
                clazz.cast(obj);
            }
            else {
                clazz = obj.getClass();
            }
        }
        catch (Exception e) {
            throw new RuntimeException("parameter type cast error..." + obj.getClass().toString() + " cast to" + clazz.toString());
        }
        Method method = null;
        Object paramValue = "";
        try {
            method = clazz.getMethod(getFieldValueFunc);
        }
        catch (SecurityException e) {
            throw new RuntimeException("security Exception...");
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("noSuchMethod: " + getFieldValueFunc + " in " + clazz.toString() + ", please check filed in xml: " + paramName);
        }
        try {
            paramValue = method.invoke(obj);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("illegalArgument Exception...please check method" + getFieldValueFunc + " in " + clazz.toString());
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("illegalAccess Exception...please check method" + getFieldValueFunc + " in " + clazz.toString());
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException("invocationTarget Exception... please check Object" + obj.getClass().toString() + " in " + clazz.toString());
        }
        return " '" + paramValue + "' ";
    }
    
    private static int countStr(String str, String strs) {
        int counter = 0;
        if (TAG_NUM_CONTAINER.get() == null || TAG_NUM_CONTAINER.get() == 0) {
            TAG_NUM_CONTAINER.set(0);
        }
        counter = TAG_NUM_CONTAINER.get();
        if (strs.indexOf(str) == -1) {
            return TAG_NUM_CONTAINER.get();
        }
        else {
            TAG_NUM_CONTAINER.set(counter + 1);
            countStr(str, strs.substring(strs.indexOf(str) + str.length()));
        }
        return TAG_NUM_CONTAINER.get();
    }
    
}
