package com.nongyeos.loan.admin.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusOtherContact;
import com.nongyeos.loan.admin.entity.BusTransferLand;
import com.nongyeos.loan.admin.service.ITransferLandService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/transferLand")
public class TransferLandController {
	private static final Logger logger = LogManager.getLogger(TransferLandController.class);
	
	@Autowired
	private ITransferLandService transferLandService;
	
	@RequestMapping("/list")
	@ResponseBody
	public PageBeanUtil<BusTransferLand> list(int currentPage,int pageSize,String intoPieceId) throws Exception{
		if(StrUtils.isEmpty(intoPieceId))
			return null;
		BusTransferLand transferLand = new BusTransferLand();
		transferLand.setIntoPieceId(intoPieceId);
		try {
			return transferLandService.selectPageByIpId(currentPage, pageSize,intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
		
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject save(HttpServletRequest request,BusTransferLand transferLand){
		JSONObject json = new JSONObject();
		if(transferLand == null){
			json.put("msg", "联系人模板为空");
			json.put("code", 400);
			return json;
		}
		try {
			if(StringUtils.isEmpty(transferLand.getId())){
				transferLand.setId(UUIDUtil.getUUID());
				Integer sort=  transferLandService.selectMaxSortByIpId(transferLand.getIntoPieceId());
				Integer sort1 =1;
				if(sort!=null){
					sort1=sort+1;
				}
				transferLand.setSort(sort1);
				transferLandService.insert(transferLand);
			}else{
				transferLandService.updateByPrimaryKeySelective(transferLand);
			}
			json.put("msg", "保存成功");
			json.put("code", 200);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", "保存失败");
			json.put("code", 400);
			return json;
		}
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public JSONObject delete(HttpServletRequest request,String id){
		JSONObject retJson = new JSONObject();
		if(StringUtils.isEmpty(id)){
			retJson.put("msg", "请选择需要删除的记录");
			retJson.put("code", 400);
			return retJson;
		}
		try {
			transferLandService.deleteByPrimaryKey(id);
			retJson.put("msg", "保存成功");
			retJson.put("code", 200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("msg", "保存失败");
			retJson.put("code", 400);
			return retJson;
		}
	}
}
