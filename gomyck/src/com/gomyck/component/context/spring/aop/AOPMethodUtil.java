package com.gomyck.component.context.spring.aop;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;

/**
 * 
 * 获取切面方法工具类
 * 
 * @author   郝洋 QQ:474798383
 * @version  [版本号, 2017年7月5日]
 * @see      [相关类/方法]
 * @since    [产品/模块版本]
 */
public class AOPMethodUtil {
    
    public static Method getPointMethod(JoinPoint joinPoint)
        throws Exception {
        final String temp = joinPoint.getStaticPart().toShortString();
        final String longTemp = joinPoint.getStaticPart().toLongString();
        joinPoint.getStaticPart().toString();
        final String classType = joinPoint.getTarget().getClass().getName();
        final String methodName = temp.substring(10, temp.length() - 1);
        final Class<?> className = Class.forName(classType);
        final Class<?>[] args = new Class[joinPoint.getArgs().length];
        final String[] sArgs = (longTemp.substring(longTemp.lastIndexOf("(") + 1, longTemp.length() - 2)).split(",");
        for (int i = 0; i < args.length; i++) {
            String argName = sArgs[i];
            if (argName.endsWith("[]")) {
                args[i] = Array.newInstance(Class.forName(argName.substring(0, argName.length() - 2)), 1).getClass();
            }
            else if (argName.indexOf(".") == -1) {
                args[i] = isBaseType(argName);
            }
            else {
                args[i] = Class.forName(argName);
            }
        }
        final Method method = className.getMethod(methodName.substring(methodName.indexOf(".") + 1, methodName.indexOf("(")), args);
        return method;
    }
    
    private static Class<?> isBaseType(String typeName) {
        final Object[][] baseTypes = {{"byte", byte.class}, {"short", short.class}, {"char", char.class}, {"int", int.class}, {"long", long.class}, {"float", float.class}, {"double", double.class}, {"boolean", boolean.class}};
        for (int i = 0; i < baseTypes.length; i = i + 1) {
            Object[] type = baseTypes[i];
            if (type[0].equals(typeName)) {
                return (Class<?>)type[1];
            }
        }
        return null;
    }
}
