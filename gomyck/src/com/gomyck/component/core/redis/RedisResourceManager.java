package com.gomyck.component.core.redis;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;

import com.gomyck.component.context.spring.AOPMethodUtil;
import com.gomyck.component.core.redis.annotation.RedisManager;
import com.gomyck.component.core.redis.utils.RedisCache;


public class RedisResourceManager { 
    
    public void startDoIt(final JoinPoint joinPoint)
        throws Exception {
        Method pointMethod = AOPMethodUtil.getPointMethod(joinPoint);
        if (pointMethod.isAnnotationPresent(RedisManager.class)) {
            RedisManager rm = pointMethod.getAnnotation(RedisManager.class);
            RedisCache.startDoIt();
            RedisCache.selectDB(rm.dbIndex());
        }
    }
    
    public void finishDoIt(final JoinPoint joinPoint) 
        throws Exception {
        Method pointMethod = AOPMethodUtil.getPointMethod(joinPoint);
        if (pointMethod.isAnnotationPresent(RedisManager.class)) {
            RedisCache.finishDoIt();
        }
    }
    
}
