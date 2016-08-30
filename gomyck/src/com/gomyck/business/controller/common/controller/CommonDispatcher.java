/*
 * 文 件 名:  CommenDispatcher.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-8-25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.gomyck.business.controller.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gomyck.business.interceptor.LogInfo;

/**
 * 通用转发器
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-25]
 * @see #gotoLogin(String)
 * @see #gotoMain(String)
 * @since 1.0
 */
@Controller
@RequestMapping(value = "common")
public class CommonDispatcher
{
    @LogInfo(operateModelNm = "通用跳转", operateFuncNm = "欢迎页通用跳转")
    @RequestMapping(value = "forward/welcome/{page}", method = RequestMethod.GET)
    public String gotoLogin(@PathVariable final String page)
    {
        return "welcome/" + page;
    }
    
    @LogInfo(operateModelNm = "通用跳转", operateFuncNm = "主界面通用跳转")
    @RequestMapping(value = "forward/main/{page}", method = RequestMethod.GET)
    public String gotoMain(@PathVariable final String page)
    {
        return "main/" + page;
    }
}
