package com.gomyck.business.interceptor;

import java.lang.reflect.Method;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gomyck.component.context.spring.aop.utils.AOPMethodUtil;
import com.gomyck.component.logger.NestLogger;
import com.gomyck.component.logger.annotation.LogInfo;
import com.gomyck.component.utils.DateUtil;
import com.gomyck.component.utils.IpUtil;
import com.gomyck.component.utils.DateUtil.DUF;

/**
 * 
 * 日志切面
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-30]
 * @see #LogService
 * @since 1.0
 */
public class LogService {
    
    private static final Logger logger = Logger.getLogger(LogService.class);
    
    public void logIt(final JoinPoint joinPoint) {
        try {
            Method method = AOPMethodUtil.getPointMethod(joinPoint);
            final StringBuilder sb = new StringBuilder();
            if (method.isAnnotationPresent(LogInfo.class)) {
                final LogInfo logInfo = method.getAnnotation(LogInfo.class);
                final String operateModelNm = logInfo.operateModelNm();
                final String operateFuncNm = logInfo.operateFuncNm();
                final HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
                Object operName = request.getSession().getAttribute("userName");
                operName = operName == null ? "no sign" : operName;
                sb.append(DateUtil.now2Str(DUF.CN_DATETIME_FORMAT) + ": " + operateModelNm + ": " + operateFuncNm + ", 操作人: " + operName + ", ");
                sb.append("方法名: " + joinPoint.getSignature().getName() + ", 请求参数: ");
                final Set<Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
                for (final Entry<String, String[]> entry : entrySet) {
                    sb.append("#");
                    sb.append(entry.getKey() + "=");
                    final Object[] allValue = entry.getValue();
                    for (int i = 0; i < allValue.length; i++) {
                        if (i != 0) {
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
        catch (final Exception e) {
            NestLogger.showException(e);
            logger.error(NestLogger.buildLog("日志记录异常", e, true));
        }
    }
    
}
