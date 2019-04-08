package com.nongyeos.loan.admin.controller;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.junziqian.api.bean.ResultInfo;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.mapper.SysWebUserMapper;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.service.IGuaranteeFeeInfoService;
import com.nongyeos.loan.admin.service.IRefundService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.IpAddressUtil;

@Controller
@RequestMapping("/asyncInter")
public class AsyncInterController {

	@Autowired
	private IntoPieceConfig pieceConfig;
	@Autowired
	private SysWebUserMapper userMapper;
	
	@Autowired
	private IRefundService refundService;
	
	@Autowired
	private IGuaranteeFeeInfoService feeInfoService;

	
	/**
	 * 先锋新协议支付异步回调
	 * @param request
	 * @param response
	 */
	@RequestMapping("/bgXFSignPayNoSms")
	public void bgXFSignPayNoSms(HttpServletRequest request, HttpServletResponse response){
		try {
			String ipAddress = IpAddressUtil.getIpAddress(request);
			System.out.println("访问先锋新协议支付异步回调的ip是："+ipAddress);
			
			SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
			if (StringUtils.isEmpty(request.getParameter("data"))) {
			    throw new Exception("异步回调接收数据失败");
			}
			System.out.println("新协议支付先锋data："+request.getParameter("data"));
			
			/**
			 * nginx转发通过from区别公司
			 */
			String channel = request.getHeader("from");
			System.out.println("异步channel类型："+ channel);
			
			String companyType = "";
	    	if(!StringUtils.isEmpty(channel)){
	    		if(channel.equals("SX")){
	    			//吉林首信
	    			companyType = channel;
	    		}else if(channel.equals("AJ")){
	    			//安家世行
	    			companyType = channel;
	    		}else if(channel.equals("HLJSX")){
	    			//黑龙江首信
	    			companyType = channel;
	    		}else{
	    			//农鲸2
	    			companyType = channel;
	    		}
	    	}else{
	    		throw new Exception("公司类别为空");
	    	}
	    	System.out.println("companyType类型："+companyType);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("data", request.getParameter("data"));
			map.put("userid", user.getUserId());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("companyType", companyType);//公司类型
			//先锋新协议支付回调
			String result = HttpClientUtil.doPost(pieceConfig.getBgXFSignPayNoSms(), map, "utf-8");
			if(StringUtils.isEmpty(result)){
				throw new Exception("先锋新协议支付异步回调失败！");
			}
			JSONObject resultJson = JSONObject.parseObject(result);
			if(resultJson.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
				response.getWriter().println("SUCCESS");
             	return;
			}else{
				throw new Exception("先锋新协议支付异步回调失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 服务费（担保费）支付异步回调
	 * @param request
	 * @param response
	 */
	@RequestMapping("/bgXFGuaranteeFee")
	public void bgXFGuaranteeFee(HttpServletRequest request, HttpServletResponse response){
		try {
			String ipAddress = IpAddressUtil.getIpAddress(request);
			System.out.println("访问先锋服务费（担保费）支付异步回调的ip是："+ipAddress);
			
			SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
			if (StringUtils.isEmpty(request.getParameter("data"))) {
			    throw new Exception("异步回调接收数据失败");
			}
			System.out.println("服务费先锋data："+request.getParameter("data"));
			
			/**
			 * nginx转发通过from区别公司
			 */
			String channel = request.getHeader("from");
			System.out.println("异步channel类型："+ channel);
			
			String companyType = "";
	    	if(!StringUtils.isEmpty(channel)){
	    		if(channel.equals("SX")){
	    			//吉林首信
	    			companyType = channel;
	    		}else if(channel.equals("AJ")){
	    			//安家世行
	    			companyType = channel;
	    		}else if(channel.equals("HLJSX")){
	    			//黑龙江首信
	    			companyType = channel;
	    		}else{
	    			//农鲸2
	    			companyType = channel;
	    		}
	    	}else{
	    		throw new Exception("公司类别为空");
	    	}
	    	System.out.println("companyType类型："+companyType);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("data", request.getParameter("data"));
			map.put("userid", user.getUserId());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("companyType", companyType);//公司类型
			//先锋新协议支付回调
			String result = HttpClientUtil.doPost(pieceConfig.getBgXFGuaranteeFee(), map, "utf-8");
			if(StringUtils.isEmpty(result)){
				throw new Exception("先锋新协议支付异步回调失败！");
			}
			JSONObject resultJson = JSONObject.parseObject(result);
			if(resultJson.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
				response.getWriter().println("SUCCESS");
             	return;
			}else{
				throw new Exception("先锋新协议支付异步回调失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * 君子签异步回调
	 * @param request
	 * @param response
	 */
	@RequestMapping("/bgJzqRet")
	public void bgJzqRet(HttpServletRequest request, HttpServletResponse response){
		try {
			String ipAddress = IpAddressUtil.getIpAddress(request);
			System.out.println("访问君子签异步回调的ip是："+ipAddress);
			
			/**
			 * nginx转发通过from区别公司
			 */
			String channel = request.getHeader("from");
			System.out.println("异步channel类型："+ channel);
			
			String companyType = "";
	    	if(!StringUtils.isEmpty(channel)){
	    		if(channel.equals("SX")){
	    			//吉林首信
	    			companyType = channel;
	    		}else if(channel.equals("AJ")){
	    			//安家世行
	    			companyType = channel;
	    		}else if(channel.equals("HLJSX")){
	    			//黑龙江首信
	    			companyType = channel;
	    		}else{
	    			//农鲸2
	    			companyType = channel;
	    		}
	    	}else{
	    		throw new Exception("公司类别为空");
	    	}
	    	System.out.println("companyType类型："+companyType);
			
			Map<String,String> signParameters = new HashMap<String,String>();//保存参与验签字段
			Map<?, ?> parameters = request.getParameterMap();//保存request请求参数的临时变量
			Iterator<?> paiter = parameters.keySet().iterator();
	        while (paiter.hasNext()) {
	            String key = paiter.next().toString();
	            String[] values = (String[])parameters.get(key);
	            System.out.println(key+"-------------"+values[0]);
                signParameters.put(key, values[0]);
	        }
	        
	        SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
	        signParameters.put("userid", user.getUserId());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			signParameters.put("signature", DigestUtils.md5Hex(signature));
			signParameters.put("companyType", companyType);//公司类型
			
			//君子签回调
	        String result = HttpClientUtil.doPost(pieceConfig.getBgJzqRet(), signParameters, "utf-8");
	        JSONObject resultJson = JSONObject.parseObject(result);
			if(resultJson.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
				ResultInfo.create().success();
             	return;
			}else{
				throw new Exception("君子签异步回调失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 微信支付付款异步通知
	 * @param request
	 * @param response
	 */
	@RequestMapping("/bgWXPayNotice")
	public void bgWXPayNotice(HttpServletRequest request, HttpServletResponse response){
		try {
			String ipAddress = IpAddressUtil.getIpAddress(request);
			System.out.println("访问微信支付付款异步通知的ip是："+ipAddress);
			
			/**
			 * nginx转发通过from区别公司
			 */
			String channel = request.getHeader("from");
			System.out.println("异步channel类型："+ channel);
			
			String companyType = "";
	    	if(!StringUtils.isEmpty(channel)){
	    		if(channel.equals("SX")){
	    			//吉林首信
	    			companyType = channel;
	    		}else if(channel.equals("AJ")){
	    			//安家世行
	    			companyType = channel;
	    		}else if(channel.equals("HLJSX")){
	    			//黑龙江首信
	    			companyType = channel;
	    		}else{
	    			//农鲸2
	    			companyType = channel;
	    		}
	    	}else{
	    		throw new Exception("公司类别为空");
	    	}
	    	System.out.println("companyType类型："+companyType);
			
			Map<String,String> signParameters = new HashMap<String,String>();//保存参与验签字段
			Map<?, ?> parameters = request.getParameterMap();//保存request请求参数的临时变量
			Iterator<?> paiter = parameters.keySet().iterator();
	        while (paiter.hasNext()) {
	            String key = paiter.next().toString();
	            String[] values = (String[])parameters.get(key);
	            System.out.println(key+"-------------"+values[0]);
	            signParameters.put(key, values[0]);
	        }
	        
	        BufferedReader reader = request.getReader();
			String line="";
			StringBuffer inputString=new StringBuffer();
			while((line=reader.readLine())!=null){
				inputString.append(line);
			}
			request.getReader().close();
	        
	        SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
	        signParameters.put("userid", user.getUserId());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			signParameters.put("signature", DigestUtils.md5Hex(signature));
			signParameters.put("companyType", companyType);//公司类型
			signParameters.put("reader", inputString.toString());//微信header
	        
			//微信支付回调
	        String result = HttpClientUtil.doPost(pieceConfig.getBgWXPayNotice(), signParameters, "utf-8");
	        JSONObject resultJson = JSONObject.parseObject(result);
	        if(resultJson.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
	        	System.out.println("微信支付异步回调ok！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
	        	//返回给微信服务器的消息
	        	response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
             	return;
			}else{
				throw new Exception("微信支付异步回调失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 微信服务费（担保费）支付异步回调
	 * @param request
	 * @param response
	 */
	@RequestMapping("/bgWXPayGuaranteeFeeNotice")
	public void bgWXPayGuaranteeFeeNotice(HttpServletRequest request, HttpServletResponse response){
		try {
			String ipAddress = IpAddressUtil.getIpAddress(request);
			System.out.println("访问微信服务费（担保费）支付异步回调的ip是："+ipAddress);
			
			/**
			 * nginx转发通过from区别公司
			 */
			String channel = request.getHeader("from");
			System.out.println("异步channel类型："+ channel);
			
			String companyType = "";
	    	if(!StringUtils.isEmpty(channel)){
	    		if(channel.equals("SX")){
	    			//吉林首信
	    			companyType = channel;
	    		}else if(channel.equals("AJ")){
	    			//安家世行
	    			companyType = channel;
	    		}else if(channel.equals("HLJSX")){
	    			//黑龙江首信
	    			companyType = channel;
	    		}else{
	    			//农鲸2
	    			companyType = channel;
	    		}
	    	}else{
	    		throw new Exception("公司类别为空");
	    	}
	    	System.out.println("companyType类型："+companyType);
			
			Map<String,String> signParameters = new HashMap<String,String>();//保存参与验签字段
			Map<?, ?> parameters = request.getParameterMap();//保存request请求参数的临时变量
			Iterator<?> paiter = parameters.keySet().iterator();
	        while (paiter.hasNext()) {
	            String key = paiter.next().toString();
	            String[] values = (String[])parameters.get(key);
	            System.out.println(key+"-------------"+values[0]);
	            signParameters.put(key, values[0]);
	        }
	        
	        BufferedReader reader = request.getReader();
			String line="";
			StringBuffer inputString=new StringBuffer();
			while((line=reader.readLine())!=null){
				inputString.append(line);
			}
			request.getReader().close();
	        
	        SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
	        signParameters.put("userid", user.getUserId());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			signParameters.put("signature", DigestUtils.md5Hex(signature));
			signParameters.put("companyType", companyType);//公司类型
			signParameters.put("reader", inputString.toString());//微信header
	        
			//微信支付回调
	        String result = HttpClientUtil.doPost(pieceConfig.getBgWXPayGuaranteeFeeNotice(), signParameters, "utf-8");
	        JSONObject resultJson = JSONObject.parseObject(result);
	        if(resultJson.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
	        	System.out.println("微信支付异步回调ok！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
	        	//返回给微信服务器的消息
	        	if(!StringUtils.isEmpty(resultJson.getString("guaranteeId"))&&!StringUtils.isEmpty(resultJson.getString("status"))){
	        		feeInfoService.payNotice(resultJson);
	        	}
	        	response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
             	return;
			}else{
				throw new Exception("微信服务费（担保费）支付异步回调失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	/**
	 * 微信退款异步回调
	 * @param request
	 * @param response
	 */
	@RequestMapping("/bgWXRefundNotice")
	public void bgWXRefundNotice(HttpServletRequest request, HttpServletResponse response){
		try {
			String ipAddress = IpAddressUtil.getIpAddress(request);
			System.out.println("访问微信退款异步回调的ip是："+ipAddress);
			
			/**
			 * nginx转发通过from区别公司
			 */
			String channel = request.getHeader("from");
			System.out.println("异步channel类型："+ channel);
			
			String companyType = "";
	    	if(!StringUtils.isEmpty(channel)){
	    		if(channel.equals("SX")){
	    			//吉林首信
	    			companyType = channel;
	    		}else if(channel.equals("AJ")){
	    			//安家世行
	    			companyType = channel;
	    		}else if(channel.equals("HLJSX")){
	    			//黑龙江首信
	    			companyType = channel;
	    		}else{
	    			//农鲸2
	    			companyType = channel;
	    		}
	    	}else{
	    		throw new Exception("公司类别为空");
	    	}
	    	System.out.println("companyType类型："+companyType);
			
			Map<String,String> signParameters = new HashMap<String,String>();//保存参与验签字段
			Map<?, ?> parameters = request.getParameterMap();//保存request请求参数的临时变量
			Iterator<?> paiter = parameters.keySet().iterator();
	        while (paiter.hasNext()) {
	            String key = paiter.next().toString();
	            String[] values = (String[])parameters.get(key);
	            System.out.println(key+"-------------"+values[0]);
	            signParameters.put(key, values[0]);
	        }
	        
	        BufferedReader reader = request.getReader();
			String line="";
			StringBuffer inputString=new StringBuffer();
			while((line=reader.readLine())!=null){
				inputString.append(line);
			}
			request.getReader().close();
	        
	        SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
	        signParameters.put("userid", user.getUserId());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			signParameters.put("signature", DigestUtils.md5Hex(signature));
			signParameters.put("companyType", companyType);//公司类型
			signParameters.put("reader", inputString.toString());//微信header
	        
			//微信支付回调
	        String result = HttpClientUtil.doPost(pieceConfig.getBgWXRefundNotice(), signParameters, "utf-8");
	        JSONObject resultJson = JSONObject.parseObject(result);
	        if(resultJson.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
	        	System.out.println("微信退款异步回调ok！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
	        	//返回给微信服务器的消息
	        	if(!StringUtils.isEmpty(resultJson.getString("refundId"))&&!StringUtils.isEmpty(resultJson.getString("status"))){
	        		refundService.refundSuccess(resultJson);
	        	}
	        	response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
             	return;
			}else{
				throw new Exception("微信退款异步回调失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * 七陌款异步通知
	 * @param request
	 * @param response
	 */
	@RequestMapping("/bgQMRet")
	public void bgQMRet(HttpServletRequest request, HttpServletResponse response){
		try {
			String ipAddress = IpAddressUtil.getIpAddress(request);
			System.out.println("访问七陌款异步通知的ip是："+ipAddress);
			
			Map<String,String> signParameters = new HashMap<String,String>();//保存参与验签字段
			Map<?, ?> parameters = request.getParameterMap();//保存request请求参数的临时变量
			Iterator<?> paiter = parameters.keySet().iterator();
	        while (paiter.hasNext()) {
	            String key = paiter.next().toString();
	            String[] values = (String[])parameters.get(key);
	            System.out.println(key+"-------------"+values[0]);
	            signParameters.put(key, values[0]);
	        }
	        
	        SysWebUser user = userMapper.selectByName(pieceConfig.getUserName());
	        signParameters.put("userid", user.getUserId());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			signParameters.put("signature", DigestUtils.md5Hex(signature));
	        
			//七陌外呼回调
	        String result = HttpClientUtil.doPost(pieceConfig.getBgQMRet(), signParameters, "utf-8");
	        JSONObject resultJson = JSONObject.parseObject(result);
	        if(resultJson.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
	        	//七陌外呼要求返回200，通知接收成功
            	response.getWriter().print("200");
             	return;
			}else{
				throw new Exception("七陌外呼异步回调失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
