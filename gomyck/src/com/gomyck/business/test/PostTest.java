package com.gomyck.business.test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomyck.component.util.MailUtil;
import com.gomyck.component.util.NetworkUntil;
import com.gomyck.component.util.PropertiesReader;

public class PostTest {

	public static void main(String[] args) throws Exception {
		String str = "";
		while(true){
			str = "";
			for(int i = 0; i< 4; i ++){
				char temp = (char)(new Random().nextInt(26) + 97);
				str += temp;
			}
			Thread.sleep(5000);
			NetworkUntil net = new NetworkUntil();
			Map<String, String> param = new HashMap<String, String>();
			param.put("action"  , "checkDomainAvailable");
			param.put("domainName", str);
			param.put("tldList[]", ".com");
			try {
				String doPost = net.doPost(new URI("https://qcwss.qcloud.com/domain/ajax/?uin=474798383&csrfCode=1630159753&mc_gtk=1630159753&g_tk=1630159753&reqSeqId=0f7b2080-3b4c-528c-71aa-cc5f484fe8d3"), param);
				ObjectMapper om = new ObjectMapper();
				JsonNode readTree = om.readTree(doPost);
				Boolean ifRegister = Boolean.valueOf(readTree.get("result").get("data").get(0).get("available").asText());
				if(ifRegister){
					System.out.println("www." + str + ".com >>> can  register !!!!! ");
					sendEmail(" gomyck ", "hao474798383@163.com", "www." + str + ".com");
				}else{
					System.out.println("www." + str + ".com >>> cant register ..... ");
				}
			} catch (Exception e) {
				System.out.println("scan error www." + str + ".com   " + e.getMessage());
			} 
		}
	}
	
	private static void sendEmail(final String userName, final String emailAddr, final String validateCode) throws IOException, MessagingException
    {
        final PropertiesReader pr = new PropertiesReader(PostTest.class.getClassLoader().getResource("").getFile().replace("WEB-INF/classes/", "")  + "config/mail.properties");
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
        mailBody.append("尊敬的用户: " + userName + "<br/>&nbsp;&nbsp;可以激活的邮箱为: " + validateCode);
        MailUtil.sendHTMLMessage(session, hostAccounts, emailAddr, title, mailBody.toString());
    }
	
}
