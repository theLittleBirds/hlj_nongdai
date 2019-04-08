package com.nongyeos.loan.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.ReportSourcesql;
import com.nongyeos.loan.admin.mapper.ReportSourcesqlMapper;
import com.nongyeos.loan.admin.service.IReportSourcesqlService;

@Service("reportSourcesqlService")
public class ReportSourcesqlServiceImpl implements IReportSourcesqlService {
	
	@Autowired
	private ReportSourcesqlMapper reportSourcesqlMapper;

	@Override
	public List<ReportSourcesql> selectByTplId(String tplId) throws Exception {
		List<ReportSourcesql> list = null;
		try{
			if(tplId != null && !tplId.equals("")){
				list = reportSourcesqlMapper.selectByTplId(tplId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("rptId为空");
		}
		return list;
	}
	
	@Override
	public List<ReportSourcesql> getList() throws Exception {
		List<ReportSourcesql> list = null;
		try{
				list = reportSourcesqlMapper.getList();
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("sourcesqllist为空");
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(ReportSourcesql reportSourcesql) throws Exception {
		try{
			if(reportSourcesql != null){
				reportSourcesqlMapper.insertSelective(reportSourcesql);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("reportSourcesql为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(ReportSourcesql reportSourcesql) throws Exception {
		try{
			if(reportSourcesql != null){
				reportSourcesqlMapper.updateByPrimaryKey(reportSourcesql);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("reportTemplate为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void del(String sourcesqlId) throws Exception {
		try{
			if(sourcesqlId != null && !sourcesqlId.equals("")){
				reportSourcesqlMapper.deleteByPrimaryKey(sourcesqlId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("sourcesqlId为空");
		}
	}

	@Override
	public List<ReportSourcesql> selectByTplIdAndStatus(String tplId,Short sqlStatusOpen) throws Exception {
		List<ReportSourcesql> list = null;
		try{
			if(tplId != null && !tplId.equals("")){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("tplId", tplId);
				map.put("status", sqlStatusOpen);
				list = reportSourcesqlMapper.selectByTplIdAndStatus(map);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("tplId为空");
		}
		return list;
	}

}
