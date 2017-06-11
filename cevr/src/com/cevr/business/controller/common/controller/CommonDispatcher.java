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
package com.cevr.business.controller.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cevr.business.interceptor.LogInfo;
import com.cevr.business.model.to.TicketInfo;

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
    @LogInfo(operateModelNm = "通用跳转", operateFuncNm = "页面通用跳转")
    @RequestMapping(value = "forward/{view}/{page}", method = RequestMethod.GET)
    public String gotoMain(@PathVariable final String view, @PathVariable final String page)
    {
        return view + "/" + page;
    }

}
