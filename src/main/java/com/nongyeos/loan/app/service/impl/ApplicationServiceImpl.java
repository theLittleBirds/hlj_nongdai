package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.AppAppgroup;
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.mapper.AppAppgroupMapper;
import com.nongyeos.loan.app.mapper.AppApplicationMapper;
import com.nongyeos.loan.app.mapper.BusFinsMapper;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("applicationService")
public class ApplicationServiceImpl implements IApplicationService{
	
	@Autowired
	private AppApplicationMapper appLicationMapper;
	@Autowired
	private AppAppgroupMapper groupmapper;
	@Autowired
	private BusFinsMapper busFinsMapper;
	
	int num;
	
	@Override
	public String appTreeString(List<String> orgIdList)
	{
		AppAppgroup beanGroup;
		int myselfNum;
		String jsonAll;
		String jsonFins;
		
		num = 0;
		jsonAll = "";
		
		List<BusFins> FList = busFinsMapper.selectByOrgIdList(orgIdList);
		if(FList != null)
		{
			for(int i=0;i<FList.size();i++)
			{
				BusFins beanFins = FList.get(i);				
				List<AppAppgroup> AGList = groupmapper.selectByFinsId(beanFins.getFinsId());
				if(AGList != null)
				{
					num ++;
					jsonFins = "";
					myselfNum = num;
					
					for(int j=0;j<AGList.size();j++)
					{						
						beanGroup = AGList.get(j);
						if(beanGroup != null)
						{
							num ++;
							String jsonGrp = getGroupJson(beanGroup, num, myselfNum);
							if(!jsonGrp.equals(""))
							{
								if(jsonFins.equals(""))
								{
									jsonFins = jsonGrp;
								}
								else
								{
									jsonFins = jsonFins + ", " + jsonGrp;
								}
							}
						}
					}
					
					if(!jsonFins.equals(""))
					{
						if(!jsonAll.equals(""))
						{
							jsonAll = jsonAll + ", ";
						}
						
						jsonAll = jsonAll + "{\"id\":" + myselfNum + ", \"pid\":" + "0"
										  + ", \"name\":\"" + beanFins.getCname()
										  + "\", \"finsId\":\"" + beanFins.getFinsId()
										  + "\"}, " + jsonFins;
					}
				}			
			}
		}
		
		return("["+jsonAll+"]");
	}
	
	
	private String getGroupJson(AppAppgroup group, int myselfId, int parentId)
	{
		AppApplication app;
		AppAppgroup chdGrp;
		String jsonGroup;
		
		jsonGroup = "";
		List<AppApplication> appList = appLicationMapper.selectByGroupId(group.getGroupId());
		if(appList != null)
		{
			for(int k=0;k<appList.size();k++)
			{
				app = appList.get(k);
				if(app != null)
				{
					num ++;
					
					if(!jsonGroup.equals(""))
					{
						jsonGroup = jsonGroup + ", ";
					}
					
					jsonGroup = jsonGroup + "{\"id\":" + num
								+ ", \"pid\":" + myselfId
								+ ", \"name\":\"" + app.getCname()
								+ "\", \"appId\":\""+ app.getAppId()
								+ "\", \"groupId\":\""+ app.getGroupId()
								+ "\", \"finsId\":\"" + app.getFinsCode()
								+ "\", \"zhuangtai\":\"" + app.getStatus()
								+ "\", \"paixu\":\"" + app.getSeqno()
								+ "\"}";
				}
			}
			
			List<AppAppgroup> chdGrplist = groupmapper.selectByParentId(group.getGroupId());
			if(chdGrplist != null)
			{
				for(int k=0;k<chdGrplist.size();k++)
				{
					chdGrp = chdGrplist.get(k);
					if(chdGrp != null)
					{
						num ++;
						String jsonChildGrp = getGroupJson(chdGrp, num, myselfId);
						if(!jsonChildGrp.equals(""))
						{
							if(jsonGroup.equals(""))
							{
								jsonGroup = jsonChildGrp;
							}
							else
							{
								jsonGroup = jsonGroup + ", " + jsonChildGrp;
							}
						}
					}
				}
			}
		}
		
		if(!jsonGroup.equals(""))
		{
			jsonGroup = "{\"id\":" + myselfId + ", \"pid\":" + parentId
						+ ", \"name\":\"" + group.getName()
			          	+ "\", \"groupId\":\"" + group.getGroupId()
			          	+ "\", \"finsId\":\"" + group.getFinsId()
						+ "\"}, " + jsonGroup;
		}
		
		return(jsonGroup);
	}
	
	
	@Override
	public PageBeanUtil<AppApplication> selectByPage(int limit, int offset, String groupId) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<AppApplication> allItems = appLicationMapper.selectByGroupId(groupId);
			int countNums = appLicationMapper.count(groupId);// 总记录数
			PageBeanUtil<AppApplication> pageData = new PageBeanUtil<AppApplication>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
        }
	}
	
	@Override
	public List<AppApplication> selectByFinsCode(String finsCode)throws Exception{
		try{
			if(finsCode != null && !finsCode.equals("")){
				List<AppApplication> list = appLicationMapper.selectByFinsCode(finsCode);
				return list;
			}else{
				throw new Exception("机构编号为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addApplication(AppApplication application) throws Exception{
		try{
			appLicationMapper.insertSelective(application);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateApplication(AppApplication application) throws Exception{
		try{
			appLicationMapper.updateByPrimaryKey(application);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String appId)throws Exception{
		try{
			if(appId != null && !appId.equals("")){
				appLicationMapper.deleteByPrimaryKey(appId);
			}else{
				throw new Exception("机构Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public AppApplication getApplicationById(String appId)throws Exception{
		try{
			if(appId != null && !appId.equals("")){
				AppApplication application = appLicationMapper.selectByPrimaryKey(appId);
		        return application;
			}else{
				throw new Exception("产品编号为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<AppApplication> selectAllApplications() throws Exception {
		try {
			List<AppApplication> list = appLicationMapper.selectAllApplications();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AppApplication> selectByGroupId(String groupId) throws Exception {
		try {
			List<AppApplication> list = appLicationMapper.selectByGroupId(groupId);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public AppApplication selectByModelId(String modelId) throws Exception {
		if(StringUtils.isEmpty(modelId)){
			throw new Exception("产品标识为空！");
		}
		try {
			return appLicationMapper.selectByPrimaryKey(modelId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询错误！");
		}
	}


	@Override
	public List<AppApplication> selectByOrgId(String orgId) throws Exception {
		if(StringUtils.isEmpty(orgId)){
			throw new Exception("部门表示为空！");
		}
		try {
			return appLicationMapper.selectApplicationsByOrg(orgId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询产品出错！");
		}
	}


	@Override
	public List<AppApplication> quetyByFinsIdType(AppApplication record) throws Exception{
		if(record == null){
			throw new Exception("流程查询条件为空！");
		}
		try {
			return appLicationMapper.quetyByFinsIdType(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询产品出错！");
		}
	}

}
