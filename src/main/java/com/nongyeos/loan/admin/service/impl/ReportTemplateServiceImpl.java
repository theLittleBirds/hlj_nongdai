package com.nongyeos.loan.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.ReportTemplate;
import com.nongyeos.loan.admin.mapper.ReportTemplateMapper;
import com.nongyeos.loan.admin.service.IReportTemplateService;

@Service("reportTemplateService")
public class ReportTemplateServiceImpl implements IReportTemplateService {
	
	@Autowired
	private ReportTemplateMapper reportTemplateMapper;

	@Override
	public List<ReportTemplate> selectByRptId(String rptId) throws Exception {
		List<ReportTemplate> list = null;
		try{
			if(rptId != null && !rptId.equals("")){
				list = reportTemplateMapper.selectByRptId(rptId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("rptId为空");
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addTemplate(ReportTemplate reportTemplate) throws Exception {
		try{
			if(reportTemplate != null){
				reportTemplateMapper.insertSelective(reportTemplate);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("reportTemplate为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateTemplate(ReportTemplate reportTemplate) throws Exception {
		try{
			if(reportTemplate != null){
				reportTemplateMapper.updateByPrimaryKey(reportTemplate);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("reportTemplate为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delRptTemplate(String templateId) throws Exception {
		try{
			if(templateId != null && !templateId.equals("")){
				reportTemplateMapper.deleteByPrimaryKey(templateId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("templateId为空");
		}
	}

	@Override
	public boolean existenceRptId(String rptId, String tplId) throws Exception {
		boolean b = false;
		try{
			if(rptId != null && !rptId.equals("")){
				List<ReportTemplate> list=reportTemplateMapper.existenceRptId(rptId, tplId);
				if(list!=null && list.size()>0){
					b=true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("rptId为空");
		}
		return b;
	}

	@Override
	public ReportTemplate selectByRptIdAndStatus(String rptId,Short modelStatusOpen) throws Exception{
		ReportTemplate reportTemplate = null;
		try{
			if(rptId != null && !rptId.equals("")){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("rptId", rptId);
				map.put("status", modelStatusOpen);
				reportTemplate = reportTemplateMapper.selectByRptIdAndStatus(map);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("rptId为空");
		}
		return reportTemplate;
	}

	@Override
	public ReportTemplate selectByTplId(String tplId) throws Exception {
		ReportTemplate reportTemplate = null;
		try{
			if(tplId != null && !tplId.equals("")){
				reportTemplate = reportTemplateMapper.selectByPrimaryKey(tplId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("tplId为空");
		}
		
		return reportTemplate;
	}


}
