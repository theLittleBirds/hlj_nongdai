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
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysOrgApplication;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysPersonRole;
import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.base.util.Base64Util;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.JsonUtil;

@Controller  
@RequestMapping("/org")
public class OrgController {
	
	@Resource
	private IOrgService orgService;
	
	@Resource
	private IPersonService personService;

	
	private static final Logger logger = LogManager.getLogger(OrgController.class);

	
	@RequestMapping(value = "orgList", method = RequestMethod.POST)
	@ResponseBody
	public String orgTree(Boolean firstLevel,HttpSession session) throws Exception{
		String personId = (String) session.getAttribute(Constants.SESSION_PERSONCD);
		String orgtree;		
		orgtree = orgService.getListByParentId(personId, firstLevel, false);//得到一级组织机构
		if (orgtree != null && !orgtree.equals("")) {
			return orgtree;
		}else{
			return null;
		}
	}

	@RequestMapping(value = "orgInfo", method = RequestMethod.POST)
	@ResponseBody
	public String info(String orgid) throws Exception {
		try {
			if (orgid != null) {
				String strJson = "";
				SysOrg orgInfo = orgService.selectByOrgId(orgid);
				strJson = JsonUtil.bean2json(orgInfo);
				return strJson;
			}else {
				throw new Exception("orgid为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@RequestMapping(value = "orgSave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> save(SysOrg sysOrg,HttpSession session,String productBrand) throws Exception{
		System.err.println(productBrand);
		String personId = (String) session.getAttribute(Constants.SESSION_PERSONCD);
		short personRoleType = (short) session.getAttribute(Constants.SESSION_PERSONTYPE);
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysPerson> list = new ArrayList<>();
		List<SysPersonRole> list2 = new ArrayList<>();
		SysPersonorg bean = new SysPersonorg();
		if(sysOrg != null){
			if(sysOrg.getOrgId() == null || sysOrg.getOrgId().equals("")){
				SysOrg sysorg = orgService.selectByName(sysOrg.getFullCname());
				if(sysorg != null){
					map.put("msg", "机构名称重复，请重新填写！");
				}else{
					if((personRoleType != 2 && sysOrg.getParentOrgId() == null) || (personRoleType != 2 && sysOrg.getParentOrgId().equals(""))){
						map.put("msg", "没有权限建立平级机构");
						return map;
					}
					orgService.addOrg(sysOrg);
					//给当前创建人授权机构
					bean.setOrgId(sysOrg.getOrgId());
					bean.setPersonId(personId);
					personService.addPersonorg(bean);
					//给所有的系统管理员授权机构
					list2= personService.getPersonByRole(Constants.ROLE_ADMIN);
					if(list2 != null && list2.size() > 0){
						for(int i = 0;i < list2.size();i++){
							if(!list2.get(i).getPersonId().equals(personId)){  //防止admin添加2次
								bean.setOrgId(sysOrg.getOrgId());
								bean.setPersonId(list2.get(i).getPersonId());
								personService.addPersonorg(bean);
							}
						}
					}
					//给上级机构所有的机构管理员授权机构
					String parentOrgIds = sysOrg.getParentOrgIds();
					if(parentOrgIds != null && !parentOrgIds.equals("")){
						String[] orgIds = parentOrgIds.split(",");
						for(int i = 0;i < orgIds.length; i++){
							list = personService.selectByPage(orgIds[i]);
							if(list != null && list.size() > 0){
								for(int j = 0;j < list.size();j++){
									if(list.get(j).getRoleType() != null 
											&& list.get(j).getRoleType() == Constants.PERSON_ROLE_ORG
											&& !list.get(j).getPersonId().equals(personId)){
										bean.setOrgId(sysOrg.getOrgId());
										bean.setPersonId(list.get(j).getPersonId());
										personService.addPersonorg(bean);
									}
								}
						    }
						}
					}
					//更新session
					List<SysPersonorg> orgList = orgService.getOrgByPersonId(personId);
					session.setAttribute(Constants.SESSION_ORGLIST, orgList);
					map.put("msg", "添加成功");
				}
			}else{
				//删除老数据，清理脏数据
				SysOrg org = orgService.selectByOrgId(sysOrg.getOrgId());
				if(org != null && org.getFilepath() != null){
					String[] oldFilePath = org.getFilepath().split(";");
					for (String filepath : oldFilePath) {
						if(filepath != null && !"".equals(filepath)){
							File file  = new File(filepath);
							if(file.exists()){
								file.delete();
							}
						}
					}
				}
				orgService.updateOrg(sysOrg);
				map.put("msg", "修改成功");
			}
		}else{
			map.put("msg", "操作失败");
		}
		return map;
	}
	
	@RequestMapping(value = "orgDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete(String orgId,HttpSession session) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String personId = (String) session.getAttribute(Constants.SESSION_PERSONCD);
		if(orgService.getChildOrgs(orgId).size() == 0 && personService.selectByPage(orgId).size() == 0){
			personService.delPersonorg2(orgId);  //删除机构人员关联表数据
			orgService.deleteByOrgId(orgId);     //删除机构
			List<SysPersonorg> orgList = orgService.getOrgByPersonId(personId);
			session.setAttribute(Constants.SESSION_ORGLIST, orgList);
			map.put("msg", "删除成功");
		}else{
			map.put("msg", "请先删除该机构下所属子机构和该机构下的人员！");
		}
		return map;
	}
	
	@RequestMapping(value = "getOrgDS", method = RequestMethod.POST)
	@ResponseBody
	public String getOrgDS()throws Exception{
		String strJson = "[";
		try{
			List<SysOrg> list = orgService.getFirstLevelList(null);
			if(list != null && list.size() > 0){
				SysOrg bean = null;
				for(int i = 0;i <list.size();i++){
					bean = list.get(i);
					if(bean != null){
						if(i>0)
	    					strJson = strJson + ", ";
	    				
	    				strJson = strJson + "{parameterName:'" + bean.getFullCname() + "', parameterValue:'" + bean.getOrgId() + "'}";
					}
				}
			}
			strJson = strJson + "]";
			return strJson;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "orgTreeStr", method = RequestMethod.POST)
	@ResponseBody
	public String orgTreeStr(HttpSession session) throws Exception{
		List<String> orgIdList = new ArrayList<>();
    	List<SysPersonorg> list = (List<SysPersonorg>) session.getAttribute(Constants.SESSION_ORGLIST);
    	String personId = (String) session.getAttribute(Constants.SESSION_PERSONCD);
    	if (personId.equals(Constants.ROLE_ADMIN)) {
			List<SysOrg> orgList = orgService.selectAll();
			for (SysOrg sysOrg : orgList) {
				orgIdList.add(sysOrg.getOrgId());
			}
		}else {
			for (SysPersonorg perorg : list) {
				orgIdList.add(perorg.getOrgId());
			}
		}
		return orgService.orgTreeString(orgIdList);
	}
	
	@RequestMapping("/saveAppByOrg")
    @ResponseBody
    public Map<String,Object> saveAppByOrg(String appIds,String orgId) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(orgId != null && !orgId.equals("")){
    		orgService.delOrgapp(orgId);
    		if(appIds != null && !appIds.equals("")){
    			SysOrgApplication bean = new SysOrgApplication();
    			String[] appIds2 = appIds.split(",");
    			for(String appId : appIds2){
    				bean.setOrgId(orgId);
    				bean.setAppId(appId);
    				orgService.addOrgapp(bean);
    			}
    		}
    		map.put("msg", "保存成功");
    	}else{
			map.put("msg", "保存失败");
		}
		return map;
    }
	
    @RequestMapping("/getAppByOrg")
    @ResponseBody
    public Map<String,Object> getAppByOrg(String orgId) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	String objectIdString = "";
    	List<SysOrgApplication> list = orgService.getOrgs(orgId);
    	if(list != null && list.size()>0){
    		SysOrgApplication bean = null;
			for(int i = 0; i < list.size(); i++){
				bean = list.get(i);
				if(objectIdString.equals("")){
					objectIdString = bean.getAppId();
				}else{
					objectIdString += "," + bean.getAppId();
				}
			}
		}
    	map.put("objectIds", objectIdString);
    	return map;
    }
    
    @RequestMapping(value = "upload")
    @ResponseBody
	public String upload(HttpServletRequest request,MultipartFile file) throws IllegalStateException, IOException {
		String str = "";
		try{
			String filename = file.getOriginalFilename();
			String extension = filename.substring(filename.lastIndexOf("."));// 得到文件的后缀
			long sysTime = System.currentTimeMillis(); 
			String name = String.valueOf(sysTime) + extension;
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

		String orgId = request.getParameter("orgId");
		String imgUrl = "";
		String[] imgUrlList;
		List<String> list = new ArrayList<String>();
		SysOrg sysO = new SysOrg();
		try {
			if (orgId != null && !orgId.equals(""))
			{
				sysO = orgService.selectByOrgId(orgId);
				imgUrl = sysO.getFilepath();
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
