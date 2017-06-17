package com.cevr.business.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 日志注解
 * 
 * @author 郝洋
 * @see LogInfo
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogInfo {
    
    /**
     * 
     * 操作描述
     * 
     * @return String String
     */
    String operateFuncNm();
    
    /**
     * 
     * 模块名称
     * 
     * @return String String
     */
    String operateModelNm();
}