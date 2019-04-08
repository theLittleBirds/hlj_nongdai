package com.nongyeos.loan.admin.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusFamilySituation;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusMember;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.mapper.BusIntoPieceMapper;
import com.nongyeos.loan.admin.mapper.IntSignatoriesMapper;
import com.nongyeos.loan.admin.mapper.SysOrgApplicationMapper;
import com.nongyeos.loan.admin.mapper.SysOrgMapper;
import com.nongyeos.loan.admin.mapper.SysPersonMapper;
import com.nongyeos.loan.admin.mapper.SysPersonorgMapper;
import com.nongyeos.loan.admin.resultMap.IntoPieceMap;
import com.nongyeos.loan.admin.service.IContactMakeService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IMemberLoginService;
import com.nongyeos.loan.admin.service.IMemberService;
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.entity.AppEntity;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.mapper.AppApplicationMapper;
import com.nongyeos.loan.app.mapper.AppEntryMapper;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.JPushDO;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.UUIDUtil;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.FlowMgr;

@Service("intoPieceService")
public class IntoPieceServiceImpl implements IIntoPieceService {
	
	@Autowired
	private BusIntoPieceMapper intoPieceMapper;
	
	@Autowired
	private IMemberService memberService;
	
	@Autowired
	private AppApplicationMapper applicationMapper;
	
	@Autowired
	private SysOrgMapper orgMapper;
	
	@Autowired
	private SysPersonMapper personMapper;
	
	@Autowired
	private SysPersonorgMapper personorgMapper;
	
	@Autowired
	private IMemberLoginService memberLoginService;
	
	@Autowired
	private FlowMgr flowMgrImpl;
	
	@Autowired
	private AppEntryMapper appEntryMapper;
	
	@Autowired
	private IContactMakeService contactMakeService;
	
	@Autowired
	private IntSignatoriesMapper signatoriesMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusIntoPiece record) throws Exception{
		if(record == null){
			throw new Exception("进件模板为空");
		}
		if(record.getId() == null || "".equals(record.getId())){
			throw new Exception("进件模板主键为空");
		}
		try {
			return intoPieceMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusIntoPiece selectByPrimaryKey(String id) throws Exception{
		if(id == null || "".equals(id)){
			throw new Exception("进件模板主键为空");
		}
		try {
			return intoPieceMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusIntoPiece record) throws Exception{
		if(record == null){
			throw new Exception("进件模板为空");
		}
		if(record.getId() == null || "".equals(record.getId())){
			throw new Exception("进件模板主键为空");
		}
		try {
			return intoPieceMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<IntoPieceMap> selectByPage(int currentPage,
			int pageSize, IntoPieceMap record) throws Exception {
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<IntoPieceMap> list = intoPieceMapper.selectByCondition(record);
			int countNums = intoPieceMapper.selectCountByCondition(record);
			PageBeanUtil<IntoPieceMap> pageData = new PageBeanUtil<IntoPieceMap>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BusIntoPiece saveIntoPieceAndMember(BusIntoPiece intoPiece) throws Exception {
		if(intoPiece==null)
			throw new Exception("进件模板为空！");
		try {
			if(StringUtils.isEmpty(intoPiece.getId())){
				if(intoPiece.getCreditCapital() == null){
					//初始化
					intoPiece.setCreditCapital(BigDecimal.ZERO);
				}
				intoPiece.setId(UUIDUtil.getUUID());
				intoPiece.setCreDate(DateUtils.getNowDate());
				intoPiece.setUpdDate(DateUtils.getNowDate());
				intoPiece.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
//				Map<String, Object> map1 = new HashMap<String, Object>();
//				List<String> alist = new ArrayList<String>();
//				for (int i = 1; i < 13; i++) {
//					alist.add(i+"");
//				}
//				map1.put("idCard", intoPiece.getIdCard());
//				map1.put("status", alist);
//				map1.put("orgId", intoPiece.getOrgId());
//				List<IntoPieceMap> list = intoPieceMapper.selectByHistory(map1);
//				//如果该部门下任有进件，无法进件
//				if(list!=null&&list.size()>0){
//					return null;
//				}
				Map<String, Object> map = memberService.addMemberByIntoPiece(intoPiece, intoPiece.getId());
				BusMember member = (BusMember) map.get("member");
				intoPiece.setMemberId(member.getMemberId());
				if((Boolean)map.get("reject")){
					
					intoPiece.setReject(Constants.REJECT_FLAG_YES);
				}
				intoPieceMapper.insert(intoPiece);
			}else{
				BusIntoPiece ip = intoPieceMapper.selectByPrimaryKey(intoPiece.getId());
				Map<String, Object> map =memberService.addMemberByIntoPiece(intoPiece, intoPiece.getId());
				BusMember member = (BusMember) map.get("member");
				intoPiece.setMemberId(member.getMemberId());
				if((Boolean)map.get("reject")){
					
					intoPiece.setReject(Constants.REJECT_FLAG_YES);
				}
				intoPieceMapper.updateByPrimaryKeySelective(intoPiece);
			}
			return intoPiece;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		
	}

	@Override
	public String selectModelByIntoPID(String intoPieceId,String orgId) throws Exception {
		
		try {
			StringBuilder selectsb = new StringBuilder();
			if(intoPieceId==null){
				intoPieceId="";
			}
			BusIntoPiece intoPiece =null;
			if(!StringUtils.isEmpty(intoPieceId)){
				intoPiece = intoPieceMapper.selectByPrimaryKey(intoPieceId);
			}
			if(intoPiece==null){
				intoPiece=new BusIntoPiece();
			}
			selectsb.append("<option value=''>--请选择--</option>");
//			if(StringUtils.isEmpty(orgId)){
//				return selectsb.toString();
//			}
//			List<AppApplication> list = applicationMapper.selectAllApplications();
			List<AppApplication> list =null;
			if(StringUtils.isEmpty(orgId)){
				if(intoPiece.getOrgId()==null){
					list = applicationMapper.selectAllApplications();
				}else{
					list = applicationMapper.selectApplicationsByOrg(intoPiece.getOrgId());
				}
			}else{
				list = applicationMapper.selectApplicationsByOrg(orgId);
			}
			
			if(list!=null&&list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).getAppId().equals(intoPiece.getModelId()==null?"":intoPiece.getModelId())){
						selectsb.append("<option value=\""+list.get(i).getAppId()+"\" selected=true>"+list.get(i).getCname()+"</option>");
					}else{
						selectsb.append("<option value=\""+list.get(i).getAppId()+"\" >"+list.get(i).getCname()+"</option>");
					}
				}
				
			}
			
			return selectsb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Map<String, Object> getOrgTree(String intopId) throws Exception {
		if(intopId==null){
			throw new Exception("未指定进件标识");
		}
		try {
			BusIntoPiece intoPiece = null;
			if(!intopId.equals("noId")){
				intoPiece = intoPieceMapper.selectByPrimaryKey(intopId);
			}
			if(intoPiece==null)
				intoPiece=new BusIntoPiece();
			Map<String, Object> map = new HashMap<String, Object>();
			List<SysOrg> productOrgs = orgMapper.selectPrList();
			SysOrg productOrg = orgMapper.selectByPrimaryKey(intoPiece.getOrgId());
			String orgId ="";
			String orgName ="";
			if(productOrg!=null){
				orgId=productOrg.getOrgId();
				orgName=productOrg.getFullCname();
			}
			
			String jsonOrg = "";
			for (int i = 0; i < productOrgs.size(); i++) {
				SysOrg first = productOrgs.get(i);
				if(!jsonOrg.equals(""))
					jsonOrg+=",";
				jsonOrg+=getDescendants(first,productOrg);
			}
			map.put("ids", orgId);
			map.put("names", orgName);
			map.put("jsonData", "["+jsonOrg+"]");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String getDescendants(SysOrg pOrg,SysOrg orgProduct) {
		try {
			String jsonDescendants = "";
			String jsonSameLevel = "";
			if(pOrg == null)
				return(jsonDescendants);
			jsonDescendants = "{\"text\":\"" + pOrg.getFullCname() + "\", \"id\":\"" + pOrg.getOrgId() + "\",  \"selectable\":" + true ;
			if(orgProduct!=null){
				if(pOrg.getOrgId().equals(orgProduct.getOrgId())){
					jsonDescendants+= ",\"state\":{\"checked\":true}";
				}
			}
			List<SysOrg> childList = orgMapper.selectPList(pOrg.getOrgId());
			if(childList.size() == 0)
				jsonDescendants = jsonDescendants + "}";
			else
			{
				jsonDescendants = jsonDescendants + " , \"nodes\": [";
				
				for(int i=0;i<childList.size();i++)
				{
					SysOrg childOrg = childList.get(i);
					if(!jsonSameLevel.equals(""))
						jsonSameLevel = jsonSameLevel + ",";
					
					jsonSameLevel = jsonSameLevel + getDescendants(childOrg,orgProduct);
				}
				
				jsonDescendants = jsonDescendants + jsonSameLevel + "]}";
			}
			return jsonDescendants;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(BusIntoPiece record) throws Exception {
		if(record == null){
			throw new Exception("进件模板为空");
		}
		if(record.getId() == null || "".equals(record.getId())){
			throw new Exception("进件模板主键为空");
		}
		try {
			return intoPieceMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<IntoPieceMap> selectByMemberLoginId(Integer page1,
			int pageSize, String condition, String loginId,
			short commonIsnotDelete, String channel) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> asList = Arrays.asList(condition.toString().split(","));
		map.put("status", asList);
		map.put("loginId", loginId);
		map.put("isDelete", commonIsnotDelete);
		map.put("channel", channel);
		try {
			PageHelper.startPage(page1, pageSize, false);
			List<IntoPieceMap> list = intoPieceMapper.selectByMemberLoginId(map);
			int countNums = intoPieceMapper.selectCountByMemberLoginId(map);
			PageBeanUtil<IntoPieceMap> pageData = new PageBeanUtil<IntoPieceMap>(page1, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<IntoPieceMap> selectAllIntoPieces(Map<String, Object> map)
			throws Exception {
		if(map==null){
			throw new Exception("查询条件为空！");
		}
		try {
			return intoPieceMapper.selectByMemberLoginId(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错！");
		}
	}

	
	@Override
	public PageBeanUtil<IntoPieceMap> selectByUserId(Integer page1, int pageSize, String string,
			String userId, short commonIsnotDelete, String memberName) throws Exception {
		try {
			SysPerson person = personMapper.selectByUserIdAndIsdefault(userId);
			List<SysPersonorg> personorgs = personorgMapper.selectByPersonId(person.getPersonId());
			StringBuilder condition = new StringBuilder();
			List<String> orgIds = new ArrayList<String>(); 
			if(personorgs!=null&&personorgs.size()>0){
				for (int i = 0; i < personorgs.size(); i++) {
					List<String> orgIdsTemp = orgMapper.selectByIdOrParent(personorgs.get(i).getOrgId());
					if(orgIdsTemp!=null&&orgIdsTemp.size()>0){
						for (int j = 0; j < orgIdsTemp.size(); j++) {
							orgIds.add(orgIdsTemp.get(j));
						}
					}
				}
			}
			if(orgIds.size()>0){
				condition.append(orgIds.get(0));
				for (int i = 0; i < orgIds.size(); i++) {
					condition.append(","+orgIds.get(i));
				}
			}
			List<String> asList = Arrays.asList(condition.toString().split(","));
			List<String> bsList = Arrays.asList(string.split(","));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", asList);
			map.put("isDeleted", Constants.COMMON_ISNOT_DELETE);
			map.put("status", bsList);
			if(StringUtils.isNotEmpty(memberName)){
				map.put("memberName", memberName);
			}
			PageHelper.startPage(page1, pageSize, false);
 			List<IntoPieceMap> list = intoPieceMapper.selectByOrgIds(map);
 			int countNums = intoPieceMapper.selectCountByOrgIds(map);
			PageBeanUtil<IntoPieceMap> pageData = new PageBeanUtil<IntoPieceMap>(page1, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询失败！");
		}
		
	}

	@Override
	public List<IntoPieceMap> selectByUserIdAndStatus(String userId,
			short commonIsnotDelete) throws Exception {
		try {
			SysPerson person = personMapper.selectByUserIdAndIsdefault(userId);
			List<SysPersonorg> personorgs = personorgMapper.selectByPersonId(person.getPersonId());
			StringBuilder condition = new StringBuilder();
			List<String> orgIds = new ArrayList<String>(); 
			if(personorgs!=null&&personorgs.size()>0){
				for (int i = 0; i < personorgs.size(); i++) {
					List<String> orgIdsTemp = orgMapper.selectByIdOrParent(personorgs.get(i).getOrgId());
					if(orgIdsTemp!=null&&orgIdsTemp.size()>0){
						for (int j = 0; j < orgIdsTemp.size(); j++) {
							orgIds.add(orgIdsTemp.get(j));
						}
					}
				}
			}
			if(orgIds.size()>0){
				condition.append(orgIds.get(0));
				for (int i = 0; i < orgIds.size(); i++) {
					condition.append(","+orgIds.get(i));
				}
			}
			List<String> asList = Arrays.asList(condition.toString().split(","));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", asList);
			map.put("isDeleted", Constants.COMMON_ISNOT_DELETE);
			map.put("date", new Date(DateUtils.getNowDate().getTime()-5*24*60*60*1000l) );
 			return intoPieceMapper.selectByOrgIds(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错！");
		}
	}

	@Override
	public List<BusIntoPiece> selectByMemberId(String memberId) throws Exception{
		if(memberId==null){
			throw new Exception("查询条件为空！");
		}
		try {
			return intoPieceMapper.selectByMemberId(memberId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错！");
		}
	}
	public void updateBean(Object record,SqlSession session) throws Exception{
		try{
			if(record != null){
				session.getMapper(BusIntoPieceMapper.class).updateByPrimaryKeySelective((BusIntoPiece) record);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("record为空");
		}
	}

	@Override
	public List<IntoPieceMap> weekIpStatistics(Map<String, Object> map)
			throws Exception {
		try {
			return intoPieceMapper.weekIpStatistics(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}		
	}

	@Override
	public List<IntoPieceMap> weekMoneyStatistics(Map<String, Object> map)
			throws Exception {
		try {
			return intoPieceMapper.weekMoneyStatistics(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}	
	}

	@Override
	public int todayCreCount(Map<String, Object> map) throws Exception {
		try {
			return intoPieceMapper.todayCreCount(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}	
	}

	@Override
	public List<BusIntoPiece> todayMoneyToMember(Map<String, Object> map)
			throws Exception {
		try {
			return intoPieceMapper.todayMoneyToMember(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}	
	}

	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject submitPrimary(BusIntoPiece ip,BusLoan loan,List<BusFamilySituation> list,String personId) throws Exception{
		AppEntry entry = appEntryMapper.queryByAppModeId(ip.getId());
		if(entry == null)
			throw new Exception("无流程记录");
		JSONObject node = flowMgrImpl.getNextTask(entry);
		if(node != null && node.getIntValue("code") == 200){
			JSONArray arr = node.getJSONArray("nodes");
			if(arr != null){
				String nextNodeId = arr.getJSONObject(0).getString("nodeId");
				String pcId = node.getString("pcId");
				BusinessObject business = new BusinessObject(entry,ip,applicationMapper.selectByPrimaryKey(ip.getModelId()));
				JSONObject result = flowMgrImpl.next(business, nextNodeId, pcId, personId);
				result.put("nextNodeId", nextNodeId);
				return result;
			}				
		}
		throw new Exception("未配置下一步节点");
	}

	@Override
	public Map<String, Object> getOrgs(String personId,String intoPieceId) throws Exception {
		if(StringUtils.isEmpty(personId)){
			throw new Exception("人员标识为空！");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BusIntoPiece ip =null;
			if(StringUtils.isEmpty(intoPieceId)){
				ip=new BusIntoPiece();
			}else{
				ip=intoPieceMapper.selectByPrimaryKey(intoPieceId);
				if(ip==null){
					ip=new BusIntoPiece();
				}
			}
			StringBuilder selectsb = new StringBuilder();
			selectsb.append("<option value=''>--请选择--</option>");
//			List<SysOrg> orgs = orgMapper.selectOrgsByPerson(personId);
			List<SysOrg> orgs =orgMapper.selectipOrgs(personId);
			if(orgs!=null&&orgs.size()>0){

				for (int i = 0; i < orgs.size(); i++) {
					if(orgs.get(i).getOrgId().equals(ip.getOrgId()==null?"":ip.getOrgId())){
						selectsb.append("<option value=\""+orgs.get(i).getOrgId()+"\" selected='selected'>"+orgs.get(i).getFullCname()+"</option>");
					}else{
						selectsb.append("<option value=\""+orgs.get(i).getOrgId()+"\" >"+orgs.get(i).getFullCname()+"</option>");
					}
				}
			}
			map.put("result", selectsb.toString());
			map.put("errono", 2000);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errono", 3000);
		}
		return map;
	}

	@Override
	public Map<String, Object> selectExportMap(String intopieceId)
			throws Exception {
		if(StringUtils.isEmpty(intopieceId)){
			throw new Exception("进件标识为空");
		}
		try {
			return intoPieceMapper.selectExportMap(intopieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
	}

	@Override
	public PageBeanUtil<BusIntoPiece> selectNongZiPage(int currentPage,
			int pageSize, Map<String, Object> map) throws Exception {
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusIntoPiece> list = intoPieceMapper.queryNongZi(map);
			int countNums = intoPieceMapper.queryCountNongZi(map);
			PageBeanUtil<BusIntoPiece> pageData = new PageBeanUtil<BusIntoPiece>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return null;
	}

	@Override
	public List<IntoPieceMap> selectByCondition(IntoPieceMap record)
			throws Exception {
		try {
			return intoPieceMapper.selectByCondition(record);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
