package com.nongyeos.loan.admin.controller.nj;

import java.io.BufferedReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusMemberOperateMedia;
import com.nongyeos.loan.admin.service.IMemberOperateMediaService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/nj/picture")
public class UPictureController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CApplicationController.class);
	
	
	@Autowired
	private IMemberOperateMediaService pictureService;
	
	/**
	 * 档案资料图片保存
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pictureSave")
	@ResponseBody
	public JSONObject pictureSave(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String userId = (String) request.getAttribute("loginId");
		try {
			//档案Id
			BufferedReader reader = request.getReader();
	    	String str,wholeStr ="";
	    	while((str=reader.readLine())!=null){
	    		wholeStr+=str;
	    	}
	    	JSONObject obj = JSONObject.parseObject(wholeStr);
	    	String id = obj.getString("id");
	    	String qiniuKeys = obj.getString("qiniuKey");
			if(StringUtils.isEmpty(id)){
				retJson.put("message", "未提供档案标识！");
				retJson.put("errno", 3040);
				response.setStatus(400);
				return retJson;
			}
			JSONArray ARR = JSONObject.parseArray(qiniuKeys);
	    	if(ARR!=null&&ARR.size()>0){
	    		for (int i = 0; i < ARR.size(); i++) {
					JSONObject object =  JSONObject.parseObject(ARR.get(i).toString());
					BusMemberOperateMedia pictureforsearch = new BusMemberOperateMedia();
					pictureforsearch.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
					pictureforsearch.setMemberOperateId(id);
					pictureforsearch.setQiniuKey(object.getString("qiniuKey"));
					int check = pictureService.existenceByKey(pictureforsearch);
					if(check>0){
						continue;
					}
					pictureforsearch.setId(UUIDUtil.getUUID());
					pictureforsearch.setExtName("jpg");
					pictureforsearch.setType(Constants.MEDIATYPE_IMAGE);
					pictureforsearch.setCreDate(DateUtils.getNowDate());
					pictureforsearch.setCreOperId(userId);
					pictureforsearch.setUpdDate(DateUtils.getNowDate());
					pictureforsearch.setUpdOperId(userId);
					pictureService.insert(pictureforsearch);
				}
	    	}else{
	    		retJson.put("message", "请上传图片！");
				retJson.put("errno", 3002);
				response.setStatus(400);
				return retJson;
	    	}
			
//			while (true) {
				
//				String qiniuKey = request.getParameter("qiniuKey["+i+"]");
//				i++;
//				if(StringUtils.isNotEmpty(qiniuKey)){
//					BusMemberOperateMedia pictureforsearch = new BusMemberOperateMedia();
//					pictureforsearch.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
//					pictureforsearch.setMemberOperateId(id);
//					pictureforsearch.setQiniuKey(qiniuKey);
//					int check = pictureService.existenceByKey(pictureforsearch);
//					if(check>0){
//						continue;
//					}
//					pictureforsearch.setId(UUIDUtil.getUUID());
//					pictureforsearch.setExtName("jpg");
//					pictureforsearch.setType(Constants.MEDIATYPE_IMAGE);
//					pictureforsearch.setCreDate(DateUtils.getNowDate());
//					pictureforsearch.setCreOperId(userId);
//					pictureforsearch.setUpdDate(DateUtils.getNowDate());
//					pictureforsearch.setUpdOperId(userId);
//					pictureService.insert(pictureforsearch);
//				}else{
//					if(i==1){
//						retJson.put("message", "请上传图片！");
//						retJson.put("errno", 3002);
//						response.setStatus(400);
//						return retJson;
//					}else{
//						break;
//					}
//				}
//			}
			retJson.put("message", "保存成功！");
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			retJson.put("errno", 3027);
			response.setStatus(400);
		}
		return retJson;
	}
	
	/**
	 * 图片信息回显
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pictureInfo")
	@ResponseBody
	public JSONObject pictureInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			String id = request.getParameter("id");
			if(StringUtils.isEmpty(id)){
				retJson.put("message", "未提供档案标识！");
				retJson.put("errno", 3040);
				response.setStatus(400);
				return retJson;
			}
			List<BusMemberOperateMedia> list = pictureService.selectByMOId(id);
			if(list==null||list.size()<1){
				response.setStatus(200);
				return retJson;
			}else{
				
				JSONArray arr = new JSONArray();
				for (int i = 0; i < list.size(); i++) {
					JSONObject obj = new JSONObject();
					String image_url = QiNiuUtil.getUrlLimit10k(list.get(i).getQiniuKey());
					String original_url = QiNiuUtil.getUrl(list.get(i).getQiniuKey());
					obj.put("image_url", image_url);
					obj.put("original_url", original_url);
					obj.put("pictureId", list.get(i).getId());
					arr.add(obj);
				}
				retJson.put("data", arr);
				retJson.put("errno", 0);
				response.setStatus(200);
				return retJson;
			}
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			retJson.put("errno", 3027);
			response.setStatus(400);
		}
		return retJson;
	}
}
