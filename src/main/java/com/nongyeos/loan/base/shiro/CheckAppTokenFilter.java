package com.nongyeos.loan.base.shiro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.nongyeos.loan.base.util.MD5;
import com.nongyeos.loan.base.util.StrUtils;

public class CheckAppTokenFilter implements Filter{
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.err.println("check app token filter init");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse) res;
		String url = request.getServletPath();
		
		if (!request.getRequestURI().startsWith("/user/login") && 
        		!url.startsWith("/nj/") && !SecurityUtils.getSubject().isAuthenticated()) {
            //判断session里是否有用户信息
            if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                //如果是ajax请求响应头会有，x-requested-with
                response.setHeader("session-status", "timeOut");//在响应头设置session状态
                return;
            }
        }
		
		String basePath = request.getContextPath();
        request.setAttribute("basePath", basePath);

		if(url.startsWith("/nj/")){
			//不校验token，直接放行
			if(request.getRequestURI().startsWith("/nj/login") || request.getRequestURI().startsWith("/nj/wxpage")){
				chain.doFilter(request, response);
				return;
			}
			String timestamp = request.getParameter("timestamp");
			String sign = request.getParameter("sign");
			String token = request.getHeader("token");
			String platForm = request.getHeader("Platform");
			String version = request.getHeader("Version");
			/*if(StrUtils.isEmpty(timestamp) == true || StrUtils.isEmpty(sign) == true || StrUtils.isEmpty(token) == true){
				response.setContentType("text/html; charset=UTF-8");
		        response.getWriter().print("{\"message\":\"认证失败，请重新登录！\"}");
		        response.setStatus(401);
				return;
			}
			//时间戳一个小时有效
			try {
				long diff = System.currentTimeMillis() - Long.parseLong(timestamp);
				if(diff > 3600000L || diff < -3600000L){
					response.setContentType("text/html; charset=UTF-8");
			        response.getWriter().print("{\"message\":\"认证失败，请重新登录！\"}");
			        response.setStatus(401);
					return;
				}
			} catch (Exception e) {
				response.setContentType("text/html; charset=UTF-8");
		        response.getWriter().print("{\"message\":\"认证失败，请重新登录！\"}");
		        response.setStatus(401);
				return;
			}
			//除sign参数外其余参数按名称排序，拼接成字符串，md5后和sign比较
			Enumeration<?> paraName = request.getParameterNames();
			HashMap<String, Object> map = new HashMap<String, Object>();
			ArrayList<String> arr = new ArrayList<String>();
			while (paraName.hasMoreElements()) {
	            String pName = (String) paraName.nextElement();
	            if("sign".equals(pName))
	            	continue;
	            String pValue = request.getParameter(pName);
	            if(pValue != null && pValue.length() > 0){
	            	map.put(pName, pValue);
	            	arr.add(pName);
	            }
	        }
			Collections.sort(arr);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arr.size(); i++) {
				if(i > 0)
					sb.append("&");
				sb.append(arr.get(i));
				sb.append("=");
				sb.append(map.get(arr.get(i)));
			}
			MD5 md5 = new MD5();
			String result = md5.getMD5ofStr(sb.toString());
			if(!sign.equals(result)){
				response.setContentType("text/html; charset=UTF-8");
		        response.getWriter().print("{\"message\":\"认证失败，请重新登录！\"}");
		        response.setStatus(401);
				return;
			}*/
			//验证token
			String id = stringRedisTemplate.opsForValue().get("token"+token);
			if(id != null){
				try {
					request.setAttribute("loginId", id);
					chain.doFilter(request, response);
				} catch (Exception e) {
					response.setContentType("text/html; charset=UTF-8");
			        response.getWriter().print("{\"message\":\"系统出错，请稍后再试\"}");
			        response.setStatus(500);
				}
				return;
			}else{
				response.setContentType("text/html; charset=UTF-8");
		        response.getWriter().print("{\"message\":\"认证失败，请重新登录！\"}");
		        response.setStatus(401);
				return;
			}
		}else{
			chain.doFilter(request, response);
		}
	}
	
	@Override
	public void destroy() {
		
	}
}

