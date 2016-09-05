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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gomyck.component.util.MailUtil;
import com.gomyck.component.util.MathUtil;
import com.gomyck.component.util.PropertiesReader;
import com.gomyck.component.util.ResultBuild;

/**
 * 忘记密码
 * 
 * @author 郝洋
 * @version [版本号, 2016-8-25]
 * @see #ForgotPwd
 * @since 1.0
 */
@Controller
@RequestMapping(value = "asyn/pwd")
public class ForgotPwd
{
    @RequestMapping(value = "getPwdByEmail", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPwdByEmail(final String emailAddr)
    {
        final Map<String, Object> result = new HashMap<String, Object>();
        try
        {
            sendEmail(emailAddr, MathUtil.getStringRandom(6));
        }
        catch (final Exception e)
        {
            e.printStackTrace();
            return ResultBuild.init(false, "邮件系统发送出错,请稍后再试!", null);
        }
        return ResultBuild.init(true, "邮件已发送!", null);
    }
    
    private static void sendEmail(final String emailAddr, final String validateCode) throws IOException, MessagingException
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
        mailBody.append("尊敬的用户: <br/>您好, 您的验证码为: " + validateCode + " , 请在忘记密码页面输入后更改密码!此验证码10分钟内有效!");
        MailUtil.sendHTMLMessage(session, hostAccounts, emailAddr, title, mailBody.toString());
    }
}
