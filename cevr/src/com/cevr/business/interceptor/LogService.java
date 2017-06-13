package com.cevr.business.interceptor;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cevr.component.logger.NestLogger;
import com.cevr.component.util.DateUtil;
import com.cevr.component.util.DateUtil.DUF;
import com.cevr.component.util.IpUtil;

/**
 * 
 * 日志切面
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-30]
 * @see #LogService
 * @since 1.0
 */
public class LogService
{
    private static final Logger logger = Logger.getLogger(LogService.class);
    
    @SuppressWarnings("rawtypes")
    public void logIt(final JoinPoint joinPoint)
    {
        try
        {
            final String temp = joinPoint.getStaticPart().toShortString();
            final String longTemp = joinPoint.getStaticPart().toLongString();
            joinPoint.getStaticPart().toString();
            final String classType = joinPoint.getTarget().getClass().getName();
            final String methodName = temp.substring(10, temp.length() - 1);
            final Class<?> className = Class.forName(classType);
            final Class[] args = new Class[joinPoint.getArgs().length];
            final String[] sArgs = (longTemp.substring(longTemp.lastIndexOf("(") + 1, longTemp.length() - 2)).split(",");
            for (int i = 0; i < args.length; i++)
            {
                if (sArgs[i].endsWith("String[]"))
                {
                    args[i] = Array.newInstance(Class.forName("java.lang.String"), 1).getClass();
                }
                else if (sArgs[i].endsWith("Long[]"))
                {
                    args[i] = Array.newInstance(Class.forName("java.lang.Long"), 1).getClass();
                }
                else if (sArgs[i].indexOf(".") == -1)
                {
                    if (sArgs[i].equals("int"))
                    {
                        args[i] = int.class;
                    }
                    else if (sArgs[i].equals("char"))
                    {
                        args[i] = char.class;
                    }
                    else if (sArgs[i].equals("float"))
                    {
                        args[i] = float.class;
                    }
                    else if (sArgs[i].equals("long"))
                    {
                        args[i] = long.class;
                    }
                }
                else
                {
                    args[i] = Class.forName(sArgs[i]);
                }
            }
            final Method method = className.getMethod(methodName.substring(methodName.indexOf(".") + 1, methodName.indexOf("(")), args);
            final StringBuilder sb = new StringBuilder();
            if (method.isAnnotationPresent(LogInfo.class))
            {
                final LogInfo logInfo = method.getAnnotation(LogInfo.class);
                final String operateModelNm = logInfo.operateModelNm();
                final String operateFuncNm = logInfo.operateFuncNm();
                final HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
                Object operName = request.getSession().getAttribute("userName");
                operName = operName == null ? "no sign" : operName;
                sb.append(DateUtil.nowStr(DUF.CN_DATETIME_FORMAT) + ": " + operateModelNm + ": " + operateFuncNm + ", 操作人: " + operName + ", ");
                sb.append("方法名: " + joinPoint.getSignature().getName() + ", 请求参数: ");
                final Set<Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
                for (final Entry<String, String[]> entry : entrySet)
                {
                    sb.append("#");
                    sb.append(entry.getKey() + "=");
                    final Object[] allValue = entry.getValue();
                    for (int i = 0; i < allValue.length; i++)
                    {
                        if (i != 0)
                        {
                            sb.append(", ");
                        }
                        sb.append(allValue[i].toString() + " ");
                    }
                }
                sb.append("  请求ip: " + IpUtil.getRemoteAddr(request));
            }
            logger.info(sb.toString());
            NestLogger.locationLog(sb.toString());
        }
        catch (final Exception e)
        {
            NestLogger.showException(e);
            logger.error(NestLogger.buildLog("日志记录异常", e, true));
        }
    }
}
