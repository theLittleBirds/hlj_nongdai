package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.afterturn.easypoi.excel.entity.params.ExcelBaseEntity;

import com.nongyeos.loan.app.entity.AppAppgroup;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.mapper.AppAppgroupMapper;
import com.nongyeos.loan.app.mapper.BusFinsMapper;
import com.nongyeos.loan.app.service.IAppGroupService;

@Service("groupService")
public class AppGroupServiceImpl implements IAppGroupService{
	
	@Autowired
	private AppAppgroupMapper groupmapper;
	@Autowired
	private BusFinsMapper busFinsMapper;
	
	int num7 = 2;
	int num = 1;
	boolean flag = false;
	
	@Override
	public String groupTreeString (List<String> orgIdList){
		String strJson = "[";
		int dou = 0;
		BusFins beanFins = null;
		AppAppgroup beanGroup = null;
		List<BusFins> Flist= busFinsMapper.selectByOrgIdList(orgIdList);

		if(Flist != null && Flist.size() > 0){
			for(int i = 0;i < Flist.size();i++){
				List<AppAppgroup> Alist = groupmapper.selectByFinsId(Flist.get(i).getFinsId());
				if(Alist != null && Alist.size() > 0){
					
					if(dou != 0){
						num = num7;
					}
					beanFins = Flist.get(i);
					if (dou > 0){
						strJson = strJson + ", ";
					}
					dou++;
					strJson = strJson + "{\"id\":" + num + ", \"pid\":" + "0"
							+ ", \"name\":\"" + beanFins.getCname()
				          	+ "\", \"finsId\":\"" + beanFins.getFinsId()
							+ "\"}";
					int num2 = num;
					num++;
					for(int j = 0;j < Alist.size();j++){
						beanGroup = Alist.get(j);
						strJson = strJson + ", ";
					    strJson = strJson + "{\"id\":" + num + ", \"pid\":" + num2
							+ ", \"name\":\"" + beanGroup.getName()
				          	+ "\", \"groupId\":\"" + beanGroup.getGroupId()
				          	+ "\", \"finsId\":\"" + beanGroup.getFinsId()
							+ "\"}";
					    int num4 = num;
					    num++;
				        strJson = groupTreeCh(beanGroup.getGroupId(), strJson, num4);
					}
				}
			}
		}
		strJson = strJson + "]";
		return (strJson);
	}

	public String groupTreeCh(String parentId,String strJson, int num4){
		AppAppgroup beanGroup = null;
		int num3 = num4;
		List<AppAppgroup> list = groupmapper.selectByParentId(parentId);
		if(list != null && list.size() > 0){
			strJson = strJson + ", ";
			for(int j = 0;j < list.size();j++){
				if(flag == true && j != 0){
					num = num7;
				}
				beanGroup = list.get(j);
				if(beanGroup != null){
					if (j > 0)
						strJson = strJson + ", ";
					
					strJson = strJson + "{\"id\":" + num
							+ ", \"pid\":" + num3 
							+ ", \"name\":\"" + beanGroup.getName()
							+ "\", \"groupId\":\""+ beanGroup.getGroupId()
							+ "\", \"finsId\":\"" + beanGroup.getFinsId()
							+ "\"}";
				}
				num4 = num;
				num++;
				flag = false;
					strJson = groupTreeCh(beanGroup.getGroupId(), strJson, num4);
			}
		}else{
			num7 = num;
			flag = true;
		}
		return strJson;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addGroup(AppAppgroup appGroup)throws Exception{
		try{
			if(appGroup != null){
				groupmapper.insertSelective(appGroup);
			}else{
				throw new Exception("appGroup为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updGroup(AppAppgroup appGroup)throws Exception{
		try{
			if(appGroup != null){
				groupmapper.updateByPrimaryKey(appGroup);
			}else{
				throw new Exception("appGroup为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delGroup(String groupId)throws Exception{
		try{
			if(groupId != null && !groupId.equals("")){
				groupmapper.deleteByPrimaryKey(groupId);
			}else{
				throw new Exception("groupId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public List<AppAppgroup> getlistByFinsId(String finsId)throws Exception{
		List<AppAppgroup> list = null;
		try{
			list = groupmapper.selectAllByFindId(finsId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<AppAppgroup> getAll()throws Exception{
		List<AppAppgroup> list = null;
		try{
			list = groupmapper.selectAll();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public AppAppgroup getGroupById(String groupId) throws Exception{
		AppAppgroup beanGroup = null;
		try{
			if(groupId != null && !groupId.equals("")){
				beanGroup = groupmapper.selectByPrimaryKey(groupId);
			}else{
				throw new Exception("groupId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return beanGroup;
	}

	@Override
	public List<AppAppgroup> selectByParentId(String id) throws Exception {
		List<AppAppgroup> list = null;
		try{
			list = groupmapper.selectByParentId(id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<BusFins> getFinsByOrgList(List<String> orgIdList)throws Exception {
		List<BusFins> Finslist= null;
		try{
			if(orgIdList != null){
				Finslist = busFinsMapper.selectByOrgIdList(orgIdList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Finslist;
	}

}
