package com.nongyeos.loan.admin.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.ReportEntry;
import com.nongyeos.loan.admin.entity.ReportRptgroup;
import com.nongyeos.loan.admin.mapper.ReportEntryMapper;
import com.nongyeos.loan.admin.mapper.ReportRptgroupMapper;
import com.nongyeos.loan.admin.service.IReportEntryService;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.mapper.BusFinsMapper;

@Service("reportEntry")
public class ReportEntryServiceImpl implements IReportEntryService {

	@Autowired
	private ReportEntryMapper reportEntryMapper;
	@Autowired
	private ReportRptgroupMapper reportGroupMapper;
	@Autowired
	private BusFinsMapper busFinsMapper;
	
	int num;
	
	@Override
	public List<ReportEntry> selectByGroupId(String groupId) throws Exception {
		List<ReportEntry> list = null;
		try{
			if(groupId != null && !groupId.equals("")){
				list=reportEntryMapper.selectByGroupId(groupId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("groupId为空"); 
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addEntry(ReportEntry reportEntry) throws Exception {
		try{
			if(reportEntry != null){
				reportEntryMapper.insertSelective(reportEntry);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("reportEntry为空"); 
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateEntry(ReportEntry reportEntry) throws Exception {
		try{
			if(reportEntry != null){
				reportEntryMapper.updateByPrimaryKey(reportEntry);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("reportEntry为空"); 
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delRptEntry(String rptId) throws Exception {
		try{
			if(rptId != null && !rptId.equals("")){
				reportEntryMapper.deleteByPrimaryKey(rptId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("rptId为空"); 
		}
	}

	@Override
	public String appTreeString() throws Exception {

		ReportRptgroup reportGroup;
		int myselfNum;
		String jsonAll;
		String jsonFins;
		
		num = 0;
		jsonAll = "";
		
		List<BusFins> FList = busFinsMapper.selectAll();
		if(FList != null)
		{
			for(int i=0;i<FList.size();i++)
			{
				BusFins beanFins = FList.get(i);				
				List<ReportRptgroup> retGroupList = reportGroupMapper.selectByFinsId(beanFins.getFinsId());
				if(retGroupList != null)
				{
					num ++;
					jsonFins = "";
					myselfNum = num;
					
					for(int j=0;j<retGroupList.size();j++)
					{						
						reportGroup = retGroupList.get(j);
						if(reportGroup != null)
						{
							num ++;
							String jsonGrp = getGroupJson(reportGroup, num, myselfNum);
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
	
	private String getGroupJson(ReportRptgroup reportGroup, int myselfId, int parentId)
	{
		ReportEntry reportEntry;
		ReportRptgroup chdGrp;
		String jsonGroup;
		
		jsonGroup = "";
		List<ReportEntry> reportList = reportEntryMapper.selectByGroupId(reportGroup.getGroupId());
		if(reportList != null)
		{
			for(int k=0;k<reportList.size();k++)
			{
				reportEntry = reportList.get(k);
				if(reportEntry != null)
				{
					num ++;
					
					if(!jsonGroup.equals(""))
					{
						jsonGroup = jsonGroup + ", ";
					}
					
					jsonGroup = jsonGroup + "{\"id\":" + num
								+ ", \"pid\":" + myselfId
								+ ", \"name\":\"" + reportEntry.getName()
								+ "\", \"rptId\":\""+ reportEntry.getRptId()
								+ "\", \"groupId\":\""+ reportEntry.getGroupId()
								+ "\", \"finsId\":\"" + reportEntry.getFinsId()
								+ "\", \"paixu\":\"" + reportEntry.getSeqno()
								+ "\"}";
				}
			}
			
			List<ReportRptgroup> chdGrplist = reportGroupMapper.selectByParentId(reportGroup.getGroupId());
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
						+ ", \"name\":\"" + reportGroup.getName()
			          	+ "\", \"groupId\":\"" + reportGroup.getGroupId()
			          	+ "\", \"finsId\":\"" + reportGroup.getFinsId()
						+ "\"}, " + jsonGroup;
		}
		
		return(jsonGroup);
	}

	@Override
	public List doSql(String sqlstr) throws Exception {
		List list=null;
		List list1=new ArrayList<>();
		try{
			if(sqlstr != null && !"".equals(sqlstr)){
				list = reportEntryMapper.doSql(sqlstr);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = (Map<String, Object>) list.get(i);
					Collection<Object> obj = map.values();
					Object[] obj1 = obj.toArray();
					list1.add(obj1);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("sqlstr为空"); 
		}
		return list1;
	}

	@Override
	public List doLookSql(String sqlstr) throws Exception {
		List list=null;
		List<List> list2 = new ArrayList<List>();
		try{
			if(sqlstr != null && !"".equals(sqlstr)){
				list = reportEntryMapper.doSql(sqlstr);
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = (Map<String, Object>) list.get(i);
					Collection<Object> obj = map.values();
					Object[] obj1 = obj.toArray();
					List list1=new ArrayList<>();
					for(int j = 0; j < obj1.length; j++){
						list1.add(obj1[j]);
					}
					list2.add(list1);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("sqlstr为空"); 
		}
		return list2;
	}

}
