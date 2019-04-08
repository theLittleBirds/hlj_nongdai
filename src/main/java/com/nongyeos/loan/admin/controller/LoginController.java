package com.nongyeos.loan.admin.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nongyeos.loan.admin.model.RootPointConfig;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.RandomValidateCodeUtil;

  
@Controller  
public class LoginController {  
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);
	
    @Resource  
    private IWebUserService webUserService;  
    
    @Autowired
    private RootPointConfig rootPointConfig;
    
    @RequestMapping("/")
    public String loginOpen(HttpServletRequest request) {
    	String channel = request.getHeader("from");
    	if(!StringUtils.isEmpty(channel)){
    		if(channel.equals("HLJSX")){
    			return "admin/shouxinlogin";
    		}else if(channel.equals("AJ")){
    			return "admin/anjialogin";
    		}
    	}
    	return "admin/login";
    }
    
    @RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		return "admin/login";
	}
    
    /**
     * 生成验证码
     */
    @RequestMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            logger.error("获取验证码失败>>>>   ", e);
        }
    }
    
}  
