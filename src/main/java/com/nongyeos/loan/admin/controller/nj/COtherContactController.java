package com.nongyeos.loan.admin.controller.nj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

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
import com.nongyeos.loan.admin.entity.BusOtherContact;
import com.nongyeos.loan.admin.service.IOtherContactService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/nj/otherContact")
public class COtherContactController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(COtherContactController.class);
	
	@Autowired
	private IOtherContactService otherContactService;
	
	/**
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * 
	 * @Title: saveOtherContact 
	 * @Description:保存其他联系人 
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject saveOtherContact(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, IOException{
//		String param= null; 
		JSONObject retJson = new JSONObject();
		
		
//            BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));  
//            StringBuilder responseStrBuilder = new StringBuilder();  
//            String inputStr;  
//            while ((inputStr = streamReader.readLine()) != null)  
//                responseStrBuilder.append(inputStr);  
//              
//            JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());  
//            param= jsonObject.toJSONString();  
//            System.out.println("这是第一个方法获取的参数"+param);  
		
		
		
//		String intoPieceId = request.getParameter("intoPieceId");
		
		
		try {
			BufferedReader reader = request.getReader();
	    	String str,wholeStr ="";
	    	while((str=reader.readLine())!=null){
	    		wholeStr+=str;
	    	}
	    	JSONObject jsonpara = JSONObject.parseObject(wholeStr);
	    	String intoPieceId = jsonpara.getString("intoPieceId");
	    	if(StringUtils.isEmpty(intoPieceId)){
				retJson.put("message", "其他联系人进件标识为空！");
				retJson.put("errno", 3001);
				response.setStatus(400);
				return retJson;
			}
	    	String loginId = (String) request.getAttribute("loginId");
			String otherContacts = jsonpara.getString("otherContacts");
			if(otherContacts!=null){
				JSONArray otherContactsJson = JSONArray.parseArray(otherContacts);
				if(otherContactsJson!=null&&otherContactsJson.size()>0){
					for (int i = 0; i < otherContactsJson.size(); i++) {
						String otherContact =  otherContactsJson.get(i).toString();
						JSONObject otherContactJson = JSONObject.parseObject(otherContact);
						String id = otherContactJson.getString("id");
						String relation = otherContactJson.getString("relation");
						String phone = otherContactJson.getString("phone");
						String name = otherContactJson.getString("name");
						BusOtherContact oc =null;
						if(!StringUtils.isEmpty(id)){
							oc =otherContactService.selectByPrimaryKey(id);
						}
						if(oc!=null){
							oc.setRelation(relation);
							oc.setPhone(phone);
							oc.setName(name);
							oc.setUpdDate(DateUtils.getNowDate());
							oc.setUpdOperId(loginId);
							otherContactService.updateByPrimaryKey(oc);
						}else{
							oc=new BusOtherContact();
							oc.setIntoPieceId(intoPieceId);
							oc.setId(UUIDUtil.getUUID());
							oc.setRelation(relation);
							oc.setPhone(phone);
							oc.setName(name);
							oc.setUpdDate(DateUtils.getNowDate());
							oc.setUpdOperId(loginId);
							oc.setCreDate(DateUtils.getNowDate());
							oc.setCreOperId(loginId);
							oc.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
							otherContactService.insert(oc);
						}
					}
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
	 * @Title: otherContactInfo 
	 * @Description:其他联系人回显 
	 * @param @param request
	 * @param @return
	 * @param @throws Exception     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/otherContactInfo")
	@ResponseBody
	public JSONObject otherContactInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject retJson = new JSONObject();
		String intoPieceId = request.getParameter("intoPieceId");
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "其他联系人进件标识为空！");
			retJson.put("errno", 3001);
			response.setStatus(400);
			return retJson;
		}
		JSONArray arr = new JSONArray();
		try {
			BusOtherContact oc = new BusOtherContact();
			oc.setIntoPieceId(intoPieceId);
			oc.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			List<BusOtherContact> otherContactList = otherContactService.selectByIntpId(oc);
			if(otherContactList!=null&&otherContactList.size()>0){
				for (int i = 0; i < otherContactList.size(); i++) {
					JSONObject obj = new JSONObject();
					obj.put("id", otherContactList.get(i).getId());
					obj.put("relation", StringUtils.isEmpty(otherContactList.get(i).getRelation())?"":otherContactList.get(i).getRelation() );
					obj.put("phone", StringUtils.isEmpty(otherContactList.get(i).getPhone())?"":otherContactList.get(i).getPhone());
					obj.put("name", StringUtils.isEmpty(otherContactList.get(i).getName())?"":otherContactList.get(i).getName());
					arr.add(obj);
				}
			}
			retJson.put("otherContacts",arr);
			retJson.put("errno", 0);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return retJson;
	}
}
