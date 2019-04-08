package com.nongyeos.loan.admin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nongyeos.loan.admin.aop.RecordLog;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysPersonRole;
import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.IRoleService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Base64Util;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller  
@RequestMapping("/person")
public class PersonController {
	
	@Resource 
	private IPersonService personService;
	@Resource 
	private IWebUserService userService;
	@Resource 
	private IRoleService roleService;
    @Resource 
    private ISysSenoService sysSenoService;
	
    @RequestMapping("/personList")
    @ResponseBody
    public PageBeanUtil<SysPerson> usersPage(int limit,int offset,String orgId) throws Exception{
    	PageBeanUtil<SysPerson> list = personService.selectByPage(limit,offset,orgId);
    	if(list != null){
		    return list;
    	}else {
    		return null;
    	}
    }
    
    @RequestMapping("/addPerson")
    @ResponseBody
    @RecordLog(description = "操作人员")
    public Map<String,Object> save(SysPerson sysPerson,SysWebUser user,String detaile) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
		//判断重复
		if(userService.existedSameName(user))
		{
			map.put("msg", "该账号已存在，请重新填写！");
		}
		else 
		{
			if((user.getUserId()==null || user.getUserId().equals("")) && sysPerson.getPersonId().equals(""))
			{
		        //盐值
		        ByteSource salt = ByteSource.Util.bytes(user.getUsername());
		        
				user.setUserId(sysSenoService.getNextString(Constants.TABLE_NAME_USER, 10, "U", 1));
				user.setPassword(String.valueOf(new SimpleHash("MD5", user.getPassword(), salt, 1024)));
				user.setType(Constants.USER_TYPE_PRINCIPAL);
				user.setStatus((short) 1);
				userService.addUser(user);
    			sysPerson.setPersonId(sysSenoService.getNextString(Constants.TABLE_NAME_PERSON, 10, "P", 1));
    			sysPerson.setUserId(user.getUserId());
    			personService.addPerson(sysPerson);
				map.put("msg", "添加成功");
			}
			else
			{
				SysWebUser userOld = userService.selectUserById(user.getUserId());
				user.setLastIp(userOld.getLastIp());
				user.setLastTime(userOld.getLastTime());
				//删除老数据，清理脏数据
				SysPerson person = personService.selectByPersonId(sysPerson.getPersonId());
				if(person != null && person.getFilePath() != null){
					String[] oldFilePath = person.getFilePath().split(";");
					for (String filepath : oldFilePath) {
						if(filepath != null && !"".equals(filepath)){
							File file  = new File(filepath);
							if(file.exists()){
								file.delete();
							}
						}
					}
				}
				if(user.getPassword().length() == 32){
					userService.updateUser(user);
					personService.updatePerson(sysPerson);
				}else{
					//盐值
			        ByteSource salt = ByteSource.Util.bytes(user.getUsername());
					user.setPassword(String.valueOf(new SimpleHash("MD5", user.getPassword(), salt, 1024)));
					userService.updateUser(user);
					personService.updatePerson(sysPerson);
				}
				map.put("code", 10);
				map.put("msg", "修改成功");
			}
		}
		return map;
    }
    
    @RequestMapping("/delPerson")
    @ResponseBody
    public Map<String,Object> delete(String personIds) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(personIds != null && !personIds.equals("")){
    		String[] personIds2 = personIds.split(",");
    		for(String personId : personIds2){
    			SysPerson person = personService.selectByPersonId(personId);
    			roleService.delByPersonId(personId);
    			personService.delPersonorg(personId);
    			personService.deletePerson(personId);
    			userService.deleteUser(person.getUserId());
    		}
    		map.put("msg", "删除成功");
    	}else{
    		map.put("msg", "删除失败");
    	}
    	return map;
    }
    
    @RequestMapping("/getRoleByPerson")
    @ResponseBody
    public Map<String, Object> getRoleByPerson(String personId) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	String objectIdString = "";
    	List<SysPersonRole> list = personService.getRoles(personId);
    	if(list != null && list.size()>0){
			SysPersonRole beanPersonRole = null;
			for(int i = 0; i < list.size(); i++){
				beanPersonRole = list.get(i);
				if(objectIdString.equals("")){
					objectIdString = beanPersonRole.getRoleId();
				}else{
					objectIdString += "," + beanPersonRole.getRoleId();
				}
			}
		}
    	map.put("objectIds", objectIdString);
    	return map;
    }
    
    @RequestMapping("/getOrgByPerson")
    @ResponseBody
    public Map<String,Object> getOrgByPerson(String personId) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	String objectIdString = "";
    	List<SysPersonorg> list = personService.getOrgs(personId);
    	if(list != null && list.size()>0){
    		SysPersonorg bean = null;
			for(int i = 0; i < list.size(); i++){
				bean = list.get(i);
				if(objectIdString.equals("")){
					objectIdString = bean.getOrgId();
				}else{
					objectIdString += "," + bean.getOrgId();
				}
			}
		}
    	map.put("objectIds", objectIdString);
    	return map;
    }
    
    @RequestMapping("/saveOrgByPerson")
    @ResponseBody
    public Map<String,Object> addOrgAndPerson(String orgIds,String personId) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(personId != null && !personId.equals("")){
    		personService.delPersonorg(personId);
    		if(orgIds != null && !orgIds.equals("")){
    			SysPersonorg bean = new SysPersonorg();
    			String[] orgIds2 = orgIds.split(",");
    			for(String orgId : orgIds2){
    				bean.setPersonId(personId);
    				bean.setOrgId(orgId);
    				personService.addPersonorg(bean);
    			}
    		}
    		map.put("msg", "保存成功");
    	}else{
			map.put("msg", "保存失败");
		}
		return map;
    }
    
    @RequestMapping(value = "upload")
    @ResponseBody
	public String upload(HttpServletRequest request,MultipartFile file) throws IllegalStateException, IOException {
		String str = "";
		try{
			String name = file.getOriginalFilename();
			String path = request.getServletContext().getRealPath("/upload/");// 上传保存的路径
			String dividerSign = "";
			String osName = System.getProperties().getProperty("os.name"); // 操作系统名称
			if (osName.indexOf("Linux") != -1) {
				path = Constants.UPLOAD_FILE_ROOT_Linux;
				dividerSign = "/";
			} else if (osName.indexOf("Windows") != -1) {
				path = Constants.UPLOAD_FILE_ROOT_Windows;
				dividerSign = "/";
			}
			String fileName = generateFileName(name);
			String filePath = path + dividerSign;
			// 创建文件夹
			File fileFolder = new File(filePath); // new file;
			// 判断文件夹是否存在
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
			fileName = path + dividerSign + fileName;
			fileName = new String(fileName.getBytes(),"utf-8");

//			File file1 = new File(fileName);
//			file.transferTo(file1);
			File oldFile = new File(fileName);
				try {	
					if(!oldFile.exists()){
						oldFile.createNewFile();
						FileOutputStream fos = new FileOutputStream(oldFile);
				        byte[] f = file.getBytes();
				        fos.write(f); 
				        fos.close();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			str = "{\"src\":\"" + fileName + "\"}";
			System.out.println(str);
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
    
    @PostMapping("/getImage")
	@ResponseBody
	public List<String> getImage(HttpServletRequest request) {

		String personId = request.getParameter("personId");
		String imgUrl = "";
		String[] imgUrlList;
		List<String> list = new ArrayList<String>();
		SysPerson sysP = new SysPerson();
		try {
			if (personId != null && !personId.equals(""))
			{
				sysP = personService.selectByPersonId(personId);
				imgUrl = sysP.getFilePath();
				if(imgUrl != null && !"".equals(imgUrl))
				{
					imgUrlList = imgUrl.split(";");
					for(int i = 0; i < imgUrlList.length; i++)
					{
						list.add(Base64Util.file2Str(imgUrlList[i]));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
    
    /** 以时间生成文件名方法(解决上传相同文件和并发上传的问题) */
	private String generateFileName(String fileName) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HHmmss"); // 指定格式
		String formDate = format.format(new Date());// 格式化当前时间
		int random = new Random().nextInt(10000);// 生成一个四位数的随机数
		String extension = fileName.substring(fileName.lastIndexOf("."));// 得到文件的后缀
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		return "[" + fileName + "]" + formDate + random + extension;
	}

}
