package com.gomyck.business.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.gomyck.component.util.NetworkUntil;

public class PostTest {

	public static void main(String[] args) {
		NetworkUntil net = new NetworkUntil();
		Map<String, String> param = new HashMap<String, String>();
		param.put("t_phone"  , "13333333335");
		param.put("t_passwrd", "123123123");
		try {
			String doPost = net.doPost(new URI("http://btw.gomyck.com/bentian/create"), param);
			System.out.println(doPost);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
