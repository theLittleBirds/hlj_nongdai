package com.nongyeos.loan.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;  
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller  
@RequestMapping("/user")
public class UserController {  
    @Resource  
    private IWebUserService userService; 
    @Resource  
    private IPersonService personService;
    @Resource  
    private IOrgService orgService;
    @Resource 
    private ISysSenoService sysSenoService;
    @Resource 
    private IWebUserService webUserService;
    
    private static final Logger logger = LogManager.getLogger(UserController.class);
    
    @RequestMapping(value = "main", method = RequestMethod.GET)
	public String mainOpen(HttpServletRequest request, HttpServletResponse response,Model model) {
    	String channel = request.getHeader("from");
    	model.addAttribute("tittle", "农鲸");
    	model.addAttribute("ico", "favicon.ico");
    	if(!StringUtils.isEmpty(channel)){
    		if(channel.equals("HLJSX")){
    			model.addAttribute("tittle", "首信");
    			model.addAttribute("ico", "shouxinfavicon.ico");
    		}else if(channel.equals("AJ")){
    			model.addAttribute("tittle", "安家世行");
    			model.addAttribute("ico", "anjiafavicon.ico");
    		}
    	}
		return "admin/main";
	}
    
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request,String uname,String pwd,String codes) throws Exception {  
    	
    	Session session = SecurityUtils.getSubject().getSession();
    	Map<String, Object> resultMap = new HashMap<String, Object>();
		String v = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
		if(!codes.equals(v)){
    		resultMap.put("status", 500);
			resultMap.put("message", "验证码错误！");
			return resultMap;
    	}
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(uname, pwd);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			resultMap.put("status", 200);
			resultMap.put("message", "登录成功");
			//登录成功后执行
			SysWebUser user=(SysWebUser) subject.getPrincipal();
			String userId = user.getUserId();
			String userName = user.getUsername();
			String personCode = "";
			String personName = "";
			short personRoleType = (short) 0;
			String orgId = "";
			String orgName = "";
			String baseOrgCode = "";
			List<SysPersonorg> orgList = new ArrayList<SysPersonorg>();
			SysPerson sysPerson = personService.selectByUserIdAndIsdefault(userId);
			if(sysPerson != null)
			{
				personName = sysPerson.getNameCn();
				personCode = sysPerson.getPersonId();
				if(sysPerson.getRoleType() != null){
					personRoleType = sysPerson.getRoleType();
				}
				
				SysOrg org = orgService.selectByOrgId(sysPerson.getOrgId());
				if(org != null)
				{
					orgId = org.getOrgId();
					orgName = org.getFullCname();
					baseOrgCode = orgService.getBaseOrgId(orgId);
				}
				
				orgList = orgService.getOrgByPersonId(personCode);
			}
		
			//记录session
			session.setAttribute(Constants.SESSION_USERCD, userId);
			session.setAttribute(Constants.SESSION_PERSONCD, personCode);
			session.setAttribute(Constants.SESSION_ORGCD, orgId);
			session.setAttribute(Constants.SESSION_ORGNM, orgName);
			session.setAttribute(Constants.SESSION_ORGCDBASE, baseOrgCode);
			session.setAttribute(Constants.SESSION_USERNM, userName);
			session.setAttribute(Constants.SESSION_PERSONNM, personName);
			session.setAttribute(Constants.SESSION_PERSONTYPE, personRoleType);
			session.setAttribute(Constants.SESSION_ORGLIST, orgList);
			session.setAttribute("personName", personName);
			session.setAttribute("userName", userName);
			
			String lastIp = request.getRemoteAddr();
			user.setLastIp(lastIp);
			user.setLastTime(new Date());
			userService.updateUser(user);

		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", 500);
			resultMap.put("message", "用户密码错误!");
		}

	    return resultMap;  
    }  
    
    /**
     * 分页功能(集成mybatis的分页插件pageHelper实现)
     * 
     * @param currentPage    :当前页数
     * @param pageSize       :每页显示的总记录数
     * @return
     * @throws Exception 
     */
    @RequestMapping("/userList")
    @ResponseBody
    public PageBeanUtil<SysWebUser> usersPage(int currentPage,int pageSize) throws Exception{
    	
    	PageBeanUtil<SysWebUser> list = userService.selectByPage(currentPage, pageSize);
    	if(list != null)
		    return list;
    	else 
    		return null;
    }
    
    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(HttpServletResponse response,HttpServletRequest request,SysWebUser user) throws Exception{
		//MD5 md5 = new MD5();
		Map<String, Object> map = new HashMap<String, Object>();
		//判断重复
		if(userService.existedSameName(user))
		{
			map.put("msg", "该账号已存在，请重新填写！");
		}
		else 
		{
			if(user.getUserId()==null || user.getUserId().equals(""))
			{
		        //盐值
		        ByteSource salt = ByteSource.Util.bytes(user.getUsername());
		        
				user.setUserId(sysSenoService.getNextString(Constants.TABLE_NAME_USER, 10, "U", 1));
				//user.setPassword(md5.getMD5ofStr(user.getPassword()));
				user.setPassword(String.valueOf(new SimpleHash("MD5", user.getPassword(), salt, 1024)));
				user.setType(Constants.USER_TYPE_PRINCIPAL);
				userService.addUser(user);
				map.put("msg", "添加成功");
			}
			else 
			{
				SysWebUser userOld = userService.selectUserById(user.getUserId());
				user.setLastIp(userOld.getLastIp());
				user.setLastTime(userOld.getLastTime());
				if(user.getPassword().length() == 32){
					userService.updateUser(user);
				}
				else{
					//盐值
			        ByteSource salt = ByteSource.Util.bytes(user.getUsername());
					user.setPassword(String.valueOf(new SimpleHash("MD5", user.getPassword(), salt, 1024)));
					userService.updateUser(user);
				}
				map.put("code", 10);
				map.put("msg", "更新成功");
			}
		}
		return map;
    }
    
    @RequestMapping("/delUser")
    @ResponseBody
    public Map<String, Object> delete(String currIds)throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(currIds != null && !currIds.equals("")){
    		String[] userIds = currIds.split(",");
    		for(String userId : userIds){
				userService.deleteUser(userId);
    		}
    		map.put("msg", "删除成功");
    	}else{
    		map.put("msg", "删除失败");
    	}
    	return map;
    }
    
    @RequestMapping("/userDS")
    @ResponseBody
    public String getUserDS() throws Exception{
    	String strJson = "[";
    	List<SysWebUser> list = userService.selectAll();
    	if(list != null && list.size() > 0){
    		SysWebUser bean = null;
    		for(int i=0;i<list.size();i++)
    		{
    			bean = list.get(i);
    			if(bean != null)
    			{
    				if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + bean.getUsername() + "', parameterValue:'" + bean.getUserId() + "'}";
    			}
    		}
    	}
    	strJson = strJson + "]";
		return(strJson);
    }
    
    @RequestMapping("/getUserByPerson")
    @ResponseBody
    public SysWebUser getUser(String userId) throws Exception{
    	SysWebUser user = userService.selectUserById(userId);
    	return user;
    }
    
    @RequestMapping("/updatepwd")
    @ResponseBody
    public Map<String, Object> updatepwd(HttpServletRequest request,String pwd,String pwd1) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	String userId = request.getSession().getAttribute(Constants.SESSION_USERCD).toString();
    	if(userId != null){
    		SysWebUser userBean = userService.selectUserById(userId);
    		//盐值
    		ByteSource salt = ByteSource.Util.bytes(userBean.getUsername());  
    		//校验原密码
    		if(userBean.getPassword().equals(String.valueOf(new SimpleHash("MD5", pwd, salt, 1024)))){
    			//校验新密码
    			if(!userBean.getPassword().equals(String.valueOf(new SimpleHash("MD5", pwd1, salt, 1024)))){
    				userBean.setPassword(String.valueOf(new SimpleHash("MD5", pwd1, salt, 1024)));
    				userService.updateUser(userBean);
    				map.put("message", "修改成功");	
    			}else{
    				map.put("message", "新密码与原密码一致，请重新输入");	
    			}
    		}else{
    			map.put("message", "原密码错误");
    		}
    	}else{
    		map.put("message", "修改失败，用户登录异常！");
    	}
    	return map;
    }
    
}  
