/*
 * 文 件 名:  register.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-8-25
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.gomyck.business.controller.welcome.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gomyck.business.controller.welcome.service.IRegisterService;
import com.gomyck.business.model.entity.welcome.BizUser;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping(value = "asyn/reg")
public class Register
{
    @Autowired
    @Qualifier(value = "DefaultRegisterService")
    private IRegisterService regSer;
    
    @RequestMapping(value = "createUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPwdByEmail(final BizUser user)
    {
        user.setDeleteFlag("0");
        user.setCancleFlag("0");
        user.setHoldeFlag("0");
        user.setLastLoginTime(new Date());
        user.setPowerId(0);
        this.regSer.saveUser(user);
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("msg", "注册成功!");
        return result;
    }
    
    @RequestMapping(value = "validataParam", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> validataParam(final int type, final String value)
    {
        switch (type)
        {
            case 1:
                System.out.println("1" + value);
                break;
            case 2:
                System.out.println("2" + value);
                break;
            case 3:
                System.out.println("3" + value);
                break;
            default:
                System.out.println("-1" + value);
        }
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", true);
        result.put("msg", "没毛病!");
        return result;
    }
}
