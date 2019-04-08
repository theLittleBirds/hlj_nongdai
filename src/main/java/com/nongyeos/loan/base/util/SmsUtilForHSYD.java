package com.nongyeos.loan.base.util;


import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

/**
 * 
 ********************************************************* .<br>
 * 
 * @classname SmsUtilForHSYD <br>
 * @description 华森亿东 短信通道<br>
 * @author zj <br>
 * @created 2016-6-13 下午4:31:57 <br>
 * @desc 会员营销内容要求 ：结尾需为 退订复TD+签名( 如：【蜂信】) .<br>
 */
public class SmsUtilForHSYD {
	protected static Logger log = LoggerFactory.getLogger(SmsUtilForHSYD.class);
	private final static String URL = "http://www.duancaixin360.com:8088";  //短彩信url
	private final static String VOICEURL="http://voice.duancaixin360.com:8088/voiceverify.ashx?user=USER&pwd=PWD&phone=PHONE&code=CODE";  //语音url

	// 验证码账户
	private final static String VALIDCODE_USER = "byss";
	private final static String VALIDCODE_PWD = "byss123";

	// 行业通知账户
//	private final static String BUSINESS_USER = "byss";
//	private final static String BUSINESS_PWD = "byss123";

	// 会员营销账户
	private final static String VIP_USER = "";
	private final static String VIP_PWD = "";


    public static boolean sendSms(Map<String, Object> params) {
		Map<String, String> smsMap = new HashMap<String, String>();
		String returnStr = "短信发送异常";
		boolean returnFlag = false;
		try {
		    log.info("++++++++++++++++++info++++++++++"+params.get("mobile"));
		    log.debug("++++++++++++++++debug++++++++++++"+params.get("mobile"));
		    log.info("==================短信内容============"+params.get("Content"));
			// 发送手机号码不能为空
			if (params.get("mobile").toString().equals("")) {
				throw new Exception("发送手机号码不能为空");
			}
			// 验证短信内容不能为空
			if (params.get("Content").toString().equals("")) {
				throw new Exception("短信内容不能为空");
			}
			String content =params.get("Content").toString(); //短信内容
			smsMap.put("user", VALIDCODE_USER);
			smsMap.put("pwd", VALIDCODE_PWD);
			smsMap.put("mobiles", params.get("mobile").toString());
			smsMap.put("contents", URLEncoder.encode(content,"utf-8").replaceAll("\\+",  "%20"));
			returnStr = HttpClientUtil.doPost(URL + "/SendMes.ashx", smsMap, "utf-8");
			Map<String, Object> resultMap = XMLConvertor.xml2map(returnStr,false);
			if ("0".equals(resultMap.get("result").toString())) {
				log.info((resultMap.get("desc").toString()));
				returnFlag=true;
			}
			log.debug("++++++++++++++++debug++voer++++++++++"+params.get("mobile"));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			returnStr = e.getMessage();
		}
		return returnFlag;
	}
	
	/**
	 * 
	 *仅限 验证码调用
	 * @author zjk
	 * 2016年7月21日
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public static boolean sendVoiceSms(Map<String, Object> params){
        String returnStr = "短信发送异常";
        boolean returnFlag = false;
        try {
            // 发送手机号码不能为空
            if (params.get("mobile").toString().equals("")) {
                throw new Exception("发送手机号码不能为空");
            }
            // 验证短信内容不能为空
            if (params.get("Content").toString().equals("")) {
                throw new Exception("短信内容不能为空");
            }
            // 验证码短信
            String reqUrl=VOICEURL.replace("USER", VALIDCODE_USER).replace("PWD", VALIDCODE_PWD).replace("PHONE", params.get("mobile").toString()).replace("CODE",params.get("Content")
                    .toString());
            returnStr = HttpClientUtil.doGet(reqUrl);
            
            Map<String, Object> resultMap = XMLConvertor.xml2map(returnStr,false);
            if ("0".equals( resultMap.get("result"))) {
                log.info(( resultMap.get("desc").toString()));
                returnFlag=true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            returnStr = e.getMessage();
        }
        return returnFlag;
	} 

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", "18202791359");
		map.put("Content", "首信集团向您发送一份委托服务合同-电子版，点击前往签署！");
		System.out.println(SmsUtilForHSYD.sendSms(map));
	}
}
