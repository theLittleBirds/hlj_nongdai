package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.ReportRptgroup;
import com.nongyeos.loan.admin.mapper.ReportRptgroupMapper;
import com.nongyeos.loan.admin.service.IReportRptgroupService;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.mapper.BusFinsMapper;

@Service("rptgroupService")
public class ReportRptgroupServiceImpl implements IReportRptgroupService{
	
	@Autowired
	private ReportRptgroupMapper groupmapper;
	@Autowired
	private BusFinsMapper busFinsMapper;
	
	int num7 = 2;
	boolean flag = false;
	
	@Override
	public String groupTreeString (){
		String strJson = "[";
		int num = 1;
		int dou = 0;
		BusFins beanFins = null;
		ReportRptgroup beanGroup = null;
		List<BusFins> Flist= busFinsMapper.selectAll();

		if(Flist != null && Flist.size() > 0){
			for(int i = 0;i < Flist.size();i++){
				List<ReportRptgroup> Alist = groupmapper.selectByFinsId(Flist.get(i).getFinsId());
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
				        strJson = groupTreeCh(beanGroup.getGroupId(), strJson, num, num4);
					}
				}
			}
		}
		strJson = strJson + "]";
		return (strJson);
	}

	public String groupTreeCh(String parentId,String strJson,int num,int num4){
		ReportRptgroup beanGroup = null;
		int num3 = num4;
		List<ReportRptgroup> list = groupmapper.selectByParentId(parentId);
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
					strJson = groupTreeCh(beanGroup.getGroupId(), strJson, num, num4);
			}
		}else{
			num7 = num;
			flag = true;
		}
		return strJson;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(ReportRptgroup rptGroup)throws Exception{
		try{
			if(rptGroup != null){
				groupmapper.insertSelective(rptGroup);
			}else{
				throw new Exception("rptGroup为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void upd(ReportRptgroup rptGroup)throws Exception{
		try{
			if(rptGroup != null){
				groupmapper.updateByPrimaryKey(rptGroup);
			}else{
				throw new Exception("rptGroup为空");
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
	public List<ReportRptgroup> getlistByFinsId(String finsId)throws Exception{
		List<ReportRptgroup> list = null;
		try{
			list = groupmapper.selectAllByFindId(finsId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public ReportRptgroup getGroupById(String groupId) throws Exception{
		ReportRptgroup beanGroup = null;
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
	public ReportRptgroup selectByParentGroupId(String parentGroupId)throws Exception {
		ReportRptgroup beanGroup = null;
		try{
			if(parentGroupId != null && !parentGroupId.equals("")){
				beanGroup = groupmapper.selectByParentGroupId(parentGroupId);
			}else{
				throw new Exception("groupId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return beanGroup;
	}

}
