package com.nongyeos.loan.admin.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusApplySignFile;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusMedia;
import com.nongyeos.loan.admin.entity.SysParaGroup;
import com.nongyeos.loan.admin.service.IApplySignFileService;
import com.nongyeos.loan.admin.service.ILoanService;
import com.nongyeos.loan.admin.service.IMediaService;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.admin.service.IParaService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/media")
public class MediaController {
	
	@Autowired  
    private IParaService paraService; 
	
	@Autowired  
    private IParaGroupService paraGroupService;
	
	@Autowired
	private IMediaService mediaService;
	
	@Value("${fileupload.baseDir}")
	private String baseDir;
	
	@Autowired
	private ILoanService loanService;
	
	@Autowired
	private IApplySignFileService applySignFileService;
	
	
	@RequestMapping("/imagepara")
	@ResponseBody
	public JSONObject imagePara(String paraGroupName){
		JSONObject json = new JSONObject();
		String paraStr = "";
		try {
			SysParaGroup pGroup = paraGroupService.selectByName(paraGroupName);
			if(pGroup != null)
			{
				paraStr = paraService.selectByPGroupId(pGroup.getPgroupId());
			}
			String token = QiNiuUtil.upToken();
			json.put("para", paraStr);
			json.put("token", token);
			json.put("domain", QiNiuUtil.domainUrl());
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject save(String hashKey,Integer fileType,String intoPieceId,HttpServletRequest request){
		JSONObject json = new JSONObject();
		if(fileType == null){
			fileType = Constants.OTHER;//类型：其他
		}
		if(StrUtils.isEmpty(intoPieceId)){
			json.put("code", 400);
			json.put("msg", "进件标识为空");
			return json;
		}
		if(StrUtils.isEmpty(hashKey)){
			json.put("code", 400);
			json.put("msg", "图片标识为空");
			return json;
		}
		try {
			String key[] = hashKey.split(",");
			for (int i = 0; i < key.length; i++) {
				if(StrUtils.isNotEmpty(key[i])){
					BusMedia m = new BusMedia();
					m.setIntoPieceId(intoPieceId);
					m.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
					m.setQiniuKey(key[i]);
					int check = mediaService.existenceByKey(m);
					//如果存在，不再保存
					if(check>0){
						continue;
					}
					m.setId(UUIDUtil.getUUID());
					m.setFileType(fileType);
					m.setType(Constants.MEDIATYPE_IMAGE);
					m.setCreOperId( request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
					m.setCreOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
					m.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
					m.setCreDate(new Date());
					m.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
					m.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
					m.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
					m.setUpdDate(new Date());
					mediaService.insert(m);
				}
			}			
			json.put("code", 200);
			json.put("msg", "保存成功");
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", "保存失败");
			return json;
		}
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public JSONArray list(String intoPieceId,String type,Integer fileType){
		try {
			if(StrUtils.isEmpty(intoPieceId))
				return null;
			BusMedia m = new BusMedia();
			m.setIntoPieceId(intoPieceId);
			m.setType(type);
			m.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			if(fileType != null)
				m.setFileType(fileType);
			List<BusMedia> list = mediaService.selectByIpId(m);
			return QiNiuUtil.getUrlEndPNG(list);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@RequestMapping("/filesave")
	@ResponseBody
	public JSONObject fileSave(MultipartFile file,String intoPieceId,HttpServletRequest request){
		JSONObject json =  new JSONObject();
		if(file != null && file.getSize() >0){
			try {
				BusMedia m = new BusMedia();
				String targetPath = baseDir+"contract/";
				String dir = FileUtils.getTimeFilePath();
				String name = FileUtils.generateRandomFilename();
				String fileName = file.getOriginalFilename();
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
	            byte[] f = file.getBytes();
	            fos.write(f); 
	            fos.close();
	            
				m.setId(UUIDUtil.getUUID());
				m.setIntoPieceId(intoPieceId);
				m.setIsDeleted(Constants.COMMON_ISNOT_DELETE);	
				m.setType(Constants.MEDIATYPE_FILE);
				m.setPath(dir+name);
				m.setName(fileName);
				m.setCreOperId( request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				m.setCreOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				m.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				m.setCreDate(new Date());
				m.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				m.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				m.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				m.setUpdDate(new Date());	
				mediaService.insert(m);
				json.put("code", 200);
				json.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("code", 400);
				json.put("msg", e.getMessage());
			}			
		}else{
			json.put("code", 400);
			json.put("msg", "文件为空");
		}
		return json;
	}
	
	@RequestMapping("/filelist")
	@ResponseBody
	public JSONObject fileList(String intoPieceId,String type){
		JSONObject json = new JSONObject();
		try {
			if(StrUtils.isEmpty(intoPieceId))
				return null;
			BusLoan loan = loanService.selectByIpId(intoPieceId);
			JSONArray contractArray = new JSONArray();
			if(loan != null){
				List<BusApplySignFile>  fileList = applySignFileService.finishSign(loan.getId());
				for (int i = 0; i < fileList.size(); i++) {
					JSONObject file = new JSONObject();
					file.put("id", fileList.get(i).getId());
					file.put("name", fileList.get(i).getRemark());
					file.put("time", fileList.get(i).getUpdDate());
					contractArray.add(file);
				}
			}
			json.put("fileList", contractArray);
			BusMedia m = new BusMedia();
			m.setIntoPieceId(intoPieceId);
			m.setType(type);
			m.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			List<BusMedia> mediaList = mediaService.selectByIpId(m);
			JSONArray mediaArray = new JSONArray();
			for (int i = 0; i < mediaList.size(); i++) {
				JSONObject file = new JSONObject();
				file.put("id", mediaList.get(i).getId());
				file.put("name", mediaList.get(i).getName());
				file.put("time", mediaList.get(i).getCreDate());
				mediaArray.add(file);
			}
			json.put("mediaList", mediaArray);
			json.put("code", 200);
			return  json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			return json;
		}
		
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public JSONObject delete(String id){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "图片标识为空");
			return json;
		}
		try {
			BusMedia m = new BusMedia();
			m.setId(id);
			m.setIsDeleted(Constants.COMMON_IS_DELETE);
			mediaService.updateByPrimaryKeySelective(m);
			json.put("code", 200);
			json.put("msg", "图片删除成功");
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", "图片删除失败");
			return json;
		}
		
	}
	
	@RequestMapping("/load")
	public String load(String id,HttpServletRequest request, HttpServletResponse response){
		if(id == null)
			return null;
		try {
			BusMedia m = mediaService.selectByPrimaryKey(id);
			String filePath = baseDir+"contract/"+m.getPath();
			File file = new File(filePath);
			if(file.exists()){
				response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(m.getName(), "UTF-8"));// 设置文件名
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
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
		
	}
	/**
     * 字节转换KB、MB
     * @param size 文件大小（字节）
     * @return 大于1MB，则转换为MB，否则转换为KB
     */
    private String convertSize(long size) {
        if (size > 0) {
            if (size / 1024 > 1024) {
                return (long)((double)size / 1024 / 1024 + 0.5) + " MB";
            } else {
                return (long)((double)size / 1024 + 0.5) + " KB";
            }
        } else {
            return "0 KB";
        }
    }
    
    @RequestMapping("/imagesavelocal")
	@ResponseBody
	public JSONObject imageSaveLocal(MultipartFile file){
		JSONObject json =  new JSONObject();
		if(file != null && file.getSize() >0){
			try {
				String targetPath = baseDir+"image/";
				String dir = FileUtils.getTimeFilePath();
				String name = FileUtils.generateRandomFilename();
				String fileName = file.getOriginalFilename();
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
	            byte[] f = file.getBytes();
	            fos.write(f); 
	            fos.close();
	            
				json.put("code", 200);
				json.put("path", "/image" + dir + name);
			} catch (Exception e) {
				e.printStackTrace();
				json.put("code", 400);
				json.put("msg", e.getMessage());
			}			
		}else{
			json.put("code", 400);
			json.put("msg", "文件为空");
		}
		return json;
	}
    
    @RequestMapping("/imagesaveqiniu")
	@ResponseBody
	public JSONObject imageSaveQiniu(MultipartFile file){
		JSONObject json =  new JSONObject();
		if(file != null && file.getSize() >0){
			try {
				String key = QiNiuUtil.upLoadFile(file.getBytes());
				String url = QiNiuUtil.getJpgUrl(key);	            
				json.put("code", 200);
				json.put("url", url);
				json.put("key", key);
			} catch (Exception e) {
				e.printStackTrace();
				json.put("code", 400);
				json.put("msg", e.getMessage());
			}			
		}else{
			json.put("code", 400);
			json.put("msg", "文件为空");
		}
		return json;
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

