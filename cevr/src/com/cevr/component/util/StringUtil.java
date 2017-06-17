package com.cevr.component.util;

public class StringUtil {
    
    /**
     * 判断字符串是否为空或null,是则返回true,否则返回false
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(final String str) {
        if (str != null && !"".equals(str) && str.trim().length() > 0) {
            return false;
        }
        return true;
    }
    
    /**
     * 
     * 如果字符串是null则返回空串""
     * 
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String nullToStr(final Object obj) {
        
        if (obj == null) {
            return "";
        }
        final String str = obj.toString();
        return str.trim();
    }
    
    public static String strTrim(final String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.trim();
    }
}
