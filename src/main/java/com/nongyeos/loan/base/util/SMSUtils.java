package com.nongyeos.loan.base.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service("smsUtils")
public class SMSUtils {
	
	private static String account= "SDK-WSS-010-06450";
	
	private static String password= "7-F685-4";
	
	private static SMSClient client = null;
	
	/**
	 * 生成随机验证码
	 * 
	 * @return
	 */
	public static String createCharacter() {
		char[] codeSeq = {'1', '2', '3', '4', '5', '6', '7', '8', '9' };
		Random random = new Random();
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);
			s.append(r);
		}
		return s.toString();
	}
	
	public static void init(){
		try {
			client = new SMSClient(account,password);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public static int sendSMS(String content,String phoneNumber,String channelName){
		//1成功   0 失败
		int flag = Constants.SMS_FAIL;
		try {
			/*content = URLEncoder.encode(content, "utf8");
			String result = client.mdsmssend(phoneNumber, content, "", "", "", "");*/
			if(channelName.equals("信达通")){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mobile", phoneNumber);
				map.put("Content", content);
				boolean sendSms = SmsUtilForHSYD.sendSms(map);
				if(sendSms)
					flag = Constants.SMS_SUCCESS;
			}else{
				content = URLEncoder.encode(content, "utf8");
				String result = client.mdsmssend(phoneNumber, content, "", "", "", "");
				if(!"".equals(result) && !result.startsWith("-"));
				flag = Constants.SMS_SUCCESS;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	@PostConstruct 
	public void smsInitStart(){
		System.out.println("短信初始化.........");
		SMSUtils.init();
	}
}
