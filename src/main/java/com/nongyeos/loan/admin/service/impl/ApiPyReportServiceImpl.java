package com.nongyeos.loan.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.nongyeos.loan.admin.entity.ApiPyReport;
import com.nongyeos.loan.admin.mapper.ApiPyReportMapper;
import com.nongyeos.loan.admin.service.ApiPyReportService;

@Service("apiPyReportService")
public class ApiPyReportServiceImpl implements ApiPyReportService{

	@Autowired
	private ApiPyReportMapper apiPyReportMapper;

	@Override
	public ApiPyReport queryPyReportSelective(ApiPyReport record)
			throws Exception {
		try {
			if(record == null){
				throw new Exception("ApiPyReport对象为空");
			}
			if(StringUtils.isEmpty(record.getIdCardNo())){
				throw new Exception("身份证号为空");
			}
			
			ApiPyReport pyReport = apiPyReportMapper.queryPyReportSelective(record);
			return pyReport;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addPyReportSelective(ApiPyReport record) throws Exception {
		try {
			if(record == null){
				throw new Exception("ApiPyReport对象为空");
			}
			
			return apiPyReportMapper.addPyReportSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ApiPyReport record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ApiPyReport selectByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(ApiPyReport record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(ApiPyReport record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}