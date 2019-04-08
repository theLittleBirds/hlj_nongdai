package com.nongyeos.loan.admin.aop;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nongyeos.loan.admin.entity.SysActLog;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.mapper.SysActLogMapper;
import com.nongyeos.loan.base.util.Constants;

@Aspect
@Component
public class OperationLog {

	@Autowired
	private SysActLogMapper actLogMapper;

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 切入点设置到自定义的RecordLog注解上
	@Pointcut("@annotation(com.nongyeos.loan.admin.aop.RecordLog)")
	public void webLog() {
	}

	@After(value = "webLog()")
	public void methodBefore(JoinPoint joinPoint) {
		SysActLog actLog = new SysActLog();
		//获取session并取出其中的值
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		String userCode = (String) session.getAttribute(Constants.SESSION_USERCD);
		String personCode = (String) session.getAttribute(Constants.SESSION_PERSONCD);
		String personName = (String) session.getAttribute(Constants.SESSION_PERSONNM);
		String orgCode = (String) session.getAttribute(Constants.SESSION_ORGCD);
		String orgName = (String) session.getAttribute(Constants.SESSION_ORGNM);
		String ip = request.getRemoteAddr(); 
		String actObject = "";
		
		//记录操作内容
        Map map = request.getParameterMap();
        Set keSet = map.entrySet();
        for(Iterator itr = keSet.iterator();itr.hasNext();){
            Map.Entry me= (Map.Entry)itr.next();
            Object ok = me.getKey();
            Object ov = me.getValue();
            String[] value = new String[1];
            if(ov instanceof String[]){
                value = (String[])ov;
            }else{
                value[0] = ov.toString();
            }
            for(int k=0;k<value.length;k++){
            	//System.out.println(ok+"="+value[k]);
            	if(ok != null && ok.toString().equals("detaile"))
            	{
            		actObject += value[k]+",";
            		actObject = actObject.substring(0,actObject.length()-1);
            	}
            }
        }
        Map<String, String> map0;
        if(userCode != null && !userCode.equals("")){  
            try{  
                //记录日志
            	actLog.setUserCode(userCode);
        		actLog.setPersonCode(personCode);
        		actLog.setPersonName(personName);
        		actLog.setOrgCode(orgCode);
        		actLog.setOrgName(orgName);
        		actLog.setIp(ip);
        		// 下面设置日志中的操作类型，操作内容，操作时间等信息
    			map0 = getLogMark(joinPoint);
    			actLog.setActEvent(map0.get("description"));
    			actLog.setActObject(actObject);
    			actLog.setActTime(new Date());
    			actLogMapper.insert(actLog);
            }catch(Exception ex){  
                logger.error(ex.getMessage());  
            }
        }
		
		/*ServletRequestAttributes requestAttributes =
		(ServletRequestAttributes)
		RequestContextHolder.getRequestAttributes(); HttpServletRequest
		request = requestAttributes.getRequest(); //打印请求内容
		logger.info("===============请求内容===============");
		logger.info("请求地址:"+request.getRequestURL().toString());
		logger.info("请求方式:"+request.getMethod());
		logger.info("请求类方法:"+joinPoint.getSignature());
		logger.info("请求类方法参数:"+ Arrays.toString(joinPoint.getArgs()));
		logger.info("===============请求内容===============");*/
	}

	private Map<String, String> getLogMark(JoinPoint joinPoint)
			throws ClassNotFoundException {
		Map<String, String> map = new HashMap<String, String>();
		String methodName = joinPoint.getSignature().getName();
		String targetName = joinPoint.getTarget().getClass().getName();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				RecordLog recordLog = method.getAnnotation(RecordLog.class);
				map.put("description", recordLog.description());
			}
		}
		return map;
	}

}
