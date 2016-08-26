/*
 * 文 件 名:  ForgetPwd.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-8-25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.gomyck.business.controller.welcome.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping(value = "asyn/pwd")
public class ForgotPwd
{
    @RequestMapping(value = "getPwdByEmail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPwdByEmail()
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("msg", "邮件已发送!");
        return result;
    }
}
