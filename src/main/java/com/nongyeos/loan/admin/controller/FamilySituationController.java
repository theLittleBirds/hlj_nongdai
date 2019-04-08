package com.nongyeos.loan.admin.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusFamilySituation;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusProduct;
import com.nongyeos.loan.admin.service.IFamilySituationService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IProductService;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.GetRepaymentUtils;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("family")
public class FamilySituationController {
	
	@Autowired
	private IFlowNodeService fleNodeService;
	@Autowired
	private IAppEntryService appEntryService;//业务_状态记录
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IFamilySituationService familySituationService;
	
	@Autowired
	private IProductService productService;
	
	@RequestMapping("getbytype")
	@ResponseBody
	public BusFamilySituation getByType(String intoPieceId,Integer type){
		if(StrUtils.isEmpty(intoPieceId))
			return null;
		if(type == null)
			return null;
		try {
			BusFamilySituation fs = new BusFamilySituation();
			fs.setIntoPieceId(intoPieceId);
			fs.setType(type);
			return familySituationService.selectByIntopIdAndType(fs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("getbyseqno")
	@ResponseBody
	public JSONObject getBySeqno(String intoPieceId,Integer seqno){
		if(StrUtils.isEmpty(intoPieceId))
			return null;
		if(seqno == null)
			return null;
		try {
			BusFamilySituation fs = new BusFamilySituation();
			fs.setIntoPieceId(intoPieceId);
			fs.setSeqno(seqno);
			BusFamilySituation family = familySituationService.selectByIntopIdAndSeqno(fs);
			JSONObject json = new JSONObject();
			if(family!=null){
				json.put("name", family.getName());
				json.put("type", family.getType());
				json.put("idCard", family.getIdCard());
				json.put("phone", family.getPhone());
				json.put("liveAddress", family.getLiveAddress());
				json.put("idCardN", family.getIdCardN());
				json.put("idCardP", family.getIdCardP());
				if(StrUtils.isNotEmpty(family.getIdCardP())){
					json.put("left", QiNiuUtil.getJpgUrl(family.getIdCardP()));
				}
				if(StrUtils.isNotEmpty(family.getIdCardN())){
					json.put("right", QiNiuUtil.getJpgUrl(family.getIdCardN()));
				}
			}
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject save(HttpServletRequest request,BusFamilySituation fs){
		JSONObject json = new JSONObject();
		if(fs == null){
			json.put("code", 400);
			json.put("msg", "家庭信息为空");
			return json;
		}
		try {
			if(fs.getIsDead()!=null&&fs.getIsDead().equals(Constants.FAMILY_DEAD)){
				fs=new BusFamilySituation();
				fs.setId(UUIDUtil.getUUID());
				fs.setCreOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				fs.setCreOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				fs.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				fs.setCreDate(new Date());
			}
			BusFamilySituation bfs = familySituationService.selectByIntopIdAndType(fs);
			fs.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			fs.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			fs.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
			fs.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
			fs.setUpdDate(new Date());
			if(bfs != null){
				fs.setId(bfs.getId());
				fs.setCreOperId(bfs.getCreOperId());
				fs.setCreOperName(bfs.getCreOperName());
				fs.setCreOrgId(bfs.getCreOrgId());
				fs.setCreDate(bfs.getCreDate());
			}else{
				fs.setCreOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				fs.setCreOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				fs.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				fs.setCreDate(new Date());
			}
			if(StrUtils.isNotEmpty(fs.getName()) && StrUtils.isNotEmpty(fs.getIdCard()) &&StrUtils.isNotEmpty(fs.getPhone())){
				Map<String, Object> map = familySituationService.saveOrUpdate(fs);
				boolean flag = (boolean) map.get("reject");
				if(flag == true){
					BusIntoPiece primaryKey = intoPieceService.selectByPrimaryKey(bfs.getIntoPieceId());
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
					
					json.put("message", "该客户不符合进件申请条件，请在列表查看并核实原因，30天后重新发起申请！");
					json.put("code", 400);
					return json;
				}
			}else{
				if(StrUtils.isNotEmpty(fs.getId())){
					familySituationService.updateByPrimaryKey(fs);
				}else{
					fs.setId(UUIDUtil.getUUID());
					familySituationService.insert(fs);
				}
			}
			json.put("code", 200);
			json.put("msg", "保存成功");
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
			return json;
		}
		
	}
	
	@RequestMapping("/coland")
	@ResponseBody
	public JSONObject coLand(String intoPieceId){
		JSONObject json = new JSONObject();
		try {			
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			List<BusProduct> listp = productService.selectListByFinsId(ip.getLenderId());
			json.put("list", familySituationService.thirdpartycredit(intoPieceId));
			json.put("serviceFee", GetRepaymentUtils.getServiceFee(ip.getCapital(), ip.getTerm().intValue(), listp.get(0)).get("first"));
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", "土地共有人查询失败");
		}
		return json;
	}
}
