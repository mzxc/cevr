package com.cevr.business.interceptor;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 业务类拦截器
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-30]
 * @see #CkInterceptor
 * @since 2.1
 */
public class CkInterceptor implements HandlerInterceptor
{
    /**
     * 日志服务
     */
    private static final Logger logger = Logger.getLogger(CkInterceptor.class);
    
    public String loginFlag;
    
    public String[] allowUrls;
    
    public boolean ifNeedLogin;
    
    public void setIfNeedLogin(boolean ifNeedLogin) {
		this.ifNeedLogin = ifNeedLogin;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public void setAllowUrls(final String[] allowUrls)
    {
        this.allowUrls = allowUrls;
    }
    
    /**
     * 重载方法
     * 
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @throws Exception
     */
    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object obj, final Exception ex) throws Exception
    {
        if (ex != null)
        {
            logger.error("错误请求：" + request.getRequestURI() + ",异常信息：" + ex.toString());
        }
    }
    
    /**
     * 重载方法
     * 
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     * @throws Exception
     */
    @Override
    public void postHandle(final HttpServletRequest arg0, final HttpServletResponse arg1, final Object arg2, final ModelAndView arg3) throws Exception
    {
        
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception
    {
        final Map<String, String[]> parameterMap = request.getParameterMap();
        final Iterator<String> key = parameterMap.keySet().iterator();
        while (key.hasNext())
        {
            final String[] strs = parameterMap.get(key.next());
            for (final String str : strs)
            {
                final String dict = "like |drop table |and |or |exec |insert |select |delete |xp_cmdshell |update |count |chr |mid |netlocalgroup administrators |master |truncate |char |declare |script|alert|jquery|${";
                final String[] checkStr = dict.split("\\|");
                for (int i = 0; i < checkStr.length; i++)
                {
                    try
                    {
                        if (str.toLowerCase().indexOf(checkStr[i]) != -1)
                        {
                            request.getSession().invalidate();
                            return false;
                        }
                    }
                    catch (final Exception e)
                    {
                        request.getSession().invalidate();
                        return false;
                    }
                }
            }
        }
        final Object obj = request.getSession().getAttribute(loginFlag);
        if (obj != null && ifNeedLogin)
        {
            return true;
        }
        if(!ifNeedLogin){
        	request.getSession().setAttribute(loginFlag, "login");
        	response.sendRedirect(request.getContextPath() + "/common/forward/main/mainframe");
        	return false;
        }
        final String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
        if (null != this.allowUrls && this.allowUrls.length >= 1)
        {
            for (final String url : this.allowUrls)
            {
                if (requestUrl.contains(url))
                {
                    return true;
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/common/forward/main/mainframe");
        return false;
    }
}
