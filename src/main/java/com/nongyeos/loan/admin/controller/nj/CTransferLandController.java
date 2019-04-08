package com.nongyeos.loan.admin.controller.nj;

import java.io.BufferedReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.docx4j.wml.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusTransferLand;
import com.nongyeos.loan.admin.service.ITransferLandService;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/nj/transferLand")
public class CTransferLandController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CTransferLandController.class);
	
	@Autowired
	private ITransferLandService transferLandService;
	
	@RequestMapping("/transferLandSave")
	@ResponseBody
	public JSONObject transferLandSave(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			BufferedReader reader = request.getReader();
	    	String str,wholeStr ="";
	    	while((str=reader.readLine())!=null){
	    		wholeStr+=str;
	    	}
	    	JSONObject jsonpara = JSONObject.parseObject(wholeStr);
	    	String intoPieceId = jsonpara.getString("intoPieceId");
	    	if(StringUtils.isEmpty(intoPieceId)){
				retJson.put("message", "土地信息进件标识为空！");
				retJson.put("errno", 3001);
				response.setStatus(400);
				return retJson;
			}
	    	String transferLands = jsonpara.getString("transferLands");
	    	Integer sortMax = null;
	    	Integer sortmax = transferLandService.selectMaxSortByIpId(intoPieceId);
	    	if(sortmax==null){
	    		sortMax=0;
	    	}else{
	    		sortMax=sortmax;
	    	}
	    	if(transferLands!=null){
	    		JSONArray transferLandsJson = JSONArray.parseArray(transferLands);
	    		if(transferLandsJson!=null&&transferLandsJson.size()>0){
	    			for (int i = 0; i < transferLandsJson.size(); i++) {
	    				String transferLand = transferLandsJson.get(i).toString();
	    				JSONObject transferLandJson = JSONObject.parseObject(transferLand);
	    				String id = transferLandJson.getString("id");
	    				String landName = transferLandJson.getString("landName");
	    				String landLevel = transferLandJson.getString("landLevel");
	    				String landArea = transferLandJson.getString("landArea");
	    				String easternBorder = transferLandJson.getString("easternBorder");
	    				String westernBorder = transferLandJson.getString("westernBorder");
	    				String northernBorder = transferLandJson.getString("northernBorder");
	    				String southernBorder = transferLandJson.getString("southernBorder");
	    				String outsource = transferLandJson.getString("outsource");
	    				String outsourcingTerm = transferLandJson.getString("outsourcingTerm");
	    				BusTransferLand tf = null;
	    				if(!StringUtils.isEmpty(id)){
	    					tf=transferLandService.selectByPrimaryKey(id);
	    				}
	    				if(tf==null){
	    					sortMax+=1;
	    					tf=new BusTransferLand();
	    					tf.setId(UUIDUtil.getUUID());
	    					tf.setIntoPieceId(intoPieceId);
	    					tf.setLandName(landName);
	    					tf.setLandLevel(landLevel);
	    					tf.setLandArea(landArea);
	    					tf.setSort(sortMax);
	    					tf.setWesternBorder(westernBorder);
	    					tf.setEasternBorder(easternBorder);
	    					tf.setNorthernBorder(northernBorder);
	    					tf.setSouthernBorder(southernBorder);
	    					if(StringUtils.isNoneEmpty(outsource)){
	    						tf.setOutsource(new Integer(outsource));
	    					}
	    					if(StringUtils.isNoneEmpty(outsourcingTerm)){
	    						tf.setOutsourcingTerm(outsourcingTerm);
	    					}
	    					transferLandService.insert(tf);
	    				}else{
	    					tf.setLandName(landName);
	    					tf.setLandLevel(landLevel);
	    					tf.setLandArea(landArea);
	    					tf.setWesternBorder(westernBorder);
	    					tf.setEasternBorder(easternBorder);
	    					tf.setNorthernBorder(northernBorder);
	    					tf.setSouthernBorder(southernBorder);
	    					if(StringUtils.isNoneEmpty(outsource)){
	    						tf.setOutsource(new Integer(outsource));
	    					}
	    					if(StringUtils.isNoneEmpty(outsourcingTerm)){
	    						tf.setOutsourcingTerm(outsourcingTerm);
	    					}
	    					transferLandService.updateByPrimaryKey(tf);
	    				}
					}
	    		}else{
					retJson.put("message", "保存失败！");
					response.setStatus(400);
					return retJson;
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
	
	@RequestMapping("/transferLandInfo")
	@ResponseBody
	public JSONObject transferLandInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject retJson = new JSONObject();
		String intoPieceId = request.getParameter("intoPieceId");
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "进件标识为空");
			response.setStatus(400);
			return retJson;
		}
		JSONArray arr = new JSONArray();
		try {
			List<BusTransferLand> list = transferLandService.selectByIpId(intoPieceId);
			for (int i = 0; i < list.size(); i++) {
				JSONObject obj = new JSONObject();
				obj.put("id", list.get(i).getId());
				obj.put("landName", list.get(i).getLandName());
				obj.put("landLevel", list.get(i).getLandLevel());
				obj.put("landArea", list.get(i).getLandArea());
				obj.put("easternBorder", list.get(i).getEasternBorder()==null?"":list.get(i).getEasternBorder());
				obj.put("westernBorder", list.get(i).getWesternBorder()==null?"":list.get(i).getWesternBorder());
				obj.put("northernBorder", list.get(i).getNorthernBorder()==null?"":list.get(i).getNorthernBorder());
				obj.put("southernBorder", list.get(i).getSouthernBorder()==null?"":list.get(i).getSouthernBorder());
				obj.put("outsource", list.get(i).getOutsource()==null?"":list.get(i).getOutsource());
				obj.put("outsourcingTerm", list.get(i).getOutsourcingTerm()==null?"":list.get(i).getOutsourcingTerm());
				arr.add(obj);
			}
			retJson.put("transferLands",arr);
			retJson.put("errno", 0);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			response.setStatus(400);
			return retJson;
		}
		return retJson;
	}
}
