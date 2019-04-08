package com.nongyeos.loan.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysOrgApplication;
import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.mapper.SysOrgApplicationMapper;
import com.nongyeos.loan.admin.mapper.SysOrgMapper;
import com.nongyeos.loan.admin.mapper.SysPersonorgMapper;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.base.util.Constants;

@Service("orgService")
public class OrgServiceImpl implements IOrgService{
	
	@Autowired
	private SysOrgMapper orgMapper;
	@Autowired
	private SysOrgApplicationMapper orgappMapper;
	@Autowired
	private SysPersonorgMapper perorgMapper;
    @Resource 
    private ISysSenoService sysSenoService;
    
	int num7 = 2;
	boolean flag = false;
	boolean flag2 = false;
	@Override
    public SysOrg selectByOrgId(String orgId) throws Exception {  
		try {
			if (orgId != null) {
				SysOrg org = orgMapper.selectByPrimaryKey(orgId);
				return org;
			}else {
				throw new Exception("orgid为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
    }

	@Override
	public String getBaseOrgId(String orgId) throws Exception {
		try {
			SysOrg sysOrg = orgMapper.selectByPrimaryKey(orgId);
			if(sysOrg != null){
				if(sysOrg.getParentOrgId() != null && !sysOrg.getParentOrgId().equals("")){
					return getBaseOrgId(sysOrg.getParentOrgId());
				}else{
					return sysOrg.getOrgId();
				}
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("orgid为空");
		}
	}
	
	@Override
	public String getListByParentId(String personId, boolean firstLevel,boolean checkbox) throws Exception {

		List<SysOrg> listFirst = new ArrayList<>();
	    Map<String, String> map = new HashMap<>();
		
		List<SysPersonorg> list = perorgMapper.selectByPersonId(personId);
		if (list != null && list.size()>0) {
			for (SysPersonorg personorg : list) {
				SysOrg org = orgMapper.selectByPrimaryKey(personorg.getOrgId());
				listFirst.add(org);
			}
		}

		String jsonData = "";
		for (int i = 0; i < listFirst.size(); i++) {
			SysOrg org = listFirst.get(i);
			if (map.get(org.getOrgId())=="1") {
				
			}else{
				if (!jsonData.equals(""))
					jsonData = jsonData + ",";
				if (firstLevel == true) {
					jsonData = jsonData
							+ getDescendants(org, firstLevel, checkbox,map);
				} else {
					jsonData = jsonData + getDescendants(org, checkbox,listFirst,map);
				}
			}
		}

		listFirst = new ArrayList<>();
		map = new HashMap<>();
		
		jsonData = "[" + jsonData + "]";
		return (jsonData);
	}
	
	private String getDescendants(SysOrg org, boolean checkbox, List<SysOrg> listFirst,Map<String, String> map) throws Exception {
		String jsonDescendants = "";
		String jsonSameLevel = "";
		
		if(org == null)
			return(jsonDescendants);
		if(org.getIsDeleted() != null && org.getIsDeleted() == 1){
			jsonDescendants = "{\"text\":\"" + org.getFullCname() + "\",\"state\": {\"checked\":\"true\"},\"id\":\"" + org.getOrgId() + "\", \"parentOrgIds\":\"" + org.getParentOrgIds() + "\"";
		}else{
			jsonDescendants = "{\"text\":\"" + org.getFullCname() + "\", \"id\":\"" + org.getOrgId() + "\", \"parentOrgIds\":\"" + org.getParentOrgIds() + "\"";
		}
		
		if(checkbox)
			jsonDescendants = jsonDescendants + ", \"checked\":\"false\"";
		
		List<SysOrg> childList = getChildList1(org, listFirst,map);
		if(childList.size() == 0)
			jsonDescendants = jsonDescendants + "}";
		else
		{
			jsonDescendants = jsonDescendants +", \"state\":{\"expanded\":\"true\"}" + " , \"nodes\": [";
			
			for(int i=0;i<childList.size();i++)
			{
				SysOrg childItem = childList.get(i);
				if(!jsonSameLevel.equals(""))
					jsonSameLevel = jsonSameLevel + ",";
				
				jsonSameLevel = jsonSameLevel + getDescendants(childItem, checkbox, listFirst, map);
			}
			
			jsonDescendants = jsonDescendants + jsonSameLevel + "]}";
		}
		return(jsonDescendants);
	}
	
	private List<SysOrg> getChildList1(SysOrg org,List<SysOrg> listFirst,Map<String, String> map) throws Exception {
		try {
			List<SysOrg> list = new ArrayList<>();
			List<String> list1 = new ArrayList<>();
			List<String> list2 = new ArrayList<>();
			for (SysOrg sysOrg : listFirst) {
				if (sysOrg!=null) {
					list1.add(sysOrg.getOrgId());
				}
			}
			if (org != null) {
				List<SysOrg> list3 = orgMapper.selectPList(org.getOrgId());
				for (SysOrg sysOrg : list3) {
					list2.add(sysOrg.getOrgId());
				}
			}else {
				throw new Exception("org为空");
			}
			list1.retainAll(list2);
			for (String orgId : list1) {
				list.add(orgMapper.selectByPrimaryKey(orgId));
				map.put(orgId, "1");
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	private List<SysOrg> getChildList(SysOrg org) throws Exception {
		try {
			if (org != null) {
				List<SysOrg> list = orgMapper.selectPList(org.getOrgId());
				return list;
			}else {
				throw new Exception("org为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	private String getDescendants(SysOrg org, boolean firstLevel,
			boolean checkbox,Map<String, String> map) {
		String jsonDescendants = "";
		
		if(org == null)
			return(jsonDescendants);
		
		jsonDescendants = "{\"text\":\"" + org.getFullCname() + "\", \"id\":\"" + org.getOrgId() + "\"";
		if(checkbox)
			jsonDescendants = jsonDescendants + ", \"checked\":\"false\"";
		
		jsonDescendants = jsonDescendants + "}";
		return(jsonDescendants);
	}
	
	@Override
	public List<SysOrg> getFirstLevelList(SysOrg org) throws Exception {
		try {
				List<SysOrg> list = null;
				list = orgMapper.selectPrList();
				return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("org为空");
		}
	}
	
	@Override
	public String orgTreeString(List<String> orgIdList){
		String strJson = "[";
		int num = 1;
		SysOrg beanOrg = null;
		List<SysOrg> list= new ArrayList<>();
		for (String orgId : orgIdList) {
			SysOrg org = orgMapper.selectByPrimaryKey(orgId);
			list.add(org);
		}
		
		if(list != null && list.size() > 0){
			for(int i = 0;i < list.size();i++){
				beanOrg = list.get(i);
				if (i > 0)
					strJson = strJson + ", ";
				
				strJson = strJson + "{\"id\":" + num + ", \"pid\":" + "0"
						+ ", \"name\":\"" + beanOrg.getFullCname()
						+ "\", \"orgId\":\"" + beanOrg.getOrgId()
						+ "\"}";
			}
		}
		strJson = strJson + "]";
		return (strJson);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addOrg(SysOrg sysOrg) throws Exception {
		String parentOrgIdsString = "";
		try {
			if(sysOrg != null){
				sysOrg.setOrgId(sysSenoService.getNextString(Constants.TABLE_NAME_ORG, 10, "D", 1));
				orgMapper.insert(sysOrg);
				if(sysOrg.getParentOrgIds() != null && !sysOrg.getParentOrgIds().equals("")){
				      parentOrgIdsString = sysOrg.getParentOrgIds() + "," + sysOrg.getParentOrgId();
				}else{
					  parentOrgIdsString = sysOrg.getParentOrgId();
				}
			    sysOrg.setParentOrgIds(parentOrgIdsString);
			    orgMapper.updateByPrimaryKeySelective(sysOrg);
			}else {
				throw new Exception("sysorg为空");
			}
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
	}

	@Override
	public SysOrg selectByName(String name) throws Exception {
		try {
			if (name != null) {
				SysOrg sysOrg = orgMapper.selectByName(name);
				return sysOrg;
			}else {
				throw new Exception("name为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateOrg(SysOrg sysOrg) throws Exception {
		try {
			if (sysOrg != null) {
				orgMapper.updateByPrimaryKey(sysOrg);
			}else {
				throw new Exception("sysorg为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
        }
	}

	@Override
	public List<SysOrg> getChildOrgs(String orgId) throws Exception {
		try {
			if (orgId != null) {
				List<SysOrg> list = new ArrayList<SysOrg>();
				SysOrg bean = null;
				bean = orgMapper.selectByPrimaryKey(orgId);
				list = getChildList(bean);
				return list;
			}else {
				throw new Exception("orgid为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
        }
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteByOrgId(String orgId) throws Exception {
		try {
			if (orgId != null) {
				orgMapper.deleteByPrimaryKey(orgId);
			}else {
				throw new Exception("orgid为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
        }
	}

	@Override
	public List<SysOrg> selectByNameSearch(String orgName, String rootOrgId) throws Exception {
		if(StringUtils.isEmpty(orgName)){
			throw new Exception("请填写商家名！");
		}
		List<SysOrg> list = null;
		try {
			SysOrg org = new SysOrg();
			org.setFullCname(orgName);
			org.setOrgId(rootOrgId);
			list = orgMapper.selectByNameSearch(org);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
		
		return list;
	}

	@Override
	public List<SysOrg> selectOrgsByPerson(String personId) throws Exception {
		if(StringUtils.isEmpty(personId)){
			throw new Exception("角色标记为空！");
		}
		try {
			return  orgMapper.selectipOrgs(personId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错！");
		}
	}

	public void delOrgapp(String orgId) throws Exception {
		try{
			if(orgId != null && !orgId.equals("")){
				orgappMapper.deleteByOrgId(orgId);
			}else{
				throw new Exception("orgId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addOrgapp(SysOrgApplication bean) throws Exception {
		try{
			if(bean != null){
				orgappMapper.insertSelective(bean);
			}else{
				throw new Exception("对象为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<SysOrgApplication> getOrgs(String orgId) throws Exception {
		try{
			if(orgId != null && !orgId.equals("")){
				List<SysOrgApplication> list = orgappMapper.selectByOrgId(orgId);
			    return list;
			}else{
				throw new Exception("orgId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	
	@Override
	public List<SysPersonorg> getOrgByPersonId(String personId) throws Exception{
		try{
			if(personId != null && !personId.equals("")){
				List<SysPersonorg> list = perorgMapper.selectByPersonId(personId);
				return list;
			}else{
				throw new Exception("personId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<SysOrg> selectAll() throws Exception {
		try {
			List<SysOrg> list = orgMapper.selectAll();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<SysOrg> selectBaseOrg() throws Exception {
		try {
			return orgMapper.selectBaseOrg();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
    
}
