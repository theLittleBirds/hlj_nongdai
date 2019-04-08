package com.nongyeos.loan.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nongyeos.loan.admin.entity.BusLender;
import com.nongyeos.loan.admin.entity.BusLenderOrg;
import com.nongyeos.loan.admin.entity.BusProductOrg;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.mapper.BusLenderMapper;
import com.nongyeos.loan.admin.mapper.BusLenderOrgMapper;
import com.nongyeos.loan.admin.mapper.SysOrgMapper;
import com.nongyeos.loan.admin.service.ILenderService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.UUIDUtil;

@Service("lenderService")
public class LenderServiceImpl implements ILenderService{
	@Autowired
	private BusLenderMapper lenderMapper;
	
	@Autowired
	private BusLenderOrgMapper lenderOrgMapper;
	
	@Autowired SysOrgMapper orgMapper;

	@Override
	public PageBeanUtil<BusLender> selectByPage(int startIndex, int pageSize, BusLender lender) throws Exception {
		if(lender==null){
			throw new Exception("出借人/银行模板为空");
		}
		if(lender.getIdCard().equals(""))
			lender.setIdCard(null);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int totalNum = lenderMapper.selectTotalNum(lender);
			PageBeanUtil<BusLender> pageBean = new PageBeanUtil<BusLender>(startIndex/pageSize+1,pageSize,totalNum);
			map.put("startIndex", startIndex);
			if(pageBean.getIsMore()==0)
				map.put("pageSize", totalNum-startIndex);
			else
				map.put("pageSize", pageSize);
			map.put("name", lender.getName());
			map.put("idCard", lender.getIdCard());
			List<BusLender> list =lenderMapper.selectByPage(map);
			pageBean.setItems(list);
			return pageBean;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean existedSameName(BusLender lender) throws Exception {
		if(lender==null){
			throw new Exception("出借人/银行模板为空");
		}
		BusLender busLender = lenderMapper.selectByName(lender.getName());
		if(busLender!=null){
			if(lender.getLenderId()==null||lender.getLenderId().equals("")){
				return true;
			}else{
				if(!lender.getLenderId().equals(busLender.getLenderId())){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void addLender(BusLender lender, String chooseOrgIds) throws Exception {
		if(lender==null){
			throw new Exception("出借人/银行模板为空");
		}
		lender.setLenderId(UUIDUtil.getUUID());
		lender.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		lender.setCreDate(DateUtils.getNowDate());
		lender.setUpdDate(DateUtils.getNowDate());
		try {
			if(!chooseOrgIds.isEmpty()){
				String[] orgIds = chooseOrgIds.split(",");
				for (int i = 0; i < orgIds.length; i++) {
					BusLenderOrg lenderOrg = new BusLenderOrg();
					lenderOrg.setId(UUIDUtil.getUUID());
					lenderOrg.setOrgId(orgIds[i]);
					lenderOrg.setLenderId(lender.getLenderId());
					lenderOrgMapper.insert(lenderOrg);
				}
				
			}
			lenderMapper.insert(lender);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateLender(BusLender lender, String chooseOrgIds) throws Exception {
		if(lender==null){
			throw new Exception("出借人/银行模板为空");
		}
		lender.setUpdDate(DateUtils.getNowDate());
		try {
			lenderOrgMapper.deleteByLenderId(lender.getLenderId());
			if(!chooseOrgIds.isEmpty()){
				String[] orgIds = chooseOrgIds.split(",");
				for (int i = 0; i < orgIds.length; i++) {
					BusLenderOrg lenderOrg = new BusLenderOrg();
					lenderOrg.setId(UUIDUtil.getUUID());
					lenderOrg.setOrgId(orgIds[i]);
					lenderOrg.setLenderId(lender.getLenderId());
					lenderOrgMapper.insert(lenderOrg);
				}
			}
			lenderMapper.updateByPrimaryKeySelective(lender);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delLender(String lenderId) throws Exception {
		if(lenderId==null){
			throw new Exception("未指定出借人/银行标识");
		}
		BusLender lender = new BusLender();
		lender.setLenderId(lenderId);
		lender.setIsDeleted(Constants.COMMON_IS_DELETE);
		try {
			lenderOrgMapper.deleteByLenderId(lender.getLenderId());
			lenderMapper.updateByPrimaryKeySelective(lender);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> getOrgTree(String lenderId) throws Exception {
		if(lenderId==null){
			throw new Exception("未指定出借人/银行标识");
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<SysOrg> Orgs = orgMapper.selectPrList();
			StringBuilder orgIds =new StringBuilder(""); 
			StringBuilder orgNames =new StringBuilder(""); 
			List<BusLenderOrg> lenderOrgs = null;
			if(!lenderId.equals("noId")){
				lenderOrgs=lenderOrgMapper.selectByLenderId(lenderId);
			}
			if(lenderOrgs!=null&&lenderOrgs.size()>0){
				for (BusLenderOrg busLenderOrg : lenderOrgs) {
					orgIds.append(busLenderOrg.getOrgId()+",");
					SysOrg sysOrg = orgMapper.selectByPrimaryKey(busLenderOrg.getOrgId());
					orgNames.append(sysOrg.getFullCname()+",");
				}
			}
			String jsonOrg = "";
			for (int i = 0; i < Orgs.size(); i++) {
				SysOrg first = Orgs.get(i);
				if(!jsonOrg.equals(""))
					jsonOrg+=",";
				jsonOrg+=getDescendants(first,lenderOrgs);
			}
			map.put("ids", orgIds.toString());
			map.put("names", orgNames.toString());
			map.put("jsonData", "["+jsonOrg+"]");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	private String getDescendants(SysOrg first, List<BusLenderOrg> lenderOrgs) {
		try {
			String jsonDescendants = "";
			String jsonSameLevel = "";
			if(first == null)
				return(jsonDescendants);
			jsonDescendants = "{\"text\":\"" + first.getFullCname() + "\", \"id\":\"" + first.getOrgId() + "\",  \"selectable\":" + true ;
			if(lenderOrgs!=null&&lenderOrgs.size()>0){
				for (BusLenderOrg busLenderOrg : lenderOrgs) {
					if(first.getOrgId().equals(busLenderOrg.getOrgId())){
						jsonDescendants+= ",\"state\":{\"checked\":true}";
					}
				}
			}
			List<SysOrg> childList = orgMapper.selectPList(first.getOrgId());
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
					
					jsonSameLevel = jsonSameLevel + getDescendants(childOrg,lenderOrgs);
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
	public List<BusLender> selectByOrgId(String orgId) throws Exception {
		if(orgId == null || "".equals(orgId))
			throw new Exception("产业链标识为空");
		try {
			return lenderMapper.selectByOrgId(orgId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusLender selectByPrimaryKey(String lenderId) throws Exception {
		if(lenderId == null || "".equals(lenderId))
			throw new Exception("出借人标识为空");
		try {
			return lenderMapper.selectByPrimaryKey(lenderId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusLender> selectAll() throws Exception {
		try {
			return lenderMapper.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	
}
