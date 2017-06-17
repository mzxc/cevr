package com.cevr.component.util;

import javax.servlet.http.HttpServletRequest;

/**
 * IpUtils工具类
 * 
 * @author lcj
 * @version 2017-04-23
 */
public class IpUtil {
    
    // /**
    // * 从客户端请求取得真实Ip地址
    // * @param request
    // */
    // public static String getRemoteAddr(HttpServletRequest request){
    // String ip = request.getHeader("x-forwarded-for");
    // if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    // ip = request.getHeader("Proxy-Client-IP");
    // }
    // if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    // ip = request.getHeader("WL-Proxy-Client-IP");
    // }
    // if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
    // ip = request.getRemoteAddr();
    // }
    // return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    // }
    
    /**
     * 获取访问者IP
     * 
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * 
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)， 如果还不存在则调用Request .getRemoteAddr()。
     * 
     * @param request
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtil.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            }
            else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!StringUtil.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
    
}
