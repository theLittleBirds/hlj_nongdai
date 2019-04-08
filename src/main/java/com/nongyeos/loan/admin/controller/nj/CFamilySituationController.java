package com.nongyeos.loan.admin.controller.nj;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.nongyeos.loan.admin.entity.BusFamilySituation;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.service.IFamilySituationService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IFlowEntranceService;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.IdCheck;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/nj/familySituation")
public class CFamilySituationController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CFamilySituationController.class);
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IFamilySituationService familySituationService;

	@Autowired
	private IFlowEntranceService flowEntranceService;
	@Autowired
	private IFlowNodeService fleNodeService;
	@Autowired
	private IAppEntryService appEntryService;//业务_状态记录
	@Value("${web.url}")
	private String weburl;
	@Value("${fileupload.baseDir}")
	private String baseDir;
	
	/**
	 * 
	 * @Title: saveFamily 
	 * @Description: 补全家庭成员信息
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject saveFamily(HttpServletRequest request,HttpServletResponse response,MultipartFile idCardP,MultipartFile idCardN){
		JSONObject retJson = new JSONObject();
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			String type1 =  request.getParameter("type");
			if(StringUtils.isEmpty(intoPieceId)){
				retJson.put("message", "未提供进件标识！");
				retJson.put("errno", 3001);
				response.setStatus(400);
				return retJson;
			}
			if(StringUtils.isEmpty(type1)){
				retJson.put("message", "未提供家庭成员类型！");
				retJson.put("errno", 3008);
				response.setStatus(400);
				return retJson;
			}
			Integer type = new Integer(type1);
			BusIntoPiece intopiece = intoPieceService.selectByPrimaryKey(intoPieceId);
			if(intopiece == null){
				retJson.put("message", "未找到贷款信息！");
				retJson.put("errno", 3009);
				response.setStatus(400);
				return retJson;
			}
			BusFamilySituation familySituation = new BusFamilySituation(); 
			familySituation.setIntoPieceId(intoPieceId);
			familySituation.setType(type);
			BusFamilySituation selectedRecord =familySituationService.selectByIntopIdAndType(familySituation);
			if(selectedRecord==null){
				selectedRecord = new BusFamilySituation();
				selectedRecord.setIntoPieceId(intoPieceId);
				selectedRecord.setMemberId(intopiece.getMemberId());
				selectedRecord.setType(type);
				selectedRecord.setCreDate(DateUtils.getNowDate());
				selectedRecord.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			}
			selectedRecord.setUpdDate(DateUtils.getNowDate());
			String teleReg = "^1\\d{10}$";
			//手机号
			String phone = request.getParameter("phone");
			//身份证号
			String idCard = request.getParameter("idCard");
			if(!StringUtils.isBlank(phone)){
				phone = phone.trim().replaceAll("\\s+","");
				if(!phone.matches(teleReg)){
					retJson.put("message", "手机号输入有误，请重新输入！");
					retJson.put("errno", 3003);
					response.setStatus(400);
					return retJson;
				}
			}
			//年龄
			Integer age =null;
			//性别
			Integer sex =null;
			if(idCard!=null&&IdCheck.isValidatedAllIdcard(idCard)){
				if(idCard.length()==15){
					idCard = IdCheck.convertIdcarBy15bit(idCard);
				}
				age = IdCheck.getAgeByIdCard(idCard);
				String sex1 = IdCheck.getGenderByIdCard(idCard);
				sex = new Integer(sex1);
				}
			if(!StringUtils.isBlank(idCard)){
				idCard = idCard.trim().replaceAll("\\s+","");
				if(!IdCheck.isValidatedAllIdcard(idCard)){
					retJson.put("message", "身份证号输入有误，请重新输入！");
					retJson.put("errno", 3003);
					response.setStatus(400);
					return retJson;
				}
			}
			//成员姓名
			String name = StringUtils.isEmpty(request.getParameter("name"))?null:request.getParameter("name");
			//居住情况
			Integer status = StringUtils.isEmpty(request.getParameter("status"))?null:new Integer(request.getParameter("status"))  ;
			//不同住时的居住地址
			String liveAddress =StringUtils.isEmpty(request.getParameter("liveAddress"))?null:request.getParameter("liveAddress");
			//非农职业单位名称
			String nonfarmComName = StringUtils.isEmpty(request.getParameter("nonfarmComName"))?null:request.getParameter("nonfarmComName");
			//非农职业单位地址
			String nonfarmComAddress =StringUtils.isEmpty(request.getParameter("nonfarmComAddress"))?null: request.getParameter("nonfarmComAddress");
			//非农职业单位电话
			String nonfarmComPhone = StringUtils.isEmpty(request.getParameter("nonfarmComPhone"))?null:request.getParameter("nonfarmComPhone");
			//能否作为共同借款人（1：能  0：否）
			Integer coBorrower = StringUtils.isEmpty(request.getParameter("coBorrower"))?null:new Integer(request.getParameter("coBorrower"));
			//健康状况(1很好 2一般 3有疾病,有疾病时候描述)
			Integer healthStatus = StringUtils.isEmpty(request.getParameter("healthStatus"))?null:new Integer(request.getParameter("healthStatus"));
			//子女婚姻状况（1：已婚  2：未婚）
			Integer maritalStatus = StringUtils.isEmpty(request.getParameter("maritalStatus"))?null:new Integer(request.getParameter("maritalStatus"));
			//疾病描述
			String diseaseRemark = StringUtils.isEmpty(request.getParameter("diseaseRemark"))?null:request.getParameter("diseaseRemark");
			//学习情况描述
			String school = StringUtils.isEmpty(request.getParameter("school"))?null:request.getParameter("school");
			//文化程度
			Integer educationLevel = StringUtils.isEmpty(request.getParameter("educationLevel"))?null:new Integer(request.getParameter("educationLevel"));
			//是否去世（1，去世 2，在世）
			Integer isDead = StringUtils.isEmpty(request.getParameter("isDead"))?null:new Integer(request.getParameter("isDead"));
			//是否为土地共有人（1 是 2否）
			Integer coLand = StringUtils.isEmpty(request.getParameter("coLand"))?null:new Integer(request.getParameter("coLand"));
			//从事职业
			String duty = request.getParameter("duty");
			selectedRecord.setName(StringUtils.isEmpty(name)?null:name);
			selectedRecord.setAge(age);
			selectedRecord.setSex(sex);
			selectedRecord.setPhone(StringUtils.isEmpty(phone)?null:phone);
			selectedRecord.setHealthStatus(healthStatus);
			selectedRecord.setDiseaseRemark(diseaseRemark);
			selectedRecord.setIdCard(StringUtils.isEmpty(idCard)?null:idCard);
			selectedRecord.setStatus(status);
			selectedRecord.setLiveAddress(liveAddress);
			selectedRecord.setNonfarmComName(nonfarmComName);
			selectedRecord.setNonfarmComAddress(nonfarmComAddress);
			selectedRecord.setNonfarmComPhone(nonfarmComPhone);
			selectedRecord.setCoLand(coLand);
			Boolean CO = false;
			switch (type) {
			case 1:
				selectedRecord.setCoBorrower(coBorrower);
				selectedRecord.setIsDead(isDead);
				if(age!=null){
					if(age<18||age>65){
						selectedRecord.setCoBorrower(0);
						CO=true;
					}
				}
				
				selectedRecord.setDuty(duty);
				break;
			case 2:
				if(age!=null){
					if(age<18||age>65){
						selectedRecord.setCoBorrower(0);
						CO=true;
					}
				}
				selectedRecord.setCoBorrower(coBorrower);
				selectedRecord.setIsDead(isDead);
				selectedRecord.setDuty(duty);
				break;
			case 3:
				selectedRecord.setCoBorrower(coBorrower);
				selectedRecord.setEducationLevel(educationLevel);
//				selectedRecord.setDuty(duty);
				break;
			case 4:
				selectedRecord.setSchool(school);
				selectedRecord.setEducationLevel(educationLevel);
				selectedRecord.setCoBorrower(coBorrower);
				selectedRecord.setMaritalStatus(maritalStatus);
				break;
			case 7:
				selectedRecord.setCoBorrower(coBorrower);
				break;
			default:
				selectedRecord.setSchool(school);
				selectedRecord.setEducationLevel(educationLevel);
				selectedRecord.setCoBorrower(coBorrower);
				selectedRecord.setMaritalStatus(maritalStatus);
				break;
			}
			if(type==4||type==5){
				Integer types = type+1;
				familySituation.setType(types);
				BusFamilySituation haveOtherChildren = familySituationService.selectByIntopIdAndType(familySituation);
				if(haveOtherChildren!=null){
					retJson.put("anotherChild", "yes");
				}else{
					retJson.put("anotherChild", "no");
				}
			}
			Map<String, Object> result = null;
			try {
				result=familySituationService.saveOrUpdate(selectedRecord);
			} catch (Exception e) {
				retJson.put("message", e.getMessage());
				response.setStatus(400);
				return retJson;
			}
				if((Boolean)result.get("reject")){
					//自动拒件	 自动拒件让流程节点直接到已拒件
					BusIntoPiece primaryKey = intoPieceService.selectByPrimaryKey(selectedRecord.getIntoPieceId());
					if(primaryKey == null){
						throw new Exception("没有找到相应进件");
					}
					//查询流程节点
					FlowNode flowNode = new FlowNode();
					flowNode.setAppId(primaryKey.getModelId());
					flowNode.setEname(Constants.FLOW_REFUSE);
					FlowNode fleNodeId = fleNodeService.queryByEnameAndModel(flowNode);
					//获的相关进件业务数据
					AppEntry appEntry = appEntryService.queryByAppModeId(primaryKey.getId());
					appEntry.setNodeId(fleNodeId.getNodeId());//流程节点id 主键
					appEntry.setNodeName(fleNodeId.getEname());//流程节点状态码
					//更改拒件流程节点
					appEntryService.updateByAppEntrySelective(appEntry);
					
					retJson.put("message", "该客户不符合进件申请条件，请在列表查看并核实原因，30天后重新发起申请！");
	        		retJson.put("errno", 3032);
	        		response.setStatus(200);
				}else{
					retJson.put("message", "保存成功！");
					if(CO){
						retJson.put("message", "年龄超限,无法作为共同借款人！");
						retJson.put("errno", 3038);
					}
					response.setStatus(200);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
		}
		return retJson;
	}
	
	/**
	 * 
	 * @Title: familyInfo 
	 * @Description:家庭情况回显 
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/familyInfo")
	@ResponseBody
	public JSONObject familyInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			String type1 =  request.getParameter("type");
			if(StringUtils.isEmpty(intoPieceId)){
				retJson.put("message", "未提供进件标识！");
				retJson.put("errno", 3001);
				response.setStatus(400);
				return retJson;
			}
			if(StringUtils.isEmpty(type1)){
				retJson.put("message", "未提供家庭成员类型！");
				retJson.put("errno", 3008);
				response.setStatus(400);
				return retJson;
			}
			Integer type = new Integer(type1);
			BusIntoPiece intopiece = intoPieceService.selectByPrimaryKey(intoPieceId);
			if(intopiece == null){
				retJson.put("message", "未找到贷款信息！");
				retJson.put("errno", 3009);
				response.setStatus(400);
				return retJson;
			}
			BusFamilySituation familySituation = new BusFamilySituation(); 
			familySituation.setIntoPieceId(intoPieceId);
			familySituation.setType(type);
			BusFamilySituation selectedRecord =familySituationService.selectByIntopIdAndType(familySituation);
			if(selectedRecord==null){
				selectedRecord= new BusFamilySituation();
			}
			JSONObject obj = new JSONObject();
			obj.put("name", selectedRecord.getName()==null?"":selectedRecord.getName());
			obj.put("phone", selectedRecord.getPhone()==null?"":selectedRecord.getPhone());
			obj.put("healthStatus", selectedRecord.getHealthStatus()==null?"":selectedRecord.getHealthStatus().toString());
			obj.put("diseaseRemark", selectedRecord.getDiseaseRemark()==null?"":selectedRecord.getDiseaseRemark());
			obj.put("idCard", selectedRecord.getIdCard()==null?"":selectedRecord.getIdCard());
			obj.put("status", selectedRecord.getStatus()==null?"":selectedRecord.getStatus().toString());
			obj.put("liveAddress", selectedRecord.getLiveAddress()==null?"":selectedRecord.getLiveAddress());
			obj.put("coLand", selectedRecord.getCoLand()==null?"":selectedRecord.getCoLand().toString());
			obj.put("nonfarmComName", selectedRecord.getNonfarmComName()==null?"":selectedRecord.getNonfarmComName());
			obj.put("nonfarmComAddress", selectedRecord.getNonfarmComAddress()==null?"":selectedRecord.getNonfarmComAddress());
			obj.put("nonfarmComPhone", selectedRecord.getNonfarmComPhone()==null?"":selectedRecord.getNonfarmComPhone());
			switch (type) {
			case 1:
				obj.put("coBorrower", selectedRecord.getCoBorrower()==null?"":selectedRecord.getCoBorrower().toString());
				obj.put("isDead", selectedRecord.getIsDead()==null?"":selectedRecord.getIsDead().toString());
				obj.put("duty", selectedRecord.getDuty()==null?"":selectedRecord.getDuty().toString());
				break;
			case 2:
				obj.put("coBorrower", selectedRecord.getCoBorrower()==null?"":selectedRecord.getCoBorrower().toString());
				obj.put("isDead", selectedRecord.getIsDead()==null?"":selectedRecord.getIsDead().toString());
				obj.put("duty", selectedRecord.getDuty()==null?"":selectedRecord.getDuty().toString());
				break;
			case 3:
				obj.put("educationLevel", selectedRecord.getEducationLevel()==null?"":selectedRecord.getEducationLevel().toString());
				obj.put("duty", selectedRecord.getDuty()==null?"":selectedRecord.getDuty().toString());
				break;
			case 7:
				obj.put("coBorrower", selectedRecord.getCoBorrower()==null?"":selectedRecord.getCoBorrower().toString());
				break;

			default:
				obj.put("educationLevel", selectedRecord.getEducationLevel()==null?"":selectedRecord.getEducationLevel().toString());
				obj.put("coBorrower", selectedRecord.getCoBorrower()==null?"":selectedRecord.getCoBorrower().toString());
				obj.put("school", selectedRecord.getSchool()==null?"":selectedRecord.getSchool());
				obj.put("maritalStatus", selectedRecord.getMaritalStatus()==null?"":selectedRecord.getMaritalStatus().toString());
				break;
			}
			retJson.put("data", obj);
			retJson.put("errno", 0);
			response.setStatus(200);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;			
	}
	
	@RequestMapping("/coland")
	@ResponseBody
	public JSONObject coLand(String intoPieceId,HttpServletResponse response){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(intoPieceId)){
			json.put("errno", 3001);
			json.put("message", "进件标识为空！");
			response.setStatus(400);
			return json;
		}
		try {
			JSONArray arr = new JSONArray();
			List<BusFamilySituation> list = familySituationService.thirdpartycredit(intoPieceId);
			for (int i = 0; i < list.size(); i++) {
				JSONObject obj = new JSONObject();
				obj.put("id", list.get(i).getId());
				obj.put("name", list.get(i).getName());
				arr.add(obj);
			}
			json.put("coLand",arr);
			json.put("errno", 0);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("errno", 3008);
			json.put("message", "土地共有人查询失败！");
			response.setStatus(400);
		}
		return json;
	}
	
	@RequestMapping("/wxsave")
	@ResponseBody
	public JSONObject wxSave(HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		String intoPieceId = request.getParameter("intoPieceId");
		if(StringUtils.isEmpty(intoPieceId)){
			json.put("message", "未提供进件标识！");
			json.put("errno", 3001);
			response.setStatus(400);
			return json;
		}		
		String seqno = request.getParameter("seqno");
		if(StringUtils.isEmpty(seqno)){
			json.put("message", "未提供家庭成员类型！");
			json.put("errno", 3008);
			response.setStatus(400);
			return json;
		}
		try {
			BusFamilySituation familySituation = new BusFamilySituation(); 
			familySituation.setIntoPieceId(intoPieceId);
			familySituation.setSeqno(Integer.parseInt(seqno));
			BusFamilySituation selectedRecord =familySituationService.selectByIntopIdAndSeqno(familySituation);
			String type =  request.getParameter("type");
			String name =  request.getParameter("name");
			String idCard =  request.getParameter("idCard");
			String phone =  request.getParameter("phone");
			String liveAddress =  request.getParameter("liveAddress");
			String loginId = request.getParameter("loginId");
			String idCardP = request.getParameter("idCardP");
			String idCardN = request.getParameter("idCardN");
			if(selectedRecord == null){
				familySituation.setType(StringUtils.isEmpty(type)?null:Integer.parseInt(type));
				familySituation.setName(name);
				familySituation.setIdCard(idCard);
				familySituation.setPhone(phone);
				familySituation.setLiveAddress(liveAddress);
				familySituation.setCreDate(new Date());
				familySituation.setCreOperId(loginId);
				familySituation.setUpdDate(new Date());
				familySituation.setUpdOperId(loginId);
				familySituation.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				familySituation.setIdCardP(idCardP);
				familySituation.setIdCardN(idCardN);
				familySituation.setId(UUIDUtil.getUUID());
				familySituationService.insert(familySituation);
			}else{
				selectedRecord.setType(StringUtils.isEmpty(type)?null:Integer.parseInt(type));
				selectedRecord.setName(name);
				selectedRecord.setIdCard(idCard);
				selectedRecord.setPhone(phone);
				selectedRecord.setLiveAddress(liveAddress);
				selectedRecord.setUpdDate(new Date());
				selectedRecord.setUpdOperId(loginId);
				if(idCardP!=null){
					selectedRecord.setIdCardP(idCardP);	
				}
				if(idCardN!=null){
					selectedRecord.setIdCardN(idCardN);	
				}
				familySituationService.updateByPrimaryKey(selectedRecord);
			}	
			json.put("message", "保存成功！");
			json.put("errno", 0);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("message", "保存失败！");
			json.put("errno", 3008);
			response.setStatus(400);
			return json;			
		}
	}
}
