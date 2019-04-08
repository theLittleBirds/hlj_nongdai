package com.nongyeos.loan.admin.controller.nj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusMedia;
import com.nongyeos.loan.admin.service.IMediaService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/nj/media")
public class CMediaController {
	
	@Autowired
	private IMediaService mediaService;
	
	@Value("${fileupload.baseDir}")
	private String baseDir;
	
	@Value("${web.url}")
	private String weburl;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CMediaController.class);
	
	/**
	 * 
	 * @Title: saveMedia 
	 * @Description: 保存图片
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject saveMedia(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		try {
			BufferedReader reader = request.getReader();
	    	String str,wholeStr ="";
	    	while((str=reader.readLine())!=null){
	    		wholeStr+=str;
	    	}
	    	JSONObject jsonpara = JSONObject.parseObject(wholeStr);
	    	String intoPieceId = jsonpara.getString("intoPieceId");
			if(StringUtils.isEmpty(intoPieceId)){
					retJson.put("message", "未提供进件标识！");
					retJson.put("errno", 3001);
					response.setStatus(400);
					return retJson;
			}
			BusMedia mediaModel = new BusMedia();
			mediaModel.setIntoPieceId(intoPieceId);
			mediaModel.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			//证件类: 身份证、结婚证、户口本
			String CERTIFICATES = jsonpara.getString("CERTIFICATES");
			JSONArray CERTIFICATESarr = JSONArray.parseArray(CERTIFICATES);
			if(CERTIFICATESarr!=null&&CERTIFICATESarr.size()>0){
				for (int i = 0; i < CERTIFICATESarr.size(); i++) {
					JSONObject jsonObject = CERTIFICATESarr.getJSONObject(i);
					String qiniuKey = jsonObject.getString("qiniuKey");
					mediaModel.setQiniuKey(qiniuKey);
					int check = mediaService.existenceByKey(mediaModel);
					//如果存在，不再保存
					if(check>0){						
						continue;
					}
					mediaModel.setId(UUIDUtil.getUUID());
					mediaModel.setFileType(Constants.CERTIFICATES);
					mediaModel.setExtName("jpg");
					mediaModel.setType(Constants.MEDIATYPE_IMAGE);
					mediaModel.setCreDate(DateUtils.getNowDate());
					mediaModel.setCreOperId(loginId);
					mediaModel.setUpdDate(DateUtils.getNowDate());
					mediaModel.setUpdOperId(loginId);
					mediaService.insert(mediaModel);
				}
			}
			//资产类
			String ASSET = jsonpara.getString("ASSET");
			JSONArray ASSETarr = JSONArray.parseArray(ASSET);
			if(ASSETarr!=null&&ASSETarr.size()>0){
				for (int i = 0; i < ASSETarr.size(); i++) {
					JSONObject jsonObject =  ASSETarr.getJSONObject(i);
					String qiniuKey = jsonObject.getString("qiniuKey");
					mediaModel.setQiniuKey(qiniuKey);
					int check = mediaService.existenceByKey(mediaModel);
					//如果存在，不再保存
					if(check>0){
						continue;
					}
					mediaModel.setId(UUIDUtil.getUUID());
					mediaModel.setFileType(Constants.ASSET);
					mediaModel.setExtName("jpg");
					mediaModel.setType(Constants.MEDIATYPE_IMAGE);
					mediaModel.setCreDate(DateUtils.getNowDate());
					mediaModel.setCreOperId(loginId);
					mediaModel.setUpdDate(DateUtils.getNowDate());
					mediaModel.setUpdOperId(loginId);
					mediaService.insert(mediaModel);
				}
			}
			
			//土地证件类	
			String LAND = jsonpara.getString("LAND");
			JSONArray LANDarr = JSONArray.parseArray(LAND);
			if(LANDarr!=null&&LANDarr.size()>0){
				for (int i = 0; i < LANDarr.size(); i++) {
					JSONObject jsonObject = LANDarr.getJSONObject(i);
					String qiniuKey = jsonObject.getString("qiniuKey");
					mediaModel.setQiniuKey(qiniuKey);
					int check = mediaService.existenceByKey(mediaModel);
					//如果存在，不再保存
					if(check>0){
						continue;
					}
					mediaModel.setId(UUIDUtil.getUUID());
					mediaModel.setFileType(Constants.LAND);
					mediaModel.setExtName("jpg");
					mediaModel.setType(Constants.MEDIATYPE_IMAGE);
					mediaModel.setCreDate(DateUtils.getNowDate());
					mediaModel.setCreOperId(loginId);
					mediaModel.setUpdDate(DateUtils.getNowDate());
					mediaModel.setUpdOperId(loginId);
					mediaService.insert(mediaModel);
				}
			}
			//信用报告类
			String CREDIT = jsonpara.getString("CREDIT");
			JSONArray CREDITarr = JSONArray.parseArray(CREDIT);
			if(CREDITarr!=null&&CREDITarr.size()>0){
				for (int i = 0; i < CREDITarr.size(); i++) {
					JSONObject jsonObject =  CREDITarr.getJSONObject(i);
					String qiniuKey = jsonObject.getString("qiniuKey");
					mediaModel.setQiniuKey(qiniuKey);
					int check = mediaService.existenceByKey(mediaModel);
					//如果存在，不再保存
					if(check>0){
						continue;
					}
					mediaModel.setId(UUIDUtil.getUUID());
					mediaModel.setFileType(Constants.CREDIT);
					mediaModel.setExtName("jpg");
					mediaModel.setType(Constants.MEDIATYPE_IMAGE);
					mediaModel.setCreDate(DateUtils.getNowDate());
					mediaModel.setCreOperId(loginId);
					mediaModel.setUpdDate(DateUtils.getNowDate());
					mediaModel.setUpdOperId(loginId);
					mediaService.insert(mediaModel);
				}
			}
			
			//人物类
			String PERSON = jsonpara.getString("PERSON");
			JSONArray PERSONarr = JSONArray.parseArray(PERSON);
			if(PERSONarr!=null&&PERSONarr.size()>0){
				for (int i = 0; i < PERSONarr.size(); i++) {
					JSONObject jsonObject =  PERSONarr.getJSONObject(i);
					String qiniuKey = jsonObject.getString("qiniuKey");
					mediaModel.setQiniuKey(qiniuKey);
					int check = mediaService.existenceByKey(mediaModel);
					//如果存在，不再保存
					if(check>0){
						continue;
					}
					mediaModel.setId(UUIDUtil.getUUID());
					mediaModel.setFileType(Constants.PERSON);
					mediaModel.setExtName("jpg");
					mediaModel.setType(Constants.MEDIATYPE_IMAGE);
					mediaModel.setCreDate(DateUtils.getNowDate());
					mediaModel.setCreOperId(loginId);
					mediaModel.setUpdDate(DateUtils.getNowDate());
					mediaModel.setUpdOperId(loginId);
					mediaService.insert(mediaModel);
				}
			}
			//其他
			String OTHER = jsonpara.getString("OTHER");
			JSONArray OTHERarr = JSONArray.parseArray(OTHER);
			if(OTHERarr!=null&&OTHERarr.size()>0){
				for (int i = 0; i < OTHERarr.size(); i++) {
					JSONObject jsonObject =  OTHERarr.getJSONObject(i);
					String qiniuKey = jsonObject.getString("qiniuKey");
					mediaModel.setQiniuKey(qiniuKey);
					int check = mediaService.existenceByKey(mediaModel);
					//如果存在，不再保存
					if(check>0){
						continue;
					}
					mediaModel.setId(UUIDUtil.getUUID());
					mediaModel.setFileType(Constants.OTHER);
					mediaModel.setExtName("jpg");
					mediaModel.setType(Constants.MEDIATYPE_IMAGE);
					mediaModel.setCreDate(DateUtils.getNowDate());
					mediaModel.setCreOperId(loginId);
					mediaModel.setUpdDate(DateUtils.getNowDate());
					mediaModel.setUpdOperId(loginId);
					mediaService.insert(mediaModel);
				}
			}

			retJson.put("message", "保存成功！");
			retJson.put("errno", 0);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "保存失败！");
			response.setStatus(400);
			return retJson;
		}
		return retJson;
	}
	
	/**
	 * 
	 * @Title: mediaInfo 
	 * @Description: 图片回显
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/mediaInfo")
	@ResponseBody
	public JSONObject mediaInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String intoPieceId = request.getParameter("intoPieceId");
		if(StringUtils.isEmpty(intoPieceId)){
				retJson.put("message", "未提供进件标识！");
				retJson.put("errno", 3001);
				response.setStatus(400);
				return retJson;
		}
		try {
			BusMedia media = new BusMedia();
			media.setIntoPieceId(intoPieceId);
			media.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			List<BusMedia> medias = mediaService.selectByIpId(media);
			JSONArray CERTIFICATES = new JSONArray();
	        JSONArray ASSET = new JSONArray();
	        JSONArray LAND = new JSONArray();
	        JSONArray CREDIT = new JSONArray();
	        JSONArray PERSON = new JSONArray();
	        JSONArray OTHER = new JSONArray();
	        String image_url = "";//缩略图
            String original_url = "";//原图
			if(medias!=null&&medias.size()>0){
				for (int i = 0; i < medias.size(); i++) {
					JSONObject obj1 = new JSONObject();
					BusMedia busMedia = medias.get(i);
					image_url = QiNiuUtil.getUrlLimit10k(busMedia.getQiniuKey());
                    original_url = QiNiuUtil.getUrl(busMedia.getQiniuKey());
                    obj1.put("image_url", image_url);
					obj1.put("original_url", original_url);
					obj1.put("id", busMedia.getId());
					if(Constants.CERTIFICATES==busMedia.getFileType()){
						CERTIFICATES.add(obj1);
					}else if(Constants.ASSET==busMedia.getFileType()){
						ASSET.add(obj1);
					}else if(Constants.LAND==busMedia.getFileType()){
						LAND.add(obj1);
					}else if(Constants.CREDIT==busMedia.getFileType()){
						CREDIT.add(obj1);
					}else if(Constants.PERSON==busMedia.getFileType()){
						PERSON.add(obj1);
					}else if(Constants.OTHER==busMedia.getFileType()){
						OTHER.add(obj1);
					}
				}
			}
			JSONObject obj = new JSONObject();
			obj.put("CERTIFICATES", CERTIFICATES);
			obj.put("ASSET", ASSET);
			obj.put("LAND", LAND);
			obj.put("CREDIT", CREDIT);
			obj.put("PERSON", PERSON);
			obj.put("OTHER", OTHER);
			retJson.put("data", obj);
			retJson.put("errno", 0);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "系统出错！");
			response.setStatus(400);
			return retJson;
		}
		return retJson;
	}
	
	@RequestMapping("/deleteMeida")
	@ResponseBody
	public JSONObject deleteMeida(HttpServletRequest request,HttpServletResponse response,String id,String intoPieceId){
		JSONObject retJson = new JSONObject();
		if(StringUtils.isEmpty(id)){
			retJson.put("message", "请选择需要删除的图片！");
			response.setStatus(400);
			return retJson;
		}
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "未提供进件标识！");
			retJson.put("errno", 3001);
			response.setStatus(400);
			return retJson;
		}
		try {
			BusMedia selectByPrimaryKey = mediaService.selectByPrimaryKey(id);
			if(selectByPrimaryKey!=null){
				selectByPrimaryKey.setIsDeleted(Constants.COMMON_IS_DELETE);
				mediaService.updateByPrimaryKeySelective(selectByPrimaryKey);
			}
			retJson.put("message", "删除成功！");
			retJson.put("errno", 0);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retJson;
	}
	
	@RequestMapping("/wxsave")
	@ResponseBody
    public JSONObject wxFileSave(MultipartFile filePath,HttpServletRequest request,HttpServletResponse response){
    	JSONObject json = new JSONObject();
    	if(filePath == null){
    		response.setStatus(400);
			json.put("message", "请选择要上传的图片");
			return json;
    	}
		try {
			String key = QiNiuUtil.upLoadFile(filePath.getBytes());
			String url = QiNiuUtil.getUrl(key);
			json.put("url", url);
			json.put("key", key);
			json.put("message", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			json.put("message", "图片保存失败");
		}
		return json;
    }
	
	
	@RequestMapping("/localsave")
	@ResponseBody
	public JSONObject localSave(MultipartFile filePath,HttpServletResponse response){
		JSONObject json =  new JSONObject();
		if(filePath != null && filePath.getSize() >0){
			try {
				String targetPath = baseDir+"contract/";
				String dir = FileUtils.getTimeFilePath();
				String name = FileUtils.generateRandomFilename();
				String fileName = filePath.getOriginalFilename();
				if(fileName.lastIndexOf(".") != -1){
					name = name + fileName.substring(fileName.lastIndexOf("."));
				}
				targetPath = targetPath+dir;
				FileUtils.createDirectory(targetPath);
				File pathDest = new File(targetPath, name);
	            if (!pathDest.exists()) {
	            	pathDest.createNewFile();
	               }
	            FileOutputStream fos = new FileOutputStream(pathDest);
	            byte[] f = filePath.getBytes();
	            fos.write(f); 
	            fos.close();
				json.put("key", dir + name);
				json.put("url", weburl + "/nj/login/localimageshow?path=/contract" +dir + name);
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(400);
				json.put("message", "文件保存失败");
			}			
		}else{
			response.setStatus(400);
			json.put("message", "文件为空");
		}
		return json;
	}
	
	@RequestMapping("/localfileshow")
    public String localImageShow(String path, HttpServletResponse response){
    	response.setContentType("image/gif");
    	if(StrUtils.isEmpty(path))
    		return null;
    	FileInputStream fis = null;
    	try {
            OutputStream out = response.getOutputStream();
            File file = new File(baseDir + path);
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
