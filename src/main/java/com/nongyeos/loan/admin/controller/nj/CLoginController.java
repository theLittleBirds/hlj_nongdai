package com.nongyeos.loan.admin.controller.nj;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.service.IMemberLoginService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.ISmsHistoryService;
import com.nongyeos.loan.admin.service.ISmsTemplateService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.SMSUtils;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/nj/login")
public class CLoginController {
	
	@Autowired
	private ISmsHistoryService smsHistoryService;
	
	@Autowired
	private ISmsTemplateService smsTemplateService;
	
	@Autowired
	private IMemberLoginService memberLoginService;
	
	@Autowired  
    private IWebUserService userService; 
	
	@Autowired  
	private IPersonService personService;
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	@Value("${rootpoint.mark}")
	private String appMark;
	
	@Value("${fileupload.baseDir}")
	private String baseDir;

	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CLoginController.class);
	
	@RequestMapping("/checktype")
    @ResponseBody
	public JSONObject checkType(HttpServletRequest request, HttpServletResponse response,String loginName){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(loginName)){
			json.put("message", "请输入登录名");
			response.setStatus(400);
			return json;
		}
		String channel = request.getHeader("channel");
		if(StrUtils.isEmpty(channel)){
			json.put("message", "版本错误");
			response.setStatus(400);
			return json;
		}
		try {
			BusMemberLogin ml = new BusMemberLogin();
			ml.setLoginName(loginName);
			ml.setChannel(channel);
			if(memberLoginService.selectByLoginName(ml) != null){
				json.put("type", Constants.NJ_TOKEN_MEMBER);
				return json;
			}
			JSONObject obj = JSONObject.parseObject(appMark);
			SysWebUser user = userService.selectUserByUserName(loginName);
			if(user!= null){
				SysPerson person = personService.selectByUserIdAndIsdefault(user.getUserId());
				String orgIds = person.getParentOrgIds();
				String[] ids = orgIds.split(",");
				if(ids[0].equals(obj.getString(channel))){
					json.put("type", Constants.NJ_TOKEN_USER);
					return json;
				}
			}
			json.put("message", "账户不存在");
			response.setStatus(400);
		} catch (Exception e) {
			json.put("message", "系统错误");
			response.setStatus(400);
		}
		return json;
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
	public JSONObject user(HttpServletResponse response,HttpServletRequest request,String loginName,String password){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(loginName)){
			json.put("message", "请输入登录名");
			response.setStatus(400);
			return json;
		}
		if(StrUtils.isEmpty(password)){
			json.put("message", "请输入密码");
			response.setStatus(400);
			return json;
		}
		SysWebUser user = userService.selectUserByUserName(loginName);
		if(user == null){
			json.put("message", "账户不存在");
			response.setStatus(400);
			return json;
		}
		try {
			//盐值
	        ByteSource salt = ByteSource.Util.bytes(user.getUsername());
	        String md = String.valueOf(new SimpleHash("MD5", password, salt, 1024));
	        if(!md.equals(user.getPassword())){
	        	json.put("message", "密码错误");
	        	response.setStatus(400);
				return json;
	        }
	        String platForm = request.getHeader("Platform");
	        if("wx".equals(platForm)){
	        	//微信小程序
	        	if(StrUtils.isNotEmpty(user.getWxtoken()))
		        	stringRedisTemplate.delete("token"+user.getWxtoken());
	        	user.setWxtoken(Constants.NJ_TOKEN_USER+UUIDUtil.getUUID());
		        user.setUpdDate(new Date());
		        userService.updateUser(user);
		        stringRedisTemplate.opsForValue().set("token"+user.getWxtoken(), user.getUserId());
		        json.put("token", user.getWxtoken());
	        }else{
	        	//app
	        	if(StrUtils.isNotEmpty(user.getToken()))
		        	stringRedisTemplate.delete("token"+user.getToken());
		        user.setToken(Constants.NJ_TOKEN_USER+UUIDUtil.getUUID());
		        user.setUpdDate(new Date());
		        userService.updateUser(user);
		        stringRedisTemplate.opsForValue().set("token"+user.getToken(), user.getUserId());
		        json.put("token", user.getToken());
	        }
		} catch (Exception e) {
			e.printStackTrace();
			json.put("message", "登录出错，请稍后重试");
			response.setStatus(400);
		}		
		return json;
	}
	
	@RequestMapping("/registersms")
    @ResponseBody
	public JSONObject registerSms(HttpServletRequest request, HttpServletResponse response,String phone){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(phone)){
			json.put("message", "请输入手机号");
			response.setStatus(400);
			return json;
		}else if(!phone.matches("^1[0-9]{10}$")){
			json.put("message", "手机号错误");
			response.setStatus(400);
			return json;
		}
		String channel = request.getHeader("channel");
		if(StrUtils.isEmpty(channel)){
			json.put("message", "版本错误");
			response.setStatus(400);
			return json;
		}
		try {
			BusMemberLogin ml = new BusMemberLogin();
			ml.setChannel(channel);
			ml.setLoginName(phone);
			if(memberLoginService.selectByLoginName(ml) != null){
				json.put("message", "账户已存在，请登录");
				response.setStatus(400);
				return json;
			}
			String code = SMSUtils.createCharacter();
			Map<String,String> map = new HashMap<String, String>();
			map.put("CODE", code);
			JSONObject obj = JSONObject.parseObject(appMark);
			int result = smsTemplateService.smsSend("register", obj.getString(channel), map, phone);
			if(result == Constants.SMS_SUCCESS){
				stringRedisTemplate.opsForValue().set("register"+phone, code, 15, TimeUnit.MINUTES);
				json.put("message", "已发送");				
			}else{
				json.put("message", "发送失败，请稍后重试");
				response.setStatus(400);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("message", "发送失败，请稍后重试");
			response.setStatus(400);
		}
		return json;
	}
	
	@RequestMapping(value = "/registercheck", method = RequestMethod.POST)
    @ResponseBody
	public JSONObject registerCheck(HttpServletRequest request, HttpServletResponse response, String phone, String code){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(phone)){
			json.put("message", "请输入手机号");
			response.setStatus(400);
			return json;
		}
		if(StrUtils.isEmpty(code)){
			json.put("message", "请输入验证码");
			response.setStatus(400);;
			return json;
		}
		String channel = request.getHeader("channel");
		if(StrUtils.isEmpty(channel)){
			json.put("message", "版本错误");
			response.setStatus(400);
			return json;
		}
		try {
			BusMemberLogin check = new BusMemberLogin();
			check.setChannel(channel);
			check.setLoginName(phone);
			if(memberLoginService.selectByLoginName(check) != null){
				json.put("message", "账户已存在，请登录");
				response.setStatus(400);
				return json;
			}
			String redisCode = stringRedisTemplate.opsForValue().get("register"+phone);
			if(redisCode == null){
				json.put("message", "请获取验证码");
				response.setStatus(400);
				return json;
			}
			if(!redisCode.equals(code)){
				json.put("message", "验证码错误");
				response.setStatus(400);
				return json;
			}
			String token = Constants.NJ_TOKEN_MEMBER+UUIDUtil.getUUID();
			BusMemberLogin ml = new BusMemberLogin();
			ml.setLoginId(UUIDUtil.getUUID());
			ml.setLoginName(phone);
			String platForm = request.getHeader("Platform");
		    if("wx".equals(platForm)){
		    	ml.setWxtoken(token);
		    }else{
		    	ml.setToken(token);
		    }
			ml.setStatus(Constants.MEMBERLOGIN_STATUS_ENABLED);
			ml.setChannel(channel);
			ml.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			ml.setCreDate(new Date());
			ml.setUpdDate(new Date());
			memberLoginService.addRecord(ml);
			stringRedisTemplate.opsForValue().set("token"+token, ml.getLoginId());
			
			json.put("message", "注册成功");
			json.put("token", token);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("message", "注册出错了，请稍后重试");
			response.setStatus(400);
		}
		
		return json;
	}
	
	@RequestMapping("/loginsms")
    @ResponseBody
	public JSONObject loginSms(HttpServletRequest request,HttpServletResponse response,String phone){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(phone)){
			json.put("message", "手机号为空");
			response.setStatus(400);
			return json;
		}else if(!phone.matches("^1[0-9]{10}$")){
			json.put("message", "手机号码错误");
			response.setStatus(400);
			return json;
		}
		String channel = request.getHeader("channel");
		if(StrUtils.isEmpty(channel)){
			json.put("message", "版本错误");
			response.setStatus(400);
			return json;
		}
		try {
			BusMemberLogin check = new BusMemberLogin();
			check.setChannel(channel);
			check.setLoginName(phone);
			if(memberLoginService.selectByLoginName(check) == null){
				json.put("message", "账户不存在，请注册");
				response.setStatus(400);
				return json;
			}
			String code = SMSUtils.createCharacter();
			Map<String,String> map = new HashMap<String, String>();
			map.put("CODE", code);
			JSONObject obj = JSONObject.parseObject(appMark);
			int result = smsTemplateService.smsSend("validate", obj.getString(channel), map, phone);
			if(result == Constants.SMS_SUCCESS){
				stringRedisTemplate.opsForValue().set("login"+phone, code, 15, TimeUnit.MINUTES);
				json.put("message", "已发送");
			}else{
				json.put("message", "发送失败，请稍后重试");
				response.setStatus(400);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("message", "发送失败，请稍后重试");
			response.setStatus(400);	
		}
		return json;
	}
	
	@RequestMapping(value ="/logincheck", method = RequestMethod.POST)
    @ResponseBody
	public JSONObject loginCheck(HttpServletRequest request,HttpServletResponse response,String phone,String code){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(phone)){
			json.put("message", "请输入手机号");
			response.setStatus(400);
			return json;
		}
		if(StrUtils.isEmpty(code)){
			json.put("message", "请输入验证码");
			response.setStatus(400);
			return json;
		}
		String loginCode = stringRedisTemplate.opsForValue().get("login"+phone);
		if(loginCode == null){
			json.put("message", "请获取验证码");
			response.setStatus(400);
			return json;
		}
		if(!loginCode.equals(code)){
			json.put("message", "验证码错误");
			response.setStatus(400);
			return json;
		}
		String channel = request.getHeader("channel");
		if(StrUtils.isEmpty(channel)){
			json.put("message", "版本错误");
			response.setStatus(400);
			return json;
		}
		try {
			BusMemberLogin check = new BusMemberLogin();
			check.setChannel(channel);
			check.setLoginName(phone);
			BusMemberLogin ml = memberLoginService.selectByLoginName(check);
			if(ml == null){
				json.put("message", "账户不存在，请注册");
				response.setStatus(400);
				return json;
			}
			String platForm = request.getHeader("Platform");
			String token = Constants.NJ_TOKEN_MEMBER+UUIDUtil.getUUID();
		    if("wx".equals(platForm)){
		    	if(StrUtils.isNotEmpty(ml.getWxtoken())){
		    		stringRedisTemplate.delete("token"+ml.getWxtoken());
		    	}		    	
				ml.setWxtoken(token);
		    }else{
		    	if(StrUtils.isNotEmpty(ml.getToken())){
		    		stringRedisTemplate.delete("token"+ml.getToken());
		    	}	
				ml.setToken(token);
		    }
			ml.setUpdDate(new Date());
			memberLoginService.updateByPrimaryKeySelective(ml);
			stringRedisTemplate.opsForValue().set("token"+token, ml.getLoginId());
			json.put("message", "登录成功");
			json.put("token", token);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("message", "登录出错了，请稍后重试");
			response.setStatus(400);
		}
		return json;
	}
	
	
	
	@RequestMapping("/notice")
	public String notice(){
		return "nongJing/notice";
	}
	
	@RequestMapping("/localimageshow")
    public String localImageShow(String path, HttpServletResponse response){
    	response.setContentType("image/gif");
    	if(StrUtils.isEmpty(path))
    		return null;
    	FileInputStream fis = null;
    	try {
            OutputStream out = response.getOutputStream();
            File file = new File(baseDir+ path);
            fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
             e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                   fis.close();
                } catch (IOException e) {
                e.printStackTrace();
                }   
            }
        }
    	return null;
    }
}
