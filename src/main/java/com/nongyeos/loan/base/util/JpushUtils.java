package com.nongyeos.loan.base.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusJpush;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.model.JpushConfig;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IJpushService;
import com.nongyeos.loan.admin.service.IMemberLoginService;

@Component
public class JpushUtils {
	
	@Autowired
	private JpushConfig config;
//	private static String APP_KEY = "e4c89b70895f8776e1c59d7d";
//	private static String MASTER_SECRET = "4254591eff6d26b712054b05";
	@Autowired
	private IJpushService jpushService;
	
	@Autowired
	private IMemberLoginService memberLoginService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	
    /**
     * 消息推送
     * @param title 标题
     * @param alert 内容
     * @param registrationId 极光推送ID
     * @param channel 
     */
	public  void SendPush(String title,String alert,String registrationId, String channel) {
		if(StringUtils.isEmpty(title) || StringUtils.isEmpty(alert) || StringUtils.isEmpty(registrationId))
			return;
		ClientConfig clientConfig = ClientConfig.getInstance();
		String mastersecretString = config.getMASTERSECRET();
		String appkeyString = config.getAPPKEY();
		JSONObject appkeyJson = JSONObject.parseObject(appkeyString);
		JSONObject mastersecretJson = JSONObject.parseObject(mastersecretString);
		String appkey="";
		String mastersecret="";
		if(channel==null){
			return;
		}else{
			appkey=appkeyJson.getString(channel);
			mastersecret=mastersecretJson.getString(channel);
		}
        final JPushClient jpushClient = new JPushClient(mastersecret,appkey , null, clientConfig);
        final PushPayload payload = buildPushObject_android_and_ios(title,alert,registrationId);
        try {
            PushResult result = jpushClient.sendPush(payload);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
	
	 public  PushPayload buildPushObject_android_and_ios(Map<String, String> extras,String title,String alert) {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.android_ios())
	                .setAudience(Audience.all())
	                .setNotification(Notification.newBuilder()
	                		.setAlert(alert)
	                		.addPlatformNotification(AndroidNotification.newBuilder()
	                				.setTitle(title)
	                                .addExtras(extras).build())
	                		.addPlatformNotification(IosNotification.newBuilder()
	                				.incrBadge(1)
	                				.addExtras(extras).build())
	                		.build())
	                .build();
	 }
	 public  PushPayload buildPushObject_android_and_ios(Map<String, String> extras,String title,String alert,String registrationId) {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.android_ios())
	                .setAudience(Audience.registrationId(registrationId))
	                .setNotification(Notification.newBuilder()
	                		.setAlert(alert)
	                		.addPlatformNotification(AndroidNotification.newBuilder()
	                				.setTitle(title)
	                                .addExtras(extras).build())
	                		.addPlatformNotification(IosNotification.newBuilder()
	                				.incrBadge(1)
	                				.addExtras(extras).build())
	                		.build())
	                .build();
	 }
	 public  PushPayload buildPushObject_android_and_ios(String title,String alert,String registrationId) {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.android_ios())
	                .setAudience(Audience.registrationId(registrationId))
	                .setNotification(Notification.newBuilder()
	                		.setAlert(alert)
	                		.addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build())	                				
	                		.addPlatformNotification(IosNotification.newBuilder().build())
	                		.build())
	                .build();
	 }
	 /**
	  * 保存提示消息，唤醒推送线程
	  * @param member_login_id
	  * @param title
	  * @param content
	 * @throws Exception 
	  */
	 public void save(String member_login_id,String title,String content) throws Exception{
		 BusJpush jpush = new BusJpush();
		 jpush.setId(UUIDUtil.getUUID());
		 jpush.setUserLoginId(member_login_id);
		 jpush.setTitle(title);
		 jpush.setContent(content);
		 jpush.setTime(DateUtils.getNowDate());
		 jpushService.save(jpush);
		 JpushThread.getJpushThread().setFlag();
	 }
	 /**
	  * 保存提示消息，唤醒推送线程
	  * @param member_login_id
	  * @param title
	  * @param content
	 * @throws Exception 
	  */
	 public  void save(Object ipOrLoan,String status) throws Exception{
		 if(status==null || ipOrLoan==null )
			 return;
		 BusIntoPiece ip = null ;
		 BusLoan loan = null ;
		 try {
			ip= (BusIntoPiece)ipOrLoan;
		 } catch (Exception e) {
			loan=(BusLoan)ipOrLoan;
			ip=intoPieceService.selectByPrimaryKey(loan.getIntoPieceId());
		 }
		 List<BusMemberLogin> list =null;
		 Map<String, String> memberIdChannel = new HashMap<String, String>();
		 memberIdChannel.put("memberId", ip.getMemberId());
		 memberIdChannel.put("channel", ip.getChannel());
		 list =memberLoginService.selectByMemberId(memberIdChannel);
		 if(list !=null&&list.size()>0){
			 for (int j = 0; j < list.size(); j++) {
				 BusMemberLogin busMemberLogin = list.get(j);
				 BusJpush jpush = new BusJpush();
				 jpush.setId(UUIDUtil.getUUID());
				 jpush.setUserLoginId(busMemberLogin.getLoginId());
				 String typecpntent = config.getTYPECPNTENT();
				 String[] split = typecpntent.split(",");
				 for (int i = 0; i < split.length; i++) {
					 String[] split2 = split[i].split("-");
					 if(status.equals(split2[0])){
						 jpush.setTitle(split2[1]);
						 jpush.setContent("您的申请贷款进度："+split2[1]);
					 }
				 }
				 jpush.setTime(DateUtils.getNowDate());
				 jpushService.save(jpush);
			}
		 }
		 JpushThread.getJpushThread().setFlag();
	 }
	 
}
