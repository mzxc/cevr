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

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gomyck.business.controller.welcome.service.IRegisterService;
import com.gomyck.business.model.entity.welcome.BizActivate;
import com.gomyck.business.model.entity.welcome.BizUser;
import com.gomyck.component.util.MailUtil;
import com.gomyck.component.util.MathUtil;
import com.gomyck.component.util.PropertiesReader;

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
        final Map<String, Object> result = new HashMap<String, Object>();
        user.setDeleteFlag("0");
        user.setCancleFlag("0");
        user.setHoldeFlag("1");
        user.setLastLoginTime(new Date());
        user.setPowerId(0);
        this.regSer.saveObj(user);
        final BizActivate ba = new BizActivate();
        final StringBuffer sb = new StringBuffer();
        sb.append(MathUtil.getStringRandom(6));
        ba.setCancleFlag("0");
        ba.setSingTime(new Date());
        ba.setUserId(user.getId());
        ba.setValidateCode(sb.toString());
        this.regSer.saveObj(ba);
        try
        {
            sendEmail(user.getUserName(), user.getEmail(), sb.toString());
        }
        catch (final Exception e)
        {
            result.put("result", false);
            result.put("msg", "邮件系统出错,请稍后再试");
            return result;
        }
        result.put("result", true);
        result.put("msg", "邮件已发送!请登陆您的邮箱查看激活码!页面即将跳转");
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
    
    private static void sendEmail(final String userName, final String emailAddr, final String validateCode) throws IOException, MessagingException
    {
        final PropertiesReader pr = new PropertiesReader(System.getProperty("gomyck.root") + "config\\mail.properties");
        final String hostAccounts = pr.getValueByKey("mail.username");
        final String sysUser = hostAccounts.substring(0, hostAccounts.indexOf("@"));
        final String password = pr.getValueByKey("mail.password");
        final Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.126.com");
        props.put("mail.smtp.auth", "true");
        final Session session = Session.getInstance(props, new Authenticator()
        {
            @Override
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(sysUser, password);
            }
        });
        final String title = "gomyck";
        final StringBuffer mailBody = new StringBuffer();
        mailBody.append("尊敬的用户: " + userName + "<br/>&nbsp;&nbsp;您的账号激活码为: " + validateCode + " , 请在登陆系统后输入激活,感谢您的注册!");
        MailUtil.sendHTMLMessage(session, hostAccounts, emailAddr, title, mailBody.toString());
    }
}
