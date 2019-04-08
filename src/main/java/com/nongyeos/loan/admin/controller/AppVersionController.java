package com.nongyeos.loan.admin.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.SysAppVersion;
import com.nongyeos.loan.admin.model.FileConfig;
import com.nongyeos.loan.admin.service.IAppVersionService;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;

@Controller
@RequestMapping("/appVersion")
public class AppVersionController {
	private static final Logger logger = LogManager.getLogger(AppVersionController.class);
	
	@Autowired
	private IAppVersionService appVersionService;
	
	@Autowired
	private IParaGroupService groupService;
	
	@Value("${web.url}")
	private String weburl;
	
	@Autowired
	private FileConfig fileConfig; 
	
	@Value("${rootpoint.mark}")
	private String appMark;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
//	@Autowired
//    private StringRedisTemplate stringRedisTemplate;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView();
		JSONObject obj = JSONObject.parseObject(appMark);
		Set<String> set = obj.keySet();
		Iterator<String> it = set.iterator();
		StringBuffer sb = new StringBuffer();
		sb.append("<option value=''>--请选择--</option>");
		while(it.hasNext()){
			String name = it.next();
			sb.append("<option value='"+name+"'>"+name+"</option>");
		}
		mv.addObject("app", sb.toString());
		mv.setViewName("appVersion/list");
		return mv;
	}
	
	/**
	 * 
	 * @Title: appList 
	 * @Description: 版本列表
	 * @param @param currentPage
	 * @param @param pageSize
	 * @param @param versionNumber
	 * @param @param type
	 * @param @return     
	 * @return PageBeanUtil<SysAppVersion>     
	 * @throws
	 */
	@RequestMapping("/appList")
	@ResponseBody
	public PageBeanUtil<SysAppVersion> appList(int currentPage,int pageSize,String versionNumber,String type){
		PageBeanUtil<SysAppVersion> pb = null;
		try {
			SysAppVersion sav = new SysAppVersion();
			if(!StringUtils.isEmpty(versionNumber)){
				sav.setVersionNumber(versionNumber);
			}
			if(!StringUtils.isEmpty(type)){
				sav.setType(new Integer(type));
			}
			sav.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			pb= appVersionService.selectByPage(currentPage,pageSize,sav);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pb;
	}
	/**
	 * 
	 * @Title: form 
	 * @Description: 保存或回显的form表单
	 * @param @param id
	 * @param @return
	 * @param @throws Exception     
	 * @return ModelAndView     
	 * @throws
	 */
	@RequestMapping("/form")
	public ModelAndView form(String id) throws Exception{
		ModelAndView modelAndView = new ModelAndView();
		try {
			SysAppVersion appVersion = new SysAppVersion();
			if(!StringUtils.isEmpty(id)){
				appVersion =appVersionService.selectById(id);
			}
			JSONObject obj = JSONObject.parseObject(appMark);
			Set<String> set = obj.keySet();
			Iterator<String> it = set.iterator();
			StringBuffer sb = new StringBuffer();
			sb.append("<option value=''>--请选择--</option>");
			while(it.hasNext()){
				String name = it.next();
				if(name.equals(appVersion.getChannel())){
					sb.append("<option selected = 'selected' value='"+name+"'>"+name+"</option>");
				}else{
					sb.append("<option value='"+name+"'>"+name+"</option>");
				}				
			}
			modelAndView.addObject("app", sb.toString());
			modelAndView.addObject("appVersion", appVersion);
			modelAndView.addObject("APP_OPERATING_SYSTEM", groupService.getSelectOption("APP_OPERATING_SYSTEM", appVersion.getType()==null?null:appVersion.getType().toString()));
			modelAndView.addObject("FORCE_UPDATE", groupService.getSelectOption("FORCE_UPDATE", appVersion.getForceUpdate()==null?null:appVersion.getForceUpdate().toString()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		modelAndView.setViewName("appVersion/form");
		return modelAndView;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject saveVsersion(MultipartFile apk,SysAppVersion appVersion,HttpServletRequest request,String fileName) throws Exception{
		JSONObject retJson = new JSONObject();
		try {
			String url =null;
			if(StringUtils.isEmpty(appVersion.getChannel())){
				retJson.put("code", 1000);
				retJson.put("msg", "请选择使用企业！");
				return retJson;
			}
			if(apk!=null){
                String saveName = generateRandomFilename();
                String extName = fileName.substring(fileName.lastIndexOf("."));
                String saveFileName = saveName + extName;
                String baseurl = fileConfig.getUploadBaseDir();
                FileUtils.createDirectory(baseurl);
                File pathDest = new File(baseurl, saveFileName);
                if (!pathDest.exists()) {
                	pathDest.createNewFile();
                   }
                FileOutputStream fos = new FileOutputStream(pathDest);
                byte[] f = apk.getBytes();
                fos.write(f); 
                fos.close();
                url = weburl+"/appVersion/download"+"?apk="+saveFileName;
			}
			
			SysAppVersion appVersion1 = appVersionService.selectByVersionNumber(appVersion);
			if(appVersion1!=null&&StringUtils.isEmpty(appVersion.getId())){
				retJson.put("code", 1000);
				retJson.put("msg", "已存在的版本号！");
				return retJson;
			}
			if(StringUtils.isEmpty(appVersion.getId())){
				appVersion.setCreDate(DateUtils.getNowDate());
				if(StringUtils.isNotEmpty(url))
					appVersion.setUrl(url);
				appVersion.setCreOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				appVersion.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				appVersion.setUpdDate(DateUtils.getNowDate());
				appVersion.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				appVersionService.saveOrUpdate(appVersion);
			}else{
				
				SysAppVersion selectById = appVersionService.selectById(appVersion.getId());
				if(selectById!=null){
					if(StringUtils.isNotEmpty(url))
						selectById.setUrl(url);
					selectById.setUpdDate(DateUtils.getNowDate());
					selectById.setVersionNumber(appVersion.getVersionNumber());
					selectById.setType(appVersion.getType());
					selectById.setForceUpdate(appVersion.getForceUpdate());
					selectById.setHost(appVersion.getHost());
					selectById.setComment(appVersion.getComment());
					selectById.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
					appVersionService.saveOrUpdate(selectById);
				}
			}
			HashOperations<String,String,Object> opsForHash = redisTemplate.opsForHash();	
			opsForHash.put(appVersion.getChannel()+"APP", "version", appVersion.getVersionNumber());
			opsForHash.put(appVersion.getChannel()+"APP", "url", appVersion.getUrl());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		retJson.put("code", 1001);
		retJson.put("msg", "保存成功！");
		return retJson;
	}
	
	@RequestMapping("/del")
	public String delVersion(String id,HttpServletRequest request) throws Exception{
		try {
			SysAppVersion appVersion = appVersionService.selectById(id);
			appVersion.setIsDeleted(Constants.COMMON_IS_DELETE);
			appVersion.setUpdDate(DateUtils.getNowDate());
			appVersion.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			appVersionService.delVersion(appVersion);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "redirect:/appVersion/index";
	}
	
	/*
     * 生成随机文件名  
     */
    private String generateRandomFilename() {
        String randomFilename = "";
        Random rand = new Random();//生成随机数
        int random = rand.nextInt();

        Calendar calCurrent = Calendar.getInstance();
        int intDay = calCurrent.get(Calendar.DATE);
        int intMonth = calCurrent.get(Calendar.MONTH) + 1;
        int intYear = calCurrent.get(Calendar.YEAR);
        int intHour = calCurrent.get(Calendar.HOUR_OF_DAY);
        int intMinute = calCurrent.get(Calendar.MINUTE);
        int intSecond = calCurrent.get(Calendar.SECOND);
        String now = String.valueOf(intYear) + String.valueOf(intMonth) + String.valueOf(intDay)
                + String.valueOf(intHour) + String.valueOf(intMinute) + String.valueOf(intSecond);
        randomFilename = now + String.valueOf(random > 0 ? random : (-1) * random);

        return randomFilename;
    }
	
    @RequestMapping("/load")
    public String appLoad(String channel,HttpServletResponse response){
    	try {
			if(StrUtils.isEmpty(channel))
				return null;
			SysAppVersion para = new SysAppVersion();
			para.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			para.setChannel(channel);
			para.setType(Constants.ANDROID);
			SysAppVersion app = appVersionService.selectNewest(para);
			if(app == null)
				return null;
			String fileName = app.getUrl().substring(app.getUrl().indexOf("=")+1);
			if (fileName != null) {
	            //设置文件路径
	            String realPath = fileConfig.getUploadBaseDir();
	            File file = new File(realPath , fileName);
	            if (file.exists()) {
	                response.setContentType("application/force-download");// 设置强制下载不打开
	                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
	                byte[] buffer = new byte[1024];
	                FileInputStream fis = null;
	                BufferedInputStream bis = null;
	                try {
	                    fis = new FileInputStream(file);
	                    bis = new BufferedInputStream(fis);
	                    OutputStream os = response.getOutputStream();
	                    int i = bis.read(buffer);
	                    while (i != -1) {
	                        os.write(buffer, 0, i);
	                        i = bis.read(buffer);
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                } finally {
	                    if (bis != null) {
	                        try {
	                            bis.close();
	                        } catch (IOException e) {
	                            e.printStackTrace();
	                        }
	                    }
	                    if (fis != null) {
	                        try {
	                            fis.close();
	                        } catch (IOException e) {
	                            e.printStackTrace();
	                        }
	                    }
	                }
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    @RequestMapping("/download")
    public String downloadFile(String apk,HttpServletRequest request, HttpServletResponse response) {
        String fileName = apk;// 设置文件名，根据业务需要替换成要下载的文件名
        if (fileName != null) {
            //设置文件路径
            String realPath = fileConfig.getUploadBaseDir();
            File file = new File(realPath , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
    
}
