package com.gomyck.component.core.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * redis资源管理器开启注解
 * 
 * @author 郝洋 QQ:474798383
 * @version [版本号, 2017年7月5日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisManager {
    
    public int dbIndex() default 0;
    
}
